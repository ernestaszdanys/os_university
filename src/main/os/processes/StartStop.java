package main.os.processes;

import main.os.Primitives;
import main.os.ProcessDescriptor;
import main.os.ResourceDescriptor;

/**
 * Created by Ernestas on 2015-05-21.
 */
public class StartStop extends main.os.Process {
    public StartStop(){
        super.name = "StartStop";
    }
    public void run() {
        Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.SUPERVIZORINE_ATMINTIS, true);
        Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.VARTOTOJO_ATMINTIS, true);
        Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.BENDRA_ATMINTIS, true);
        Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.PROCESORIUS, true);
        Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.KANALU_IRENGINYS, true);

        Primitives.createProcess(new ReadFromFlash(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new JCL(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new Loader(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new MainProc(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new Interrupt(), ++ProcessDescriptor.id, null, 0);
        Primitives.createProcess(new PrintLine(), ++ProcessDescriptor.id, null, 0);

        Primitives.requestResource(ResourceDescriptor.POS_PABAIGA);

        // Sisteminiu procesu naikinimas
        // Sisteminiu resursu naikinimas
    }
}
