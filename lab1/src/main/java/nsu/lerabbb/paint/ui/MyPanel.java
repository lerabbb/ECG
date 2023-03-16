package nsu.lerabbb.paint.ui;

import lombok.Getter;
import nsu.lerabbb.paint.Logger;
import nsu.lerabbb.paint.logic.*;
import nsu.lerabbb.paint.logic.algorithms.BresenhamDrawingLine;
import nsu.lerabbb.paint.logic.algorithms.DrawingLineStrategy;
import nsu.lerabbb.paint.logic.algorithms.FillingStrategy;
import nsu.lerabbb.paint.logic.algorithms.SpanFilling;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyPanel extends JPanel implements MouseListener {
    private int startX, startY;

    @Getter
    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;
    private final DrawingLineStrategy dls;
    private final FillingStrategy fs;

    public MyPanel(){
        super();
        setPreferredSize(new Dimension(640, 480));
        addMouseListener(this);

        bufferedImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect (0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        dls = new BresenhamDrawingLine(bufferedImage);
        fs = new SpanFilling(bufferedImage);
        Logger.getInstance().info("panel created");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (PaintConfig.getInstance().getMode()) {
            case PaintConfig.LINE_MODE -> drawLine(startX, startY, e.getX(), e.getY());
            case PaintConfig.FILL_MODE -> fs.fill(startX, startY);
            case PaintConfig.POLYGON_MODE -> dls.drawPolygon(startX, startY);
            case PaintConfig.STAR_MODE -> dls.drawStar(startX, startY);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (PaintConfig.getInstance().getMode()) {
            case PaintConfig.LINE_MODE -> drawLine(e.getX(), e.getY(), e.getX(), e.getY());
            case PaintConfig.FILL_MODE ->  fs.fill(e.getX(), e.getY());
            case PaintConfig.POLYGON_MODE -> dls.drawPolygon(e.getX(), e.getY());
            case PaintConfig.STAR_MODE -> dls.drawStar(e.getX(), e.getY());
        }
        repaint();
    }

    public void addFileToWorkSpace(File file) throws IOException {
        BufferedImage newImage = ImageIO.read(file);
        graphics2D = newImage.createGraphics();
        graphics2D.drawImage(newImage, 0, 0, null);
        updateBuffer(newImage);
        PaintConfig.getInstance().setSpaceHeight(newImage.getHeight());
        PaintConfig.getInstance().setSpaceHeight(newImage.getWidth());
        Logger.getInstance().info("opened file " + file.getName());
        repaint();
    }

    public void resizeWorkSpace(int width, int height){
        BufferedImage resizedImage = new BufferedImage(width, height, bufferedImage.getType());
        graphics2D.dispose();
        graphics2D = resizedImage.createGraphics();
        graphics2D.fillRect (0, 0, width, height);
        graphics2D.drawImage(bufferedImage, 0, 0, null);
        updateBuffer(resizedImage);
        Logger.getInstance().info("image resized to " + width + "x" + height);
        repaint();
    }

    public void clearWorkSpace(){
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0,0, bufferedImage.getWidth(), bufferedImage.getHeight());
        Logger.getInstance().info("image cleared ");
        repaint();
    }

    private void drawLine(int startX, int startY, int endX, int endY){
        Logger.getInstance().info("started drawing line");

        startX = checkBounds(startX, bufferedImage.getWidth());
        startY = checkBounds(startY, bufferedImage.getHeight());
        endX = checkBounds(endX, bufferedImage.getWidth());
        endY = checkBounds(endY, bufferedImage.getHeight());

        if (PaintConfig.getInstance().getStroke() == 1) {
            dls.drawLine(startX, startY, endX, endY);
        } else {
            graphics2D.setColor(PaintConfig.getInstance().getColor());
            graphics2D.setStroke(new BasicStroke(PaintConfig.getInstance().getStroke()));
            graphics2D.drawLine(startX, startY, endX, endY);
        }

        Logger.getInstance().info("ended drawing line");
    }

    private int checkBounds(int val, int max){
        if(val >= max){
            return max;
        } else if(val < 0){
            return 0;
        }
        return val;
    }

    private void updateBuffer(BufferedImage newImage){
        bufferedImage = newImage;
        dls.setBuffer(bufferedImage);
        fs.setBuffer(newImage);
        setPreferredSize(new Dimension(newImage.getWidth(), newImage.getHeight()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}