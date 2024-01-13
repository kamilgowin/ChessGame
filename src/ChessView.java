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

            initializeChessBoardPanel();
            initializeOtherComponents();

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
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
                ImageIcon icon = (piece != null) ? getImageIcon(piece.getImagePath()) : null;

                if (icon != null) {
                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(
                            boardLabels[row][col].getWidth(),
                            boardLabels[row][col].getHeight(),
                            Image.SCALE_SMOOTH
                    );
                    icon = new ImageIcon(scaledImage);
                }

                boardLabels[row][col].setIcon(icon);

                if (row == selectedRow && col == selectedCol) {
                    boardLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                } else {
                    boardLabels[row][col].setBorder(null);
                }
            }
        }
    }

    private ImageIcon getImageIcon(String imagePath) {
        if (imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        }

        ImageIcon icon = null;

        try {
            icon = new ImageIcon(imagePath);
            imageCache.put(imagePath, icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return icon;
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

    public void deselect(){
        selectedCol = -1;
        selectedRow = -1;
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
                selectedRow = -1;
                selectedCol = -1;
            } else {
                controller.handleBoardButtonClick(row, col);
                selectedRow = row;
                selectedCol = col;
            }
            updateBoard(controller.getModel().getBoard());
        }
    }
}