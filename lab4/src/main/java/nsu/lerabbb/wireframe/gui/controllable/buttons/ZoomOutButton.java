package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class ZoomOutButton extends IconButton {
    public ZoomOutButton(ActionListener actl) {
        super("Уменьшить", "/icons/zoom_out.png", actl);
    }
}
