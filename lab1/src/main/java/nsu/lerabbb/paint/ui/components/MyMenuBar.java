package nsu.lerabbb.paint.ui.components;

import nsu.lerabbb.paint.Controller;
import nsu.lerabbb.paint.ui.control_elements.MyMenuItem;
import nsu.lerabbb.paint.logic.PaintConfig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar implements ActionListener{
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;

    private JMenuItem newFileItem;
    private JMenuItem openFileItem;
    private JMenuItem saveFileItem;
    private JMenuItem exitFileItem;
    private JMenuItem settingsItem;
    private JMenuItem colorChooseItem;
    private JMenuItem lineItem;
    private JMenuItem fillItem;
    private JMenuItem clearItem;
    private JMenuItem helpItem;
    private JMenuItem polygonItem;
    private JMenuItem starItem;

    public MyMenuBar(){
        super();
        initFileMenu(this);
        initEditMenu(this);
        initHelpMenu(this);

        add(fileMenu);
        add(editMenu);
        add(helpMenu);
    }

    private void initFileMenu(ActionListener l){
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        newFileItem = new MyMenuItem("New file", l, KeyEvent.VK_N);
        openFileItem = new MyMenuItem("Open", l, KeyEvent.VK_O);
        saveFileItem = new MyMenuItem("Save", l, KeyEvent.VK_S);
        exitFileItem = new MyMenuItem("Exit", l, KeyEvent.VK_E);

        fileMenu.add(newFileItem);
        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.add(exitFileItem);
    }

    private void initEditMenu(ActionListener l){
        editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        settingsItem = new MyMenuItem("Settings", l, KeyEvent.VK_S);
        colorChooseItem = new MyMenuItem("Choose color", l, KeyEvent.VK_C);
        lineItem = new MyMenuItem("Draw line", l, KeyEvent.VK_L);
        fillItem = new MyMenuItem("Fill", l, KeyEvent.VK_F);
        clearItem = new MyMenuItem("Clear", l, KeyEvent.VK_X);
        polygonItem = new MyMenuItem("Polygon", l, KeyEvent.VK_P);
        starItem = new MyMenuItem("Star", l, KeyEvent.VK_Z);

        editMenu.add(settingsItem);
        editMenu.add(colorChooseItem);
        editMenu.add(lineItem);
        editMenu.add(polygonItem);
        editMenu.add(starItem);
        editMenu.add(fillItem);
        editMenu.add(clearItem);
    }

    private void initHelpMenu(ActionListener l){
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpItem = new MyMenuItem("About program", l, KeyEvent.VK_A);
        helpMenu.add(helpItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newFileItem){
            Controller.clearWorkSpace();
        }
        if(e.getSource() == openFileItem) {
            Controller.openFile();
        }
        if(e.getSource() == saveFileItem){
            Controller.saveFile();
        }
        if(e.getSource() == exitFileItem){
            System.exit(0);
        }
        if(e.getSource() == helpItem){
            Controller.openHelp();
        }
        if(e.getSource() == settingsItem) {
            Controller.chooseSettings();
        }
        if(e.getSource() == clearItem){
            Controller.clearWorkSpace();
        }
        if(e.getSource() == lineItem) {
            Controller.chooseShape(PaintConfig.LINE_MODE, 4);
        }
        if(e.getSource() == fillItem) {
            Controller.chooseShape(PaintConfig.FILL_MODE, 4);
        }
        if(e.getSource() == colorChooseItem) {
            Controller.chooseColor();
        }
        if(e.getSource() == polygonItem){
            Controller.chooseShape(PaintConfig.POLYGON_MODE, 4);
        }
        if(e.getSource() == starItem){
            Controller.chooseShape(PaintConfig.STAR_MODE, 4);
        }
    }
}
