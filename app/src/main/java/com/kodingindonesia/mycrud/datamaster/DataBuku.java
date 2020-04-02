package com.kodingindonesia.mycrud.datamaster;

public class DataBuku {
    private String nama_buku;
    private String id_buku;

    public DataBuku(){
    }
    public DataBuku(String id_buku, String nama_buku){
        this.id_buku=id_buku;
        this.nama_buku=nama_buku;
    }
    public String getId_buku() {
        return id_buku;
    }
    public void setId_buku(String id_buku) {
        this.id_buku = id_buku;
    }
    public String getNama_buku() {
        return nama_buku;
    }
    public void setNama_buku(String nama_buku) {
        this.nama_buku = nama_buku;
    }
}
