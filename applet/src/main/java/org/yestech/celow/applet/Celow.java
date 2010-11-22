/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.celow.applet;

import org.yestech.celow.core.*;

import java.applet.Applet;
import java.awt.*;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * This is the dice games celow.
 */
public class Celow extends Applet implements Runnable, IView {

    private static final String APPLET_VERSION = "Version 1.2";
    public static final String APPLET_INFO = "Celow " + APPLET_VERSION + ", developed by YES Technology, copyright yestech.org 1999-2010";

    private Thread animator;
    private Button btRoll;
    private String bank;
    private String point;
    private TextField tfWager;
    private String title;

    // BEGIN Progress Bar Shit
    private boolean imagesLoaded = false;
    private double imageCount;
    private AMDProgressBarEmbed progress;
    boolean dieDone = false;
    boolean backDone = false;
    // END PROGRESS
    // GRAPHIC IMAGES
    private MediaTracker tracker;
    private MediaTracker backtrack;
    private Image diceImages[];
    private Image dice1;
    private Image dice2;
    private Image dice3;
    private double backCount;
    // END GRAPHIC IMAGES


    private Font bigFont;
    private Font midFont;

    private IGame game;
    public static final String POINT_TITLE = "     ....... Point .......";
    public static final String LOSER_TITLE = "     ....... Loser .......";
    public static final String WINNER_TITLE = "     ....... Winner .......";
    public static final String NOTHING_TITLE = "     ....... Nothing .......";
    public static final String COMPUTER_NOTHING_TITLE = "....... Computer Nothing .......";
    public static final String COMPUTER_WINNER_TITLE = "....... Computer Winner .......";
    public static final String COMPUTER_LOSER_TITLE = "....... Computer Loser .......";
    public static final String PUSH_TITLE = "     ....... PUSH .......";
    public static final String WELCOME_TO_CELOW_TITLE = "..... Welcome To Celow .....";
    public static final String INVALID_WAGER_TITLE = ".... Wager must be greater than 0 ....";
    public static final String LOADING_IMAGES_TITLE = "Loading Images....";
    private boolean invalidWager;
    private int balance;

    //--------------------------------------------------------------------------
    //  A P P L E T   C O M P L I A N T   M E T H O D S 
    //--------------------------------------------------------------------------

    IGame getGame() {
        return game;
    }

    void setGame(IGame game) {
        this.game = game;
    }

    Image[] getDiceImages() {
        return diceImages;
    }

    void setDiceImages(Image[] diceImages) {
        this.diceImages = diceImages;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getPoint() {
        return point;
    }

    void setPoint(String point) {
        this.point = point;
    }

    String getBank() {
        return bank;
    }

    void setBank(String bank) {
        this.bank = bank;
    }

    Button getBtRoll() {
        return btRoll;
    }

    void setBtRoll(Button btRoll) {
        this.btRoll = btRoll;
    }

    TextField getTfWager() {
        return tfWager;
    }

    void setTfWager(TextField tfWager) {
        this.tfWager = tfWager;
    }

    /**
     * @see java.applet.Applet#init()
     */
    final public void init() {
        resize(595, 330);
        setBackground(Color.black);
        setForeground(Color.white);
        setLayout(new BorderLayout());
        bigFont = new Font("Helvetica", Font.PLAIN, 24);
        midFont = new Font("Helvetica", Font.PLAIN, 12);
        Font smallFont = new Font("Helvetica", Font.PLAIN, 10);
        showStatus("Loading Celow..... Please wait......");
        Panel leftPanel = new Panel();
        leftPanel.setLayout(new GridLayout(2, 2));
        Panel southPanel = new Panel();
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        Label lbVersion = new Label(APPLET_VERSION);
        lbVersion.setFont(smallFont);

        btRoll = new Button();
        btRoll.setForeground(Color.black);
        btRoll.setBackground(Color.gray);
        btRoll.setLabel("ROLL");

        Label lbWager = new Label("Wager:");
        lbWager.setFont(midFont);
        tfWager = new TextField(10);
        tfWager.setFont(midFont);
        tfWager.setForeground(Color.BLACK);

        bank = "0.0";
        hidePoint();
        title = WELCOME_TO_CELOW_TITLE;

        southPanel.add("West", lbVersion);
        buttonPanel.add(lbWager);
        buttonPanel.add(tfWager);
        buttonPanel.add(btRoll);
        southPanel.add("East", buttonPanel);

        progress = new AMDProgressBarEmbed(this);
        //
        // dice images placement into array
        //
        diceImages = new Image[6];
        tracker = new MediaTracker(this);
        for (int i = 0; i < 6; i++) {
            Image im = getImage(getCodeBase(), "images/die" + (i + 1) + ".gif");
            tracker.addImage(im, i);
            if (im == null) {
                break;
            }
            diceImages[i] = im;
        }
        //
        // Stuff background image
        //
        backtrack = new MediaTracker(this);
        Image imback = getImage(getCodeBase(), "images/background.gif");
        backtrack.addImage(imback, 0);
        add("South", southPanel);
        game = new Game();
        game.setView(this);
    }

    /**
     * @see java.applet.Applet#start()
     */
    final public void start() {
        if (animator == null) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * @see java.lang.Thread#run()
     */
    final public void run() {
        currentThread().setPriority(Thread.MIN_PRIORITY);
        int tempDie = -1;
        while (getSize().width > 0 && getSize().height > 0 && animator != null) {
            repaint();
            if (imagesLoaded) {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                }
                for (int d = 0; d < diceImages.length; d++) {
                    int dice = tracker.statusID(d, true);
                    if (dice == MediaTracker.COMPLETE && !dieDone) {
                        ++imageCount;
                        ++tempDie;
                        if (tempDie == (diceImages.length - 1)) {
                            dieDone = true;
                        }
                    }
                }
                int back = backtrack.statusID(0, true);
                if (back == MediaTracker.COMPLETE && !backDone) {
                    ++imageCount;
                    backDone = true;
                }
                imagesLoaded = false;
                if (dieDone && backDone) {
                    imagesLoaded = true;
                    dice1 = diceImages[0];
                    dice2 = diceImages[0];
                    dice3 = diceImages[0];
                }
            }
        }
    }

    /**
     * @see java.applet.Applet#stop()
     */
    final public void stop() {
        if (animator != null) {
            animator.stop();
            animator = null;
        }
    }

    /**
     *
     */
    final public boolean action(Event evt, Object arg) {
        if (evt.target == btRoll) {
            invalidWager = false;
            game.rollDice();
            if (!invalidWager) {
                return true;
            }
        }
        return false;
    }

    private void hidePoint() {
        point = "0";
    }

    final public void update(Graphics g) {
        if (imagesLoaded) {
            paint(g);
        } else {
            paint(g);
        }
    }

    /**
     * This is the applets paint procedure.
     *
     * @param g
     * @see java.awt.Graphics
     */
    final public void paint(Graphics g) {
        if (imagesLoaded) {
            //process game...
            showStatus(WELCOME_TO_CELOW_TITLE);
            if (++backCount < 2) {
                g.setColor(Color.black);
                g.fillRect(0, 0, getSize().width, getSize().height);
            }
            g.setColor(Color.black);
            g.fillRect(0, 20, getSize().width, 40);
            g.setColor(Color.white);
            g.setFont(bigFont);
            g.drawString(title, (getSize().width / 2) - 150, 50);
            g.setFont(midFont);
            g.drawString("Dice1:", (getSize().width / 2 - 50), (getSize().height / 2) - 50);
            g.drawImage(dice1, (getSize().width / 2 + 20), (getSize().height / 2) - 60, this);
            g.drawString("Dice2:", (getSize().width / 2 - 50), (getSize().height / 2));
            g.drawImage(dice2, (getSize().width / 2 + 20), (getSize().height / 2) - 10, this);
            g.drawString("Dice3:", (size().width / 2 - 50), (getSize().height / 2) + 50);
            g.drawImage(dice3, (getSize().width / 2 + 20), (getSize().height / 2) + 40, this);
            g.setColor(Color.black);
            g.fillRect(0, 0, 150, getSize().height);
            g.setColor(Color.white);
            g.drawString("Bank:", 40, 150);
            if (balance > 0) {
                g.setColor(Color.green);
            } else if (balance < 0) {
                g.setColor(Color.red);
            }
            g.drawString(bank, 80, 150);
            g.setColor(Color.white);
            if (!"0".equals(point)) {
                g.drawString("Point:", 40, 200);
                g.drawString(point, 80, 200);
            }
        } else {
//            g.drawString("Please allow up to 2 minutes for "
//                    + "loading on 28.8 Modems....",
//                    (getSize().width / 2) - 180, (getSize().height / 2) - 10);

            g.drawString(LOADING_IMAGES_TITLE,
                    (getSize().width / 2) - 20, (getSize().height / 2) + 10);
            progress.reshape(212, 200, 190, 30);
            progress.setBoxColors(new Color(11, 31, 223), new Color(125, 175, 251));
            progress.setBarColor(new Color(203, 143, 251));
            progress.setTextColors(Color.white, Color.black);
            double imageTotal = 7;
            progress.setText(Math.round(((imageCount / imageTotal) * 100))
                    + " %");
            progress.setPercent(imageCount / imageTotal);
            progress.paint(g);
        }
    }

    /**
     * @see java.applet.Applet#getAppletInfo()
     */
    final public String getAppletInfo() {
        return APPLET_INFO;
    }

    @Override
    public void update(IState state) {
        title = caculateTitle(state.getResult());
        balance = state.getTotal();
        bank = state.getBank();
        point = state.getPointAsString();
        dice1 = diceImages[state.getDie(0) - 1];
        dice2 = diceImages[state.getDie(1) - 1];
        dice3 = diceImages[state.getDie(2) - 1];
    }

    private String caculateTitle(GameResultEnum result) {
        String title = WELCOME_TO_CELOW_TITLE;
        switch (result) {
            case LOSER:
                title = LOSER_TITLE;
                break;
            case WINNER:
                title = WINNER_TITLE;
                break;
            case NOTHING:
                title = NOTHING_TITLE;
                break;
            case POINT:
                title = POINT_TITLE;
                break;
            case PUSH:
                title = PUSH_TITLE;
                break;
            case COMPUTER_LOSER:
                title = COMPUTER_LOSER_TITLE;
                break;
            case COMPUTER_WINNER:
                title = COMPUTER_WINNER_TITLE;
                break;
            case COMPUTER_NOTHING:
                title = COMPUTER_NOTHING_TITLE;
                break;
            case COMPUTER_POINT:
                title = POINT_TITLE;
                break;
        }
        return title;
    }

    @Override
    public String getWagerAmount() {
        return tfWager.getText();
    }

    @Override
    public void showInvalidAmount() {
        title = INVALID_WAGER_TITLE;
        invalidWager = true;
    }
}
