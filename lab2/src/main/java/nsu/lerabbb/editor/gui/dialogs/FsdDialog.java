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

public class FsdDialog extends JDialog implements ActionListener, ItemListener {
    private final EditController controller;
    private JToggleButton okButton;
    private JToggleButton cancelButton;
    private SliderGroup redGroup;
    private SliderGroup greenGroup;
    private SliderGroup blueGroup;

    public FsdDialog(EditController controller){
        super(new Frame(), "Параметры дизеринга Флойдом-Стейнбергом", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Logger.getInstance().info("FS dithering settings dialog opened");

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

        redGroup = new SliderGroup("Квантование красного цвета:", Utils.FSD_QUANT_MIN, Utils.FSD_QUANT_MAX);
        greenGroup = new SliderGroup("Квантование зеленого цвета:", Utils.FSD_QUANT_MIN, Utils.FSD_QUANT_MAX);
        blueGroup = new SliderGroup("Квантование синего цвета:", Utils.FSD_QUANT_MIN, Utils.FSD_QUANT_MAX);

        ButtonGroup toggleBtnGroup = new ButtonGroup();
        okButton = GuiUtils.createButton("Применить", toggleBtnGroup, this);
        cancelButton = GuiUtils.createButton("Отмена", toggleBtnGroup, this);
        setElementsState();

//        JLabel label = new JLabel("Настройте параметры дизеринга");
//        panel.add(label, constraints);

        constraints.gridy = -1;
        constraints.gridx = -1;

        GuiUtils.addSlidersToGrid(panel, constraints, redGroup);
        GuiUtils.addSlidersToGrid(panel, constraints, greenGroup);
        GuiUtils.addSlidersToGrid(panel, constraints, blueGroup);
        GuiUtils.addButtonToGrid(panel, constraints, okButton, cancelButton);

        add(panel);
    }

    private void setElementsState(){
        redGroup.setValue(Config.getRedFsNum());
        greenGroup.setValue(Config.getGreenFsNum());
        blueGroup.setValue(Config.getBlueFsNum());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            saveChanges();
            controller.onFsdAction();
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void saveChanges() {
        Config.setRedFsNum(redGroup.getValue());
        Config.setGreenFsNum(greenGroup.getValue());
        Config.setBlueFsNum(blueGroup.getValue());
        Logger.getInstance().info("FS dithering settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
