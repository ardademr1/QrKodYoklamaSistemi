package com.example.ogrenciqr;

public class ogrenci {
    public String adsoyad,email,sifre,durum;

    public ogrenci(){

    }

    public ogrenci(String adsoyad,String email,String sifre, String durum) {
        this.adsoyad = adsoyad;
        this.email=email;
        this.sifre=sifre;
        this.durum=durum;
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public String getEmail() {
        return email;
    }

    public String getSifre() {
        return sifre;
    }

    public String getDurum() { return durum; }
}
