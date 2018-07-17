package com.kiviliut;

public class OrderHandler implements Runnable{

    private String name;

    public OrderHandler(String itemName) {
        this.name = itemName;
    }

    @Override
    public void run() {

        // Order notification
        Notification.AddLogEntry(this.name + " was Ordered");

        // Update DB
        int minStock;
        synchronized (this) {
            // Update DB
            new DBCon().DBUpdate(this.name, "Ordered",null);
            minStock = new DBCon().getMinStock(this.name);
        }

        // Deliver
        Notification.AddLogEntry(this.name + " is being delivered");

        // Simulate deliver time
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Update DB when delivered
        synchronized (this) {
            new DBCon().DBUpdate(this.name, "In Stock",Integer.toString(minStock));
        }

        Notification.AddLogEntry(this.name + " was delivered and added to stock");
    }
}

