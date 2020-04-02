package com.kodingindonesia.mycrud.datamaster;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilJabatan extends AppCompatActivity implements OnClickListener{
    private EditText editTextId;
    private EditText editTextNama;
    private EditText editTextKet;

    private ImageView imageView;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_jabatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_jabatan);
        Intent intent = getIntent();

        id_jabatan = intent.getStringExtra(konfigurasi.JABATAN_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextKet = (EditText) findViewById(R.id.editTextKet);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);


        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        editTextId.setText(id_jabatan);

        getJabatan();
    }
    private void getJabatan() {
        class GetJabatan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilJabatan.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showBuku(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_JABATAN,id_jabatan);
                return s;
            }
        }

        GetJabatan ge = new GetJabatan();
        ge.execute();
    }
    private void showBuku(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY4);
            JSONObject c = result.getJSONObject(0);
            String nama_jab = c.getString(konfigurasi.TAG_NAMA_JABATAN);
            String ket_jabatan= c.getString(konfigurasi.TAG_KET_JABATAN);

            editTextNama.setText(nama_jab);
            editTextKet.setText(ket_jabatan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateJabatan(){
        final String nama_jab = editTextNama.getText().toString().trim();
        final String ket_jabatan = editTextKet.getText().toString().trim();
    class UpdateJabatan extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(TampilJabatan.this,"Updating...","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(TampilJabatan.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(konfigurasi.KEY_JABATAN_ID,id_jabatan);
            hashMap.put(konfigurasi.KEY_JABATAN_NAMA,nama_jab);
            hashMap.put(konfigurasi.KEY_JABATAN_KET,ket_jabatan);

            RequestHandler rh = new RequestHandler();
            String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_JABATAN,hashMap);
            return s;
        }
    }

    UpdateJabatan updateJabatan = new UpdateJabatan();
        updateJabatan.execute();
}
    private void deleteKelas(){
        class DeleteJabatan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilJabatan.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilJabatan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_JABATAN, id_jabatan);
                return s;
            }
        }

        DeleteJabatan de = new DeleteJabatan();
        de.execute();
    }
    private void confirmDeleteJabatan(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Jabatan ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteKelas();
                        startActivity(new Intent(TampilJabatan.this,TampilSemuaJbt.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            if (editTextNama.getText().toString().length()==0){
                editTextNama.setError("Nama Jabatan diperlukan!");
            }else{
                updateJabatan();
            }

        }

        if(v == buttonDelete){
            confirmDeleteJabatan();
        }
        if(v == imageView){
            Intent intent= new Intent(TampilJabatan.this, TampilSemuaJbt.class);
            startActivity(intent);
        }

    }
}
