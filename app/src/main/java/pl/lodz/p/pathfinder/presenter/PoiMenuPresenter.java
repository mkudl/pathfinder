package pl.lodz.p.pathfinder.presenter;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.rest.DatabasePoiRest;
import pl.lodz.p.pathfinder.service.PoiRepository;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import pl.lodz.p.pathfinder.view.PoiMenuActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by QDL on 2017-04-09.
 */

public class PoiMenuPresenter
{

    //NOTE memory leaks prevented by nulling view reference at the end; another possible solution - weakreference

    private PoiMenuActivity view;
    private PointOfInterestClient poiClient;
//    private TripDownloadService tripRepository;
    private Retrofit rxRetrofit;

    private List<PointOfInterest> created;
    private List<PointOfInterest> favorites;


    public PoiMenuPresenter(PoiMenuActivity view, PointOfInterestClient poiClient)
    {
        this.view = view;
        this.poiClient = poiClient;


        this.rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
//                .client(httpClient.build()) //for debugging
                .build();

    }

    public void startActivity()
    {
        view.showSpinner();
        //get data

        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        PoiRepository poiRepository = new PoiRepository(rxRetrofit.create(DatabasePoiRest.class),poiClient);


        poiRepository.loadUserCreatedPois(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setCreated(x), t -> onConnectionFailure(t), () -> returnData());

        poiRepository.loadUserFavoritePois(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( x -> setFavorites(x), t -> onConnectionFailure(t), () -> returnData());

    }

    private synchronized void returnData()
    {
        if(favorites != null && created != null)
        {
            view.onDataRetrieved(created, favorites);
            view.hideSpinner();
            view.showUI();
            Log.d("PoiMenuPresenter","Finished getting data");
            view = null;
        }
    }


    protected void onConnectionFailure(Throwable t)
    {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        t.printStackTrace(pw);
        Log.d("PoiMenuPresenter", " Connection Failure", t);

        view.displayCreationErrorMessage(t);
        view.hideSpinner();
    }

    public void setCreated(List<PointOfInterest> created)
    {
        this.created = created;
    }

    public void setFavorites(List<PointOfInterest> favorites)
    {
        this.favorites = favorites;
    }
}
