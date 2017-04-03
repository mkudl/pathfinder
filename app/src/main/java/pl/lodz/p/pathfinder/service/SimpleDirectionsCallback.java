package pl.lodz.p.pathfinder.service;

import pl.lodz.p.pathfinder.model.SimpleDirections;

/**
 * Created by QDL on 2017-03-29.
 */

public interface SimpleDirectionsCallback
{
    //TODO? change method types to void
    void successCallback(SimpleDirections directions, int itemPosition);

    //failure calling the api
    void failCallback(Throwable t);
    //returned http fail code
    void httpCodeFailCallback( int code);

    /**
     * Top-level Status Codes
     *
     *OK indicates the response contains a valid result.
     *INVALID_REQUEST indicates that the provided request was invalid.
     *MAX_ELEMENTS_EXCEEDED indicates that the product of origins and destinations exceeds the per-query limit.
     *OVER_QUERY_LIMIT indicates the service has received too many requests from your application within the allowed time period.
     *REQUEST_DENIED indicates that the service denied use of the Distance Matrix service by your application.
     *UNKNOWN_ERROR indicates a Distance Matrix request could not be processed due to a server error. The request may succeed if you try again.
     *
     *
     *Element-level Status Codes
     *
     *OK indicates the response contains a valid result.
     *NOT_FOUND indicates that the origin and/or destination of this pairing could not be geocoded.
     *MAX_ROUTE_LENGTH_EXCEEDED indicates the requested route is too long and cannot be processed.
     *
     *
     *
     *ZERO_RESULTS IS HANDLED INSIDE THE CLIENT
     */
    //api returned error status inside response (top-level)
    void apiFailTopLevelCallback(String code);
    //api returned error status inside response (element-level)
    void apiFailElementCallback(String code);
}
