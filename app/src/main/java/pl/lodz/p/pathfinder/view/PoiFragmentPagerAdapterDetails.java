package pl.lodz.p.pathfinder.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-04-11.
 */

public class PoiFragmentPagerAdapterDetails extends PoiFragmentPagerAdapter
{

    public PoiFragmentPagerAdapterDetails(FragmentManager fm, Context context, List<PointOfInterest> created, List<PointOfInterest> favorites)
    {
        super(fm, context, created, favorites);
    }




    @Override
    public Fragment getItem(int position) {
        Fragment f= null;
        switch (position){
            case 0:
                //pois created by user tab
                f =  PoiListFragmentOpenEdit.newInstance(created);
                break;
            case 1:
                //pois favorited by user tab
                f =  PoiListFragmentOpenEdit.newInstance(favorites);
                break;
            case 2:
                //poi search tab
                f =  PoiSearchFragment.newInstance("DISPLAY_DETAILS");
                break;

        }
        return f;
    }


}
