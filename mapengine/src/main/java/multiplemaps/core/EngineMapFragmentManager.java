package multiplemaps.core;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import multiplemaps.core.google.GoogleMapFragment;
import multiplemaps.core.here.HereMapFragment;


/**
 * Created by Daniel on 2018/11/14.
 */
public class EngineMapFragmentManager {

    public static EngineMapFragment findFragment(FragmentActivity activity, int id) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(id);
        if (fragment instanceof GoogleMapFragment) {
            return (GoogleMapFragment)fragment;
        } else if (fragment instanceof HereMapFragment) {
            return (HereMapFragment)fragment;
        }
        return null;
    }
}
