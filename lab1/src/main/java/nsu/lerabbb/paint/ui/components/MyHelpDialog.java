package nsu.lerabbb.paint.ui.components;

import nsu.lerabbb.paint.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MyHelpDialog extends JDialog implements ActionListener {
    private final JButton okButton;
    public static final int WARNING_MODE = 1;
    public static final int HELP_MODE = 2;
    private final String warningText = "Неверно введены параметры";
    private final String helpText = """
            --------------------------------------Приложение "Paint"------------------------------------------
            Автор программы: студент ФИТ 20206, Базаргуроева Валерия Бэликтоевна
            Данная программа имеет базовую функциональность для рисования примитивных элементов графики:
                1. линия с изменяемой величиной;
                2. правильный многоугольник толщины 1;
                3. правильная звезда толщины 1.
            Присутствует возможность выбора цвета, угла поворота фигуры, изменения количества вершин у фигуры.
            Минимальный размер холста - 100х100.
            Максимальный размер холста - 1024х1024.

            Подсказки для работы в приложении:
            " Alt + F " - вызов меню "File"
            " Alt + F + N " - создать новый файл
            " Alt + F + O " - открыть файл
            " Alt + F + S " - сохранить файл
            " Alt + F + E " - выйти
            " Alt + E " - вызов меню "Edit"
            " Alt + E + S " - настроить параметры
            " Alt + E + C " - выбрать цвет
            " Alt + E + L " - нарисовать линию
            " Alt + E + P " - правильный многоугольник
            " Alt + E + Z " - правильная звезда
            " Alt + E + F " - применить заливку
            " Alt + E + X " - очистить все
            " Alt + H " - вызов меню "Help"
            """;

    public MyHelpDialog(int mode) {
        super(new Frame(), "О программе", ModalityType.APPLICATION_MODAL);
        Logger.getInstance().info("info dialog opened");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JTextArea textArea = new JTextArea();
        if(mode == WARNING_MODE){
            textArea.setText(warningText);
        } else {
            textArea.setText(helpText);
        }
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
