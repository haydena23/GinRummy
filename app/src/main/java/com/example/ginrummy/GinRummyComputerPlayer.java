package com.example.ginrummy;

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;

public class GinRummyComputerPlayer extends GameComputerPlayer {
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public GinRummyComputerPlayer(String name) {
        super(name);
    }

    public GinRummyComputerPlayer(String name, boolean isSmart) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
    }
}
