package nsu.lerabbb.wireframe.gui.frames;

import nsu.lerabbb.wireframe.controllers.EditController;
import nsu.lerabbb.wireframe.controllers.FileController;
import nsu.lerabbb.wireframe.gui.MainPanel;
import nsu.lerabbb.wireframe.gui.components.ToolBar;
import nsu.lerabbb.wireframe.gui.components.MenuBar;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private JPanel mainPanel;
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private EditController editController;
    private FileController fileController;

    public MyFrame(){
        super("Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        toolBar = new ToolBar();
        add(toolBar, BorderLayout.NORTH);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        mainPanel = new MainPanel();
        initControllers();
        add(mainPanel);

        setMinimumSize(new Dimension(640, 480));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initControllers(){
        fileController = new FileController(this, mainPanel);
        editController = new EditController(this, mainPanel);

        ((ToolBar) toolBar).setEditController(editController);
        ((ToolBar) toolBar).setFileController(fileController);

        ((MenuBar) menuBar).setEditController(editController);
        ((MenuBar) menuBar).setFileController(fileController);
    }
}
