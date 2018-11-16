package multiplemaps.core.google;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Polygon;

import multiplemaps.core.EnginePolygon;

/**
 * Created by Daniel on 2018/11/15.
 */
public class GooglePolygon implements EnginePolygon {

    private Polygon polygon;

    public GooglePolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public String getId() {
        return polygon.getId();
    }

    @Override
    public void setFillColor(int color) {
        polygon.setFillColor(color);
    }

    @Override
    public void setStrokeColor(int color) {
        polygon.setStrokeColor(color);
    }

    @Override
    public void setTag(@Nullable Object var1) {
        polygon.setTag(var1);
    }

    @Override
    public Object getTag() {
        return polygon.getTag();
    }

    @Override
    public void setMapObject(@Nullable Object var1) {
        polygon = (Polygon) var1;
    }

    @Override
    public Object getMapObject() {
        return polygon;
    }
}
