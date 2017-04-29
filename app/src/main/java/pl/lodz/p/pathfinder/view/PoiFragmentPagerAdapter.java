package pl.lodz.p.pathfinder.view;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;


/**
 * Created by QDL on 2017-01-17.
 */

public abstract class PoiFragmentPagerAdapter extends FragmentPagerAdapter
{

    private final static int PAGE_COUNT = 3;
    private String[] tabTitles;
    private Context context;

    List<PointOfInterest> created;
    List<PointOfInterest> favorites;

    public PoiFragmentPagerAdapter(FragmentManager fm, Context context, List<PointOfInterest> created, List<PointOfInterest> favorites)
    {
        super(fm);
        this.context = context;
        this.tabTitles = context.getResources().getStringArray(R.array.poi_menu_tabs);
        this.created = created;
        this.favorites = favorites;

    }


    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }



    @Override
    public abstract Fragment getItem(int position);
}

