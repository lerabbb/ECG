package nsu.lerabbb.wireframe.gui;

import nsu.lerabbb.wireframe.Logger;
import nsu.lerabbb.wireframe.logic.Config;
import nsu.lerabbb.wireframe.logic.algorithms.BresenhamLine;
import nsu.lerabbb.wireframe.logic.algorithms.DrawingLineStrategy;
import nsu.lerabbb.wireframe.logic.algorithms.Wireframe;
import nsu.lerabbb.wireframe.logic.algorithms.WireframeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private final BufferedImage image;
    private final Graphics2D graphics2D;
    private final DrawingLineStrategy ds;
    private final WireframeModel wireframeModel;
    private final Wireframe wireframe;
    private int lastX, lastY;
    private int width, height;
    private final Color BG_COLOR = Color.WHITE;
    private final Color LINE_COLOR = Color.BLACK;

    public MainPanel(){
        super();
        setPreferredSize(new Dimension(640, 480));
        addMouseListener(this);

        width = 640;
        height = 480;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = image.createGraphics();
        graphics2D.setBackground(BG_COLOR);
        graphics2D.clearRect(0, 0, width, height);
        ds = new BresenhamLine(image);
        wireframeModel = new WireframeModel(width, height);
        wireframe = new Wireframe(image);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void updateOld(){
        graphics2D.setBackground(BG_COLOR);
        graphics2D.clearRect(0,0, image.getWidth(), image.getHeight());
        wireframeModel.makeWireframe();
        int M = Config.getM();
        int M1 = Config.getM1();
        int N = Config.getN();
        int K = Config.getK();
        int num = (N * (K - 3) + 1);
        int dx = width/2;
        int dy = height/2;
        int i, j;
        double[][][] coords = wireframeModel.getCurveCoords();
        graphics2D.setColor(LINE_COLOR);
        for(i=0; i< M; i++){
            for(j=0; j<num-1; j++){
                ds.drawLine(
                        (int) (coords[i][j][0]+ dx), (int) (coords[i][j][1] + dy),
                        (int) (coords[i][j+1][0]+dx), (int) (coords[i][j+1][1] + dy),
                        LINE_COLOR
                );
            }
        }
        Logger.getInstance().info("curves drawn");
        coords = wireframeModel.getCircleCoords();
        for(i=0; i < K-2; i++){
            for( j=0; j<M1*M-1; j++){
                ds.drawLine(
                        (int) (coords[i][j][0] + dx), (int) (coords[i][j][1] + dy),
                        (int) (coords[i][j+1][0] + dx), (int) (coords[i][j+1][1] + dy),
                        LINE_COLOR
                );
            }
            ds.drawLine(
                    (int) (coords[i][j][0] + dx), (int) (coords[i][j][1] + dy),
                    (int) (coords[i][0][0] + dx), (int) (coords[i][0][1] + dy),
                    LINE_COLOR
            );
        }
        Logger.getInstance().info("circles drawn");
        repaint();
    }

    public void update(){
        graphics2D.setBackground(BG_COLOR);
        graphics2D.clearRect(0,0, image.getWidth(), image.getHeight());
        wireframe.drawModel();
        repaint();
    }

    public void resetParams(){
        wireframeModel.resetRotation();
        wireframeModel.resetScale();
        wireframeModel.resetZoom();
        wireframe.resetRotation();
        wireframe.resetZoom();
        update();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(image == null){
            return;
        }
        int dx = (e.getX() - lastX);
        int dy = (e.getY() - lastY);
        lastX = e.getX();
        lastY = e.getY();
        wireframeModel.changeRotation(dx, dy);
        wireframe.changeRotation(dx, dy);
        update();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (image == null) {
            return;
        }
        wireframeModel.changeZoom(e.getWheelRotation());
        wireframe.changeZoom(e.getWheelRotation());
        update();
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}