package pl.lodz.p.pathfinder.service;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-04-03.
 */

public interface PoiCreatedCallback
{
    void poiInformationRetrieved(PointOfInterest poi);
}
