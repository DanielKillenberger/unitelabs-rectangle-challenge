public class JsonRectangle {
    int x, y, w, h;
    boolean xSet;
    boolean ySet;
    boolean wSet;
    boolean hSet;

    public boolean isXSet() {
        return xSet;
    }

    public boolean isYSet() {
        return ySet;
    }

    public boolean isWSet() {
        return wSet;
    }

    public boolean isHSet() {
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