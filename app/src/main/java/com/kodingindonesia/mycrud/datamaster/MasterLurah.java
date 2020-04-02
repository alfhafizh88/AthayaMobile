package com.kodingindonesia.mycrud.datamaster;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.konfigurasi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MasterLurah extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNama, edtNIP, edtKet, edtJbt;


    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private DatePicker dpMasuk;
    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageView;
    private Spinner spinner;
    private ArrayList<String> categoriesList;
    List<String>valueIdJabatan=new ArrayList<>();
    List<String>valueNamaJabatan=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_lurah);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtKet= (EditText) findViewById(R.id.edtKet);
        edtNIP= (EditText) findViewById(R.id.edtNIP);
        edtJbt= (EditText) findViewById(R.id.edtJbt);
        /*dpMasuk=(DatePicker)findViewById(R.id.tgl_masuk);*/

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        /*spinner=(Spinner)findViewById(R.id.spinner1);*/
        categoriesList = new ArrayList<String>();

        imageView=(ImageView)findViewById(R.id.imgClose);
        imageView.setOnClickListener(this);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        /*spinner.setOnItemSelectedListener(this);*/
    }

    private void addLurah() {
        final String nip = edtNIP.getText().toString().trim();
        final String nama_lurah = edtNama.getText().toString().trim();
        final String jabatan = edtJbt.getText().toString().trim();
        final String ket_lurah = edtKet.getText().toString().trim();
        final String tgl_masuk = txtDate.getText().toString().trim();

        class AddLurah extends AsyncTask<Void,Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MasterLurah.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

/*
                // Membuat adapter untuk spinner
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MasterLurah.this,
                        android.R.layout.simple_spinner_item, valueNamaJabatan);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Mengaitkan adapter spinner dengan spinner yang ada di layout
                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String idJabatan=valueIdJabatan.get(i);
                        String namaJabatan=valueNamaJabatan.get(i);
                        Toast.makeText(MasterLurah.this, "Anda Memilih ID JABATAN: "+idJabatan+", Nama: "+namaJabatan, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
*/

                Toast.makeText(MasterLurah.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_LURAH_ID,nip);
                params.put(konfigurasi.KEY_LURAH_NAMA,nama_lurah);
                params.put(konfigurasi.KEY_LURAH_JABATAN,jabatan);
                params.put(konfigurasi.KEY_LURAH_MASUK,tgl_masuk);
                params.put(konfigurasi.KEY_LURAH_KET,ket_lurah);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_LURAH, params);
                return res;

            }
        }
        AddLurah addLurah = new AddLurah();
        addLurah.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if (edtNama.getText().toString().length()==0){
                edtNama.setError("Nama Lurah diperlukan!");
            }else if(edtNIP.getText().toString().length()==0){
                edtNIP.setError("NIP diperlukan!");
            }else if(txtDate.getText().toString().length()==0){
                edtNIP.setError("Tanggal Masuk diperlukan!");
            }else{
                addLurah();
            }
        }
        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaLurah.class));
        }
        if(v == imageView){
            Intent intent= new Intent(MasterLurah.this, DataMaster.class);
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

                            txtDate.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }


}
