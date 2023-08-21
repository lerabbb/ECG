package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class ZoomInButton extends IconButton {
    public ZoomInButton(ActionListener actl) {
        super("Увеличить", "/icons/zoom_in.png", actl);
    }
}
