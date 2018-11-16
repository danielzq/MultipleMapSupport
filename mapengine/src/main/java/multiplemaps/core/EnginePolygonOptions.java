package multiplemaps.core;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel on 2018/11/15.
 */
public class EnginePolygonOptions {

    private List<LatLng> points;

    private int fillColor = 0;

    private int strokeColor = -16777216;

    private float strokeWidth = 10.0F;

    public EnginePolygonOptions() {
        this.points = new ArrayList<>();
    }

    public final EnginePolygonOptions add(LatLng var1) {
        this.points.add(var1);
        return this;
    }

    public final EnginePolygonOptions add(LatLng... var1) {
        this.points.addAll(Arrays.asList(var1));
        return this;
    }

    public final EnginePolygonOptions addAll(Iterable<LatLng> var1) {
        Iterator var2 = var1.iterator();

        while(var2.hasNext()) {
            LatLng var3 = (LatLng)var2.next();
            this.points.add(var3);
        }

        return this;
    }

    public final List<LatLng> getPoints() {
        return this.points;
    }

    public final EnginePolygonOptions fillColor(int var1) {
        this.fillColor = var1;
        return this;
    }

    public final int getFillColor() {
        return this.fillColor;
    }

    public final EnginePolygonOptions strokeColor(int var1) {
        this.strokeColor = var1;
        return this;
    }

    public final int getStrokeColor() {
        return this.strokeColor;
    }

    public final EnginePolygonOptions strokeWidth(float var1) {
        this.strokeWidth = var1;
        return this;
    }

    public final float getStrokeWidth() {
        return this.strokeWidth;
    }
}
