/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * @(#)AMDProgressBarEmbed.java    0.90 12/09/96 Adam Doppelt
 */

package org.yestech.celow.applet;

import java.awt.*;

/**
 * A ProgressBar bar is a widget which indicates how close a task is to
 * completion. The developer can create a ProgressBar bar, draw it
 * somewhere on screen, and make the bar move from 0% to 100% while
 * the rest of the program does something time consuming.<P>
 *
 * There are two versions of the ProgressBar bar, one which is a subclass
 * of an AWT component, and one which can be embedded in the drawing
 * area of other components. AMDProgressBarEmbed must be embedded in the
 * drawing area of another component to work, usually an Applet or
 * Canvas. That way, one Canvas can be used to contain a ProgressBar bar
 * alongside whatever else the developer wants to draw. Here are the
 * basic steps to adding an AMDProgressBarEmbed to a component:<P>
 *
 * <UL>
 *    <LI>Create an AMDProgressBarEmbed, passing the component in which
 *    the bar should be embeddedas the <CODE>parent</CODE> parameter.
 *    <LI>Position it within the component using <CODE>reshape()</CODE>.
 *    <LI><B>Important:</B> call the bar's <CODE>paint()</CODE> method
 *        from within the component's <CODE>paint</CODE>, passing the
 *        Graphics object that the bar should paint in.
 * </UL><P>
 *
 * Note that when the bar needs to repaint itself, it will call its
 * parent's <CODE>repaint()</CODE> method. If the parent's
 * <CODE>paint()</CODE> method does not call the bar's
 * <CODE>paint()</CODE> method, the bar will never redraw<P>
 *
 * A ProgressBar bar looks like this:<P>
 *
 * <CENTER><IMG SRC="../bar.gif"></CENTER><P>
 *
 * <B>Orig Version:</B> 0.90 09 Dec 1996<BR>
 * <B>Author:</B> <A HREF="http://www.cs.brown.edu/people/amd/">Adam Doppelt</A><BR>
 * @see AMDProgressBar 
 *
 */
final public class AMDProgressBarEmbed {
	
    //--------------------------------------------------------------------------
    //  P R O T E C T E D   M E M B E R   V A R I A B L E S 
    //--------------------------------------------------------------------------
    /////////////////////////////////////
    // things the user can set

    int x_, y_, width_, height_;

    Color outerBorderColor_ = Color.black;
    Color innerBorderColor_ = Color.black;

    Color boxColorA_ = new Color(244, 224, 32);
    Color boxColorB_ = new Color(120, 80, 4);

    Color barColor_ = new Color(52, 112, 4);

    Color backTopColor_ = new Color(40, 40, 40);
    Color backBottomColor_ = new Color(76, 76, 76);

    Color textColor_ = new Color(208, 192, 28);
    Color textShadowColor_ = Color.black;
    Font font_ = new Font("TimesRoman", Font.BOLD, 14);

    String text_;

    int sinePeriod_ = 87;
    int colorSegment_ = 7;

    /////////////////////////////////////
    // things stored for quick drawing

    Component parent_;
    Graphics graphics_;
    Image image_;

    double percent_;
    boolean updateTrim_, updateLocations_, updateBar_;

    // state variables
    int lastDigits_, halfY_, textX_, textY_;

    int r_, g_, b_, r2_, g2_, b2_;
	
    //--------------------------------------------------------------------------
    //  C O N S T R U C T O R S 
    //--------------------------------------------------------------------------
    /**
     * Constructs a ProgressBar bar.
     * @param parent The AWT parent for this bar. Note that the bar isn't
     * actually attached to the parent in the usual AWT sense. See the
     * note at the top for a longer explanation.
     */
    public AMDProgressBarEmbed(Component parent) {
	parent_ = parent;

        lastDigits_ = 1;
	setPercent(0);

        updateTrim_ = true;
	updateLocations_ = true;
	updateBar_ = true;
    }

    /**
     *
     */
    void drawSineBox(Color peakA, Color peakB, int x, int y
                     , int width, int height) {
        r_  = peakA.getRed();
	g_  = peakA.getGreen();
	b_  = peakA.getBlue();
	r2_ = peakB.getRed();
	g2_ = peakB.getGreen();
	b2_ = peakB.getBlue();

        int lastX = x + width;
	int lastY = y + height;

        int offset = 0;
	int p = (int)((double)sinePeriod_ / 4.0);
	int loop;

        loop = drawSineLine(p, offset, x, y, lastX, y);

        p += loop * colorSegment_;
	offset += (loop * colorSegment_) - lastX + 1;
	loop = drawSineLine(p, offset, lastX, y, lastX, lastY);

        p += loop * colorSegment_;
        offset += (loop * colorSegment_) - lastY + 1;
        loop = drawSineLine(p, offset, lastX, lastY, x, lastY);

        p += loop * colorSegment_;
	offset += (loop * colorSegment_) - lastX + 1;
	loop = drawSineLine(p, offset, x, lastY, x, y);
    }

    /**
     *
     */
    int drawSineLine(int p, int offset, int x1, int y1
                     , int x2, int y2) {
        boolean swap = (x1 == x2);

        if (swap) {
            int t = x1;
            x1 = y1; y1 = t;
            t = x2;
            x2 = y2; y2 = t;
        }
	boolean reverse = (x1 > x2);

        if (reverse)
            offset *= -1;

        // start colorSegment_
	graphics_.setColor(getColor(p));
	if (swap)
            graphics_.drawLine(y1, x1, y1, x1 + offset);
        else
            graphics_.drawLine(x1, y1, x1 + offset, y1);

        int loop = 1;

        // main colorSegment_
	if (!reverse)
            offset += colorSegment_;

        p += colorSegment_;
	while ((reverse && x1 + offset - colorSegment_ > x2) ||
               (!reverse && x1 + offset < x2)) {
            graphics_.setColor(getColor(p));
            if (swap)
		graphics_.drawLine(y1, x1 + offset - colorSegment_,
                                   y1, x1 + offset);
            else
		graphics_.drawLine(x1 + offset - colorSegment_, y1,
                                   x1 + offset, y1);

            offset += reverse ? -colorSegment_ : colorSegment_;
            p += colorSegment_;
            ++loop;
        }

        if (reverse)
            offset += colorSegment_;

        // end colorSegment_
	graphics_.setColor(getColor(p));
	if (swap)
            graphics_.drawLine(y1, x1 + offset - colorSegment_,
                               y1, x2);
        else
            graphics_.drawLine(x1 + offset - colorSegment_, y1,
                               x2, y1);
        return loop;
    }

    /**
     *
     */
    Color getColor(int p) {
	double s = Math.sin(((double)(p % sinePeriod_) * 2.0 * Math.PI) /
                            (double)sinePeriod_) + 1.0;
        return new Color(r_ + (int)(s * (double)(r2_ - r_) / 2.0),
                         g_ + (int)(s * (double)(g2_ - g_) / 2.0),
                         b_ + (int)(s * (double)(b2_ - b_) / 2.0));
    }

    /**
     * Paint the ProgressBar bar. This should be called from the parent's
     * <CODE>paint()</CODE> method. Otherwise <B>the bar will never
     * draw.</B>
     * @param g The graphics object to draw in.
     */
    public void paint(Graphics g) {
	if (graphics_ == null) {
            image_ = parent_.createImage(width_, height_);
            graphics_ = image_.getGraphics();
            updateTrim_ = true;
            updateLocations_ = true;
            updateBar_ = true;
        }

        if (updateTrim_)
            updateTrim();

        if (updateLocations_)
            updateLocations();

        if (updateBar_) {
            int left = (int)((double)(width_ - 6) * percent_);
            int right = width_ - 6 - left;

            graphics_.translate(3, 3);
            if (left > 0) {
		graphics_.setColor(barColor_);
		graphics_.fillRect(0, 0, left, height_ - 6);
            }

            if (right > 0) {
		graphics_.setColor(backTopColor_);
		graphics_.fillRect(left, 0,      right, halfY_);
		graphics_.setColor(backBottomColor_);
		graphics_.fillRect(left, halfY_, right, height_ - 6 - halfY_);
            }
            graphics_.translate(-3, -3);

            graphics_.setFont(font_);
            graphics_.setColor(textShadowColor_);

            String p;
            if (text_ == null)
		p = "" + ((int)(percent_ * 100)) + "%";
            else
		p = text_;

            graphics_.drawString(p, textX_ + 1, textY_ + 1);
            graphics_.setColor(textColor_);
            graphics_.drawString(p, textX_,     textY_);

            updateBar_ = false;
        }
	g.drawImage(image_, x_, y_, null);
    }
    
    /**
     * Resize the ProgressBar bar.
     * @param x The new x location with the parent.
     * @param y The new y location with the parent.
     * @param width The new width of the bar, from outer border on the
     * left to outer border on the right.
     * @param height The new height of the bar, from outer border on the
     * top to outer border on the bottom.
     */
    public void reshape (int x, int y, int width, int height) {
	x_ = x;
	y_ = y;
	width_ = width;
	height_ = height;
	if (graphics_ != null) {
            graphics_.dispose();
            graphics_ = null;
        }
	parent_.repaint();
    }
    
    /**
     * Set the colors of the top and bottom half of the background inside
     * the box.
     * @param top The color of the top half of the background.
     * @param bottom The color of the bottom half of the background. 
     */
    public void setBackgroundColors(Color top, Color bottom) {
	backTopColor_ = top;
	backBottomColor_ = bottom;
	updateBar_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the color of the bar itself.
     * @param bar The color of the bar.
     */
    public void setBarColor(Color bar) {
	barColor_ = bar;
	updateBar_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the colors of the outer and inner borders on the box.
     * @param outer The color of the 1 pixel border outside the colored box.
     * @param inner The color of the 1 pixel border just inside the colored box.
     */
    public void setBorderColors(Color outer, Color inner) {
	outerBorderColor_ = outer;
	innerBorderColor_ = inner;
	updateTrim_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the colors of the high and low points of the sine box
     * surrounding the bar. Note that the actual color will walk in a sine
     * wave between the two colors passed here. The sine wave will crawl
     * from the upperleft corner, clockwise around the box. If a == b, the
     * box will be drawn in a single color.
     * @param a The color of the first peak of the sine wave.
     * @param b The color of the other peak of the sine wave.
     */
    public void setBoxColors(Color a, Color b) {
	boxColorA_ = a;
	boxColorB_ = b;
	updateTrim_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the font of the text.
     * @param font The font of the text in the middle of the box.
     */
    public void setFont(Font font) {
	font_ = font;
	updateLocations_= true;
	updateBar_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the percent of the ProgressBar bar.
     * @param percent The new percent, with 0.0 being completely empty and
     * 1.0 completely full.
     */
    public void setPercent(double percent) {
	if (percent < 0)
            percent_ = 0.0;
        else if (percent > 1.0)
            percent_ = 1.0;
        else
            percent_ = percent;
        updateBar_= true;

        int newDigits;
	if (percent < 0.1)
            newDigits = 1;
        else if (percent < 1.0)
            newDigits = 2;
        else
            newDigits = 3;

        if (newDigits != lastDigits_) {
            lastDigits_ = newDigits;
            if (text_ == null)
		updateLocations_ = true;
        }
	parent_.repaint();
    }
    
    /**
     * Set some properties of the sine box.
     * @param period The distance in pixels between two peaks in the sine
     * box.
     * @param segmentSize The size in pixels of each colored segment in
     * the sine box.
     */
    public void setSineProperties(int period, int segmentSize) {
	sinePeriod_ = period;
	colorSegment_ = segmentSize;
	updateTrim_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the text which should be drawn in the center of the bar.
     * @param text The text to draw. If null is passed, the current
     * percent will be drawn.
     */
    public void setText(String text) {
	text_ = text;

        updateLocations_ = true;
	updateBar_ = true;
	parent_.repaint();
    }
    
    /**
     * Set the colors of the text and its shadow.
     * @param text The color of the text in the middle of the box.
     * @param shadow The color of the shadow on the text.
     */
    public void setTextColors(Color text, Color shadow) {
	textColor_ = text;
	textShadowColor_ = shadow;
	updateBar_ = true;
	parent_.repaint();
    }

    /**
     *
     */
    void updateLocations() {
	halfY_ = (height_ - 6) / 2;

        FontMetrics m = parent_.getFontMetrics(font_);
	String sample;

        if (text_ == null) {
            if (lastDigits_ == 1)
		sample = "3";
            else if (lastDigits_ == 2)
		sample = "30";
            else
		sample = "100";
        }
	else
            sample = text_;

        textX_ = (width_ - m.stringWidth(sample)) / 2;
        textY_ = (height_ + m.getAscent() - m.getDescent()) / 2;
	updateLocations_ = false;
    }

    /**
     *
     */
    void updateTrim() {
	graphics_.setColor(outerBorderColor_);
	graphics_.drawRect(0, 0, width_ - 1, height_ - 1);

        graphics_.setColor(innerBorderColor_);
	graphics_.drawRect(2, 2, width_ - 5, height_ - 5);

        if (boxColorB_ == null || boxColorA_.equals(boxColorB_)) {
            graphics_.setColor(boxColorA_);
            graphics_.drawRect(1, 1, width_ - 3, height_ - 3);
        }
	else
            drawSineBox(boxColorA_, boxColorB_, 1, 1, width_ - 3, height_ - 3);

        updateTrim_ = false;
    }
}
