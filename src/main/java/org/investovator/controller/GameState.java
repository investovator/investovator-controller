package org.investovator.controller;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class GameState {

    public static GameModes currentGameMode;
    public static GameStates currentGameState;

}

enum GameModes{
    AGENT_GAME,
    NN_GAME,
    PAYBACK_ENG
}

enum GameStates{
    NEW,
    CONFIGURED,
    RUNNING
}
