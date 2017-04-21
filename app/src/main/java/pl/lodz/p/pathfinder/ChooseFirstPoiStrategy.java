package pl.lodz.p.pathfinder;

import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-04-21.
 */

public class ChooseFirstPoiStrategy implements RepresentativePoiStrategy
{
    @Override
    public PointOfInterest pickRepresentativePoi(Trip trip)
    {
        return trip.getPointOfInterestList().get(0);
    }
}
