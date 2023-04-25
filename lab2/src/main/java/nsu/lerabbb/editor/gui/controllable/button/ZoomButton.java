package nsu.lerabbb.editor.gui.controllable.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ZoomButton extends IconToggleButton {
    public ZoomButton(ActionListener actl, ItemListener iteml) {
        super("Приблизить", "/icons/zoom.png", actl, iteml);
    }
}
