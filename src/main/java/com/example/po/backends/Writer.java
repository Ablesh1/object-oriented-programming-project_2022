package com.example.po.backends;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public void writeTransfers(String str)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Transfer.txt", true));
        //FileWriter writer = new FileWriter("Transfers.txt");
        writer.write(str + "\n");
        writer.close();
    }

    public void writeWithdraws(String str)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Withdraws.txt", true));
        //FileWriter writer = new FileWriter("Transfers.txt");
        writer.write(str + "\n");
        writer.close();
    }
}
