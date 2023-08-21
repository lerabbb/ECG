package nsu.lerabbb.wireframe.logic.algorithms;

import lombok.Getter;
import nsu.lerabbb.wireframe.Logger;
import nsu.lerabbb.wireframe.logic.Config;
import nsu.lerabbb.wireframe.logic.utils.MatrixOp;
import nsu.lerabbb.wireframe.logic.utils.Vector3;


public class WireframeModel {
    private final double zoomCoeff = 5.5;
    private final double[] camMatr;

    private BSpline spline;
    private int N, K, M, M1, num;
    private int Sw;
    private int Sh;
    private double zn;
    private double tetaX, tetaY, tetaZ;
    private double kx, ky;
    @Getter
    private double[][][] curveCoords =null;
    @Getter
    private double[][][] circleCoords =null;
    private double maxCoord=-100;


    public WireframeModel(int width, int height){
        this.Sw = width;
        this.Sh = height;
        this.zn = 5;
        this.tetaX = tetaY = tetaZ = 0;
        this.camMatr = getCamMatrix();
        kx = ky = 10;
    }

    public void makeWireframe() {
        K = Config.getK();
        N = Config.getN();
        M = Config.getM();
        M1 = Config.getM1();
        spline = Config.getBSpline();
        num = N * (K - 3) + 1;
        curveCoords = new double[M][num][4];
        circleCoords = new double[K-2][M1*M][4];

//        double[] shiftMatr = MatrixOp.getShiftMatrix(0,0,-spline.getMeanX());
//        double[] rotateMatr = MatrixOp.matrixMul(MatrixOp.getRotateXMatrix(tetaX), MatrixOp.getRotateYMatrix(tetaY), 4);
//        rotateMatr = MatrixOp.matrixMul(MatrixOp.getRotateZMatrix(tetaZ), rotateMatr, 4);
//        double[] affineMatr = MatrixOp.matrixMul(rotateMatr, shiftMatr, 4);
//        shiftMatr = MatrixOp.getShiftMatrix(0,0,spline.getMeanX());
//        affineMatr = MatrixOp.matrixMul(shiftMatr, affineMatr, 4);
//        double[] tempMatr = MatrixOp.matrixMul(camMatr, affineMatr, 4);
//        double[] viewPortMatr = MatrixOp.getViewPortMatr(2,2,9);
//        double[] transformMatr = MatrixOp.matrixMul(viewPortMatr, tempMatr, 4);
//        viewPortMatr = MatrixOp.getViewPortMatr(Sw,Sh,zn);
//        transformMatr = MatrixOp.matrixMul(viewPortMatr, transformMatr, 4);

//        double[] transformMatr = getTransformMatr();

        Logger.getInstance().info("matrix multiplied");

        double w;
        double sx = Sw*1.0/2;
        double sy = Sh*1.0/2;
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < num; i++) {
                curveCoords[j][i] = evaluateCurvePoint(j, i, M);
//                curveCoords[j][i] = MatrixOp.matrixVectorMul(transformMatr, curveCoords[j][i], 4);
                if (curveCoords[j][i][3] != 0) {
                    w = Math.abs(curveCoords[j][i][3]);
                    curveCoords[j][i][0] /= w;
                    curveCoords[j][i][1] /= w;
                }
            }
        }
        Logger.getInstance().info("curve made");
        int k;
        for (int i = 0; i < num; i+=N) {
            for (int j = 0; j < M1*M; j++) {
                k = i/N;
                circleCoords[k][j] = evaluateCurvePoint(j, i, M1*M);
//                circleCoords[k][j] = MatrixOp.matrixVectorMul(transformMatr, circleCoords[k][j], 4);
                if (circleCoords[k][j][3] != 0) {
                    w = Math.abs(circleCoords[k][j][3]);
                    circleCoords[k][j][0] /= w;
                    circleCoords[k][j][1] /= w;
                }
            }
        }
        Logger.getInstance().info("circles made");
    }

    public void makeAnotherWireframe(){
        spline = Config.getBSpline();
        K = Config.getK();
        N = Config.getN();
        M = Config.getM();
        M1 = Config.getM1();

        num = N * (K - 3) + 1;
        curveCoords = new double[M][num][4];
        circleCoords = new double[K-2][M1*M][4];

//        double[] transformMatr = getTransformMatr();
        double w;

        for(int j=0; j<M; j++){
            for(int i =0; i<num; i++){
                curveCoords[j][i] = evaluateCurvePoint(j, i, M);
//                curveCoords[j][i] = MatrixOp.matrixVectorMul(transformMatr, curveCoords[j][i], 4);
                if(curveCoords[j][i][3]!=0){
                    w = Math.abs(curveCoords[j][i][3]);
                    curveCoords[j][i][0] /= w;
                    curveCoords[j][i][1] /= w;
                    curveCoords[j][i][2] /= w;
                }
            }
        }
        int k;
        for(int j=0; j<num; j++){
            for(int i =0; i<M1*M; i++){
                k = j/N;
                circleCoords[k][i] = evaluateCurvePoint(i, j, M);
//                circleCoords[k][i] = MatrixOp.matrixVectorMul(transformMatr, circleCoords[k][i], 4);
                if(circleCoords[k][i][3]!=0){
                    w = Math.abs(circleCoords[j][i][3]);
                    circleCoords[k][i][0] /= w;
                    circleCoords[k][i][1] /= w;
                    circleCoords[k][i][2] /= w;
                }
            }
        }
    }

//    private double[] getTransformMatr(){
//        double[] resMatr = MatrixOp.matrixMul(
//                MatrixOp.getRotateXMatrix(tetaX),
//                MatrixOp.getShiftMatrix(0,0,-spline.getMeanX()),
//                4
//                );
//        resMatr = MatrixOp.matrixMul(
//                MatrixOp.getRotateYMatrix(tetaY),
//                resMatr,
//                4
//        );
//
//        resMatr = MatrixOp.matrixMul(
//                MatrixOp.getRotateZMatrix(tetaZ),
//                resMatr,
//                4
//        );
//
//        resMatr = MatrixOp.matrixMul(camMatr, resMatr,4);
//        resMatr = MatrixOp.matrixMul(
//                MatrixOp.getViewPortMatr(2, 2, 9),
//                resMatr,
//                4
//        );
//        resMatr = MatrixOp.matrixMul(
//                MatrixOp.getViewPortMatr(Sw, Sh, zn),
//                resMatr,
//                4
//        );
//        return resMatr;
//    }


    public void changeZoom(int zoom){
        zn += zoom * zoomCoeff;
    }
    public void changeRotation(int dx, int dy){
        tetaX += 0.75*dy;
        tetaY -= 0.75*dx;
        tetaZ -= 0.5*dx;
    }
    public void changeScale(int newWidth, int newHeight) {
        kx = kx * newWidth / Sw;
        ky = ky * newHeight / Sh;
        Sw = newWidth;
        Sh = newHeight;
    }
    public void resetRotation(){
        tetaX = tetaY = 0;
    }
    public void resetScale(){
        kx = ky = 10;
    }
    public void resetZoom(){
        zn = 1;
    }

    private double[] evaluateCurvePoint(int j, int i, int cnt){
        double[] res = new double[4];
        double fi = j * 2.0 * Math.PI / cnt;
        res[0] = spline.getCurvePoint(i).getY() * Math.cos(fi);
        res[1] = spline.getCurvePoint(i).getY() * Math.sin(fi);
        res[2] = spline.getCurvePoint(i).getX();
        res[3] = 1;
        return res;
    }

    private double[] getCamMatrix(){
        Vector3 i,j,k;
        Vector3 eye = new Vector3(-10, 0, 0);
        Vector3 lookAt = new Vector3(10, 0, 0);
        Vector3 up = new Vector3(0,-1,0);

        k = eye.sub(lookAt);
        k.normalize();
        i = up.crossProd(k);
        i.normalize();
        j = k.crossProd(i);
        j.normalize();

        double[] matr1 = new double[]{
                i.getX(), i.getY(), i.getZ(), 0,
                j.getX(), j.getY(), j.getZ(), 0,
                k.getX(), k.getY(), k.getZ(), 0,
                0, 0, 0, 1
        };
        double[] matr2 = new double[]{
                1, 0, 0, -eye.getX(),
                0, 1, 0, -eye.getY(),
                0, 0, 1, -eye.getZ(),
                0, 0, 0, 1
        };

//        return MatrixOp.matrixMul(matr2, matr1, 4);

        return new double[]{
                1,0,0,0,
                0,1,0,0,
                0,0,1,10,
                0,0,0,1
        };

    }


    private void fitToCube(){
        int i, j;
        for(j=0; j< M; j++){
            for(i=0; i<num; i++){
                curveCoords[j][i][0] /= maxCoord;
                curveCoords[j][i][1] /= maxCoord;
                curveCoords[j][i][2] /= maxCoord;
                curveCoords[j][i][3] /= maxCoord;
            }
        }
        for(j=0; j< K-2; j++){
            for(i=0; i<M1; i++){
                circleCoords[j][i][0] /= maxCoord;
                circleCoords[j][i][1] /= maxCoord;
                circleCoords[j][i][2] /= maxCoord;
                circleCoords[j][i][3] /= maxCoord;
            }
        }
    }
}
