package nsu.lerabbb.editor.logic.filter.pixel;

import lombok.Getter;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

import static nsu.lerabbb.editor.logic.Utils.*;

public class ColorSplash implements Filter {
    private final int bounds=80;
    @Getter
    private int selected;

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Color splash filter");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int pixel, r,g,b;
        selected = Config.getColorSelection().getRGB();
        int sr = getR(selected);
        int sg = getG(selected);
        int sb = getB(selected);

        for(int y=0; y<img.getHeight(); y++){
            for(int x=0; x<img.getWidth(); x++){
                pixel = img.getRGB(x, y);

                r = getR(pixel);
                g = getG(pixel);
                b = getB(pixel);

                if(Math.abs(sr - r) > bounds || Math.abs(sg - g) > bounds || Math.abs(sb - b) > bounds) {
                //if(r < (b*1.4) || r < (g * 1.4)){
                    pixel = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    pixel = getPixel(pixel, pixel, pixel);
                }
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }
}
