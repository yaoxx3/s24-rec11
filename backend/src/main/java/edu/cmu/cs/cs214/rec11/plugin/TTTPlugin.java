package edu.cmu.cs.cs214.rec11.plugin;


import edu.cmu.cs.cs214.rec11.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec11.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec11.games.TicTacToe;

public class TTTPlugin implements GamePlugin<String> {
    private static final int SIZE = 3;

    private static final String GAME_NAME = "TicTacToe";
    private static final String WON_MSG = "Player %s won!";
    private static final String TIED_MSG = "Players X and O tied.";

    private GameFramework framework;
    private TicTacToe game;
    private boolean moved;

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public int getGridWidth() {
        return SIZE;
    }

    @Override
    public int getGridHeight() {
        return SIZE;
    }

    @Override
    public void onRegister(GameFramework f) {
        framework = f;
    }

    @Override
    public void onNewGame() {
        game = new TicTacToe();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                framework.setSquare(i, j, "");
            }
        }
        framework.setFooterText(String.format("%s's Turn", game.currentPlayer().toString()));
    }

    @Override
    public void onNewMove() {
        moved = false;
    }

    @Override
    public boolean isMoveValid(int x, int y) {
        return game.isValidPlay(x, y);
    }

    @Override
    public boolean isMoveOver() {
        return moved;
    }

    @Override
    public void onMovePlayed(int x, int y) {
        framework.setSquare(x, y, game.currentPlayer().toString());
        game.play(x, y);
        framework.setFooterText(String.format("%s's Turn", game.currentPlayer().toString()));
        moved = true;
    }

    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    @Override
    public String getGameOverMessage() {
        if (game.winner() != null) {
            return String.format(WON_MSG, game.winner().toString());
        }
        return TIED_MSG;
    }

    @Override
    public void onGameClosed() {
    }

    @Override
    public String currentPlayer() {
        return game.currentPlayer().toString();
    }


}
