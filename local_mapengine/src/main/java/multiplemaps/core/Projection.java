package multiplemaps.core;

import android.graphics.Point;


/**
 * Created by Daniel on 2018/11/14.
 */
public interface Projection {

    Point toScreenLocation(LatLng var1);

    LatLng fromScreenLocation(Point var1);
}
