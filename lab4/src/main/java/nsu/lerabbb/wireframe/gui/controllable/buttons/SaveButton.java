package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class SaveButton extends IconButton {
    public SaveButton(ActionListener actl) {
        super("Сохранить файл", "/icons/save.png", actl);
    }
}
