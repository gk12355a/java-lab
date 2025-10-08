package com.bookstore.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class Fine implements Serializable {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private String fineId;
    private String readerId;
    private String borrowingId;
    private String reason;
    private double amount;
    private Date fineDate;
    private String paymentStatus;

    private String note;

    public Fine() {}

    public Fine(String fineId, String readerId, String borrowingId, String reason, double amount, Date fineDate, String paymentStatus, String note) {
        this.fineId = fineId;
        this.readerId = readerId;
        this.borrowingId = borrowingId;
        this.reason = reason;
        this.amount = amount;
        this.fineDate = fineDate;
        this.paymentStatus = paymentStatus;
        this.note = note;
    }

    public String getFineId() {
        return fineId;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(String borrowingId) {
        this.borrowingId = borrowingId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getFineDate() {
        return fineDate;
    }

    public void setFineDate(Date fineDate) {
        this.fineDate = fineDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f|%s|%s|%s",
                fineId, readerId, borrowingId, reason, amount,
                fineDate != null ? DATE_FORMAT.format(fineDate) : "null",
                paymentStatus, note != null ? note : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fine fine = (Fine) obj;
        return Objects.equals(fineId, fine.fineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fineId);
    }


}
