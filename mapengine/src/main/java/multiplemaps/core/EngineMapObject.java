package multiplemaps.core;

import android.support.annotation.Nullable;

/**
 * Created by Daniel on 2018/11/15.
 */
public interface EngineMapObject {

    void setTag(@Nullable Object var1);

    Object getTag();

    void setMapObject(@Nullable Object var1);

    Object getMapObject();
}
