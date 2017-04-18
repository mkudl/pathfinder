package pl.lodz.p.pathfinder.presenter;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
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

public abstract class TripMenuPresenter
{
    protected TripDownloadService tripDownloadService;
    protected TripMenuActivity view;
    private List<Trip> tripList;

    TripMenuPresenter(TripDownloadService tripDownloadService, TripMenuActivity view)
    {
        this.tripDownloadService = tripDownloadService;
        this.view = view;
    }

    public void startActivity()
    {

        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        downloadTrips(idToken);

    }

    abstract void downloadTrips(String idToken);

    synchronized void returnData()
    {
        view.onDataRetrieved(getTripList());
        view.hideSpinner();
        Log.d("TripMenuPresenter","Finished getting data");
    }

    protected void onConnectionFailure(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        Log.d("TripMenuPresenter", sw.toString());

        view.displayCreationErrorMessage(t);
        view.hideSpinner();
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
