public class Main {
    public static void main(String[] args) {
        Board board = new Board(20, 20);

        board.fill(1, 3);
        board.fill(2, 3);
        board.fill(3, 3);
        board.fill(3, 2);
        board.fill(2, 1);

        board.displayAll();
    }
}
