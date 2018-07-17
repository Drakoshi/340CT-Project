package com.kiviliut.Jframes;

import javax.swing.*;
import java.awt.*;

/**
 * Base Class for Windows created in this project
 */
abstract class BaseWindow {

    JFrame frame;

    /**
     * To be used in constructor of child class
     * @param title Tittle of the window
     * @param contentPane main pane to be displayed
     * @param size minimum size of the window
     */
    void CreateFrame(String title, Container contentPane, Dimension size) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setContentPane(contentPane);
        this.frame.setMinimumSize(size);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    /**
     * To be used in constructor of child class, specifies closeOperation
     * @param title Tittle of the window
     * @param contentPane main pane to be displayed
     * @param size minimum size of the window
     * @param closeOperation JFrame Constants for on close actions
     */
    void CreateFrame(String title, Container contentPane, Dimension size,int closeOperation) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(closeOperation);
        this.frame.setContentPane(contentPane);
        this.frame.setMinimumSize(size);
        this.frame.pack();
        this.frame.setVisible(true);
    }
}
