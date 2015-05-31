package main.os.processes;

import main.RealMachine;
import main.os.Primitives;
import main.os.ProcessDescriptor;
import main.os.ResourceDescriptor;

import javax.swing.*;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class JobGovernor extends main.os.Process {

    public JobGovernor(){
        super.name = "JobGovernor";
    }

    public void run() {
        Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.PAKRAUK_PROGRAMA, true);

        int loaderId = ++ProcessDescriptor.id;
        Primitives.createProcess(new Loader(), loaderId, null, 0);

        Primitives.requestResource(ResourceDescriptor.PAKRAUTA);
        Primitives.requestResource(ResourceDescriptor.VARTOTOJO_ATMINTIS);

        main.VirtualMachine virtualMachine = RealMachine.createVirtualMachine();
        main.CPU.cmdREAD();

        int virtualMachineId = ++ProcessDescriptor.id;
        Primitives.createProcess(new VirtualMachine(virtualMachine), virtualMachineId, null, 0);

        Primitives.requestResource(ResourceDescriptor.IS_INTERUPT);
        Primitives.stopProcess(virtualMachineId);

        // TODO: interrupto pranesimas
        // FORK
    }


}
