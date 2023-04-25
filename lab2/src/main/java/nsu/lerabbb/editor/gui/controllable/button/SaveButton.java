package nsu.lerabbb.editor.gui.controllable.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class SaveButton extends IconToggleButton {
    public SaveButton(ActionListener actl, ItemListener iteml) {
        super("Сохранить файл", "/icons/save.png", actl, iteml);
    }
}
