package multiplemaps.core.google;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Circle;

import multiplemaps.core.EngineCircle;
import multiplemaps.core.LatLng;


/**
 * Created by Daniel on 2018/11/13.
 */
public class GoogleCircle implements EngineCircle {

    private Circle circle;

    public GoogleCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public String getId() {
        return circle.getId();
    }

    @Override
    public void remove() {
        circle.remove();
    }

    @Override
    public LatLng getCenter() {
        return new LatLng(circle.getCenter().latitude, circle.getCenter().longitude);
    }

    @Override
    public void setCenter(LatLng var1) {
        com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(var1.latitude, var1.longitude);
        circle.setCenter(latLng);
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
        circle.setStrokeColor(color);
    }

    @Override
    public void setTag(@Nullable Object var1) {
        circle.setTag(var1);
    }

    @Override
    public Object getTag() {
        return circle.getTag();
    }

    @Override
    public void setMapObject(@Nullable Object var1) {
        circle = (Circle) var1;
    }

    @Override
    public Object getMapObject() {
        return circle;
    }
}
