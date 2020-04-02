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

public class MasterBuku extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNama;
    private EditText edtMulai;
    private EditText edtSelesai;
    private EditText edtKet;
    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_buku);
       
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtMulai = (EditText) findViewById(R.id.edtMulai);
        edtSelesai = (EditText) findViewById(R.id.edtSelesai);
        edtKet= (EditText) findViewById(R.id.edtKet);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        imageView=(ImageView)findViewById(R.id.imgClose);
        imageView.setOnClickListener(this);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }
    private void addBuku(){
        final String nama_buku = edtNama.getText().toString().trim();
        final String mulai_buku = edtMulai.getText().toString().trim();
        final String selesai_buku = edtSelesai.getText().toString().trim();
        final String ket_buku = edtKet.getText().toString().trim();

        class AddBuku extends AsyncTask<Void,Void, String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MasterBuku.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MasterBuku.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_BUKU_NAMA,nama_buku);
                params.put(konfigurasi.KEY_BUKU_MULAI,mulai_buku);
                params.put(konfigurasi.KEY_BUKU_SELESAI,selesai_buku);
                params.put(konfigurasi.KEY_BUKU_KET,ket_buku);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_BUKU, params);
                /*Log.e("Response: ", "> " + res);*/
                return res;
            }
        }
    AddBuku addBuku = new AddBuku();
        addBuku.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if (edtNama.getText().toString().length()==0){
              edtNama.setError("Nama diperlukan!");
            }else if(edtMulai.getText().toString().length()==0){
                edtMulai.setError("Nomor mulai diperlukan!");
            }else if(edtSelesai.getText().toString().length()==0){
                edtSelesai.setError("Nomor selesai diperlukan!");
            }else{
                addBuku();
            }
        }
        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaBuku.class));
        }
        if(v == imageView){
            Intent intent= new Intent(MasterBuku.this, DataMaster.class);
            startActivity(intent);
        }
    }
}
