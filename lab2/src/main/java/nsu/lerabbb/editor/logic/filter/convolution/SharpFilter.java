package nsu.lerabbb.editor.logic.filter.convolution;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

import static nsu.lerabbb.editor.logic.Utils.*;

public class SharpFilter implements Filter {
    private final int n = 3;
    private final int[] weights;

    public SharpFilter(){
        weights = new int[]{0, -1, 0,
                            -1, 5, -1,
                            0, -1, 0};
    }

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Sharp filter");
        int maxX = img.getWidth();
        int maxY = img.getHeight();
        BufferedImage newImg = new BufferedImage(maxX, maxY, img.getType());
        int x, y, i, j, pixel, k,r,g,b;

        for (y = 0; y < maxY; y++) {
            for (x = 0; x < maxX; x++) {
                r=g=b=0;
                for (i = -n / 2; i <= n / 2; i++) {
                    for (j = -n / 2; j <= n / 2; j++) {
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

                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);
                pixel = getPixel(r, g, b);
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }
}
