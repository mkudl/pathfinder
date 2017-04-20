package pl.lodz.p.pathfinder.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.service.PoiRepository;
import pl.lodz.p.pathfinder.view.PoiAddActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-17.
 */

public class PoiAddPresenter
{
    private PoiAddActivity view;

    private PoiRepository poiRepository;
    private GoogleApiClient googleApiClient;

    private LatLng selectedCoordinates;
    private String selectedAddress;

//    public PoiAddPresenter(PoiAddActivity view)
//    {
//        this.view = view;
//    }

    public PoiAddPresenter(PoiAddActivity view, PoiRepository poiRepository, GoogleApiClient googleApiClient)
    {
        this.view = view;
        this.poiRepository = poiRepository;
        this.googleApiClient = googleApiClient;
    }

    public void updateLocation(LatLng coordinates, String address)
    {
        selectedCoordinates = coordinates;
        selectedAddress = address;
        view.updateLocationDisplay(coordinates,address);
        view.showFinishButton();
    }

    public void createPoi(String name)
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();

        AddPlaceRequest request = new AddPlaceRequest(name,selectedCoordinates,selectedAddress,
                Collections.<Integer>singletonList(Place.TYPE_PARK),"phone number");

//        Places.GeoDataApi.addPlace(googleApiClient,request)
//                .setResultCallback(places -> poiRepository.addPoiToCreated(idToken,places.get(0).getId())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe( x ->
//                                {
//                                    view.displayCreationSuccessMessage();}
//                                , t -> {
//                                    t.printStackTrace();
//                                    view.displayCreationErrorMessage(t);
//                                }
//                                , () -> view.finishActivity()));

        poiRepository.addPoiToCreated(idToken,"ChIJWXvZPtw0GkcR5fe1V3RNltQ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x ->
                        {
                            view.displayCreationSuccessMessage();}
                        , t -> {
                            t.printStackTrace();
                            view.displayCreationErrorMessage(t);
                        }
                        , () -> view.finishActivity());



    }
}
