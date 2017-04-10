package pl.lodz.p.pathfinder.service;

import java.util.List;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.json.server.PointOfInterestJson;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.rest.DatabasePoiRest;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by QDL on 2017-04-10.
 */

public class PoiRepository
{

    private Retrofit rxRetrofit;
    private DatabasePoiRest restClient;
    private PointOfInterestClient poiClient;


    public PoiRepository(Retrofit rxRetrofit, PointOfInterestClient poiClient)
    {
        this.rxRetrofit = rxRetrofit;
//        this.restClient = restClient;
        this.poiClient = poiClient;

        this.restClient = rxRetrofit.create(DatabasePoiRest.class);
    }

    public Observable<List<PointOfInterest>> loadUserCreatedPois(String idToken)
    {
        return  restClient.loadUserCreatedPois(idToken)
                .flatMap(Observable::from)
                .map( pj -> poiClient.retrievePoiDetails(pj.getGoogleID()))
                .toList();
    }

    public Observable<List<PointOfInterest>>  loadUserFavoritePois(String idToken)
    {
        return  restClient.loadUserFavoritePois(idToken)
                .flatMap(Observable::from)
                .map( pj -> poiClient.retrievePoiDetails(pj.getGoogleID()))
                .toList();
    }

    public Observable<ResponseBody> addPoiToFavorites(String idToken, String poiGoogleId)
    {
        return restClient.addPoiToFavorites(idToken,poiGoogleId);
    }


}
