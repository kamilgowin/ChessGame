import java.util.Objects;

public class KingValidMoves implements Strategy{

    private final ChessModel model;
    public KingValidMoves(ChessModel model){
        this.model = model;
    }

    public boolean execute(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference, boolean executeSpecialMoves){
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
                if(model.getBoard().get(model.getPosition(toFile+1,toRank)) != null){
                    if(piece.moves == 0 && model.getBoard().get(model.getPosition(toFile+1,toRank)).moves == 0){
                        if(model.getBoard().get(model.getPosition(toFile,toRank)) == null && model.getBoard().get(model.getPosition(toFile-1,toRank)) == null){
                            if(!model.isFieldInCheck("F1","white") && !model.isFieldInCheck("G1","white")){
                                if(executeSpecialMoves){
                                    model.getBoard().put("F1",model.getBoard().remove("H1"));
                                }
                                return true;
                            }
                        }
                    }
                }
            }

            if(rankDifference == 0 && toFile == 3){
                if(model.getBoard().get(model.getPosition(toFile-2,toRank)) != null){
                    if(piece.moves == 0 && model.getBoard().get(model.getPosition(toFile-2,toRank)).moves == 0){
                        if(model.getBoard().get(model.getPosition(toFile,toRank)) == null && model.getBoard().get(model.getPosition(toFile-1,toRank)) == null && model.getBoard().get(model.getPosition(toFile+1,toRank)) == null){
                            if(!model.isFieldInCheck("B1","white") && !model.isFieldInCheck("C1","white") && !model.isFieldInCheck("D1","white")){
                                if(executeSpecialMoves){
                                    model.getBoard().put("D1",model.getBoard().remove("A1"));
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        if(Objects.equals(piece.getColor(), "black")){
            if(rankDifference == 0 && toFile == 7){
                if(model.getBoard().get(model.getPosition(toFile+1,toRank)) != null){
                    if(piece.moves == 0 && model.getBoard().get(model.getPosition(toFile+1,toRank)).moves == 0){
                        if(model.getBoard().get(model.getPosition(toFile,toRank)) == null && model.getBoard().get(model.getPosition(toFile-1,toRank)) == null){
                            if(!model.isFieldInCheck("F8","black") && !model.isFieldInCheck("G8","black")){
                                if(executeSpecialMoves){
                                    model.getBoard().put("F8",model.getBoard().remove("H8"));
                                }
                                return true;
                            }
                        }
                    }
                }
            }

            if(rankDifference == 0 && toFile == 3){
                if(model.getBoard().get(model.getPosition(toFile-2,toRank)) != null){
                    if(piece.moves == 0 && model.getBoard().get(model.getPosition(toFile-2,toRank)).moves == 0){
                        if(model.getBoard().get(model.getPosition(toFile,toRank)) == null && model.getBoard().get(model.getPosition(toFile-1,toRank)) == null && model.getBoard().get(model.getPosition(toFile+1,toRank)) == null){
                            if(!model.isFieldInCheck("B8","black") && !model.isFieldInCheck("C8","black") && !model.isFieldInCheck("D8","black")){
                                if(executeSpecialMoves){
                                    model.getBoard().put("D8",model.getBoard().remove("A8"));
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
