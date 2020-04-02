package com.kodingindonesia.mycrud.datamaster;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class TampilLurah extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextId, editTextNama, editTextKet, editTextJabatan;

    private ImageView imageView;

    Button btnDatePicker, btnDatePicker2;
    EditText txtDate,txtDate2;
    private int mYear, mMonth, mDay;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String nip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_lurah);
        Intent intent = getIntent();

        nip = intent.getStringExtra(konfigurasi.LURAH_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextJabatan= (EditText) findViewById(R.id.editTextJabatan);
        editTextKet = (EditText) findViewById(R.id.editTextKet);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnDatePicker2=(Button)findViewById(R.id.btn_date2);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtDate2=(EditText)findViewById(R.id.out_date);

        btnDatePicker.setOnClickListener(this);
        btnDatePicker2.setOnClickListener(this);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        editTextId.setText(nip);

        getLurah();

    }
    private void getLurah() {
        class GetLurah extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilLurah.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showLurah(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_LURAH,nip);
                return s;
            }
        }

        GetLurah ge = new GetLurah();
        ge.execute();
    }
    private void showLurah(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY5);
            JSONObject c = result.getJSONObject(0);
            String nama_lurah = c.getString(konfigurasi.TAG_NAMA_LURAH);
            String nama_jab= c.getString(konfigurasi.TAG_JABATAN_LURAH);
            String tgl_masuk = c.getString(konfigurasi.TAG_MASUK_LURAH);
            String tgl_keluar= c.getString(konfigurasi.TAG_KELUAR_LURAH);
            String ket_lurah= c.getString(konfigurasi.TAG_KET_LURAH);

            editTextNama.setText(nama_lurah);
            editTextJabatan.setText(nama_jab);
            txtDate.setText(tgl_masuk);
            txtDate2.setText(tgl_keluar);
            editTextKet.setText(ket_lurah);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateLurah(){
        final String nama_lurah = editTextNama.getText().toString().trim();
        final String nama_jab = editTextJabatan.getText().toString().trim();
        final String ket_lurah = editTextKet.getText().toString().trim();
        final String tgl_masuk = txtDate.getText().toString().trim();
        final String tgl_keluar = txtDate2.getText().toString().trim();


        class UpdateLurah extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilLurah.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilLurah.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_LURAH_ID,nip);
                hashMap.put(konfigurasi.KEY_LURAH_NAMA,nama_lurah);
                hashMap.put(konfigurasi.KEY_LURAH_JABATAN,nama_jab );
                hashMap.put(konfigurasi.KEY_LURAH_MASUK,tgl_masuk);
                hashMap.put(konfigurasi.KEY_LURAH_KELUAR,tgl_keluar);
                hashMap.put(konfigurasi.KEY_LURAH_KET,ket_lurah);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_LURAH,hashMap);
                return s;
            }
        }

        UpdateLurah updateLurah = new UpdateLurah();
        updateLurah.execute();
    }
    private void deleteLurah(){
        class DeleteKelas extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilLurah.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilLurah.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_LURAH, nip);
                return s;
            }
        }

        DeleteKelas de = new DeleteKelas();
        de.execute();
    }

    private void confirmDeleteLurah(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Lurah ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteLurah();
                        startActivity(new Intent(TampilLurah.this,TampilSemuaLurah.class));
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
                editTextNama.setError("Nama Lurah diperlukan!");
            }else{
                updateLurah();
            }
        }if(v == buttonDelete){
            confirmDeleteLurah();
        }if(v == imageView){
            Intent intent= new Intent(TampilLurah.this, TampilSemuaLurah.class);
            startActivity(intent);
        }if(v == btnDatePicker){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth );

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }if(v == btnDatePicker2){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate2.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth );

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
