package nsu.lerabbb.editor.logic.filter.dithering;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static nsu.lerabbb.editor.logic.Utils.*;

public class OrderedDithering implements Filter {
    private final int MAX = 255;
    private int N;
    private int[] matr;

    private int redNum;
    private int greenNum;
    private int blueNum;
    private int[] redQuants;
    private int[] greenQuants;
    private int[] blueQuants;
    private int i;

    public OrderedDithering(){
        redNum = -1;
        greenNum = -1;
        blueNum = -1;
        N = 8;
    }

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Ordered dithering");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int x, y, i, j, pixel, dr, dg, db,r,g,b;

        updateParams();
        for (y = 0; y < newImg.getHeight(); y++) {
            for (x = 0; x < newImg.getWidth(); x++) {
                pixel = img.getRGB(x,y);
                i = x % N;
                j = y % N;

                dr = (int) ((matr[j * N + i]*1.0/(N*N) - 1.0/2) * 255.0/(redNum-1));
                dg = (int) ((matr[j * N + i]*1.0/(N*N) - 1.0/2) * 255.0/(greenNum-1));
                db = (int) ((matr[j * N + i]*1.0/(N*N) - 1.0/2) * 255.0/(blueNum-1));

//                dr = 255*(2*matr[j*N + i] - N*N) / (2*N*N*(redNum-1));
//                dg = 255*(2*matr[j*N + i] - N*N) / (2*N*N*(greenNum-1));
//                db = 255*(2*matr[j*N + i] - N*N) / (2*N*N*(blueNum-1));

                r = getNearestQuant(redNum, redQuants, getR(pixel) + dr);
                g = getNearestQuant(greenNum, greenQuants, getG(pixel) + dg);
                b = getNearestQuant(blueNum, blueQuants, getB(pixel) + db);
                pixel = getPixel(r,g,b);
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }

    private void updateParams(){
        if(redNum == Config.getRedOdNum() && greenNum == Config.getGreenOdNum() && blueNum == Config.getBlueOdNum()){
            return;
        }
        if(redNum != Config.getRedOdNum()){
            redNum = Config.getRedOdNum();
            redQuants = setQuants(redNum);
        }
        if(greenNum != Config.getGreenOdNum()){
            greenNum = Config.getGreenOdNum();
            greenQuants = setQuants(greenNum);
        }
        if(blueNum != Config.getBlueOdNum()){
            blueNum = Config.getBlueOdNum();
            blueQuants = setQuants(blueNum);
        }
        if(inBounds(2, 8)) {
            N = 16;
        } else if(inBounds(8, 16)) {
            N = 8;
        } else if(inBounds(16, 64)) {
            N = 4;
        } else if(inBounds(64, 128)) {
            N = 2;
        }
        matr = initMatr(N);
        Logger.getInstance().info("matrix Bayer = " + Arrays.toString(matr));
    }

    private int[] setQuants(int n){
        int[] arr = new int[n];
        int segment = (MAX + 1) / (n - 1);
        for(i=0; i<n-1; i++){
            arr[i] = i*segment;
        }
        arr[n-1] = 255;
        return arr;
    }

    private boolean inBounds(int start, int end){
        return redNum >= start && redNum <= end &&
                greenNum >= start && greenNum <= end &&
                blueNum >= start && blueNum <= end;
    }

    private int[] initMatr(int n) {
        if (n == 2) {
            return new int[]{0, 2, 3, 1};
        }
        int[] D = initMatr(n / 2);
        int[] matr = new int[n * n];
        int i, j;
        for (i = 0; i < n / 2; i++) {
            for (j = 0; j < n / 2; j++) {
                matr[i * n + j] = 4 * D[i * n / 2 + j];
            }
            for (j = n / 2; j < n; j++) {
                matr[i * n + j] = 4 * D[i * n / 2 + j % (n / 2)] + 2;
            }
        }
        for (i = n / 2; i < n; i++) {
            for (j = 0; j < n / 2; j++) {
                matr[i * n + j] = 4 * D[(i % (n / 2)) * n / 2 + j] + 3;
            }
            for (j = n / 2; j < n; j++) {
                matr[i * n + j] = 4 * D[(i % (n / 2)) * n / 2 + j % (n / 2)] + 1;
            }
        }
        return matr;
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
