package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kompiuteris on 15-03-23.
 */
public class GUI {

    private JPanel panel;
    private JScrollPane memoryPanel;
    private JTextField SP;
    private JTextField PTR;
    private JTextField PID;
    private JButton stepButton;
    private JButton executeButton;
    private JButton memoryButton;
    private JButton pageForwardButton;
    private JButton pageBackButton;
    private JTextField PC;
    private int memoryMode;
    public final int MEMORY_MODE_VIRTUAL = 0;
    public final int MEMORY_MODE_REAL = 1;
    public final int MEMORY_PAGE_SIZE = 256;
    private int memoryPage;
    private JTextField[] memoryField;

    public GUI() {
        memoryMode = MEMORY_MODE_VIRTUAL;
        memoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memoryMode == MEMORY_MODE_VIRTUAL)
                    memoryMode = MEMORY_MODE_REAL;
                else {
                    memoryMode = MEMORY_MODE_VIRTUAL;
                    memoryPage = 0;
                }
                redraw();
            }
        });
        pageBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memoryMode == MEMORY_MODE_REAL){
                    if(memoryPage == 0){
                        memoryPage = 15;
                    }
                    else {
                        memoryPage--;
                    }
                    redraw();
                }
            }
        });
        pageForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memoryMode == MEMORY_MODE_REAL){
                    if(memoryPage == 15){
                        memoryPage = 0;
                    }
                    else {
                        memoryPage++;
                    }
                    redraw();
                }

            }
        });
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RealMachine.executeProgram(false);
                redraw();
            }
        });
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RealMachine.executeProgram(true);
                redraw();
            }
        });
    }

    public JPanel getPanel(){
        return panel;
    }



    private void createUIComponents() {
        JPanel insidePanel = new JPanel(new SpringLayout());
        memoryPanel = new JScrollPane(insidePanel);
        int rows = VirtualMachine.MEMORY_SIZE;
        int cols = 7;
        int i = 0;
        memoryField = new JTextField[(VirtualMachine.MEMORY_SIZE+1)*cols];
        memoryField[i] = new JTextField("Real Address");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        memoryField = new JTextField[(VirtualMachine.MEMORY_SIZE+1)*cols];
        memoryField[i] = new JTextField("Virtual Address");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        for (int c = 3; c >= 0; c--) {
            memoryField[i] = new JTextField("Byte " + c);
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);
        }
        memoryField[i] = new JTextField("As Int");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        for (int r = 0; r < rows; r++) {

            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i] = new JTextField("" + (r + (memoryPage * MEMORY_PAGE_SIZE)));
                insidePanel.add(memoryField[i]);
                memoryField[i++].setEditable(false);
            }
            else{
                memoryField[i] = new JTextField("" + PMMU.virtualToRealAddress(r));
                insidePanel.add(memoryField[i]);
                memoryField[i++].setEditable(false);
            }

            memoryField[i] = new JTextField("" + (r + (memoryPage*MEMORY_PAGE_SIZE)));
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);

            for (int c = 3; c >= 0; c--) {
                if(memoryMode == MEMORY_MODE_REAL) {
                    memoryField[i] = new JTextField(String.format("%02X ", RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE).getByte(c)));
                }
                else {
                    memoryField[i] = new JTextField(String.format("%02X ", PMMU.read(r).getByte(c)));
                }
                insidePanel.add(memoryField[i]);
                memoryField[i++].setEditable(false);
            }
            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i] = new JTextField(Integer.toString(Word.wordToInt(RealMachine.getRealMemory().read(r+memoryPage*MEMORY_PAGE_SIZE))));
            }
            else {
                memoryField[i] = new JTextField(Integer.toString(Word.wordToInt(PMMU.read(r))));
            }
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);
        }
        SpringUtilities.makeCompactGrid(insidePanel, //parent
                rows, cols,
                2, 2,  //initX, initY
                2, 2); //xPad, yPad
    }

    public void redraw(){
        SP.setText(Integer.toString(RealMachine.getCPU().getSP()));
        PTR.setText(Integer.toString(RealMachine.getCPU().getPTR()));
        PID.setText(Integer.toString(RealMachine.getCPU().getPID()));
        PC.setText(Integer.toString(RealMachine.getCPU().getPC()));

        int rows = VirtualMachine.MEMORY_SIZE;
        if(memoryMode == MEMORY_MODE_REAL) {
            memoryField[1].setText("Real Address");
        }else{
            memoryField[1].setText("Virtual Address");
        }
        int cols = 7;
        int i = cols;
        for (int r = 0; r < rows; r++) {

            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i++].setText("" + (r + (memoryPage * MEMORY_PAGE_SIZE)));
            }else{
                memoryField[i++].setText("" + (PMMU.virtualToRealAddress(r)));
            }

            memoryField[i++].setText("" + (r + (memoryPage * MEMORY_PAGE_SIZE)));

            for (int c = 3; c >= 0; c--) {
                if(memoryMode == MEMORY_MODE_REAL) {
                    memoryField[i++].setText(String.format("%02X ", RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE).getByte(c)));
                }
                else {
                    memoryField[i++].setText(String.format("%02X ", PMMU.read(r).getByte(c)));
                }
            }
            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i++].setText(Integer.toString(Word.wordToInt(RealMachine.getRealMemory().read(r+memoryPage*MEMORY_PAGE_SIZE))));
            }
            else {
                memoryField[i++].setText(Integer.toString(Word.wordToInt(PMMU.read(r))));
            }
        }
    }
}
