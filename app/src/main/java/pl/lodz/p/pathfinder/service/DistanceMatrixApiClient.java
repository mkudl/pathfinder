package pl.lodz.p.pathfinder.service;

import com.google.android.gms.maps.model.LatLng;

import pl.lodz.p.pathfinder.json.distance.DistanceMatrixResponse;
import pl.lodz.p.pathfinder.json.distance.Element;
import pl.lodz.p.pathfinder.model.SimpleDirections;
import pl.lodz.p.pathfinder.rest.DistanceMatrixRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by QDL on 2017-03-29.
 */

public class DistanceMatrixApiClient
{

    private SimpleDirectionsCallback directionsCallback;

    public DistanceMatrixApiClient(SimpleDirectionsCallback directionsCallback)
    {
        this.directionsCallback = directionsCallback;
    }





    public void sendRequest(LatLng origin, LatLng destination,int itemPosition)
    {
        callTransitApi(""+origin.latitude+","+origin.longitude , ""+destination.latitude+","+destination.longitude, itemPosition);
    }

    private void callTransitApi(String origin, String destination,int itemPosition)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/").addConverterFactory(GsonConverterFactory.create()).build();
        DistanceMatrixRest rI = retrofit.create(DistanceMatrixRest.class);
        Call<DistanceMatrixResponse> response = rI.getDirections(origin,destination);
        response.enqueue(new DistanceMatrixApiClientCallback(itemPosition,directionsCallback));     //register  a callback
    }

    private void callWalkingApi(String origin, String destination,int itemPosition)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/").addConverterFactory(GsonConverterFactory.create()).build();
        DistanceMatrixRest rI = retrofit.create(DistanceMatrixRest.class);
        Call<DistanceMatrixResponse> response = rI.getDirectionsWalking(origin,destination);
        response.enqueue(new DistanceMatrixApiClientCallback(itemPosition,directionsCallback));     //register  a callback
    }







    private class DistanceMatrixApiClientCallback  implements Callback<DistanceMatrixResponse>
    {

        private int itemPosition;
        private SimpleDirectionsCallback directionsCallback;

         DistanceMatrixApiClientCallback(int itemPosition, SimpleDirectionsCallback directionsCallback)
        {
            this.itemPosition = itemPosition;
            this.directionsCallback = directionsCallback;
        }





        @Override
        public void onResponse(Call<DistanceMatrixResponse> call, Response<DistanceMatrixResponse> response)
        {
            if (!response.isSuccessful()){  //call returns html error code
                directionsCallback.httpCodeFailCallback(response.code());
                return;     // avoid trying to check nulls by returning early
            }

            boolean apiStatusOk = response.body().getStatus().equals("OK");

            if (!apiStatusOk) {     //call returns a response, but the api returns a top-level error
                directionsCallback.apiFailTopLevelCallback(response.body().getStatus());
                return;
            }

            //According to the documentation the API won't return more than one row or element
            // for a single origin-destination pair, so it should be fine to just get the 1st item
            boolean pathStatusOk = response.body().getRows().get(0).getElements().get(0).getStatus().equals("OK");

            if(apiStatusOk && pathStatusOk && response.isSuccessful())  //api call is processed successfully, api returns
            {
                Element element = response.body().getRows().get(0).getElements().get(0);
                SimpleDirections sd = new SimpleDirections();
                sd.setDuration(element.getDuration().getText());
                sd.setDuration_actual(element.getDuration().getValue());
                sd.setDistance(element.getDistance().getText());
                sd.setDistance_actual(element.getDistance().getValue());

                directionsCallback.successCallback(sd,itemPosition);

//            if(response.body().getRows().size()>1)  Log.wtf("Matrix Distance API","Response has more rows than expected");

            } else if (!pathStatusOk) {    //call returns a response, but there is no public transport connection
                String errCode = response.body().getRows().get(0).getElements().get(0).getStatus();
                if(errCode.equals("ZERO_RESULTS")){
                    //try again in walking mode
                DistanceMatrixApiClient.this.callWalkingApi(response.body().getOriginAddresses().get(0),response.body().getDestinationAddresses().get(0), itemPosition);
                }else{
                    directionsCallback.apiFailElementCallback(errCode);
                }
            }

        }

        @Override
        public void onFailure(Call<DistanceMatrixResponse> call, Throwable t)
        {
            directionsCallback.failCallback(t);

        }
    }
}
