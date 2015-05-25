package main.os.processes;

import main.os.Primitives;
import main.os.ProcessDescriptor;
import main.os.ResourceDescriptor;

/**
 * Created by Ernestas on 2015-05-21.
 */
public class StartStop extends main.os.Process {
    public void run() {
        Primitives.createResource(++ResourceDescriptor.id, "Supervizorine atmintis", true);
        Primitives.createResource(++ResourceDescriptor.id, "Vartotojo atmintis", true);
        Primitives.createResource(++ResourceDescriptor.id, "Bendra atmintis", true);
        Primitives.createResource(++ResourceDescriptor.id, "Procesorius", true);
        Primitives.createResource(++ResourceDescriptor.id, "Kanalu irenginys", true);

        Primitives.createProcess(new ReadFromFlash(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new JCL(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new Loader(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new MainProc(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new Interupt(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new PrintLine(), ++ProcessDescriptor.id, null, 0);

        Primitives.requestResource(ResourceDescriptor.POS_PABAIGA);

        // Sisteminiu procesu naikinimas
        // Sisteminiu resursu naikinimas
    }
}
