package main.os.processes;

import main.os.Primitives;
import main.os.ResourceDescriptor;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class Interrupt extends main.os.Process {

    public Interrupt(){
        super.name = "Interrupt";
    }

    public void run() {
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.PERTRAUKIMAS);
            return;
        }
        // TODO: interupto info
        // TODO: VM id is info ir identifikuot joberi (vm.father)

    }
}
