package pl.lodz.p.pathfinder.service;

import android.graphics.Bitmap;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

/**
 * Created by QDL on 2017-04-21.
 */

//NOTE ultimately this should replace PoiUtils
public class PoiPhotoClient
{
    private final static int PHOTO_TO_GET=0;    //TODO consider other methods of picking the appropriate photo

    private GoogleApiClient googleApiClient;

    public PoiPhotoClient(GoogleApiClient googleApiClient)
    {
        this.googleApiClient = googleApiClient;
    }

    public Bitmap getPhoto(String poiID)
    {
        PlacePhotoMetadataBuffer buffer = Places.GeoDataApi.getPlacePhotos(googleApiClient,poiID).await().getPhotoMetadata();
        PlacePhotoMetadata photo = buffer.get(0);
        PlacePhotoResult placePhotoResult = photo.getPhoto(googleApiClient).await();
        Bitmap bmp = placePhotoResult.getBitmap();
        buffer.release();
        return bmp;
    }
}
