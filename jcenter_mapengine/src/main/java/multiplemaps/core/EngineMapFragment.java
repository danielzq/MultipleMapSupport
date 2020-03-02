/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core;

/**
 * Created by Daniel on 2018/11/13.
 */
public interface EngineMapFragment {

    void getEngineMapAsync(OnEngineMapReadyCallback var1);

    void setCopyrightMargin(int margin);
}
