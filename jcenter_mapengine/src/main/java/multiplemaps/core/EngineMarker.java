/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core;

/**
 * Created by Daniel on 2018/11/13.
 */
public interface EngineMarker extends EngineMapObject {

    String getTitle();

    void setTitle(String title);

    void showInfoWindow();

    void remove();

    void setAnchor(float x, float y);

    void setIcon(int iconRes);

    int getIconResId();

    void setPosition(LatLng position);

    LatLng getPosition();

}
