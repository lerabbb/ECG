package nsu.lerabbb.paint.ui.control_elements;

import lombok.Getter;
import nsu.lerabbb.paint.logic.PaintConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class MyRadioButtonGroup extends ButtonGroup implements ActionListener {
    private final JRadioButton redRadioBtn;
    private final JRadioButton yellowRadioBtn;
    private final JRadioButton greenRadioBtn;
    private final JRadioButton cyanRadioBtn;
    private final JRadioButton blueRadioBtn;
    private final JRadioButton magentaRadioBtn;
    private final JRadioButton whiteRadioBtn;
    private final JRadioButton blackRadioBtn;

    public MyRadioButtonGroup(){
        super();

        redRadioBtn = createRadioBtn("Красный цвет");
        yellowRadioBtn = createRadioBtn("Желтый цвет");
        greenRadioBtn = createRadioBtn("Зеленый цвет");
        cyanRadioBtn = createRadioBtn("Голубой цвет");
        blueRadioBtn = createRadioBtn("Синий цвет");
        magentaRadioBtn = createRadioBtn("Фиолетовый цвет");
        whiteRadioBtn = createRadioBtn("Белый цвет");
        blackRadioBtn = createRadioBtn("Черный цвет");

        add(redRadioBtn);
        add(yellowRadioBtn);
        add(greenRadioBtn);
        add(cyanRadioBtn);
        add(blueRadioBtn);
        add(magentaRadioBtn);
        add(whiteRadioBtn);
        add(blackRadioBtn);
    }

    private JRadioButton createRadioBtn(String text){
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.addActionListener(this);
        return radioButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == redRadioBtn) {
            PaintConfig.getInstance().setColor(Color.RED);
        }
        if (e.getSource() == yellowRadioBtn) {
            PaintConfig.getInstance().setColor(Color.YELLOW);
        }
        if (e.getSource() == greenRadioBtn) {
            PaintConfig.getInstance().setColor(Color.GREEN);
        }
        if (e.getSource() == cyanRadioBtn) {
            PaintConfig.getInstance().setColor(Color.CYAN);
        }
        if (e.getSource() == blueRadioBtn) {
            PaintConfig.getInstance().setColor(Color.BLUE);
        }
        if (e.getSource() == magentaRadioBtn) {
            PaintConfig.getInstance().setColor(Color.MAGENTA);
        }
        if (e.getSource() == whiteRadioBtn) {
            PaintConfig.getInstance().setColor(Color.WHITE);
        }
        if (e.getSource() == blackRadioBtn) {
            PaintConfig.getInstance().setColor(Color.BLACK);
        }
    }
}
