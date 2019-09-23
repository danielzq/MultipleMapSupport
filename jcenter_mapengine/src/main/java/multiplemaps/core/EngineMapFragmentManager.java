package multiplemaps.core;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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

    public static EngineMapFragment findFragment(Fragment f, int id) {
        Fragment fragment = f.getChildFragmentManager().findFragmentById(id);
        if (fragment instanceof GoogleMapFragment) {
            return (GoogleMapFragment)fragment;
        } else if (fragment instanceof HereMapFragment) {
            return (HereMapFragment)fragment;
        }
        return null;
    }
}
