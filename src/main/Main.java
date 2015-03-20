package main;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.FileNotFoundException;
import java.io.IOException;

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
        VirtualMachine VM1 = RealMachine.createVirtualMachine();
        RealMachine.loadVirtualMachine(VM1);

        RealMachine.getCPU().setSP(134);

        RealMachine.getCPU().cmdPUNx(10);
        RealMachine.getCPU().cmdPUNx(12);
        RealMachine.getCPU().cmdADD();
        System.out.println("VM1: " + VM1.loadPTR());
        VirtualMachine VM2 = RealMachine.createVirtualMachine();
        System.out.println("VM2: " + VM2.loadPTR());

        RealMachine.getRealMemory().printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM2);
        RealMachine.getCPU().setSP(200);
        RealMachine.getCPU().cmdPUNx(44);
        RealMachine.getCPU().cmdPUNx(90);
        RealMachine.getCPU().cmdADD();
        RealMachine.getRealMemory().printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM1);
        System.out.println(RealMachine.getCPU().getSP());
        RealMachine.getRealMemory().printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        RealMachine.loadVirtualMachine(VM2);
        System.out.println(RealMachine.getCPU().getSP());
        RealMachine.getRealMemory().printBlock(RealMachine.getCPU().getSP());
        RealMachine.unloadVirtualMachine();


    }

}
