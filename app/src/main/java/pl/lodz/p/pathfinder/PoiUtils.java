package pl.lodz.p.pathfinder;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.view.PhotoDownloadCallback;

/**
 * Created by QDL on 2017-04-03.
 */

public class PoiUtils       //TODO? get poi photo link instead, delegate loading to Picasso in view
{

    public static void loadPoiPhoto(final GoogleApiClient googleApiClient, PointOfInterest displayedPoi, final ImageView display)
    {
        Places.GeoDataApi.getPlacePhotos(googleApiClient,displayedPoi.getGoogleID())
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
                    @Override
                    public void onResult(@NonNull final PlacePhotoMetadataResult placePhotoMetadataResult)
                    {
                        PlacePhotoMetadata photo = placePhotoMetadataResult.getPhotoMetadata().get(1);
                        photo.getPhoto(googleApiClient).setResultCallback(new ResultCallback<PlacePhotoResult>()
                        {
                            @Override
                            public void onResult(@NonNull PlacePhotoResult placePhotoResult)
                            {
                                Bitmap bmp = placePhotoResult.getBitmap();
                                display.setImageBitmap(bmp);
                            }
                        });

                    }
                });
    }




    public static void getPhotosForPoi(final GoogleApiClient googleApiClient, PointOfInterest displayedPoi, final int position, final PhotoDownloadCallback callback)
    {


            Places.GeoDataApi.getPlacePhotos(googleApiClient,displayedPoi.getGoogleID())
                    .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
                        @Override
                        public void onResult(@NonNull final PlacePhotoMetadataResult placePhotoMetadataResult)
                        {
                            PlacePhotoMetadata photo = placePhotoMetadataResult.getPhotoMetadata().get(1);
                            photo.getPhoto(googleApiClient).setResultCallback(new ResultCallback<PlacePhotoResult>()
                            {
                                @Override
                                public void onResult(@NonNull PlacePhotoResult placePhotoResult)
                                {
                                    Bitmap bmp = placePhotoResult.getBitmap();
                                    callback.photoDownloaded(bmp,position);
                                    placePhotoMetadataResult.getPhotoMetadata().release();
                                }
                            });

                        }
                    });



    }
}
