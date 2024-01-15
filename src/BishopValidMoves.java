public class BishopValidMoves implements Strategy {
    private final ChessModel model;
    public BishopValidMoves(ChessModel model) {
        this.model = model;
    }

    public boolean execute(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference, boolean executeSpecialMoves) {
        if(fileDifference > 0 && fileDifference == rankDifference){
            if(toRank > fromRank && toFile > fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile+i,fromRank+i)) != null){
                        return false;
                    }
                }
            }
            if(toRank > fromRank && toFile < fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile-i,fromRank+i)) != null){
                        return false;
                    }
                }
            }
            if(toRank < fromRank && toFile > fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile+i,fromRank-i)) != null){
                        return false;
                    }
                }
            }
            if(toRank < fromRank && toFile < fromFile){
                for (int i = 1; i < rankDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile-i,fromRank-i)) != null){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}