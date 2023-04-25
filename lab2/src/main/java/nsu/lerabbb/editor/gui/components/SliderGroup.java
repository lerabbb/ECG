package nsu.lerabbb.editor.gui.components;

import lombok.Getter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Getter
public class SliderGroup implements ChangeListener {
    protected final JSlider slider;
    protected final JLabel label;
    protected final JTextField textField;

    public SliderGroup(String labelText, int min, int max){
        label = new JLabel(labelText);

        slider = new JSlider(min, max, min);
        slider.addChangeListener(this);

        textField = new JTextField(min);
        textField.setText(String.valueOf(slider.getValue()));
        textField.setColumns(3);
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

    @Override
    public void stateChanged(ChangeEvent e) {
        textField.setText(String.valueOf(getValue()));
    }

    protected void handleTextField(int min, int max){
        String text = textField.getText();
        if (!text.matches("\\d+")) {
            return;
        }
        int value = Math.min(Integer.parseInt(text), max);
        value = Math.max(value, min);
        slider.setValue(value);
    }
}
