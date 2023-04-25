package nsu.lerabbb.editor.gui.dialogs;

import nsu.lerabbb.editor.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class InfoDialog extends JDialog implements ActionListener {
    private final JButton okButton;
    public static final int WARNING_MODE = 1;
    public static final int HELP_MODE = 2;
    private final String warningText = "Неверно введены параметры";
    private final String helpText = """
            --------------------------------------Приложение "Редактор изображений"-------------------------------------
            Автор программы: студент ФИТ 20206, Базаргуроева Валерия Бэликтоевна
            Приложение предназначено для обработки изображений. Реализованы следующие инструменты обработки:
            1. Сглаживание изображения. Регулируемые параметры: размер ядра фильтра (3-11), сигма (0.5-10)
               При размере ядра 3 или 5 используется размытие по Гауссу. При больших размерах используется сглаживание
               по усредненному значению.
            2. Фильтр повышения резкости.
            3. Тиснение.
            4. Перевод цветного изображения в черно-белое.
            5. Преобразование изображения в негативное.
            6. Гамма-коррекция. Регулируемый параметр: гамма (0.1-10).
            7. Фильтры выделения границ (операторы Робертса и Собеля). Регулируемый параметр: порог бинаризации (0-255).
            8. Дизеринг алгоритмом Флойда-Стейнберга. Регулируемые параметры: число квантования для каждого цвета (2-128).
            9. Упорядоченный дизеринг. Регулируемые параметры: число квантования для каждого цвета (2-128).
            10. Акварелизация.
            11. Поворот изображения. Регулируемый параметр: угол поворота (0-360).
            12. Эффект "всплеск цвета". Регулируемый параметр: выделяемый цвет.
            """;

    public InfoDialog(int mode) {
        super(new Frame(), "Информация", Dialog.ModalityType.APPLICATION_MODAL);
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
