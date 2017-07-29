package pl.lodz.p.pathfinder.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.PolylineUtils;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.DetailDirections;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.SimpleDirections;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.presenter.TripViewingPresenter;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import pl.lodz.p.pathfinder.service.TripFavoriteChecker;
import pl.lodz.p.pathfinder.service.TripUploadService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripViewingActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMapsMovable
{

    private Trip trip;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    List<Polyline> polylinesDrawn;
    GoogleApiClient googleApiClient;
    private PoiListFragmentDirections poiListFragment;
    private List<DetailDirections> detailDirections;

    FloatingActionButton fabDirections;
    private boolean navigationStarted;
    FloatingActionButton fabFavorite;

    private TripViewingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(pl.lodz.p.pathfinder.R.layout.trip_viewing_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationStarted = false;

        trip = getIntent().getParcelableExtra("TRIP_PARAM");
        setupFloatButton();
        setupPresenter();
        initializeDetailDirections();
        setupNavButton();
        prepareMap();
        //add a view with a list of POIs
        poiListFragment = PoiListFragmentDirections.newInstance(trip.getPointOfInterestList());
        //adds fragment as a child of the specified view
        getSupportFragmentManager().beginTransaction().add(R.id.trip_viewing_content_bottom, poiListFragment).commit();
        setupBottomSheet();
        initGoogleApiClient();
    }

    private void prepareMap()
    {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);
        polylinesDrawn = new ArrayList<>();
    }

    private void setupPresenter()
    {
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
                .build();
        TripFavoriteChecker tripFavoriteChecker = new TripFavoriteChecker(rxRetrofit.create(DatabaseTripRest.class));
        TripUploadService tripUploadService = new TripUploadService(rxRetrofit.create(DatabaseTripRest.class));

        presenter = new TripViewingPresenter(this,trip,tripFavoriteChecker,tripUploadService);
        presenter.getSimpleDirections(trip.getPointOfInterestList());
        presenter.initFavorite();
    }

    private void setupFloatButton()
    {
        fabFavorite = (FloatingActionButton) findViewById(R.id.trip_favorite_fab);
        fabFavorite.setVisibility(View.INVISIBLE);
    }

    private void initializeDetailDirections()
    {
        detailDirections = new ArrayList<>();
        detailDirections.add(null);
        detailDirections.add(null);
        detailDirections.add(null);
        detailDirections.add(null);
        detailDirections.add(null);
    }

    private void setupNavButton()
    {
        fabDirections = (FloatingActionButton) findViewById(R.id.trip_navigate_fab);
        fabDirections.setOnClickListener(view -> presenter.startNavigation(trip.getPointOfInterestList()));
    }

    private void setupBottomSheet()
    {
        View bottomSheet = findViewById(R.id.trip_viewing_content_bottom);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        final ImageView bottomSheetExpandIcon = (ImageView) findViewById(R.id.trip_viewing_content_bottom_img);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
                switch (newState)
                {
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        bottomSheetExpandIcon.setRotation(180);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottomSheetExpandIcon.setRotation(0);
                        break;
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset)
            {
                //intentionally left blank
            }
        });
    }

    private void initGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart()
    {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trip_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.action_refresh:
                actionRefresh();
                return true;
//                break;
            case R.id.action_next:
                actionNext();
                return true;
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        positionList.forEach(latLngBuilder::include);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBuilder.build(), width, height, 200));
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
        po.addAll(positionList);
        po.width(5).color(Color.RED);
        Polyline line = mMap.addPolyline(po);
        polylinesDrawn.add(line);
    }


    @Override
    public void moveMapFromPosition(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }


    private void actionRefresh()
    {
        if (presenter != null && trip != null && navigationStarted)
        {
            presenter.startNavigation(trip.getPointOfInterestList());
        }
    }

    private void actionNext()
    {
        if (navigationStarted && trip.getPointOfInterestList().size() >= 1)
        {
            trip.getPointOfInterestList().remove(0);
            poiListFragment.removeAt(0);
            presenter.startNavigation(trip.getPointOfInterestList());
        } else
            Log.d("TripViewingActivity", "User pressed next, but there was only one poi left, ignoring click");
    }


    //CALLING PRESENTER
    private void getSimpleDirections(List<PointOfInterest> pois)
    {
        presenter.getSimpleDirections(pois);
    }


    //CALLBACKS FOR PRESENTER
    public void updateDirections(SimpleDirections directions, int position)
    {
        poiListFragment.updateDirections(directions, position);
    }

    public void handleDirectionsFail()
    {
        //TODO
    }

    public void showMessageFavorited()
    {
        Toast.makeText(this, getString(R.string.poi_favorite_added), Toast.LENGTH_SHORT).show();
    }

    public void showMessageUnfavorited()
    {
        Toast.makeText(this, getString(R.string.poi_favorite_removed), Toast.LENGTH_SHORT).show();
    }

    public void showDBDownloadErrorMessage()
    {
        Snackbar.make(fabDirections, getString(R.string.error_database_download_generic), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showDBUploadErrorMessage()
    {
        Snackbar.make(fabDirections, getString(R.string.error_database_upload_generic), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    public void setButtonListenerFavorite()
    {
        fabFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        fabFavorite.setOnClickListener(view ->
        {
            presenter.buttonClicked();
        });
    }

    /**
     * Called when the poi is user's favorite, and the action to be set is to unfavorite the poi
     */
    public void setButtonListenerUnfavorite()
    {
        fabFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        fabFavorite.setOnClickListener(view ->
        {
            presenter.buttonClicked();
        });
    }




    public void startNavigation(DetailDirections dirs, int position)
    {
        navigationStarted = true;
        fabDirections.setVisibility(View.GONE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //permissions already checked on launch, no use in checking here
            return;
        }
        //get directions from user's current location to first poi
        android.location.Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null)
        {
            LatLng usrLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
            presenter.loadDirectionsFromLocation(usrLocation,trip.getPointOfInterestList().get(0));

        }
        poiListFragment.updateDetailedDirections(dirs,position);
        detailDirections.set(position,dirs);
    }

    public void hideNavButton()
    {
        fabDirections.setVisibility(View.GONE);
    }

    public void showFavoriteButtion()
    {
        fabFavorite.setVisibility(View.VISIBLE);
    }


    public void clearDrawings()
    {
        polylinesDrawn.forEach(Polyline::remove);
    }


    public void drawDirections(DetailDirections detailDirections)
    {
        PolylineOptions po = new PolylineOptions();
        List<LatLng> positionList = PolylineUtils.decodePolyline(detailDirections.getOverviewPolyline().getPoints());
        po.addAll(positionList);
        po.width(5).color(Color.RED);
        Polyline line = mMap.addPolyline(po);
        polylinesDrawn.add(line);
    }


    public void drawCurrentDirections(DetailDirections currentDirections)
    {
        drawDirections(currentDirections);
    }

}
