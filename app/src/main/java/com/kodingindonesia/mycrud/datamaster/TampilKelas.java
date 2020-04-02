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

public class TampilKelas extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextId;
    private EditText editTextNama;
    private EditText editTextKet;

    private ImageView imageView;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_kelas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_kelas);

        Intent intent = getIntent();
        id_kelas = intent.getStringExtra(konfigurasi.KELAS_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextKet = (EditText) findViewById(R.id.editTextKet);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);


        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        editTextId.setText(id_kelas);

        getKelas();
    }
    private void getKelas() {
        class GetKelas extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilKelas.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_KELAS,id_kelas);
                return s;
            }
        }

        GetKelas ge = new GetKelas();
        ge.execute();
    }
    private void showBuku(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY3);
            JSONObject c = result.getJSONObject(0);
            String nama_kelas = c.getString(konfigurasi.TAG_NAMA_KELAS);
            String ket_kelas= c.getString(konfigurasi.TAG_KET_KELAS);

            editTextNama.setText(nama_kelas);
            editTextKet.setText(ket_kelas);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateKelas(){
        final String nama_kelas = editTextNama.getText().toString().trim();
        final String ket_kelas = editTextKet.getText().toString().trim();


        class UpdateBuku extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilKelas.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilKelas.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_KELAS_ID,id_kelas);
                hashMap.put(konfigurasi.KEY_KELAS_NAMA,nama_kelas);
                hashMap.put(konfigurasi.KEY_KELAS_KET,ket_kelas);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_KELAS,hashMap);
                return s;
            }
        }

        UpdateBuku updateKelas = new UpdateBuku();
        updateKelas.execute();
    }
    private void deleteBuku(){
        class DeleteKelas extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilKelas.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilKelas.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_KELAS, id_kelas);
                return s;
            }
        }

        DeleteKelas de = new DeleteKelas();
        de.execute();
    }

    private void confirmDeleteKelas(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Kelas ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteBuku();
                        startActivity(new Intent(TampilKelas.this,TampilSemuaKelas.class));
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
                editTextNama.setError("Nama Kelas diperlukan!");
            }else{
                updateKelas();
            }
        }if(v == buttonDelete){
            confirmDeleteKelas();
        }if(v == imageView){
            Intent intent= new Intent(TampilKelas.this, TampilSemuaKelas.class);
            startActivity(intent);
        }
    }
}
