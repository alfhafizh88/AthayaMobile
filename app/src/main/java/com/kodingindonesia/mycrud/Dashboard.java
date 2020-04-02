package com.kodingindonesia.mycrud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kodingindonesia.mycrud.datamaster.Administrasi;
import com.kodingindonesia.mycrud.datamaster.DataMaster;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{
    private SessionHandler session;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    CardView masterCard, tanahCard, laporanCard, profilCard, logoutCard, cariCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        txt_id.setText(" "+id);
        txt_username.setText(" " + username);

        masterCard=(CardView)findViewById(R.id.mastercardId);
        tanahCard=(CardView)findViewById(R.id.tanahcardId);
        /*laporanCard=(CardView)findViewById(R.id.laporancard);*/
        profilCard=(CardView)findViewById(R.id.profilcard);
        /*cariCard=(CardView)findViewById(R.id.caricardId);*/
        logoutCard=(CardView)findViewById(R.id.logutcard);
    //Add Click listener to the cards
        masterCard.setOnClickListener(this);
        tanahCard.setOnClickListener(this);
        /*laporanCard.setOnClickListener(this);*/
        profilCard.setOnClickListener(this);
        /*cariCard.setOnClickListener(this);*/

        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(Dashboard.this, Login.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
    Intent i;
        switch (view.getId()){
            case R.id.mastercardId: i  = new Intent(this, DataMaster.class);startActivity(i);break;
            case R.id.tanahcardId: i  = new Intent(this, Administrasi.class);startActivity(i);break;
            /*case R.id.laporancard: i  = new Intent(this, Laporan.class);startActivity(i);break;*/
            case R.id.profilcard: i  = new Intent(this, Profil.class);startActivity(i);break;
            /*case R.id.caricardId: i  = new Intent(this, Pencarian.class);startActivity(i);break;*/

            default:break;
        }
    }
}
