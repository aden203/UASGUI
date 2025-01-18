/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.math.BigDecimal;

/**
 *
 * @author acer
 */
public class Transaksi {
    private String idTransaksi;
    private String tanggal;
    private String namaBarang;
    private int jumlah;
    private BigDecimal harga;
    
    public Transaksi(String idTransaksi, String tanggal, String namaBarang, int jumlah, BigDecimal harga) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public BigDecimal hitungTotalHarga() {
        return harga.multiply(BigDecimal.valueOf(jumlah));
    }

    // Getter dan setter
    public String getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(String idTransaksi) { this.idTransaksi = idTransaksi; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public BigDecimal getHarga() { return harga; }
    public void setHarga(BigDecimal harga) { this.harga = harga; }
}


