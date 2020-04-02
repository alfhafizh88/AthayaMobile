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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.RequestHandler;
import com.kodingindonesia.mycrud.adapter.Adapter;
import com.kodingindonesia.mycrud.app.AppController;
import com.kodingindonesia.mycrud.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Administrasi extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNama;
    private EditText edtBuku;
    private EditText edtKohir;
    private EditText edtAlamat;
    private EditText edtJT;
    private EditText edtSJT;
    private EditText edtNotaris;
    private EditText edtNomor;
    private EditText edtPersil;
    private EditText edtLuas;
    private EditText edtJenisTanah;
    private EditText edtDate;
    private EditText edtKet;

    TextView txt_hasil;
    Spinner spinner_pendidikan;
    ProgressDialog pDialog;
    Adapter adapter;
    List<DataBuku> listPendidikan = new ArrayList<DataBuku>();

    public static final String url = "http://192.168.43.138/Android/asli/menu_buku.php";

    private static final String TAG = Administrasi.class.getSimpleName();

    public static final String TAG_ID = "id_buku";
    public static final String TAG_PENDIDIKAN = "nama_buku";

    public static final String UPLOAD_URL = "http://192.168.43.138/Android/pegawai/upload.php";
    public static final String UPLOAD_KEY = "foto";
    Button btnDatePicker, btnDatePicker2, buttonChoose;
    EditText txtDate, txtDate2;
    private  int PICK_IMAGE_REQUEST=1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private int mYear, mMonth, mDay, mYear2, mMonth2, mDay2;

    private Button buttonAdd;
    private Button buttonView;
    private ImageView imageView;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrasi);
        requestStoragePermission();
        edtBuku= (EditText) findViewById(R.id.edtBuku);
        edtKohir = (EditText) findViewById(R.id.edtKohir);
        edtAlamat= (EditText) findViewById(R.id.edtAlamat);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtJT= (EditText) findViewById(R.id.edtJT);
        edtSJT= (EditText) findViewById(R.id.edtSJT);
        edtNotaris= (EditText) findViewById(R.id.edtNotaris);
        edtNomor= (EditText) findViewById(R.id.edtNomor);
        edtPersil= (EditText) findViewById(R.id.edtPersil);
        edtPersil= (EditText) findViewById(R.id.edtPersil);
        edtLuas= (EditText) findViewById(R.id.edtLuas);
        edtJenisTanah= (EditText) findViewById(R.id.edtJenisTanah);
        edtDate= (EditText) findViewById(R.id.in_date);
        edtKet= (EditText) findViewById(R.id.edtKet);
        txt_hasil = (TextView) findViewById(R.id.txt_hasil);
        /*spinner_pendidikan = (Spinner) findViewById(R.id.spinner_buku);

        spinner_pendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO Auto-generated method stub
                txt_hasil.setText("Pendidikan Terakhir : " + listPendidikan.get(i).getNama_buku());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        /*adapter = new Adapter(Administrasi.this, listPendidikan);*/
/*        spinner_pendidikan.setAdapter(adapter);*/

        /*callData();*/

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose=(Button)findViewById(R.id.btn_choose);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnDatePicker2=(Button)findViewById(R.id.btn_date2);
        txtDate=(EditText)findViewById(R.id.in_date);
        /*txtDate2=(EditText)findViewById(R.id.in_date2);*/

        btnDatePicker.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    private void callData() {
        listPendidikan.clear();

        pDialog = new ProgressDialog(Administrasi.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataBuku item = new DataBuku();

                                item.setId_buku(obj.getString(TAG_ID));
                                item.setNama_buku(obj.getString(TAG_PENDIDIKAN));

                                listPendidikan.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Administrasi.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    /*public void uploadMultipart() {
        //getting name for the image
        String name = editText.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request

            new MultipartUploadRequest(this, uploadId, konfigurasi.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .addParameter("id", uploadId) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

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
                imageView.setImageBitmap(bitmap);
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
            final String id_buku = edtBuku.getText().toString().trim();
            final String kohir = edtKohir.getText().toString().trim();
            final String nama_lengkap = edtNama.getText().toString().trim();
            final String alamat = edtAlamat.getText().toString().trim();
            final String id_transaksi = edtJT.getText().toString().trim();
            final String id_jb = edtSJT.getText().toString().trim();
            final String nama_notaris = edtNotaris.getText().toString().trim();
            final String nomor= edtNomor.getText().toString().trim();
            final String persil = edtPersil.getText().toString().trim();
            final String luas = edtLuas.getText().toString().trim();
            final String id_kelas = edtJenisTanah.getText().toString().trim();
            final String tgl_regis = edtDate.getText().toString().trim();
            final String ket = edtKet.getText().toString().trim();



            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Administrasi.this, "Uploading Image", "Please wait...",true,true);
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
                data.put(konfigurasi.KEY_LUAS,luas);
                data.put(konfigurasi.KEY_KET_TANAH,ket);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.UPLOAD_URL, data);

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

    //method to get the file path from uri
    /*public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }*/

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

    //This method will be called when the user will tap on allow or deny
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
    public void onClick(View v) {
        if(v == buttonAdd){
            if(filePath!=null) {
                uploadImage();
            } else {
                Toast.makeText(Administrasi.this,"Select Image",Toast.LENGTH_LONG).show();
            }
            /*uploadMultipart();*/
        }if(v == buttonView){
            startActivity(new Intent(this, TampilSemuaTanah.class));
        }if(v == imageView){
            Intent intent= new Intent(Administrasi.this, DataMaster.class);
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
        }if(v == btnDatePicker2){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear2 = c.get(Calendar.YEAR);
            mMonth2 = c.get(Calendar.MONTH);
            mDay2 = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate2.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear2, mMonth2, mDay2);
            datePickerDialog.show();

        }if (v == buttonChoose){
            showFileChooser();
        }
    }
}
