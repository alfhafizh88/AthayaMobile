package com.kodingindonesia.mycrud.datamaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilSemuaJbt extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener{

    private ImageView imageView;
    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_jbt);
        listView = (ListView) findViewById(R.id.listView);
        imageView=(ImageView)findViewById(R.id.imgClose);
        listView.setOnItemClickListener(this);
        imageView.setOnClickListener(this);
        getJSON();
    }
    private void showJabatan() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY4);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_jabatan = jo.getString(konfigurasi.TAG_ID_JABATAN);
                String nama_jab = jo.getString(konfigurasi.TAG_NAMA_JABATAN);

                HashMap<String,String> jabatan = new HashMap<>();
                jabatan.put(konfigurasi.TAG_ID_JABATAN,id_jabatan);
                jabatan.put(konfigurasi.TAG_NAMA_JABATAN,nama_jab);
                list.add(jabatan);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaJbt.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_ID_JABATAN,konfigurasi.TAG_NAMA_JABATAN},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }
    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaJbt.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showJabatan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_JABATAN);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onClick(View v) {
        if(v== imageView){
            Intent intent= new Intent(TampilSemuaJbt.this, MasterJabatan.class);
            startActivity(intent);
        }   
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, TampilJabatan.class);
        HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
        String jabatanId = map.get(konfigurasi.TAG_ID_JABATAN).toString();
        intent.putExtra(konfigurasi.JABATAN_ID,jabatanId);
        startActivity(intent);
    }
}
