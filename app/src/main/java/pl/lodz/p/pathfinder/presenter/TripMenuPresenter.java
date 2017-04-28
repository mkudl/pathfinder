package pl.lodz.p.pathfinder.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.service.PoiPhotoClient;
import pl.lodz.p.pathfinder.service.TripDownloadService;
import pl.lodz.p.pathfinder.view.TripCardRVAdapter;
import pl.lodz.p.pathfinder.view.TripMenuActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-13.
 */

public abstract class TripMenuPresenter
{
    protected TripDownloadService tripDownloadService;
    PoiPhotoClient poiPhotoClient;
    RepresentativePoiStrategy poiStrategy;

    protected TripMenuActivity view;
    private List<Trip> tripList;
    private List<Bitmap> photos;



    TripMenuPresenter(TripDownloadService tripDownloadService, TripMenuActivity view, PoiPhotoClient poiPhotoClient, RepresentativePoiStrategy poiStrategy)
    {
        this.tripDownloadService = tripDownloadService;
        this.view = view;
        this.poiPhotoClient = poiPhotoClient;
        this.poiStrategy = poiStrategy;
    }

    public void startActivity()
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        downloadTrips(idToken);
    }

    abstract void downloadTrips(String idToken);

    synchronized void returnData()
    {
        view.onDataRetrieved(createRVAdapter(getTripList()));
        view.hideSpinner();
        Log.d("TripMenuPresenter","Finished getting data");
        downloadPhotos();
    }

    TripCardRVAdapter createRVAdapter(List<Trip> trips)
    {
        return new TripCardRVAdapter(trips);
    }

    //TODO possible SRP violation
    private void downloadPhotos()
    {
        //TODO move to constructor
//        RepresentativePoiStrategy poiStrategy = new ChooseFirstPoiStrategy();

        //for every trip chooses the poi that best represents it (according to strategy)
        //then retrieves its photo and stores it in a list
        Observable.just(getTripList())
                .flatMap(Observable::from)
                .map( t -> poiStrategy.pickRepresentativePoi(t).getGoogleID())
                .map( s -> poiPhotoClient.getPhoto(s))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( (List<Bitmap> x) -> setPhotos(x),
                        Throwable::printStackTrace,
                        () -> updatePhotos());
    }

    private void updatePhotos()
    {
        view.updatePhotos(photos);
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

    private void setPhotos(List<Bitmap> photos)
    {
        this.photos = photos;
    }
}
