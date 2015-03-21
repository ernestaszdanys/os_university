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

    public void savePC(int PC){
        PMMU.write(Word.intToWord(PC), PC_ADDRESS);
    }
    public void saveSP(int SP){
        PMMU.write(Word.intToWord(SP), SP_ADDRESS);
    }
    public void savePID(int PID){
        PMMU.write(Word.intToWord(PID), PID_ADDRESS);
    }
}
