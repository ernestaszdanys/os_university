package main;

import javax.sound.midi.Soundbank;

public class RealMachine {

    private static CPU CPU = new CPU();
    private static PMMU pmmu = new PMMU();
    public static final int MEMORY_SIZE = 256 * PMMU.WORDS_IN_BLOCK;
    public final static int VM_SIZE_IN_BLOCKS = 16;
    private static Memory realMemory = new Memory(MEMORY_SIZE);
    private Memory externalMemory = new Memory(MEMORY_SIZE);
    private OutputDevice outputDevice = new OutputDevice();
    private InputDevice inputDevice = new InputDevice();
    private static VirtualMachine currentVirtualMachine;
    public static int PTR_TABLE_ADDRESS = PageTable.findFreePage().getPageIndex() * PMMU.WORDS_IN_BLOCK;
    private static boolean[] indexes = new boolean[15];

    public RealMachine(){

    }

    public static VirtualMachine createVirtualMachine(){
        // memory allocation

        CPU.setMODE(main.CPU.SUPERVISOR);
        Page tablePage = PageTable.findFreePage();
        int pageTableRealAddress = tablePage.getPageIndex() * PMMU.WORDS_IN_BLOCK;
        int index = getFreeIndex();
        if(index == -1) {
            return null;
        }
        PMMU.write(Word.intToWord(pageTableRealAddress), PTR_TABLE_ADDRESS + index);

        for(int i = 0; i < VM_SIZE_IN_BLOCKS; i++) {
            Page page = PageTable.findFreePage();
            PMMU.write(Word.intToWord(page.getPageIndex()), pageTableRealAddress+i);
        }
        CPU.setMODE(main.CPU.USER);
        VirtualMachine VM = new VirtualMachine(index);
        int temp = main.CPU.getPTR();
        CPU.setPTR(pageTableRealAddress);
        VM.savePID(CPU.getPID());
        CPU.setPID(CPU.getPID() + 1);
        CPU.setPTR(temp);
        return VM;
    }

    public static void unloadVirtualMachine(){
        CPU.setMODE(main.CPU.USER);
        currentVirtualMachine.savePC(CPU.getPC());
        currentVirtualMachine.saveSP(CPU.getSP());
        currentVirtualMachine = null;
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

    private int processInterupt(){
        while (CPU.getInterrupt() != 0){
            switch (CPU.getInterrupt()){
                case 1:
                    outputDevice.printString("(TI = 0)Timer counter equals 0");
                    CPU.resetInterrupts();
                    break;
                case 2:
                    outputDevice.printString("(PI = 1)Wrong address");
                    CPU.resetInterrupts();
                    break;
                case 3:
                    outputDevice.printString("(PI = 2)Wrong operation code");
                    CPU.resetInterrupts();
                    break;
                case 4:
                    outputDevice.printString("(PI = 3)Unable to assign");
                    CPU.resetInterrupts();
                    break;
                case 5:
                    outputDevice.printString("(PI = 4)Overflow");
                    CPU.resetInterrupts();
                    break;
                case 6:
                    //CPU.cmdPRTS();
                    CPU.resetInterrupts();
                    break;
                case 7:
                    CPU.cmdPRTN();
                    CPU.resetInterrupts();
                    break;
                case 8:
                    //CPU.cmdP();
                    CPU.resetInterrupts();
                    break;
                case 9:
                    CPU.cmdREAD();

                    CPU.resetInterrupts();
                    break;
                case 10:
                    //CPU.cmdSTOPF();
                    CPU.resetInterrupts();
                    break;
                case 11:
                    //CPU.cmdFOxy();
                    CPU.resetInterrupts();
                    break;
                default:
                    break;
            }
        }
        return 0;
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

    private static int getFreeIndex(){

        for(int i = 0; i < 15; i++) {
            if(!indexes[i]) {
                indexes[i] = true;
                return i;
            }
        }
        return -1;
    }


}
