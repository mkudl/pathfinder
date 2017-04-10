package pl.lodz.p.pathfinder.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.TripConverter;
import pl.lodz.p.pathfinder.json.server.TripJson;
import pl.lodz.p.pathfinder.json.server.TripJsonWrapper;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


//TODO? change name (service might be more appropriate)
public class TripRepository
{

    private Retrofit rxRetrofit;
    private DatabaseTripRest restClient;
    private PointOfInterestClient poiClient;


    public TripRepository(PointOfInterestClient poiClient)
    {
        this.poiClient = poiClient;

        this.rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
//            .client(httpClient.build()) //for debugging
                .build();

        restClient = rxRetrofit.create(DatabaseTripRest.class);
    }


    /**
     * get list of trips from the server, convert from json representation to model, then return the whole list
     * part of the conversion process involves a call to the Google Places API, in order to retrieve
     * details of each POI
     * @return  List of pois created by the currently logged in user
     */
    Observable<List<Trip>> loadUserCreated()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadUserCreated(idToken)
                .flatMap( Observable::from)
                .map( tj -> TripConverter.convertToModel(tj,poiClient) )
                .toList();
    }

    Observable<List<Trip>> loadUserFavorites()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadUserFavorites(idToken)
                .flatMap( Observable::from)
                .map( tj -> TripConverter.convertToModel(tj,poiClient) )
                .toList();
    }

    Observable<List<Trip>> loadRecommended()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadRecommended(idToken)
                .flatMap( Observable::from)
                .map( tj -> TripConverter.convertToModel(tj,poiClient) )
                .toList();
    }




    Observable<ResponseBody> createTrip(Trip tripToCreate)
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.createTrip(TripConverter.convertToJsonWrapper(idToken,tripToCreate));
    }

    Observable<ResponseBody> updateTrip(Trip tripToUpdate)
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.updateTrip(TripConverter.convertToJsonWrapper(idToken,tripToUpdate));
    }

    Observable<ResponseBody> addToFavorites(Trip favoriteTrip)
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.addToFavorites(TripConverter.convertToJsonWrapper(idToken,favoriteTrip));
    }









//TODO? remove
//    public void getPoisForTrip(List<String> googleIDs, Trip trip, Context context)
//    {
//        //TODO? think about moving to Rx
//        ArrayList<PointOfInterest> poiList = new ArrayList<>();
//
//
//
//        for (String s : googleIDs)
//        {
//            poiList.add(null);
//            PointOfInterestClient poiClient = new PointOfInterestClient(context);
//            poiClient.retrievePoiDetails(s);
//        }
//    }





}
