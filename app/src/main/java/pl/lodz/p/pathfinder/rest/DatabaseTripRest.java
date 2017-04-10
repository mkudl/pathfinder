package pl.lodz.p.pathfinder.rest;

import java.util.List;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.TokenJson;
import pl.lodz.p.pathfinder.json.server.TripJson;
import pl.lodz.p.pathfinder.json.server.TripJsonWrapper;
import pl.lodz.p.pathfinder.model.Trip;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by QDL on 2017-04-06.
 */

public interface DatabaseTripRest
{
    //TODO? rename class and methods to be explicitly Trip-related


//    @GET("trip/getAll")
//    Call<List<Trip>> loadUserCreated(@Query("idToken") String idToken);
//
//    @GET("trip/favorites")
//    Call<List<Trip>> loadUserFavorites(@Query("idToken") String idToken);
//
//    @GET("trip/recommended")
//    Call<List<Trip>> loadRecommended(@Query("idToken") String idToken);
//
//    @POST("trip/create")
//    Call<ResponseBody> createTrip(@Body TripJsonWrapper tripRequest);
//
//    @PUT("trip/update")
//    Call<ResponseBody> updateTrip(@Body TripJsonWrapper tripRequest);
//
//    @PUT("trip/addFavorite")
//    Call<ResponseBody> addToFavorites(@Body TripJsonWrapper tripRequest);


    @GET("trip/getAll")
    Observable<List<TripJson>> loadUserCreated(@Query("idToken") String idToken);

    @GET("trip/favorites")
    Observable<List<TripJson>> loadUserFavorites(@Query("idToken") String idToken);

    @GET("trip/recommended")
    Observable<List<TripJson>> loadRecommended(@Query("idToken") String idToken);

    //TODO observable?
    @POST("trip/create")
    Observable<ResponseBody> createTrip(@Body TripJsonWrapper tripRequest);

    @PUT("trip/update")
    Observable<ResponseBody> updateTrip(@Body TripJsonWrapper tripRequest);

    @PUT("trip/addFavorite")
    Observable<ResponseBody> addToFavorites(@Body TripJsonWrapper tripRequest);

}
