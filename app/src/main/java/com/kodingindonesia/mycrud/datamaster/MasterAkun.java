package com.kodingindonesia.mycrud.datamaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.konfigurasi;

import java.util.HashMap;


public class MasterAkun extends AppCompatActivity implements View.OnClickListener {

    private EditText  edtUsername, edtNama, edtEmail, edtAlamat, edtTlp, edtPass, edtStatus, edtKet;
    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_akun);
        edtUsername= (EditText) findViewById(R.id.edtUsername);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtEmail= (EditText) findViewById(R.id.edtEmail);
        edtAlamat= (EditText) findViewById(R.id.edtAlamat);
        edtTlp= (EditText) findViewById(R.id.edtTelp);
        edtPass= (EditText) findViewById(R.id.edtPass);
        edtStatus= (EditText) findViewById(R.id.edtStatus);
        edtKet= (EditText) findViewById(R.id.edtKet);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        imageView=(ImageView)findViewById(R.id.imgClose);
        imageView.setOnClickListener(this);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);

        setupFloatingLabelError();
    }

    private void addAkun(){
        final String username = edtUsername.getText().toString().trim();
        final String nama_akun = edtNama.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String alamat = edtAlamat.getText().toString().trim();
        final String telepon= edtTlp.getText().toString().trim();
        final String password = edtPass.getText().toString().trim();
        final String status= edtStatus.getText().toString().trim();
        final String ket = edtKet.getText().toString().trim();

        class AddAkun extends AsyncTask<Void,Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MasterAkun.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MasterAkun.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_AKUN_ID,username);
                params.put(konfigurasi.KEY_AKUN_NAMA,nama_akun);
                params.put(konfigurasi.KEY_AKUN_EMAIL,email);
                params.put(konfigurasi.KEY_AKUN_ALAAMAT,alamat);
                params.put(konfigurasi.KEY_AKUN_TLP,telepon);
                params.put(konfigurasi.KEY_AKUN_PASS,password);
                params.put(konfigurasi.KEY_AKUN_STATUS,status);
                params.put(konfigurasi.KEY_AKUN_KET,ket);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_AKUN, params);
                /*Log.e("Response: ", "> " + res);*/
                return res;
            }
        }
        AddAkun addAkun = new AddAkun();
        addAkun.execute();
    }



    private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    floatingUsernameLabel.setError(getString(R.string.username));
                    floatingUsernameLabel.setErrorEnabled(true);
                } else {
                    floatingUsernameLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if (edtUsername.getText().toString().length()==0){
                edtUsername.setError("Username diperlukan!");
            }else if(edtNama.getText().toString().length()==0){
                edtNama.setError("Nama lengkap diperlukan!");
            }else if(edtEmail.getText().toString().length()==0){
                edtEmail.setError("Email diperlukan!");
            }else{
                addAkun();
            }
        }
        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaAkun.class));
        }
        if(v == imageView){
            Intent intent= new Intent(MasterAkun.this, DataMaster.class);
            startActivity(intent);
        }
    }
}
