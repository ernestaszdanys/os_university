package main;

/**
 * Created by Kompiuteris on 15-03-20.
 */
public class OutputDevice {

        public void printByte(byte b) {
            //System.out.print((char)b);
            Main.getGUI().print(Character.toString((char) b));
        }

        public void printString(String string) {
            //System.out.println(string);
            Main.getGUI().print(string);
        }
}
