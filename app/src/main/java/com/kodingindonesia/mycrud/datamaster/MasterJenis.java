package com.kodingindonesia.mycrud.datamaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.util.HashMap;

public class MasterJenis extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNama;
    private EditText edtKet;
    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_jenis);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtKet= (EditText) findViewById(R.id.edtKet);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        imageView=(ImageView)findViewById(R.id.imgClose);
        imageView.setOnClickListener(this);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    private void addJenis() {
        final String nama_jenis = edtNama.getText().toString().trim();
        final String ket_jenis = edtKet.getText().toString().trim();
        class AddKelas extends AsyncTask<Void,Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MasterJenis.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MasterJenis.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_JENIS_NAMA,nama_jenis);
                params.put(konfigurasi.KEY_JENIS_KET,ket_jenis);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_JENIS, params);
                return res;
            }
        }
        AddKelas addKelas = new AddKelas();
        addKelas.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if (edtNama.getText().toString().length()==0){
                edtNama.setError("Nama Jenis diperlukan!");
            }else{
                addJenis();
            }
        }
        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaJenis.class));
        }
        if(v == imageView){
            Intent intent= new Intent(MasterJenis.this, DataMaster.class);
            startActivity(intent);
        }
    }
}

