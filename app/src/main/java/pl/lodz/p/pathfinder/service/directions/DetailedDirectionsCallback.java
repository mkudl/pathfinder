package pl.lodz.p.pathfinder.service.directions;

import pl.lodz.p.pathfinder.model.DetailDirections;

/**
 * Created by QDL on 2017-04-01.
 */

public interface DetailedDirectionsCallback
{
    void successCallback(DetailDirections directions, int itemPosition);


    //api returns error code
    void apiFailCallback(String statusCode);

    //failure calling the api
    void failCallback(Throwable t);
}
