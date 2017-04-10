package pl.lodz.p.pathfinder.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-04-03.
 */



public class PointOfInterestClient
{
    Context context;
    GoogleApiClient googleApiClient;

    //TODO can prolly be removed
//    PoiCreatedCallback poiCreatedCallback;


    public PointOfInterestClient(Context context)
    {
        this.context = context;
    }



    //TODO can prolly be removed
//    public void retrievePoiDetailsAsync(final String googleID,final PoiCreatedCallback poiCreatedCallback)
//    {
//        //FIXME
//        googleApiClient = new GoogleApiClient.Builder(context)
////                .enableAutoManage(this /* FragmentActivity */,
////                        this /* OnConnectionFailedListener */)
////                .addConnectionCallbacks(this)
////                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
////                .addApi(Places.PLACE_DETECTION_API)
//                .build();
//        googleApiClient.connect();
//
//        Places.GeoDataApi.getPlaceById(googleApiClient,googleID)
//                .setResultCallback(new ResultCallback<PlaceBuffer>()
//                {
//                    @Override
//                    public void onResult(@NonNull PlaceBuffer places)
//                    {
//                        if (places.getStatus().isSuccess() && places.getCount() > 0)
//                        {
//                            Place place = places.get(0);
//                            PointOfInterest poi = new PointOfInterest(place.getName().toString(), "placeholder"/*FIXME*/, place.getLatLng(), googleID);
//                            poiCreatedCallback.poiInformationRetrieved(poi);
//                        }
//                    }
//                });
//    }



    public  List<PointOfInterest> getPoisFromIds(List<String> googleIDs)
    {
        //TODO? think about moving to Rx
        ArrayList<PointOfInterest> poiList = new ArrayList<>();

        for (String s : googleIDs)
        {
            poiList.add(retrievePoiDetails(s));
        }

        return poiList;
    }



    public PointOfInterest retrievePoiDetails(final String googleID)
    {
        PendingResult<PlaceBuffer> pendingResult = retrieveDetailsPending(googleID);
        PlaceBuffer places = pendingResult.await();

        //FIXME release at the end, return after assign only in if/else
        if (places.getStatus().isSuccess() && places.getCount() > 0)
        {
            Place place = places.get(0);
            PointOfInterest poi = new PointOfInterest(place.getName().toString(), "placeholder"/*FIXME*/, place.getLatLng(), googleID);
            places.release();
            return poi; //TODO merge with ^
        }
        else return null; //FIXME error handling?
    }


    private PendingResult<PlaceBuffer> retrieveDetailsPending(final String googleID)
    {
        //FIXME  PROVIDE AS DEPENDENCY IN CONSTRUCTOR
        googleApiClient = new GoogleApiClient.Builder(context)
//                .enableAutoManage(this /* FragmentActivity */,
//                        this /* OnConnectionFailedListener */)
//                .addConnectionCallbacks(this)
//                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();

        return Places.GeoDataApi.getPlaceById(googleApiClient,googleID);
    }



}
