package nsu.lerabbb.paint.logic;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class PaintConfig {
    public static final int LINE_MODE = 0;
    public static final int FILL_MODE = 1;
    public static final int POLYGON_MODE = 2;
    public static final int STAR_MODE = 3;

    private static PaintConfig paintConfig;

    @Getter @Setter
    private int mode;
    @Getter @Setter
    private int peaksNum;
    @Getter @Setter
    private int radius;
    @Getter @Setter
    private int rotation;
    @Getter @Setter
    private int stroke;
    @Getter @Setter
    private Color color;
    @Getter @Setter
    private int spaceWidth;
    @Getter @Setter
    private int spaceHeight;

    public static PaintConfig getInstance(){
        if(paintConfig == null){
            paintConfig = new PaintConfig();
        }
        return paintConfig;
    }

    private PaintConfig(){
        mode = LINE_MODE;
        peaksNum = 4;
        radius = 10;
        rotation = 0;
        stroke = 1;
        color = Color.BLACK;
        spaceWidth = 640;
        spaceHeight = 480;
    }

    public int getRGB(){ return color.getRGB(); }
}