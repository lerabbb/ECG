package nsu.lerabbb.editor.gui.dialogs;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.gui.GuiUtils;
import nsu.lerabbb.editor.gui.components.SliderGroup;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SobelDialog extends JDialog implements ActionListener, ItemListener {
    private final EditController controller;
    private JToggleButton okButton;
    private JToggleButton cancelButton;
    private SliderGroup binGroup;

    public SobelDialog(EditController controller){
        super(new Frame(), "Параметры выделения границ", Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Logger.getInstance().info("Sobel's operator settings dialog opened");

        this.controller = controller;
        initElements();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initElements(){
        JPanel panel = new JPanel();
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(15,10,15,10);
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;

        binGroup = new SliderGroup("Параметр бинаризации:", Utils.SOBEL_MIN, Utils.SOBEL_MAX);

        ButtonGroup toggleBtnGroup = new ButtonGroup();
        okButton = GuiUtils.createButton("Применить", toggleBtnGroup, this);
        cancelButton = GuiUtils.createButton("Отмена", toggleBtnGroup, this);
        setElementsState();

//        JLabel label = new JLabel("Настройте параметр бинаризации оператора Собеля");
//        panel.add(label, constraints);

        constraints.gridy = -1;
        constraints.gridx = -1;
        GuiUtils.addSlidersToGrid(panel, constraints, binGroup);
        GuiUtils.addButtonToGrid(panel, constraints, okButton, cancelButton);

        add(panel);
    }

    private void setElementsState(){
        binGroup.setValue(Config.getBinSobel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            saveChanges();
            controller.onSobelAction();
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void saveChanges() {
        Config.setBinSobel(binGroup.getValue());
        Logger.getInstance().info("Sobel' operator settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
