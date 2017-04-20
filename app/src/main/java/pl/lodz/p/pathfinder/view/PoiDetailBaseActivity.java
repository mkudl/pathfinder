package pl.lodz.p.pathfinder.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.PoiUtils;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.presenter.PoiDetailPresenter;
import pl.lodz.p.pathfinder.rest.DatabasePoiRest;
import pl.lodz.p.pathfinder.service.PoiRepository;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoiDetailBaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener//, GoogleApiClient.ConnectionCallbacks
{
    PointOfInterest displayedPoi;

    GoogleApiClient googleApiClient;

    ImageView img;
    TextView name;
    CollapsingToolbarLayout header;
    TextView description;
    FloatingActionButton fab;
    PoiDetailPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_detail_base);
        displayedPoi = getIntent().getParcelableExtra("POI_PARAM");

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
//                .addConnectionCallbacks(this)
//                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();

        img = (ImageView) findViewById(R.id.poi_detail_image);
        description = (TextView) findViewById(R.id.poi_detail_description);
        header = (CollapsingToolbarLayout) findViewById(R.id.poi_detail_toolbar_layout);

        header.setTitle(displayedPoi.getName());


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);



//        Places.GeoDataApi.getPlaceById(googleApiClient,ID).setResultCallback(this);

        //TODO fix, possibly delegate
//        Places.GeoDataApi.getPlacePhotos(googleApiClient,displayedPoi.getGoogleID())
//                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
//            @Override
//            public void onResult(@NonNull final PlacePhotoMetadataResult placePhotoMetadataResult)
//            {
//                PlacePhotoMetadata photo = placePhotoMetadataResult.getPhotoMetadata().get(1);
////                Picasso.with(Main5Activity.this).load("http://i.imgur.com/DvpvklR.png").into(img);
//                photo.getPhoto(googleApiClient).setResultCallback(new ResultCallback<PlacePhotoResult>()
//                {
//                    @Override
//                    public void onResult(@NonNull PlacePhotoResult placePhotoResult)
//                    {
//                        Bitmap bmp = placePhotoResult.getBitmap();
//                        img.setImageBitmap(bmp);
//                    }
//                });
//
//            }
//        });
        //TODO remove
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        //presenter dependencies
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
                .client(httpClient.build()) //for debugging
                .build();
        PointOfInterestClient poiClient = new PointOfInterestClient(this);
        PoiRepository poiRepository = new PoiRepository(rxRetrofit.create(DatabasePoiRest.class),poiClient);

        presenter = new PoiDetailPresenter(displayedPoi,this,poiRepository);
        presenter.init();

        PoiUtils.loadPoiPhoto(googleApiClient,displayedPoi,img);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        //TODO
    }


    public void showButton()
    {
        fab.setVisibility(View.VISIBLE);
    }


    /**
     * Called when the poi is not user's favorite, and the action to be set is to favorite the poi
     */
    public void setButtonListenerFavorite()
    {
        fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        fab.setOnClickListener(view ->
        {
            presenter.buttonClicked();
        });
    }

    /**
     * Called when the poi is user's favorite, and the action to be set is to unfavorite the poi
     */
    public void setButtonListenerUnfavorite()
    {
        fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        fab.setOnClickListener(view ->
        {
            presenter.buttonClicked();
        });
    }


    public void showMessageFavorited()
    {
        Snackbar.make(fab, getString(R.string.poi_favorite_added), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showMessageUnfavorited()
    {
        Snackbar.make(fab, getString(R.string.poi_favorite_removed), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


//    @Override
//    public void onConnected(@Nullable Bundle bundle)
//    {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i)
//    {
//
//    }
}
