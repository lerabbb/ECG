package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class InfoButton extends IconButton {
    public InfoButton(ActionListener actl) {
        super("О программе", "/icons/info.png", actl);
    }
}
