package pl.lodz.p.pathfinder.presenter;

import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import pl.lodz.p.pathfinder.model.DetailDirections;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.SimpleDirections;
import pl.lodz.p.pathfinder.service.DistanceMatrixApiClient;
import pl.lodz.p.pathfinder.service.SimpleDirectionsCallback;
import pl.lodz.p.pathfinder.service.directions.DetailedDirectionsCallback;
import pl.lodz.p.pathfinder.service.directions.DirectionsApiClient;
import pl.lodz.p.pathfinder.view.TripViewingActivity;

/**
 * Created by QDL on 2017-04-02.
 */

public class TripViewingPresenter implements SimpleDirectionsCallback, DetailedDirectionsCallback
{
    //TODO change to interfaces, decouple etc.

    private TripViewingActivity view;

    private DistanceMatrixApiClient simpleDirectionsApi; //TODO see if this can be turned into a local variable


    public TripViewingPresenter(TripViewingActivity view)
    {
        this.view = view;
        simpleDirectionsApi = new DistanceMatrixApiClient(this);
    }


    public void getSimpleDirections(List<PointOfInterest> pois)
    {
        //get directions from current element to next element
        for(int i=0; i<=pois.size()-2 ;i++)
        {
            simpleDirectionsApi.sendRequest(pois.get(i).getPosition(),pois.get(i+1).getPosition(), i);

        }

    }


    public void startNavigation(List<PointOfInterest> pois)
    {
        DirectionsApiClient detailDirectionsApi = new DirectionsApiClient(this);


        for(int i=0; i<=pois.size()-2 ;i++)
        {
            detailDirectionsApi.sendRequest(pois.get(i).getPosition(),pois.get(i+1).getPosition(),i);

        }

        view.clearDrawings();
    }


    public void loadDirectionsFromLocation(LatLng currentLocation, PointOfInterest poi)
    {
        //TODO? create non-anonymous class
        DirectionsApiClient directionsApi = new DirectionsApiClient(new DetailedDirectionsCallback()
        {
            @Override
            public void successCallback(DetailDirections directions, int itemPosition)
            {
                view.drawDirections(directions);
            }

            @Override
            public void apiFailCallback(String statusCode)
            {
                view.handleDirectionsFail();
            }

            @Override
            public void failCallback(Throwable t)
            {
                view.handleDirectionsFail();
            }
        });

        directionsApi.sendRequest(currentLocation,poi.getPosition(),0);

    }







    //Distance Matrix API client callback
    @Override
    public void successCallback(SimpleDirections directions, int itemPosition)
    {
        view.updateDirections(directions,itemPosition);
    }


    //Directions API client callback
    @Override
    public void successCallback(DetailDirections directions, int itemPosition)
    {
        view.startNavigation(directions,itemPosition);
        view.drawDirections(directions);
    }



    //Directions API client & Distance Matrix API client failure callbacks
    @Override
    public void apiFailCallback(String statusCode)
    {
        view.handleDirectionsFail();
    }

    @Override
    public void failCallback(Throwable t)
    {
        view.handleDirectionsFail();
    }

    @Override
    public void httpCodeFailCallback(int code)
    {
        view.handleDirectionsFail();
    }

    @Override
    public void apiFailTopLevelCallback(String code)
    {
        view.handleDirectionsFail();
    }

    @Override
    public void apiFailElementCallback(String code)
    {
        view.handleDirectionsFail();
    }
}
