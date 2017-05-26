package pl.lodz.p.pathfinder.service;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-04-03.
 */



public class PointOfInterestClient
{
    private Context context;
    private GoogleApiClient googleApiClient;


    public PointOfInterestClient(Context context)
    {
        this.context = context;
    }


    public  List<PointOfInterest> getPoisFromIds(List<String> googleIDs)
    {
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

        if (places.getStatus().isSuccess() && places.getCount() > 0)
        {
            Place place = places.get(0);
            PointOfInterest poi = new PointOfInterest(place.getName().toString(), place.getLatLng(), googleID);
            places.release();
            return poi;
        }
        else return null; //FIXME error handling?
    }


    private PendingResult<PlaceBuffer> retrieveDetailsPending(final String googleID)
    {
        //NOTE  might be a good idea to provide a dependency in constructor
        //also client does not get closed
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();

        return Places.GeoDataApi.getPlaceById(googleApiClient,googleID);
    }



}
