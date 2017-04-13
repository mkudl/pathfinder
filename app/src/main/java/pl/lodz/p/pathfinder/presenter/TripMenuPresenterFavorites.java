package pl.lodz.p.pathfinder.presenter;

import android.util.Log;

import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.service.TripDownloadService;
import pl.lodz.p.pathfinder.view.TripMenuActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-13.
 */

public class TripMenuPresenterFavorites
{

    private TripDownloadService tripDownloadService;

    private List<Trip> tripList;

    private TripMenuActivity view;


    public TripMenuPresenterFavorites(TripDownloadService tripDownloadService, TripMenuActivity view)
    {
        this.tripDownloadService = tripDownloadService;
        this.view = view;
    }

    public void startActivity()
    {

        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();

        tripDownloadService.loadUserFavorites(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setTripList(x), t -> t.printStackTrace(), () -> returnData());

    }


    private synchronized void returnData()
    {
        view.onDataRetrieved(getTripList());
        view.hideSpinner();
        Log.d("TripMenuPresenter","Finished getting data");
    }



    public List<Trip> getTripList()
    {
        return tripList;
    }

    public void setTripList(List<Trip> tripList)
    {
        this.tripList = tripList;
    }
}
