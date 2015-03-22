package main;
// TODO: add fork :(

import java.io.FileNotFoundException;

public class CPU {

    // Registers
    private static int PTR;
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
    private int time = 30;

    public static String[] cmdList = {"ADD", "SUB", "MUL", "DIV", "WR", "RD", "CMP", "CPID", "LD", "PT", "PUN", "PUS"};
    public static final int SUPERVISOR = 0;
    public static final int USER = 1;



    // Default constructor
    public CPU() {
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

    public void cmdADD(){
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) + Word.wordToInt(PMMU.read(SP - 1))), SP-1);
        SP--;
    }

    public void cmdSUB(){
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) - Word.wordToInt(PMMU.read(SP - 1))), SP-1);
        SP--;
    }

    public void cmdMUL(){
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) * Word.wordToInt(PMMU.read(SP - 1))), SP-1);
        SP--;
    }

    public void cmdDIV(){
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) / Word.wordToInt(PMMU.read(SP - 1))), SP-1);
        SP--;
    }

    public void cmdWR(int x){
        PMMU.write(PMMU.read(SP), x);
    }

    public void cmdRD(int x){
        PMMU.write(PMMU.read(x), SP);
        SP--;
    }

    public void cmdCMP(){
        if (Word.wordToInt(PMMU.read(SP)) > Word.wordToInt(PMMU.read(SP-1))){
            PMMU.write(Word.intToWord(0), SP+1);
        } else if (Word.wordToInt(PMMU.read(SP)) == Word.wordToInt(PMMU.read(SP-1))){
            PMMU.write(Word.intToWord(1), SP+1);
        } else {
            PMMU.write(Word.intToWord(2), SP+1);
        }
        SP--;
    }

    public void cmdCPID(){
        if (Word.wordToInt(PMMU.read(SP)) != PID){
            PMMU.write(Word.intToWord(0), SP);
        } else {
            PMMU.write(Word.intToWord(1), SP);
        }
        SP--;
    }

    public void cmdLD(int x, int y){
        PMMU.write(PMMU.read(RealMachine.VM_SIZE_IN_BLOCKS * x + y), SP);
        SP--;
    }

    public void cmdPT(int x, int y){
        SP++;
        PMMU.write(PMMU.read(SP), RealMachine.VM_SIZE_IN_BLOCKS * x + y);
    }

    public void cmdPUN(int x){
        SP++;
        main.PMMU.write(Word.intToWord(x), SP);
    }

    public void cmdPUS(Word x){
        SP++;
        main.PMMU.write(x, SP);
    }

    public void cmdPRTN(){

    }


    // Getters
    public static int getPTR() {
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

    public static void cmdREAD() {
        try {
            InputDevice.openFile();
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file name.");
        }

        Word[] words;
        String line;
        int counter = 0;
        try {
            line = Word.wordsToString(InputDevice.getInput());

            if (line.equals("DATA")) {
                words = InputDevice.getInput();
                line = Word.wordsToString(words);
                while (!line.equals("CODE")) {
                    for (Word w : words) {
                        for (int i = 0; i < 4; i++) {
                            byte b = w.getByte(i);
                            if (b != 0x0) {
                                PMMU.write(Word.intToWord(b), VirtualMachine.DATA_START + counter++);
                            }
                        }
                    }
                    words = InputDevice.getInput();
                    line = Word.wordsToString(words);
                }

                counter = 0;
                words = InputDevice.getInput();
                line = Word.wordsToString(words);
                while (!line.equals("STOP")) {
                    for (Word w : words) {
                        for (int i = 0; i < 4; i++) {
                            PMMU.write(Word.intToWord(w.getByte(i)), VirtualMachine.PROGRAM_START + counter++);
                        }
                    }
                    words = InputDevice.getInput();
                    line = Word.wordsToString(words);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading finished.");
        }
    }
}
