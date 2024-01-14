import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ChessView {
    private ChessController controller;
    private JFrame frame;
    private JPanel chessBoardPanel;
    private JLabel[][] boardLabels;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Map<String, ImageIcon> imageCache = new HashMap<>();
    public void setController(ChessController controller) {
        this.controller = controller;
    }

    public void initialize() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension screenSize = new Dimension(900, 900);
            frame.setResizable(false);
            frame.setPreferredSize(screenSize);
            initializeChessBoardPanel();
            initializeOtherComponents();

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            setupImageCache();
            updateBoard(controller.getModel().getBoard());
        });
    }

    private void initializeChessBoardPanel() {
        chessBoardPanel = new JPanel(new GridLayout(8, 8));
        boardLabels = new JLabel[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground(getSquareColor(row, col));
                label.addMouseListener(new ChessBoardMouseListener(row, col));
                boardLabels[row][col] = label;
                chessBoardPanel.add(label);
            }
        }

        frame.add(chessBoardPanel, BorderLayout.CENTER);
    }

    private void setupImageCache(){
        ImageIcon icon = new ImageIcon("Images/BlackBishop.png");
        ImageIcon scaledIcon = scaleIcon(icon);
        imageCache.put("Images/BlackBishop.png", scaledIcon);

        icon = new ImageIcon("Images/BlackKing.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/BlackKing.png", scaledIcon);

        icon = new ImageIcon("Images/BlackKnight.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/BlackKnight.png", scaledIcon);

        icon = new ImageIcon("Images/BlackPawn.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/BlackPawn.png", scaledIcon);

        icon = new ImageIcon("Images/BlackQueen.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/BlackQueen.png", scaledIcon);

        icon = new ImageIcon("Images/BlackRook.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/BlackRook.png", scaledIcon);

        icon = new ImageIcon("Images/WhiteBishop.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/WhiteBishop.png", scaledIcon);

        icon = new ImageIcon("Images/WhiteKing.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/WhiteKing.png", scaledIcon);

        icon = new ImageIcon("Images/WhiteKnight.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/WhiteKnight.png", scaledIcon);

        icon = new ImageIcon("Images/WhitePawn.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/WhitePawn.png", scaledIcon);

        icon = new ImageIcon("Images/WhiteQueen.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/WhiteQueen.png", scaledIcon);

        icon = new ImageIcon("Images/WhiteRook.png");
        scaledIcon = scaleIcon(icon);
        imageCache.put("Images/WhiteRook.png", scaledIcon);
    }

    private ImageIcon scaleIcon(ImageIcon icon){
        if (icon != null) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(
                    boardLabels[0][0].getWidth(),
                    boardLabels[0][0].getHeight(),
                    Image.SCALE_SMOOTH
            );
            icon = new ImageIcon(scaledImage);
        }
        return icon;
    }

    private void initializeOtherComponents() {
        // Inicjalizacja i konfiguracja innych komponentów interfejsu użytkownika
    }

//    public void updateBoard(Map<String, ChessPiece> newBoard) {
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                String position = getPosition(row, col);
//                ChessPiece piece = newBoard.get(position);
//                ImageIcon icon = (piece != null) ? new ImageIcon(piece.getImagePath()) : null;
//
//                if (icon != null) {
//                    // Dostosuj rozmiar obrazka do wielkości pola
//                    Image image = icon.getImage();
//                    Image scaledImage = image.getScaledInstance(
//                            boardLabels[row][col].getWidth(),
//                            boardLabels[row][col].getHeight(),
//                            Image.SCALE_SMOOTH
//                    );
//                    icon = new ImageIcon(scaledImage);
//                }
//
//                boardLabels[row][col].setIcon(icon);
//                //String displayText = (piece != null) ? piece.getType() : "";
//                //boardLabels[row][col].setText(displayText);
//
//                if (row == selectedRow && col == selectedCol) {
//                    boardLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
//                } else {
//                    boardLabels[row][col].setBorder(null);
//                }
//            }
//        }
//    }

    public void updateBoard(Map<String, ChessPiece> newBoard) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String position = getPosition(row, col);
                ChessPiece piece = newBoard.get(position);
//                ImageIcon icon = (piece != null) ? getImageIcon(piece.getImagePath()) : null;
//                if (icon != null) {
//                    Image image = icon.getImage();
//                    Image scaledImage = image.getScaledInstance(
//                            boardLabels[row][col].getWidth(),
//                            boardLabels[row][col].getHeight(),
//                            Image.SCALE_SMOOTH
//                    );
//                    icon = new ImageIcon(scaledImage);
//                }
                ImageIcon icon = null;
                if(piece != null){
                    icon = imageCache.get(piece.getImagePath());
                }

                boardLabels[row][col].setIcon(icon);

                if (position.equals(controller.getModel().getCheckedKingPosition())) {
                    boardLabels[row][col].setBackground(Color.PINK);
                } else {
                    boardLabels[row][col].setBackground(getSquareColor(row, col));
                }


                if (row == selectedRow && col == selectedCol) {
                    boardLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                } else {
                    boardLabels[row][col].setBorder(null);
                }
            }
        }
    }

    void deselect(){
        selectedRow = -1;
        selectedCol = -1;
    }


    private Color getSquareColor(int row, int col) {
        return (row + col) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private String getPosition(int row, int col) {
        char file = (char) ('A' + col);
        int rank = 8 - row;
        return String.valueOf(file) + rank;
    }


    private class ChessBoardMouseListener extends java.awt.event.MouseAdapter {
        private int row;
        private int col;

        public ChessBoardMouseListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (selectedRow == row && selectedCol == col) {
                // Odznacz pole po ponownym kliknięciu
                controller.handleBoardButtonClick(row,col,true);
                selectedRow = -1;
                selectedCol = -1;
            } else {
                controller.handleBoardButtonClick(row, col,false);
                selectedRow = row;
                selectedCol = col;
            }
            updateBoard(controller.getModel().getBoard());
        }
    }
}