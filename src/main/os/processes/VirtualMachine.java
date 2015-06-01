package main.os.processes;

import main.CPU;
import main.Main;
import main.RealMachine;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class VirtualMachine extends main.os.Process {

    private main.VirtualMachine VM;

    public VirtualMachine (main.VirtualMachine VM) {
        this.VM = VM;
        super.name = "VirtualMachine";
    }

    public void run() {
        if(step == 0) {
            step++;
            RealMachine.getCPU().setMODE(CPU.USER);
            RealMachine.loadVirtualMachine(VM);
            CPU.cmdREAD();
            CPU.test();
        }
        RealMachine.executeProgram(true);
        Main.getGUI().redraw();
    }
}
