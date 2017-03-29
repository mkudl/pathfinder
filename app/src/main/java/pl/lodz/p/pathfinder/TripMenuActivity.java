package pl.lodz.p.pathfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;

public class TripMenuActivity extends AppCompatActivity
{

    RecyclerView recyclerView;


    List<Trip> newDataSet = new ArrayList<Trip>();


    private void createTrips()
    {
        PointOfInterest poi1 = new PointOfInterest("poi1","poi1",new LatLng(51.74869,19.45537));
        PointOfInterest poi2 = new PointOfInterest("poi2","poi2",new LatLng(51.74893,19.45957));
        PointOfInterest poi3 = new PointOfInterest("poi3","poi3",new LatLng(51.74548,19.46182));
        PointOfInterest poi4 = new PointOfInterest("poi4","poi4",new LatLng(51.74086,19.46393));
        PointOfInterest poi5 = new PointOfInterest("poi4","poi4",new LatLng(51.71092,19.48337));

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
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        createTrips();

        recyclerView = (RecyclerView) findViewById(R.id.main3recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        TripCardRVAdapter adapter = new TripCardRVAdapter(newDataSet);
        recyclerView.setAdapter(adapter);
    }




}
