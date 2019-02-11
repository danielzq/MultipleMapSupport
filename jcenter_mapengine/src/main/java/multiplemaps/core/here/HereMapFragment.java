package multiplemaps.core.here;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.here.android.mpa.common.CopyrightLogoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.SupportMapFragment;

import java.io.File;

import multiplemaps.core.EngineMap;
import multiplemaps.core.EngineMapFragment;
import multiplemaps.core.OnEngineMapReadyCallback;


/**
 * Created by Daniel on 2018/11/13.
 */
public class HereMapFragment extends SupportMapFragment implements EngineMapFragment, OnEngineInitListener {

    private OnEngineMapReadyCallback onEngineMapReadyCallback;

    public HereMapFragment() {
        super();
    }

    @Override
    public void getEngineMapAsync(OnEngineMapReadyCallback var1) {
        onEngineMapReadyCallback = var1;
        setCopyrightLogoPosition(CopyrightLogoPosition.BOTTOM_LEFT);
        setRetainInstance(false);
//        mapEngine = MapEngine.getInstance();
//        ApplicationContext appContext = new ApplicationContext(getActivity().getApplicationContext());

        // Set path of isolated disk cache
        String diskCacheRoot = Environment.getExternalStorageDirectory().getPath()
                + File.separator + ".isolated-here-maps";
        // Retrieve intent name from manifest
        String intentName = "";
        try {
            ApplicationInfo ai = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            intentName = bundle.getString("INTENT_NAME");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(this.getClass().toString(), "Failed to find intent name, NameNotFound: " + e.getMessage());
        }

        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(diskCacheRoot, intentName);
        if (!success) {
            // Setting the isolated disk cache was not successful, please check if the path is valid and
            // ensure that it does not match the default location
            // (getExternalStorageDirectory()/.here-maps).
            // Also, ensure the provided intent name does not match the default intent name.
        } else {
            init(this);
        }
    }

    @Override
    public void onEngineInitializationCompleted(Error error) {
        if (error == OnEngineInitListener.Error.NONE) {
            Map map = getMap();
            EngineMap engineMap = new HereEngineMap(map, this);
            if (onEngineMapReadyCallback != null) {
                onEngineMapReadyCallback.onMapReady(engineMap);
            }
        } else {
            error.getThrowable().printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (PositioningManager.getInstance().isActive()) {
                PositioningManager.getInstance().stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
