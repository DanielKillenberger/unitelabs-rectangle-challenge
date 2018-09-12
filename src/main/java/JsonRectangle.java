public class JsonRectangle {
    int x, y, w, h;
    private boolean xSet;
    private boolean ySet;
    private boolean wSet;
    private boolean hSet;

    boolean isXSet() {
        return xSet;
    }

    boolean isYSet() {
        return ySet;
    }

    boolean isWSet() {
        return wSet;
    }

    boolean isHSet() {
        return hSet;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        xSet = true;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        ySet = true;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
        wSet = true;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
        hSet = true;
    }
}