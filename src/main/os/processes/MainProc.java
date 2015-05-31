package main.os.processes;

import main.os.*;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class MainProc extends main.os.Process {

    public MainProc(){
        super.name = "MainProc";
    }

    public void run() {
        Primitives.requestResource(ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE);

        // TODO: implement vykdymo laikas
//        if (vykdymo laikas == 0) then naikinam
//        Primitives.deleteProcess();

        int processId = ++ProcessDescriptor.id;
        Primitives.createProcess(new JobGovernor(), processId, null, 0);
    }

}
