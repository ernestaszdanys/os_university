package main;

import java.util.Arrays;

public class Memory {

    private int size;
    private Word[] memory;


    public Memory(int size){
        if (size > 0){
            this.size = size;
            memory = new Word[size];
            for (int i = 0; i < size; i++){
                memory[i] = new Word();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    
    public Word read(int address){
        return memory[address].clone();
    }

    public void write(Word word, int address){
        memory[address] = word.clone();
    }

    public int getSize(){
        return size;
    }

    public Word[] getMemory(){
        return Arrays.copyOf(memory, size);
    }

}
