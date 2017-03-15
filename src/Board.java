import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    private int height;
    private int width;
    private boolean prepared = false;

    private int iteration = 0;

    private Element[][] board;
    private List<Element> elementList;

    public Board(int height, int width) {
        // Minimum Height and Width = 3
        if (height < 3) height = 3;
        if (width < 3) width = 3;

        this.height = height + 2;
        this.width = width + 2;

        this.board = new Element[width + 2][height + 2];

        this.initialFill();

        this.elementList = new ArrayList<>();
    }

    private Element element(int x, int y) {
        return board[x][y];
    }

    private void initialFill() {
        for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
                board[x][y] = new Element(x, y);
            }
        }

        // One-element border that mirrors the other side
        // x = 0
        for (int y = 1; y < this.width - 1; y++) {
            board[0][y] = element(this.width - 2, y);
        }

        // x = this.width - 1
        for (int y = 1; y < this.width - 1; y++) {
            board[this.width - 1][y] = element(1, y);
        }

        // y = 0
        for (int x = 1; x < this.height - 1; x++) {
            board[x][0] = element(x, this.height - 2);
        }

        // x = this.width - 1
        for (int x = 1; x < this.height - 1; x++) {
            board[x][this.height - 1] = element(x, 1);
        }

        // Corner cases
        board[0][0] = element(this.width - 2, this.height - 2);
        board[this.width - 1][0] = element(1, this.height - 2);
        board[0][this.height - 1] = element(this.width - 2, 1);
        board[this.width - 1][this.height - 1] = element(1, 1);
    }

    public void fill(int x, int y) {
        element(x, y).setState(true);

        this.elementList.add(element(x, y));
    }

    public void prepare() {
        Iterator<Element> elementIterator = this.elementList.iterator();
        while (elementIterator.hasNext()) {
            live(elementIterator.next());
        }

        this.prepared = true;
    }

    public void display(int x, int y) {
        System.out.print(element(x, y).getState() ? "x" : "o");
    }

    public void displayAll() {
        System.out.println("        Current Iteration: " + iteration);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                display(x, y);
            }
            System.out.println();
        }
    }

    public void displayLivingAround() {
        System.out.println("        Current Iteration: " + iteration);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(element(x, y).getLivingAround());
            }
            System.out.println();
        }
    }

    public void iterate() {
        if (!prepared) {
            System.out.println("Elements not correctly configured! prepare() needs to be run on this board instance.");
            return;
        }

        Iterator<Element> elementIterator = elementList.iterator();

        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();

            int x = element.getX();
            int y = element.getY();
            int living = element(x, y).getLivingAround();
            if (element.getState() && living < 2 || living > 3) {
                this.kill(element);
            } else if (living == 3) {
                this.live(element);
            }
        }

        iteration++;
    }

    private void live(Element element) {
        int x = element.getX();
        int y = element.getY();

        element.setState(true);

        element(x - 1, y - 1).addLiving();
        element(x, y - 1).addLiving();
        element(x + 1, y - 1).addLiving();

        element(x - 1, y).addLiving();
        element(x + 1, y).addLiving();

        element(x - 1, y + 1).addLiving();
        element(x, y + 1).addLiving();
        element(x + 1, y + 1).addLiving();

        elementList.add(element);

        elementList.add(element(x - 1, y - 1));
        elementList.add(element(x, y - 1));
        elementList.add(element(x + 1, y - 1));

        elementList.add(element(x - 1, y));
        elementList.add(element(x + 1, y));

        elementList.add(element(x - 1, y + 1));
        elementList.add(element(x, y + 1));
        elementList.add(element(x + 1, y + 1));
    }

    private void kill(Element element) {
        int x = element.getX();
        int y = element.getY();

        element.setState(false);

        element(x - 1, y - 1).subtractLiving();
        element(x, y - 1).subtractLiving();
        element(x + 1, y - 1).subtractLiving();

        element(x - 1, y).subtractLiving();
        element(x + 1, y).subtractLiving();

        element(x - 1, y + 1).subtractLiving();
        element(x, y + 1).subtractLiving();
        element(x + 1, y + 1).subtractLiving();

        elementList.remove(element);

        if (!element(x - 1, y - 1).getState()) elementList.remove(element(x - 1, y - 1));
        if (!element(x, y - 1).getState()) elementList.remove(element(x, y - 1));
        if (!element(x + 1, y - 1).getState()) elementList.remove(element(x + 1, y - 1));

        if (!element(x - 1, y).getState()) elementList.remove(element(x - 1, y));
        if (!element(x + 1, y).getState()) elementList.remove(element(x + 1, y));

        if (!element(x - 1, y + 1).getState()) elementList.remove(element(x - 1, y + 1));
        if (!element(x, y + 1).getState()) elementList.remove(element(x, y + 1));
        if (!element(x + 1, y + 1).getState()) elementList.remove(element(x + 1, y + 1));
    }
}
