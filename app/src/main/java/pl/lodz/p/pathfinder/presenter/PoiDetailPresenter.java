package pl.lodz.p.pathfinder.presenter;

import okhttp3.ResponseBody;
import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.service.PoiRepository;
import pl.lodz.p.pathfinder.view.PoiDetailBaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-19.
 */

public class PoiDetailPresenter
{

    private boolean isFavorite = false;
    PointOfInterest displayedPoi;


    PoiDetailBaseActivity view;
    PoiRepository poiRepository;

    public PoiDetailPresenter(PointOfInterest displayedPoi, PoiDetailBaseActivity view, PoiRepository poiRepository)
    {
        this.displayedPoi = displayedPoi;
        this.view = view;
        this.poiRepository = poiRepository;
    }


    public void init()
    {
        view.showButton();

        //TODO call API to check if it is user favorite
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        poiRepository.checkFavorite(idToken,displayedPoi.getGoogleID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setFavorite(x.get("isFavorite")), t -> t.printStackTrace(), () -> updateButtonListener(isFavorite));
        view.setButtonListenerFavorite();
    }

    public void buttonClicked()
    {
//        isFavorite = !isFavorite;
        //flip the state first, then update according to the new state
        setFavorite(!isFavorite);
        updateButtonListener(isFavorite);
        updateServer(isFavorite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> showSuccessMessageFavorite(isFavorite) , t -> t.printStackTrace(), () -> updateButtonListener(isFavorite));
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

    private Observable<ResponseBody> updateServer(boolean favorite)
    {
        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        if(favorite) {
             return poiRepository.addPoiToFavorites(idToken,displayedPoi.getGoogleID());
        }
        else {
            return poiRepository.removePoiFromFavorites(idToken,displayedPoi.getGoogleID());
        }
    }

    private void showSuccessMessageFavorite(boolean favorite)
    {
        if(favorite) {
            view.showMessageFavorited();
        }
        else {
            view.showMessageUnfavorited();
        }
    }




    private void setFavorite(boolean favorite)
    {
        isFavorite = favorite;
    }
}
