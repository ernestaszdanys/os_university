package main.os.processes;

import main.os.Planner;
import main.os.Primitives;
import main.os.ResourceDescriptor;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class PrintLine extends main.os.Process {
    public PrintLine() {
        super.name = "PrintLine";
    }
    public void run(){
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.EILUTE_ATMINTYJE);
        }
        else if(step == 1) {
            System.out.println("ISVEDIMAS");
            Primitives.requestResource(ResourceDescriptor.EILUTE_ATMINTYJE);
        }
    }
}
