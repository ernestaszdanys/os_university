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

    public final static int BLOCK_SIZE = 256;
    public final static int WORDS_IN_BLOCK = 16;
    private static VirtualMachine virtualMachine; // to -> RM

    static void setVirtualMachine(VirtualMachine virtualMachine) {
        PMMU.virtualMachine = virtualMachine;
    } // to -> RM

    static VirtualMachine getVirtualMachine() {
        return PMMU.virtualMachine;
    }// to -> RM

    static void write(Word word, int address) {
        PMMU.virtualMachine.getVirtualMemory().write(word, address);
    }

    static Word read(int address) {

        if (RealMachine.getCPU().getMODE() == CPU.SUPERVISOR) {
            return RealMachine.getMemory().read(address);
        } else if (RealMachine.getCPU().getMODE() == CPU.USER) {
            int realAddress = virtualToRealAddress(address);
            // TO DO: can VM read this?
            return RealMachine.getMemory().read(realAddress);
        }

        //return PMMU.virtualMachine.getVirtualMemory().read(address);
    }

    private static int virtualToRealAddress(int address) {
        return WORDS_IN_BLOCK * [RealMachine.getMemory().read(WORDS_IN_BLOCK * (WORDS_IN_BLOCK * ((CPU.getPTR() & 0x0000ff00) >> 8) + (CPU.getPTR() & 0x000000ff)) + ((address & 0x0000ff00) >> 8))] + (CPU.getPTR() & 0x000000ff);
    }
}
