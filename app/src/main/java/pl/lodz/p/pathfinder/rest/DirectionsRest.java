package pl.lodz.p.pathfinder.rest;

import pl.lodz.p.pathfinder.json.directions.DirectionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by QDL on 2017-01-24.
 */

public interface DirectionsRest
{
    //@GET("json?origin=%C5%81%C3%B3d%C5%BA&destination=Warszawa&mode=transit&key=AIzaSyBmTT2y-DlDUsw1oND7FES1VF_2UBEtUzo")
    @GET("maps/api/directions/json?mode=transit&key=AIzaSyBmTT2y-DlDUsw1oND7FES1VF_2UBEtUzo")
    Call<DirectionsResponse> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("departure_time") Integer departTime);
}
