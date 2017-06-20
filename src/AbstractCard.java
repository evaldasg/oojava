import java.util.ArrayList;
import java.util.Collections;

public abstract class AbstractCard {
    protected String[] ranks  = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
    // diamonds (♦), clubs (♣), hearts (♥), spades (♠)
    protected String[] suites = { "H", "D", "C", "S" };

    protected abstract String printCard();

    public static ArrayList<Card> getSameRankCards(ArrayList<Card> cards, int rank) {
        ArrayList<Card> list = new ArrayList<Card>();
        for (Card card: cards) {
            if (card.getRank() == rank) {
                list.add(card);
            }
        }
        return list;
    }

    public static ArrayList<Card> getSameSuiteCards(ArrayList<Card> cards, char suite) {
        ArrayList<Card> list = new ArrayList<Card>();
        for (Card card: cards) {
            if (card.getSuite() == suite) {
                list.add(card);
            }
        }
        return list;
    }

    public static ArrayList<Card> getCardsWithoutTrump(ArrayList<Card> cards, char trump) {
        ArrayList<Card> list = new ArrayList<Card>();
        for (Card card: cards) {
            if (card.getSuite() != trump) {
                list.add(card);
            }
        }
        return list;
    }

    public static Card getSmallestCard(ArrayList<Card> cards) {
        Card smallest = cards.get(0);

        for (Card card: cards) {
            if (card.getRank() < smallest.getRank()) {
                smallest = card;
            }
        }
        return smallest;
    }

    public static ArrayList<Card> getPlayingCards(ArrayList<Card> cards, char tableCard) {
        ArrayList<Card> cardsWithoutTrump = Card.getCardsWithoutTrump(cards, tableCard);
        ArrayList<Card> trumpCards = Card.getSameSuiteCards(cards, tableCard);
        return cardsWithoutTrump.size() > 0 ? cardsWithoutTrump : trumpCards;
    }

    public static String cardsAsString(ArrayList<Card> cards) {
        String text = "";

        for (Card card: cards) {
            text += (text.length() == 0 ? "" : ", ") + card.printCard();
        }

        return text;
    }

    public static Card getHigherRankCard(ArrayList<Card> sameSuiteCards, Card card) {
        Card higherRankCard = null;
        Collections.sort(sameSuiteCards);

        for (Card sameSuiteCard: sameSuiteCards) {
            if (sameSuiteCard.getRank() > card.getRank()) {
                higherRankCard = sameSuiteCard;
                break;
            }
        }

        return higherRankCard;
    }
}
