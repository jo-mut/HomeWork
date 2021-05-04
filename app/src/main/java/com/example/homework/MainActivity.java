package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.homework.adapters.PaymentMethodAdapter;
import com.example.homework.interfaces.LoadListener;
import com.example.homework.models.ApplicableNetwork;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadListener {
    private PaymentMethodAdapter mPaymentMethodAdapter;
    private List<ApplicableNetwork> mApplicableNetworks = new ArrayList<>();
    private RecyclerView mPaymentMethodsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPaymentMethodsRecyclerView = findViewById(R.id.paymentRecyclerView);
        LoadPaymentMethods.setLoadListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new LoadPaymentMethods.LoadPaymentMethodsTask(this).execute();
//        LoadPaymentMethods.setLoadListener(new LoadListener() {
//            @Override
//            public void load(List<ApplicableNetwork> applicableNetworks) {
//
//
//            }
//        });
    }

    @Override
    public void load(List<ApplicableNetwork> applicableNetworks) {
        mApplicableNetworks = applicableNetworks;
        inflateRecyclerView();
    }

    private void inflateRecyclerView() {
        mPaymentMethodAdapter = new PaymentMethodAdapter(this, mApplicableNetworks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPaymentMethodsRecyclerView.setLayoutManager(linearLayoutManager);
        mPaymentMethodsRecyclerView.setHasFixedSize(true);
        mPaymentMethodsRecyclerView.setAdapter(mPaymentMethodAdapter);
    }

}