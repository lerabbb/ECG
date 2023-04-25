package nsu.lerabbb.editor.gui.controllable.button;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Objects;

public class IconToggleButton extends JToggleButton {
    public IconToggleButton(String tipText, String iconPath, ActionListener actl, ItemListener iteml){
        super();
        setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath))));
        setToolTipText(tipText);
        addActionListener(actl);
        addItemListener(iteml);
    }
}
