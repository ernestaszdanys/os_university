package main;

public class VirtualMachine {

    private VirtualCPU virtualCPU;

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
        this.virtualCPU = new VirtualCPU();
        this.virtualMemory = new VirtualMemory(STACK_START+STACK_SIZE);
    }

    public VirtualMemory getVirtualMemory(){
        return virtualMemory;
    }

    public void printMemory() {
        for(int i = 0; i < STACK_START+STACK_SIZE; i++)
            System.out.println(virtualMemory.getMemory()[i] + " ");
    }
}
