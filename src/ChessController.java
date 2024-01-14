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

    public void handleBoardButtonClick(int row, int col, boolean deselect) {
        String position = getPosition(row, col);

        if(!deselect){
            if (selectedPosition == null) {
                handleSelectPosition(position);
            } else {
                handleMove(selectedPosition, position);
            }
        }
        else {
            selectedPosition = null;
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
        if (model.isMoveValid(from, to) && model.isMoveSafeForKing(from, to)) {
            model.movePiece(from, to);
            updateView(from, to);
            selectedPosition = null;

            if (model.isCheckmate("white")) {
                view.showMessage("Checkmate! Black wins!");
            } else if (model.isStalemate("white")) {
                view.showMessage("Stalemate! It's a draw.");
            } else if (model.isCheckmate("black")) {
                view.showMessage("Checkmate! White wins!");
            } else if (model.isStalemate("black")){
                view.showMessage("Stalemate! It's a draw.");
            }
        } else {
            view.showMessage("Invalid move. Try again.");
            selectedPosition = null;
        }
    }

    private void updateView(String from, String to) {
        Map<String, ChessPiece> board = model.getBoard();
        view.deselect();
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