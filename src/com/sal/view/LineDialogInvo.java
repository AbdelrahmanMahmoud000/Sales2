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

public class LineDialogInvo extends JDialog{
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemCountField;
    private JLabel itemNameLabel;
    private JLabel itemPriceLabel;    
    private JLabel itemCountLabel;
    private JButton addButton;
    private JButton cancelButton;
    
    public LineDialogInvo(MFrame fram) {
        itemNameLabel = new JLabel("Item Name");
        itemNameField = new JTextField(15);
        
        itemPriceLabel = new JLabel("Item Price");
        itemPriceField = new JTextField(15);

        itemCountLabel = new JLabel("Item Count");
        itemCountField = new JTextField(15);
        
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        
        addButton.setActionCommand("createLineAdd");
        cancelButton.setActionCommand("createLineCancel");
        
        addButton.addActionListener(fram.getController());
        cancelButton.addActionListener(fram.getController());
        setLayout(new GridLayout(4, 4));
        
        add(itemNameLabel);
        add(itemNameField);
        add(itemPriceLabel);
        add(itemPriceField);
        add(itemCountLabel);
        add(itemCountField);
        add(addButton);
        add(cancelButton);
        
        pack();
    }
 
    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
