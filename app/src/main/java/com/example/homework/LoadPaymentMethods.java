package com.example.homework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.example.homework.interfaces.LoadListener;
import com.example.homework.models.ApplicableNetwork;
import com.example.homework.models.InputElement;
import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadPaymentMethods {
    public static List<ApplicableNetwork> mApplicableNetworks = new ArrayList<>();
    private static final String TAG = LoadPaymentMethods.class.getSimpleName();

    public static class LoadPaymentMethodsTask extends AsyncTask<Void, Void, Void> {
        private LoadListener loadListener;
        private static WeakReference<Context> mContext;

        public LoadPaymentMethodsTask(Context context) {
            mContext = new WeakReference<>(context);
            loadListener = MainActivity.mLoadListener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                String address = Constants.URL;
                URL url = new URL(address);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                int status = urlConnection.getResponseCode();
                Log.d("response code", status + "");
                if(status == 200) {
                    InputStream in = urlConnection.getInputStream();
                    String data = new String(ByteStreams.toByteArray(in));
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) != -1) {
                        String curr = new String(buffer, "UTF-8");
                        data += curr;
                    }
                    JSONObject jsonObject = new JSONObject(data);
                    parseJsonToObject(jsonObject);
                } else {
                    Log.e( "url_connection_error", urlConnection.getErrorStream() +"");
                }

            } catch (JSONException jsonException) {
                Log.e(TAG, "JSON ERROR", jsonException);
            } catch (IOException ioException) {
                Log.e(TAG, "IOException ERROR", ioException);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadListener.load(mApplicableNetworks);

        }

        public List<ApplicableNetwork> getApplicableNetworks() {
            return mApplicableNetworks;
        }

    }

    public static LoadListener setLoadListener(LoadListener loadListener) {
        return loadListener;
    }

    private static void parseJsonToObject(JSONObject json) {
        try {
            String networksString = json.getString("networks");
            JSONObject applicableObject = new JSONObject(networksString);
            JSONArray applicableNetworks = new JSONArray(applicableObject.getString("applicable"));
            for (int i = 0; i <= applicableNetworks.length() - 1; i++) {
                JSONObject obj = applicableNetworks.getJSONObject(i);
                // create a new json object
                ApplicableNetwork network = new ApplicableNetwork();
                network.setCode(obj.getString("code"));
                network.setLabel(obj.getString("label"));
                network.setMethod(obj.getString("method"));
                network.setGrouping(obj.getString("grouping"));
                network.setRegistration(obj.getString("registration"));
                network.setRecurrence(obj.getString("recurrence"));
                network.setRedirect(obj.getBoolean("redirect"));
                network.setOperationType(obj.getString("operationType"));
                network.setSelected(obj.getBoolean("selected"));

                // illiterate over the links in the json object
                JSONObject links = new JSONObject(obj.getString("links"));
                try {
                    Map<String, URL> map = new HashMap<>();
                    map.put("logo", new URL(links.getString("logo")));
                    map.put("self", new URL(links.getString("self")));
                    map.put("lang", new URL(links.getString("lang")));
                    map.put("operation", new URL(links.getString("operation")));
                    map.put("validation", new URL(links.getString("validation")));
                    network.setLinks(map);
                } catch (MalformedURLException exception) {
                    exception.printStackTrace();
                }

                // illiterate over the input elements in the json object
//                JSONArray inputElements =  new JSONArray(obj.getString("inputElements"));
//                List<InputElement> elements = new ArrayList<>();
//                for (int input = 0; input <= inputElements.length() - 1; input++) {
//                    JSONObject inputObject = inputElements.getJSONObject(input);
//                    InputElement element = new InputElement();
//                    element.setName(inputObject.getString("name"));
//                    element.setType(inputObject.getString("type"));
//                    elements.add(element);
//                }
//                network.setInputElements(elements);

                mApplicableNetworks.add(network);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
