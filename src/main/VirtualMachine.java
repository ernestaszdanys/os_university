package main;

public class VirtualMachine {

    // Registers
    private int PC;
    private int SP;
    private int PID;

    // Memory

    private VirtualMemory virtualMemory;

    final int DATA_START = 0;
    final int DATA_SIZE = 112;

    final int PROGRAM_START = 112;
    final int PROGRAM_SIZE = 112;

    final int STACK_START = 224;
    final int STACK_SIZE = 32;


    // Default constructor
    public VirtualMachine(){
        setPC(PC);
        setSP(SP);
        setPID(PID);
        this.virtualMemory = new VirtualMemory(STACK_START+STACK_SIZE);
    }


    // Setters
    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }


    // Getters
    public int getPC() {
        return PC;
    }

    public int getSP() {
        return SP;
    }

    public int getPID() {
        return PID;
    }

    public VirtualMemory getVirtualMemory(){
        return virtualMemory;
    }

    public void printMemory() {
        for(int i = 0; i < STACK_START+STACK_SIZE; i++)
            System.out.println(virtualMemory.getMemory()[i] + " ");
    }
}
