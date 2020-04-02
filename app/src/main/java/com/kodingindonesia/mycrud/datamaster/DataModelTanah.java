package com.kodingindonesia.mycrud.datamaster;

public class DataModelTanah {
    private String id_tanah, nama_lengkap;

    public DataModelTanah(){
    }

    public DataModelTanah(String id_tanah, String nama_lengkap) {
        this.id_tanah = id_tanah;
        this.nama_lengkap = nama_lengkap;
    }
    public String getId_tanah() {
        return id_tanah;
    }

    public void setId_tanah(String id_tanah) {
        this.id_tanah = id_tanah;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }
}
