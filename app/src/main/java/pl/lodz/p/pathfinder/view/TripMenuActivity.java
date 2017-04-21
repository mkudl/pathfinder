package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.ChooseFirstPoiStrategy;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.RepresentativePoiStrategy;
import pl.lodz.p.pathfinder.TripFactory;
import pl.lodz.p.pathfinder.TripMenuType;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.presenter.TripMenuPresenter;
import pl.lodz.p.pathfinder.presenter.TripMenuPresenterFactory;
import pl.lodz.p.pathfinder.presenter.TripMenuPresenterFavorites;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import pl.lodz.p.pathfinder.service.PoiPhotoClient;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import pl.lodz.p.pathfinder.service.TripDownloadService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripMenuActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{

    RecyclerView recyclerView;
    private ProgressBar spinner;
    private FloatingActionButton fab;

    List<Trip> newDataSet = new ArrayList<Trip>();
    TripMenuPresenter presenter;

    TripCardRVAdapter adapter;


    //FIXME
    private void createTrips()
    {
//        PointOfInterest poi1 = new PointOfInterest("poi1","poi1",new LatLng(51.74869,19.45537), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi2 = new PointOfInterest("poi2","poi2",new LatLng(51.74893,19.45957), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi3 = new PointOfInterest("poi3","poi3",new LatLng(51.74548,19.46182), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi4 = new PointOfInterest("poi4","poi4",new LatLng(51.74086,19.46393), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi5 = new PointOfInterest("poi4","poi4",new LatLng(51.71092,19.48337), "ChIJt9trB0euEmsR8NbepO14j3M");
        PointOfInterest poi1 = new PointOfInterest("poi1",new LatLng(51.74869,19.45537), "ChIJt9trB0euEmsR8NbepO14j3M");
        PointOfInterest poi2 = new PointOfInterest("poi2",new LatLng(51.74893,19.45957), "ChIJt9trB0euEmsR8NbepO14j3M");
        PointOfInterest poi3 = new PointOfInterest("poi3",new LatLng(51.74548,19.46182), "ChIJt9trB0euEmsR8NbepO14j3M");
        PointOfInterest poi4 = new PointOfInterest("poi4",new LatLng(51.74086,19.46393), "ChIJt9trB0euEmsR8NbepO14j3M");
        PointOfInterest poi5 = new PointOfInterest("poi4",new LatLng(51.71092,19.48337), "ChIJt9trB0euEmsR8NbepO14j3M");

        ArrayList<PointOfInterest> poiList = new ArrayList<>();
        poiList.add(poi1);
        poiList.add(poi2);
        poiList.add(poi3);
        poiList.add(poi4);
        poiList.add(poi5);


        Trip trip1 = new Trip("Best Trip","Best trip in the entire world",poiList);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
        newDataSet.add(trip1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //TODO split cases of new creation and recreating from savedInstanceState
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        spinner = (ProgressBar) findViewById(R.id.trip_menu_spinner) ;
        recyclerView = (RecyclerView) findViewById(R.id.main3recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);


        //creating presenter dependencies
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
//            .client(httpClient.build()) //for debugging
                .build();
        DatabaseTripRest restClient = rxRetrofit.create(DatabaseTripRest.class);
        PointOfInterestClient poiClient = new PointOfInterestClient(this);
        TripFactory tripFactory = new TripFactory(poiClient);
        TripDownloadService tripDownloadService = new TripDownloadService(tripFactory, restClient);
        RepresentativePoiStrategy poiStrategy = new ChooseFirstPoiStrategy();
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();
        PoiPhotoClient poiPhotoClient = new PoiPhotoClient(googleApiClient);
        TripMenuType type = (TripMenuType) getIntent().getSerializableExtra("TRIPMENU_TYPE");
        presenter = TripMenuPresenterFactory.createPresenter(type,tripDownloadService,this,poiPhotoClient,poiStrategy);

        presenter.startActivity();
    }


    public void onDataRetrieved(List<Trip> trips)
    {
        newDataSet = trips;
        adapter = new TripCardRVAdapter(newDataSet,this);
        recyclerView.setAdapter(adapter);

        //only set listener after data successfully retrieved
        //user shouldn't try to add anything if there is no connection anyway
        fab.setOnClickListener(view ->
        {
            Intent intent = new Intent(TripMenuActivity.this, TripAddActivity.class);
            startActivity(intent);
        });
    }

    public void displayCreationErrorMessage(Throwable t)
    {
        Snackbar.make( spinner,
                getResources().getString(R.string.error_connection_generic),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showSpinner()
    {
        spinner.setVisibility(View.VISIBLE);
    }

    public void hideSpinner()
    {
        spinner.setVisibility(View.GONE);
    }

    public void updatePhotos(List<Bitmap> photos)
    {
        adapter.updatePhotos(photos);
    }





    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
}
