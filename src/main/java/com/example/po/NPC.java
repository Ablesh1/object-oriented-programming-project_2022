package com.example.po;


//Here be general NPC and their behaviours
//We will put some special NPCs in different classes
public class NPC extends Thread{

    private Integer id;

    public NPC(Integer idNumber){
        this.id = idNumber;
    }



    //Suicide method kills the NPC
    //Yes, this is the first funciton I wrote
    //Yes, it`s only to show that we know how to do it
    public int Suicide(){
        System.out.println(this.id + "FUCKING KILLED HIMSELF");
        this.interrupt();
        return 1;
    }
}
