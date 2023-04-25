package nsu.lerabbb.editor.logic.filter.convolution;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

import static nsu.lerabbb.editor.logic.Utils.*;

public class EmbossFilter implements Filter {
    private final int N = 3;
    private final int[] weights;

    public EmbossFilter(){
        weights = new int[]{0, 1, 0,
                            1, 0, -1,
                            0, -1, 0};
    }

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Emboss filter");
        int maxX = img.getWidth();
        int maxY = img.getHeight();
        BufferedImage newImg = new BufferedImage(maxX, maxY, img.getType());
        int pixel, r, g, b, x, y, i, j, k;

        for (y = 0; y < maxY; y++) {
            for (x = 0; x < maxX; x++) {
                r = b = g = 0;
                for (i = -N / 2; i <= N / 2; i++) {
                    for (j = -N / 2; j <= N / 2; j++) {
                        pixel = img.getRGB(
                                checkBounds(x+j, 0, maxX),
                                checkBounds(y+i, 0, maxY)
                        );
                        k = (N / 2 + i) * N + N / 2 + j;

                        r += getR(pixel) * weights[k];
                        g += getG(pixel) * weights[k];
                        b += getB(pixel) * weights[k];
                    }
                }
                r += 128;
                g += 128;
                b += 128;

                r = remainder(r, 255);
                g = remainder(g, 255);
                b = remainder(b, 255);
                pixel = getPixel(r,g,b);
                newImg.setRGB(x, y, pixel);
            }
        }

        return newImg;
    }
}
