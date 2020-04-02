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

public class MasterJabatan extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNama;
    private EditText edtKet;
    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_jabatan);
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
    private void addJabatan() {
        final String nama_jab = edtNama.getText().toString().trim();
        final String ket = edtKet.getText().toString().trim();
        class AddJabatan extends AsyncTask<Void,Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MasterJabatan.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MasterJabatan.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_JABATAN_NAMA,nama_jab);
                params.put(konfigurasi.KEY_JABATAN_KET,ket);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_JABATAN, params);
                return res;
            }
        }
        AddJabatan addJabatan = new AddJabatan();
        addJabatan.execute();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if (edtNama.getText().toString().length()==0){
                edtNama.setError("Nama Jabatan diperlukan!");
            }else{
                addJabatan();
            }
        }
        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaJbt.class));
        }
        if(v == imageView){
            Intent intent= new Intent(MasterJabatan.this, DataMaster.class);
            startActivity(intent);
        }
    }
}
