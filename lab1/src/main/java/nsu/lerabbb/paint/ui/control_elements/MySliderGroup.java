package nsu.lerabbb.paint.ui.control_elements;

import lombok.Getter;
import nsu.lerabbb.paint.ui.components.MyHelpDialog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Getter
public class MySliderGroup implements ChangeListener {
    private final JSlider slider;
    private final JLabel label;
    private final JTextField textField;

    public MySliderGroup(String labelText, int min, int max){
        label = new JLabel(labelText);

        slider = new JSlider(min, max);
        slider.addChangeListener(this);

        textField = new JTextField(min);
        textField.setText(String.valueOf(slider.getValue()));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleTextField(min, max);
            }
        });
    }

    public int getValue(){ return  slider.getValue(); }
    public void setValue(int value){
        slider.setValue(value);
    }
    public void setEditable(boolean editable){
        slider.setEnabled(editable);
        textField.setEditable(editable);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        textField.setText(String.valueOf(slider.getValue()));
    }

    private void handleTextField(int min, int max){
        String text = textField.getText();
        slider.setValue(0);
        if (!text.matches("\\d+")) {
            JDialog dialog = new MyHelpDialog(MyHelpDialog.WARNING_MODE);
            return;
        }
        int value = Math.min(Integer.parseInt(text), max);
        value = Math.max(value, min);
        slider.setValue(value);
    }
}