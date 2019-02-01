package com.test.bradt;


import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.test.bradt.model.Store;
import com.test.bradt.model.Stores;
import com.test.bradt.model.StoresViewModel;

import androidx.navigation.Navigation;

/**
 * A fragment that shows the selected Store in a Google MapView.
 * Displays the details of the Store differently depending on device orientation.
 **/
public class StoreMapFragment extends Fragment implements OnMapReadyCallback {

    private Stores mStores;
    private Store mSelectedStore;

    LinearLayout mMapInfo;
    MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_map, container, false);

        // Only show side info when in landscape mode.
        mMapInfo = view.findViewById(R.id.map_info);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMapInfo.setVisibility(View.VISIBLE);
        }

        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mStores == null || mSelectedStore == null) {
            return;
        }

        // Need our own InfoWindowAdapter to enable multiline.
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Store newStore = null;
                for(Store store : mStores.getStores()) {
                    if((""+store.getStoreID()).equals(marker.getTitle())) {
                        newStore = store;
                        break;
                    }
                }

                if(newStore == null) {
                    return null;
                }

                mSelectedStore = newStore;

                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(newStore.getFormattedName());
                info.addView(title);

                // If we are in portrait mode add a snippet to marker, else update side info.
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    TextView snippet = new TextView(getContext());
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(getMapSnippet(newStore));
                    info.addView(snippet);
                } else {
                    updateInfo(newStore);
                }

                return info;
            }
        });

        // Add a marker for the selected store and move the camera
        LatLng latLng = new LatLng(mSelectedStore.getLatitude(), mSelectedStore.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(""+mSelectedStore.getStoreID())) // Set ID as title so we can lookup our Store when it's clicked.
                .showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        // Add markers for other stores
        for (Store store : mStores.getStores()) {
            if(!store.equals(mSelectedStore)) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(store.getLatitude(), store.getLongitude()))
                        .title(""+store.getStoreID()));
            }
        }
    }

    // Build the snippet for a store.
    private String getMapSnippet(Store store) {
        StringBuilder builder = new StringBuilder();
        builder.append("Phone: ").append(store.getPhone()).append("\n");
        builder.append("Address:\n").append(store.getFormattedAddress());
        return builder.toString();
    }

    // Update the side info for a store.
    private void updateInfo(Store store) {
        if(mMapInfo != null) {
            ((TextView) mMapInfo.findViewById(R.id.map_info_name)).setText(store.getFormattedName());
            ((TextView) mMapInfo.findViewById(R.id.map_info_phone)).setText(store.getPhone());
            ((TextView) mMapInfo.findViewById(R.id.map_info_address)).setText(store.getFormattedAddress());

            Picasso.get().load(store.getStoreLogoURL()).into((ImageView) mMapInfo.findViewById(R.id.map_info_logo));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        StoresViewModel viewModel = ViewModelProviders.of(getActivity()).get(StoresViewModel.class);
        mStores = viewModel.getStores().getValue();
        mSelectedStore = viewModel.getSelectedStore().getValue();

        if(mStores == null) {
            // ViewModel was cleared, return to loadingFragment to reload it.
            Navigation.findNavController(getView()).navigate(R.id.action_storeMapFragment_to_loadingFragment2);
        }

        if(mMapView != null) {
            mMapView.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(mMapView != null) {
            mMapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMapView != null) {
            mMapView.onDestroy();
        }
    }
}
