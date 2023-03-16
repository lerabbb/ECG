package nsu.lerabbb.paint;

import nsu.lerabbb.paint.ui.components.MyHelpDialog;
import nsu.lerabbb.paint.ui.components.MySettingsDialog;
import nsu.lerabbb.paint.logic.PaintConfig;
import nsu.lerabbb.paint.ui.MyPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Controller {
    private static Controller controller;

    private JFrame frame;
    private JPanel panel;

    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    private void setFrame_(JFrame frame){
        this.frame = frame;
    }
    private void setPanel_(JPanel panel){
        this.panel = panel;
    }
    public static void setFrame(JFrame frame){
        getInstance().setFrame_(frame);
    }
    public static void setPanel(JPanel panel){
        getInstance().setPanel_(panel);
    }

    public static void chooseColor(){
        new JColorChooser();
        Color color = JColorChooser.showDialog(null, "Выберите цвет", Color.BLACK);
        PaintConfig.getInstance().setColor(color);
    }

    public static void chooseShape(int mode, int peaks){
        PaintConfig.getInstance().setMode(mode);
        PaintConfig.getInstance().setPeaksNum(peaks);
    }

    public static void chooseSettings(){
        new MySettingsDialog();
        ((MyPanel) getInstance().panel).resizeWorkSpace(
             PaintConfig.getInstance().getSpaceWidth(),
             PaintConfig.getInstance().getSpaceHeight()
        );
    }


    public static void openFile(){
        try {
            FileDialog fd = new FileDialog(getInstance().frame, "Открыть изображение", FileDialog.LOAD);
            fd.setFile("*.png; *.jpg; *.jpeg; *.gif; *.bmp");
            fd.setVisible(true);
            if (fd.getFile() == null)
                return;
            File inputFile = (fd.getFiles()[0]);
            ((MyPanel) getInstance().panel).addFileToWorkSpace(inputFile);
        } catch(IOException e){
            Logger.getInstance().error(e.getMessage());
        }
    }

    public static void saveFile(){
        try {
            FileDialog fd = new FileDialog(getInstance().frame, "Сохранить изображение", FileDialog.SAVE);
            fd.setFile("*.png");
            fd.setVisible(true);
            if (fd.getFile() == null)
                return;
            File outputFile = (fd.getFiles()[0]);
            ImageIO.write(((MyPanel) getInstance().panel).getBufferedImage(), "png", outputFile);
        } catch (IOException e){
            Logger.getInstance().error(e.getMessage());
        }
    }

    public static void clearWorkSpace(){
        ((MyPanel) getInstance().panel).clearWorkSpace();
    }

    public static void openHelp(){
        new MyHelpDialog(MyHelpDialog.HELP_MODE);
    }
}
