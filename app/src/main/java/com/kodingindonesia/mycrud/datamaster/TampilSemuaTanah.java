package com.kodingindonesia.mycrud.datamaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.adapter.Adapter;
import com.kodingindonesia.mycrud.app.AppController;
import com.kodingindonesia.mycrud.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TampilSemuaTanah extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String url_cari = "http://192.168.43.138/Android/pegawai/cari_data.php";
    public static final String url_data = "http://192.168.43.138/Android/pegawai/tampilSemuaTanah.php";

    Adapter adapter;
    ProgressDialog pDialog;
    private static final List<DataModelTanah> listData = new ArrayList<DataModelTanah>();
    SwipeRefreshLayout swipe;
    private ImageView imageView;
    private ListView listView;
    private String JSON_STRING;
    String tag_json_obj = "json_obj_req";

    private static final String TAG = TampilSemuaTanah.class.getSimpleName();
    public static final String TAG_ID = "id_tanah";
    public static final String TAG_NAMA = "nama_lengkap";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_tanah);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        listView = (ListView) findViewById(R.id.listView);
        imageView=(ImageView)findViewById(R.id.imgClose);
        listView.setOnItemClickListener(this);
        imageView.setOnClickListener(this);
        /*swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           callData();
                       }
                   }
        );*/
        getJSON();
    }

    private void showTanah() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY6);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_tanah = jo.getString(konfigurasi.TAG_ID_TANAH);
                String nama_lengkap = jo.getString(konfigurasi.TAG_NAMA_LENGKAP);

                HashMap<String, String> buku = new HashMap<>();
                buku.put(konfigurasi.TAG_ID_TANAH, id_tanah);
                buku.put(konfigurasi.TAG_NAMA_LENGKAP, nama_lengkap);
                list.add(buku);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ListAdapter adapter = new SimpleAdapter(
                TampilSemuaTanah.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_ID_TANAH, konfigurasi.TAG_NAMA_LENGKAP},
                new int[]{R.id.id, R.id.name});
        listView.setAdapter(adapter);

    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaTanah.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showTanah();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_TANAH);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }




    private void callData() {
        listData.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_data, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataModelTanah item = new DataModelTanah();

                        item.setId_tanah(obj.getString(TAG_ID));
                        item.setNama_lengkap(obj.getString(TAG_NAMA));

                        listData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TampilSemuaTanah.this, error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }








    @Override
    public void onRefresh() {
     /*showTanah();*/
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.type_name));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void cariData(final String keyword) {
        pDialog = new ProgressDialog(TampilSemuaTanah.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModelTanah data = new DataModelTanah();

                            data.setId_tanah(obj.getString(TAG_ID));
                            data.setNama_lengkap(obj.getString(TAG_NAMA));

                            listData.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onClick(View v) {
        if (v == imageView) {
            Intent intent = new Intent(TampilSemuaTanah.this, Administrasi.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilTanah.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String tanahId = map.get(konfigurasi.TAG_ID_TANAH).toString();
        intent.putExtra(konfigurasi.TANAH_ID, tanahId);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

