package nsu.lerabbb.paint.ui.control_elements;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MyMenuItem extends JMenuItem {
    public MyMenuItem(String name, ActionListener l, int mnemonic){
        super(name);
        addActionListener(l);
        setMnemonic(mnemonic);
    }
}
