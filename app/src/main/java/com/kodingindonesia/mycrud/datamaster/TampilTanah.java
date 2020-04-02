package com.kodingindonesia.mycrud.datamaster;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TampilTanah extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText editTextId, editTextNama, editTextKet, editTextAlamat, editTextPersil, editTextJT, editTextSJT, editTextLuas, editTextDate, editTextSisa, edtBuku, edtKohir, edtNama,edtJt, edtSJT, edtNotaris, edtNomor, edtLuas,edtDate, editTextKelas, edtKet   ;

    private ImageView imageView, imageDoc;
    private Bitmap bitmaps;

    public static final String UPLOAD_KEY = "foto";
    Button btnDatePicker, btnDatePicker2, buttonChoose;
    EditText txtDate, txtDate2;
    private  int PICK_IMAGE_REQUEST=1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private int mYear, mMonth, mDay;
    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageViewDoc;
    private Bitmap bitmap;
    private Uri filePath;
    private Button buttonUpdate;
    private Button buttonDelete;


    private String id_tanah;

    private ListView listView;
    private String JSON_STRING;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_tanah);
        Intent intent = getIntent();
        id_tanah= intent.getStringExtra(konfigurasi.TANAH_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAlamat= (EditText) findViewById(R.id.editTextAlamat);
        editTextPersil= (EditText) findViewById(R.id.editTextPersil);
        editTextJT= (EditText) findViewById(R.id.editTextJT);
        editTextSJT= (EditText) findViewById(R.id.editTextSJT);
        editTextLuas= (EditText) findViewById(R.id.editTextLuas);
        editTextKelas= (EditText) findViewById(R.id.editTextKelas);
        editTextDate= (EditText) findViewById(R.id.in_date1);
        editTextKet = (EditText) findViewById(R.id.editTextKet);
        edtBuku= (EditText) findViewById(R.id.edtBuku);
        edtKohir= (EditText) findViewById(R.id.edtKohir);
        edtNama= (EditText) findViewById(R.id.edtNama);
        edtJt= (EditText) findViewById(R.id.edtJT);
        edtSJT= (EditText) findViewById(R.id.edtSJT);
        edtNotaris= (EditText) findViewById(R.id.edtNotaris);
        edtNomor= (EditText) findViewById(R.id.edtNomor);
        edtLuas= (EditText) findViewById(R.id.edtLuas);
        edtDate= (EditText) findViewById(R.id.in_date);
        edtKet= (EditText) findViewById(R.id.edtKet);
        buttonChoose=(Button)findViewById(R.id.btn_choose);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        btnDatePicker2=(Button)findViewById(R.id.btn_date2);
        txtDate=(EditText)findViewById(R.id.in_date);


        btnDatePicker.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageView=(ImageView)findViewById(R.id.imgClose);
        imageViewDoc=(ImageView)findViewById(R.id.imageViewDoc);


        /*buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);*/

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(this);
        imageView.setOnClickListener(this);
        editTextId.setText(id_tanah);
        getBuku();
        getJSON();

    }
    private void getBuku() {
        class GetBuku extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilTanah.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String ss) {
                super.onPostExecute(ss);
                loading.dismiss();
                showBuku2(ss);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_TANAH,id_tanah);
                return s;
            }
        }

        GetBuku ge = new GetBuku();
        ge.execute();
    }
    private void showBuku2(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY6);
            JSONObject c = result.getJSONObject(0);
            String nama_lengkap = c.getString(konfigurasi.TAG_NAMA_LENGKAP);
            String alamat= c.getString(konfigurasi.TAG_ALAMAT);
            String persil= c.getString(konfigurasi.TAG_persil);
            String id_transaksi= c.getString(konfigurasi.TAG_ID_TRANSAKSI2);
            String id_jb= c.getString(konfigurasi.TAG_JB2);
            String luas= c.getString(konfigurasi.TAG_LUAS);
            String id_kelas= c.getString(konfigurasi.TAG_ID_KELAS2);
            /*String sisa= c.getString(konfigurasi.TAG_SISA);*/
            String tgl_regis= c.getString(konfigurasi.TAG_TGL_REGIS);
            String ket= c.getString(konfigurasi.TAG_KET_TANAH);

            editTextNama.setText(nama_lengkap);
            editTextAlamat.setText(alamat);
            editTextPersil.setText(persil);
            editTextJT.setText(id_transaksi);
            editTextSJT.setText(id_jb);
            editTextKelas.setText(id_kelas);
            editTextLuas.setText(luas);
            /*editTextSisa.setText(sisa);*/
            editTextDate.setText(tgl_regis);
            editTextKet.setText(ket);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*==================AWAL MUTASI==================================*/

    private void showListView() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY6);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_tanah= jo.getString(konfigurasi.TAG_ID_TANAH);
                String nama_lengkap = jo.getString(konfigurasi.TAG_NAMA_LENGKAP);

                HashMap<String,String> buku = new HashMap<>();
                buku.put(konfigurasi.TAG_ID_TANAH,id_tanah);
                buku.put(konfigurasi.TAG_NAMA_LENGKAP,nama_lengkap);
                list.add(buku);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                TampilTanah.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_ID_TANAH,konfigurasi.TAG_NAMA_LENGKAP},
                new int[]{R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }
    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilTanah.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showListView();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_ALL_TANAH2, id_tanah);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewDoc.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            final String id_tanah = editTextId.getText().toString().trim();
            final String id_buku = edtBuku.getText().toString().trim();
            final String kohir = edtKohir.getText().toString().trim();
            final String nama_lengkap = edtNama.getText().toString().trim();
            final String alamat = editTextAlamat.getText().toString().trim();
            final String id_transaksi = edtJt.getText().toString().trim();
            final String id_jb = edtSJT.getText().toString().trim();
            final String nama_notaris = edtNotaris.getText().toString().trim();
            final String nomor= edtNomor.getText().toString().trim();
            final String persil = editTextPersil.getText().toString().trim();
            final String luas2 = edtLuas.getText().toString().trim();
            final String luas = editTextLuas.getText().toString().trim();
            final String id_kelas = editTextKelas.getText().toString().trim();
            final String tgl_regis = edtDate.getText().toString().trim();
            final String ket = edtKet.getText().toString().trim();

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilTanah.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("name",getFileName(filePath));
                data.put(konfigurasi.KEY_ID_TANAH,id_tanah);
                data.put(konfigurasi.KEY_KOHIR,kohir);
                data.put(konfigurasi.KEY_ID_BUKU2,id_buku);
                data.put(konfigurasi.KEY_NAMA_LENGKAP,nama_lengkap);
                data.put(konfigurasi.KEY_ALAMAT,alamat);
                data.put(konfigurasi.KEY_ID_TRANSAKSI2,id_transaksi);
                data.put(konfigurasi.KEY_JB2,id_jb);
                data.put(konfigurasi.KEY_notaris,nama_notaris);
                data.put(konfigurasi.KEY_nomor,nomor);
                data.put(konfigurasi.KEY_TGL_REGIS,tgl_regis);
                data.put(konfigurasi.KEY_persil,persil);
                data.put(konfigurasi.KEY_ID_KELAS2,id_kelas);
                data.put(konfigurasi.KEY_LUAS,luas2);
                data.put(konfigurasi.KEY_LUAS2,luas);
                data.put(konfigurasi.KEY_KET_TANAH,ket);



                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.UPLOAD_TRANSAKSI, data);

                return  res;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "\n" +
                        "Izin diberikan. Sekarang Anda dapat mengakses penyimpanan", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops Anda baru saja menolak izin", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilTanah.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String tanahId = map.get(konfigurasi.TAG_ID_TANAH).toString();
        intent.putExtra(konfigurasi.TANAH_ID,tanahId );
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        if(view == imageView){
            Intent intent= new Intent(TampilTanah.this, Administrasi.class);
            startActivity(intent);
        }if (view==btnDatePicker){
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
        }if (view==buttonChoose){
            showFileChooser();
        }if(view == buttonAdd){
            if(filePath!=null) {
                if (edtBuku.getText().toString().length()==0){
                    edtBuku.setError("Buku Fisik diperlukan!");
                }else if (edtKohir.getText().toString().length()==0){
                    edtKohir.setError("Nomor kohir diperlukan!");
                }else if (edtNama.getText().toString().length()==0){
                    edtNama.setError("Nama lengkap pemilik diperlukan!");
                }else if (edtJt.getText().toString().length()==0){
                    edtJt.setError("Jenis Transaksi diperlukan!");
                }else if (edtSJT.getText().toString().length()==0){
                    edtSJT.setError("Sub jenis diperlukan!");
                }else if (edtLuas.getText().toString().length()==0){
                    edtLuas.setError("Luas tanah diperlukan!");
                }else if (edtDate.getText().toString().length()==0){
                    edtDate.setError("Tanggal Registrasi diperlukan!");
                }else{
                    uploadImage();
                }

            } else {
                Toast.makeText(TampilTanah.this,"Select Image",Toast.LENGTH_LONG).show();
            }
        }
    }
}
