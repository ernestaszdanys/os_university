package main;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            InputDevice.openFile();
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file name.");
        }

        Word[] words;
        try {
            while (true) {
                words = InputDevice.getInput();
                for (Word w : words) {
                    System.out.println(w);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading finished.");
        }
    }

}
