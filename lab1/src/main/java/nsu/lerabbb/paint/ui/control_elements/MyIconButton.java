package nsu.lerabbb.paint.ui.control_elements;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Objects;

public class MyIconButton extends JToggleButton {
    public MyIconButton(String tipText, String iconPath, ActionListener actl, ItemListener iteml){
        super();
        setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath))));
        setToolTipText(tipText);
        addActionListener(actl);
        addItemListener(iteml);
    }
}