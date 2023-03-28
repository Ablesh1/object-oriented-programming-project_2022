package com.example.po.backends;

import com.example.po.backends.BankBackend;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class Department{

    // Department must know which bank it is in
    BankBackend bankBackend;

    public BankBackend getBankBackend() {
        return bankBackend;
    }

    // Each department has its own thread
    // We do not inherit from thread since we already inherit from Application
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    refresh();
                    Thread.sleep(200);
                }
                catch(InterruptedException | IOException interruptedException){
                    System.out.println("AAAAAAA");
                    return;
                }
            }
        }
    });

    public Department(){
        thread.start();
    }

    public Department(BankBackend bankBackend){
        this.bankBackend = bankBackend;
        thread.start();
    }

    //@Override
    public void start(Stage primaryStage) throws IOException {

    }

    // This is where the panels will update themselves and collect data from other classes
    // Which will run in the background all the time
    // E.g. Client databases
    public void refresh() throws IOException {
        System.out.println("Bruh");
    }

    public int kill(){
        thread.interrupt();
        System.out.println("Thread killed");
        return 0;
    }
}
