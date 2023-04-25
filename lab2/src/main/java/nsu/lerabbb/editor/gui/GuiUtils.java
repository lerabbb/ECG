package nsu.lerabbb.editor.gui;

import nsu.lerabbb.editor.gui.components.FieldGroup;
import nsu.lerabbb.editor.gui.components.SliderGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiUtils {
    public static JToggleButton createButton(String text, ButtonGroup group, ActionListener l){
        JToggleButton button = new JToggleButton(text);
        button.addActionListener(l);
        group.add(button);
        return button;
    }

    public static JRadioButton createRadioBtn(String text, ButtonGroup group, ActionListener l){
        JRadioButton button = new JRadioButton(text);
        button.addActionListener(l);
        group.add(button);
        return button;
    }

    public static void addSlidersToGrid(Container container, GridBagConstraints constraints, SliderGroup elements){
        constraints.gridy++;
        constraints.gridx = 0;
        container.add(elements.getLabel(), constraints);
        constraints.gridx++;
        container.add(elements.getSlider(), constraints);
        constraints.gridx++;
        container.add(elements.getTextField(), constraints);
    }

    public static void addButtonToGrid(Container container, GridBagConstraints constraints, JToggleButton okButton, JToggleButton cancelButton){
        constraints.gridy++;
        constraints.gridx = 0;
        container.add(okButton, constraints);
        constraints.gridx++;
        container.add(cancelButton, constraints);
    }

    public static void addTextFieldToGrid(Container container, GridBagConstraints constraints, FieldGroup group){
        constraints.gridy++;
        constraints.gridx = 0;
        container.add(group.getLabel(), constraints);
        constraints.gridx++;
        container.add(group.getTextField(), constraints);
    }

}
