package com.example.homework.view_holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;

public class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View mView;
    public TextView mPaymentMethodNameTextView;
    public ImageView mPaymentMethodImageView;

    public PaymentMethodViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mView = itemView;
        mPaymentMethodNameTextView = mView.findViewById(R.id.paymentMethodTextView);
        mPaymentMethodImageView =  mView.findViewById(R.id.paymentMethodImageView);
    }
}
