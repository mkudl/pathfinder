package pl.lodz.p.pathfinder.view;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-03-28.
 */

public class PoiListFragmentOpenEdit extends PoiListFragment
{

    public static PoiListFragment newInstance(List<PointOfInterest> poiList)
    {
        PoiListFragment fragment = new PoiListFragmentOpenEdit();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,new ArrayList<Parcelable>(poiList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    RvItemClickListener<PointOfInterest> createItemListener()
    {
        return new PoiListOnClickOpenEditMode();
    }
}
