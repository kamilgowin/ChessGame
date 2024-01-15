import java.util.Map;

public class ChessController {
    private final ChessModel model;
    private final ChessView view;
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
        }
    }

    private void handleMove(String from, String to) {
        if (model.isMoveValid(from, to, false, true) && model.isMoveSafeForKing(from, to)) {
            ChessPiece piece = model.getPieceAt(from);
            String pieceColor = null;
            String pieceType = null;
            int pieceMoves = 0;
            if(piece != null){
                pieceColor = piece.getColor();
                pieceType = piece.getType();
                pieceMoves = piece.moves;
            }
            model.movePiece(from, to);
            handlePawnPromotion(to, pieceType, pieceColor, pieceMoves);
            updateView();
            selectedPosition = null;

            if (model.isCheckmate("white")) {
                view.showMessage("Checkmate! Black wins!");
                resetGame();
            } else if (model.isStalemate("white")) {
                view.showMessage("Stalemate! It's a draw.");
            } else if (model.isCheckmate("black")) {
                view.showMessage("Checkmate! White wins!");
                resetGame();
            } else if (model.isStalemate("black")){
                view.showMessage("Stalemate! It's a draw.");
            } else if (model.isInsufficientMaterial()) {
                view.showMessage("Insufficient material! It's a draw.");
            }
        } else {
            view.showMessage("Invalid move. Try again.");
            selectedPosition = null;
        }
    }

    private void handlePawnPromotion(String to, String type, String color, int moves){

        int toRank = Integer.parseInt(to.substring(1));
        String promoteTo;
        if(type != null && color != null){
            if(type.equals("pawn")){
                if (color.equals("white") && toRank == 8){
                    promoteTo = view.choosePawnPromotion();
                    model.promotePawn(color,to, promoteTo, moves);
                }
            }
            if(type.equals("pawn")){
                if (color.equals("black") && toRank == 1){
                    promoteTo = view.choosePawnPromotion();
                    model.promotePawn(color,to, promoteTo, moves);
                }
            }
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

    private void resetGame(){
        view.closeFrame();
        model.initializeBoard();
        view.initialize();
    }

    public ChessModel getModel() {
        return model;
    }
}