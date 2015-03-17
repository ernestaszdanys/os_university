package main;

public class VirtualMachine {

    // Registers
    private int PC;
    private int SP;
    private int PID;


    // Default constructor
    public VirtualMachine(){
        setPC(PC);
        setSP(SP);
        setPID(PID);
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
}
