package multiplemaps.core;

/**
 * Created by Daniel on 2018/11/13.
 */
public interface EngineCircle extends EngineMapObject {

    String getId();

    void remove();

    void setCenter(LatLng var1);

    void setRadius(double r);

    void setFillColor(int color);

    void setStrokeColor(int color);


}
