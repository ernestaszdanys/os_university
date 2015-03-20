package main;

/**
 * Created by Kompiuteris on 15-03-20.
 */
public class PageTable {

    private Page[] pages;

    public PageTable(int size)
    {
        pages = new Page[size];
    }

    public Page findFreePage()
    {
        int randomNum;
        do {
            randomNum = rand.nextInt((max - min) + 1) + min;
        } while (pages[randomNum].isAllocated());
        return pages[randomNum];
    }

    public boolean IsAllocatedPage(int virtualPageIndex) {
        return pages[virtualPageIndex].isAllocated();
    }
}
