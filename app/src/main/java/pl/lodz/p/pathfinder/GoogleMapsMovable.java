package pl.lodz.p.pathfinder;

import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by QDL on 2017-03-28.
 */

public interface GoogleMapsMovable //extends Parcelable
{
    void moveMapFromPosition(LatLng position);
}
