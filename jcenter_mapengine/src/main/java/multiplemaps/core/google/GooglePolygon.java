/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core.google;


import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;

import multiplemaps.core.EnginePolygon;
import multiplemaps.core.LatLng;


/**
 * Created by Daniel on 2018/11/15.
 */
public class GooglePolygon implements EnginePolygon {

    private Polygon polygon;

    public GooglePolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public String getId() {
        return polygon.getId();
    }

    @Override
    public void setPoints(List<LatLng> points) {
        ArrayList<com.google.android.gms.maps.model.LatLng> list = new ArrayList<>();
        for (LatLng latLng : points) {
            list.add(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
        }
        polygon.setPoints(list);
    }

    @Override
    public void setFillColor(int color) {
        polygon.setFillColor(color);
    }

    @Override
    public void setStrokeColor(int color) {
        polygon.setStrokeColor(color);
    }

    @Override
    public void setTag(@Nullable Object var1) {
        polygon.setTag(var1);
    }

    @Override
    public Object getTag() {
        return polygon.getTag();
    }

    @Override
    public void setMapObject(@Nullable Object var1) {
        polygon = (Polygon) var1;
    }

    @Override
    public Object getMapObject() {
        return polygon;
    }
}
