package multiplemaps.core.google;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import multiplemaps.core.EngineMarker;
import multiplemaps.core.LatLng;


/**
 * Created by Daniel on 2018/11/13.
 */
public class GoogleMarker implements EngineMarker {

    private Marker marker;

    public GoogleMarker(@NonNull Marker marker) {
        this.marker = marker;
    }

    @Override
    public String getId() {
        return marker.getId();
    }

    @Override
    public String getTitle() {
        return marker.getTitle();
    }

    @Override
    public void setTitle(String title) {
        marker.setTitle(title);
    }

    @Override
    public void showInfoWindow() {
        marker.showInfoWindow();
    }

    @Override
    public void remove() {
        marker.remove();
    }

    @Override
    public void setAnchor(float x, float y) {
        marker.setAnchor(x, y);
    }

    @Override
    public void setIcon(int iconRes) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(iconRes));
    }

    @Override
    public void setPosition(LatLng position) {
        marker.setPosition(new com.google.android.gms.maps.model.LatLng(position.latitude, position.longitude));
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
    }

    @Override
    public void setTag(@Nullable Object var1) {
        marker.setTag(var1);
    }

    @Override
    public Object getTag() {
        return marker.getTag();
    }

    @Override
    public void setMapObject(@Nullable Object var1) {
        marker = (Marker) var1;
    }

    @Override
    public Object getMapObject() {
        return marker;
    }
}
