import java.util.ArrayList;
import java.util.List;

public class Board {
    private int height;
    private int width;
    private boolean prepared = false;

    private Element[][] board;
    private List<Element> living;

    public Board(int height, int width) {
        // Minimum Height and Width = 3
        if (height < 3) height = 3;
        if (width < 3) width = 3;

        this.height = height + 2;
        this.width = width + 2;

        this.board = new Element[width + 2][height + 2];

        this.initialFill();

        this.living = new ArrayList<>();
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

        this.living.add(element(x, y));
    }

    public void prepare() {
        for (Element element : this.living) {
            int x = element.getX();
            int y = element.getY();

            element(x - 1, y - 1).addLiving();
            element(x    , y - 1).addLiving();
            element(x + 1, y - 1).addLiving();

            element(x - 1, y    ).addLiving();
            element(x + 1, y    ).addLiving();

            element(x - 1, y + 1).addLiving();
            element(x    , y + 1).addLiving();
            element(x + 1, y + 1).addLiving();
        }

        this.prepared = true;
    }

    public void display(int x, int y) {
        System.out.print(element(x, y).getState() ? 1 : 0);
    }

    public void displayAll() {
        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                display(x, y);
            }
            System.out.println();
        }
    }

    public void iterate() {
        for (Element element : living) {
            if (element(element.getX(), element.getY()).getLivingAround() < 2) {
                element.setState(false);
                this.subtractAround(element);
            }
        }
    }

    private void subtractAround(Element element) {

    }
}
