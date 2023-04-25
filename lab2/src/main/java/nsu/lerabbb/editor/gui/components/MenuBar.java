package nsu.lerabbb.editor.gui.components;

import lombok.Setter;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.controller.FileController;
import nsu.lerabbb.editor.gui.controllable.menuitem.MenuItem;
import nsu.lerabbb.editor.gui.dialogs.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {
    private JMenu fileMenu;
    private JMenu modifyMenu;
    private JMenu filterMenu;
    private JMenu helpMenu;

    private JMenuItem openFileItem;
    private JMenuItem saveFileItem;
    private JMenuItem exitFileItem;
    private JMenuItem helpItem;
    private JMenuItem bwItem;
    private JMenuItem negativeItem;
    private JMenuItem blurItem;
    private JMenuItem sharpItem;
    private JMenuItem gammaItem;
    private JMenuItem fsdItem;
    private JMenuItem odItem;
    private JMenuItem robertsItem;
    private JMenuItem sobelItem;
    private JMenuItem embossItem;
    private JMenuItem watercolorItem;
    private JMenuItem rotateItem;
    private JMenuItem selectItem;
    private JMenuItem fitScreenItem;
    private JMenuItem realSizeItem;

    @Setter
    private EditController editController;
    @Setter
    private FileController fileController;

    public MenuBar(){
        super();
        initFileMenu(this);
        initHelpMenu(this);
        initModifyMenu(this);
        initFilterMenu(this);

        add(fileMenu);
        add(modifyMenu);
        add(filterMenu);
        add(helpMenu);
    }

    private void initFileMenu(ActionListener l){
        fileMenu = new JMenu("File");

        openFileItem = new MenuItem("Open", l);
        saveFileItem = new MenuItem("Save", l);
        exitFileItem = new MenuItem("Exit", l);

        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.add(exitFileItem);
    }

    private void initModifyMenu(ActionListener l){
        modifyMenu = new JMenu("Modify");

        rotateItem = new MenuItem("Rotate image", l);
        fitScreenItem = new MenuItem("Fit screen", l);
        realSizeItem = new MenuItem("Real size of image", l);

        modifyMenu.add(rotateItem);
        modifyMenu.add(fitScreenItem);
        modifyMenu.add(realSizeItem);
    }

    private void initFilterMenu(ActionListener l){
        filterMenu = new JMenu("Filter");

        bwItem = new MenuItem("Black-White filter", l);
        negativeItem = new MenuItem("Negative filter", l);
        blurItem = new MenuItem("Blur filter", l);
        sharpItem = new MenuItem("Sharpen filter", l);
        gammaItem = new MenuItem("Gamma filter", l);
        fsdItem = new MenuItem("Floyd-Steinberg dithering", l);
        odItem = new MenuItem("Ordered dithering", l);
        robertsItem = new MenuItem("Roberts operator", l);
        sobelItem = new MenuItem("Sobel operator", l);
        embossItem = new MenuItem("Emboss filter", l);
        watercolorItem = new MenuItem("Watercolor filter", l);
        selectItem = new MenuItem("Color splash filter", l);

        filterMenu.add(bwItem);
        filterMenu.add(negativeItem);
        filterMenu.add(gammaItem);
        filterMenu.add(blurItem);
        filterMenu.add(sharpItem);
        filterMenu.add(embossItem);
        filterMenu.add(fsdItem);
        filterMenu.add(odItem);
        filterMenu.add(robertsItem);
        filterMenu.add(sobelItem);
        filterMenu.add(watercolorItem);
        filterMenu.add(selectItem);
    }

    private void initHelpMenu(ActionListener l){
        helpMenu = new JMenu("Help");
        helpItem = new MenuItem("About program", l);
        helpMenu.add(helpItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitFileItem){
            fileController.exitFile();
        }
        if(e.getSource() == helpItem){
            fileController.openInfoDialog();
        }
        if(e.getSource() == openFileItem){
            fileController.openFile();
            editController.setImg(fileController.getImage());
            editController.setImgChanged(true);
        }
        if(e.getSource() == saveFileItem){
            fileController.saveFile();
        }
        if(e.getSource() == bwItem){
            editController.onBlackWhiteAction();
        }
        if(e.getSource() == negativeItem){
            editController.onNegativeAction();
        }
        if(e.getSource() == gammaItem){
            new GammaCorDialog(editController);
        }
        if(e.getSource() == fsdItem){
            new FsdDialog(editController);
        }
        if(e.getSource() == odItem){
            new OdDialog(editController);
        }
        if(e.getSource() == blurItem){
            new BlurDialog(editController);
        }
        if(e.getSource() == sharpItem){
            editController.onSharpAction();
        }
        if(e.getSource() == embossItem){
            editController.onEmbossAction();
        }
        if(e.getSource() == robertsItem){
            new RobertsDialog(editController);
        }
        if(e.getSource() == sobelItem){
            new SobelDialog(editController);
        }
        if(e.getSource() == watercolorItem){
            editController.onWaterColoring();
        }
        if(e.getSource() == selectItem){
            editController.onSelectAction();
        }
        if(e.getSource() == rotateItem){
            new RotateDialog(editController);
        }
        if(e.getSource() == fitScreenItem){
            new InterpolationDialog(editController);
        }
        if(e.getSource() == realSizeItem){
            editController.onRealSizeAction();
        }
    }
}
