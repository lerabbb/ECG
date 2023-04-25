package nsu.lerabbb.editor.logic.filter;

import lombok.Getter;
import nsu.lerabbb.editor.logic.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotation implements Filter{
    @Getter
    private float prevAngle;
    private int W;
    private int H;
    private float matr[];

    @Override
    public BufferedImage edit(BufferedImage img) {
        updateParams(img.getWidth(), img.getHeight());
        BufferedImage newImg = new BufferedImage(W, H, img.getType());

        int x,y, oldX, oldY;
        int dx = W/2;
        int dy = H/2;
        int oldWidth = img.getWidth();
        int oldHeight = img.getHeight();

        for(y=0; y<H; y++){
            for(x=0; x<W; x++){
                oldX = (int) ((x-dx)*matr[0] + (y-dy)*matr[1]) + oldWidth/2;
                oldY = (int) ((x-dx)*matr[2] + (y-dy)*matr[3]) + oldHeight/2;

                if(oldX >= 0 && oldX < oldWidth && oldY>=0 && oldY<oldHeight){
                    newImg.setRGB(x, y, img.getRGB(oldX, oldY));
                }
                else {
                    newImg.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        return newImg;
    }

    private void updateParams(int oldW, int oldH){
        if(prevAngle == Config.getRotation()){
            return;
        }
        prevAngle = (float) (Config.getRotation() * Math.PI/180);
        float cos = (float) Math.cos(-prevAngle);
        float sin = (float) Math.sin(-prevAngle);
        matr = new float[]{cos, sin, -sin, cos};
        W = (int) (oldW * Math.abs(cos) + oldH * Math.abs(sin));
        H = (int) (oldW * Math.abs(sin) + oldH * Math.abs(cos));
    }
}
