package pl.lodz.p.pathfinder;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.List;

/**
 * Created by QDL on 2017-01-14.
 */

public class PolylineUtils
{
    public static List<LatLng> decodePolyline(String encodedPolyline)
    {
//        String reformattedPolyline = StringEscapeUtils.unescapeJava(encodedPolyline);
        String reformattedPolyline = encodedPolyline;
        return PolyUtil.decode(reformattedPolyline);
    }
}
