package nsu.lerabbb.editor.gui.controllable.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class NewFileButton extends IconToggleButton {

    public NewFileButton(ActionListener actl, ItemListener iteml) {
        super("Новый файл", "/icons/new_file.png", actl, iteml);
    }
}
