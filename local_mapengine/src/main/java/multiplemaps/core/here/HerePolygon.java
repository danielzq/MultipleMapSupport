package multiplemaps.core.here;

import androidx.annotation.Nullable;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPolygon;
import com.here.android.mpa.mapping.MapPolygon;

import java.util.List;

import multiplemaps.core.EnginePolygon;
import multiplemaps.core.LatLng;


/**
 * Created by Daniel on 2018/11/15.
 */
public class HerePolygon implements EnginePolygon {

    private MapPolygon polygon;

    private String id;

    private Object tag;

    public HerePolygon(String id, MapPolygon polygon) {
        this.id = HerePolygon.class.getSimpleName() + id;
        this.polygon = polygon;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setPoints(List<LatLng> points) {
        GeoPolygon geoPolygon = new GeoPolygon();
        for (LatLng latLng : points) {
            geoPolygon.add(new GeoCoordinate(latLng.latitude, latLng.longitude));
        }
        polygon.setGeoPolygon(geoPolygon);
    }

    @Override
    public void setFillColor(int color) {
        polygon.setFillColor(color);
    }

    @Override
    public void setStrokeColor(int color) {
        polygon.setLineColor(color);
    }

    @Override
    public void setTag(@Nullable Object var1) {
        this.tag = var1;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void setMapObject(@Nullable Object var1) {
        polygon = (MapPolygon) var1;
    }

    @Override
    public Object getMapObject() {
        return polygon;
    }
}
