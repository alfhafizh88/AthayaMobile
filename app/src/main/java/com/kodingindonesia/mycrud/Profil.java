package com.kodingindonesia.mycrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kodingindonesia.mycrud.datamaster.TampilAkun;
import com.kodingindonesia.mycrud.datamaster.TampilSemuaAkun;
import com.kodingindonesia.mycrud.datamaster.TampilSemuaKelas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Profil extends AppCompatActivity implements View.OnClickListener {

    private TextView txtNama, txtUsername, txtEmail, txtTlp, txtAlamat, txtKet;
    private Button btnKembali;
    private String username;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Intent intent = getIntent();
        username = intent.getStringExtra(konfigurasi.AKUN_ID);

        txtUsername=(TextView)findViewById(R.id.txtUsername);
        txtNama=(TextView)findViewById(R.id.txtNama);
        txtEmail=(TextView)findViewById(R.id.txtEmail);
        txtTlp=(TextView)findViewById(R.id.txtTlp);
        txtAlamat=(TextView)findViewById(R.id.txtAlamat2);
        /*txtKet=(TextView)findViewById(R.id.txtKet);*/

        btnKembali=(Button)findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        Intent intent= new Intent(Profil.this, Dashboard.class);
        startActivity(intent);
    }
}
