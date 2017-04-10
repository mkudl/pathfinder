package pl.lodz.p.pathfinder.json.server;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by QDL on 2017-04-08.
 */

public class PointOfInterestJson
{
    private int ID;
    private String googleID;

    public PointOfInterestJson(int ID, String googleID)
    {
        this.ID = ID;
        this.googleID = googleID;
    }

    public PointOfInterestJson()
    {
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getGoogleID()
    {
        return googleID;
    }

    public void setGoogleID(String googleID)
    {
        this.googleID = googleID;
    }
}
