package com.kiviliut.Jframes;

import com.kiviliut.EventHandler;

import javax.swing.*;
import java.awt.*;

// Main window, bunch of buttons and a log
public class mainWindow extends BaseWindow{

    // GUI elements
    private JPanel mainPanel;
    private JButton button1;
    private JButton stockStatusButton;
    private JButton addNewItemButton;
    private JButton salesReportButton;
    // TextArea for log
    public JTextArea logArea;

    // Constructor
    public mainWindow() {
        // Create the frame
        CreateFrame("Stock Monitoring System",this.mainPanel,new Dimension(400,400));

        // Opens statusWindow
        stockStatusButton.addActionListener(e -> {
            // Add to eventQueue
            EventHandler.getEventQueue().add("statusWindow");
        });
        addNewItemButton.addActionListener(e -> {
            // Add to eventQueue
            EventHandler.getEventQueue().add("addNewItemWindow");
        });
        salesReportButton.addActionListener(e ->  {
            //add to eventQueue
            EventHandler.getEventQueue().add("reportWindow");
        });
        button1.addActionListener(e ->  {
            // Change this to something less crude
            System.exit(0);
        });
    }
}


