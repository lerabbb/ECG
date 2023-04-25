package nsu.lerabbb.editor.gui.controllable.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class InfoButton extends IconToggleButton {
    public InfoButton(ActionListener actl, ItemListener iteml) {
        super("О программе", "/icons/info.png", actl, iteml);
    }
}
