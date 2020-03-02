/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core.here;


import android.os.Bundle;

import androidx.annotation.Nullable;

import com.here.android.mpa.common.CopyrightLogoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;

import multiplemaps.core.EngineMap;
import multiplemaps.core.EngineMapFragment;
import multiplemaps.core.OnEngineMapReadyCallback;


/**
 * Created by Daniel on 2018/11/13.
 */
public class HereMapFragment extends AndroidXMapFragment implements EngineMapFragment, OnEngineInitListener {

    private OnEngineMapReadyCallback onEngineMapReadyCallback;

    public HereMapFragment() {
        super();
    }

    @Override
    public void getEngineMapAsync(OnEngineMapReadyCallback var1) {
        onEngineMapReadyCallback = var1;
        setRetainInstance(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this);
    }

    @Override
    public void onEngineInitializationCompleted(Error error) {
        if (error == OnEngineInitListener.Error.NONE) {
            setCopyrightLogoPosition(CopyrightLogoPosition.BOTTOM_LEFT);
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
