package nsu.lerabbb.wireframe.logic;

import nsu.lerabbb.wireframe.Logger;
import nsu.lerabbb.wireframe.logic.algorithms.BSpline;

public class Config {
    private static Config config;
    private int k;
    private int n;
    private int m;
    private int m1;
    private BSpline bSpline = null;


    public static Config getInstance(){
        if(config == null){
            config = new Config();
        }
        return config;
    }

    private Config(){
        k = 4;
        n = 1;
        m = 2;
        m1 = 1;
    }

    public static void setBSpline(BSpline spline){
        getInstance().bSpline = spline;
        Logger.getInstance().info("is spline null" + (spline == null));
    }
    public static BSpline getBSpline(){
        if(getInstance().bSpline == null){
            return new BSpline();
        }
        return getInstance().bSpline;
    }

    public static void setK(int k){
        getInstance().k = k;
    }
    public static int getK(){
        return getInstance().k;
    }

    public static void setN(int n){
        getInstance().n = n;
    }
    public static int getN(){
        return getInstance().n;
    }

    public static void setM(int m){
        getInstance().m = m;
    }
    public static int getM(){
        return getInstance().m;
    }
    public static void setM1(int m1){
        getInstance().m1 = m1;
    }
    public static int getM1(){
        return getInstance().m1;
    }
}
