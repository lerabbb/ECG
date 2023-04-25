package nsu.lerabbb.editor.controller;

import lombok.Getter;
import lombok.Setter;
import nsu.lerabbb.editor.gui.ImagePanel;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.filter.Filter;
import nsu.lerabbb.editor.logic.filter.Interpolation;
import nsu.lerabbb.editor.logic.filter.MedianFilter;
import nsu.lerabbb.editor.logic.filter.Rotation;
import nsu.lerabbb.editor.logic.filter.border.RobertsOperator;
import nsu.lerabbb.editor.logic.filter.border.SobelOperator;
import nsu.lerabbb.editor.logic.filter.convolution.BlurFilter;
import nsu.lerabbb.editor.logic.filter.convolution.EmbossFilter;
import nsu.lerabbb.editor.logic.filter.convolution.SharpFilter;
import nsu.lerabbb.editor.logic.filter.dithering.FloydSteinbergDithering;
import nsu.lerabbb.editor.logic.filter.dithering.OrderedDithering;
import nsu.lerabbb.editor.logic.filter.pixel.BlackWhiteFilter;
import nsu.lerabbb.editor.logic.filter.pixel.ColorSplash;
import nsu.lerabbb.editor.logic.filter.pixel.GammaCorrection;
import nsu.lerabbb.editor.logic.filter.pixel.NegativeFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class EditController {
    private final Filter robert;
    private final Filter sobel;
    private final Filter blur;
    private final Filter sharp;
    private final Filter emboss;
    private final Filter floydSteinberg;
    private final Filter orderedDith;
    private final Filter blackWhite;
    private final Filter gamma;
    private final Filter negative;
    private final Filter rotate;
    private final Filter select;
    private final Filter median;
    private final Filter interpolate;
    @Setter
    private boolean imgChanged;
    private boolean isImgOriginal;

    private JPanel panel;
    @Setter
    private BufferedImage img;
    @Setter
    private BufferedImage editedImg;
    private EditMode curMode;

    public EditController(JPanel panel){
        robert = new RobertsOperator();
        sobel = new SobelOperator();
        blur = new BlurFilter();
        sharp = new SharpFilter();
        emboss = new EmbossFilter();
        floydSteinberg = new FloydSteinbergDithering();
        orderedDith = new OrderedDithering();
        blackWhite = new BlackWhiteFilter();
        gamma = new GammaCorrection();
        negative = new NegativeFilter();
        rotate = new Rotation();
        select = new ColorSplash();
        median = new MedianFilter();
        interpolate = new Interpolation();
        this.panel = panel;
        curMode = EditMode.ORIGINAL;
        imgChanged = false;
        isImgOriginal = true;
    }

    public void onBlackWhiteAction() {
        if (curMode == EditMode.BLACK_WHITE && !imgChanged) {
            return;
        }
        editImg(blackWhite, EditMode.BLACK_WHITE);
    }

    public void onNegativeAction(){
        if(curMode == EditMode.NEGATIVE && !imgChanged){
            return;
        }
        editImg(negative, EditMode.NEGATIVE);
    }

    public void onBlurAction(){
        if(curMode == EditMode.BLUR && !imgChanged
                && Config.getGaussSigma() == ((BlurFilter) blur).getPrevSigma()
                && Config.getBlurSize() == ((BlurFilter)blur).getPrevN())
        {
            return;
        }
        editImg(blur, EditMode.BLUR);
    }
    public void onSharpAction(){
        if(curMode == EditMode.SHARP && !imgChanged){
            return;
        }
        editImg(sharp, EditMode.SHARP);
    }

    public void onGammaCorAction(){
        if(curMode == EditMode.GAMMA && !imgChanged
            && Config.getGammaCorrection() == ((GammaCorrection)gamma).getGamma()){
            return;
        }
        editImg(gamma, EditMode.GAMMA);
    }
    public void onFsdAction() {
        if (curMode == EditMode.FSD && !imgChanged
                && Config.getRedFsNum() == ((FloydSteinbergDithering)floydSteinberg).getRedNum()
                && Config.getGreenFsNum() == ((FloydSteinbergDithering)floydSteinberg).getGreenNum()
                && Config.getBlueFsNum() == ((FloydSteinbergDithering)floydSteinberg).getBlueNum()) {
            return;
        }
        editImg(floydSteinberg, EditMode.FSD);
    }
    public void onOdAction() {
        if (curMode == EditMode.OD && !imgChanged) {
            return;
        }
        editImg(orderedDith, EditMode.OD);
    }

    public void onRobertsAction() {
        if (curMode == EditMode.ROBERTS && !imgChanged) {
            return;
        }
        editImg(robert, EditMode.ROBERTS);
    }

    public void onSobelAction() {
        if (curMode == EditMode.SOBEL && !imgChanged) {
            return;
        }
        editImg(sobel, EditMode.SOBEL);
    }

    public void onEmbossAction() {
        if (curMode == EditMode.EMBOSS && !imgChanged) {
            return;
        }
        editImg(emboss, EditMode.EMBOSS);
    }

    public void onWaterColoring(){
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(curMode == EditMode.WATER_COLOR && !imgChanged){
            return;
        }
        curMode = EditMode.WATER_COLOR;
        isImgOriginal = false;
        editedImg = median.edit(img);
        editedImg = sharp.edit(editedImg);
        ((ImagePanel) panel).setImage(editedImg,false);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void onRotateAction(){
        if (curMode == EditMode.ROTATE && !imgChanged
                && ((Rotation)rotate).getPrevAngle() == Config.getRotation()) {
            return;
        }
        editImg(rotate, EditMode.ROTATE);
    }

    public void onSelectAction(){
        new JColorChooser();
        Color color = JColorChooser.showDialog(null, "Выберите цвет", Color.BLACK);
        Config.setColorSelection(color);
        if(curMode == EditMode.SELECT
                && ((ColorSplash)select).getSelected() == Config.getColorSelection().getRGB()){
            return;
        }
        editImg(select, EditMode.SELECT);
    }

    public void onFitScreenAction(){
//        ((ImagePanel) panel).fitScreen();
        if(curMode == EditMode.ORIGINAL){
            editedImg = img;
        }
//        if(curMode == EditMode.INTERPOLATION && !imgChanged
//                && ((Interpolation)interpolate).getPrevMode() == Config.getInterpMode()){
//            return;
//        }
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        curMode = EditMode.INTERPOLATION;
        isImgOriginal = false;
//        ((Interpolation)interpolate).setOldSize(((ImagePanel)panel).getPanelSize());
        editedImg = interpolate.edit(img);
        ((ImagePanel) panel).setPanelSize(new Dimension(editedImg.getWidth(), editedImg.getHeight()));
        ((ImagePanel) panel).setImage(editedImg, false);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    public void onRealSizeAction(){
        ((ImagePanel) panel).realSize();
    }

    public void onChangeImage(){
        if(isImgOriginal) {
            ((ImagePanel) panel).setImage(editedImg, false);
        } else {
            ((ImagePanel) panel).setImage(img, false);
        }
        isImgOriginal = !isImgOriginal;
    }

    private void editImg(Filter filter, EditMode mode){
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        curMode = mode;
        isImgOriginal = false;
        editedImg = filter.edit(img);
        ((ImagePanel) panel).setImage(editedImg, false);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
