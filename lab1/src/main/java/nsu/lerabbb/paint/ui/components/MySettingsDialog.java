package nsu.lerabbb.paint.ui.components;

import nsu.lerabbb.paint.Logger;
import nsu.lerabbb.paint.ui.control_elements.MyRadioButtonGroup;
import nsu.lerabbb.paint.ui.control_elements.MySliderGroup;
import nsu.lerabbb.paint.logic.PaintConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MySettingsDialog extends JDialog implements ActionListener, ItemListener {
    private JToggleButton lineButton;
    private JToggleButton polygonButton;
    private JToggleButton starButton;
    private JToggleButton okButton;
    private JToggleButton cancelButton;

    private MySliderGroup radiusGroup;
    private MySliderGroup rotationGroup;
    private MySliderGroup strokeGroup;
    private MySliderGroup peaksNumGroup;

    private JTextField widthField;
    private JTextField heightField;

    public MySettingsDialog(){
        super(new Frame(), "Настройки", ModalityType.APPLICATION_MODAL);
        Logger.getInstance().info("settings dialog opened");
        setResizable(false);
        initElements();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    saveChanges();
                }
            }
        });

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

        ButtonGroup radioButtonGroup = new MyRadioButtonGroup();
        ButtonGroup toggleBtnGroup = new ButtonGroup();

        lineButton = createButton("Линия", toggleBtnGroup);
        polygonButton = createButton("Многоугольник", toggleBtnGroup);
        starButton = createButton("Звезда", toggleBtnGroup);
        okButton = createButton("Применить", toggleBtnGroup);
        cancelButton = createButton("Отмена", toggleBtnGroup);
        radiusGroup = new MySliderGroup("Радиус (px): ", 10, 100);
        rotationGroup = new MySliderGroup("Угол поворота (°): ", 0, 360);
        strokeGroup = new MySliderGroup("Толщина (px): ", 1, 40);
        peaksNumGroup = new MySliderGroup("Количество вершин: ", 3, 16);
        JLabel sizeLabel = new JLabel("Размер холста: ");
        widthField = new JTextField("Ширина");
        heightField = new JTextField("Высота");
        widthField.setToolTipText("от 50 до 2048");
        heightField.setToolTipText("от 50 до 2048");
        setElementsState();

        constraints.gridy = 0;
        constraints.gridx = 0;
        panel.add(lineButton, constraints);
        constraints.gridx++;
        panel.add(polygonButton, constraints);
        constraints.gridx++;
        panel.add(starButton, constraints);

        addSlidersToGrid(panel, constraints, radiusGroup);
        addSlidersToGrid(panel, constraints, rotationGroup);
        addSlidersToGrid(panel, constraints, strokeGroup);
        addSlidersToGrid(panel, constraints, peaksNumGroup);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getRedRadioBtn(), constraints);
        constraints.gridx++;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getYellowRadioBtn(), constraints);
        constraints.gridx++;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getGreenRadioBtn(), constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getCyanRadioBtn(), constraints);
        constraints.gridx++;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getBlueRadioBtn(), constraints);
        constraints.gridx++;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getMagentaRadioBtn(), constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getWhiteRadioBtn(), constraints);
        constraints.gridx++;
        panel.add(((MyRadioButtonGroup) radioButtonGroup).getBlackRadioBtn(), constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(sizeLabel, constraints);
        constraints.gridx++;
        panel.add(widthField, constraints);
        constraints.gridx++;
        panel.add(heightField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(okButton, constraints);
        constraints.gridx++;
        panel.add(cancelButton, constraints);

        add(panel);
    }

    private JToggleButton createButton(String text, ButtonGroup group){
        JToggleButton button = new JToggleButton(text);
        button.addActionListener(this);
        group.add(button);
        return button;
    }

    private void setElementsState(){
        if(PaintConfig.getInstance().getMode() == PaintConfig.LINE_MODE){
            radiusGroup.setEditable(false);
            rotationGroup.setEditable(false);
            strokeGroup.setEditable(true);
            peaksNumGroup.setEditable(false);
        }
        else{
            radiusGroup.setEditable(true);
            rotationGroup.setEditable(true);
            strokeGroup.setEditable(false);
            peaksNumGroup.setEditable(true);
        }
        radiusGroup.setValue(PaintConfig.getInstance().getRadius());
        rotationGroup.setValue(PaintConfig.getInstance().getRotation());
        strokeGroup.setValue(PaintConfig.getInstance().getStroke());
        peaksNumGroup.setValue(PaintConfig.getInstance().getPeaksNum());
        widthField.setText(String.valueOf(PaintConfig.getInstance().getSpaceWidth()));
        heightField.setText(String.valueOf(PaintConfig.getInstance().getSpaceHeight()));
    }

    private void addSlidersToGrid(Container container, GridBagConstraints constraints, MySliderGroup elements){
        constraints.gridy++;
        constraints.gridx = 0;
        container.add(elements.getLabel(), constraints);
        constraints.gridx++;
        container.add(elements.getSlider(), constraints);
        constraints.gridx++;
        container.add(elements.getTextField(), constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lineButton) {
            PaintConfig.getInstance().setMode(PaintConfig.LINE_MODE);
            setElementsState();
        }
        if (e.getSource() == polygonButton) {
            PaintConfig.getInstance().setMode(PaintConfig.POLYGON_MODE);
            setElementsState();
        }
        if (e.getSource() == starButton) {
            PaintConfig.getInstance().setMode(PaintConfig.STAR_MODE);
            setElementsState();
        }
        if (e.getSource() == okButton) {
            saveChanges();
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void saveChanges(){
        if(!validateSizeInput()){
            Logger.getInstance().error("invalid parameters input");
            new MyHelpDialog(MyHelpDialog.WARNING_MODE);
            return;
        }
        PaintConfig.getInstance().setRadius(radiusGroup.getValue());
        PaintConfig.getInstance().setRotation(rotationGroup.getValue());
        PaintConfig.getInstance().setStroke(strokeGroup.getValue());
        PaintConfig.getInstance().setPeaksNum(peaksNumGroup.getValue());
        Logger.getInstance().info("settings dialog closed");
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private boolean validateSizeInput(){
        String widthText = widthField.getText();
        String heightText = heightField.getText();
        if (!widthText.matches("\\d+") || !heightText.matches("\\d+")) {
            return false;
        }
        int width = Integer.parseInt(widthText);
        int height = Integer.parseInt(heightText);
        if(width < 50 || width > 2048 || height < 50 || height > 2048) {
            return false;
        }
        PaintConfig.getInstance().setSpaceWidth(width);
        PaintConfig.getInstance().setSpaceHeight(height);
        return true;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
