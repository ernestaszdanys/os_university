package main;

public class Main {

    public static void main(String[] args){
        RealMachine realMachine = new RealMachine();

        VirtualMachine VM1 = realMachine.createVirtualMachine();
        CPU CPU = realMachine.getCPU();

        CPU.cmdPUNx(10);
        CPU.cmdPUNx(45);
        CPU.cmdADD();

        VM1.printMemory();

    }

}
