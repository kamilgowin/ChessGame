public class Main {
    public static void main(String[] args) {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView();
        ChessController controller = new ChessController(model, view);

        view.setController(controller);
        view.initialize();
    }
}