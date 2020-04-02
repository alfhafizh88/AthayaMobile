package com.kodingindonesia.mycrud.datamaster;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class TampilJenis extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextId;
    private EditText editTextNama;
    private EditText editTextKet;

    private ImageView imageView;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_jenis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_jenis);

        Intent intent = getIntent();
        id_jenis = intent.getStringExtra(konfigurasi.JENIS_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextKet = (EditText) findViewById(R.id.editTextKet);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);


        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        editTextId.setText(id_jenis);

        getJenis();
    }
    private void getJenis() {
        class GetJenis extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilJenis.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showJenis(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_JENIS,id_jenis);
                return s;
            }
        }

        GetJenis ge = new GetJenis();
        ge.execute();
    }
    private void showJenis(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY7);
            JSONObject c = result.getJSONObject(0);
            String nama_jenis = c.getString(konfigurasi.TAG_NAMA_JENIS);
            String ket_jenis= c.getString(konfigurasi.TAG_KET_JENIS);

            editTextNama.setText(nama_jenis);
            editTextKet.setText(ket_jenis);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateJenis(){
        final String nama_jenis = editTextNama.getText().toString().trim();
        final String ket_jenis = editTextKet.getText().toString().trim();


        class UpdateBuku extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilJenis.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilJenis.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_JENIS_ID,id_jenis);
                hashMap.put(konfigurasi.KEY_JENIS_NAMA,nama_jenis);
                hashMap.put(konfigurasi.KEY_JENIS_KET,ket_jenis);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_JENIS,hashMap);
                return s;
            }
        }

        UpdateBuku updateJenis = new UpdateBuku();
        updateJenis.execute();
    }
    private void deleteJenis(){
        class DeleteJenis extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilJenis.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilJenis.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_JENIS, id_jenis);
                return s;
            }
        }

        DeleteJenis de = new DeleteJenis();
        de.execute();
    }

    private void confirmDeleteJenis(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Jenis ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteJenis();
                        startActivity(new Intent(TampilJenis.this,TampilSemuaJenis.class));
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
                editTextNama.setError("Nama Jenis diperlukan!");
            }else{
                updateJenis();
            }
        }if(v == buttonDelete){
            confirmDeleteJenis();
        }if(v == imageView){
            Intent intent= new Intent(TampilJenis.this, TampilSemuaJenis.class);
            startActivity(intent);
        }
    }
}
