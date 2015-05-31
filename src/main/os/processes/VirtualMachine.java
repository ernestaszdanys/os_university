package main.os.processes;

import main.CPU;
import main.RealMachine;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class VirtualMachine extends main.os.Process {

    public VirtualMachine(){
        super.name = "VirtualMachine";
    }

    private main.VirtualMachine VM;

    public VirtualMachine (main.VirtualMachine VM) {
        this.VM = VM;
    }

    public void run() {
        RealMachine.getCPU().setMODE(CPU.USER);
        RealMachine.loadVirtualMachine(VM);
        RealMachine.executeProgram(true);

        // TODO: throw catch. create resource interrupt
        // TODO: set interrupt info, vm id
    }
}
