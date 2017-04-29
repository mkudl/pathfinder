package pl.lodz.p.pathfinder.json.server;

import java.util.List;

/**
 * Created by QDL on 2017-04-08.
 */


public class TripJson
{

    private int id;
    private String name;
    private String description;
//    private List<PointOfInterestJson> pointOfInterestList;
    private List<String> pointOfInterestList;   //list of pois' google ids












    public TripJson(int id, String name, String description, List<String> pointOfInterestList)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pointOfInterestList = pointOfInterestList;
    }

    public TripJson()
    {

    }




    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getPointOfInterestList()
    {
        return pointOfInterestList;
    }

    public void setPointOfInterestList(List<String> pointOfInterestList)
    {
        this.pointOfInterestList = pointOfInterestList;
    }
}
