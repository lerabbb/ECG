package nsu.lerabbb.editor.logic.filter.dithering;

import lombok.Getter;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static nsu.lerabbb.editor.logic.Utils.*;

public class FloydSteinbergDithering implements Filter {
    private final int MAX = 255;
    @Getter
    private int redNum;
    @Getter
    private int greenNum;
    @Getter
    private int blueNum;
    private int[] redQuants;
    private int[] greenQuants;
    private int[] blueQuants;
    private int a,r,g,b;
    private int er, eg, eb;
    private int[] arr;
    private int i;

    public FloydSteinbergDithering() {
        redNum = -1;
        greenNum = -1;
        blueNum = -1;
    }

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Floyd-Steinberg dithering");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        newImg.setData(img.getData());
        int x, y, pixel, neighbor;
        int maxX = newImg.getWidth();
        int maxY = newImg.getHeight();
        //int[] oldPixels = new int[maxY*maxX];

        updateQuants();
       // oldPixels[0] = img.getRGB(0,0);
        for (y = 0; y < maxY; y++) {
            for (x = 0; x < maxX; x++) {
                //pixel = oldPixels[y*maxX+x];
                pixel = newImg.getRGB(x, y);
                pixel = getNearestColor(pixel);
                newImg.setRGB(x, y, pixel);

                if (x < maxX - 1) {
                    neighbor = newImg.getRGB(x + 1, y);
                    neighbor = handlePixel(neighbor, 7.0f / 16);
                    newImg.setRGB(x + 1, y, neighbor);
//                    oldPixels[y*maxX + x+1] = neighbor;
                }

                if (x > 0 && y < maxY - 1) {
                    neighbor = newImg.getRGB(x - 1, y + 1);
                    neighbor = handlePixel(neighbor, 3.0f / 16);
                    newImg.setRGB(x - 1, y + 1, neighbor);
//                    oldPixels[(y+1)*maxX + x-1] = neighbor;
                }
                if (y < maxY - 1) {
                    neighbor = newImg.getRGB(x, y + 1);
                    neighbor = handlePixel(neighbor, 5.0f / 16);
                    newImg.setRGB(x, y + 1, neighbor);
//                    oldPixels[(y+1)*maxX + x] = neighbor;
                }

                if (x < maxX - 1 && y < maxY - 1) {
                    neighbor = newImg.getRGB(x + 1, y + 1);
                    neighbor = handlePixel(neighbor, 1.0f / 16);
                    newImg.setRGB(x + 1, y + 1, neighbor);
//                    oldPixels[(y+1)*maxX + x+1] = neighbor;
                }
            }
        }
        return newImg;
    }

    private void updateQuants(){
        if(redNum != Config.getRedFsNum()){
            redNum = Config.getRedFsNum();
            redQuants = setQuants(redNum);
        }
        if(greenNum != Config.getGreenFsNum()){
            greenNum = Config.getGreenFsNum();
            greenQuants = setQuants(greenNum);
        }
        if(blueNum != Config.getBlueFsNum()){
            blueNum = Config.getBlueFsNum();
            blueQuants = setQuants(blueNum);
        }
        Logger.getInstance().info("red quants: " + Arrays.toString(redQuants));
        Logger.getInstance().info("green quants: " + Arrays.toString(greenQuants));
        Logger.getInstance().info("blue quants: " + Arrays.toString(blueQuants));
    }

    private int[] setQuants(int n){
        arr = new int[n];
        int segment = (MAX + 1) / (n - 1);
        for(i=0; i<n-1; i++){
            arr[i] = i* segment;
        }
        arr[n-1] = 255;
        return arr;
    }

    private int handlePixel(int pixel, float k){
        r = (int) (getR(pixel) + er*k);
        g = (int) (getG(pixel) + eg*k);
        b = (int) (getB(pixel) + eb*k);

        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));
        return getPixel(r,g,b);
    }

    private int getNearestColor(int oldPixel){
        int oldR = getR(oldPixel);
        int oldG = getG(oldPixel);
        int oldB = getB(oldPixel);

        r = getNearestQuant(redNum, redQuants, oldR);
        g = getNearestQuant(greenNum, greenQuants, oldG);
        b = getNearestQuant(blueNum, blueQuants, oldB);

        er = oldR - r;
        eg = oldG - g;
        eb = oldB - b;
        return getPixel(r,g,b);
    }

    private int getNearestQuant(int num, int[] quants, int z){
        int distance = MAX;
        int k = num - 1;
        for(i=0; i<num; i++){
            if(Math.abs(quants[i] - z) < distance){
                distance = Math.abs(quants[i] - z);
                k = i;
            }
        }
        return quants[k];
    }
}
