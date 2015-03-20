package main;

/**
 * Created by Rokas on 2015-03-17.
 */
public class PMMU {
    /*public final static int BLOCKSIZE = 16;
    private final int userBlocks;
    private final int supervisorBlocks;
    private CPU cpu;
    private Memory realMemory;
    private RealMachine realMachine;

    public PMMU(CPU cpu, Memory realMemory, RM realMachine){
        this.cpu = cpu;
        this.realMemory = realMemory;
        this.realMachine = realMachine;
    }*/
    private static VirtualMachine virtualMachine;

    static void setVirtualMachine(VirtualMachine virtualMachine) {
        PMMU.virtualMachine = virtualMachine;
    }

    static VirtualMachine getVirtualMachine() {
        return PMMU.virtualMachine;
    }

    static void write(Word word, int address){
        PMMU.virtualMachine.getVirtualMemory().write(word, address);
    }

    static Word read(int address) {
        return PMMU.virtualMachine.getVirtualMemory().read(address);
    }
}
