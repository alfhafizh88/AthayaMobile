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

public class TampilSemuaBuku extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener {

    private ImageView imageView;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_buku);
        listView = (ListView) findViewById(R.id.listView);
        imageView=(ImageView)findViewById(R.id.imgClose);
        listView.setOnItemClickListener(this);
        imageView.setOnClickListener(this);
        getJSON();
    }
    private void showBuku() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY2);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_buku = jo.getString(konfigurasi.TAG_ID_BUKU);
                String nama_buku = jo.getString(konfigurasi.TAG_NAMA_BUKU);

                HashMap<String,String> buku = new HashMap<>();
                buku.put(konfigurasi.TAG_ID_BUKU,id_buku);
                buku.put(konfigurasi.TAG_NAMA_BUKU,nama_buku);
                list.add(buku);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaBuku.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_ID_BUKU,konfigurasi.TAG_NAMA_BUKU},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaBuku.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showBuku();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_BUKU);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilBuku.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String bukuId = map.get(konfigurasi.TAG_ID_BUKU).toString();
        intent.putExtra(konfigurasi.BUKU_ID,bukuId);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view == imageView){
            Intent intent= new Intent(TampilSemuaBuku.this, MasterBuku.class);
            startActivity(intent);
        }
    }
}
