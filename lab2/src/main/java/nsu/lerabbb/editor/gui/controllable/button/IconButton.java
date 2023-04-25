package nsu.lerabbb.editor.gui.controllable.button;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class IconButton extends JButton {
    public IconButton(String tipText, String iconPath, ActionListener actl){
        super();
        setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath))));
        setToolTipText(tipText);
        addActionListener(actl);
    }
}
