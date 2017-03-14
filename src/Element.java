public class Element {
    private int x;
    private int y;
    private boolean state = false;
    private int livingAround = 0;

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getLivingAround() {
        return livingAround;
    }

    public void addLiving() {
        this.livingAround++;
    }

    public void subtractLiving() {
        this.livingAround--;
        if (this.livingAround < 0) {
            livingAround = 0;
        }
    }
}
