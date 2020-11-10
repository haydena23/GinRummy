package com.example.ginrummy;

import org.junit.Test;

import static org.junit.Assert.*;

public class RummyGameStateTest {

    @Test
    public void drawDraw() {
        RummyGameState rummyGameState = new RummyGameState();
        int i = 0;
        int j = 0;
        for (Card c : rummyGameState.getDrawPile()) {
            if (c == null) {
                i++;
            }
        }
        assertTrue(rummyGameState.drawDraw() instanceof Card);
        for (Card c : rummyGameState.getDrawPile()) {
            if (c == null) {
                j++;
            }
        }
        assertTrue(j-1==i);
        assertTrue(rummyGameState.getCurrentStage() == "discardStage");
    }

    @Test
    public void drawDiscard() {
        RummyGameState rummyGameState = new RummyGameState();
        assertTrue(rummyGameState.drawDiscard() instanceof Card);
        //rummyGameState.drawDiscard();
        assertTrue(rummyGameState.getDiscardedCard() == null);
        assertTrue(rummyGameState.getCurrentStage() == "discardStage");
    }

    @Test
    public void createStartingDeck() {
        RummyGameState rummyGameState = new RummyGameState();
        Card[] startingDeck = new Card[0];
        rummyGameState.setStartingDeck(startingDeck);//empty the startingDeck
        assertTrue(rummyGameState.createStartingDeck() instanceof Card[]);

        rummyGameState.setStartingDeck(rummyGameState.createStartingDeck());
        int x = 0;
        for (Card c : rummyGameState.getStartingDeck()) {
            x++;
        }
        assertTrue(x==52);
    }

    @Test
    public void createPlayerHand() {
        RummyGameState rummyGameState = new RummyGameState(); // 32 - 1 drawpile
        //Card[] player1Hand = new Card[0];
        //rummyGameState.setPlayer1Cards(player1Hand);//empty the player1 cards
        assertTrue(rummyGameState.getPlayer1Cards() instanceof Card[]); // checks if return card[] 31-10 = 21

        //rummyGameState.setPlayer1Cards(rummyGameState.createPlayerHand()); //21-10=11
        int x = 0;
        for (Card c : rummyGameState.getPlayer1Cards()) {
            if (c != null) {
                x++;
            }
        }
        assertEquals(x, 10); // Checks that player 1 has 10 cards.
        int i = 0;
        for ( Card c : rummyGameState.getDrawPile()) {
            if (c!= null) {
                i++;
            }
        }
        assertEquals(i, 31); // Checks that after taking 10 cards (twice for 2 hands) and the draw pile, the drawpile has 31 cards.
    }

    @Test
    public void discardCard() {
        RummyGameState rummyGameState = new RummyGameState();
            assertTrue(rummyGameState.getDiscardedCard() instanceof Card);
            Card trash = new Card(50, "trash");
            rummyGameState.setDiscardedCard(trash);
            Card[] player1Cards = rummyGameState.getPlayer1Cards();
            rummyGameState.discardCard(player1Cards, 3);
            assertEquals(rummyGameState.getDiscardedCard().getNumber(), 50);
    }

    @Test
    public void createDrawPile() {
        RummyGameState rummyGameState = new RummyGameState();
        assertTrue(rummyGameState.getDrawPile() instanceof Card[]);
        rummyGameState.setDrawPile(null);
        rummyGameState.setStartingDeck(rummyGameState.createStartingDeck());
        rummyGameState.setDrawPile(rummyGameState.createDrawPile());
        int x = 0;
        for (Card c : rummyGameState.getDrawPile()) {
            if(c != null) {
                x++;
            }
        }
        assertEquals(x, 32);
    }
}