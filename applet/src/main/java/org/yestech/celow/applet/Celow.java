/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.celow.applet;

import java.applet.*;
import java.awt.*;

/**
 * This is the dice games celow.
 *
 * @author BOO i GoT yOu !!!
 * @version $Revision: 1.2 $
 *
 */
final public class Celow extends Applet implements Runnable {

    /**
     *
     */
    final private int LOSER = 0;
    /**
     *
     */
    final private int WINNER = 1;
    /**
     *
     */
    final private int POINT = 2;
    /**
     *
     */
    final private int NOTHING = 3;
    /**
     *
     */
    final private int DICESIDES = 6;
    /**
     *
     */
    private Thread animator;
    /**
     *
     */
    private Button btRoll;
    /**
     *
     */
    private Label lbWager;
    /**
     *
     */
    //private Label lbBank;
    /**
     *
     */
    private String bank;
    /**
     *
     */
    //private Label lbPoint;
    /**
     *
     */
    private String point;
    /**
     *
     */
    private TextField tfWager;
    /**
     *
     */
    //private Label lbTitle;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private Label lbVersion;
    // BEGIN Progress Bar Shit
    /**
     *
     */
    private boolean imagesLoaded = false;
    /**
     *
     */
    private double imageCount;
    /**
     *
     */
    private double imageTotal = 7;
    /**
     *
     */
    private int dice;
    /**
     *
     */
    private int back;
    /**
     *
     */
    private AMDProgressBarEmbed progress;
    /**
     *
     */
    boolean dieDone = false;
    /**
     *
     */
    boolean backDone = false;
    // END PROGRESS
    // GRAPHIC IMAGES
    /**
     *
     */
    private MediaTracker tracker;
    /**
     *
     */
    private MediaTracker backtrack;
    /**
     *
     */
    private Image diceImages[];
    /**
     *
     */
    private Image dice1;
    /**
     *
     */
    private Image dice2;
    /**
     *
     */
    private Image dice3;
    /**
     *
     */
    private Image background;
    /**
     *
     */
    private double backCount;
    // END GRAPHIC IMAGES
    /**
     *
     */
    private double bet;
    /**
     *
     */
    private double total;
    /**
     *
     */
    private int result; // 0:lose -  1:win - 2:point - 3:nothing
    /**
     *
     */
    private int countPoint;
    /**
     *
     */
    private int playerPoint;
    /**
     *
     */
    private int computerPoint;
    /**
     *
     */
    private boolean hasPoint = false;
    /**
     *
     */
    private int die[] = new int[3];
    /**
     *
     */
    private Panel titlePanel;
    /**
     *
     */
    private Panel leftPanel;
    /**
     *
     */
    private Panel rightPanel;
    /**
     *
     */
    private Panel southPanel;
    /**
     *
     */
    private Panel buttonPanel;
    /**
     *
     */
    private Font bigFont;
    /**
     *
     */
    private Font midFont;
    /**
     *
     */
    private Font smallFont;

    //--------------------------------------------------------------------------
    //  A P P L E T   C O M P L I A N T   M E T H O D S 
    //--------------------------------------------------------------------------
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
        smallFont = new Font("Helvetica", Font.PLAIN, 10);
        //setFont(new Font("Helvetica", Font.PLAIN, 24));
        showStatus("Loading Celow..... Please wait......");
        titlePanel = new Panel();
        leftPanel = new Panel();
        leftPanel.setLayout(new GridLayout(2, 2));
        rightPanel = new Panel();
        southPanel = new Panel();
        buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        lbVersion = new Label("Version 1.2");
        //lbVersion = new Label("Version 1.2", Label.LEFT);
        lbVersion.setFont(smallFont);

        btRoll = new Button();
        btRoll.setForeground(Color.black);
        btRoll.setBackground(Color.gray);
        btRoll.setLabel("ROLL");

        lbWager = new Label("Wager:");
        lbWager.setFont(midFont);
        tfWager = new TextField(10);
        tfWager.setFont(midFont);
        tfWager.setForeground(Color.BLACK);

        /*
        lbBank = new Label("Bank:");
        lbBank.setFont(midFont);
        bank = new Label("0");
        bank.setFont(midFont);

        lbPoint = new Label("Point:");
        lbPoint.setFont(midFont);
        point = new Label("N/A");
        point.setFont(midFont);

        lbTitle = new Label("..... Welcome To Celow .....", Label.CENTER);
        lbTitle.setFont(bigFont);

        titlePanel.add(lbTitle);
         */
        total = 0;
        bet = 0;
        bank = "0.0";
        point = "0";
        title = "..... Welcome To Celow .....";

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
            //System.out.println("Getting die[" + i + "]: " + getCodeBase() +
            //* "images/die" + (i + 1) + ".gif");
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
        //System.out.println("Getting background: " + getCodeBase() +
        //"images/background.gif");
        Image imback = getImage(getCodeBase(), "images/background.gif");
        background = imback;
        backtrack.addImage(imback, 0);
        if (imback == null) {
            //System.out.println("IMBACK is NULL...");
        }
        //add("North", titlePanel);
        //add("West", leftPanel);
        add("South", southPanel);
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
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        int tempDie = -1;
        while (getSize().width > 0 && getSize().height > 0 && animator != null) {
            repaint();
            if (imagesLoaded) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                for (int d = 0; d < diceImages.length; d++) {
                    dice = tracker.statusID(d, true);
                    //System.out.println("imageCount DICE: " + imageCount);
                    if (dice == MediaTracker.COMPLETE && !dieDone) {
                        ++imageCount;
                        ++tempDie;
                        if (tempDie == (diceImages.length - 1)) {
                            dieDone = true;
                        }
                    }
                }
                back = backtrack.statusID(0, true);
                if (back == MediaTracker.COMPLETE && !backDone) {
                    ++imageCount;
                    backDone = true;
                }
                //System.out.println("imageCount END: " + imageCount);
                imagesLoaded = false;
                if (dieDone && backDone) {
                    imagesLoaded = true;
                    dice1 = diceImages[0];
                    dice2 = diceImages[0];
                    dice3 = diceImages[0];
                }
            }
        }
        /*
        while (true) {
        rollDice();
        processRoll();
        System.out.println("Label: " + lbTitle.getText());
        System.out.println("Die[0]: " + die[0]);
        System.out.println("Die[1]: " + die[1]);
        System.out.println("Die[2]: " + die[2]);
        System.out.println("##################################");
        }
         */
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
            if (checkAmount(tfWager.getText())) {
                rollDice();
                processRoll();
                return true;
            } else {
                /*
                lbTitle.setFont(midFont);
                lbTitle.setForeground(Color.red);
                lbTitle.setText(".... Sorry but the wager must be greater than 0 ....");
                 */
                title = ".... Wager must be greater than 0 ....";
            }
        }
        return false;
    }

    /**
     * @see java.applet.Applet#update()
     */
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
        //g.setColor(Color.white);
        //g.drawString("version 1.2", 0, getSize().height - 20);
        if (imagesLoaded) {
            showStatus("Welcome to Celow......");
            if (++backCount < 2) {
                g.setColor(Color.black);
                g.fillRect(0, 0, getSize().width, getSize().height);
            }
            g.setColor(Color.black);
            g.fillRect(0, 20, getSize().width, 40);
            g.setColor(Color.white);
            g.setFont(bigFont);
            //g.drawString(title, (getSize().width/2) - (title.length()/2), 50);
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
            if (total > 0) {
                g.setColor(Color.green);
            } else if (total < 0) {
                g.setColor(Color.red);
            }
            g.drawString(bank, 80, 150);
            g.setColor(Color.white);
            g.drawString("Point:", 40, 200);
            g.drawString(point, 80, 200);
        } else {
            //g.drawString(title, (getSize().width/2 - title.length()), 50);
            g.drawString("Please allow up to 2 minutes for "
                    + "loading on 28.8 Modems....",
                    (getSize().width / 2) - 180, (getSize().height / 2) - 10);
            g.drawString("Loading Images....",
                    (getSize().width / 2) - 20, (getSize().height / 2) + 10);
            progress.reshape(212, 200, 190, 30);
            progress.setBoxColors(new Color(11, 31, 223), new Color(125, 175, 251));
            progress.setBarColor(new Color(203, 143, 251));
            progress.setTextColors(Color.white, Color.black);
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
        return "Celow version 1.2, developed by YES Technology"
                + ", copyright yestech.org 1999-2010";
    }

    /**
     * Process a dice role
     */
    private void processRoll() {
        dice1 = diceImages[(die[0] - 1)];
        dice2 = diceImages[(die[1] - 1)];
        dice3 = diceImages[(die[2] - 1)];
        //System.out.println("PROCESS hasPoint: " + hasPoint);
        if (!hasPoint) {
            switch (result) {
                case LOSER:
                    title = "     ....... Loser .......";
                    total = total - bet;
                    bank = String.valueOf(total);
                    break;
                case WINNER:
                    title = "     ....... Winner .......";
                    total = total + bet;
                    bank = String.valueOf(total);
                    break;
                case POINT:
                    break;
                case NOTHING:
                    title = "     ....... NOTHING .......";
                    break;
            }
            ;
            point = "0";
        } else {
            switch (result) {
                case LOSER:
                    title = "....... Computer Loser .......";
                    total = total + bet;
                    bank = String.valueOf(total);
                    hasPoint = false;
                    countPoint = 0;
                    break;
                case WINNER:
                    title = "....... Computer Winner .......";
                    total = total - bet;
                    bank = String.valueOf(total);
                    hasPoint = false;
                    countPoint = 0;
                    break;
                case POINT:
                    title = "     ....... POINT .......";
                    point = String.valueOf(playerPoint);
                    //System.out.println("Point: " + playerPoint);
                    if (computerPoint != 0 && countPoint == 1) {
                        if (playerPoint < computerPoint) {
                            title = "....... Computer Winner .......";
                            total = total - bet;
                            bank = String.valueOf(total);
                        } else if (playerPoint == computerPoint) {
                            title = "     ....... PUSH .......";
                        } else if (playerPoint > computerPoint) {
                            title = "....... Computer Loser .......";
                            total = total + bet;
                            bank = String.valueOf(total);
                        }
                        if (countPoint == 1) {
                            hasPoint = false;
                            countPoint = 0;
                        }
                    }
                    break;
                case NOTHING:
                    title = "....... Computer NOTHING .......";
                    break;
            }
            ;
        }
    }

    /**
     * Roll the dice
     */
    private void rollDice() {
        die[0] = ((int) ((Math.random() * DICESIDES))) + 1;
        die[1] = ((int) ((Math.random() * DICESIDES))) + 1;
        die[2] = ((int) ((Math.random() * DICESIDES))) + 1;
        // RULES LOGIC
            /*
        winning hands:
        die0 == die1 == die2

        die0 == die1 && die2 == 6
        die1 == die2 && die0 == 6
        die0 == die2 && die1 == 6

        die0 == 4 && die1 == 5 && die2 == 6
        die0 == 4 && die1 == 6 && die2 == 5
        die0 == 5 && die1 == 4 && die2 == 6
        die0 == 5 && die1 == 6 && die2 == 4
        die0 == 6 && die1 == 5 && die2 == 4
        die0 == 6 && die1 == 4 && die2 == 5

        losing hands:
        die0 == 1 && die1 == 2 && die2 == 3
        die0 == 1 && die1 == 3 && die2 == 2
        die0 == 2 && die1 == 1 && die2 == 3
        die0 == 2 && die1 == 3 && die2 == 1
        die0 == 3 && die1 == 1 && die2 == 2
        die0 == 3 && die1 == 2 && die2 == 1

        die0 == die1 && die2 == 1
        die1 == die2 && die0 == 1
        die2 == die0 && die1 == 1

        points:
        die0 == die1 && die2 != 6 && die2 != 1
        die1 == die2 && die0 != 6 && die0 != 1
        die2 == die0 && die1 != 6 && die1 != 1
         */
        //winner
        if ((die[0] == die[1]) && (die[1] == die[2])) {
            result = WINNER;
        } else if ((die[0] == die[1]) && (die[2] == 6)) {
            result = WINNER;
        } else if ((die[1] == die[2]) && (die[0] == 6)) {
            result = WINNER;
        } else if ((die[0] == die[2]) && (die[1] == 6)) {
            result = WINNER;
        } else if ((die[0] == 4) && (die[1] == 5) && (die[2] == 6)) {
            result = WINNER;
        } else if ((die[0] == 4) && (die[1] == 6) && (die[2] == 5)) {
            result = WINNER;
        } else if ((die[0] == 5) && (die[1] == 4) && (die[2] == 6)) {
            result = WINNER;
        } else if ((die[0] == 5) && (die[1] == 6) && (die[2] == 4)) {
            result = WINNER;
        } else if ((die[0] == 6) && (die[1] == 5) && (die[2] == 4)) {
            result = WINNER;
        } else if ((die[0] == 6) && (die[1] == 4) && (die[2] == 5)) {
            result = WINNER;
        } else if ((die[0] == 1) && (die[1] == 2) && (die[2] == 3)) {
            result = LOSER;
        } else if ((die[0] == 1) && (die[1] == 3) && (die[2] == 2)) {
            result = LOSER;
        } else if ((die[0] == 2) && (die[1] == 1) && (die[2] == 3)) {
            result = LOSER;
        } else if ((die[0] == 2) && (die[1] == 3) && (die[2] == 1)) {
            result = LOSER;
        } else if ((die[0] == 3) && (die[1] == 1) && (die[2] == 2)) {
            result = LOSER;
        } else if ((die[0] == 3) && (die[1] == 2) && (die[2] == 1)) {
            result = LOSER;
        } else if ((die[0] == die[1]) && (die[2] == 1)) {
            result = LOSER;
        } else if ((die[1] == die[2]) && (die[0] == 1)) {
            result = LOSER;
        } else if ((die[2] == die[0]) && (die[1] == 1)) {
            result = LOSER;
        } else if ((die[0] == die[1]) && (1 < die[2]) && (die[2] < 6)) {
            if (!hasPoint) {
                playerPoint = die[2];
                //System.out.println("....... Player Point: " + playerPoint
                //* + " .......");
                hasPoint = true;
                countPoint = 0;
            } else {
                //System.out.println("....ROLL DICE COMPUTER Point2: " +
                //* die[2] + " ....");
                computerPoint = die[2];
                countPoint = 1;
            }
            result = POINT;
        } else if ((die[1] == die[2]) && (1 < die[0]) && (die[0] < 6)) {
            if (!hasPoint) {
                playerPoint = die[0];
                //System.out.println("....... Player Point: " + playerPoint
                //* + " .......");
                hasPoint = true;
                countPoint = 0;
            } else {
                //System.out.println("....ROLL DICE COMPUTER Point0: " +
                //* die[0] + " ....");
                computerPoint = die[0];
                countPoint = 1;
            }
            result = POINT;
        } else if ((die[2] == die[0]) && (1 < die[1]) && (die[1] < 6)) {
            if (!hasPoint) {
                playerPoint = die[1];
                //System.out.println("....... Player Point: " + playerPoint
                //* + " .......");
                hasPoint = true;
                countPoint = 0;
            } else {
                //System.out.println("....ROLL DICE COMPUTER Point1: " +
                //* die[1] + " ....");
                computerPoint = die[1];
                countPoint = 1;
            }
            result = POINT;
        } else {
            result = NOTHING;
        }
    }

    /**
     * Check the balance
     */
    private boolean checkAmount(String amount) {
        try {
            Double db = new Double(amount);
            boolean ok = db.isNaN();
            if (!ok) {
                //if ((db.doubleValue() > 0) && (db.doubleValue() <= 1000)) {
                if (db.doubleValue() > 0) {
                    bet = db.doubleValue();
                    ok = true;
                }
            } else {
                ok = false;
            }
            return ok;
        } catch (NumberFormatException nf) {
            return false;
        }
    }
}
