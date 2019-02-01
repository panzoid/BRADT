package com.test.bradt;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.bradt.model.StoresViewModel;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A fragment used to show the splash screen and ensures the StoresViewModel is loaded before continuing.
 */
public class LoadingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        StoresViewModel model = ViewModelProviders.of(getActivity()).get(StoresViewModel.class);
        NavController navController = Navigation.findNavController(getView());

        if(model.getStores().getValue() == null) {
            model.getStores().observe(this, stores -> {
                if (stores == null) {
                    new AlertDialog.Builder(getActivity()).setMessage(R.string.loading_error).show();
                } else {
                    navController.navigate(R.id.action_loadingFragment_to_storeGridFragment);
                }
            });
        } else {
            navController.navigate(R.id.action_loadingFragment_to_storeGridFragment);
        }
    }
}
