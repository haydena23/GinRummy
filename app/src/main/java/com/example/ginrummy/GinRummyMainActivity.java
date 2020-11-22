package com.example.ginrummy;

import com.example.R;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.gameConfiguration.GameConfig;
import com.example.game.GameFramework.gameConfiguration.GamePlayerType;

import java.util.ArrayList;

public class GinRummyMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 8585;

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

        // Create a game configuration class for SlapJack
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "GinRummy", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 2);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Guest", "", 1);

        //done!
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {
        return new GinRummyLocalGame();
    }
}
