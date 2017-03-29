package pl.lodz.p.pathfinder.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;

public class TripViewingActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMapsMovable
{

    private Trip trip;
    private SupportMapFragment mapFragment;
//    private PoiListFragment poiFragment;
    private GoogleMap mMap;
//    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(pl.lodz.p.pathfinder.R.layout.trip_viewing_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //retrieve data
        trip = getIntent().getParcelableExtra("TRIP_PARAM");

        //prepare map
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);


        //add a view with a list of POIs
        Fragment poiListFragment = PoiListFragmentUpdateMap.newInstance(trip.getPointOfInterestList());  //FIXME
        //adds fragment as a child of the specified view
        getSupportFragmentManager().beginTransaction().add(R.id.trip_viewing_content_bottom,poiListFragment).commit();


        // set bottom sheet behavior
        View bottomSheet = findViewById(R.id.trip_viewing_content_bottom);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        final ImageView bottomSheetExpandIcon = (ImageView) findViewById(R.id.trip_viewing_content_bottom_img);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {


//                LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
//                ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();

                switch (newState) {
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
//                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        bottomSheetExpandIcon.setRotation(180);
//                        params = mapFragment.getView().getLayoutParams();
//                        params.height =  mapFragment.getView().getHeight() - bottomSheet.getHeight();
//                        mapFragment.getView().setLayoutParams(params);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        bottomSheetExpandIcon.setRotation(0);
//                        params = mapFragment.getView().getLayoutParams();
//                        params.height =  mapFragment.getView().getHeight() + bottomSheet.getHeight();
//                        mapFragment.getView().setLayoutParams(params);
                        break;
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
//                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        PolylineOptions po = new PolylineOptions();
        List<LatLng> positionList = new ArrayList<>();

        for (PointOfInterest p : trip.getPointOfInterestList())
        {
            positionList.add(p.getPosition());
        }

        //FIXME
        LatLngBounds.Builder asd = new LatLngBounds.Builder();
        asd.include(positionList.get(positionList.size()-1));
        asd.include(positionList.get(0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(asd.build(),100));


//         LatLngBounds AUSTRALIA = new LatLngBounds(
//                new LatLng(-44, 113), new LatLng(-10, 154));
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 10));

        po.addAll(positionList);


        po.width(5).color(Color.RED);
        Polyline line = mMap.addPolyline(po);   //TODO unused assignment, consider whether to remove
    }


    @Override
    public void moveMapFromPosition(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }




    void refocusMapSmall()
    {

    }

    void refocusMapBig()
    {

    }

}
