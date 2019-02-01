package com.test.bradt;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.bradt.model.StoresViewModel;

import androidx.navigation.Navigation;

/**
 * A fragment that shows Stores in a Grid with different columns
 * depending on the size and orientation of the device.
 */
public class StoreGridFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_grid, container, false);
        int columns = getResources().getInteger(R.integer.columns_count);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (columns <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, columns));
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        StoresViewModel viewModel = ViewModelProviders.of(getActivity()).get(StoresViewModel.class);
        if(viewModel.getStores().getValue() == null) {
            // ViewModel was cleared, return to loadingFragment to reload it.
            Navigation.findNavController(getView()).navigate(R.id.action_storeGridFragment_to_loadingFragment2);
        } else {
            mRecyclerView.setAdapter(new StoreRecyclerViewAdapter(viewModel));
        }
    }
}
