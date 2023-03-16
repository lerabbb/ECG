package nsu.lerabbb.paint.ui;

import nsu.lerabbb.paint.Controller;
import nsu.lerabbb.paint.Logger;
import nsu.lerabbb.paint.logic.PaintConfig;
import nsu.lerabbb.paint.ui.components.MyMenuBar;
import nsu.lerabbb.paint.ui.components.MyToolBar;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private JPanel myPanel;
    private JScrollPane scrollPane;

    public MyFrame() {
        super("Paint");
        Logger.getInstance().info("frame creation started");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/app_icon.png")));

        JToolBar toolBar = new MyToolBar();
        JMenuBar menuBar = new MyMenuBar();
        initScroll();
        Controller.setFrame(this);
        Controller.setPanel(myPanel);

        setJMenuBar(menuBar);
        add(toolBar, BorderLayout.NORTH);
        add(scrollPane);
        setPreferredSize(
                new Dimension(
                        PaintConfig.getInstance().getSpaceWidth() + 200,
                        PaintConfig.getInstance().getSpaceHeight() + 200
                )
        );
        setMinimumSize(new Dimension(640, 480));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        Logger.getInstance().info("frame created and is showing");
    }

    private void initScroll() {
        myPanel = new MyPanel();
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(myPanel);
    }
}