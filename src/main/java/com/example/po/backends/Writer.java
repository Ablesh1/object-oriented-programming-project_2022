package com.example.po.backends;
import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer{

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

    public ArrayList<String> readLastTransfers(){
        ArrayList<String> transfers = new ArrayList<>();
                try{
                File file = new File("Transfer.txt");
                int n_lines = 9;
                int counter = 0;
                ReversedLinesFileReader object = null;
                try {
                    object = new ReversedLinesFileReader(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return transfers;
                }
                while(counter < n_lines) {
                    try {
                        transfers.add(object.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return transfers;
                    }
                    counter++;
                }
                return transfers;}
                catch (NullPointerException n){
                    return transfers;
                }
    }

    public void readLastWithdraws(){
                File file = new File("Withdraws.txt");
                int n_lines = 10;
                int counter = 0;
                ReversedLinesFileReader object = null;
                try {
                    object = new ReversedLinesFileReader(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                while(counter < n_lines) {
                    try {
                        System.out.println(object.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    counter++;
                }
                return;}

}
