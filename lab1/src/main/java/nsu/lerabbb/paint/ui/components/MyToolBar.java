package nsu.lerabbb.paint.ui.components;

import nsu.lerabbb.paint.Controller;
import nsu.lerabbb.paint.ui.control_elements.MyIconButton;
import nsu.lerabbb.paint.logic.PaintConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyToolBar extends JToolBar implements ActionListener, ItemListener {

    private final JToggleButton infoBtn;
    private final JToggleButton loadFileBtn;
    private final JToggleButton settingsBtn;
    private final JToggleButton lineBtn;
    private final JToggleButton fillBtn;
    private final JToggleButton redBtn;
    private final JToggleButton yellowBtn;
    private final JToggleButton greenBtn;
    private final JToggleButton lightBlueBtn;
    private final JToggleButton blueBtn;
    private final JToggleButton violetBtn;
    private final JToggleButton whiteBtn;
    private final JToggleButton blackBtn;
    private final JToggleButton paletteBtn;
    private final JToggleButton polygon4Btn;
    private final JToggleButton polygon5Btn;
    private final JToggleButton polygon6Btn;
    private final JToggleButton star4Btn;
    private final JToggleButton star5Btn;
    private final JToggleButton star6Btn;

    public MyToolBar(){
        super();

        infoBtn = new MyIconButton("О программе","/icons/info.png", this, this);
        loadFileBtn = new MyIconButton("Заргузить файл","/icons/load_file.png", this, this);
        settingsBtn = new MyIconButton("Настроить параметры","/icons/settings.png", this, this);
        lineBtn = new MyIconButton("Нарисовать линию", "/icons/line.png",this, this);
        fillBtn = new MyIconButton("Применить заливку","/icons/fill.png", this, this);

        redBtn = new MyIconButton("Красный цвет", "/icons/redBtn.png", this, this);
        yellowBtn = new MyIconButton("Желтый цвет", "/icons/yellowBtn.png", this, this);
        greenBtn = new MyIconButton("Зеленый цвет", "/icons/greenBtn.png", this, this);
        lightBlueBtn = new MyIconButton("Голубой цвет", "/icons/lightBlueBtn.png", this, this);
        blueBtn = new MyIconButton("Синий цвет", "/icons/blueBtn.png", this, this);
        violetBtn = new MyIconButton("Фиолетовый цвет", "/icons/violetBtn.png", this, this);
        whiteBtn = new MyIconButton("Белый цвет", "/icons/whiteBtn.png", this, this);
        blackBtn = new MyIconButton("Черный цвет", "/icons/blackBtn.png", this, this);
        paletteBtn = new MyIconButton("Выбрать цвет", "/icons/palette.png", this, this);

        polygon4Btn = new MyIconButton("Правильный четырехугольник", "/icons/polygon4.png", this, this);
        polygon5Btn = new MyIconButton("Правильный пятиугольник", "/icons/polygon5.png", this, this);
        polygon6Btn = new MyIconButton("Правильный шестиугольник", "/icons/polygon6.png", this, this);
        star4Btn = new MyIconButton("Четырехконечная звезда", "/icons/star4.png", this, this);
        star5Btn = new MyIconButton("Пятиконечная звезда", "/icons/star5.png", this, this);
        star6Btn = new MyIconButton("Шестиконечная звезда", "/icons/star6.png", this, this);

        initToolGroup();
        initColorGroup();
        addButtons();
    }

    private void initColorGroup(){
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(redBtn);
        colorGroup.add(yellowBtn);
        colorGroup.add(greenBtn);
        colorGroup.add(lightBlueBtn);
        colorGroup.add(blueBtn);
        colorGroup.add(violetBtn);
        colorGroup.add(whiteBtn);
        colorGroup.add(blackBtn);
    }
    private void initToolGroup(){
        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(infoBtn);
        toolGroup.add(loadFileBtn);
        toolGroup.add(settingsBtn);
        toolGroup.add(lineBtn);
        toolGroup.add(fillBtn);
        toolGroup.add(polygon4Btn);
        toolGroup.add(polygon5Btn);
        toolGroup.add(polygon6Btn);
        toolGroup.add(star4Btn);
        toolGroup.add(star5Btn);
        toolGroup.add(star6Btn);
    }

    private void addButtons(){
        add(infoBtn);
        add(loadFileBtn);
        add(settingsBtn);
        add(lineBtn);
        add(fillBtn);
        add(polygon4Btn);
        add(polygon5Btn);
        add(polygon6Btn);
        add(star4Btn);
        add(star5Btn);
        add(star6Btn);
        addSeparator();
        add(redBtn);
        add(yellowBtn);
        add(greenBtn);
        add(lightBlueBtn);
        add(blueBtn);
        add(violetBtn);
        add(whiteBtn);
        add(blackBtn);
        add(paletteBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == infoBtn) {
            Controller.openHelp();
        }
        if(e.getSource() == loadFileBtn) {
            Controller.openFile();
        }
        if(e.getSource() == settingsBtn) {
            Controller.chooseSettings();
        }

        if(e.getSource() == lineBtn) {
            PaintConfig.getInstance().setMode(PaintConfig.LINE_MODE);
        }
        if(e.getSource() == fillBtn) {
            PaintConfig.getInstance().setMode(PaintConfig.FILL_MODE);
        }
        //color events
        if(e.getSource() == redBtn){
            PaintConfig.getInstance().setColor(Color.RED);
        }
        if(e.getSource() == yellowBtn){
            PaintConfig.getInstance().setColor(Color.YELLOW);
        }
        if(e.getSource() == greenBtn){
            PaintConfig.getInstance().setColor(Color.GREEN);
        }
        if(e.getSource() == lightBlueBtn){
            PaintConfig.getInstance().setColor(Color.CYAN);
        }
        if(e.getSource() == blueBtn){
            PaintConfig.getInstance().setColor(Color.BLUE);
        }
        if(e.getSource() == violetBtn){
            PaintConfig.getInstance().setColor(Color.MAGENTA);
        }
        if(e.getSource() == whiteBtn){
            PaintConfig.getInstance().setColor(Color.WHITE);
        }
        if(e.getSource() == blackBtn){
            PaintConfig.getInstance().setColor(Color.BLACK);
        }
        if(e.getSource() == paletteBtn) {
            Controller.chooseColor();
        }

        //shapes events
        if(e.getSource() == polygon4Btn){
            Controller.chooseShape(PaintConfig.POLYGON_MODE, 4);
        }
        if(e.getSource() == polygon5Btn){
            Controller.chooseShape(PaintConfig.POLYGON_MODE, 5);
        }
        if(e.getSource() == polygon6Btn){
            Controller.chooseShape(PaintConfig.POLYGON_MODE, 6);
        }
        if(e.getSource() == star4Btn){
            Controller.chooseShape(PaintConfig.STAR_MODE, 4);
        }
        if(e.getSource() == star5Btn){
            Controller.chooseShape(PaintConfig.STAR_MODE, 5);
        }
        if(e.getSource() == star6Btn){
            Controller.chooseShape(PaintConfig.STAR_MODE, 6);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
}
