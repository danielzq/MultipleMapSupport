/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core;

import android.view.View;

/**
 * Created by Daniel on 2018/11/13.
 */
public interface EngineMap {

    void clear();

    void stopAnimation();

    void setOnCameraMoveListener(OnCameraMoveListener listener);

    void setOnCameraIdleListener(OnCameraIdleListener listener);

    void setOnCameraMoveStartedListener(OnCameraMoveStartedListener listener);

    void setOnCameraMoveCanceledListener(OnCameraMoveCanceledListener listener);

    void setOnMapLoadedCallback(OnMapLoadedCallback listener);

    void setZoomControlsEnabled(boolean enabled);

    void setCompassEnabled(boolean enabled);

    void setRotateGesturesEnabled(boolean enabled);

    void setAllGesturesEnabled(boolean enabled);

    void setMyLocationButtonEnabled(boolean enabled);

    void setMyLocationEnabled(boolean enabled);

    void setIndoorLevelPickerEnabled(boolean enabled);

    void setOnMarkerClickListener(OnMarkerClickListener listener);

    void setOnMapClickListener(OnMapClickListener listener);

    void setInfoWindowAdapter(InfoWindowAdapter infoWindowAdapter);

    void setPadding(int left, int top, int right, int bottom);

    CameraPosition getCameraPosition();

    Projection getProjection();

    void moveCamera(LatLng position, float zoomLevel);

    void animateCamera(LatLng position, float zoomLevel);

    void animateCamera(LatLng position, CancelableCallback callback);

    void animateCamera(LatLngBounds position, int padding);

    void animateCameraOffsetY(float offsetY);

    void animateCamera(LatLng position, float zoomLevel, CancelableCallback callback);

    void animateCamera(LatLngBounds latLngBounds, int padding, CancelableCallback callback);

    void animateCamera(LatLngBounds latLngBounds, int width, int height, int padding);

    void moveCamera(LatLngBounds latLngBounds, int width, int height, int padding);

    void moveCamera(LatLngBounds latLngBounds, int padding);

    EngineCircle addCircle(EngineCircleOptions var1);

    EngineMarker addMarker(EngineMarkerOptions var1);

    EnginePolygon addPolygon(EnginePolygonOptions var1);

    interface OnCameraIdleListener {
        void onCameraIdle();
    }

    interface OnCameraMoveListener {
        void onCameraMove();
    }

    interface OnCameraMoveStartedListener {
        int REASON_GESTURE = 1;
        int REASON_API_ANIMATION = 2;
        int REASON_DEVELOPER_ANIMATION = 3;

        void onCameraMoveStarted(int var1);
    }

    interface OnCameraMoveCanceledListener {
        void onCameraMoveCanceled();
    }

    interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    interface InfoWindowAdapter {
        View getInfoWindow(EngineMarker var1);

        View getInfoContents(EngineMarker var1);
    }

    interface OnMarkerClickListener {
        boolean onMarkerClick(EngineMarker var1);
    }

    interface OnMapClickListener {
        void onMapClick(LatLng var1);
    }

    interface CancelableCallback {
        void onFinish();

        void onCancel();
    }
}
