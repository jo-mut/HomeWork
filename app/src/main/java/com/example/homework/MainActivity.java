package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.homework.adapters.PaymentMethodsAdapter;
import com.example.homework.interfaces.LoadListener;
import com.example.homework.models.ApplicableNetwork;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    public PaymentMethodsAdapter mPaymentMethodsAdapter;
    private List<ApplicableNetwork> mApplicableNetworks = new ArrayList<>();
    private RecyclerView mPaymentMethodsRecyclerView;
    private Toolbar mToolbar;
    public static LoadListener mLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view by id
        mPaymentMethodsRecyclerView = findViewById(R.id.paymentRecyclerView);
        mToolbar = findViewById(R.id.toolbar);

        //support action bar
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.payment_methods);
        }

        // initialise load payments listener
        mLoadListener = LoadPaymentMethods.setLoadListener(new LoadListener() {
            @Override
            public void load(List<ApplicableNetwork> applicableNetworks) {
                mApplicableNetworks = applicableNetworks;
                if (mApplicableNetworks != null) {
                    inflateRecyclerView();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // initialise load payments methods async task
        new LoadPaymentMethods.LoadPaymentMethodsTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_settings:
////                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * populate the recycler view with payments methods*/
    private void inflateRecyclerView() {
        mPaymentMethodsAdapter = new PaymentMethodsAdapter(this, mApplicableNetworks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPaymentMethodsRecyclerView.setLayoutManager(linearLayoutManager);
        mPaymentMethodsRecyclerView.setHasFixedSize(true);
        mPaymentMethodsRecyclerView.setAdapter(mPaymentMethodsAdapter);
    }

}