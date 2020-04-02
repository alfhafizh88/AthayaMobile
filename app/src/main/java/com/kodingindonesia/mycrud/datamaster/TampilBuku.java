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

public class TampilBuku extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextId;
    private EditText editTextNama;
    private EditText editTextMulai;
    private EditText editTextSelesai;
    private EditText editTextKet;

    private ImageView imageView;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_buku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_buku);
        Intent intent = getIntent();

        id_buku = intent.getStringExtra(konfigurasi.BUKU_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextMulai = (EditText) findViewById(R.id.editTextMulai);
        editTextSelesai = (EditText) findViewById(R.id.editTextSelesai);
        editTextKet = (EditText) findViewById(R.id.editTextKet);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);


        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        editTextId.setText(id_buku);

        getBuku();
    }
    private void getBuku() {
        class GetBuku extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilBuku.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_BUKU,id_buku);
                return s;
            }
        }

        GetBuku ge = new GetBuku();
        ge.execute();
    }
    private void showBuku(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY2);
            JSONObject c = result.getJSONObject(0);
            String nama_buku = c.getString(konfigurasi.TAG_NAMA_BUKU);
            String mulai_buku = c.getString(konfigurasi.TAG_MULAI_BUKU);
            String selesai_buku = c.getString(konfigurasi.TAG_SELESAI_BUKU);
            String ket_buku= c.getString(konfigurasi.TAG_KET_BUKU);

            editTextNama.setText(nama_buku);
            editTextMulai.setText(mulai_buku);
            editTextSelesai.setText(selesai_buku);
            editTextKet.setText(ket_buku);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateBuku(){
        final String nama_buku = editTextNama.getText().toString().trim();
        final String mulai_buku = editTextMulai.getText().toString().trim();
        final String selesai_buku = editTextSelesai.getText().toString().trim();
        final String ket_buku = editTextKet.getText().toString().trim();


        class UpdateBuku extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilBuku.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilBuku.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_BUKU_ID,id_buku);
                hashMap.put(konfigurasi.KEY_BUKU_NAMA,nama_buku);
                hashMap.put(konfigurasi.KEY_BUKU_MULAI,mulai_buku);
                hashMap.put(konfigurasi.KEY_BUKU_SELESAI,selesai_buku);
                hashMap.put(konfigurasi.KEY_BUKU_KET,ket_buku);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_BUKU,hashMap);
                return s;
            }
        }

        UpdateBuku updateBuku = new UpdateBuku();
        updateBuku.execute();
    }

    private void deleteBuku(){
        class DeleteBuku extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilBuku.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilBuku.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_BUKU, id_buku);
                return s;
            }
        }

        DeleteBuku de = new DeleteBuku();
        de.execute();
    }
    private void confirmDeleteBuku(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Buku ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteBuku();
                        startActivity(new Intent(TampilBuku.this,TampilSemuaBuku.class));
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
                editTextNama.setError("Nama diperlukan!");
            }else if(editTextMulai.getText().toString().length()==0){
                editTextMulai.setError("Nomor mulai diperlukan!");
            }else if(editTextSelesai.getText().toString().length()==0){
                editTextSelesai.setError("Nomor selesai diperlukan!");
            }else{
                updateBuku();
            }

        }

        if(v == buttonDelete){
            confirmDeleteBuku();
        }
        if(v == imageView){
            Intent intent= new Intent(TampilBuku.this, TampilSemuaBuku.class);
            startActivity(intent);
        }
    }
}
