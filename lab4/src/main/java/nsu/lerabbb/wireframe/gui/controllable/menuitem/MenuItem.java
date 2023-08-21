package nsu.lerabbb.wireframe.gui.controllable.menuitem;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItem extends JMenuItem {
    public MenuItem(String name, ActionListener l){
        super(name);
        addActionListener(l);
    }
}
