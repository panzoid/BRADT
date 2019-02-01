package com.test.bradt.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.test.bradt.R;

public class StoresViewModel extends AndroidViewModel {

    private MutableLiveData<Stores> stores;
    private MutableLiveData<Store> selectedStore = new MutableLiveData<>();

    public StoresViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Stores> getStores() {
        if(stores == null) {
            stores = new MutableLiveData<>();
            loadStores();
        }

        return stores;
    }

    public void setSelectedStore(Store store) {
        selectedStore.setValue(store);
    }

    public LiveData<Store> getSelectedStore() {
        return selectedStore;
    }

    private void loadStores() {
        Log.d(StoresViewModel.class.getSimpleName(), "loadStores");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String url = getApplication().getString(R.string.stores_url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    stores.setValue(new Gson().fromJson(response.toString(), Stores.class));
                }, error -> {
                    Log.d(StoresViewModel.class.getSimpleName(), "loadStores error", error);
                    // See if we have cached data
                    stores.setValue(null);
                    //loadCachedStores();
                });

        queue.add(jsonObjectRequest);
    }

    private void loadCachedStores() {
        getApplication().getExternalCacheDir();
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
