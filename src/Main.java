import java.io.*;
import java.util.ArrayList;

public class Main implements Runnable {
    private int index;
    private static char trumpCard;
    private static ArrayList<String> gamesList = new ArrayList<>();

    private static ArrayList<Card> getCardsArray(String cards) {
        String[] cardsStrings = cards.split(" ");
        ArrayList<Card> cardsInHand = new ArrayList<>();
        for (String card: cardsStrings) {
            if (card.length() == 2) {
                cardsInHand.add(new Card(card));
            }
        }
        return cardsInHand;
    }

    private static void getGamesDetails() {
        int index = 0;
        String line;

        try (
                FileReader file = new FileReader("example-data1.txt");
                BufferedReader br = new BufferedReader(file)
        ) {
            while ((line = br.readLine()) != null) {
                if (index == 0) {
                    trumpCard = line.charAt(0);
                } else {
                    gamesList.add(line);
                }
                index += 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main(int index) {
        this.index = index;
    }

    public void run() {
        String playersCardsString = gamesList.get(index);
        String[] playersCards = playersCardsString.split("[|]");

        Game game = new Game(index + 1);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");

        p1.setCardsInHand(getCardsArray(playersCards[0]));
        p2.setCardsInHand(getCardsArray(playersCards[1]));

        game.setPlayer1(p1);
        game.setPlayer2(p2);
        game.setOffence(p1);
        game.setDefense(p2);
        game.setTrumpCard(trumpCard);

        game.println();

        game.play();
    }

    public static void main(String[] args) {
        getGamesDetails();
        for (int i = 0, j = gamesList.size(); i < j; i += 1) {
            (new Thread(new Main(i))).start();
        }
    }

}
