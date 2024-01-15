import java.util.Objects;

public class PawnValidMoves implements Strategy {

    private final ChessModel model;
    public PawnValidMoves(ChessModel model){
        this.model = model;
    }


    public boolean execute(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference, boolean executeSpecialMoves){
        if(Objects.equals(piece.getColor(), "white")){
            if((fromRank - toRank) == -1 && targetPiece == null && fileDifference == 0){
                return true;
            }
            if((fromRank - toRank) == -2 && targetPiece == null && piece.moves == 0 && fileDifference == 0 && model.getBoard().get(model.getPosition(fromFile,fromRank+1)) == null){
                return true;
            }
            if(fileDifference == 1 && (fromRank - toRank) == -1 && targetPiece != null){
                if(Objects.equals(targetPiece.getColor(), "black")){
                    return true;
                }
            }
            //en passant
            if(fileDifference == 1 && (fromRank - toRank) == -1 && targetPiece == null){
                if(model.getBoard().get(model.getPosition(toFile,toRank-1)) != null){
                    if (Objects.equals(model.getBoard().get(model.getPosition(toFile, toRank - 1)).getType(), "pawn") && Objects.equals(model.getBoard().get(model.getPosition(toFile, toRank - 1)).getColor(), "black")){
                        if (model.getBoard().get(model.getPosition(toFile,toRank-1)).moves == 1 && model.getBoard().get(model.getPosition(toFile,toRank-1)).lastMove == model.moves ){
                            if(executeSpecialMoves){
                                model.getBoard().remove(model.getPosition(toFile,toRank-1));
                            }
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
            if((fromRank - toRank) == 2 && targetPiece == null && piece.moves == 0 && fileDifference == 0 && model.getBoard().get(model.getPosition(fromFile,fromRank-1)) == null){
                return true;
            }
            if(fileDifference == 1 && (fromRank - toRank) == 1 && targetPiece != null){
                if(Objects.equals(targetPiece.getColor(), "white")){
                    return true;
                }
            }
            //en passant
            if(fileDifference == 1 && (fromRank - toRank) == 1 && targetPiece == null){
                if(model.getBoard().get(model.getPosition(toFile,toRank+1)) != null){
                    if (Objects.equals(model.getBoard().get(model.getPosition(toFile, toRank + 1)).getType(), "pawn") && Objects.equals(model.getBoard().get(model.getPosition(toFile, toRank + 1)).getColor(), "white")){
                        if (model.getBoard().get(model.getPosition(toFile,toRank+1)).moves == 1 && model.getBoard().get(model.getPosition(toFile,toRank+1)).lastMove == model.moves ){
                            if(executeSpecialMoves){
                                model.getBoard().remove(model.getPosition(toFile,toRank+1));
                            }
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }
}
