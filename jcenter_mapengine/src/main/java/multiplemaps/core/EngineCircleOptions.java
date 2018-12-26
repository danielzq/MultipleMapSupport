package multiplemaps.core;

/**
 * Created by Daniel on 2018/11/13.
 */
public class EngineCircleOptions {

    private boolean clickable = false;
    private LatLng center;
    private double radius;
    private float strokeWidth;
    private int strokeColor;
    private int fillColor = 0;

    public final EngineCircleOptions center(LatLng var1) {
        this.center = var1;
        return this;
    }

    public final EngineCircleOptions radius(double var1) {
        this.radius = var1;
        return this;
    }

    public final EngineCircleOptions strokeWidth(float var1) {
        this.strokeWidth = var1;
        return this;
    }

    public final EngineCircleOptions strokeColor(int var1) {
        this.strokeColor = var1;
        return this;
    }

    public final EngineCircleOptions fillColor(int var1) {
        this.fillColor = var1;
        return this;
    }

    public final EngineCircleOptions clickable(boolean var1) {
        this.clickable = var1;
        return this;
    }

    public LatLng getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getFillColor() {
        return fillColor;
    }

    public boolean isClickable() {
        return clickable;
    }
}
