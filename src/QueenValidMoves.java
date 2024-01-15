public class QueenValidMoves implements Strategy{
    private final ChessModel model;
    public QueenValidMoves(ChessModel model){
        this.model = model;
    }

    public boolean execute(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference, boolean executeSpecialMoves) {
        if(rankDifference > 0 && fileDifference == 0){
            if(toRank > fromRank){
                for (int i = 1; i < rankDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile,fromRank+i)) != null){
                        return false;
                    }
                }
                return true;
            }

            if(toRank < fromRank){
                for (int i = 1; i < rankDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile,fromRank-i)) != null){
                        return false;
                    }
                }
                return true;
            }
        }

        if(rankDifference == 0 && fileDifference > 0){
            if(toFile > fromFile){
                for (int i = 1; i < fileDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile+i,fromRank)) != null){
                        return false;
                    }
                }
                return true;
            }

            if(toFile < fromFile){
                for (int i = 1; i < fileDifference; i++) {
                    if (model.getBoard().get(model.getPosition(fromFile-i,fromRank)) != null){
                        return false;
                    }
                }
                return true;
            }
        }

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
