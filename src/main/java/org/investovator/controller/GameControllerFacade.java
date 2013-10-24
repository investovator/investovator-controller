package org.investovator.controller;


import org.investovator.controller.agentgaming.AgentGameFacade;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.controller.utils.enums.GameStates;
import org.investovator.controller.utils.events.GameEventListener;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class GameControllerFacade {

    private AgentGameFacade agentGameFacade;
    private static GameModes currentGameMode = null;
    private static GameStates currentGameState = GameStates.NEW;


    private static GameControllerFacade instance;

    public static GameControllerFacade getInstance() {
        if(instance == null){
            synchronized(GameControllerFacade.class){
                if(instance == null)
                    instance = new GameControllerFacade();
            }
        }
        return instance;
    }

    private GameControllerFacade(){
        //agentGameFacade = new AgentGameFacade();
        //agentGameFacade.setupAgentGame();
    }


    public void registerListener(GameEventListener listener){
        //agentGameFacade.registerListener(listener);
    }

    public GameModes getCurrentGameMode(){
        return currentGameMode;
    }

    public GameStates getCurrentGameState(){
        return  currentGameState;
    }

}
