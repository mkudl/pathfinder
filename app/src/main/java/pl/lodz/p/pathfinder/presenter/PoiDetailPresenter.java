package pl.lodz.p.pathfinder.presenter;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.service.PoiRepository;
import pl.lodz.p.pathfinder.view.PoiDetailBaseActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-19.
 */

public class PoiDetailPresenter
{

    private boolean isFavorite = false;
    private PointOfInterest displayedPoi;

    PoiDetailBaseActivity view;
    private PoiRepository poiRepository;


    public PoiDetailPresenter(PointOfInterest displayedPoi, PoiDetailBaseActivity view, PoiRepository poiRepository)
    {
        this.displayedPoi = displayedPoi;
        this.view = view;
        this.poiRepository = poiRepository;
    }


    public void init()
    {
        view.showButton();
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        poiRepository.checkFavorite(idToken,displayedPoi.getGoogleID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setFavorite(x.get("isFavorite")),
                            t -> {t.printStackTrace();
                                view.showDBDownloadErrorMessage();},
                            () -> updateButtonListener(isFavorite));
    }

    private void updateButtonListener(boolean favorite)
    {
        if(favorite) {
            view.setButtonListenerUnfavorite();
        }
        else {
            view.setButtonListenerFavorite();
        }
    }



    public void buttonClicked()
    {
        //flip the state first, then update according to the new state
        setFavorite(!isFavorite);
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        if(isFavorite)
        {
            favorite(idToken);
        }
        else
        {
            unfavorite(idToken);
        }
    }

    private void favorite(String idToken)
    {
        poiRepository.addPoiToFavorites(idToken,displayedPoi.getGoogleID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> view.showMessageFavorited(),
                            t -> {t.printStackTrace();
                                  view.showDBUploadErrorMessage();},
                            () -> view.setButtonListenerUnfavorite());
    }

    private void unfavorite(String idToken)
    {
        poiRepository.removePoiFromFavorites(idToken,displayedPoi.getGoogleID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> view.showMessageUnfavorited(),
                            t -> {t.printStackTrace();
                                  view.showDBUploadErrorMessage();},
                            () -> view.setButtonListenerFavorite());
    }







    private void setFavorite(boolean favorite)
    {
        isFavorite = favorite;
    }
}
