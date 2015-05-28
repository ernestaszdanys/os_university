package main.os.processes;

import main.CPU;
import main.RealMachine;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class VirtualMachine extends main.os.Process {

    public void run() {
        RealMachine.getCPU().setMODE(CPU.USER);
        RealMachine.executeProgram(true);

        // TODO: throw catch. create resource interrupt
        // TODO: set interrupt info, vm id
    }
}
