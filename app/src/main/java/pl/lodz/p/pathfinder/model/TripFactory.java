package pl.lodz.p.pathfinder.model;

import java.util.List;

import pl.lodz.p.pathfinder.json.server.TripJson;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;

/**
 * Created by QDL on 2017-04-13.
 */

public class TripFactory
{
    private PointOfInterestClient poiClient;


    public TripFactory(PointOfInterestClient poiClient)
    {
        this.poiClient = poiClient;
    }



    public Trip convertToModel(TripJson tripJson)
    {
        List<PointOfInterest> poiList;
        poiList = poiClient.getPoisFromIds(tripJson.getPointOfInterestList());
        Trip result = new Trip(tripJson.getId(),tripJson.getName(),tripJson.getDescription(),poiList);
        return result;
    }


}
