package pl.lodz.p.pathfinder;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.json.server.TripJson;
import pl.lodz.p.pathfinder.json.server.TripJsonWrapper;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import rx.Observable;

/**
 * Created by QDL on 2017-04-08.
 */


public class TripJsonWrapperFactory
{

    public static TripJsonWrapper convertToJsonWrapper(String idToken,Trip trip)
    {
        return new TripJsonWrapper(idToken,convertToJson(trip));
    }


    private static TripJson convertToJson(Trip trip)
    {
        List<String> poiIdList = new ArrayList<>();

        for(PointOfInterest p : trip.getPointOfInterestList())
        {
            poiIdList.add(p.getGoogleID());
        }

        return new TripJson(trip.getId(),trip.getName(),trip.getDescription(),poiIdList);
    }
}
