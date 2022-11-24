/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sal.models;

/**
 *
 * @author Bedo
 */
import java.util.ArrayList;

public class Invoice {
    
 private int num;
 private String date;
 private String customer;
 private ArrayList<Line> lines;
 
 
    public Invoice() {
    }

    public Invoice(int num, String date, String customer) {
        this.num = num;
        this.date = date;
        this.customer = customer;
    }
    
    public double getInvoiceTotal(){
        double total = 0.0;
        for (Line line : getLines()){
            total += line.getLineTotal();
        }
        return total;
    }

    public ArrayList<Line> getLines() {
        if(lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "Invoices{" + "num=" + num + ", date=" + date + ", customer=" + customer + '}';
    }
 
    public String getFileFormat(){
        return num + "," + date + "," + customer;
    }
    
}
