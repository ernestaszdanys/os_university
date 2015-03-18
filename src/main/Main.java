package main;

public class Main {

    public static void main(String[] args){
        CPU cpu = new CPU();
        VirtualMachine VM1 = new VirtualMachine();
        PMMU.setVirtualMachine(VM1);
        cpu.cmdPUNx(10);
        cpu.cmdPUNx(45);
        cpu.cmdADD();
        VM1.printMemory();

    }

}
