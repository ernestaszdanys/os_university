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
        Primitives.requestResource(ResourceDescriptor.SUPERVIZORINE_ATMINTIS);
        Primitives.requestResource(ResourceDescriptor.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE);

        // TODO: supervizorinej iskaidyt i blokus

        Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE, false);
    }

}
