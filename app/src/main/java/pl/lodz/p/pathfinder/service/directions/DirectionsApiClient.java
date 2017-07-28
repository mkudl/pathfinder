package pl.lodz.p.pathfinder.service.directions;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import pl.lodz.p.pathfinder.json.directions.DirectionsResponse;
import pl.lodz.p.pathfinder.json.directions.Leg;
import pl.lodz.p.pathfinder.model.DetailDirections;
import pl.lodz.p.pathfinder.rest.DirectionsRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by QDL on 2017-04-01.
 */

public class DirectionsApiClient
{
    private final static String API_RESPONSE_OK = "OK";


    private DetailedDirectionsCallback directionsCallback;


    public DirectionsApiClient(DetailedDirectionsCallback directionsCallback)
    {
        this.directionsCallback = directionsCallback;
    }




    public void sendRequest(LatLng origin, LatLng destination, int itemPosition)
    {
        callDirectionsApi(""+origin.latitude+","+origin.longitude,""+destination.latitude+","+destination.longitude, itemPosition, null);
    }

    public void sendRequest(LatLng origin, LatLng destination, int itemPosition, int departureTime)
    {
        callDirectionsApi(""+origin.latitude+","+origin.longitude,""+destination.latitude+","+destination.longitude, itemPosition,departureTime);
    }



    void callDirectionsApi(String origin, String destination, int itemPosition,Integer time)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/").addConverterFactory(GsonConverterFactory.create()).build();
        DirectionsRest rI = retrofit.create(DirectionsRest.class);
        Call<DirectionsResponse> response = rI.getDirections(origin,destination,time);
        response.enqueue(new DirectionsApiClientCallback(itemPosition,directionsCallback));     //register this as a callback
    }


    private class DirectionsApiClientCallback implements Callback<DirectionsResponse>
    {
        int itemPosition;
        DetailedDirectionsCallback directionsCallback;

        public DirectionsApiClientCallback(int itemPosition, DetailedDirectionsCallback directionsCallback)
        {
            this.itemPosition = itemPosition;
            this.directionsCallback = directionsCallback;
        }


        @Override
        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response)
        {
            String status = response.body().getStatus();
            if( !status.equals(API_RESPONSE_OK)){
                Log.e("Directions Api Client","Error calling api status " + status);
                directionsCallback.apiFailCallback(status);
                return;
            }

            Leg leg = response.body().getRoutes().get(0).getLegs().get(0);
            DetailDirections directions = new DetailDirections();
            directions.setDistance(leg.getDistance().getText());
            directions.setDistance_actual(leg.getDistance().getValue());
            directions.setDuration(leg.getDuration().getText());
            directions.setDuration_actual(leg.getDuration().getValue());
            if(leg.getArrivalTime() != null)    directions.setArrivalTime(leg.getArrivalTime().getText());
            if(leg.getDepartureTime() != null)    directions.setDepartureTime(leg.getDepartureTime().getText());

            directions.setDetails(leg.getSteps());
            directions.setOverviewPolyline(response.body().getRoutes().get(0).getOverviewPolyline());

            directionsCallback.successCallback(directions, itemPosition);
        }

        @Override
        public void onFailure(Call<DirectionsResponse> call, Throwable t)
        {
            directionsCallback.failCallback(t);
        }

    }

}
