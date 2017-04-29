package pl.lodz.p.pathfinder.presenter;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.service.TripUploadService;
import pl.lodz.p.pathfinder.view.TripAddActivity;
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
    }


}
