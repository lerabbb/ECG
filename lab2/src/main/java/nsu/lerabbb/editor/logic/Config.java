package nsu.lerabbb.editor.logic;

import java.awt.*;
import java.awt.image.AffineTransformOp;

public class Config {
    private static Config config;
    private int filterSize;
    private float gaussSigma;
    private float gammaCorr;
    private int redFsd;
    private int greenFsd;
    private int blueFsd;
    private int redOd;
    private int greenOd;
    private int blueOd;
    private int binRoberts;
    private int binSobel;
    private int rotation;
    private Object interpMode;
    private Color selectColor;
    private Dimension panelSize;

    public static Config getInstance(){
        if(config == null){
            config = new Config();
        }
        return config;
    }

    private Config(){
        filterSize = Utils.BLUR_SIZE_MIN;
        gaussSigma = Utils.SIGMA_MIN;
        gammaCorr = Utils.GAMMA_MIN;
        redFsd = greenFsd = blueFsd = Utils.FSD_QUANT_MIN;
        redOd = greenOd = blueOd = Utils.OD_QUANT_MIN;
        binRoberts = (Utils.ROBERTS_MAX+1)/2;
        binSobel = (Utils.SOBEL_MAX+1)/2;
        rotation = Utils.ROTATE_MIN;
        interpMode = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        selectColor = Color.RED;
    }

    public static void setBlurSize(int size){
        getInstance().filterSize = size;
    }
    public static int getBlurSize(){return getInstance().filterSize;}

    public static void setGaussSigma(float sigma){
        getInstance().gaussSigma = sigma;
    }
    public static float getGaussSigma(){return getInstance().gaussSigma;}
    public static void setGammaCorrection(float gamma){
        getInstance().gammaCorr = gamma;
    }
    public static float getGammaCorrection(){return getInstance().gammaCorr;}

    public static void setBinRoberts(int bin){
        getInstance().binRoberts = bin;
    }
    public static int getBinRoberts(){return getInstance().binRoberts;}

    public static void setBinSobel(int bin){
        getInstance().binSobel = bin;
    }
    public static int getBinSobel(){return getInstance().binSobel;}

    public static void setRedFsNum(int val){
        getInstance().redFsd = val;
    }
    public static int getRedFsNum(){return getInstance().redFsd;}

    public static void setGreenFsNum(int val){
        getInstance().greenFsd = val;
    }
    public static int getGreenFsNum(){return getInstance().greenFsd;}

    public static void setBlueFsNum(int val){
        getInstance().blueFsd = val;
    }
    public static int getBlueFsNum(){return getInstance().blueFsd;}

    public static void setRedOdNum(int val){
        getInstance().redOd = val;
    }
    public static int getRedOdNum(){return getInstance().redOd;}

    public static void setGreenOdNum(int val){
        getInstance().greenOd = val;
    }
    public static int getGreenOdNum(){return getInstance().greenOd;}

    public static void setBlueOdNum(int val){
        getInstance().blueOd = val;
    }
    public static int getBlueOdNum(){return getInstance().blueOd;}

    public static void setRotation(int val){
        getInstance().rotation = val;
    }
    public static int getRotation(){return getInstance().rotation;}

    public static void setInterpMode(Object val){
        getInstance().interpMode = val;
    }
    public static Object getInterpMode(){return getInstance().interpMode;}

    public static void setColorSelection(Color val){
        getInstance().selectColor = val;
    }
    public static Color getColorSelection(){return getInstance().selectColor;}

    public static void setPanelSize(Dimension val){
        getInstance().panelSize = val;
    }
    public static Dimension getPanelSize(){return getInstance().panelSize;}
}
