/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core;

import java.util.List;

/**
 * Created by Daniel on 2018/11/15.
 */
public interface EnginePolygon extends EngineMapObject {

    void setPoints(List<LatLng> points);

    void setFillColor(int color);

    void setStrokeColor(int color);
}
