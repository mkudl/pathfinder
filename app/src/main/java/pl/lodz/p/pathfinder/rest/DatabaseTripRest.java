package pl.lodz.p.pathfinder.rest;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.TokenJson;
import pl.lodz.p.pathfinder.json.server.TripJson;
import pl.lodz.p.pathfinder.json.server.TripJsonWrapper;
import pl.lodz.p.pathfinder.model.Trip;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    @GET("trip/getAll")
    Observable<List<TripJson>> loadUserCreated(@Query("idToken") String idToken);

    @GET("trip/favorites")
    Observable<List<TripJson>> loadUserFavorites(@Query("idToken") String idToken);

    @GET("trip/recommended")
    Observable<List<TripJson>> loadRecommended(@Query("idToken") String idToken);

    @POST("trip/create")
    Observable<ResponseBody> createTrip(@Body TripJsonWrapper tripRequest);

    @PUT("trip/update")
    Observable<ResponseBody> updateTrip(@Body TripJsonWrapper tripRequest);

    @PUT("trip/addFavorite")
    Observable<ResponseBody> addToFavorites(@Query("idToken") String idToken, @Query("tripID") int tripId);

    @DELETE("trip/removeFavorite")
    Observable<ResponseBody> removeFromFavorites(@Query("idToken") String idToken, @Query("tripID") int tripId);

    @GET("trip/checkFavorite")
    Observable<Map<String,Boolean>> checkFavorite(@Query("idToken") String idToken, @Query("tripID") int tripId);

    @DELETE("trip/deleteTrip")
    Observable<ResponseBody> deleteTrip(@Query("idToken") String idToken, @Query("tripID") int tripId);
}
