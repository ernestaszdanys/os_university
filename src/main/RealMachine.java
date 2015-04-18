package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealMachine {

    private static CPU CPU = new CPU();
    private static PMMU pmmu = new PMMU();
    public static final int MEMORY_SIZE = 256 * PMMU.WORDS_IN_BLOCK;
    public final static int VM_SIZE_IN_BLOCKS = 16;
    private static Memory realMemory = new Memory(MEMORY_SIZE);
    private static Memory externalMemory = new Memory(MEMORY_SIZE);
    private static OutputDevice outputDevice = new OutputDevice();
    private static InputDevice inputDevice = new InputDevice();
    private static VirtualMachine currentVirtualMachine;
    public static int PTR_TABLE_ADDRESS = PageTable.findFreePage().getPageIndex() * PMMU.WORDS_IN_BLOCK;
    private static int[] indexes = new int[15];
    static {
        for (int i = 0; i < 15; i++) {
            indexes[i] = -1;
        }
    }

    private static Map<Integer, VirtualMachine> virtualMachines;

    static {
        virtualMachines = new HashMap<Integer, VirtualMachine>();
    }

    public RealMachine(){

    }

    public static VirtualMachine createVirtualMachine(){
        // memory allocation
        main.CPU.setMODE(main.CPU.SUPERVISOR);
        Page tablePage = PageTable.findFreePage();
        int pageTableRealAddress = tablePage.getPageIndex() * PMMU.WORDS_IN_BLOCK;
        int index = getFreeIndex(CPU.getPID());
        if(index == -1) {
            return null;
        }
        PMMU.write(Word.intToWord(pageTableRealAddress), PTR_TABLE_ADDRESS + index);

        for(int i = 0; i < VM_SIZE_IN_BLOCKS; i++) {
            Page page = PageTable.findFreePage();
            PMMU.write(Word.intToWord(page.getPageIndex()), pageTableRealAddress+i);
        }
        main.CPU.setMODE(main.CPU.USER);
        VirtualMachine VM = new VirtualMachine(index);
        int temp = main.CPU.getPTR();
        main.CPU.setPTR(pageTableRealAddress);
        VM.savePID(CPU.getPID());
        main.CPU.setPID(CPU.getPID() + 1);
        VM.saveSP(VirtualMachine.STACK_START);
        main.CPU.setPTR(temp);
        virtualMachines.put(index, VM);
        return VM;
    }

    public static void unloadVirtualMachine(){
        CPU.setMODE(main.CPU.USER);
        currentVirtualMachine.savePC(CPU.getPC());
        currentVirtualMachine.saveSP(CPU.getSP());
        currentVirtualMachine = null;
    }

    public static void killVirtualMachine(int index){
        if(index < 0 || index > 15 || indexes[index] == -1)
        {
            main.CPU.setPI(1);
            return;
        }
        PMMU.write(Word.intToWord(0), PTR_TABLE_ADDRESS + index);
        freeIndexByPID(index);
        virtualMachines.remove(index);
    }

    public static void loadVirtualMachine(VirtualMachine VM){
        CPU.setMODE(main.CPU.SUPERVISOR);
        int pageTableRealAddress = Word.wordToInt(PMMU.read(PTR_TABLE_ADDRESS + VM.getIndex()));
        CPU.setPTR(pageTableRealAddress);
        CPU.setMODE(main.CPU.USER);
        currentVirtualMachine = VM;
        CPU.setPC(VM.getPC());
        CPU.setSP(VM.getSP());
    }

    public static void loadVirtualMachine(int index) {
        loadVirtualMachine(virtualMachines.get(index));
    }

    public static int getNextVirtualMachineIndex() {
        for (int i = currentVirtualMachine.getIndex() + 1; i < 15; i++) {
            if (indexes[i] != -1) {
                return i;
            }
        }

        for (int i = 0; i < 15; i++) {
            if (indexes[i] != -1) {
                return i;
            }
        }
        return -1;
    }

    public static String processInterupt(){
        String str = "";
        while (CPU.getInterrupt() != 0) {
            switch (CPU.getInterrupt()) {
                case 1:
                    str += "(TI = 0)Timer counter equals 0";
                    break;
                case 2:
                    str += "(PI = 1)Wrong address";
                    break;
                case 3:
                    str += "(PI = 2)Wrong operation code";
                    break;
                case 4:
                    str += "(PI = 3)Unable to assign";
                    break;
                case 5:
                    str += "(PI = 4)Overflow";
                    break;
                case 6:
                    //CPU.cmdPRTS();
                    break;
                case 7:
                    //CPU.cmdPRTN();
                    break;
                case 8:
                    //CPU.cmdP();
                    break;
                case 9:
                    //CPU.cmdREAD();
                    break;
                case 10:
                    //CPU.cmdSTOPF();
                    break;
                case 11:
                    //CPU.cmdFOxy();
                    break;
                default:
                    break;
            }
            CPU.resetInterrupts();
        }
        main.CPU.setCH2(1);
        outputDevice.printString(str);
        main.CPU.setCH2(0);
        return str;
    }

    // Getters
    public static Memory getRealMemory(){
        return realMemory;
    }

    public static CPU getCPU(){
        return CPU;
    }

    public static void printMemory(){
        realMemory.print();
    }

    public static int getFreeIndex(int PID){

        for(int i = 0; i < 15; i++) {
            if(indexes[i] == -1) {
                indexes[i] = PID;
                return i;
            }
        }
        return -1;
    }
    public static int freeIndexByPID(int PID){
        for(int i = 0; i < 15; i++) {
            if (indexes[i] == PID) {
                indexes[i] = -1;
            }
        }
        return -1;
    }

    public static VirtualMachine getCurrentVirtualMachine(){
        return currentVirtualMachine;
    }

    public static OutputDevice getOutputDevice(){
        return outputDevice;
    }

    public static void executeProgram(boolean step) {

        if(!step) {
            CPU.setPC(VirtualMachine.PROGRAM_START);
        }
        else {
            if(CPU.getPC() == 0 || CPU.getPC() == (VirtualMachine.PROGRAM_SIZE+VirtualMachine.PROGRAM_START)){
                CPU.setPC(VirtualMachine.PROGRAM_START);
            }
        }
        String cmdName = "";
        while (CPU.getPC() < (VirtualMachine.PROGRAM_SIZE+VirtualMachine.PROGRAM_START)) {

            char cc = (char) Word.wordToInt(PMMU.read(CPU.getPC()));
            CPU.setPC(CPU.getPC() + 1);
            if (cc != ' ' && cc != 0x0) {
                cmdName += cc;
            }
            else {
                continue;
            }
            if(cmdName.length() > 5){
                CPU.setPI(2);
                CPU.test();
                return;
            }
            //System.out.println(cmdName);
            for (Map.Entry<String, Integer> command : main.CPU.cmdList.entrySet()){
                if (cmdName.equals(command.getKey())) {
                    cmdName = "";

                    int number = 0;
                    char c = (char) Word.wordToInt(PMMU.read(CPU.getPC()));
                    do {
                        if ((('0' <= c) && (c <= '9')) || ((c >= 'a') && (c <= 'f'))) {
                            if(((c >= 'a') && (c <= 'f')))
                                number = number * 16 + (c - 'a' + 10);
                            else
                                number = number * 16 + (c - '0');
                            CPU.setPC(CPU.getPC()+1);
                            c = (char) Word.wordToInt(PMMU.read(CPU.getPC()));
                        }
                        else {
                            break;
                        }
                    } while (true);
                    Class[] cArg = new Class[command.getValue()];
                    if(command.getValue() == 1) {
                        cArg[0] = int.class;
                    }
                    else if(command.getValue() == 2) {
                        cArg[0] = int.class;
                        cArg[1] = int.class;
                    }
                    else
                        cArg = null;
                    try {
                        Method cmd = RealMachine.getCPU().getClass().getMethod("cmd" + command.getKey(), cArg);
                        if (command.getValue() == 1) {
                            cmd.invoke(RealMachine.getCPU(), number);
                        }
                        else if (command.getValue() == 2) {
                            cmd.invoke(RealMachine.getCPU(), number/16, number % 16);
                        }
                        else {
                            cmd.invoke(RealMachine.getCPU());
                        }
                        if(!CPU.test()){
                            return;
                        }
                        if(step){
                            return;
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
