package se.plweb.memory.gui;

import se.plweb.memory.domain.*;
import se.plweb.memory.gui.GameObjectGuiImpl.GUIState;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

import static se.plweb.memory.domain.DimensionToSizeConverter.convert;
import static se.plweb.memory.domain.PointToPositionConverter.convert;

/**
 * @author Peter Lindblom
 */

public class GameBoardGui extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static Logger logger;
    protected final GameBoard gameBoard;
    protected final Timer st = new Timer(30, this);
    private final FocusListener focusListener = new FocusAdapter() {
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            boolean requestFocusInWindow = GameBoardGui.this
                    .requestFocusInWindow();
            logger.fine("focusGained requestFocusInWindow:"
                    + requestFocusInWindow);
        }

        public void focusLost(FocusEvent e) {
            super.focusLost(e);
            boolean requestFocusInWindow = GameBoardGui.this
                    .requestFocusInWindow();
            logger.fine("focusLost requestFocusInWindow:"
                    + requestFocusInWindow);
        }
    };
    protected volatile boolean isPressed = false;
    private Position highlightedObject;
    private final KeyListener keyListener = new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            logger.fine("keyPressed:" + e.getKeyCode());

            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    moveRightIfPossible();
                    break;
                case KeyEvent.VK_LEFT:
                    moveLeftIfPossible();
                    break;
                case KeyEvent.VK_UP:
                    moveUpIfPossible();
                    break;
                case KeyEvent.VK_DOWN:
                    moveDownIfPossible();
                    break;
                case KeyEvent.VK_SPACE:
                    pressIfPossible(highlightedObject);
                    break;
            }
            repaint();
        }
    };
    private final MouseInputListener mouseInputListener = new MouseInputAdapter() {

        public void mouseMoved(MouseEvent e) {
            Position mouse = convert(e.getPoint());
			GameBoardGui.this.highlightedObject = getGameObjectGuiPositionAtPosition(mouse);
            repaint();
        }

        public void mouseEntered(MouseEvent e) {
            GameBoardGui.this.requestFocusInWindow();
        }

        public synchronized void mouseClicked(MouseEvent e) {
            Position mouse = convert(e.getPoint());
            Position gameObject = getGameObjectGuiPositionAtPosition(mouse);
            pressIfPossible(gameObject);
            repaint();
        }
    };

    public GameBoardGui() {
        logger = Logger.getLogger(this.getClass().getName());
        gameBoard = new GameBoardImpl();

        highlightedObject = Position.create(0, 0);
        setPreferredSize(new Dimension(600, 400));
        setFocusable(true);
        setEnabled(true);

        SwingUtilities.invokeLater(() -> {
            logger.fine("InvokeLater requestFocusInWindow:"
                    + GameBoardGui.this.requestFocusInWindow());
            GameBoardGui.this.addMouseListener(mouseInputListener);
            GameBoardGui.this.addMouseMotionListener(mouseInputListener);
        });

    }

    protected void paintGameBoard(Graphics g, Size gameObjectSize) {

        for (int y = 0; y < gameBoard.getXSize(); y++) {
            for (int x = 0; x < gameBoard.getXSize(); x++) {
                GUIState state;
                if (highlightedObject.equals(Position.create(x, y))) {
                    state = GUIState.MOUSE_OVER;
                } else {
                    state = GUIState.NORMAL;
                }

                try {
                    ((GameObjectGui) gameBoard
                            .getGameObject(Position.create(x, y))).draw(g,
                            gameObjectSize, state);
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }

    }

    protected Size calculateGameObjectSize(Size gameBoardGuiSize, int xSize,
                                           int ySize) {
        try {
            return Size.create((gameBoardGuiSize.getWidth() / xSize),
                    (gameBoardGuiSize.getHeight() / ySize));
        } catch (ArithmeticException e) {
            return Size.create(0, 0);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGameBoard(g, calculateGameObjectSize(convert(this.getSize()),
                gameBoard.getXSize(), gameBoard.getYSize()));
    }

    private void moveRightIfPossible() {
        if (highlightedObject.getXPos() < (gameBoard.getXSize() - 1)) {
            highlightedObject.moveRight();
        }
    }

    private void moveLeftIfPossible() {
        if (highlightedObject.getXPos() > 0) {
            highlightedObject.moveLeft();
        }
    }

    private void moveUpIfPossible() {
        if (highlightedObject.getYPos() > 0) {
            highlightedObject.moveUp();
        }
    }

    private void moveDownIfPossible() {
        if (highlightedObject.getYPos() < (gameBoard.getYSize() - 1)) {
            highlightedObject.moveDown();
        }
    }

    private synchronized void pressIfPossible(Position gameObjectPosition) {
        pair(gameBoard.getGameObject(gameObjectPosition));
    }

    private synchronized Position getGameObjectGuiPositionAtPosition(
            Position position) {
        for (int x = 0; x < gameBoard.getXSize(); x++) {
            for (int y = 0; y < gameBoard.getYSize(); y++) {
                if (((GameObjectGui) gameBoard
                        .getGameObject(Position.create(x, y)))
                        .isPositionInsideOfGameObject(position)) {
                    return Position.create(x, y);
                }
            }
        }
        return Position.create(-1, -1);
    }

    public synchronized void actionPerformed(ActionEvent obj) {
        this.requestFocusInWindow();

        if (obj.getSource() == st) {
            logger.fine("Timer");
            gameBoard.clearPressedObjects();
            this.enableObjects();
            st.stop();
            this.isPressed = false;
            this.doAfterTimerEvent();
        }
    }

    protected synchronized void doAfterTimerEvent() {
        repaint();
    }

    public synchronized int getNumberOfMatchedPairs() {
        return gameBoard.getNumberOfMatchedPairs();
    }

    public synchronized void pair(GameObject gameObject) {
        if (!isPressed) {
            gameBoard.pressObject(gameObject);

            if (gameBoard.noPressedObjectIsCorrect() && gameBoard.isAMatch()) {
                this.isPressed = true;
                this.disableObjects();
                st.setInitialDelay(1000);
                st.start();
            } else if (gameBoard.noPressedObjectIsCorrect() && !gameBoard.isAMatch()) {
                this.isPressed = true;
                this.disableObjects();
                st.setInitialDelay(1000);
                st.start();
            }
        }
    }

    public synchronized int getTotalNumberOfPairs() {
        return gameBoard.getTotalNumberOfPairs();
    }

    public synchronized int getTotalNumberOfAttempts() {
        return gameBoard.getTotalNumberOfAttempts();
    }

    public void makeGameBoard(int xSize, int ySize) {
        int value = 1;
        int i = 1;
        int valueDirectionCount = 0;
        gameBoard.newEmptyGameBoard(xSize, ySize);
        GuiHelper guiHelper = GuiHelper.create(gameBoard.getTotalNumberOfPairs());
        // GUI settings
        this.setLayout(new GridLayout(xSize, ySize));

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                Position tmpPosition = Position.create(x, y);
                GameObjectGui gameObjectGui = new GameObjectGuiImpl(value, tmpPosition, guiHelper);
                gameObjectGui.setState(GameObjectState.PRESSED_STATE);
                gameBoard.setGameObject(gameObjectGui);

                if (i % 2 == 0) {
                    value++;
                    valueDirectionCount++;
                }
                i++;


                if (valueDirectionCount == 4) {
                    valueDirectionCount = 0;
                }
            }
        }
    }

    public void startGame() {
        gameBoard.startGame();
        boolean requestFocusInWindow = this.requestFocusInWindow();
        logger.fine("startGame requestFocusInWindow:" + requestFocusInWindow);
        if (this.getKeyListeners().length < 1) {
            logger.fine("this.addKeyListener");
            this.addKeyListener(keyListener);
        }

        if (this.getFocusListeners().length < 1) {
            logger.fine("addFocusListener");
            this.addFocusListener(focusListener);
        }
    }

    public void stopGame() {
        st.stop();
        gameBoard.stopGame();
        logger.fine("stopGame");
    }

    protected synchronized void disableObjects() {
        for (int x = 0; x < gameBoard.getXSize(); x++) {
            for (int y = 0; y < gameBoard.getYSize(); y++) {
                Position tmpPosition = Position.create(x, y);

                GameObject tmpGameObject = gameBoard.getGameObject(tmpPosition);
                if (tmpGameObject.getState() == GameObjectState.NORMAL_STATE) {
                    tmpGameObject.setState(GameObjectState.DISABLED_STATE);
                    gameBoard.setGameObject(tmpGameObject);
                }
            }
        }
    }

    protected synchronized void enableObjects() {
        for (int x = 0; x < gameBoard.getXSize(); x++) {
            for (int y = 0; y < gameBoard.getYSize(); y++) {
                Position tmpPosition = Position.create(x, y);

                GameObject tmpGameObject = gameBoard.getGameObject(tmpPosition);
                if (tmpGameObject.getState() == GameObjectState.DISABLED_STATE) {
                    tmpGameObject.setState(GameObjectState.NORMAL_STATE);
                    gameBoard.setGameObject(tmpGameObject);
                }
            }
        }
    }
}
