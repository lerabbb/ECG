package nsu.lerabbb.editor.gui;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.controller.FileController;
import nsu.lerabbb.editor.gui.components.MenuBar;
import nsu.lerabbb.editor.gui.components.ToolBar;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    private JPanel panel;
    private JScrollPane scrollPane;
    private FileController fileController;
    private EditController editController;

    private JToolBar toolBar;
    private JMenuBar menuBar;

    public MyFrame(){
        super("Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        toolBar = new ToolBar();
        add(toolBar, BorderLayout.NORTH);

        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        try {
            initScroll();
        }catch (Exception e){
            Logger.getInstance().error(e.getMessage());
        }
        add(scrollPane);

        initControllers();

        setMinimumSize(new Dimension(640, 480));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initScroll() throws Exception {
        scrollPane = new JScrollPane();
        scrollPane.setForeground(Color.GRAY);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel = new ImagePanel(scrollPane, this);
        scrollPane.setViewportView(panel);
    }

    private void initControllers(){
        fileController = new FileController(this, panel);
        editController = new EditController(panel);

        ((ToolBar) toolBar).setEditController(editController);
        ((ToolBar) toolBar).setFileController(fileController);
        ((ToolBar) toolBar).setPanel(panel);
        ((ToolBar) toolBar).setFrame(this);

        ((MenuBar) menuBar).setEditController(editController);
        ((MenuBar) menuBar).setFileController(fileController);
    }

    public void changeViewedImage() {
        editController.onChangeImage();
    }

    public void clickImage(int x, int y) {
        editController.onChangeImage();
    }

}
