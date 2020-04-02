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

public class TampilSemuaLurah extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener{

    private ImageView imageView;
    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_lurah);
        listView = (ListView) findViewById(R.id.listView);
        imageView=(ImageView)findViewById(R.id.imgClose);
        listView.setOnItemClickListener(this);
        imageView.setOnClickListener(this);
        getJSON();
    }
    private void showKelas() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY5);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String nip = jo.getString(konfigurasi.TAG_ID_LURAH);
                String nama_lurah = jo.getString(konfigurasi.TAG_NAMA_LURAH);

                HashMap<String,String> lurah = new HashMap<>();
                lurah.put(konfigurasi.TAG_ID_LURAH,nip);
                lurah.put(konfigurasi.TAG_NAMA_LURAH,nama_lurah);
                list.add(lurah);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaLurah.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_ID_LURAH,konfigurasi.TAG_NAMA_LURAH},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }
    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaLurah.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showKelas();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_LURAH);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onClick(View v) {
        if(v== imageView){
            Intent intent= new Intent(TampilSemuaLurah.this, MasterLurah.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, TampilLurah.class);
        HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
        String lurahId = map.get(konfigurasi.TAG_ID_LURAH).toString();
        intent.putExtra(konfigurasi.LURAH_ID,lurahId);
        startActivity(intent);
    }
}
