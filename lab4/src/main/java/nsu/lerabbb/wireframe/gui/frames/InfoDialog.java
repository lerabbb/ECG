package nsu.lerabbb.wireframe.gui.frames;

import nsu.lerabbb.wireframe.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class InfoDialog extends JDialog implements ActionListener {
    private final JButton okButton;
    private static final String HELP_TEXT = """
            --------------------------------------Приложение "Редактор изображений"-------------------------------------
            Автор программы: студент ФИТ 20206, Базаргуроева Валерия Бэликтоевна
            Данное приложение предназначено для визуализации трехмерной проволочной модели.
            В окне настроек можно редактировать образующую модели. Также регулируются следующие параметры:
            1. K - число опорных точек (>=4)
            2. N - число отрезков для участка B-сплайна (>=1)
            3. M - число образующих (>=2)
            4. M1 - число отрезков по окружностям между соседними образующими (>=1)
            
            Фигуру можно вращать движением мышки, а также увеличивать колесиком мышки.
            Все данные сохраняются в файле формата '*.wrf'.
            """;

    public InfoDialog(){
        this(HELP_TEXT);
    }

    public InfoDialog(String msg) {
        super(new Frame(), "Информация", Dialog.ModalityType.APPLICATION_MODAL);
        Logger.getInstance().info("info dialog opened");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JTextArea textArea = new JTextArea();
        textArea.setText(msg);
        textArea.setEditable(false);
        okButton = new JButton("Ок");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(this);

        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.setBorder(new EmptyBorder(new Insets(10, 15, 10, 15)));
        panel.add(textArea);
        panel.add(okButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okButton){
            Logger.getInstance().info("info dialog closed");
            setVisible(false);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
