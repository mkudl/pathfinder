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

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.model.TripFactory;
import pl.lodz.p.pathfinder.presenter.ChooseFirstPoiStrategy;
import pl.lodz.p.pathfinder.presenter.RepresentativePoiStrategy;
import pl.lodz.p.pathfinder.presenter.TripMenuPresenter;
import pl.lodz.p.pathfinder.presenter.TripMenuPresenterFactory;
import pl.lodz.p.pathfinder.presenter.TripMenuType;
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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        spinner = (ProgressBar) findViewById(R.id.trip_menu_spinner) ;
        recyclerView = (RecyclerView) findViewById(R.id.main3recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        setupPresenter();
    }

    private void setupPresenter()
    {
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
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


    public void onDataRetrieved(TripCardRVAdapter rvAdapter)
    {
        adapter = rvAdapter;
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
