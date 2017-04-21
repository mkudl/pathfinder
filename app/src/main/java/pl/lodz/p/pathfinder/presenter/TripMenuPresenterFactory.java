package pl.lodz.p.pathfinder.presenter;

import pl.lodz.p.pathfinder.RepresentativePoiStrategy;
import pl.lodz.p.pathfinder.TripMenuType;
import pl.lodz.p.pathfinder.service.PoiPhotoClient;
import pl.lodz.p.pathfinder.service.TripDownloadService;
import pl.lodz.p.pathfinder.view.TripMenuActivity;

/**
 * Created by QDL on 2017-04-13.
 */

public class TripMenuPresenterFactory
{

    public static TripMenuPresenter createPresenter(TripMenuType type,
                                                    TripDownloadService tripDownloadService,
                                                    TripMenuActivity view,
                                                    PoiPhotoClient poiPhotoClient,
                                                    RepresentativePoiStrategy poiStrategy)
    {
        switch (type)
        {
            case FAVORITES:
                return new TripMenuPresenterFavorites(tripDownloadService,view,poiPhotoClient,poiStrategy);
            case CREATED:
                return new TripMenuPresenterCreated(tripDownloadService,view,poiPhotoClient,poiStrategy);
            case RECOMMENDED:
            default:
                return new TripMenuPresenterRecommended(tripDownloadService,view,poiPhotoClient,poiStrategy);
        }
    }

}
