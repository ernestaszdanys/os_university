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

    int virtualMachineId;

    public void run() {
        if(step == 0) {
            step++;
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.PAKRAUK_PROGRAMA, true);

            int loaderId = ++ProcessDescriptor.id;
            Primitives.createProcess(new Loader(), loaderId, null, 0);

            Primitives.requestResource(ResourceDescriptor.PAKRAUTA);
            return;
        }
        else if(step == 1) {
            step++;
            Primitives.requestResource(ResourceDescriptor.VARTOTOJO_ATMINTIS);
            return;
        }
        else if(step == 2) {
            step++;

            main.VirtualMachine virtualMachine = RealMachine.createVirtualMachine();
            main.CPU.cmdREAD();

            virtualMachineId = ++ProcessDescriptor.id;
            Primitives.createProcess(new VirtualMachine(virtualMachine), virtualMachineId, null, 0);
            Primitives.requestResource(ResourceDescriptor.IS_INTERUPT);
            return;
        }
        else if(step == 3) {
            step++;
            Primitives.stopProcess(virtualMachineId);
            return;
        }

        // TODO: interrupto pranesimas
        // FORK
    }


}
