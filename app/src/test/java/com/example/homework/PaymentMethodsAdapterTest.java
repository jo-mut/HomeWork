package com.example.homework;

import android.app.Application;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;

import com.example.homework.adapters.PaymentMethodsAdapter;
import com.example.homework.interfaces.LoadListener;
import com.example.homework.models.ApplicableNetwork;
import com.example.homework.view_holders.PaymentMethodViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, sdk=28)
public class PaymentMethodsAdapterTest {
    private Context mContext = null;
    private List<ApplicableNetwork> mApplicableNetworks;

    @Before
    public void setUp() {
        Application application = RuntimeEnvironment.application;
        assertNotNull(application);
        mContext = App.getAppContext();
    }

    @Test
    public void initLoadPaymentsAsyncTask() {
        LoadPaymentMethods.LoadPaymentMethodsTask paymentMethodsTask = new LoadPaymentMethods.LoadPaymentMethodsTask(mContext);
        mApplicableNetworks = paymentMethodsTask.getApplicableNetworks();
    }

    @Test
    public void test_onCreateViewHolder() {
        PaymentMethodsAdapter methodsAdapter = new PaymentMethodsAdapter(mContext, mApplicableNetworks);
        LinearLayout parent = new LinearLayout(mContext);
        RecyclerView.ViewHolder methodViewHolder = methodsAdapter.onCreateViewHolder(parent, 0);
        assertTrue(methodViewHolder instanceof PaymentMethodViewHolder);
    }

    @Test
    public void test_getItemCount() {
        LoadPaymentMethods.LoadPaymentMethodsTask paymentMethodsTask = new LoadPaymentMethods.LoadPaymentMethodsTask(mContext);
        mApplicableNetworks = paymentMethodsTask.getApplicableNetworks();
        LoadPaymentMethods.setLoadListener(new LoadListener() {
            @Override
            public void load(List<ApplicableNetwork> applicableNetworks) {
                mApplicableNetworks = applicableNetworks;
                if (mApplicableNetworks != null) {
                    PaymentMethodsAdapter methodsAdapter = new PaymentMethodsAdapter(mContext, mApplicableNetworks);
                    //initial state
                    int initialExpected = 17;
                    int initialActual = methodsAdapter.getItemCount();
                    assertEquals(initialExpected, initialActual);
                }
            }
        });

    }
}
