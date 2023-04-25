package nsu.lerabbb.editor.gui.components;

import lombok.Getter;
import nsu.lerabbb.editor.Logger;

import javax.swing.*;

public class FieldGroup {
    @Getter
    private JTextField textField;
    @Getter
    private JLabel label;
    @Getter
    private float value;
    private final float min;
    private final float max;

    public FieldGroup(String labelText, float min, float max){
        label = new JLabel(labelText);
        textField = new JTextField();
        textField.setToolTipText("От " + min + " до " + max);
        this.min = min;
        this.max = max;
    }

    public boolean validateInput() {
        String input = textField.getText();
        if (!input.matches("[+-]?([0-9]*[.])?[0-9]+")) {
            Logger.getInstance().error("Expected float value, actual string");
            return false;
        }
        value = Float.parseFloat(input);
        Logger.getInstance().info("Param = " + value);
        return value >= min && value <= max;
    }

    public void setValue(float val){
        textField.setText(String.valueOf(val));
    }
}
