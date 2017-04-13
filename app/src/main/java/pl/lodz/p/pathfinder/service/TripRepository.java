package pl.lodz.p.pathfinder.service;

import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.TripFactory;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


//TODO? change name (service might be more appropriate)
public class TripRepository
{

    private Retrofit rxRetrofit;
    private DatabaseTripRest restClient;

    private TripFactory tripFactory;


    public TripRepository(TripFactory tripFactory)
    {
        this.rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
//            .client(httpClient.build()) //for debugging
                .build();
        restClient = rxRetrofit.create(DatabaseTripRest.class);

        this.tripFactory = tripFactory;
    }


    /**
     * get list of trips from the server, convert from json representation to model, then return the whole list
     * part of the conversion process involves a call to the Google Places API, in order to retrieve
     * details of each POI
     * @return  List of pois created by the currently logged in user
     */
    public Observable<List<Trip>> loadUserCreated()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadUserCreated(idToken)
                .flatMap( Observable::from)
                .map( tj -> tripFactory.convertToModel(tj) )
                .toList();
    }

    public Observable<List<Trip>> loadUserFavorites()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadUserFavorites(idToken)
                .flatMap( Observable::from)
                .map( tj -> tripFactory.convertToModel(tj) )
                .toList();
    }

    public Observable<List<Trip>> loadRecommended()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadRecommended(idToken)
                .flatMap( Observable::from)
                .map( tj -> tripFactory.convertToModel(tj) )
                .toList();
    }



}
