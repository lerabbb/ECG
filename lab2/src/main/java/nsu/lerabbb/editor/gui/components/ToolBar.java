package nsu.lerabbb.editor.gui.components;

import lombok.Setter;
import nsu.lerabbb.editor.controller.EditController;
import nsu.lerabbb.editor.controller.FileController;
import nsu.lerabbb.editor.gui.ImagePanel;
import nsu.lerabbb.editor.gui.controllable.button.*;
import nsu.lerabbb.editor.gui.dialogs.*;
import nsu.lerabbb.editor.logic.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ToolBar extends JToolBar implements ActionListener, ItemListener {
    private JToggleButton openFileBtn;
    private JToggleButton saveBtn;
    private JToggleButton exitBtn;
    private JToggleButton infoBtn;
    private JToggleButton bwBtn;
    private JToggleButton negativeBtn;
    private JToggleButton blurBtn;
    private JToggleButton sharpBtn;
    private JToggleButton gammaBtn;
    private JToggleButton fsdBtn;
    private JToggleButton odBtn;
    private JToggleButton robertsBtn;
    private JToggleButton sobelBtn;
    private JToggleButton embossBtn;
    private JToggleButton watercolorBtn;
    private JToggleButton rotateBtn;
    private JToggleButton selectBtn;
    private JButton fitScreenBtn;
    private JButton realSizeBtn;
    private ButtonGroup group;

    @Setter
    private EditController editController;
    @Setter
    private FileController fileController;
    @Setter
    private JPanel panel;
    @Setter
    private JFrame frame;

    public ToolBar(){
        super();
        group = new ButtonGroup();
        initUtilGroup();
        initEditGroup();
        addButtons();
    }

    private void initUtilGroup(){
        openFileBtn = new OpenFileButton(this, this);
        saveBtn = new SaveButton(this, this);
        exitBtn = new ExitButton(this, this);
        infoBtn = new InfoButton(this, this);

        group = new ButtonGroup();
        group.add(openFileBtn);
        group.add(saveBtn);
        group.add(exitBtn);
        group.add(infoBtn);
    }

    private void initEditGroup(){
        bwBtn = new IconToggleButton("Черно-белый", "/icons/black_white.png", this, this);
        negativeBtn = new IconToggleButton("Негатив", "/icons/invert.png", this, this);
        blurBtn = new IconToggleButton("Размытие по Гауссу", "/icons/blur.png", this, this);
        sharpBtn = new IconToggleButton("Повысить резкость", "/icons/sharp.png", this, this);
        embossBtn = new IconToggleButton("Тиснение", "/icons/emboss.png", this, this);
        gammaBtn = new IconToggleButton("Гамма-коррекция", "/icons/gamma.png", this, this);
        fsdBtn = new IconToggleButton("Дизеринг Флойда-Стейнберга", "/icons/fsd.png", this, this);
        odBtn = new IconToggleButton("Упорядоченный дизеринг", "/icons/od.png", this, this);
        robertsBtn = new IconToggleButton("Выделение границ (Робертс)", "/icons/roberts.png", this, this);
        sobelBtn = new IconToggleButton("Выделение границ (Собель)", "/icons/sobel.png", this, this);
        watercolorBtn = new IconToggleButton("Акварелизация", "/icons/watercolor.png", this, this);
        rotateBtn = new IconToggleButton("Повернуть изображение", "/icons/rotate.png", this, this);
        selectBtn = new IconToggleButton("Всплеск цвета", "/icons/color_select.png", this, this);
        fitScreenBtn = new IconButton("Подогнать по размер окна", "/icons/fitscreen.png", this);
        realSizeBtn = new IconButton("Реальный размер изображения", "/icons/realsize.png", this);

        group.add(bwBtn);
        group.add(negativeBtn);
        group.add(blurBtn);
        group.add(sharpBtn);
        group.add(embossBtn);
        group.add(gammaBtn);
        group.add(fsdBtn);
        group.add(odBtn);
        group.add(robertsBtn);
        group.add(sobelBtn);
        group.add(watercolorBtn);
        group.add(rotateBtn);
        group.add(selectBtn);
    }

    private void addButtons(){
        add(openFileBtn);
        add(saveBtn);
        add(exitBtn);
        add(infoBtn);
        add(bwBtn);
        add(negativeBtn);
        add(blurBtn);
        add(sharpBtn);
        add(embossBtn);
        add(gammaBtn);
        add(robertsBtn);
        add(sobelBtn);
        add(fsdBtn);
        add(odBtn);
        add(watercolorBtn);
        add(selectBtn);
        add(rotateBtn);
        add(fitScreenBtn);
        add(realSizeBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitBtn){
            fileController.exitFile();
        }
        if(e.getSource() == infoBtn){
            fileController.openInfoDialog();
        }
        if(e.getSource() == openFileBtn){
            fileController.openFile();
            editController.setImg(fileController.getImage());
            editController.setImgChanged(true);
        }
        if(e.getSource() == saveBtn){
            fileController.saveFile();
        }
        if(e.getSource() == bwBtn){
            editController.onBlackWhiteAction();
        }
        if(e.getSource() == negativeBtn){
            editController.onNegativeAction();
        }
        if(e.getSource() == gammaBtn){
            new GammaCorDialog(editController);
        }
        if(e.getSource() == fsdBtn){
            new FsdDialog(editController);
        }
        if(e.getSource() == odBtn){
            new OdDialog(editController);
        }
        if(e.getSource() == blurBtn){
            new BlurDialog(editController);
        }
        if(e.getSource() == sharpBtn){
            editController.onSharpAction();
        }
        if(e.getSource() == embossBtn){
            editController.onEmbossAction();
        }
        if(e.getSource() == robertsBtn){
            new RobertsDialog(editController);
        }
        if(e.getSource() == sobelBtn){
            new SobelDialog(editController);
        }
        if(e.getSource() == watercolorBtn){
            editController.onWaterColoring();
        }
        if(e.getSource() == selectBtn){
            editController.onSelectAction();
        }
        if(e.getSource() == rotateBtn){
            new RotateDialog(editController);
        }
        if(e.getSource() == fitScreenBtn){
//            editController.onFitScreenAction();
            editController.setEditedImg(((ImagePanel)panel).getImg());
            Config.setPanelSize(((ImagePanel)panel).getVisibleRectSize());
            new InterpolationDialog(editController);
        }
        if(e.getSource() == realSizeBtn){
            editController.onRealSizeAction();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
