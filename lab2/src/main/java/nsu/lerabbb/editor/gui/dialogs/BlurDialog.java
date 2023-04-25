package nsu.lerabbb.editor.gui.dialogs;

import jdk.jshell.execution.Util;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.gui.GuiUtils;
import nsu.lerabbb.editor.gui.components.BlurSliderGroup;
import nsu.lerabbb.editor.gui.components.FieldGroup;
import nsu.lerabbb.editor.gui.components.SliderGroup;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlurDialog extends JDialog implements ActionListener, ItemListener {
    private SliderGroup sizeGroup;
    private FieldGroup sigmaGroup;

    private JToggleButton okButton;
    private JToggleButton cancelButton;

    private final EditController controller;

    public BlurDialog(EditController controller){
        super(new Frame(), "Параметры размытия", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Logger.getInstance().info("Blur settings dialog opened");

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

        sizeGroup = new BlurSliderGroup("Размер окна фильтра:", Utils.BLUR_SIZE_MIN, Utils.BLUR_SIZE_MAX);
        sigmaGroup = new FieldGroup("Параметр σ:", Utils.SIGMA_MIN, Utils.SIGMA_MAX);

        ButtonGroup toggleBtnGroup = new ButtonGroup();
        okButton = GuiUtils.createButton("Применить", toggleBtnGroup, this);
        cancelButton = GuiUtils.createButton("Отмена", toggleBtnGroup, this);
        setElementsState();

//        JLabel label = new JLabel("Настройте параметры сглаживающего фильтра");
//        add(label, BorderLayout.NORTH);

        constraints.gridy = -1;
        constraints.gridx = -1;
        GuiUtils.addSlidersToGrid(panel, constraints, sizeGroup);
        GuiUtils.addTextFieldToGrid(panel, constraints, sigmaGroup);
        GuiUtils.addButtonToGrid(panel, constraints, okButton, cancelButton);

        add(panel);
    }

    private void setElementsState(){
        sizeGroup.setValue(Config.getBlurSize());
        sigmaGroup.setValue(Config.getGaussSigma());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                saveChanges();
                controller.onBlurAction();
            } catch (Exception exception) {
                Logger.getInstance().error(exception.getMessage());
                new InfoDialog(InfoDialog.WARNING_MODE);
            }
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void saveChanges() throws Exception {
        if(!sigmaGroup.validateInput()){
            throw new Exception("invalid parameters input");
        }
        Config.setBlurSize(sizeGroup.getValue());
        Config.setGaussSigma(sigmaGroup.getValue());
        Logger.getInstance().info("Blur settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
