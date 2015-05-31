package main.os;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marius on 2015-05-21.
 */
public class Primitives {

    public static void createProcess(Process process, int processId, List<Resource> resourceList, int priority) {
        System.out.println("creating process " + process.name);
        process.id = processId;
        process.resources = resourceList;
        process.createdResources = new ArrayList<Resource>();
        process.status = Process.READY;
        process.father = Planner.currentProcess.id;
        process.children = new ArrayList<Integer>();
        process.priority = priority;

        ProcessDescriptor.processes.add(process);
    }

    public static void deleteProcess(int id) {
        // TODO:
        // planuotojas ir palikuonys
        // procui duot t?v?�
        ProcessDescriptor.processes.remove(getProcessIndex(id));
    }

    public static void stopProcess(int id) {
        int index = getProcessIndex(id);
        System.out.println("stopping process " + ProcessDescriptor.processes.get(index).name);
        int status = ProcessDescriptor.processes.get(index).status;
        if (status == Process.RUN) {
            ProcessDescriptor.processes.get(index).status = Process.READY;
            Planner.currentProcess.status = Process.BLOCK;
            Planner.run();
        }
        if (status == Process.BLOCK || status == Process.BLOCKS) {
            ProcessDescriptor.processes.get(index).status = Process.BLOCKS;
        }
        else {
            ProcessDescriptor.processes.get(index).status = Process.READYS;
        }
        if (status == Process.RUN) {
            Planner.run();
        }
    }

    public static void activateProcess(int id) {
        int index = getProcessIndex(id);
        System.out.println("active process " + ProcessDescriptor.processes.get(index).name);
        int status = ProcessDescriptor.processes.get(index).status;
        if (status == Process.READYS) {
            ProcessDescriptor.processes.get(index).status = Process.READY;
        }
        else {
            ProcessDescriptor.processes.get(index).status = Process.BLOCK;
        }
        if (status == Process.READY) {
            Planner.run();
        }
    }

    public static void changeProcessPriority(int id, int priority) {
        int index = getProcessIndex(id);
        int lastPriority = ProcessDescriptor.processes.get(index).priority;
        ProcessDescriptor.processes.get(index).priority = priority;
        if (priority > lastPriority && ProcessDescriptor.processes.get(index).status == Process.READY) {
            Planner.run();
        }
    }

    public static void createResource(int id, String name, boolean reusable) {
        System.out.println("create resource " + name);
        Resource resource = new Resource(id, name, reusable, Planner.currentProcess.id);
        ResourceDescriptor.resources.add(resource);
    }

    public static void deleteResource(String name) {
        System.out.println("delete resource " + name);
        int index = getResourceIndex(name);
        for (Process process : ResourceDescriptor.resources.get(index).waitingProcesses) {
            if (process.status == Process.BLOCK) {
                process.status = Process.READY;
            }
            else {
                process.status = Process.READYS;
            }
            if (Planner.blocked.contains(process)) {
                Planner.blocked.remove(process);
            }
            Planner.ready.add(process);
        }
        ResourceDescriptor.resources.remove(index);
        Planner.run();
    }


    public static void requestResource(String name) {
        System.out.println("request resource " + name);
        int index = getResourceIndex(name);
        Resource resource = ResourceDescriptor.resources.get(index);
        resource.waitingProcesses.add(Planner.currentProcess);
        List<Process> servedProcesses = ResourceDivider.run(resource);
        boolean found = true;
        for (Process process : servedProcesses) {
            if (process.id != Planner.currentProcess.id) {
                if (Planner.blocked.contains(process)) {
                    Planner.blocked.remove(process);
                }
                Planner.ready.add(process);
                if (process.status == Process.BLOCK) {
                    process.status = Process.READY;
                }
                else {
                    process.status = Process.READYS;
                }
            }
            else {
                found = false;
            }
        }
        if (found) {
            Planner.currentProcess.status = Process.BLOCK;
            Planner.ready.remove(Planner.currentProcess);
            Planner.currentProcess = null;
        }
        Planner.run();
    }

    public static void freeResource(String name) {
        System.out.println("free resource " + name);
        int index = getResourceIndex(name);
        Resource resource = ResourceDescriptor.resources.get(index);
        List<Process> servedProcesses = ResourceDivider.run(resource);
        for (Process process : servedProcesses) {
            if (process.status == Process.BLOCK) {
                process.status = Process.READY;
            }
            else {
                process.status = Process.READYS;
            }
        }

        if (servedProcesses.size() != 0) {
            Planner.run();
        }
    }

    private static int getProcessIndex(int id) {
        for (int i = 0; i < ProcessDescriptor.processes.size(); i++) {
            if (ProcessDescriptor.processes.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }
    private static int getResourceIndex(int id) {
        for (int i = 0; i < ResourceDescriptor.resources.size(); i++) {
            if (ResourceDescriptor.resources.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }
    private static int getResourceIndex(String name) {
        for (int i = 0; i < ResourceDescriptor.resources.size(); i++) {
            if (ResourceDescriptor.resources.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
