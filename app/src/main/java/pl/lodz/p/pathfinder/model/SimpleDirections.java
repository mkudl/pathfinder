package pl.lodz.p.pathfinder.model;

/**
 * Created by QDL on 2017-03-29.
 */

/**
 * This class represents the relevant data in a response from the Distance Matrix API
 * Since the API returns a formatted String in addition to the precise value, they are both represented
 */
public class SimpleDirections
{
   //TODO? package distance and duration into objects

    private String distance;
    private Integer distance_actual;

    private String duration;
    private Integer duration_actual;

    private DirectionType directionType;






    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public Integer getDistance_actual()
    {
        return distance_actual;
    }

    public void setDistance_actual(Integer distance_actual)
    {
        this.distance_actual = distance_actual;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public Integer getDuration_actual()
    {
        return duration_actual;
    }

    public void setDuration_actual(Integer duration_actual)
    {
        this.duration_actual = duration_actual;
    }

    public DirectionType getDirectionType()
    {
        return directionType;
    }

    public void setDirectionType(DirectionType directionType)
    {
        this.directionType = directionType;
    }


}
