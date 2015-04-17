package main;
// TODO: add fork :(

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CPU {

    // Registers
    private static int PTR;
    private static int PC;
    private static int SP;
    private static int SM;
    private static int PID;
    private static int MODE;
    private static int TI;
    private static int PI;
    private static int SI;
    private static int CH1;
    private static int CH2;
    private static int CH3;

    // Commands

    // Additional variables
    private int supervisor = 0;
    private int time = 30;

    public static final Map<String, Integer> cmdList;
    static {
        Map<String, Integer> tempMap = new HashMap<String, Integer>();
        tempMap.put("ADD", 0);
        tempMap.put("SUB", 0);
        tempMap.put("MUL", 0);
        tempMap.put("DIV", 0);
        tempMap.put("WR",  1);
        tempMap.put("RD",  1);
        tempMap.put("CMP", 0);
        tempMap.put("CPID", 0);
        tempMap.put("LD",  2);
        tempMap.put("PT",  2);
        tempMap.put("PUN", 1);
        tempMap.put("PUS", 1);
        tempMap.put("JP", 2);
        tempMap.put("JE", 2);
        tempMap.put("JL", 2);
        tempMap.put("JG", 2);
        tempMap.put("FO", 2);
        cmdList = Collections.unmodifiableMap(tempMap);
    }

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
    public static void setPTR(int PTR) {
        CPU.PTR = PTR;
    }

    public static void setPC(int PC) {
        CPU.PC = PC;
    }

    public static void setSP(int SP) {
        CPU.SP = SP;
    }

    public static void setSM(int SM) {
        CPU.SM = SM;
    }

    public static void setPID(int PID) {
        CPU.PID = PID;
    }

    public static void setMODE(int MODE) {
        CPU.MODE = MODE;
    }

    public static void setTI(int TI) {
        CPU.TI = TI;
    }

    public static void setPI(int PI) {
        CPU.PI = PI;
    }

    public static void setSI(int SI) {
        CPU.SI = SI;
    }

    public static void setCH1(int CH1) {
        CPU.CH1 = CH1;
    }

    public static void setCH2(int CH2) {
        CPU.CH2 = CH2;
    }

    public static void setCH3(int CH3) {
        CPU.CH3 = CH3;
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
        //System.out.println(Word.wordToInt(PMMU.read(SP)) + ">" + Word.wordToInt(PMMU.read(SP-1)));
        if (Word.wordToInt(PMMU.read(SP)) > Word.wordToInt(PMMU.read(SP-1))){
            PMMU.write(Word.intToWord(0), SP);
        } else if (Word.wordToInt(PMMU.read(SP)) == Word.wordToInt(PMMU.read(SP-1))){
            PMMU.write(Word.intToWord(1), SP);
        } else {
            PMMU.write(Word.intToWord(2), SP);
        }
        //SP++;
    }

    public void cmdCPID(){
        if (Word.wordToInt(PMMU.read(SP)) != PID){
            PMMU.write(Word.intToWord(0), SP);
        } else {
            PMMU.write(Word.intToWord(1), SP);
        }
        //SP--;
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

    public void cmdJP(int x, int y){
        setPC(PMMU.WORDS_IN_BLOCK * x + y);
    }

    public void cmdJE(int x, int y){
        if(Word.wordToInt(main.PMMU.read(SP)) == 1){
            setPC(PMMU.WORDS_IN_BLOCK * x + y);
            SP--;
        }
    }

    public void cmdJL(int x, int y){
        if(Word.wordToInt(main.PMMU.read(SP)) == 0){
            setPC(PMMU.WORDS_IN_BLOCK * x + y);
            SP--;
        }
    }

    public void cmdJG(int x, int y){
        if(Word.wordToInt(main.PMMU.read(SP)) == 2){
            setPC(PMMU.WORDS_IN_BLOCK * x + y);
            SP--;
        }
    }

    public void cmdFO(int x, int y){
        // clone
        CPU.setMODE(CPU.USER);
        System.out.println(CPU.getPC());
        RealMachine.getCurrentVirtualMachine().savePC(CPU.getPC());
        RealMachine.getCurrentVirtualMachine().saveSP(CPU.getSP());

        VirtualMachine VM = RealMachine.getCurrentVirtualMachine().clone();

        PMMU.write(Word.intToWord(RealMachine.getCPU().getPID()), PMMU.WORDS_IN_BLOCK * x + y);

        RealMachine.unloadVirtualMachine();
        RealMachine.loadVirtualMachine(VM);
        for(int i = 0; i < VirtualMachine.MEMORY_SIZE; i++){
            PMMU.write(VM.getVirtualMemory().read(i), i);
        }
        CPU.setSP(VM.getSP());
        CPU.setPC(VM.getPC());
    }

    public void cmdST(int x, int y){
        int PID = Word.wordToInt(PMMU.read(PMMU.WORDS_IN_BLOCK * x + y));
        if(PID != 0){
            PMMU.write(Word.intToWord(0), PMMU.WORDS_IN_BLOCK * x + y);
            RealMachine.killVirtualMachine(PID);
        }
    }


    // Getters
    public static int getPTR() {
        return PTR;
    }

    public static int getPC() {
        return PC;
    }

    public static int getSP() {
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
                            if(w.getByte(i) != 0x0)
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
