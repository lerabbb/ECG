package nsu.lerabbb.editor.logic.filter;

import lombok.Setter;
import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Interpolation implements Filter{
    @Setter
    private Dimension oldSize;

    public Interpolation(){
        oldSize = new Dimension();
    }
    @Override
    public BufferedImage edit(BufferedImage img) {
        oldSize.height = img.getHeight();
        oldSize.width = img.getWidth();
        int newHeight = Config.getPanelSize().height;
        double k = (double) newHeight / oldSize.height;
        int newWidth = (int) (oldSize.width * k);

        Logger.getInstance().info("old size = " + oldSize.width + "x" + oldSize.height);
        Logger.getInstance().info("new size = " + newWidth + "x" + newHeight);

        BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
        Graphics2D g = newImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, Config.getInterpMode());

//        AffineTransform at = new AffineTransform();
//        at.scale(k,k);
        g.drawImage(img, 0, 0, newWidth, newHeight, null);
//        g.dispose();
//        AffineTransformOp op = new AffineTransformOp(at, Config.getInterpMode());
//        newImg = op.filter(img, newImg);
        Logger.getInstance().info("actual size = " + newImg.getWidth() + "x" + newImg.getHeight());
        return newImg;
    }
}
