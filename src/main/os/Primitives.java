package main.os;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marius on 2015-05-21.
 */
public class Primitives {

    public static void createProcess(int processId, List<Resource> resourceList, int priority) {
        Process process = new Process();
        process.id = processId;
        process.resources = resourceList;
        process.createdResources = new ArrayList<Resource>();
        process.status = Process.READY;
        process.father = 69;
        process.children = new ArrayList<Integer>();
        process.priority = priority;

        ProcessDescriptor.processes.add(process);
    }

    public static void deleteProcess(int id) {
        // TODO:
        // planuotojas ir palikuonys
        // procui duot tėvą
        ProcessDescriptor.processes.remove(getProcessIndex(id));
    }
    public static void stopProcess(int id) {
        int index = getProcessIndex(id);
        int status = ProcessDescriptor.processes.get(index).status;
        if (status == Process.RUN) {
            ProcessDescriptor.processes.get(index).status = Process.READY;
            // TODO: atimt nuo proceso procesoriu
        }
        if (status == Process.BLOCK || status == Process.BLOCKS) {
            ProcessDescriptor.processes.get(index).status = Process.BLOCKS;
        }
        else {
            ProcessDescriptor.processes.get(index).status = Process.READYS;
        }
        if (status == Process.RUN) {
            // TODO: then planuotojas
        }
    }

    public static void activateProcess(int id) {
        int index = getProcessIndex(id);
        int status = ProcessDescriptor.processes.get(index).status;
        if (status == Process.READYS) {
            ProcessDescriptor.processes.get(index).status = Process.READY;
        }
        else {
            ProcessDescriptor.processes.get(index).status = Process.BLOCK;
        }
        if (status == Process.READY) {
            // TODO: then planuotojas
        }
    }

    public static void changeProcessPriority(int id, int priority) {
        int index = getProcessIndex(id);
        int lastPriority = ProcessDescriptor.processes.get(index).priority;
        ProcessDescriptor.processes.get(index).priority = priority;
        if (priority > lastPriority && ProcessDescriptor.processes.get(index).status == Process.READY) {
            // TODO: then planuotojas
        }
    }

    public static void createResource() {
    }

    public static void deleteResource(){}
    public static void requestResource(){}
    public static void freeResource(){}

    private static int getProcessIndex(int id) {
        for (int i = 0; i < ProcessDescriptor.processes.size(); i++) {
            if (ProcessDescriptor.processes.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }
}
