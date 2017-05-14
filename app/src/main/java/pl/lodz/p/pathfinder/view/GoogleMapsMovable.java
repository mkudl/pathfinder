package pl.lodz.p.pathfinder.view;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by QDL on 2017-03-28.
 */

public interface GoogleMapsMovable //extends Parcelable
{
    void moveMapFromPosition(LatLng position);
}
