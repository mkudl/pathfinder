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

//TODO? split into 2 classes
//TODO? make 1st method non-static and provide dependency in constructor
public class TripConverter
{




//    public static Observable<List<Trip>> convertObservableToModel (Observable<List<TripJson>> jsonObservable)
//    {
//
//    }




    public static Trip convertToModel(TripJson tripJson, PointOfInterestClient poiClient)
    {
        List<PointOfInterest> poiList;
        poiList = poiClient.getPoisFromIds(tripJson.getPointOfInterestList());
        Trip result = new Trip(tripJson.getId(),tripJson.getName(),tripJson.getDescription(),poiList);
        return result;
    }



    //TODO? move to different class
    public static TripJsonWrapper convertToJsonWrapper(String idToken,Trip trip)
    {
        return new TripJsonWrapper(idToken,convertToJson(trip));
    }


    //TODO if above cant be made static, keep this, make into utils class(1way conversion only)
    public static TripJson convertToJson(Trip trip)
    {
        List<String> poiIdList = new ArrayList<>();

        for(PointOfInterest p : trip.getPointOfInterestList())
        {
            poiIdList.add(p.getGoogleID());
        }

//        TripJson result = new TripJson(trip.getId(),trip.getName(),trip.getDescription(),poiIdList);
//        return result;
        return new TripJson(trip.getId(),trip.getName(),trip.getDescription(),poiIdList);
    }
}
