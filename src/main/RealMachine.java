package main;

public class RealMachine {

    private CPU cpu;
    private PMMU pmmu;
    private Memory realMemory;
    private Memory externalMemory;
    // FIXME: add input and output devices


    public RealMachine(){
        cpu = new CPU();
        realMemory = new Memory();
        pmmu = new PMMU();
        cpu.setPMMU(pmmu);
        externalMemory = new Memory();
    }

    private int processInterupt(){
        while (cpu.getInterrupt() != 0){
            switch (cpu.getInterrupt()){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
            }
        }
    }

    // Getters
    public Memory getRealMemory(){
        return realMemory;
    }

}
