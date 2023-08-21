package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class OpenFileButton extends IconButton {
    public OpenFileButton(ActionListener actl) {
        super("Открыть файл", "/icons/open.png", actl);
    }
}
