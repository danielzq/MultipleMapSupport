package multiplemaps.core.google;

import android.graphics.Point;

import com.google.android.gms.maps.GoogleMap;

import multiplemaps.core.LatLng;
import multiplemaps.core.Projection;


/**
 * Created by Daniel on 2018/11/14.
 */
public class GoogleProjection implements Projection {

    private com.google.android.gms.maps.Projection projection;

    public GoogleProjection(GoogleMap map) {
        projection = map.getProjection();
    }

    @Override
    public Point toScreenLocation(LatLng var1) {
        return projection.toScreenLocation(new com.google.android.gms.maps.model.LatLng(var1.latitude, var1.longitude));
    }

    @Override
    public LatLng fromScreenLocation(Point var1) {
        com.google.android.gms.maps.model.LatLng latLng = projection.fromScreenLocation(var1);
        return new LatLng(latLng.latitude, latLng.longitude);
    }
}
