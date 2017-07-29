package pl.lodz.p.pathfinder.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-03-28.
 */

public class PoiListFragmentUpdateMap extends PoiListFragment
{



    private GoogleMapsMovable mapsMovable;

    public static PoiListFragment newInstance(List<PointOfInterest> poiList)
    {
        PoiListFragmentUpdateMap fragment = new PoiListFragmentUpdateMap();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,new ArrayList<Parcelable>(poiList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    RvItemClickListener<PointOfInterest> createItemListener()
    {
        return new PoiListOnClickUpdateMap(mapsMovable);
    }



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof GoogleMapsMovable)
        {
            mapsMovable = (GoogleMapsMovable) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement GoogleMapsMovable");
        }
    }











    public GoogleMapsMovable getMapsMovable()
    {
        return mapsMovable;
    }

    public void setMapsMovable(GoogleMapsMovable mapsMovable)
    {
        this.mapsMovable = mapsMovable;
    }
}
