package main;

public class Main {


    public static void main(String[] args){
        /*RealMachine realMachine = new RealMachine();

        VirtualMachine VM1 = realMachine.createVirtualMachine();
        CPU CPU = realMachine.getCPU();

        CPU.cmdPUNx(10);
        CPU.cmdPUNx(45);
        CPU.cmdADD();

        VM1.printMemory();

        // Reading file example
        try {
            InputDevice.openFile();
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file name.");
        }

        Word[] words;
        try {
            while (true) {
                words = InputDevice.getInput();
                for (Word w : words) {
                    System.out.println(w);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading finished.");
        }*/

        //for(int i = 0; i < 2000; i++)
        //{
        /*VirtualMachine VM1 = RealMachine.createVirtualMachine();
        RealMachine.loadVirtualMachine(VM1);

        RealMachine.getCPU().setSP(134);

        RealMachine.getCPU().cmdPUNx(10);
        RealMachine.getCPU().cmdPUNx(12);
        RealMachine.getCPU().cmdADD();
        VirtualMachine VM2 = RealMachine.createVirtualMachine();

        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        RealMachine.loadVirtualMachine(VM2);
        RealMachine.getCPU().setSP(200);
        RealMachine.getCPU().cmdPUNx(44);
        RealMachine.getCPU().cmdPUNx(90);
        RealMachine.getCPU().cmdADD();
        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM1);
        System.out.println(RealMachine.getCPU().getSP());
        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        VirtualMachine VM3 = RealMachine.createVirtualMachine();

        RealMachine.loadVirtualMachine(VM2);
        System.out.println(RealMachine.getCPU().getSP());
        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM3);
        System.out.println(RealMachine.getCPU().getSP());
        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM2);
        System.out.println(RealMachine.getCPU().getSP());
        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM1);
        RealMachine.getCPU().cmdPUNx(100);
        RealMachine.getCPU().cmdADD();
        System.out.println(RealMachine.getCPU().getSP());
        PMMU.printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");*/

        RealMachine.getCPU().cmdREAD();

        for (int i = 0; i < 7; i++) {
            PMMU.printBlock(VirtualMachine.PROGRAM_START + i*16);
        }
        //}


    }

}
