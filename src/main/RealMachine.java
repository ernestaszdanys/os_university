package main;

public class RealMachine {

    private CPU CPU;
    private PMMU pmmu;
    private Memory realMemory;
    private Memory externalMemory;
    private OutputDevice outputDevice;
    private InputDevice inputDevice;

    private int memorySize = 256;

    public RealMachine(){
        CPU = new CPU();
        realMemory = new Memory(memorySize);
        pmmu = new PMMU();
        CPU.setPMMU(pmmu);
        externalMemory = new Memory(memorySize);
        outputDevice = new OutputDevice();
        inputDevice = new InputDevice();
    }

    public VirtualMachine createVirtualMachine(){
        VirtualMachine VM = new VirtualMachine();
        PMMU.setVirtualMachine(VM);
        return VM;
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
                    //CPU.cmdREAD();
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
    public Memory getRealMemory(){
        return realMemory;
    }

    public CPU getCPU(){
        return CPU;
    }

}
