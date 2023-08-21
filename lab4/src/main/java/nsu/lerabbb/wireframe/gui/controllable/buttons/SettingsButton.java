package nsu.lerabbb.wireframe.gui.controllable.buttons;

import java.awt.event.ActionListener;

public class SettingsButton extends IconButton {
    public SettingsButton(ActionListener actl) {
        super("Настроить", "/icons/settings.png", actl);
    }
}
