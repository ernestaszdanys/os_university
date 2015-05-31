package main.os.processes;

import com.sun.org.apache.regexp.internal.REDebugCompiler;
import main.os.Primitives;
import main.os.ResourceDescriptor;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class JCL extends main.os.Process {

    public JCL(){
        super.name = "JCL";
    }

    public void run() {
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.SUPERVIZORINE_ATMINTIS);
            return;
        }
        else if(step == 1) {
            step++;
            Primitives.requestResource(ResourceDescriptor.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE);
            return;
        }

        // TODO: supervizorinej iskaidyt i blokus
        else if(step == 2) {
            step++;
            Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE, false);
            Primitives.stopProcess(this);
            return;
        }
    }

}
