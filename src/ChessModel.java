import java.util.*;

public class ChessModel {
    private Map<String, ChessPiece> board;
    private String checkedKingPosition;
    private int moves = 0;

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
            // Sprawdź, czy król został zaszachowany po ruchu
            String kingColor = piece.getColor().equals("white") ? "black" : "white";
            String kingPosition = getKingPosition(kingColor);

            if (isFieldInCheck(kingPosition, kingColor)) {
                checkedKingPosition = kingPosition;
            } else {
                checkedKingPosition = null;
            }
            moves++;
            piece.lastMove = moves;
            System.out.println(moves);
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
        if(Objects.equals(piece.getType(), "bishop")){
            return isBishopMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference);
        }
        if(Objects.equals(piece.getType(), "queen")){
            if(isBishopMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference) || isRookMoveValid(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference)){
                return true;
            }
            return false;
        }

        return false;
    }

    public boolean isBishopMoveValid(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference){
        if(rankDifference > 0 && fileDifference > 0 && fileDifference == rankDifference){
            if(toRank > fromRank && toFile > fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (board.get(getPosition(fromFile+i,fromRank+i)) != null){
                        return false;
                    }
                }
            }
            if(toRank > fromRank && toFile < fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (board.get(getPosition(fromFile-i,fromRank+i)) != null){
                        return false;
                    }
                }
            }
            if(toRank < fromRank && toFile > fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (board.get(getPosition(fromFile+i,fromRank-i)) != null){
                        return false;
                    }
                }
            }
            if(toRank < fromRank && toFile < fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (board.get(getPosition(fromFile-i,fromRank-i)) != null){
                        return false;
                    }
                }
            }
            return true;
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

        if(rankDifference == 0 && fileDifference > 0){
            if(toFile > fromFile){
                for (int i = 1; i < fileDifference; i++) {
                    if (board.get(getPosition(fromFile+i,fromRank)) != null){
                        return false;
                    }
                }
                return true;
            }

            if(toFile < fromFile){
                for (int i = 1; i < fileDifference; i++) {
                    if (board.get(getPosition(fromFile-i,fromRank)) != null){
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
        //roszada
        if(Objects.equals(piece.getColor(), "white")){
            if(rankDifference == 0 && toFile == 7){
                if(piece.moves == 0 && board.get(getPosition(toFile+1,toRank)).moves == 0){
                    if(board.get(getPosition(toFile,toRank)) == null && board.get(getPosition(toFile-1,toRank)) == null){
                        if(!isFieldInCheck("F1","white") && !isFieldInCheck("G1","white")){
                            movePiece("H1", "F1");
                            return true;
                        }
                    }
                }
            }

            if(rankDifference == 0 && toFile == 3){
                if(piece.moves == 0 && board.get(getPosition(toFile-2,toRank)).moves == 0){
                    if(board.get(getPosition(toFile,toRank)) == null && board.get(getPosition(toFile-1,toRank)) == null && board.get(getPosition(toFile+1,toRank)) == null){
                        if(!isFieldInCheck("B1","white") && !isFieldInCheck("C1","white") && !isFieldInCheck("D1","white")){
                            movePiece("A1", "D1");
                            return true;
                        }
                    }
                }
            }
        }

        if(Objects.equals(piece.getColor(), "black")){
            if(rankDifference == 0 && toFile == 7){
                if(piece.moves == 0 && board.get(getPosition(toFile+1,toRank)).moves == 0){
                    if(board.get(getPosition(toFile,toRank)) == null && board.get(getPosition(toFile-1,toRank)) == null){
                        if(!isFieldInCheck("F8","black") && !isFieldInCheck("G8","black")){
                            movePiece("H8", "F8");
                            return true;
                        }
                    }
                }
            }

            if(rankDifference == 0 && toFile == 3){
                if(piece.moves == 0 && board.get(getPosition(toFile-2,toRank)).moves == 0){
                    if(board.get(getPosition(toFile,toRank)) == null && board.get(getPosition(toFile-1,toRank)) == null && board.get(getPosition(toFile+1,toRank)) == null){
                        if(!isFieldInCheck("B8","black") && !isFieldInCheck("C8","black") && !isFieldInCheck("D8","black")){
                            movePiece("A8", "D8");
                            return true;
                        }
                    }
                }
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
            //en passant
            if(fileDifference == 1 && (fromRank - toRank) == -1 && targetPiece == null){
                if(board.get(getPosition(toFile,toRank-1)) != null){
                    if (Objects.equals(board.get(getPosition(toFile, toRank - 1)).getType(), "pawn") && Objects.equals(board.get(getPosition(toFile, toRank - 1)).getColor(), "black")){
                        if (board.get(getPosition(toFile,toRank-1)).moves == 1 && board.get(getPosition(toFile,toRank-1)).lastMove == moves ){
                            board.remove(getPosition(toFile,toRank-1));
                            return true;
                        }
                    }
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
            //en passant
            if(fileDifference == 1 && (fromRank - toRank) == 1 && targetPiece == null){
                if(board.get(getPosition(toFile,toRank+1)) != null){
                    if (Objects.equals(board.get(getPosition(toFile, toRank + 1)).getType(), "pawn") && Objects.equals(board.get(getPosition(toFile, toRank + 1)).getColor(), "white")){
                        if (board.get(getPosition(toFile,toRank+1)).moves == 1 && board.get(getPosition(toFile,toRank+1)).lastMove == moves ){
                            board.remove(getPosition(toFile,toRank+1));
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    private String getPosition(int file, int rank) {
        return String.valueOf((char) ('A' + file - 1)) + rank;
    }


    public boolean isFieldInCheck(String fieldPosition, String fieldColor) {
        for (Map.Entry<String, ChessPiece> entry : board.entrySet()) {
            String currentPosition = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (!piece.getColor().equals(fieldColor)) {
                // Sprawdź, czy figura przeciwnika może zaatakować króla
                if (isMoveValid(currentPosition, fieldPosition)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isMoveSafeForKing(String from, String to) {
        ChessPiece movedPiece = board.get(from);
        ChessPiece targetPiece = board.get(to);

        String kingColor = movedPiece.getColor();

        // Wykonaj ruch wirtualny na planszy
        board.remove(from);
        board.put(to, movedPiece);

        // Sprawdź, czy po ruchu król jest nadal szachowany
        boolean isSafe = !isFieldInCheck(getKingPosition(kingColor), kingColor);

        // Cofnij ruch na planszy
        board.remove(to);
        board.put(from, movedPiece);

        if (targetPiece != null) {
            // Jeśli było bicie, przywróć zbitego pionka
            board.put(to, targetPiece);
        }

        return isSafe;
    }

    private String getKingPosition(String kingColor) {
        for (Map.Entry<String, ChessPiece> entry : board.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getType().equals("king") && piece.getColor().equals(kingColor)) {
                return entry.getKey();
            }
        }
        return null; // To nigdy nie powinno się zdarzyć, jeśli plansza jest poprawnie skonfigurowana
    }

    public boolean isCheckmate(String kingColor) {
        String kingPosition = getKingPosition(kingColor);

        if (!isFieldInCheck(kingPosition, kingColor)) {
            // Król nie jest w szachu, więc nie może być matu
            return false;
        }

        // Sprawdź, czy król ma możliwość ucieczki na wolne pole
        assert kingPosition != null;
        int kingFile = kingPosition.charAt(0) - 'A' + 1;
        int kingRank = Integer.parseInt(kingPosition.substring(1));

        for (int fileDelta = -1; fileDelta <= 1; fileDelta++) {
            for (int rankDelta = -1; rankDelta <= 1; rankDelta++) {
                if (fileDelta == 0 && rankDelta == 0) {
                    continue;  // Pomijamy pozycję samego króla
                }

                int targetFile = kingFile + fileDelta;
                int targetRank = kingRank + rankDelta;

                if (isValidPosition(targetFile, targetRank)) {
                    String targetPosition = getPosition(targetFile, targetRank);

                    if (isMoveValid(kingPosition, targetPosition) && isMoveSafeForKing(kingPosition, targetPosition)) {
                        return false;  // Król ma możliwość ucieczki
                    }
                }
            }
        }

        // Sprawdź, czy istnieje możliwość zablokowania szacha
        Map<String, ChessPiece> originalBoard = new HashMap<>(board);

        for (Map.Entry<String, ChessPiece> entry : originalBoard.entrySet()) {
            String currentPosition = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (piece.getColor().equals(kingColor)) {
                // Sprawdź, czy dla każdej figury króla można zablokować szacha
                for (int row = 1; row <= 8; row++) {
                    for (int col = 1; col <= 8; col++) {
                        String targetPosition = getPosition(row, col);

                        if (isMoveValid(currentPosition, targetPosition) && isMoveSafeForKing(currentPosition, targetPosition)) {
                            return false;  // Można zablokować szacha
                        }
                    }
                }
            }
        }

        return true;
    }


    private boolean isValidPosition(int file, int rank) {
        return file >= 1 && file <= 8 && rank >= 1 && rank <= 8;
    }


    public boolean isStalemate(String playerColor) {
        // Sprawdź, czy król gracza nie jest w szachu
        if (isFieldInCheck(getKingPosition(playerColor), playerColor)) {
            return false; // Król jest w szachu, więc nie ma patu
        }

        // Sprawdź, czy gracz nie ma żadnych legalnych ruchów
        for (Map.Entry<String, ChessPiece> entry : board.entrySet()) {
            String currentPosition = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (piece.getColor().equals(playerColor)) {
                for (int row = 1; row <= 8; row++) {
                    for (int col = 1; col <= 8; col++) {
                        String targetPosition = getPosition(row, col);

                        if (isMoveValid(currentPosition, targetPosition) && isMoveSafeForKing(currentPosition, targetPosition)) {
                            return false; // Gracz ma legalny ruch
                        }
                    }
                }
            }
        }

        return true; // Brak legalnych ruchów, występuje pat
    }

    public ChessPiece getPieceAt(String position) {
        return board.get(position);
    }

    public Map<String, ChessPiece> getBoard() {
        return board;
    }
    public String getCheckedKingPosition() {
        return checkedKingPosition;
    }
}
