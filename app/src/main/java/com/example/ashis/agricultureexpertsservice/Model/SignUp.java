package com.example.ashis.agricultureexpertsservice.Model;

public class SignUp {

    String email;
    String full_name;
    String phone_number;
    String address;
    String occupation;

    public SignUp() {
    }

    public SignUp(String email, String full_name, String phone_number, String address, String occupation) {
        this.email = email;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.address = address;
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
