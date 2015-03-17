package main;

public class CPU {

    // Registers
    private int PTR;
    private int PC;
    private int SP;
    private int SM;
    private int PID;
    private int MODE;
    private int TI;
    private int PI;
    private int SI;
    private int CH1;
    private int CH2;
    private int CH3;

    // Commands
    // TODO: add commands

    // Additional variables
    private int supervisor = 0;
    private int time = 20;

    public CPU() {
        // FIXME: PTR should be 0?
        PTR = 0;
        PC = 0;
        SP = 0;
        SM = 0;
        PID = 0;
        TI = 0;
        PI = 0;
        SI = 0;
        CH1 = 0;
        CH2 = 0;
        CH3 = 0;
        MODE = supervisor;
    }

    public void resetInterrupts(){
        TI = time;
    }
}
