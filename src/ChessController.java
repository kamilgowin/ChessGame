import java.util.Map;

public class ChessController {
    private ChessModel model;
    private ChessView view;
    private String selectedPosition;

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
        this.selectedPosition = null;
    }

    public void handleBoardButtonClick(int row, int col) {
        String position = getPosition(row, col);

        if (selectedPosition == null) {
            handleSelectPosition(position);
        } else {
            handleMove(selectedPosition, position);
        }
    }

    private void handleSelectPosition(String position) {
        ChessPiece piece = model.getPieceAt(position);

        if (piece != null) {
            selectedPosition = position;
        } //else {
           // view.showMessage("Select a valid piece.");
        //}
    }

    private void handleMove(String from, String to) {
        if (model.isMoveValid(from, to)) {
            model.movePiece(from, to);
            view.deselect();
            updateView();
            selectedPosition = null;

            if (model.isCheckmate()) {
                view.showMessage("Checkmate! Game Over.");
            } else if (model.isStalemate()) {
                view.showMessage("Stalemate! Game Over.");
            }
        } else {
            view.showMessage("Invalid move. Try again.");
            selectedPosition = null;
        }
    }

    private void updateView() {
        Map<String, ChessPiece> board = model.getBoard();
        view.updateBoard(board);
    }

    private String getPosition(int row, int col) {
        char file = (char) ('A' + col);
        int rank = 8 - row;
        return String.valueOf(file) + rank;
    }

    public ChessModel getModel() {
        return model;
    }
}