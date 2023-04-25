package nsu.lerabbb.editor.gui.controllable.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ExitButton extends IconToggleButton {
    public ExitButton(ActionListener actl, ItemListener iteml) {
        super("Выход из программы", "/icons/exit.png", actl, iteml);
    }
}
