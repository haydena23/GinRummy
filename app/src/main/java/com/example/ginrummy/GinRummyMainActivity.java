/**
 * GinRummyMainActivity.java - The main activity for the program, that starts up the program
 *                             and creates the players and the config for the game.
 *                             Adds the players, and creates the local game
 *
 * Status of the game: As of this commit, the game runs one round.
 * In this round, the player can do all necessary actions to complete
 * this round that is in the original Gin Rummy, including
 * drawing from discard, discarding cards, grouping cards, knocking,
 * and finally ginning.
 *
 * The bugs are as follows: Grouping the same card twice,
 * and attempting to group a card with itself.
 *
 * The following are what we
 * need to implement : Correct values for each card, the quit button,
 * multiple rounds, a Smart AI, and a vertical screen layout.
 *
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

    //Instance variable
    public static final int PORT_NUMBER = 8585;

    /**
     * Method extended from the game framework. Creates the default configuration for the game
     * @return The default config
     */
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("human player (player1)") {
            public GamePlayer createPlayer(String name) {
                return new GinRummyHumanPlayer(name, R.layout.activity_main);
            }});
        playerTypes.add(new GamePlayerType("computer player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new GinRummyComputerPlayer(name, false);
            }
        });
        //Que?
        playerTypes.add(new GamePlayerType("computer player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new GinRummyComputerPlayer(name, true);
            }
        });

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
