package nsu.lerabbb.editor.logic;

public class Utils {
    public static final int BLUR_SIZE_MIN = 3;
    public static final int BLUR_SIZE_MAX = 11;
    public static final float SIGMA_MIN = 0.5f;
    public static final float SIGMA_MAX = 10;
    public static final int FSD_QUANT_MIN = 2;
    public static final int FSD_QUANT_MAX = 128;
    public static final float GAMMA_MIN = 0.1f;
    public static final float GAMMA_MAX = 10;
    public static final int OD_QUANT_MIN = 2;
    public static final int OD_QUANT_MAX = 128;
    public static final int ROBERTS_MIN = 0;
    public static final int ROBERTS_MAX = 255;
    public static final int SOBEL_MIN = 0;
    public static final int SOBEL_MAX = 255;
    public static final int ROTATE_MIN = 0;
    public static final int ROTATE_MAX = 360;

    public static int checkBounds(int a, int min, int max){
        if(a < min){
            return min + Math.abs(a) - 1;
        } else if(a >= max){
            return max - a%max - 1;
        }
        return a;
    }

    public static int remainder(int a, int n) {
        if (a >= 0) {
            return a % n;
        } else {
            return Math.abs(a) % n + n;
        }
    }

    public static int getR(int pixel){
        return (pixel >> 16) & 0xff;
    }
    public static int getG(int pixel){
        return (pixel >> 8) & 0xff;
    }
    public static int getB(int pixel){
        return (pixel) & 0xff;
    }
    public static int getPixel(int r, int g, int b){
        return (r << 16) | (g << 8) | b;
    }

    public static float[] getHSV(int pixel) {
        float r = Utils.getR(pixel) / 255f;
        float g = Utils.getG(pixel) / 255f;
        float b = Utils.getB(pixel) / 255f;
        float Cmax = Math.max(r, Math.max(g, b));
        float Cmin = Math.min(r, Math.min(g, b));
        float delta = Cmax - Cmin;
        float[] hsv = new float[3];

        if (delta == 0) {
            hsv[0] = 0;
        } else if (Cmax == r) {
            hsv[0] = (60 * ((g - b) / delta) + 360) % 360;
        } else if (Cmax == g) {
            hsv[0] =  (60 * ((b - r) / delta) + 120) % 360;
        } else if (Cmax == b) {
            hsv[0] = 60 * (60 * ((r - g) / delta) + 240) % 360;
        }

        if (Cmax == 0) {
            hsv[1] = 0;
        } else {
            hsv[1] = delta / Cmax * 100;
        }

        hsv[2] = Cmax * 100;
        return hsv;
    }
}
