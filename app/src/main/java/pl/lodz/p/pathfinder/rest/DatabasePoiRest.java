package pl.lodz.p.pathfinder.rest;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by QDL on 2017-04-10.
 */

public interface DatabasePoiRest
{
    @GET("poi/getAll")
    Observable<List<PointOfInterest>> loadUserCreatedPois(@Query("idToken") String idToken);

    @GET("poi/favorites")
    Observable<List<PointOfInterest>> loadUserFavoritePois(@Query("idToken") String idToken);

    @POST("poi/addFavorite")
    Observable<ResponseBody> addPoiToFavorites(@Query("idToken") String idToken, @Query("poiGoogleId") String poiGoogleId);

    @DELETE("poi/removeFavorite")
    Observable<ResponseBody> removePoiFromFavorites(@Query("idToken") String idToken, @Query("poiGoogleId") String poiGoogleId);

    @POST("poi/addCreated")
    Observable<ResponseBody> addCreatedPoi(@Query("idToken") String idToken, @Query("poiGoogleId") String poiGoogleId);

    @GET("poi/checkFavorite")
    Observable<Map<String,Boolean>> checkFavorite(@Query("idToken") String idToken, @Query("poiGoogleId") String poiGoogleId);
}
