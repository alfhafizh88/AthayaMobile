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


public class TampilAkun extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextId, editTextNama, editTextEmail, editTextAlamat, editTextTlp, editTextStatus, editTextKet;

    private ImageView imageView;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_akun);

        Intent intent = getIntent();
        username = intent.getStringExtra(konfigurasi.AKUN_ID);

        editTextId = (EditText) findViewById(R.id.edtUsername);

        editTextNama = (EditText) findViewById(R.id.edtNama);
        editTextEmail = (EditText) findViewById(R.id.edtEmail);
        editTextAlamat= (EditText) findViewById(R.id.edtAlamat);
        editTextTlp= (EditText) findViewById(R.id.edtTelp);
        editTextStatus= (EditText) findViewById(R.id.edtStatus);
        editTextKet = (EditText) findViewById(R.id.edtKet);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);


        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        editTextId.setText(username);

        getAkun();
    }

    private void getAkun() {
        class GetAkun extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilAkun.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showAkun(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_AKUN,username);
                return s;
            }
        }

        GetAkun ge = new GetAkun();
        ge.execute();
    }
    private void showAkun(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY8);
            JSONObject c = result.getJSONObject(0);
            /*String username= c.getString(konfigurasi.TAG_ID_AKUN);*/
            String nama_akun = c.getString(konfigurasi.TAG_NAMA_AKUN);
            String email = c.getString(konfigurasi.TAG_EMAIL_AKUN);
            String alamat= c.getString(konfigurasi.TAG_ALAMAT_AKUN);
            String telepon= c.getString(konfigurasi.TAG_TELEPON_AKUN);
            String status= c.getString(konfigurasi.TAG_STATUS_AKUN);
            String ket= c.getString(konfigurasi.TAG_KET_AKUN);

            /*editTextId.setText(username);*/
            editTextNama.setText(nama_akun);
            editTextEmail.setText(email);
            editTextAlamat.setText(alamat);
            editTextTlp.setText(telepon);
            editTextStatus.setText(status);
            editTextKet.setText(ket);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateAkun(){
        final String username = editTextId.getText().toString().trim();
        final String nama_akun = editTextNama.getText().toString().trim();
        final String email= editTextEmail.getText().toString().trim();
        final String alamat= editTextAlamat.getText().toString().trim();
        final String telepon= editTextTlp.getText().toString().trim();
        final String status= editTextStatus.getText().toString().trim();
        final String ket = editTextKet.getText().toString().trim();


        class UpdateAkun extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilAkun.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilAkun.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_AKUN_ID,username);
                hashMap.put(konfigurasi.KEY_AKUN_NAMA,nama_akun);
                hashMap.put(konfigurasi.KEY_AKUN_EMAIL,email);
                hashMap.put(konfigurasi.KEY_AKUN_ALAAMAT,alamat);
                hashMap.put(konfigurasi.KEY_AKUN_TLP,telepon);
                hashMap.put(konfigurasi.KEY_AKUN_STATUS,status);
                hashMap.put(konfigurasi.KEY_AKUN_KET,ket);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_AKUN,hashMap);
                return s;
            }
        }

        UpdateAkun updateAkun = new UpdateAkun();
        updateAkun.execute();
    }
    private void deleteAkun(){
        class DeleteAkun extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilAkun.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilAkun.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_AKUN, username);
                return s;
            }
        }

        DeleteAkun de = new DeleteAkun();
        de.execute();
    }

    private void confirmDeleteAkun(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Akun ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteAkun();
                        startActivity(new Intent(TampilAkun.this,TampilSemuaAkun.class));
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
                editTextNama.setError("Nama Akun diperlukan!");
            }else{
                updateAkun();
            }
        }if(v == buttonDelete){
            confirmDeleteAkun();
        }if(v == imageView){
            Intent intent= new Intent(TampilAkun.this, TampilSemuaAkun.class);
            startActivity(intent);
        }
    }
}
