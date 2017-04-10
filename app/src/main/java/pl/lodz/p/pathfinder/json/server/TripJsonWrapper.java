package pl.lodz.p.pathfinder.json.server;

import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-04-06.
 */


public class TripJsonWrapper
{
    private String idToken;

    private TripJson trip;





    public TripJsonWrapper(String idToken, TripJson trip)
    {
        this.idToken = idToken;
        this.trip = trip;
    }



    public TripJson getTrip()
    {
        return trip;
    }

    public void setTrip(TripJson trip)
    {
        this.trip = trip;
    }

    public String getIdToken()
    {
        return idToken;
    }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }
}
