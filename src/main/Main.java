package main;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {


    public static void main(String[] args){
        RealMachine realMachine = new RealMachine();

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
        }
    }

}
