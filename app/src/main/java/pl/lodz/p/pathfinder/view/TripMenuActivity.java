package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.TripFactory;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.presenter.TripMenuPresenterFavorites;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import pl.lodz.p.pathfinder.service.TripRepository;

public class TripMenuActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    private ProgressBar spinner;


    List<Trip> newDataSet = new ArrayList<Trip>();
    TripMenuPresenterFavorites presenter;


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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view ->
        {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            Intent intent = new Intent(TripMenuActivity.this, TripAddActivity.class);

            startActivity(intent);
        });

        spinner = (ProgressBar) findViewById(R.id.trip_menu_spinner) ;


        recyclerView = (RecyclerView) findViewById(R.id.main3recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);


        PointOfInterestClient poiClient = new PointOfInterestClient(this);
        TripFactory tripFactory = new TripFactory(poiClient);
        TripRepository tripRepository = new TripRepository(tripFactory);

        presenter = new TripMenuPresenterFavorites(tripRepository,this);
        presenter.startActivity();


//        createTrips();
    }


    public void onDataRetrieved(List<Trip> trips)
    {
        newDataSet = trips;
        TripCardRVAdapter adapter = new TripCardRVAdapter(newDataSet);
        recyclerView.setAdapter(adapter);
    }

    public void showSpinner()
    {
        spinner.setVisibility(View.VISIBLE);
    }

    public void hideSpinner()
    {
        spinner.setVisibility(View.GONE);
    }




}
