package nsu.lerabbb.editor.logic.filter.pixel;

import lombok.Getter;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

public class GammaCorrection implements Filter {
    @Getter
    private float gamma=-1;

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Gamma correction");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int y, x, pixel, a, r, g, b;

        updateGamma();
        float gammaCor = 1/gamma;
        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                pixel = img.getRGB(x, y);
                a = (pixel >> 24) & 0xff;
                r = (pixel >> 16) & 0xff;
                g = (pixel >> 8) & 0xff;
                b = pixel & 0xff;

                r = (int) (255 * Math.pow(r / 255.0, gammaCor));
                g = (int) (255 * Math.pow(g / 255.0, gammaCor));
                b = (int) (255 * Math.pow(b / 255.0, gammaCor));

                pixel = (a << 24) | (r << 16) | (g << 8) | b;
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }

    private void updateGamma(){
        if(gamma == Config.getGammaCorrection()){
            return;
        }
        gamma = Config.getGammaCorrection();
    }
}
