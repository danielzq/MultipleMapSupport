/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core;


import androidx.annotation.Nullable;

/**
 * Created by Daniel on 2018/11/15.
 */
public interface EngineMapObject {

    String getId();

    void setTag(@Nullable Object var1);

    Object getTag();

    void setMapObject(@Nullable Object var1);

    Object getMapObject();
}
