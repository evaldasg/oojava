interface Rankable {
    // Suites in ascending order: Hearts, Diamonds, Clubs, Spade
    int getRank();
    char getSuite();
    void setRank(String card);
    void setSuite(String card);
}