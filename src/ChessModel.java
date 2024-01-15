import java.util.*;

public class ChessModel {
    private final Map<String, ChessPiece> board;
    private String checkedKingPosition;
    public int moves;
    private String nextMove;
    private final Context context = new Context();

    public ChessModel() {
        board = new HashMap<>();
        initializeBoard();
    }

    public void initializeBoard() {
        board.clear();
        nextMove = "white";
        moves = 0;

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

            String kingColor = piece.getColor().equals("white") ? "black" : "white";
            String kingPosition = getKingPosition(kingColor);

            if (isFieldInCheck(kingPosition, kingColor)) {
                checkedKingPosition = kingPosition;
            } else {
                checkedKingPosition = null;
            }
            moves++;
            piece.lastMove = moves;
            if(Objects.equals(nextMove, "white")){
                nextMove = "black";
            } else if (Objects.equals(nextMove, "black")) {
                nextMove = "white";
            }
        }
    }



    public boolean isMoveValid(String from, String to, boolean skipOrder, boolean executeSpecialMoves) {
        ChessPiece piece = board.get(from);
        ChessPiece targetPiece = board.get(to);

        if (piece == null) {
            return false;
        }

        if (targetPiece != null && Objects.equals(piece.getColor(), targetPiece.getColor())){
            return false;
        }

        if(!skipOrder){
            if(!Objects.equals(piece.getColor(), nextMove)){
                return false;
            }
        }

        int fromRank = Integer.parseInt(from.substring(1));
        int toRank = Integer.parseInt(to.substring(1));
        int fromFile = from.charAt(0) - 'A' + 1;
        int toFile = to.charAt(0) - 'A' + 1;

        int rankDifference = Math.abs(toRank - fromRank);
        int fileDifference = Math.abs(toFile - fromFile);

        if(Objects.equals(piece.getType(), "pawn")){
            context.setStrategy(new PawnValidMoves(this));
        }
        if(Objects.equals(piece.getType(), "king")){
            context.setStrategy(new KingValidMoves(this));
        }
        if(Objects.equals(piece.getType(), "knight")){
            context.setStrategy(new KnightValidMoves(this));
        }
        if(Objects.equals(piece.getType(), "rook")){
            context.setStrategy(new RookValidMoves(this));
        }
        if(Objects.equals(piece.getType(), "bishop")){
            context.setStrategy(new BishopValidMoves(this));
        }
        if(Objects.equals(piece.getType(), "queen")){
            context.setStrategy(new QueenValidMoves(this));
        }
        return context.executeStrategy(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference, executeSpecialMoves);
    }

    public void promotePawn(String color, String to, String type, int pieceMoves){
        String path = null;
        if(color.equals("white")){
            if(Objects.equals(type, "queen")){
                path = "Images/WhiteQueen.png";
            }
            if(Objects.equals(type, "rook")){
                path = "Images/WhiteRook.png";
            }
            if(Objects.equals(type, "knight")){
                path = "Images/WhiteKnight.png";
            }
            if(Objects.equals(type, "bishop")){
                path = "Images/WhiteBishop.png";
            }
        }
        if(color.equals("black")){
            if(Objects.equals(type, "queen")){
                path = "Images/BlackQueen.png";
            }
            if(Objects.equals(type, "rook")){
                path = "Images/BlackRook.png";
            }
            if(Objects.equals(type, "knight")){
                path = "Images/BlackKnight.png";
            }
            if(Objects.equals(type, "bishop")){
                path = "Images/BlackBishop.png";
            }
        }
        if(path != null){
            ChessPiece promotedPiece = new ChessPiece(type, color, path);
            moves++;
            promotedPiece.lastMove = moves+1;
            promotedPiece.moves = pieceMoves;
            board.put(to, promotedPiece);
            String kingColor = color.equals("white") ? "black" : "white";
            String kingPosition = getKingPosition(kingColor);

            if (isFieldInCheck(kingPosition, kingColor)) {
                checkedKingPosition = kingPosition;
            } else {
                checkedKingPosition = null;
            }
        }
    }

    public String getPosition(int file, int rank) {
        return String.valueOf((char) ('A' + file - 1)) + rank;
    }

    public boolean isFieldInCheck(String fieldPosition, String fieldColor) {
        for (Map.Entry<String, ChessPiece> entry : board.entrySet()) {
            String currentPosition = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (!piece.getColor().equals(fieldColor)) {
                if (isMoveValid(currentPosition, fieldPosition, true, false)) {
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

        board.remove(from);
        board.put(to, movedPiece);

        boolean isSafe = !isFieldInCheck(getKingPosition(kingColor), kingColor);

        board.remove(to);
        board.put(from, movedPiece);

        if (targetPiece != null) {
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
        return null;
    }

    public boolean isCheckmate(String kingColor) {
        String kingPosition = getKingPosition(kingColor);

        if (!isFieldInCheck(kingPosition, kingColor)) {
            return false;
        }

        assert kingPosition != null;
        int kingFile = kingPosition.charAt(0) - 'A' + 1;
        int kingRank = Integer.parseInt(kingPosition.substring(1));

        for (int fileDelta = -1; fileDelta <= 1; fileDelta++) {
            for (int rankDelta = -1; rankDelta <= 1; rankDelta++) {
                if (fileDelta == 0 && rankDelta == 0) {
                    continue;
                }

                int targetFile = kingFile + fileDelta;
                int targetRank = kingRank + rankDelta;

                if (isValidPosition(targetFile, targetRank)) {
                    String targetPosition = getPosition(targetFile, targetRank);

                    if (isMoveValid(kingPosition, targetPosition, true, false) && isMoveSafeForKing(kingPosition, targetPosition)) {
                        return false;
                    }
                }
            }
        }

        Map<String, ChessPiece> originalBoard = new HashMap<>(board);

        for (Map.Entry<String, ChessPiece> entry : originalBoard.entrySet()) {
            String currentPosition = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (piece.getColor().equals(kingColor)) {
                for (int row = 1; row <= 8; row++) {
                    for (int col = 1; col <= 8; col++) {
                        String targetPosition = getPosition(row, col);

                        if (isMoveValid(currentPosition, targetPosition, true, false) && isMoveSafeForKing(currentPosition, targetPosition)) {
                            return false;
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
        if (isFieldInCheck(getKingPosition(playerColor), playerColor)) {
            return false;
        }

        for (Map.Entry<String, ChessPiece> entry : board.entrySet()) {
            String currentPosition = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (piece.getColor().equals(playerColor)) {
                for (int row = 1; row <= 8; row++) {
                    for (int col = 1; col <= 8; col++) {
                        String targetPosition = getPosition(row, col);

                        if (isMoveValid(currentPosition, targetPosition, true, false) && isMoveSafeForKing(currentPosition, targetPosition)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean isInsufficientMaterial() {
        int whiteKingsCount = 0;
        int whiteKnightsCount = 0;
        int whiteBishopsCount = 0;
        int blackKingsCount = 0;
        int blackKnightsCount = 0;
        int blackBishopsCount = 0;

        for (Map.Entry<String, ChessPiece> entry : board.entrySet()) {
            ChessPiece piece = entry.getValue();
            String type = piece.getType();
            String color = piece.getColor();

            switch (type) {
                case "king" -> {
                    if (color.equals("white")) {
                        whiteKingsCount++;
                    }
                    if (color.equals("black")) {
                        blackKingsCount++;
                    }
                }
                case "knight" -> {
                    if (color.equals("white")) {
                        whiteKnightsCount++;
                    }
                    if (color.equals("black")) {
                        blackKnightsCount++;
                    }
                }
                case "bishop" -> {
                    if (color.equals("white")) {
                        whiteBishopsCount++;
                    }
                    if (color.equals("black")) {
                        blackBishopsCount++;
                    }
                }
            }
        }
        int whitePieces = whiteKingsCount + whiteKnightsCount + whiteBishopsCount;
        int blackPieces = blackKingsCount + blackKnightsCount + blackBishopsCount;

        if(whitePieces+blackPieces != board.size()){
            return false;
        }

        if((whitePieces == 1 && blackPieces == 3 && blackKnightsCount == 2) || (blackPieces == 1 && whitePieces == 3 && whiteKnightsCount == 2)){
            return true;
        }

        if(whitePieces <=2 && blackPieces <= 2){
            return true;
        }

        return false;
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
