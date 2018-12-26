package multiplemaps.core.here;

import android.graphics.PointF;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;

import java.io.IOException;

import multiplemaps.core.EngineMarker;
import multiplemaps.core.LatLng;


/**
 * Created by Daniel on 2018/11/13.
 */
public class HereMarker implements EngineMarker {

    private MapMarker marker;

    private Map map;

    private Object tag;

    private String id;

    private float x = 0.5f, y = 0.5f;

    public HereMarker(@NonNull String id, @NonNull MapMarker marker, @NonNull Map map) {
        this.marker = marker;
        this.map = map;
        this.id = HereMarker.class.getSimpleName() + id;
    }

    public MapMarker getMarker() {
        return marker;
    }

    @Override
    public String getId() {
        return id;
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
        //marker.bubble
    }

    @Override
    public void remove() {
        map.removeMapObject(marker);
    }

    @Override
    public void setAnchor(@FloatRange(from = 0f, to = 1f) float x, @FloatRange(from = 0f, to = 1f) float y) {
        this.x = x;
        this.y = y;
        Image image = marker.getIcon();
        if (image != null) {
            PointF pointF = new PointF(image.getWidth() * x, image.getHeight() * y);
            marker.setAnchorPoint(pointF);
        }
    }

    @Override
    public void setIcon(int iconRes) {
        Image image = new Image();
        try {
            image.setImageResource(iconRes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        marker.setIcon(image);
        setAnchor(x, y);
    }

    @Override
    public void setPosition(LatLng position) {
        marker.setCoordinate(new GeoCoordinate(position.latitude, position.longitude));
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(marker.getCoordinate().getLatitude(), marker.getCoordinate().getLongitude());
    }

    @Override
    public void setTag(@Nullable Object var1) {
        this.tag = var1;
    }

    @Override
    public Object getTag() {
        return this.tag;
    }

    @Override
    public void setMapObject(@Nullable Object var1) {
        marker = (MapMarker) var1;
    }

    @Override
    public Object getMapObject() {
        return marker;
    }
}
