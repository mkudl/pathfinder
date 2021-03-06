package pl.lodz.p.pathfinder.service;

import java.util.List;

import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.model.TripFactory;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import rx.Observable;


public class TripDownloadService
{

    private DatabaseTripRest restClient;

    private TripFactory tripFactory;


    public TripDownloadService(TripFactory tripFactory, DatabaseTripRest databaseTripRest)
    {
        this.restClient = databaseTripRest;
        this.tripFactory = tripFactory;
    }


    /**
     * get list of trips from the server, convert from json representation to model, then return the whole list
     * part of the conversion process involves a call to the Google Places API, in order to retrieve
     * details of each POI
     * @return  List of pois created by the currently logged in user
     */
    public Observable<List<Trip>> loadUserCreated(String idToken)
    {
//        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadUserCreated(idToken)
                .flatMap( Observable::from)
                .map( tj -> tripFactory.convertToModel(tj) )
                .toList();
    }

    public Observable<List<Trip>> loadUserFavorites(String idToken)
    {
//        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadUserFavorites(idToken)
                .flatMap( Observable::from)
                .map( tj -> tripFactory.convertToModel(tj) )
                .toList();
    }

    public Observable<List<Trip>> loadRecommended(String idToken)
    {
//        String idToken = AccountSingleton.INSTANCE.getAccount().getIdToken();
        return restClient.loadRecommended(idToken)
                .flatMap( Observable::from)
                .map( tj -> tripFactory.convertToModel(tj) )
                .toList();
    }



}
