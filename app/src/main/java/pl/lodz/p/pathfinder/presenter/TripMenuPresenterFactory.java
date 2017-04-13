package pl.lodz.p.pathfinder.presenter;

import pl.lodz.p.pathfinder.TripMenuType;
import pl.lodz.p.pathfinder.service.TripDownloadService;
import pl.lodz.p.pathfinder.view.TripMenuActivity;

/**
 * Created by QDL on 2017-04-13.
 */

public class TripMenuPresenterFactory
{

    public static TripMenuPresenter createPresenter(TripMenuType type, TripDownloadService tripDownloadService, TripMenuActivity view)
    {
        switch (type)
        {
            case FAVORITES:
                return new TripMenuPresenterFavorites(tripDownloadService,view);
            case CREATED:
                return new TripMenuPresenterCreated(tripDownloadService,view);
            case RECOMMENDED:
            default:
                return new TripMenuPresenterRecommended(tripDownloadService,view);
        }
    }

}
