import java.util.ArrayList;
import java.util.Iterator;

public class Game implements Playable {
    private int gameId;
    private char trumpCard;
    private Player player1;
    private Player player2;
    private Player offense;
    private Player defense;
    private Player nextPlayer;
    private ArrayList<Card> cardsOnTable = new ArrayList<Card>();
    private ArrayList<Card> cardsOnTableDefended = new ArrayList<Card>();

    /* constructors */
    public Game() {
        this(1);
    }

    public Game(int id) {
        this.gameId = id;
    }
    /* end of constructors */

    public void play() {
        // first starts offense player
        setNextPlayer();

        while (this.player1.getCardsInHand().size() > 0 && this.player2.getCardsInHand().size() > 0) {
            if (this.nextPlayer == this.offense) {
                offensePlays();
            } else {
                defensePlays();
            }

            printCardsOnTable();
        }

        announceWinner();
    }

    private void announceWinner() {
        Player winner = null;
        String message = "";

        if (this.player1.getCardsInHand().isEmpty()) {
            winner = this.player1;
        } else if (this.player2.getCardsInHand().isEmpty()) {
            winner = this.player2;
        }

        if (winner == null) {
            message = "There is no winner in this game :(";
        } else {
            message += "The winner of the Game_" + this.gameId + " is '" + winner.getName() + "'!!!";
        }
        System.out.println(message);
    }

    private void setNextPlayer() {
        if (this.nextPlayer == null) {
            this.nextPlayer = this.offense;
        }
    }

    private void setNextPlayer(Player player) {
        this.nextPlayer = player;
    }

    private void defensePlays() {
        if (cardsOnTable.size() > 0) {
            Card playCard,
                 offenseCard = cardsOnTable.get(0);

            ArrayList<Card> cards = this.defense.getCardsInHand();
            ArrayList<Card> cardsOffenseRank = Card.getSameRankCards(cards, offenseCard.getRank());

            if (cardsOffenseRank.size() > 0) {
                playCard = cardsOffenseRank.get(0);
                this.defense.removeCardFromHand(playCard);
                putCardOnTable(playCard);
                switchPlayersRoles(); // Now defender became offense and hands back to defend for offense
                setNextPlayer(this.defense); // Because here player became offense
            } else {
                Iterator<Card> iterator = this.cardsOnTable.iterator();

                while (iterator.hasNext()) {
                    Card card = iterator.next();
                    ArrayList<Card> sameSuiteCards = Card.getSameSuiteCards(this.defense.getCardsInHand(), card.getSuite());
                    ArrayList<Card> defenseTrumpCards = Card.getSameSuiteCards(this.defense.getCardsInHand(), this.trumpCard);

                    playCard = Card.getHigherRankCard(sameSuiteCards, card);

                    if (playCard == null) {
                        if (defenseTrumpCards.size() > 0) {
                            playCard = Card.getSmallestCard(defenseTrumpCards);
                            this.defense.removeCardFromHand(playCard);
                            iterator.remove();
                            putDefendedCards(card, playCard);
                        } else {
                            takeAllCardsFromTable(this.defense);
                            clearTableCards();
                            break;
                        }
                    } else {
                        this.defense.removeCardFromHand(playCard);
                        iterator.remove();
                        putDefendedCards(card, playCard);
                    }
                }
                setNextPlayer(this.offense);
            }

        }
    }

    private void offensePlays() {
        if (cardsOnTable.size() == 0 && cardsOnTableDefended.size() == 0) {
            ArrayList<Card> playingCards = Card.getPlayingCards(this.offense.getCardsInHand(), this.trumpCard);
            Card playCard = Card.getSmallestCard(playingCards);

            this.offense.removeCardFromHand(playCard);
            putCardOnTable(playCard);
            setNextPlayer(this.defense);
        } else {
            ArrayList<Integer> availableCardsRanks = new ArrayList<Integer>();
            ArrayList<Card> availableCards = new ArrayList<>();

            for (Card card: allCardsOnTable()) {
                availableCardsRanks.add(card.getRank());
            }

            for (Integer rank: availableCardsRanks) {
                availableCards.addAll(Card.getSameRankCards(this.offense.getCardsInHand(), rank));
            }

            Card playCard = null;
            if (availableCards.size() > 0) {
                playCard = Card.getSmallestCard(availableCards);
            }

            if (playCard != null) {
                ArrayList<Card> playableCards = Card.getSameRankCards(availableCards, playCard.getRank());
                if (playableCards.size() > 1) {
                    putCardsOnTable(playableCards);
                    this.offense.removeCardsFromHand(playableCards);
                } else {
                    putCardOnTable(playCard);
                    this.offense.removeCardFromHand(playCard);
                }
                setNextPlayer(this.defense);
            } else {
                this.cardsOnTableDefended.clear();
                setNextPlayer(this.defense);
                switchPlayersRoles(); // Now defender became offense and hands back to defend for offense
            }
        }
    }

    private void clearTableCards() {
        this.cardsOnTable.clear();
        this.cardsOnTableDefended.clear();
    }

    private void putDefendedCards(Card offenseCard, Card defenseCard) {
        this.cardsOnTableDefended.add(offenseCard);
        this.cardsOnTableDefended.add(defenseCard);
    }

    private void takeAllCardsFromTable(Player player) {
        player.addCardsToHand(this.cardsOnTable);
        player.addCardsToHand(this.cardsOnTableDefended);
    }

    private void switchPlayersRoles() {
        this.defense = (this.defense == this.player1) ? this.player2 : this.player1;
        this.offense = (this.offense == this.player1) ? this.player2 : this.player1;
    }

    private void printCardsOnTable() {
        System.out.println("Cards on Table: " + Card.cardsAsString(this.cardsOnTable));
        System.out.println("Cards Defended: " + Card.cardsAsString(this.cardsOnTableDefended));
        this.player1.printCardsInHand();
        this.player2.printCardsInHand();
        System.out.println("Offence: " + this.offense.getName());
        System.out.println("Defense: " + this.defense.getName());
        System.out.println("NextPlayer: " + this.nextPlayer.getName());
    }

    private ArrayList<Card> allCardsOnTable() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.addAll(this.cardsOnTable);
        list.addAll(this.cardsOnTableDefended);
        return list;
    }

    // For more than 1 card
    private void putCardsOnTable(ArrayList<Card> cards) {
        this.cardsOnTable.addAll(cards);
    }

    // For only 1 card
    private void putCardOnTable(Card card) {
        this.cardsOnTable.add(card);
    }

    public void println() {
        char trump = this.trumpCard;
        System.out.println("Game: " + this.gameId + ". Trump: " + (trump == 0 ? "not set yet." : trump));
        System.out.println("Players: " + this.player1.getName() + " & " + this.player2.getName());
    }

    public Player getOffense() {
        return this.offense;
    }

    /* setters */
    public void setGameId(int id) {
        this.gameId = id;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public void setTrumpCard(char cardSuite) {
        this.trumpCard = cardSuite;
    }

    public void setOffence(Player player) {
        this.offense = player;
    }

    public void setDefense(Player player) {
        this.defense = player;
    }
    /* end of setters */

}
