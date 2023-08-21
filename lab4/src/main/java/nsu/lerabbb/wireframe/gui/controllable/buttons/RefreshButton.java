package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class RefreshButton extends IconButton {
    public RefreshButton(ActionListener actl) {
        super("Обновить", "/icons/refresh.png", actl);
    }
}
