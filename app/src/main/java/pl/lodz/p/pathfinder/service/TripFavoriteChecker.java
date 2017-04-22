package pl.lodz.p.pathfinder.service;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import rx.Observable;

/**
 * Created by QDL on 2017-04-22.
 */

public class TripFavoriteChecker
{

    private DatabaseTripRest restClient;



    public TripFavoriteChecker( DatabaseTripRest databaseTripRest)
    {
        this.restClient = databaseTripRest;
    }

    public Observable<Boolean> checkFavorite(String idToken, Trip trip)
    {
        return restClient.checkFavorite(idToken,trip.getId())
                .map( m -> m.get("isFavorite"));
    }

}
