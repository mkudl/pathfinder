package pl.lodz.p.pathfinder.view;

import android.os.Parcel;
import android.view.View;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-03-27.
 */

public class PoiListOnClickUpdateMap implements RvItemClickListener<PointOfInterest>
{

    private GoogleMapsMovable map;

    public PoiListOnClickUpdateMap(GoogleMapsMovable map)
    {
        this.map = map;
    }

    @Override
    public void onItemClicked(PointOfInterest item, View view)
    {
        map.moveMapFromPosition(item.getPosition());
    }


    //following methods belong to the Parcelable interface, which is included solely for the sake of being able to pass this listener to a Fragment
    //seeing as this class is stateless i did not see it necessary to actually implement it
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }
}
