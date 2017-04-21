package pl.lodz.p.pathfinder.presenter;

import pl.lodz.p.pathfinder.RepresentativePoiStrategy;
import pl.lodz.p.pathfinder.service.PoiPhotoClient;
import pl.lodz.p.pathfinder.service.TripDownloadService;
import pl.lodz.p.pathfinder.view.TripMenuActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-13.
 */

public class TripMenuPresenterCreated extends TripMenuPresenter
{

    public TripMenuPresenterCreated(TripDownloadService tripDownloadService, TripMenuActivity view, PoiPhotoClient poiPhotoClient, RepresentativePoiStrategy poiStrategy)
    {
        super(tripDownloadService, view, poiPhotoClient ,poiStrategy );
    }

    @Override
    void downloadTrips(String idToken)
    {
        tripDownloadService.loadUserCreated(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setTripList(x), t -> onConnectionFailure(t), () -> returnData());
    }
}
