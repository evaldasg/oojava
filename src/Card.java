import java.util.Arrays;

public class Card extends AbstractCard implements Rankable, Comparable {
    private int rank;
    private char suite;

    public Card(String card) {
        this.setRank(card);
        this.setSuite(card);
    }

    public String printCard() {
        String r = ranks[this.rank];
        return this.suite + r;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public char getSuite() {
        return this.suite;
    }

    @Override
    public void setRank(String card) {
        this.rank = Arrays.asList(ranks).indexOf(String.valueOf(card.charAt(1)));
    }

    @Override
    public void setSuite(String card) {
        this.suite = card.charAt(0);
    }

    @Override
    public int compareTo(Object obj) {
        int compareRank = ((Card)obj).getRank();
        return this.rank - compareRank;
    }
}
