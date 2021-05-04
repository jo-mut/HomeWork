package com.example.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;
import com.example.homework.models.ApplicableNetwork;
import com.example.homework.view_holders.PaymentMethodViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodViewHolder> {
    private Context mContext;
    private List<ApplicableNetwork> mApplicableNetworks = new ArrayList<>();

    public PaymentMethodAdapter(Context mContext, List<ApplicableNetwork> mApplicableNetworks) {
        this.mContext = mContext;
        this.mApplicableNetworks = mApplicableNetworks;
    }

    @NonNull
    @Override
    public PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_payment_methods, parent, false);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodViewHolder holder, int position) {
        ApplicableNetwork network = mApplicableNetworks.get(position);
        if (network != null) {
            String paymentLogo = network.getLinks().get("logo") + "";
            String paymentName = network.getLabel();

            holder.mPaymentMethodNameTextView.setText(paymentName);
            Picasso.get()
                    .load(paymentLogo)
                    .into(holder.mPaymentMethodImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mApplicableNetworks.size();
    }
}
