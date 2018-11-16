package multiplemaps.core;

/**
 * Created by Daniel on 2018/11/13.
 */
public interface EngineMarker extends EngineMapObject {

    String getId();

    String getTitle();

    void setTitle(String title);

    void showInfoWindow();

    void remove();

    void setAnchor(float x, float y);

    void setIcon(int iconRes);

    void setPosition(LatLng position);

    LatLng getPosition();

}
