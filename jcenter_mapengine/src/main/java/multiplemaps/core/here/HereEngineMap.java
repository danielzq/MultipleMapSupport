package multiplemaps.core.here;

import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.view.MotionEvent;
import android.view.View;

import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPolygon;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapCircle;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapPolygon;
import com.here.android.mpa.mapping.MapState;
import com.here.android.mpa.mapping.SupportMapFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import multiplemaps.core.CameraPosition;
import multiplemaps.core.EngineCircle;
import multiplemaps.core.EngineCircleOptions;
import multiplemaps.core.EngineMap;
import multiplemaps.core.EngineMapObject;
import multiplemaps.core.EngineMarker;
import multiplemaps.core.EngineMarkerOptions;
import multiplemaps.core.EnginePolygon;
import multiplemaps.core.EnginePolygonOptions;
import multiplemaps.core.LatLng;
import multiplemaps.core.LatLngBounds;
import multiplemaps.core.Projection;
import multiplemaps.core.R;


/**
 * Created by Daniel on 2018/11/13.
 */
public class HereEngineMap implements EngineMap, PositioningManager.OnPositionChangedListener, Map.OnTransformListener {

    private Map map;

    private SupportMapFragment mapFragment;

    private HashMap<String, EngineMapObject> mapObjects = new HashMap<>();

    private OnMarkerClickListener onMarkerClickListener;

    private OnMapClickListener onMapClickListener;

    private OnCameraMoveListener onCameraMoveListener;

    private OnCameraIdleListener onCameraIdleListener;

    private OnCameraMoveStartedListener onCameraMoveStartedListener;

    private OnCameraMoveCanceledListener onCameraMoveCanceledListener;

    private OnMapLoadedCallback onMapLoadedCallback;

    private InfoWindowAdapter infoWindowAdapter;

    private CancelableCallback cancelableCallback;

    private boolean cameraMoving;

    public HereEngineMap(@NonNull Map map, @NonNull SupportMapFragment mapFragment) {
        this.map = map;
        this.mapFragment = mapFragment;
    }

    private void clearFromId(String id) {
        if (mapObjects.containsKey(id)) {
            MapObject mapObject = (MapObject) mapObjects.get(id).getMapObject();
            map.removeMapObject(mapObject);
            mapObjects.remove(id);
        }
    }

    private void addMapObject(String id, MapObject var1) {
        clearFromId(id);
        map.addMapObject(var1);
    }

    @Override
    public void clear() {
        List<MapObject> list = new ArrayList<>();
        for (EngineMapObject engineMapObject : mapObjects.values()) {
            list.add((MapObject) engineMapObject.getMapObject());
        }
        list.addAll(list);
        map.removeMapObjects(list);
        mapObjects.clear();
    }

    @Override
    public void stopAnimation() {
    }

    @Override
    public void setOnCameraMoveListener(OnCameraMoveListener listener) {
        onCameraMoveListener = listener;
        map.addTransformListener(this);
    }

    @Override
    public void setOnCameraIdleListener(OnCameraIdleListener listener) {
        onCameraIdleListener = listener;
        map.addTransformListener(this);
    }

    @Override
    public void setOnCameraMoveStartedListener(OnCameraMoveStartedListener listener) {
        onCameraMoveStartedListener = listener;
        map.addTransformListener(this);
    }

    @Override
    public void setOnCameraMoveCanceledListener(OnCameraMoveCanceledListener listener) {
        onCameraMoveCanceledListener = listener;
        map.addTransformListener(this);
    }

    @Override
    public void setOnMapLoadedCallback(OnMapLoadedCallback listener) {
        onMapLoadedCallback = listener;
        if (onMapLoadedCallback != null) {
            onMapLoadedCallback.onMapLoaded();
        }
    }

    @Override
    public void setZoomControlsEnabled(boolean enabled) {
    }

    @Override
    public void setCompassEnabled(boolean enabled) {
    }

    @Override
    public void setRotateGesturesEnabled(boolean enabled) {
    }

    @Override
    public void setAllGesturesEnabled(boolean enabled) {
        if (!enabled) {
            mapFragment.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        } else {
            mapFragment.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }
    }

    @Override
    public void setMyLocationButtonEnabled(boolean enabled) {
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @Override
    public void setMyLocationEnabled(boolean enabled) {
        if (mapFragment != null && mapFragment.getPositionIndicator() != null) {
            mapFragment.getPositionIndicator().setVisible(enabled);
            Image image = new Image();
            try {
                image.setImageResource(R.drawable.map_current_loc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapFragment.getPositionIndicator().setMarker(image);
            mapFragment.getPositionIndicator().setZIndex(0);
        }

        try {
            PositioningManager positioningManager = PositioningManager.getInstance();
            if (enabled) {
                positioningManager.addListener(new WeakReference<PositioningManager.OnPositionChangedListener>(this));
                positioningManager.start(PositioningManager.LocationMethod.GPS_NETWORK_INDOOR);
            } else {
                if (positioningManager.isActive()) {
                    positioningManager.stop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setIndoorLevelPickerEnabled(boolean enabled) {
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        onMarkerClickListener = listener;
        map.addTransformListener(this);
        mapFragment.getMapGesture().addOnGestureListener(onGestureListener, 2, false);
    }

    @Override
    public void setOnMapClickListener(OnMapClickListener listener) {
        onMapClickListener = listener;
        mapFragment.getMapGesture().addOnGestureListener(onGestureListener, 1, false);
    }

    @Override
    public void setInfoWindowAdapter(InfoWindowAdapter infoWindowAdapter) {
        this.infoWindowAdapter = infoWindowAdapter;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (mapFragment != null && mapFragment.getView() != null) {
            int width = mapFragment.getView().getWidth();
            int height = mapFragment.getView().getHeight();
            try {
                mapFragment.setCopyrightBoundaryRect(new Rect(left, top, width - right, height - bottom));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CameraPosition getCameraPosition() {
        CameraPosition cameraPosition = new CameraPosition();
        cameraPosition.target = new LatLng(map.getCenter().getLatitude(), map.getCenter().getLongitude());
        cameraPosition.zoom = (float) map.getZoomLevel();
        cameraPosition.bearing = (float) map.getCenter().getAltitude();
        cameraPosition.tilt = map.getTilt();
        return cameraPosition;
    }

    @Override
    public Projection getProjection() {
        return new HereProjection(map);
    }

    @Override
    public void moveCamera(LatLng position, float zoomLevel) {
        try {
            map.setCenter(new GeoCoordinate(position.latitude, position.longitude), Map.Animation.NONE, zoomLevel, Map.MOVE_PRESERVE_ORIENTATION, Map.MOVE_PRESERVE_TILT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void animateCamera(LatLng position, float zoomLevel) {
        try {
            map.setCenter(new GeoCoordinate(position.latitude, position.longitude), Map.Animation.BOW, zoomLevel, Map.MOVE_PRESERVE_ORIENTATION, Map.MOVE_PRESERVE_TILT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void animateCamera(LatLng position, CancelableCallback callback) {
        try {
            map.setCenter(new GeoCoordinate(position.latitude, position.longitude), Map.Animation.BOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cancelableCallback = callback;
        cameraMoving = true;
    }

    @Override
    public void animateCamera(LatLngBounds latLngBounds, int padding) {
        ArrayList<GeoCoordinate> list = new ArrayList<>();
        for (LatLng latLng : latLngBounds.getPoints()) {
            list.add(new GeoCoordinate(latLng.latitude, latLng.longitude));
        }
        GeoBoundingBox boundingBox = GeoBoundingBox.getBoundingBoxContainingGeoCoordinates(list);
        try {
            map.zoomTo(boundingBox, Map.Animation.BOW, Map.MOVE_PRESERVE_ORIENTATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void animateCameraOffsetY(float offsetY) {
        PointF pointF = map.getTransformCenter();
        pointF.offset(0, offsetY);
        map.setTransformCenter(pointF);

    }

    @Override
    public void animateCamera(LatLng position, float zoomLevel, final CancelableCallback callback) {
        animateCamera(position, zoomLevel);
        cancelableCallback = callback;
        cameraMoving = true;
    }

    @Override
    public void animateCamera(LatLngBounds latLngBounds, int padding, final CancelableCallback callback) {
        ArrayList<GeoCoordinate> list = new ArrayList<>();
        for (LatLng latLng : latLngBounds.getPoints()) {
            list.add(new GeoCoordinate(latLng.latitude, latLng.longitude));
        }
        GeoBoundingBox boundingBox = GeoBoundingBox.getBoundingBoxContainingGeoCoordinates(list);
        try {
            map.zoomTo(boundingBox, Map.Animation.BOW, Map.MOVE_PRESERVE_ORIENTATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cancelableCallback = callback;
        cameraMoving = true;
    }

    @Override
    public void animateCamera(LatLngBounds latLngBounds, int width, int height, int padding) {
        ArrayList<GeoCoordinate> list = new ArrayList<>();
        for (LatLng latLng : latLngBounds.getPoints()) {
            list.add(new GeoCoordinate(latLng.latitude, latLng.longitude));
        }
        GeoBoundingBox boundingBox = GeoBoundingBox.getBoundingBoxContainingGeoCoordinates(list);
        try {
            map.zoomTo(boundingBox, Map.Animation.BOW, Map.MOVE_PRESERVE_ORIENTATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveCamera(LatLngBounds latLngBounds, int width, int height, int padding) {
        ArrayList<GeoCoordinate> list = new ArrayList<>();
        for (LatLng latLng : latLngBounds.getPoints()) {
            list.add(new GeoCoordinate(latLng.latitude, latLng.longitude));
        }
        GeoBoundingBox boundingBox = GeoBoundingBox.getBoundingBoxContainingGeoCoordinates(list);
        try {
            map.zoomTo(boundingBox, width, height, Map.Animation.BOW, Map.MOVE_PRESERVE_ORIENTATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EngineCircle addCircle(EngineCircleOptions var1) {
        MapCircle circle = new MapCircle();
        GeoCoordinate geoCoordinate = new GeoCoordinate(var1.getCenter().latitude, var1.getCenter().longitude);
        circle.setCenter(geoCoordinate);
        circle.setFillColor(var1.getFillColor());
        circle.setLineColor(var1.getStrokeColor());
        circle.setRadius(var1.getRadius());
        circle.setLineWidth((int) var1.getStrokeWidth());
        circle.setZIndex(mapObjects.size());
        EngineCircle engineCircle = new HereCircle(geoCoordinate.toString(), circle, map);
        addMapObject(engineCircle.getId(), circle);
        mapObjects.put(engineCircle.getId(), engineCircle);
        return engineCircle;
    }

    @Override
    public EngineMarker addMarker(EngineMarkerOptions var1) {
        Image image = new Image();
        try {
            image.setImageResource(var1.getIconRes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GeoCoordinate geoCoordinate = new GeoCoordinate(var1.getPosition().latitude, var1.getPosition().longitude);
        MapMarker marker = new MapMarker(geoCoordinate, image);
        marker.setTitle(var1.getTitle());
        marker.setDescription(var1.getSnippet());
        marker.setZIndex(mapObjects.size());
        EngineMarker engineMarker = new HereMarker(marker.getTitle() + geoCoordinate.toString(), marker, map);
        addMapObject(engineMarker.getId(), marker);
        mapObjects.put(engineMarker.getId(), engineMarker);
        return engineMarker;
    }

    @Override
    public EnginePolygon addPolygon(EnginePolygonOptions var1) {
        List<GeoCoordinate> coordinates = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (LatLng latLng : var1.getPoints()) {
            GeoCoordinate geoCoordinate = new GeoCoordinate(latLng.latitude, latLng.longitude);
            coordinates.add(geoCoordinate);
            stringBuilder.append(geoCoordinate.toString());
        }
        GeoPolygon geoPolygon = new GeoPolygon(coordinates);
        MapPolygon m_polygon = new MapPolygon(geoPolygon);
        m_polygon.setLineColor(var1.getStrokeColor());
        m_polygon.setFillColor(var1.getFillColor());
        m_polygon.setLineWidth((int) var1.getStrokeWidth());
        m_polygon.setZIndex(mapObjects.size());
        EnginePolygon enginePolygon = new HerePolygon(stringBuilder.toString(), m_polygon);
        addMapObject(enginePolygon.getId(), m_polygon);
        mapObjects.put(enginePolygon.getId(), enginePolygon);
        return enginePolygon;
    }

    @Override
    public void onPositionUpdated(PositioningManager.LocationMethod locationMethod, GeoPosition geoPosition, boolean b) {
    }

    @Override
    public void onPositionFixChanged(PositioningManager.LocationMethod locationMethod, PositioningManager.LocationStatus locationStatus) {
    }

    @Override
    public void onMapTransformStart() {
        if (onCameraMoveListener != null) {
            onCameraMoveListener.onCameraMove();
        }
        if (onCameraMoveStartedListener != null) {
            onCameraMoveStartedListener.onCameraMoveStarted(OnCameraMoveStartedListener.REASON_GESTURE);
        }
    }

    @Override
    public void onMapTransformEnd(MapState mapState) {
        if (onCameraIdleListener != null) {
            onCameraIdleListener.onCameraIdle();
        }
        if (onCameraMoveCanceledListener != null) {
            onCameraMoveCanceledListener.onCameraMoveCanceled();
        }
        if (cameraMoving) {
            if (cancelableCallback != null) {
                cancelableCallback.onFinish();
            }
            cameraMoving = false;
        }
    }

    private MapGesture.OnGestureListener onGestureListener = new MapGesture.OnGestureListener() {
        @Override
        public void onPanStart() {

        }

        @Override
        public void onPanEnd() {

        }

        @Override
        public void onMultiFingerManipulationStart() {

        }

        @Override
        public void onMultiFingerManipulationEnd() {

        }

        @Override
        public boolean onMapObjectsSelected(final List<ViewObject> list) {
            for (ViewObject viewObject : list) {
                if (viewObject.getBaseType() == ViewObject.Type.USER_OBJECT) {
                    MapObject mapObject = (MapObject) viewObject;
                    if (mapObject.getType() == MapObject.Type.MARKER) {
                        MapMarker window_marker = ((MapMarker) mapObject);
                        EngineMarker tmpMarker = new HereMarker(window_marker.getTitle() + window_marker.getCoordinate().toString(), window_marker, map);
                        if (mapObjects.containsKey(tmpMarker.getId()) && onMarkerClickListener != null) {
                            EngineMarker realMarker = (EngineMarker) mapObjects.get(tmpMarker.getId());
                            onMarkerClickListener.onMarkerClick(realMarker);
                        }
                        break;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean onTapEvent(final PointF pointF) {
            try {
                if (onMapClickListener != null) {
                    GeoCoordinate geoCoordinate = HereEngineMap.this.map.pixelToGeo(pointF);
                    if (geoCoordinate != null) {
                        onMapClickListener.onMapClick(new LatLng(geoCoordinate.getLatitude(), geoCoordinate.getLongitude()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(PointF pointF) {
            return false;
        }

        @Override
        public void onPinchLocked() {

        }

        @Override
        public boolean onPinchZoomEvent(float v, PointF pointF) {
            return false;
        }

        @Override
        public void onRotateLocked() {

        }

        @Override
        public boolean onRotateEvent(float v) {
            return false;
        }

        @Override
        public boolean onTiltEvent(float v) {
            return false;
        }

        @Override
        public boolean onLongPressEvent(PointF pointF) {
            return false;
        }

        @Override
        public void onLongPressRelease() {

        }

        @Override
        public boolean onTwoFingerTapEvent(PointF pointF) {
            return false;
        }
    };
}
