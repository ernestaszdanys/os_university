package main.os;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kompiuteris on 15-05-21.
 */
public class Resource {
    public int id;
    public String name;
    public boolean reusable;
    public int father;
    public List<Process> waitingProcesses;
    public String info;
    //public List readyList;
    //public List blockedList;
    //public int paskirstytojas;


    public Resource(int id, String name, boolean reusable, int father) {
        this.id = id;
        this.name = name;
        this.reusable = reusable;
        this.father = father;
        this.waitingProcesses = new ArrayList<Process>();
    }
}
