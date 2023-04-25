package nsu.lerabbb.editor.gui.components;

import nsu.lerabbb.editor.gui.dialogs.InfoDialog;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BlurSliderGroup extends SliderGroup implements ChangeListener {
    public BlurSliderGroup(String labelText, int min, int max) {
        super(labelText, min, max);
    }

    @Override
    public int getValue() {
        int value = slider.getValue();
        return value % 2 == 0 ? value + 1 : value;
    }


    @Override
    protected void handleTextField(int min, int max){
        String text = textField.getText();
        if (!text.matches("\\d+")) {
            new InfoDialog(InfoDialog.WARNING_MODE);
            return;
        }
        int value = Math.min(Integer.parseInt(text), max);
        value = Math.max(value, min);
        if(value%2==0){
            value++;
        }
        slider.setValue(value);
    }
}
