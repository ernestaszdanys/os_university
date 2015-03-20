package main;
// TODO: add commands

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
    private PMMU PMMU;
    private int supervisor = 0;
    private int time = 30;


    // Default constructor
    public CPU() {
        // FIXME: PTR should be 0?
        setPTR(0);
        setPC(0);
        setSP(0);
        setSM(0);
        setPID(0);
        setTI(0);
        setPI(0);
        setSI(0);
        setCH1(0);
        setCH2(0);
        setCH3(0);
        setMODE(supervisor);
    }

    public void resetInterrupts(){
        TI = time;
        SI = 0;
        PI = 0;
    }

    public int getInterrupt() {
        if(TI == 0) {
            return 1;
        }

        switch (PI) {
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            case 4: return 5;
        }

        switch(SI) {
            case 1: return 6;
            case 2: return 7;
            case 3: return 8;
            case 4: return 9;
            case 5: return 10;
            case 6: return 11;
        }

        return 0;
    }


    // Setters
    public void setPTR(int PTR) {
        this.PTR = PTR;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public void setSM(int SM) {
        this.SM = SM;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public void setMODE(int MODE) {
        this.MODE = MODE;
    }

    public void setTI(int TI) {
        this.TI = TI;
    }

    public void setPI(int PI) {
        this.PI = PI;
    }

    public void setSI(int SI) {
        this.SI = SI;
    }

    public void setCH1(int CH1) {
        this.CH1 = CH1;
    }

    public void setCH2(int CH2) {
        this.CH2 = CH2;
    }

    public void setCH3(int CH3) {
        this.CH3 = CH3;
    }

    public void setPMMU(PMMU PMMU){
        this.PMMU = PMMU;
    }

    public void cmdADD()
    {
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) + Word.wordToInt(PMMU.read(SP - 1))), SP-1);
        SP--;
    }

    public void cmdPUNx(int x){
        SP++;
        main.PMMU.write(Word.intToWord(x), SP);
    }

    public void cmdPRTN(){

    }


    // Getters
    public int getPTR() {
        return PTR;
    }

    public int getPC() {
        return PC;
    }

    public int getSP() {
        return SP;
    }

    public int getSM() {
        return SM;
    }

    public int getPID() {
        return PID;
    }

    public int getMODE() {
        return MODE;
    }

    public int getTI() {
        return TI;
    }

    public int getPI() {
        return PI;
    }

    public int getSI() {
        return SI;
    }

    public int getCH1() {
        return CH1;
    }

    public int getCH2() {
        return CH2;
    }

    public int getCH3() {
        return CH3;
    }

    public PMMU getPMMU() {
        return PMMU;
    }
}
