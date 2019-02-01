package com.test.bradt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.bradt.model.Store;
import com.test.bradt.model.Stores;
import com.test.bradt.model.StoresViewModel;

import androidx.navigation.Navigation;

/**
 * {@link RecyclerView.Adapter} that can display {@link Stores}
 */
public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.ViewHolder> {

    private final StoresViewModel mStoresViewModel;

    public StoreRecyclerViewAdapter(StoresViewModel storesViewModel) {
        mStoresViewModel = storesViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_store_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mStoresViewModel.getStores().getValue().getStores().get(position);
        Picasso.get().load(holder.mItem.getStoreLogoURL()).into(holder.mLogoView);
        holder.mPhoneView.setText(holder.mItem.getPhone());
        holder.mAddressView.setText(holder.mItem.getFormattedAddress());

        holder.mView.setOnClickListener((View v) -> {
            mStoresViewModel.setSelectedStore(holder.mItem);
            Navigation.findNavController(holder.mView).navigate(R.id.action_storeGridFragment_to_storeMapFragment);
        });
    }

    @Override
    public int getItemCount() {
        return mStoresViewModel.getStores().getValue().getStores().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mLogoView;
        public final TextView mPhoneView;
        public final TextView mAddressView;
        public Store mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLogoView = view.findViewById(R.id.logo);
            mPhoneView = view.findViewById(R.id.phone);
            mAddressView = view.findViewById(R.id.address);
        }
    }
}
