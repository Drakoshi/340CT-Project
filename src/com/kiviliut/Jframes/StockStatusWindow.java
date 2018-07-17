package com.kiviliut.Jframes;

import com.kiviliut.DBCon;
import com.kiviliut.Notification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StockStatusWindow extends BaseWindow{
    // GUI elements
    private JTable stockTable;
    private JButton Return;
    private JPanel mainPanel;

    public StockStatusWindow() {

        // Create stock status frame
        CreateFrame("Stock Status",this.mainPanel,new Dimension(400,400),JFrame.DISPOSE_ON_CLOSE);

        // Make default model based on columnNames
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Item count", "Status"},0);
        this.stockTable.setModel(model);

        // Get rows from DB
        synchronized (this)
        {
            for(Object[] row: new DBCon().getInventory(false)) {
                model.addRow(row);
            }
        }

        // Add message to log
        Notification.AddLogEntry("Stock was successfully retrieved");

        // Return button, dispose the frame
        Return.addActionListener(e -> {
                // close the frame
                this.frame.dispose();
            }
        );
    }
}
