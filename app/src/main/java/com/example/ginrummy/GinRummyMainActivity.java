/**
 * GinRummyMainActivity.java - The main activity for the program, that starts up the program
 *                             and creates the players and the config for the game.
 *                             Adds the players, and creates the local game
 *
 * Status of the game: As of this commit, the game runs one round.
 * Therefore, an actual, full run of a Gin Rummy game can't be done.
 *
 * The bugs are as follows:
 * Vertical Screen layout, playerType Name described below.
 * For the playerTypes.add(new GamePlayerType(_______)) I can't
 *  * use capital letters because of a resource error. This is an unknown bug
 *  * that we couldn't figure out
 *
 * The following are what we are missing :
 * Quit/Reset Button, Multiple Rounds, Smart AI,
 * A message on the ScoreView, highlighting cards when grouped
 * and an instruction button.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

import com.example.R;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.gameConfiguration.GameConfig;
import com.example.game.GameFramework.gameConfiguration.GamePlayerType;

import java.util.ArrayList;

public class GinRummyMainActivity extends GameMainActivity {

    //Instance variable/s
    public static final int PORT_NUMBER = 8585;

    /**
     * Method extended from the game framework. Creates the default configuration for the game
     * @return The default config
     */
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        // This method came from the SlapJack code, but slightly altered for our game.
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("human player") {
            public GamePlayer createPlayer(String name) {
                return new GinRummyHumanPlayer(name, R.layout.activity_main);
            }});
        playerTypes.add(new GamePlayerType("computer player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new GinRummyComputerPlayer(name, false);
            }
        });
        /*playerTypes.add(new GamePlayerType("computer player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new GinRummyComputerPlayer(name, true);
            }
        });*/

        // Create a game configuration class for GinRummy
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2,
                "GinRummy", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 2);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Guest", "", 1);

        //done!
        return defaultConfig;
    }

    /**
     * Method to create a new GinRummyLocalGame to start the game     *
     * @return A GinRummyLocalGame
     */
    @Override
    public LocalGame createLocalGame() {
        return new GinRummyLocalGame();
    }
}
