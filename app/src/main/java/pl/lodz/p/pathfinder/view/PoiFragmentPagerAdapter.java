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
//    private String[] tabTitles = {"my trips","Browse"}; //FIXME
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
//    {
//        Fragment f= null;
//        switch (position){
//            case 0:
//                //pois created by user tab
//                f =  PoiListFragmentOpenEdit.newInstance(created);
//            break;
//            case 1:
//                //pois favorited by user tab
//                f =  PoiListFragmentOpenEdit.newInstance(favorites);
//            break;
//            case 2:
//                //poi search tab
//                f =  PoiSearchFragment.newInstance("DISPLAY_DETAILS");
//                break;
//
//        }
//        return f;
//    }

    //TODO delete this, get real data
//    List<PointOfInterest> testData()
//    {
//        PointOfInterest poi1 = new PointOfInterest("poi1","poi1",new LatLng(51.74869,19.45537), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi2 = new PointOfInterest("poi2",context.getString(R.string.large_text),new LatLng(51.74893,19.45957), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi3 = new PointOfInterest("poi3","poi3",new LatLng(51.74548,19.46182), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi4 = new PointOfInterest("poi4","poi4",new LatLng(51.74086,19.46393), "ChIJt9trB0euEmsR8NbepO14j3M");
//        PointOfInterest poi5 = new PointOfInterest("poi4",context.getString(R.string.large_text),new LatLng(51.71092,19.48337), "ChIJt9trB0euEmsR8NbepO14j3M");
//
//        ArrayList<PointOfInterest> poiList = new ArrayList<>();
//        poiList.add(poi1);
//        poiList.add(poi2);
//        poiList.add(poi3);
//        poiList.add(poi4);
//        poiList.add(poi5);
//        return poiList;
//    }
}

