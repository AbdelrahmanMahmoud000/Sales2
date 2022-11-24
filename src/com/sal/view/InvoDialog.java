/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sal.view;

/**
 *
 * @author Bedo
 */
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoDialog extends JDialog {
    private JTextField InvoiceeDtField;    
    private JTextField customerField;
    private JLabel InvoiceeDtLabel;    
    private JLabel customerLabel;
    private JButton addButton;
    private JButton cancelButton;

    public InvoDialog(MFrame fram) {
        InvoiceeDtLabel = new JLabel("Invoicee Date:");
        InvoiceeDtField = new JTextField(15); 
        
        customerLabel = new JLabel("Customer Name:");
        customerField = new JTextField(15);
        
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        
        addButton.setActionCommand("createInvoiceeAdd");
        cancelButton.setActionCommand("createInvoiceeCancel");
        
        addButton.addActionListener(fram.getController());
        cancelButton.addActionListener(fram.getController());
        setLayout(new GridLayout(3, 3));
        
        add(InvoiceeDtLabel);
        add(InvoiceeDtField);
        add(customerLabel);
        add(customerField);
        add(addButton);
        add(cancelButton);
        
        pack();
        
    }

    public JTextField getCustomerNameField() {
        return customerField;
    }

    public JTextField getInvoiceeDtField() {
        return InvoiceeDtField;
    }
    
}
