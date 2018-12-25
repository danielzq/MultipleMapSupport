package multiplemaps.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Created by Daniel on 2018/11/13.
 */
public class EngineMarkerOptions {

    private LatLng position;
    private String title, snippet;
    private float anchorX = 0.5f, anchorY = 0.5f;
    private int iconRes;
    private boolean isFlat;

    public final EngineMarkerOptions position(@NonNull LatLng position) {
        if (position == null) {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        } else {
            this.position = position;
            return this;
        }
    }

    public final EngineMarkerOptions icon(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public final EngineMarkerOptions title(@Nullable String title) {
        this.title = title;
        return this;
    }

    public final EngineMarkerOptions snippet(@Nullable String snippet) {
        this.snippet = snippet;
        return this;
    }

    public final EngineMarkerOptions anchor(float x, float y) {
        this.anchorX = x;
        this.anchorY = y;
        return this;
    }

    public final EngineMarkerOptions flat(boolean enableFlat) {
        isFlat = enableFlat;
        return this;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public float getAnchorX() {
        return anchorX;
    }

    public float getAnchorY() {
        return anchorY;
    }

    public int getIconRes() {
        return iconRes;
    }

    public boolean isFlat() {
        return isFlat;
    }
}
