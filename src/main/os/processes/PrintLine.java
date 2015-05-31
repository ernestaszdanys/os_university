package main.os.processes;

import main.os.Primitives;

/**
 * Created by ERZD01 on 2015.05.25.
 */
public class PrintLine extends main.os.Process {
    public PrintLine() {
        super.name = "PrintLine";
    }
    public void run(){
        Primitives.stopProcess(this);
    }
}
