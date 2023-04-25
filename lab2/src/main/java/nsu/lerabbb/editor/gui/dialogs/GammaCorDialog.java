package nsu.lerabbb.editor.gui.dialogs;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.gui.GuiUtils;
import nsu.lerabbb.editor.gui.components.FieldGroup;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GammaCorDialog extends JDialog implements ActionListener {

    private EditController controller;
    private JToggleButton okButton;
    private JToggleButton cancelButton;
    private FieldGroup gammaGroup;

    public GammaCorDialog(EditController controller){
        super(new Frame(), "Параметры гамма-коррекции", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Logger.getInstance().info("Gamma correction settings dialog opened");

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

        gammaGroup = new FieldGroup("Параметр γ:", Utils.GAMMA_MIN, Utils.GAMMA_MAX); //0.5, 10, 0.1

        ButtonGroup toggleBtnGroup = new ButtonGroup();
        okButton = GuiUtils.createButton("Применить", toggleBtnGroup, this);
        cancelButton = GuiUtils.createButton("Отмена", toggleBtnGroup, this);
        setElementsState();

//        JLabel label = new JLabel("Настройте параметры гамма-коррекции");
//        panel.add(label, constraints);

        constraints.gridy = -1;
        constraints.gridx = -1;

        GuiUtils.addTextFieldToGrid(panel, constraints, gammaGroup);
        GuiUtils.addButtonToGrid(panel, constraints, okButton, cancelButton);
        add(panel);
    }

    private void setElementsState(){
        gammaGroup.setValue(Config.getGammaCorrection());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                saveChanges();
                controller.onGammaCorAction();
            } catch (Exception exception){
                new InfoDialog(InfoDialog.WARNING_MODE);
                Logger.getInstance().error(exception.getMessage());
            }
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void saveChanges() throws Exception {
        if(!gammaGroup.validateInput()){
            throw new Exception("invalid parameters input");
        }
        Config.setGammaCorrection(gammaGroup.getValue());
        Logger.getInstance().info("Gamma correction settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
