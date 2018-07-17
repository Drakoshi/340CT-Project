package com.kiviliut;

import com.kiviliut.Jframes.mainWindow;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Notification {

    private static mainWindow mainWindow;

    /**
     * Should only be called once
     * @param mainWindow - it writes the message into a JTextPane
     */
    public static void setMainWindow(mainWindow mainWindow) {
        Notification.mainWindow = mainWindow;
    }

    private static String LogEntry(String text) {
        StringBuilder entry = new StringBuilder();
        entry.append("[");
        entry.append(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
        entry.append("] ");
        entry.append(text);
        entry.append("\n");

        return entry.toString();
    }

    public static void AddLogEntry(String text) {
        mainWindow.logArea.append(LogEntry(text));
    }

    public static void PopUpMessage(String text) {
        JOptionPane.showMessageDialog(null,text);
    }
}
