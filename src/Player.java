import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cardsInHand;

    /* constructors */
    public Player() {
        this("Player1");
    }

    public Player(String name) {
        this.name = name;
    }
    /* end of constructors */

    public void printCardsInHand() {
        System.out.println("Cards of " + this.name + ": " + Card.cardsAsString(this.cardsInHand));
    }

    // For only 1 card
    public boolean removeCardFromHand(Card card) {
        return this.cardsInHand.remove(card);
    }

    // For more than 1 card
    public boolean removeCardsFromHand(ArrayList<Card> cards) {
        return this.cardsInHand.removeAll(cards);
    }

    public void addCardsToHand(ArrayList<Card> cards) {
        this.cardsInHand.addAll(cards);
    }

    /* getters */
    public String getName() {
        return this.name;
    }

    public ArrayList<Card> getCardsInHand() {
        return this.cardsInHand;
    }
    /* end of getters */

    /* setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setCardsInHand(ArrayList<Card> cards) {
        this.cardsInHand = cards;
    }
    /* end of setters */
}
