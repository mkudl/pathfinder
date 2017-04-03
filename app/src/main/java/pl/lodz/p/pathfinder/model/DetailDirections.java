package pl.lodz.p.pathfinder.model;

import java.util.List;

import pl.lodz.p.pathfinder.json.directions.Leg;
import pl.lodz.p.pathfinder.json.directions.OverviewPolyline;
import pl.lodz.p.pathfinder.json.directions.Step;

/**
 * Created by QDL on 2017-03-29.
 */

public class DetailDirections
{


    private String distance;
    private Integer distance_actual;

    private String duration;
    private Integer duration_actual;

    private String arrivalTime;


    private String departureTime;


    private List<Step> details;


    private OverviewPolyline overviewPolyline;











    public String getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(String departureTime)
    {
        this.departureTime = departureTime;
    }

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




    public OverviewPolyline getOverviewPolyline()
    {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline)
    {
        this.overviewPolyline = overviewPolyline;
    }



    public List<Step> getDetails()
    {
        return details;
    }

    public void setDetails(List<Step> details)
    {
        this.details = details;
    }
}
