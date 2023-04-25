package nsu.lerabbb.editor.gui.controllable.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class OpenFileButton extends IconToggleButton {
    public OpenFileButton(ActionListener actl, ItemListener iteml) {
        super("Открыть файл", "/icons/open_file.png", actl, iteml);
    }
}