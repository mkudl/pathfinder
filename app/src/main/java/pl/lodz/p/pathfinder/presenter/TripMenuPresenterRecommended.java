package pl.lodz.p.pathfinder.presenter;

import pl.lodz.p.pathfinder.service.TripDownloadService;
import pl.lodz.p.pathfinder.view.TripMenuActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-13.
 */

public class TripMenuPresenterRecommended extends TripMenuPresenter
{

    public TripMenuPresenterRecommended(TripDownloadService tripDownloadService, TripMenuActivity view)
    {
        super(tripDownloadService, view);
    }

    @Override
    void downloadTrips(String idToken)
    {
        tripDownloadService.loadRecommended(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setTripList(x), t -> onConnectionFailure(t), () -> returnData());
    }
}
