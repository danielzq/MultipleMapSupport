package multiplemaps.core;

import android.support.v4.app.FragmentActivity;

import multiplemaps.core.google.GoogleMapFragment;
import multiplemaps.core.here.HereMapFragment;

/**
 * Created by Daniel on 2018/11/14.
 */
public class EngineMapFragmentManager {

    public static EngineMapFragment findFragment(FragmentActivity activity, int id) {
        GoogleMapFragment googleMapFragment = (GoogleMapFragment) activity.getSupportFragmentManager().findFragmentById(id);
        if (googleMapFragment != null) {
            return googleMapFragment;
        }
        HereMapFragment hereMapFragment = (HereMapFragment) activity.getFragmentManager().findFragmentById(id);
        if (hereMapFragment != null) {
            return hereMapFragment;
        }
        return null;
    }
}
