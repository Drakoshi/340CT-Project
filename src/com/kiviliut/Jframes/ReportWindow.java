package com.kiviliut.Jframes;

import com.kiviliut.DBCon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportWindow extends BaseWindow{

    private JPanel mainPanel;
    private JButton Return;
    private JTable salesTable;

    public ReportWindow() {
        CreateFrame("Sales report",this.mainPanel,new Dimension(400,400), JFrame.DISPOSE_ON_CLOSE);

        // Create table columns
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Item count","Min Stock", "Status","Sales","Prev.Sales","Change"},0);
        this.salesTable.setModel(model);

        synchronized (this)
        {
            int count = 0;
            for(Object[] row: new DBCon().getInventory(true)) {
                model.addRow(row);
                // Calculate change in sales
                int sales = (int)model.getValueAt(count,5);
                int pSales = (int)model.getValueAt(count,6);
                int res = sales - pSales;
                // Put sales into place
                model.setValueAt(res,count,7);

                // Counter for rows
                count++;
            }
        }

        // Return button
        Return.addActionListener(e -> {
                    // close the frame
                    this.frame.dispose();
                }
        );
    }
}
