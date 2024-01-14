public class ChessPiece {
    private String type;
    private String color;
    private String imagePath;
    public int moves = 0;
    public int lastMove = 0;

    public ChessPiece(String type, String color, String imagePath) {
        this.type = type;
        this.color = color;
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getImagePath(){
        return imagePath;
    }
}