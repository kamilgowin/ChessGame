import java.util.Objects;

public class KnightValidMoves implements Strategy{

    private ChessModel model;
    public KnightValidMoves(ChessModel model){
        this.model = model;
    }

    public boolean execute(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference, boolean executeSpecialMoves){
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
}
