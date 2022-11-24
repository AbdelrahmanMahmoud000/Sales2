/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sal.controller;

/**
 *
 * @author Bedo
 */
import com.sal.models.Invoice;
import com.sal.models.InvoicesTableModelInvo;
import com.sal.models.Line;
import com.sal.models.LinesTableModelInvo;
import com.sal.view.InvoDialog;
import com.sal.view.MFrame;
import com.sal.view.LineDialogInvo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private MFrame fram;
    private InvoDialog invoDialog;
    private LineDialogInvo lineDialogInvo;

    public Controller(MFrame fram) {
        this.fram = fram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
            switch (actionCommand) {
                case "Load File":
                    loadFileMenuBar();
                    break;
                case "Save File":
                    saveFileMenuBar();
                    break;
                case "Create New Invoice":
                    createNewInvoiceButton();
                    break;
                case "Delete Invoice":
                    deleteInvoiceButton();
                    break;
                case "Create New Item":
                    createNewItemButton();
                    break;
                case "Delete Item":
                    deleteItemButton();
                    break;
                case "createInvoiceeAdd":
                    createInvoiceAddButton();
                    break;
                case "createInvoiceeCancel":
                    createInvoiceCancelButton();
                    break;
                case "createLineAdd":
                    createLineAddButton();
                    break;
                case "createLineCancel":
                    createLineCancelButton();
                    break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        int selectedIndex = fram.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            Invoice currentInvoice = fram.getInvoices().get(selectedIndex);
            fram.getInoviceNumberLabel().setText("" + currentInvoice.getNum());
            fram.getInvoiceDateText().setText(currentInvoice.getDate());
            fram.getCustomerNameText().setText(currentInvoice.getCustomer());
            fram.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            LinesTableModelInvo LinesTableModelInvo = new LinesTableModelInvo(currentInvoice.getLines());
            fram.getLineTable().setModel(LinesTableModelInvo);
            LinesTableModelInvo.fireTableDataChanged();
        }
    }

    private void loadFileMenuBar() {
        
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(fram);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("InvoicesFile read");
                ArrayList<Invoice> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        Invoice invoice = new Invoice(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(fram, "Error in file format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                result = fc.showOpenDialog(fram);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("LinesFile read");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            Invoice inv = null;
                            for (Invoice invoice : invoicesArray) {
                                if (invoice.getNum() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            Line line = new Line(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception exception) {
                                exception.printStackTrace();
                                JOptionPane.showMessageDialog(fram, "Error in file format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                fram.setInvoices(invoicesArray);
                InvoicesTableModelInvo invoicesTableModelInvo = new InvoicesTableModelInvo(invoicesArray);
                fram.setInvoicesTableModelInvo(invoicesTableModelInvo);
                fram.getInvoiceTable().setModel(invoicesTableModelInvo);
                fram.getInvoicesTableModelInvo().fireTableDataChanged();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(fram, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFileMenuBar() {
        
        ArrayList<Invoice> invoices = fram.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoice invoice : invoices) {
            String invoiceFileFormat = invoice.getFileFormat();
            headers += invoiceFileFormat;
            headers += "\n";

            for (Line line : invoice.getLines()) {
                String lineFileFormat = line.getFileFormat();
                lines += lineFileFormat;
                lines += "\n";
            }
        }
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(fram);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter headerFileWriter = new FileWriter(headerFile);
                headerFileWriter.write(headers);
                headerFileWriter.flush();
                headerFileWriter.close();
                result = fc.showSaveDialog(fram);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lineFileWriter = new FileWriter(lineFile);
                    lineFileWriter.write(lines);
                    lineFileWriter.flush();
                    lineFileWriter.close();
                }
            }
        } catch (Exception exception) {
        }
    }

    private void createNewInvoiceButton() {
        
        invoDialog = new InvoDialog(fram);
        invoDialog.setVisible(true);
    }

    private void deleteInvoiceButton() {
        
        int selectedRow = fram.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            fram.getInvoices().remove(selectedRow);
            fram.getInvoicesTableModelInvo().fireTableDataChanged();
        }
    }

    private void createNewItemButton() {
        
        lineDialogInvo = new LineDialogInvo(fram);
        lineDialogInvo.setVisible(true);
    }

    private void deleteItemButton() {
        
        int selectedRow = fram.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            LinesTableModelInvo LinesTableModelInvo = (LinesTableModelInvo) fram.getLineTable().getModel();
            LinesTableModelInvo.getLines().remove(selectedRow);
            LinesTableModelInvo.fireTableDataChanged();
            fram.getInvoicesTableModelInvo().fireTableDataChanged();
        }
    }

    private void createInvoiceCancelButton() {
        
        invoDialog.setVisible(false);
        invoDialog.dispose();
        invoDialog = null;
    }

    private void createInvoiceAddButton() {
        
        String date = invoDialog.getInvoiceeDtField().getText();
        String customer = invoDialog.getCustomerNameField().getText();
        int num = fram.getNextInvoiceNumber();
        try {
            String[] dateParts = date.split("-");
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(fram, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } 
                else {
                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);
                    if (day > 31 || month > 12) {
                        JOptionPane.showMessageDialog(fram, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                        else {
                        Invoice invoice = new Invoice(num, date, customer);
                        fram.getInvoices().add(invoice);
                        fram.getInvoicesTableModelInvo().fireTableDataChanged();
        
                        invoDialog.setVisible(false);
                        invoDialog.dispose();
                        invoDialog = null;
                }
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(fram, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineAddButton() {
        
        String item = lineDialogInvo.getItemNameField().getText();
        String countStr = lineDialogInvo.getItemCountField().getText();
        String priceStr = lineDialogInvo.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = fram.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Invoice invoice = fram.getInvoices().get(selectedInvoice);
            Line line = new Line(item, price, count, invoice);
            invoice.getLines().add(line);
            LinesTableModelInvo LinesTableModelInvo = (LinesTableModelInvo) fram.getLineTable().getModel();
            LinesTableModelInvo.fireTableDataChanged();
            fram.getInvoicesTableModelInvo().fireTableDataChanged();
        }
        
        
        lineDialogInvo.setVisible(false);
        lineDialogInvo.dispose();
        lineDialogInvo = null;
    }

    private void createLineCancelButton() {
        
        lineDialogInvo.setVisible(false);
        lineDialogInvo.dispose();
        lineDialogInvo = null;
    }

}
