package pl.lodz.p.pathfinder.presenter;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.DetailDirections;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.SimpleDirections;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.service.DistanceMatrixApiClient;
import pl.lodz.p.pathfinder.service.SimpleDirectionsCallback;
import pl.lodz.p.pathfinder.service.TripFavoriteChecker;
import pl.lodz.p.pathfinder.service.TripUploadService;
import pl.lodz.p.pathfinder.service.directions.DetailedDirectionsCallback;
import pl.lodz.p.pathfinder.service.directions.DirectionsApiClient;
import pl.lodz.p.pathfinder.view.TripViewingActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-02.
 */

public class TripViewingPresenter implements SimpleDirectionsCallback, DetailedDirectionsCallback
{

    private TripViewingActivity view;
    private Trip displayedTrip;
    private boolean isFavorite = false;

    private DistanceMatrixApiClient simpleDirectionsApi;

    private TripFavoriteChecker tripFavoriteChecker;
    private TripUploadService tripUploadService;


    public TripViewingPresenter(TripViewingActivity view, Trip displayedTrip, /*DistanceMatrixApiClient simpleDirectionsApi,*/ TripFavoriteChecker tripFavoriteChecker, TripUploadService tripUploadService)
    {
        this.view = view;
        this.displayedTrip = displayedTrip;

        simpleDirectionsApi = new DistanceMatrixApiClient(this);

        this.tripFavoriteChecker = tripFavoriteChecker;
        this.tripUploadService = tripUploadService;
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
        //NOTE create non-anonymous class?
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

    public void initFavorite()
    {

        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        tripFavoriteChecker.checkFavorite(idToken,displayedTrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setFavorite(x),
                        t -> {t.printStackTrace();
                            view.showDBDownloadErrorMessage();},
                        () -> updateButtonListener(isFavorite));
    }

    private void updateButtonListener(boolean favorite)
    {
        if(favorite) {
            view.setButtonListenerUnfavorite();
        }
        else {
            view.setButtonListenerFavorite();
        }
        view.showFavoriteButtion();
    }


    public void buttonClicked()
    {
        //flip the state first, then update according to the new state
        setFavorite(!isFavorite);
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        if(isFavorite)
        {
            favorite(idToken);
        }
        else
        {
            unfavorite(idToken);
        }
    }

    private void favorite(String idToken)
    {
        tripUploadService.addToFavorites(idToken,displayedTrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> view.showMessageFavorited(),
                        t -> {t.printStackTrace();
                            view.showDBUploadErrorMessage();},
                        () -> view.setButtonListenerUnfavorite());
    }

    private void unfavorite(String idToken)
    {
        tripUploadService.removeFromFavorites(idToken,displayedTrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> view.showMessageUnfavorited(),
                        t -> {t.printStackTrace();
                            view.showDBUploadErrorMessage();},
                        () -> view.setButtonListenerFavorite());
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
        view.hideNavButton();
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



    public void setFavorite(boolean favorite)
    {
        isFavorite = favorite;
    }
}
