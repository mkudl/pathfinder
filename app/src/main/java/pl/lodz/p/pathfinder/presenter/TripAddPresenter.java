package pl.lodz.p.pathfinder.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.TripConverter;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import pl.lodz.p.pathfinder.service.TripRepository;
import pl.lodz.p.pathfinder.service.TripUploadService;
import pl.lodz.p.pathfinder.view.TripAddActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-11.
 */

public class TripAddPresenter
{

    private TripAddActivity view;
    private List<PointOfInterest> poiList = new ArrayList<>();
    private TripUploadService tripUploadService;


    public TripAddPresenter(TripAddActivity view, TripUploadService tripUploadService)
    {
        this.view = view;
        this.tripUploadService = tripUploadService;
    }

    public void addPoi(PointOfInterest poi)
    {
        poiList.add(poi);
        view.poiPickCallback(poi);
    }

    public void removePoi(PointOfInterest poi,View child)
    {
        poiList.remove(poi);
        view.removeSimplePoiChildView(child);
    }




    public void saveTrip(String name, String description)
    {

        //TODO see whether this should be created here
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();

        Trip createdTrip = new Trip(name,description,poiList);

        tripUploadService.createTrip(idToken,createdTrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x ->
                        {Log.d("TripAddPresenter", "Received response for the create trip endpoint: " + x.toString());
                        view.displayCreationSuccessMessage();}
                , t -> {
                            t.printStackTrace();
                            view.displayCreationErrorMessage(t);
                        }
                , () -> view.finishActivity());



        //TODO on fail display snackbar
        //TODO on success display message then close? toast then close?
    }


}
