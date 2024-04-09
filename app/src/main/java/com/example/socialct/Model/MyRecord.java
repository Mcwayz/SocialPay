package com.example.socialct.Model;

public class MyRecord {

    private String nrc;
    private String fullName;
    private String customerNumber;
    private String phoneNumber;
    private String institution;
    private String accountNumber;
    private String district;
    private String status;
    private String nrc_back;
    private String nrc_front;
    double account_balance;

    public MyRecord(String nrc, String fullName, String customerNumber, String phoneNumber, String institution, String accountNumber, String district, String status, String nrc_back, String nrc_front, double account_balance) {
        this.nrc = nrc;
        this.fullName = fullName;
        this.customerNumber = customerNumber;
        this.phoneNumber = phoneNumber;
        this.institution = institution;
        this.accountNumber = accountNumber;
        this.district = district;
        this.status = status;
        this.nrc_back = nrc_back;
        this.nrc_front = nrc_front;
        this.account_balance = account_balance;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNrc_back() {
        return nrc_back;
    }

    public void setNrc_back(String nrc_back) {
        this.nrc_back = nrc_back;
    }

    public String getNrc_front() {
        return nrc_front;
    }

    public void setNrc_front(String nrc_front) {
        this.nrc_front = nrc_front;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }
}
