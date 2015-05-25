package main.os;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kompiuteris on 15-05-21.
 */
public class Process {
    public static final int RUN = 0;
    public static final int READY = 1;
    public static final int BLOCK = 2;
    public static final int READYS = 3;
    public static final int BLOCKS = 4;

    public int id;
    //public int cpu = new ArrayList<Integer>();
    //public static List<Integer> p = new ArrayList<Integer>();
    //public static List<Integer> oa = new ArrayList<Integer>();
    public List<Resource> resources;
    public List<Resource> createdResources;
    public int status;
    //public int sd;
    public int father;
    public List<Integer> children = new ArrayList<Integer>();
    public int priority;

}
