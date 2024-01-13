import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChessModel {
    private Map<String, ChessPiece> board;

    public ChessModel() {
        board = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Początkowy stan planszy

        placePiece("A1", new ChessPiece("rook", "white","Images/WhiteRook.png"));
        placePiece("B1", new ChessPiece("knight", "white","Images/WhiteKnight.png"));
        placePiece("C1", new ChessPiece("bishop", "white","Images/WhiteBishop.png"));
        placePiece("D1", new ChessPiece("queen", "white","Images/WhiteQueen.png"));
        placePiece("E1", new ChessPiece("king", "white","Images/WhiteKing.png"));
        placePiece("F1", new ChessPiece("bishop", "white","Images/WhiteBishop.png"));
        placePiece("G1", new ChessPiece("knight", "white","Images/WhiteKnight.png"));
        placePiece("H1", new ChessPiece("rook", "white","Images/WhiteRook.png"));

        placePiece("A2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("B2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("C2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("D2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("E2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("F2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("G2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));
        placePiece("H2", new ChessPiece("pawn", "white","Images/WhitePawn.png"));

        placePiece("A8", new ChessPiece("rook", "black","Images/BlackRook.png"));
        placePiece("B8", new ChessPiece("knight", "black","Images/BlackKnight.png"));
        placePiece("C8", new ChessPiece("bishop", "black","Images/BlackBishop.png"));
        placePiece("D8", new ChessPiece("queen", "black","Images/BlackQueen.png"));
        placePiece("E8", new ChessPiece("king", "black","Images/BlackKing.png"));
        placePiece("F8", new ChessPiece("bishop", "black","Images/BlackBishop.png"));
        placePiece("G8", new ChessPiece("knight", "black","Images/BlackKnight.png"));
        placePiece("H8", new ChessPiece("rook", "black","Images/BlackRook.png"));

        placePiece("A7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("B7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("C7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("D7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("E7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("F7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("G7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
        placePiece("H7", new ChessPiece("pawn", "black","Images/BlackPawn.png"));
    }

    public void placePiece(String position, ChessPiece piece) {
        board.put(position, piece);
    }

    public void movePiece(String from, String to) {
        ChessPiece piece = board.remove(from);

        if (piece != null) {
            board.put(to, piece);
            piece.moves++;
        }
    }

    public boolean isMoveValid(String from, String to) {
        ChessPiece piece = board.get(from);
        ChessPiece targetPiece = board.get(to);

        if (piece == null) {
            return false; // Ruch nieprawidłowy, bo brak figury do przesunięcia lub docelowe pole już zajęte
        }

        if (targetPiece != null && Objects.equals(piece.getColor(), targetPiece.getColor())){
            return false;
        }

        int fromRank = Integer.parseInt(from.substring(1));
        int toRank = Integer.parseInt(to.substring(1));
        int fromFile = from.charAt(0) - 'A' + 1;
        int toFile = to.charAt(0) - 'A' + 1;

        int rankDifference = Math.abs(toRank - fromRank);
        int fileDifference = Math.abs(toFile - fromFile);

        if(Objects.equals(piece.getType(), "pawn")){
            return isPawnMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference);
        }
        if(Objects.equals(piece.getType(), "king")){
            return isKingMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference);
        }
        if(Objects.equals(piece.getType(), "knight")){
            return isKnightMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference);
        }
        if(Objects.equals(piece.getType(), "rook")){
            return isRookMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference);
        }

        return false;
    }

    public boolean isRookMoveValid(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference){
        if(rankDifference > 0 && fileDifference == 0){
            if(toRank > fromRank){
                for (int i = 1; i < rankDifference; i++) {
                   if (board.get(getPosition(fromFile,fromRank+i)) != null){
                       return false;
                   }
                }
                return true;
            }

            if(toRank < fromRank){
                for (int i = 1; i < rankDifference; i++) {
                    if (board.get(getPosition(fromFile,fromRank-i)) != null){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean isKnightMoveValid(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference){
        if((rankDifference == 1 && fileDifference == 2) || (rankDifference == 2 && fileDifference == 1)){
            if(targetPiece == null){
                return true;
            }
            if(!Objects.equals(targetPiece.getColor(), piece.getColor())){
                return true;
            }
        }
        return false;
    }

    public boolean isKingMoveValid(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference){
        if(rankDifference <= 1 && fileDifference <= 1){
            if(targetPiece == null){
                return true;
            }
            if(!Objects.equals(targetPiece.getColor(), piece.getColor())){
                return true;
            }
        }
        return false;
    }

    public boolean isPawnMoveValid(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference){

        if(Objects.equals(piece.getColor(), "white")){
            if((fromRank - toRank) == -1 && targetPiece == null && fileDifference == 0){
                return true;
            }
            if((fromRank - toRank) == -2 && targetPiece == null && piece.moves == 0 && fileDifference == 0 && board.get(getPosition(fromFile,fromRank+1)) == null){
                return true;
            }
            if(fileDifference == 1 && (fromRank - toRank) == -1 && targetPiece != null){
                if(Objects.equals(targetPiece.getColor(), "black")){
                    return true;
                }
            }
        }

        if(Objects.equals(piece.getColor(), "black")){
            if((fromRank - toRank) == 1 && targetPiece == null && fileDifference == 0){
                return true;
            }
            if((fromRank - toRank) == 2 && targetPiece == null && piece.moves == 0 && fileDifference == 0 && board.get(getPosition(fromFile,fromRank-1)) == null){
                return true;
            }
            if(fileDifference == 1 && (fromRank - toRank) == 1 && targetPiece != null){
                if(Objects.equals(targetPiece.getColor(), "white")){
                    return true;
                }
            }
        }

        return false;
    }

    private String getPosition(int file, int rank) {
        return String.valueOf((char) ('A' + file - 1)) + rank;
    }

    public boolean isCheckmate() {
        // Sprawdzenie czy wystąpił mat
        return false; // tymczasowo zawsze zwracamy false
    }

    public boolean isStalemate() {
        // Sprawdzenie czy wystąpił pat
        return false; // tymczasowo zawsze zwracamy false
    }

    public ChessPiece getPieceAt(String position) {
        return board.get(position);
    }

    public Map<String, ChessPiece> getBoard() {
        return board;
    }
}
