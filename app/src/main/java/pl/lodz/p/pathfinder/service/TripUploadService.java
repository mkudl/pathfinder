package pl.lodz.p.pathfinder.service;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.TripJsonWrapperFactory;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import rx.Observable;

/**
 * Created by QDL on 2017-04-12.
 */

public class TripUploadService
{


    private DatabaseTripRest restClient;


    public TripUploadService(DatabaseTripRest restClient)
    {
        this.restClient = restClient;
    }


    public Observable<ResponseBody> createTrip(String idToken, Trip tripToCreate)
    {
//        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.createTrip(TripJsonWrapperFactory.convertToJsonWrapper(idToken,tripToCreate));
    }

    public Observable<ResponseBody> updateTrip(String idToken, Trip tripToUpdate)
    {
//        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.updateTrip(TripJsonWrapperFactory.convertToJsonWrapper(idToken,tripToUpdate));
    }

    public Observable<ResponseBody> addToFavorites(String idToken, Trip favoriteTrip)
    {
//        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.addToFavorites(TripJsonWrapperFactory.convertToJsonWrapper(idToken,favoriteTrip));
    }

}