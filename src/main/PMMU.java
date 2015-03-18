package main;

/**
 * Created by Rokas on 2015-03-17.
 */
public class PMMU {
    public final static int BLOCKSIZE = 16;
    private final int userBlocks;
    private final int supervisorBlocks;
    private CPU cpu;
    private RealMemory realMemory;
    private RealMachine realMachine;

    public PMMU(CPU cpu, Memory realMemory, RM realMachine){
        this.cpu = cpu;
        this.realMemory = realMemory;
        this.realMachine = realMachine;
    }
}
