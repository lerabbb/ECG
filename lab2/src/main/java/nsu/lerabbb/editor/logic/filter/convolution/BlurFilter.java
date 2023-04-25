package nsu.lerabbb.editor.logic.filter.convolution;

import lombok.Getter;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

import static nsu.lerabbb.editor.logic.Utils.*;

public class BlurFilter implements Filter {
    private static final float PI = 3.14f;
    @Getter
    private float prevSigma;
    @Getter
    private int prevN;
    private float sum;
    private float[] weights;


    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Blur filter");
        int maxX = img.getWidth();
        int maxY = img.getHeight();
        BufferedImage newImg = new BufferedImage(maxX, maxY, img.getType());
        int n = Config.getBlurSize();
        int x, y, i, j, k, pixel, r, g, b;

        updateWeights(Config.getGaussSigma(), n);
        for (y = 0; y < maxY; y++) {
            for (x = 0; x < maxX; x++) {
                r = g = b = 0;
                for (i = -n/2; i <= n/2; i++) {
                    for (j = -n/2; j <= n/2; j++) {
                        pixel = img.getRGB(
                                checkBounds(x + j, 0, maxX),
                                checkBounds(y + i, 0, maxY)
                        );
                        k = (n / 2 + i) * n + n / 2 + j;

                        r += getR(pixel) * weights[k];
                        g += getG(pixel) * weights[k];
                        b += getB(pixel) * weights[k];
                    }
                }
                r /= sum;
                g /= sum;
                b /= sum;
                pixel = getPixel(r, g, b);
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }

    private void updateWeights(float sigma, int n) {
        if(sigma == prevSigma && n == prevN){
            return;
        }
        prevN = n;
        prevSigma = sigma;
        weights = new float[n * n];
        sum = 0;
        if(n>=7){
            averagingBlur(n);
        } else{
            gaussianBlur(n, sigma);
        }
    }

    private void gaussianBlur(int n, float sigma) {
        float sigmaSquare = 2 * sigma * sigma;
        float divider = (float) Math.sqrt(sigmaSquare * PI);
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                weights[y * n + x] = (float) Math.exp(-(x*x + y*y) / sigmaSquare) / divider;
                sum += weights[y * n + x];
            }
        }
    }

    private void averagingBlur(int n){
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                weights[y * n + x] = 1;
            }
        }
        sum = n*n;
    }
}
