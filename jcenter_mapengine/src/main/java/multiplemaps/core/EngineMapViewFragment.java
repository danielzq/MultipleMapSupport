/*
 * Copyright (c) 2020 Daniel Zhang. All rights reserved.
 */

package multiplemaps.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import multiplemaps.core.google.GoogleMapFragment;
import multiplemaps.core.here.HereMapFragment;

/**
 * Created by Daniel on 2019-10-28.
 */
public class EngineMapViewFragment extends Fragment implements EngineMapFragment {

    private OnEngineMapReadyCallback onEngineMapReadyCallback;

    private int margin;

    public static EngineMapViewFragment getNewInstance(EngineMapType type) {
        EngineMapViewFragment fragment = new EngineMapViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EngineMapType", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_engine_map_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EngineMapType type = EngineMapType.GOOGLE_MAP;
        if (getArguments() != null && getArguments().get("EngineMapType") != null) {
            type = (EngineMapType) getArguments().get("EngineMapType");
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        EngineMapFragment engineMapFragment;
        switch (type) {
            case HERE_MAP:
                HereMapFragment hereMapFragment = new HereMapFragment();
                engineMapFragment = hereMapFragment;
                transaction.replace(R.id.engine_map_view_fl, hereMapFragment);
                break;
            default:
            case GOOGLE_MAP:
                GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                engineMapFragment = googleMapFragment;
                transaction.replace(R.id.engine_map_view_fl, googleMapFragment);
                break;
        }
        transaction.commit();

        engineMapFragment.getEngineMapAsync(onEngineMapReadyCallback);
        engineMapFragment.setCopyrightMargin(margin);
    }

    @Override
    public void getEngineMapAsync(OnEngineMapReadyCallback var1) {
        onEngineMapReadyCallback = var1;
    }

    @Override
    public void setCopyrightMargin(int margin) {
        this.margin = margin;
    }
}
