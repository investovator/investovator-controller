package org.investovator.controller.utils.enums;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public enum GameModes{
    AGENT_GAME,
    NN_GAME,
    PAYBACK_ENG;


    public static String getClassName(GameModes type){

        switch (type) {
            case AGENT_GAME:
                return "org.investovator.controller.agentgaming.AgentGameFacade";
            case NN_GAME:
                return "org.investovator.controller.nngaming.NNGameFacade";
            case PAYBACK_ENG:
                return "org.investovator.controller.dataplaybackengine.DataPlaybackGameFacade";
            default:return null;
        }
    }
}
