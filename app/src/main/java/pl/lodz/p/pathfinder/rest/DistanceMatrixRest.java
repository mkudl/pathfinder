package pl.lodz.p.pathfinder.rest;

import pl.lodz.p.pathfinder.json.distance.DistanceMatrixResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by QDL on 2017-03-29.
 */

public interface DistanceMatrixRest
{
    @GET("maps/api/distancematrix/json?mode=transit&key=AIzaSyBmTT2y-DlDUsw1oND7FES1VF_2UBEtUzo")
    Call<DistanceMatrixResponse> getDirections(@Query("origins") String origin, @Query("destinations") String destination);

    @GET("maps/api/distancematrix/json?mode=walking&key=AIzaSyBmTT2y-DlDUsw1oND7FES1VF_2UBEtUzo")
    Call<DistanceMatrixResponse> getDirectionsWalking(@Query("origins") String origin, @Query("destinations") String destination);
}
