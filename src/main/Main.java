package main;

import javax.swing.*;

public class Main {

    private static GUI GUI;
    public static void main(String[] args){
        VirtualMachine VM1 = RealMachine.createVirtualMachine();
        RealMachine.loadVirtualMachine(VM1);

        JFrame frame = new JFrame("RM");
        GUI = new GUI();
        frame.setContentPane(GUI.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        GUI.redraw();

        CPU.cmdREAD();
        CPU.test();

        GUI.redraw();
    }

    public static GUI getGUI(){
        return GUI;
    }

}
