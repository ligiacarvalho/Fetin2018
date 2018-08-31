package com.fetin.securityapp.model;

public class Celular {

    private String modelo, chip1, chip2, imei1, imei2;
    private int check1, check2;
    private double coordenadaLong, coordenadaLat;


    //code->generate->getandset ou command+n
    // get and set
    public double getCoordenadaLong() {
        return coordenadaLong;
    }
    public void setCoordenadaLong(double coordenadaLong) {
        this.coordenadaLong = coordenadaLong;
    }
    public double getCoordenadaLat() {
        return coordenadaLat;
    }
    public void setCoordenadaLat(double coordenadaLat) {
        this.coordenadaLat = coordenadaLat;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getChip1() {
        return chip1;
    }

    public void setChip1(String chip1) {
        this.chip1 = chip1;
    }

    public String getChip2() {
        return chip2;
    }

    public void setChip2(String chip2) {
        this.chip2 = chip2;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public int getCheck1() {
        return check1;
    }

    public void setCheck1(int check1) {
        this.check1 = check1;
    }

    public int getCheck2() {
        return check2;
    }

    public void setCheck2(int check2) {
        this.check2 = check2;
    }
}
