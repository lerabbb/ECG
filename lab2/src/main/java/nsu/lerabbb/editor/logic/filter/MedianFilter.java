package nsu.lerabbb.editor.logic.filter;


import java.awt.image.BufferedImage;
import java.util.Arrays;

import static nsu.lerabbb.editor.logic.Utils.checkBounds;

public class MedianFilter implements Filter{
    private int n = 5;
    private int[] redArray;
    private int[] greenArray;
    private int[] blueArray;

    public MedianFilter(){
        redArray = new int[n*n];
        greenArray = new int[n*n];
        blueArray = new int[n*n];
    }

    @Override
    public BufferedImage edit(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int y, x, i, j, k, pixel, a = 0;
        int maxX = img.getWidth();
        int maxY = img.getHeight();

        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                for (i = -n / 2; i <= n / 2; i++) {
                    for (j = -n / 2; j <= n / 2; j++) {
                        pixel = img.getRGB(
                                checkBounds(x + j, 0, maxX),
                                checkBounds(y + i, 0, maxY)
                        );
                        k = (n / 2 + i) * n + n / 2 + j;
                        a = (pixel >> 24) & 0xff;
                        redArray[k] = (pixel >> 16) & 0xff;
                        greenArray[k] = (pixel >> 8) & 0xff;
                        blueArray[k] = (pixel) & 0xff;
                    }
                }
                Arrays.sort(redArray);
                Arrays.sort(greenArray);
                Arrays.sort(blueArray);
                pixel = (a << 24) | (redArray[13] << 16) | (greenArray[13] << 8) | blueArray[13];
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }
}
