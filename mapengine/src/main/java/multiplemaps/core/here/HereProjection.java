package multiplemaps.core.here;

import android.graphics.Point;
import android.graphics.PointF;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;

import multiplemaps.core.LatLng;
import multiplemaps.core.Projection;

/**
 * Created by Daniel on 2018/11/14.
 */
public class HereProjection implements Projection {

    private Map map;

    public HereProjection(Map map) {
        this.map = map;
    }

    @Override
    public Point toScreenLocation(LatLng var1) {
        Map.PixelResult pointResult = map.projectToPixel(new GeoCoordinate(var1.latitude, var1.longitude));
        return new Point((int)pointResult.getResult().x, (int)pointResult.getResult().y);
    }

    @Override
    public LatLng fromScreenLocation(Point var1) {
        GeoCoordinate geoCoordinate = map.pixelToGeo(new PointF(var1.x, var1.y));
        return new LatLng(geoCoordinate.getLatitude(), geoCoordinate.getLongitude());
    }
}
