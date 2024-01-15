public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public boolean executeStrategy(ChessPiece piece, ChessPiece targetPiece, int fromRank, int toRank, int fromFile, int toFile, int rankDifference, int fileDifference, boolean executeSpecialMoves){
        return strategy.execute(piece, targetPiece, fromRank, toRank, fromFile, toFile, rankDifference, fileDifference, executeSpecialMoves);
    }
}
