package com.kiviliut;

import com.kiviliut.Jframes.*;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandler implements Runnable{

    // Stores all events
    private static Queue<String> eventQueue = new LinkedList();

    // Constructor, adds default event to create main window
    public EventHandler() {
        eventQueue.add("mainWindow");
    }

    // For access from outside
    public static Queue<String> getEventQueue() {
        return eventQueue;
    }

    @Override
    public void run(){

        // Always run while program is running
        while(true)
        {
            // Checks if there are items in eventQueue
            if(!eventQueue.isEmpty())
            {
                // Choose action depending on event
                String event = eventQueue.poll();
                switch(event)
                {
                    case "mainWindow":
                        mainWindow mainWindow = new mainWindow();
                        Notification.setMainWindow(mainWindow);
                        break;
                    case "statusWindow":
                        new StockStatusWindow();
                        break;
                    case "addNewItemWindow":
                        new StockEditWindow();
                        break;
                    case "reportWindow":
                        new ReportWindow();
                        break;
                    default:
                        System.out.println("Event: " + event + ", could not be handled");
                        break;
                }
            }
            else
            {
                // Sleep for 0.5s before checking again
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

