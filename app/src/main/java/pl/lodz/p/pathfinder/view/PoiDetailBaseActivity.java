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

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;

public class PoiDetailBaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
{
    PointOfInterest displayedPoi;

    GoogleApiClient googleApiClient;

    ImageView img;
    TextView name;
    CollapsingToolbarLayout header;
    TextView description;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_detail_base);
        displayedPoi = getIntent().getParcelableExtra("POI_PARAM");

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
//                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();

        img = (ImageView) findViewById(R.id.poi_detail_image);
        description = (TextView) findViewById(R.id.poi_detail_description);
        header = (CollapsingToolbarLayout) findViewById(R.id.poi_detail_toolbar_layout);

        header.setTitle(displayedPoi.getName());
        description.setText(displayedPoi.getDescription());


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



//        Places.GeoDataApi.getPlaceById(googleApiClient,ID).setResultCallback(this);

        //TODO fix, possibly delegate
        Places.GeoDataApi.getPlacePhotos(googleApiClient,displayedPoi.getGoogleID())
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
            @Override
            public void onResult(@NonNull final PlacePhotoMetadataResult placePhotoMetadataResult)
            {
                PlacePhotoMetadata photo = placePhotoMetadataResult.getPhotoMetadata().get(1);
//                Picasso.with(Main5Activity.this).load("http://i.imgur.com/DvpvklR.png").into(img);
                photo.getPhoto(googleApiClient).setResultCallback(new ResultCallback<PlacePhotoResult>()
                {
                    @Override
                    public void onResult(@NonNull PlacePhotoResult placePhotoResult)
                    {
                        Bitmap bmp = placePhotoResult.getBitmap();
                        img.setImageBitmap(bmp);
                    }
                });

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        //TODO
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }
}
