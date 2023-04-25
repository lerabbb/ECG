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
import java.awt.image.AffineTransformOp;

public class RotateDialog extends JDialog implements ActionListener, ItemListener {
    private final EditController controller;
    private JToggleButton okButton;
    private JToggleButton cancelButton;
    private SliderGroup rotateGroup;

    public RotateDialog(EditController controller){
        super(new Frame(), "Параметры поворота изображения", Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Logger.getInstance().info("Rotation settings dialog opened");

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

        rotateGroup = new SliderGroup("Угол поворота:", Utils.ROTATE_MIN, Utils.ROTATE_MAX);

        ButtonGroup toggleBtnGroup = new ButtonGroup();
        okButton = GuiUtils.createButton("Применить", toggleBtnGroup, this);
        cancelButton = GuiUtils.createButton("Отмена", toggleBtnGroup, this);
        setElementsState();

//        JLabel label = new JLabel("Настройте угол поворота изображения");
//        add(label, BorderLayout.NORTH);

        constraints.gridy = -1;
        constraints.gridx = -1;
        GuiUtils.addSlidersToGrid(panel, constraints, rotateGroup);
        GuiUtils.addButtonToGrid(panel, constraints, okButton, cancelButton);

        add(panel);
    }

    private void setElementsState(){
        rotateGroup.setValue(Config.getRotation());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            saveChanges();
            controller.onRotateAction();
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void saveChanges() {
        Config.setRotation(rotateGroup.getValue());
        Logger.getInstance().info("Rotation settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}

}
