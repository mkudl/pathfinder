package pl.lodz.p.pathfinder.json.server;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-04-06.
 */

public class PointOfInterestJsonWrapper
{
    private String idToken;

    private PointOfInterest poi;





    public PointOfInterestJsonWrapper(String idToken, PointOfInterest poi)
    {
        this.idToken = idToken;
        this.poi = poi;
    }



    public PointOfInterest getPoi()
    {
        return poi;
    }

    public void setPoi(PointOfInterest poi)
    {
        this.poi = poi;
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
