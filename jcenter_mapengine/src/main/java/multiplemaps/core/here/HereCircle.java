package multiplemaps.core.here;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapCircle;

import java.nio.charset.StandardCharsets;

import multiplemaps.core.EngineCircle;
import multiplemaps.core.LatLng;


/**
 * Created by Daniel on 2018/11/13.
 */
public class HereCircle implements EngineCircle {

    private MapCircle circle;

    private Map map;

    private String id;

    private Object tag;

    public HereCircle(String id, MapCircle circle, Map map) {
        byte[] data = new byte[0];
        data = (HereCircle.class.getSimpleName() + id).getBytes(StandardCharsets.UTF_8);
        this.id = Base64.encodeToString(data, Base64.NO_PADDING);
        this.circle = circle;
        this.map = map;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void remove() {
        map.removeMapObject(circle);
    }

    @Override
    public LatLng getCenter() {
        return new LatLng(circle.getCenter().getLatitude(), circle.getCenter().getLongitude());
    }

    @Override
    public void setCenter(LatLng var1) {
        circle.setCenter(new GeoCoordinate(var1.latitude, var1.longitude));
    }

    @Override
    public void setRadius(double r) {
        circle.setRadius(r);
    }

    @Override
    public void setFillColor(int color) {
        circle.setFillColor(color);
    }

    @Override
    public void setStrokeColor(int color) {
        circle.setLineColor(color);
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
        circle = (MapCircle) var1;
    }

    @Override
    public Object getMapObject() {
        return circle;
    }
}
