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
                int counter1 = 0;
                ReversedLinesFileReader object = null;
                try {
                    object = new ReversedLinesFileReader(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return transfers;
                }
                while(counter1 < n_lines) {
                    try {
                        transfers.add(object.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return transfers;
                    }
                    counter1 ++;
                }
                return transfers;
                }
                catch (NullPointerException n){
                    return transfers;
                }
    }

    public ArrayList<String> readLastWithdraws(){
        ArrayList<String> withdraws = new ArrayList<>();
                try{
                File file = new File("Withdraws.txt");
                int n_lines = 10;
                int counter2 = 0;
                ReversedLinesFileReader object = null;
                try {
                    object = new ReversedLinesFileReader(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return withdraws;
                }
                while(counter2 < n_lines) {
                    try {
                        withdraws.add(object.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return withdraws;
                    }
                    counter2 ++;
                }
                return withdraws;
                }
                catch (NullPointerException n){
                    return withdraws;
                }
    }
}