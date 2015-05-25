package main.os;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kompiuteris on 15-05-21.
 */
public class Planner {
    public static List<Process> ready = new ArrayList<Process>();
    public static List<Process> blocked = new ArrayList<Process>();

    public static Process currentProcess;
}
