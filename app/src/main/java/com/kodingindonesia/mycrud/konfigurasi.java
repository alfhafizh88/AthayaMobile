package com.kodingindonesia.mycrud;

/**
 * Created by muhammadyusuf on 01/19/2017.
 * kodingindonesia
 */

public class konfigurasi {
    public static final String UPLOAD_URL = "http://192.168.43.138/Android/pegawai/upload.php";
    public static final String UPLOAD_TRANSAKSI = "http://192.168.43.138/Android/pegawai/upload_transaksi.php";
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD="http://192.168.43.138/Android/asli/tambahPgw.php";
    public static final String URL_GET_ALL = "http://192.168.43.138/Android/asli/tampilSemuaPgw.php";
    public static final String URL_GET_EMP = "http://192.168.43.138/Android/asli/tampilPgw.php?id=";
    public static final String URL_UPDATE_EMP = "http://192.168.43.138/Android/asli/updatePgw.php";
    public static final String URL_DELETE_EMP = "http://192.168.43.138/Android/asli/hapusPgw.php?id=";

    //URL BUKU
    public static final String URL_ADD_BUKU="http://192.168.43.138/Android/pegawai/tambahBuku.php";
    public static final String URL_GET_ALL_BUKU = "http://192.168.43.138/Android/pegawai/tampilSemuaBuku.php";
    public static final String URL_GET_BUKU = "http://192.168.43.138/Android/pegawai/tampilBuku.php?id=";
    public static final String URL_UPDATE_BUKU = "http://192.168.43.138/Android/pegawai/updateBuku.php";
    public static final String URL_DELETE_BUKU = "http://192.168.43.138/Android/pegawai/hapusBuku.php?id=";

    //URL KELAS
    public static final String URL_ADD_KELAS="http://192.168.43.138/Android/pegawai/tambahKelas.php";
    public static final String URL_GET_ALL_KELAS= "http://192.168.43.138/Android/pegawai/tampilSemuaKelas.php";
    public static final String URL_GET_KELAS = "http://192.168.43.138/Android/pegawai/tampilKelas.php?id=";
    public static final String URL_UPDATE_KELAS= "http://192.168.43.138/Android/pegawai/updateKelas.php";
    public static final String URL_DELETE_KELAS = "http://192.168.43.138/Android/pegawai/hapusKelas.php?id=";

    //URL JENIS
    public static final String URL_ADD_JENIS="http://192.168.43.138/Android/pegawai/tambahJenis.php";
    public static final String URL_GET_ALL_JENIS= "http://192.168.43.138/Android/pegawai/tampilSemuaJenis.php";
    public static final String URL_GET_JENIS = "http://192.168.43.138/Android/pegawai/tampilJenis.php?id=";
    public static final String URL_UPDATE_JENIS= "http://192.168.43.138/Android/pegawai/updateJenis.php";
    public static final String URL_DELETE_JENIS = "http://192.168.43.138/Android/pegawai/hapusJenis.php?id=";

    //URL JABATAN
    public static final String URL_ADD_JABATAN="http://192.168.43.138/Android/pegawai/tambahJbt.php";
    public static final String URL_GET_ALL_JABATAN = "http://192.168.43.138/Android/pegawai/tampilSemuaJbt.php";
    public static final String URL_GET_JABATAN = "http://192.168.43.138/Android/pegawai/tampilJbt.php?id=";
    public static final String URL_UPDATE_JABATAN= "http://192.168.43.138/Android/pegawai/updateJbt.php";
    public static final String URL_DELETE_JABATAN = "http://192.168.43.138/Android/pegawai/hapusJbt.php?id=";

    //URL LURAH
    public static final String URL_ADD_LURAH="http://192.168.43.138/Android/pegawai/tambahLurah.php";
    public static final String URL_GET_ALL_LURAH= "http://192.168.43.138/Android/pegawai/tampilSemuaLurah.php";
    public static final String URL_GET_LURAH = "http://192.168.43.138/Android/pegawai/tampilLurah.php?id=";
    public static final String URL_UPDATE_LURAH= "http://192.168.43.138/Android/pegawai/updateLurah.php";
    public static final String URL_DELETE_LURAH= "http://192.168.43.138/Android/pegawai/hapusLurah.php?id=";

    //URL TANAH
    public static final String URL_ADD_TANAH="http://192.168.43.138/Android/pegawai/tambahBuku.php";
    public static final String URL_GET_ALL_TANAH= "http://192.168.43.138/Android/pegawai/tampilSemuaTanah.php";
    public static final String URL_GET_ALL_TANAH2= "http://192.168.43.138/Android/pegawai/tampilMutasi.php?id=";
    public static final String URL_GET_TANAH= "http://192.168.43.138/Android/pegawai/tampilTanah.php?id=";
    public static final String URL_UPDATE_TANAH= "http://192.168.43.138/Android/pegawai/updateBuku.php";
    public static final String URL_DELETE_TANAH = "http://192.168.43.138/Android/pegawai/hapusBuku.php?id=";

    //URL KELAS`
    public static final String URL_ADD_AKUN="http://192.168.43.138/Android/pegawai/tambahAkun.php";
    public static final String URL_GET_ALL_AKUN= "http://192.168.43.138/Android/pegawai/tampilSemuaAkun.php";
    public static final String URL_GET_AKUN = "http://192.168.43.138/Android/pegawai/tampilAkun.php?id=";
    public static final String URL_GET_AKUN2 = "http://192.168.43.138/Android/pegawai/tampilAkun2.php";
    public static final String URL_UPDATE_AKUN= "http://192.168.43.138/Android/pegawai/updateAkun.php";
    public static final String URL_DELETE_AKUN= "http://192.168.43.138/Android/pegawai/hapusAkun.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_POSISI = "desg"; //desg itu variabel untuk posisi
    public static final String KEY_EMP_GAJIH = "salary"; //salary itu variabel untuk gajih

    //KEY MASTER BUKU
    public static final String KEY_BUKU_ID = "id_buku";
    public static final String KEY_BUKU_NAMA = "nama_buku";
    public static final String KEY_BUKU_MULAI= "mulai_buku";
    public static final String KEY_BUKU_SELESAI = "selesai_buku";
    public static final String KEY_BUKU_KET = "ket_buku";

    //KEY MASTER KELAS
    public static final String KEY_KELAS_ID = "id_kelas";
    public static final String KEY_KELAS_NAMA = "nama_kelas";
    public static final String KEY_KELAS_KET = "ket_kelas";

    //KEY MASTER JENIS
    public static final String KEY_JENIS_ID = "id_transaksi";
    public static final String KEY_JENIS_NAMA = "nama_jenis";
    public static final String KEY_JENIS_KET = "ket_jenis";

    //KEY MASTER JABATAN
    public static final String KEY_JABATAN_ID = "id_jabatan";
    public static final String KEY_JABATAN_NAMA = "nama_jab";
    public static final String KEY_JABATAN_KET = "ket_jabatan";

    //KEY MASTER JABATAN
    public static final String KEY_LURAH_ID = "nip";
    public static final String KEY_LURAH_NAMA = "nama_lurah";
    public static final String KEY_LURAH_JABATAN = "nama_jab";
    public static final String KEY_LURAH_MASUK= "tgl_masuk";
    public static final String KEY_LURAH_KELUAR= "tgl_keluar";
    public static final String KEY_LURAH_KET = "ket_lurah";

    //KEY MASTER AKUN
    public static final String KEY_AKUN_ID = "username";
    public static final String KEY_AKUN_NAMA = "nama_akun";
    public static final String KEY_AKUN_EMAIL = "email";
    public static final String KEY_AKUN_ALAAMAT = "alamat";
    public static final String KEY_AKUN_TLP = "telepon";
    public static final String KEY_AKUN_PASS = "password";
    public static final String KEY_AKUN_STATUS = "status";
    public static final String KEY_AKUN_KET = "ket";

    //KEY MASTER TANAH
    public static final String KEY_ID_TANAH = "id_tanah";
    public static final String KEY_INDUK = "induk";
    public static final String KEY_KOHIR = "kohir";
    public static final String KEY_ID_BUKU2 = "id_buku";
    public static final String KEY_NAMA_LENGKAP= "nama_lengkap";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_MAPS= "maps";
    public static final String KEY_ID_TRANSAKSI2 = "id_transaksi";
    public static final String KEY_JB2 = "id_jb";
    public static final String KEY_notaris = "nama_notaris";
    public static final String KEY_nomor = "nomor";
    public static final String KEY_tgl = "tgl_regis";
    public static final String KEY_persil = "persil";
    public static final String KEY_ID_KELAS2 = "id_kelas";
    public static final String KEY_LUAS = "luas";
    public static final String KEY_LUAS2 = "luas2";
    public static final String KEY_ID_SATUAN2 = "id_satuan";
    public static final String KEY_TGL_REGIS = "tgl_regis";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_GOL = "gol";
    public static final String KEY_USERNAME2 = "username";
    public static final String KEY_LAST_SEEN= "last_seen";
    public static final String KEY_STATUS2= "status";
    public static final String KEY_AKUN_PEMILIK= "akun_pemilik";
    public static final String KEY_KET_TANAH= "ket";

    //JSON Tags EMP
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "desg";
    public static final String TAG_GAJIH = "salary";
    //JSON Tags Buku
    public static final String TAG_JSON_ARRAY2="result";
    public static final String TAG_ID_BUKU = "id_buku";
    public static final String TAG_NAMA_BUKU = "nama_buku";
    public static final String TAG_MULAI_BUKU = "mulai_buku";
    public static final String TAG_SELESAI_BUKU = "selesai_buku";
    public static final String TAG_KET_BUKU = "ket_buku";
    //JSON Tags Kelas
    public static final String TAG_JSON_ARRAY3="result";
    public static final String TAG_ID_KELAS = "id_kelas";
    public static final String TAG_NAMA_KELAS = "nama_kelas";
    public static final String TAG_KET_KELAS = "ket_kelas";
    //JSON Tags JENIS
    public static final String TAG_JSON_ARRAY7="result";
    public static final String TAG_ID_JENIS = "id_transaksi";
    public static final String TAG_NAMA_JENIS = "nama_jenis";
    public static final String TAG_KET_JENIS= "ket_jenis";

    //JSON Tags Akun
    public static final String TAG_JSON_ARRAY8="result";
    public static final String TAG_ID_AKUN = "username";
    public static final String TAG_NAMA_AKUN= "nama_akun";
    public static final String TAG_EMAIL_AKUN= "email";
    public static final String TAG_ALAMAT_AKUN= "alamat";
    public static final String TAG_TELEPON_AKUN= "telepon";
    /*public static final String TAG_PASSWORD_AKUN= "password";*/
    public static final String TAG_STATUS_AKUN= "status";
    public static final String TAG_KET_AKUN = "ket";

    //JSON Tags Jabatan
    public static final String TAG_JSON_ARRAY4="result";
    public static final String TAG_ID_JABATAN = "id_jabatan";
    public static final String TAG_NAMA_JABATAN= "nama_jab";
    public static final String TAG_KET_JABATAN = "ket_jabatan";
    //JSON Tags Lurah
    public static final String TAG_JSON_ARRAY5="result";
    public static final String TAG_ID_LURAH = "nip";
    public static final String TAG_NAMA_LURAH= "nama_lurah";
    public static final String TAG_JABATAN_LURAH = "nama_jab";
    public static final String TAG_MASUK_LURAH= "tgl_masuk";
    public static final String TAG_KELUAR_LURAH= "tgl_keluar";
    public static final String TAG_KET_LURAH= "ket_lurah";
    //JSON Tags Tanah
    public static final String TAG_JSON_ARRAY6="result";
    public static final String TAG_ID_TANAH = "id_tanah";
    public static final String TAG_INDUK = "induk";
    public static final String TAG_KOHIR = "kohir";
    public static final String TAG_ID_BUKU2 = "id_buku";
    public static final String TAG_NAMA_LENGKAP= "nama_lengkap";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_MAPS= "maps";
    public static final String TAG_ID_TRANSAKSI2 = "id_transaksi";
    public static final String TAG_JB2 = "id_jb";
    public static final String TAG_notaris = "nama_notaris";
    public static final String TAG_nomor = "nomor";
    public static final String TAG_tgl = "tgl_regis";
    public static final String TAG_persil = "persil";
    public static final String TAG_ID_KELAS2 = "id_kelas";
    public static final String TAG_LUAS = "luas";
    public static final String TAG_SISA = "sisa";
    public static final String TAG_ID_SATUAN2 = "id_satuan";
    public static final String TAG_TGL_REGIS = "tgl_regis";
    public static final String TAG_FOTO = "foto";
    public static final String TAG_GOL = "gol";
    public static final String TAG_USERNAME2 = "username";
    public static final String TAG_LAST_SEEN= "last_seen";
    public static final String TAG_STATUS2= "status";
    public static final String TAG_AKUN_PEMILIK= "akun_pemilik";
    public static final String TAG_KET_TANAH= "ket";


    //ID karyawan
    //emp itu singkatan dari Tanah
    public static final String TANAH_ID = "tanah_id";
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
    //emp itu singkatan dari Buku
    public static final String BUKU_ID = "buku_id";
    //emp itu singkatan dari Kelas
    public static final String KELAS_ID = "kelas_id";
    //emp itu singkatan dari Kelas
    public static final String JENIS_ID = "jenis_id";
    //emp itu singkatan dari Kelas
    public static final String AKUN_ID = "akun_id";
    //emp itu singkatan dari Jbt
    public static final String JABATAN_ID = "jabatan_id";
    //emp itu singkatan dari Jbt
    public static final String LURAH_ID = "nip";

}
