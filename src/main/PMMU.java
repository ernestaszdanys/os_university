package main;

/**
 * Created by Rokas on 2015-03-17.
 */
public class PMMU {

    public final static int BLOCK_SIZE = 256;
    public final static int WORDS_IN_BLOCK = 16;

    static void write(Word word, int address) {
        if (RealMachine.getCPU().getMODE() == CPU.SUPERVISOR) {
            RealMachine.getRealMemory().write(word, address);
        } else {
            int realAddress = virtualToRealAddress(address);
            RealMachine.getRealMemory().write(word, realAddress);
        }
    }

    static Word read(int address) {

        if (RealMachine.getCPU().getMODE() == CPU.SUPERVISOR) {
            return RealMachine.getRealMemory().read(address);
        } else {
            int realAddress = virtualToRealAddress(address);
            // TO DO: can VM read this?
            return RealMachine.getRealMemory().read(realAddress);
        }
    }

    private static int virtualToRealAddress(int address) {
        /*System.out.println("a: " + ((CPU.getPTR() & 0x0000ff00) >> 8));
        System.out.println("a: " + (CPU.getPTR() & 0x000000ff));
        System.out.println("a: " + ((address & 0x0000ff00) >> 8));
        System.out.println("a: " + (address & 0x000000ff));*/
        int blockIndexAddress = ((CPU.getPTR() & 0x0000ff00) >> 8) * PMMU.WORDS_IN_BLOCK + (CPU.getPTR() & 0x000000ff) + ((address & 0x0000ff00) >> 8);
        //System.out.println("b: " + blockIndexAddress);
        Word blockIndexInRealMemory = RealMachine.getRealMemory().read(blockIndexAddress);
        int blockRealAddress = Word.wordToInt(blockIndexInRealMemory) * PMMU.WORDS_IN_BLOCK;
        return blockRealAddress + (address & 0x000000ff);
    }
}
