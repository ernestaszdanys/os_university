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

    final int PC_ADDRESS = 109;
    final int SP_ADDRESS = 110;
    final int PID_ADDRESS = 111;

    final int PTR_ADDRESS = 108;


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

    public int getPC(){
        return Word.wordToInt(PMMU.read(PC_ADDRESS));
    }
    public int getSP(){
        return Word.wordToInt(PMMU.read(SP_ADDRESS));
    }
    public int getPID(){
        return Word.wordToInt(PMMU.read(PID_ADDRESS));
    }
    public int loadPTR(){
        return Word.wordToInt(PMMU.read(PTR_ADDRESS));
    }

    public void setPC(int PC){
        PMMU.write(Word.intToWord(PC), PC_ADDRESS);
    }
    public void setSP(int SP){
        PMMU.write(Word.intToWord(SP), SP_ADDRESS);
    }
    public void setPID(int PID){
        PMMU.write(Word.intToWord(PID), PID_ADDRESS);
    }
    public void savePTR(int PTR){
        PMMU.write(Word.intToWord(PTR), PTR_ADDRESS);
    }
}
