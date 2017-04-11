package pl.lodz.p.pathfinder.presenter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.view.TripAddActivity;

/**
 * Created by QDL on 2017-04-11.
 */

public class TripAddPresenter
{

    TripAddActivity view;
    List<PointOfInterest> poiList = new ArrayList<>();

    public TripAddPresenter(TripAddActivity view)
    {
        this.view = view;
    }

    public void addPoi(PointOfInterest poi)
    {
        poiList.add(poi);
        view.poiPickCallback(poi);
    }

    public void removePoi(PointOfInterest poi,View child)
    {
        poiList.remove(poi);
        view.removeSimplePoiChildView(child);
    }
}
