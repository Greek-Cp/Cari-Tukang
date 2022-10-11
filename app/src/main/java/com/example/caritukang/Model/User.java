package com.example.caritukang.Model;

public class User {
    private String nameUser;
    private String alamatUser;
    private String nomorHpUser;
    private String passwordUser;
    private String roleUser;
    private int saldoUser;
    private String locationGeoXYZUser;
    public User(){
        //empty constructor
    }
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setAlamatUser(String alamatUser) {
        this.alamatUser = alamatUser;
    }

    public void setNomorHpUser(String nomorHpUser) {
        this.nomorHpUser = nomorHpUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public void setSaldoUser(int saldoUser) {
        this.saldoUser = saldoUser;
    }

    public void setLocationGeoXYZUser(String locationGeoXYZUser) {
        this.locationGeoXYZUser = locationGeoXYZUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getAlamatUser() {
        return alamatUser;
    }

    public String getNomorHpUser() {
        return nomorHpUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public int getSaldoUser() {
        return saldoUser;
    }

    public String getLocationGeoXYZUser() {
        return locationGeoXYZUser;
    }
}
