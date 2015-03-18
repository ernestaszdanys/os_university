package main;

/**
 * Created by Kompiuteris on 15-03-18.
 */
public class VirtualMemory {
    Word[] memory;

    public VirtualMemory(int size) {
        memory = new Word[size];
    }

    public Word[] getMemory(){
        return memory;
    }

    public Word read(int address) {
        return memory[address];
    }
    public void write(Word word, int address) {
        memory[address] = word;
    }
}
