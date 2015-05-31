package main.os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Kompiuteris on 15-05-21.
 */
public class Planner {
    public static List<Process> ready = new ArrayList<Process>();
    public static List<Process> blocked = new ArrayList<Process>();

    public static Process currentProcess;

    public static void run() {
        System.out.println("Planner");
        ready.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o1.status - o2.status;
            }
        });
        ready.get(0).status = Process.RUN;
        ready.get(0).run();
    }
}
