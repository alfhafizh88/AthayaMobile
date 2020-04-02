package com.kodingindonesia.mycrud.datamaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.kodingindonesia.mycrud.R;

public class DataMaster extends AppCompatActivity implements View.OnClickListener {
    CardView bukuCard, jenisCard, kelasCard, jabatanCard, lurahCard, akunCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_master);
        bukuCard=(CardView)findViewById(R.id.bukucardId);
        kelasCard=(CardView)findViewById(R.id.kelascardId);
        jabatanCard=(CardView)findViewById(R.id.jabatancardId);
        lurahCard=(CardView)findViewById(R.id.lurahcardId);
        jenisCard=(CardView)findViewById(R.id.jeniscardId);
        akunCard=(CardView)findViewById(R.id.akuncardId);

        bukuCard.setOnClickListener(this);
        kelasCard.setOnClickListener(this);
        jabatanCard.setOnClickListener(this);
        lurahCard.setOnClickListener(this);
        jenisCard.setOnClickListener(this);
        akunCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.bukucardId: i  = new Intent(this, MasterBuku.class);startActivity(i);break;
            case R.id.kelascardId: i  = new Intent(this, MasterKelas.class);startActivity(i);break;
            case R.id.jabatancardId: i  = new Intent(this, MasterJabatan.class);startActivity(i);break;
            case R.id.lurahcardId: i  = new Intent(this, MasterLurah.class);startActivity(i);break;
            case R.id.jeniscardId: i  = new Intent(this, MasterJenis.class);startActivity(i);break;
            case R.id.akuncardId: i  = new Intent(this, MasterAkun.class);startActivity(i);break;
            default:break;
        }
    }
}
