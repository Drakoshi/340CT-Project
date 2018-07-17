package com.kiviliut.Jframes;

import com.kiviliut.DBCon;
import com.kiviliut.Notification;
import com.kiviliut.OrderHandler;

import javax.swing.*;
import java.awt.*;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class StockEditWindow extends BaseWindow{

    private JTextField nameField;
    private JTextField itemCountField;
    private JTextField minStockField;
    private JTextField salesField;
    private JButton returnButton;
    private JPanel mainPanel;
    private JButton saveButton;
    private JComboBox itemStatus;

    public StockEditWindow() {

        // Prepare the window
        CreateFrame("Item Addition",this.mainPanel,new Dimension(400,400),JFrame.DISPOSE_ON_CLOSE);
        DropDownAdd(itemStatus,new String[]{"In Stock","Out of stock", "Ordered","Low"});

        // Listeners
        saveButton.addActionListener(e ->  {
            ArrayList<String> info = new ArrayList<>();

            // Check if those fields are not empty
            if(nameField.getText().isEmpty() || minStockField.getText().isEmpty())
            {
                Notification.PopUpMessage("Name and Min stock cannot be empty!");
            }
            else
            {
                // Retrieves the data from text fields
                info.add(nameField.getText());
                info.add(itemCountField.getText());
                info.add(minStockField.getText());
                info.add(itemStatus.getSelectedItem().toString());
                info.add(salesField.getText());

                // Hands the request to DB handler
                String result;
                synchronized (this) {
                    result = new DBCon().InsertToDB(info);
                }
                // Handle results
                if (result.equals("success")){
                    Notification.AddLogEntry(info.get(0) + " was added to database");

                    // Orders the items
                    if(info.get(3).equals("Out of stock")) {
                        new Thread(new OrderHandler(info.get(0))).start();
                    }
                }
                else if (result.equals("duplicate")){
                    Notification.PopUpMessage(info.get(0) + " is already in database, terminating action");
                }
            }
        });

        // Close the window, but not exit the program
        returnButton.addActionListener(e -> this.frame.dispose());

        // Actions to perform once the field lost focus
        minStockField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                // duplicate the input to other field, still editable afterwards
                itemCountField.setText(minStockField.getText());
            }
        });
    }
    //

    // Populates the dropdown menu
    private void DropDownAdd(JComboBox dropdown,String[] items){
        for (String i: items) {
            dropdown.addItem(i);
        }
    }
}
