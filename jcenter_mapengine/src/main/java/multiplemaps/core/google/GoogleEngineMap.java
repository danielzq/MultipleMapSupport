package multiplemaps.core.google;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import multiplemaps.core.CameraPosition;
import multiplemaps.core.EngineCircle;
import multiplemaps.core.EngineCircleOptions;
import multiplemaps.core.EngineMap;
import multiplemaps.core.EngineMarker;
import multiplemaps.core.EngineMarkerOptions;
import multiplemaps.core.EnginePolygon;
import multiplemaps.core.EnginePolygonOptions;
import multiplemaps.core.LatLng;
import multiplemaps.core.LatLngBounds;
import multiplemaps.core.Projection;


/**
 * Created by Daniel on 2018/11/13.
 */
public class GoogleEngineMap implements EngineMap, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;

    private OnMarkerClickListener onMarkerClickListener;

    private OnMapClickListener onMapClickListener;

    private OnCameraMoveListener onCameraMoveListener;

    private OnCameraIdleListener onCameraIdleListener;

    private OnCameraMoveStartedListener onCameraMoveStartedListener;

    private OnCameraMoveCanceledListener onCameraMoveCanceledListener;

    private OnMapLoadedCallback onMapLoadedCallback;

    private InfoWindowAdapter infoWindowAdapter;

    public GoogleEngineMap(@NonNull GoogleMap map) {
        this.map = map;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void stopAnimation() {
        map.stopAnimation();
    }

    @Override
    public void setOnCameraMoveListener(OnCameraMoveListener listener) {
        onCameraMoveListener = listener;
        map.setOnCameraMoveListener(this);
    }

    @Override
    public void setOnCameraIdleListener(OnCameraIdleListener listener) {
        onCameraIdleListener = listener;
        map.setOnCameraIdleListener(this);
    }

    @Override
    public void setOnCameraMoveStartedListener(OnCameraMoveStartedListener listener) {
        onCameraMoveStartedListener = listener;
        map.setOnCameraMoveStartedListener(this);
    }

    @Override
    public void setOnCameraMoveCanceledListener(OnCameraMoveCanceledListener listener) {
        onCameraMoveCanceledListener = listener;
        map.setOnCameraMoveCanceledListener(this);
    }

    @Override
    public void setOnMapLoadedCallback(OnMapLoadedCallback listener) {
        onMapLoadedCallback = listener;
        map.setOnMapLoadedCallback(this);
    }

    @Override
    public void setZoomControlsEnabled(boolean enabled) {
        map.getUiSettings().setZoomControlsEnabled(enabled);
    }

    @Override
    public void setCompassEnabled(boolean enabled) {
        map.getUiSettings().setCompassEnabled(enabled);
    }

    @Override
    public void setRotateGesturesEnabled(boolean enabled) {
        map.getUiSettings().setRotateGesturesEnabled(enabled);
    }

    @Override
    public void setAllGesturesEnabled(boolean enabled) {
        map.getUiSettings().setAllGesturesEnabled(enabled);
    }

    @Override
    public void setMyLocationButtonEnabled(boolean enabled) {
        map.getUiSettings().setMyLocationButtonEnabled(enabled);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @Override
    public void setMyLocationEnabled(boolean enabled) {
        map.setMyLocationEnabled(enabled);
    }

    @Override
    public void setIndoorLevelPickerEnabled(boolean enabled) {
        map.getUiSettings().setIndoorLevelPickerEnabled(enabled);
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        onMarkerClickListener = listener;
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void setOnMapClickListener(OnMapClickListener listener) {
        onMapClickListener = listener;
        map.setOnMapClickListener(this);
    }

    @Override
    public void setInfoWindowAdapter(InfoWindowAdapter infoWindowAdapter) {
        this.infoWindowAdapter = infoWindowAdapter;
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return GoogleEngineMap.this.infoWindowAdapter.getInfoWindow(new GoogleMarker(marker));
            }

            @Override
            public View getInfoContents(Marker marker) {
                return GoogleEngineMap.this.infoWindowAdapter.getInfoContents(new GoogleMarker(marker));
            }
        });
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        map.setPadding(left, top, right, bottom);
    }

    @Override
    public CameraPosition getCameraPosition() {
        CameraPosition cameraPosition = new CameraPosition();
        cameraPosition.target = new LatLng(map.getCameraPosition().target.latitude, map.getCameraPosition().target.longitude);
        cameraPosition.zoom = map.getCameraPosition().zoom;
        cameraPosition.bearing = map.getCameraPosition().bearing;
        cameraPosition.tilt = map.getCameraPosition().tilt;
        return cameraPosition;
    }

    @Override
    public Projection getProjection() {
        return new GoogleProjection(map);
    }

    @Override
    public void moveCamera(LatLng position, float zoomLevel) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(position.latitude, position.longitude), zoomLevel));
    }

    @Override
    public void animateCamera(LatLng position, float zoomLevel) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(position.latitude, position.longitude), zoomLevel));
    }

    @Override
    public void animateCamera(LatLng position, final CancelableCallback callback) {
        map.animateCamera(CameraUpdateFactory.newLatLng(new com.google.android.gms.maps.model.LatLng(position.latitude, position.longitude)), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            @Override
            public void onCancel() {
                if (callback != null) {
                    callback.onCancel();
                }
            }
        });
    }

    @Override
    public void animateCamera(LatLngBounds latLngBounds, int padding) {
        com.google.android.gms.maps.model.LatLngBounds.Builder bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder();
        for (LatLng latLng : latLngBounds.getPoints()) {
            bounds.include(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
        }
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), padding));
    }

    @Override
    public void animateCameraOffsetY(float offsetY) {
        map.animateCamera(CameraUpdateFactory.scrollBy(0, offsetY));
    }

    @Override
    public void animateCamera(LatLng position, float zoomLevel, final CancelableCallback callback) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(position.latitude, position.longitude), zoomLevel), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            @Override
            public void onCancel() {
                if (callback != null) {
                    callback.onCancel();
                }
            }
        });
    }

    @Override
    public void animateCamera(LatLngBounds latLngBounds, int padding, final CancelableCallback callback) {
        com.google.android.gms.maps.model.LatLngBounds.Builder bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder();
        for (LatLng latLng : latLngBounds.getPoints()) {
            bounds.include(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
        }
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), padding), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            @Override
            public void onCancel() {
                if (callback != null) {
                    callback.onCancel();
                }
            }
        });
    }

    @Override
    public void animateCamera(LatLngBounds latLngBounds, int width, int height, int padding) {
        com.google.android.gms.maps.model.LatLngBounds.Builder bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder();
        for (LatLng latLng : latLngBounds.getPoints()) {
            bounds.include(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
        }
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), width, height, padding));
    }

    @Override
    public void moveCamera(LatLngBounds latLngBounds, int width, int height, int padding) {
        com.google.android.gms.maps.model.LatLngBounds.Builder bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder();
        for (LatLng latLng : latLngBounds.getPoints()) {
            bounds.include(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
        }
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), width, height, padding));
    }

    @Override
    public EngineCircle addCircle(EngineCircleOptions var1) {
        com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(var1.getCenter().latitude, var1.getCenter().longitude);
        Circle circle = map.addCircle(new CircleOptions()
                .center(latLng)
                .fillColor(var1.getFillColor())
                .radius(var1.getRadius())
                .strokeColor(var1.getStrokeColor())
                .fillColor(var1.getFillColor())
                .clickable(var1.isClickable())
        );
        EngineCircle engineCircle = new GoogleCircle(circle);
        return engineCircle;
    }

    @Override
    public EngineMarker addMarker(EngineMarkerOptions var1) {
        Marker marker = map.addMarker(new MarkerOptions()
                .title(var1.getTitle())
                .position(new com.google.android.gms.maps.model.LatLng(var1.getPosition().latitude, var1.getPosition().longitude))
                .snippet(var1.getSnippet())
                .flat(var1.isFlat())
                .icon(BitmapDescriptorFactory.fromResource(var1.getIconRes()))
                .anchor(var1.getAnchorX(), var1.getAnchorY())
        );
        EngineMarker engineMarker = new GoogleMarker(marker);
        return engineMarker;
    }

    @Override
    public EnginePolygon addPolygon(EnginePolygonOptions var1) {
        ArrayList<com.google.android.gms.maps.model.LatLng> list = new ArrayList<>();
        for (LatLng latLng:var1.getPoints()) {
            list.add(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
        }
        Polygon polygon = map.addPolygon(new PolygonOptions()
                .addAll(list)
                .fillColor(var1.getFillColor())
                .strokeColor(var1.getStrokeColor())
        );
        EnginePolygon enginePolygon = new GooglePolygon(polygon);
        return enginePolygon;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        EngineMarker engineMarker = new GoogleMarker(marker);
        return onMarkerClickListener.onMarkerClick(engineMarker);
    }

    @Override
    public void onMapClick(com.google.android.gms.maps.model.LatLng latLng) {
        onMapClickListener.onMapClick(new LatLng(latLng.latitude, latLng.longitude));
    }

    @Override
    public void onCameraMove() {
        onCameraMoveListener.onCameraMove();
    }

    @Override
    public void onCameraIdle() {
        onCameraIdleListener.onCameraIdle();
    }

    @Override
    public void onCameraMoveStarted(int i) {
        onCameraMoveStartedListener.onCameraMoveStarted(i);
    }

    @Override
    public void onCameraMoveCanceled() {
        onCameraMoveCanceledListener.onCameraMoveCanceled();
    }

    @Override
    public void onMapLoaded() {
        onMapLoadedCallback.onMapLoaded();
    }
}
