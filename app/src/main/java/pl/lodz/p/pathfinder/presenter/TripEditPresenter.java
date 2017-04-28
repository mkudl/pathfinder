package pl.lodz.p.pathfinder.presenter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.service.TripUploadService;
import pl.lodz.p.pathfinder.view.TripEditActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-26.
 */

public class TripEditPresenter
{


    private TripEditActivity view;
    private List<PointOfInterest> poiList;
    private TripUploadService tripUploadService;

    private Trip tripToUpdate;



    public TripEditPresenter(TripEditActivity view, TripUploadService tripUploadService,Trip tripToUpdate)
    {
        this.view = view;
        this.tripUploadService = tripUploadService;
        this.tripToUpdate = tripToUpdate;
        this.poiList = tripToUpdate.getPointOfInterestList();
    }

    public void addPoi(PointOfInterest poi)
    {
        poiList.add(poi);
        view.addPoiPickRepresentation(poi);
    }

    public void removePoi(PointOfInterest poi,View child)
    {
        poiList.remove(poi);
        view.removeSimplePoiChildView(child);
    }




    public void saveTrip(String name, String description)
    {

        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();

        tripToUpdate.setName(name);
        tripToUpdate.setDescription(description);
        tripToUpdate.setPointOfInterestList(poiList);

        tripUploadService.updateTrip(idToken,tripToUpdate)
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

    public void deleteTrip()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        tripUploadService.deleteTrip(idToken,tripToUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x ->
                        {
                            view.displayDeleteSuccessMessage();}
                        , t -> {
                            t.printStackTrace();
                            view.displayDeleteErrorMessage(t);
                        }
                        , () -> view.finishActivity());
    }


}
