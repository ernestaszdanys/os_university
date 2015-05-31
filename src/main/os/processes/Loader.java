package main.os.processes;

import main.os.Primitives;
import main.os.ResourceDescriptor;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class Loader extends main.os.Process {

    public Loader(){
        super.name = "Loader";
    }

    public void run() {
        Primitives.requestResource(ResourceDescriptor.PAKRAUK_PROGRAMA);
        Primitives.requestResource(ResourceDescriptor.VARTOTOJO_ATMINTIS);
        Primitives.requestResource(ResourceDescriptor.UZDUOTIES_DUOMENYS_SUPERVIZORINEJE_ATMINTYJE);

        // XCHG

        Primitives.freeResource(ResourceDescriptor.UZDUOTIES_DUOMENYS_SUPERVIZORINEJE_ATMINTYJE);
        Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.PAKRAUTA, false);
    }
}
