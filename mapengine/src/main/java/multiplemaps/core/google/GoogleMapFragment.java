package multiplemaps.core.google;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import multiplemaps.core.EngineMap;
import multiplemaps.core.EngineMapFragment;
import multiplemaps.core.OnEngineMapReadyCallback;

/**
 * Created by Daniel on 2018/11/13.
 */
public class GoogleMapFragment extends SupportMapFragment implements EngineMapFragment, OnMapReadyCallback {

    private OnEngineMapReadyCallback onEngineMapReadyCallback;

    @Override
    public void getEngineMapAsync(OnEngineMapReadyCallback var1) {
        onEngineMapReadyCallback = var1;
        MapsInitializer.initialize(getContext());
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        EngineMap engineMap = new GoogleEngineMap(googleMap);
        if (onEngineMapReadyCallback != null) {
            onEngineMapReadyCallback.onMapReady(engineMap);
        }
    }
}
