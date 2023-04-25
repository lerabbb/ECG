package nsu.lerabbb.editor.gui.dialogs;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.gui.GuiUtils;
import nsu.lerabbb.editor.logic.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.AffineTransformOp;

public class InterpolationDialog extends JDialog implements ActionListener, ItemListener {
    private final EditController controller;
    private JToggleButton okButton;
    private JToggleButton cancelButton;
    private JRadioButton bilinearBtn;
    private JRadioButton nearestBtn;

    public InterpolationDialog(EditController controller){
        super(new Frame(), "Режимы интерполяции", Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Logger.getInstance().info("Interpolation settings dialog opened");

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

        ButtonGroup radioBtnGroup = new ButtonGroup();
        bilinearBtn = GuiUtils.createRadioBtn("Билинейная интерполяция", radioBtnGroup, this);
        nearestBtn = GuiUtils.createRadioBtn("Метод ближайшего соседа", radioBtnGroup, this);

        ButtonGroup toggleBtnGroup = new ButtonGroup();
        okButton = GuiUtils.createButton("Применить", toggleBtnGroup, this);
        cancelButton = GuiUtils.createButton("Отмена", toggleBtnGroup, this);
        setElementsState();
//        JLabel label = new JLabel("Выберите режим интерполяции");
//        add(label, BorderLayout.NORTH);

        constraints.gridy = 0;
        constraints.gridx = 0;
        panel.add(bilinearBtn, constraints);
        constraints.gridy++;
        panel.add(nearestBtn, constraints);
        GuiUtils.addButtonToGrid(panel, constraints, okButton, cancelButton);
        add(panel);
    }

    private void setElementsState(){
        if(Config.getInterpMode() == RenderingHints.VALUE_INTERPOLATION_BILINEAR){
            bilinearBtn.setSelected(true);
            nearestBtn.setSelected(false);
        }
        if(Config.getInterpMode() == RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR){
            bilinearBtn.setSelected(false);
            nearestBtn.setSelected(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            saveChanges();
            controller.onFitScreenAction();
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if(e.getSource() == bilinearBtn){
            Config.setInterpMode(RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }
        if(e.getSource() == nearestBtn){
            Config.setInterpMode(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }
    }

    private void saveChanges() {
        Logger.getInstance().info("Interpolation settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
