package pl.lodz.p.pathfinder.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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

    ImageView img;

    GoogleApiClient googleApiClient;



    //FIXME replace with parameter
    String ID = "ChIJt9trB0euEmsR8NbepO14j3M";


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



//        Places.GeoDataApi.getPlaceById(googleApiClient,ID).setResultCallback(this);

        Places.GeoDataApi.getPlacePhotos(googleApiClient,ID).setResultCallback(new ResultCallback<PlacePhotoMetadataResult>()
        {
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
