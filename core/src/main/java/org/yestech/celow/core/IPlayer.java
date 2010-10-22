package org.yestech.celow.core;

/**
 *
 *
 */
public interface IPlayer {
    PlayerTypeEnum getType();

    void setType(PlayerTypeEnum type);
    
    String getId();

    void setId(String id);

    void setWager(double wager);

    double getWager();

    void setBalance(double balance);

    double getBalance();

    int getPoint();

    void setPoint(int countPoint);

    boolean hasPoint();

    int getDice1();

    void setDice1(int dice1);

    int getDice2();

    void setDice2(int dice2);

    int getDice3();

    void setDice3(int dice3);

    ResultsEnum getResult();

    void setResult(ResultsEnum result);

    void setScreenName(String screenName);

    String getScreenName();

    void clearPoint();

    boolean isComputer();
}
