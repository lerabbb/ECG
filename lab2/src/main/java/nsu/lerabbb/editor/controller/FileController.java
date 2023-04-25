package nsu.lerabbb.editor.controller;

import lombok.Getter;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.gui.ImagePanel;
import nsu.lerabbb.editor.gui.dialogs.InfoDialog;
import nsu.lerabbb.editor.logic.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileController {
    private JFrame frame;
    private JPanel panel;
    @Getter
    private BufferedImage image;

    public FileController(JFrame frame, JPanel panel){
        this.frame = frame;
        this.panel = panel;
    }

    public void openFile(){
        try {
            FileDialog fd = new FileDialog(frame, "Открыть изображение", FileDialog.LOAD);
            fd.setFile("*.png; *.jpg; *.jpeg; *.gif; *.bmp");
            fd.setVisible(true);
            if (fd.getFile() == null)
                return;
            File inputFile = (fd.getFiles()[0]);
            image = ImageIO.read(inputFile);
            ((ImagePanel) panel).setImage(image, true);
            ((ImagePanel) panel).realSize();
        } catch(IOException e){
            Logger.getInstance().error(e.getMessage());
        }
    }

    public void saveFile(){
        try {
            FileDialog fd = new FileDialog(frame, "Сохранить изображение", FileDialog.SAVE);
            fd.setFile("*.png");
            fd.setVisible(true);
            if (fd.getFile() == null)
                return;
            File outputFile = (fd.getFiles()[0]);
            ImageIO.write(((ImagePanel) panel).getImg(), "png", outputFile);
        } catch (IOException e){
            Logger.getInstance().error(e.getMessage());
        }
    }

    public void exitFile(){
        System.exit(0);
    }
    public void openInfoDialog(){
        new InfoDialog(InfoDialog.HELP_MODE);
    }
}
