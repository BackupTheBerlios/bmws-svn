/*
 * Copyright (c) 2005-2006 Substance Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Substance Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.jvnet.substance;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalProgressBarUI;

import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.theme.SubstanceTheme;

/**
 * UI for progress bars in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceProgressBarUI extends MetalProgressBarUI {
	/**
	 * Hash for computed stripe images.
	 */
	private static Map<String, BufferedImage> stripeMap = new HashMap<String, BufferedImage>();

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		stripeMap.clear();
	}

	/**
	 * The current state of the indeterminate animation's cycle. 0, the initial
	 * value, means paint the first frame. When the progress bar is indeterminate
	 * and showing, the default animation thread updates this variable by invoking
	 * incrementAnimationIndex() every repaintInterval milliseconds.
	 */
	private int animationIndex;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceProgressBarUI();
	}

	/**
	 * Retrieves stripe image.
	 * 
	 * @param baseSize
	 *          Stripe base in pixels.
	 * @return Stripe image.
	 */
	private static synchronized BufferedImage getStripe(int baseSize,
			boolean isRotated) {
		String key = "" + baseSize + ":" + isRotated;
		BufferedImage result = stripeMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getStripe(baseSize);
			if (isRotated) {
				result = SubstanceImageCreator.getRotated(result, 1);
			}
			stripeMap.put(key, result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicProgressBarUI#paintDeterminate(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
//	 @Override
//	public void paintDeterminate(Graphics g, JComponent c) {
//		if (!(g instanceof Graphics2D)) {
//			return;
//		}
//
//		Insets b = progressBar.getInsets(); // area for border
//		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
//		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
//
//		// amount of progress to draw
//		int amountFull = getAmountFull(b, barRectWidth, barRectHeight);
//
//		Graphics2D graphics = (Graphics2D) g;
//
//		// background
//		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
//			SubstanceImageCreator.paintRectangularStripedBackground(graphics, b.left,
//					b.top, barRectWidth, barRectHeight + 1, SubstanceLookAndFeel
//							.getGrayColorScheme(), null, 0, false);
//		} else { // VERTICAL
//			SubstanceImageCreator.paintRectangularStripedBackground(graphics, b.left,
//					b.top, barRectWidth + 1, barRectHeight, SubstanceLookAndFeel
//							.getGrayColorScheme(), null, 0, true);
//		}
//
//		if (amountFull > 0) {
//
//			ColorScheme fillColorScheme = progressBar.isEnabled() ? SubstanceLookAndFeel
//					.getColorScheme()
//					: SubstanceLookAndFeel.getMetallicColorScheme();
//			if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
//				if (c.getComponentOrientation().isLeftToRight()) {
//					SubstanceImageCreator.paintProgressBar(g, b.left, b.top, amountFull,
//							barRectHeight, fillColorScheme, false);
//				} else {
//					SubstanceImageCreator.paintProgressBar(g, barRectWidth + b.left,
//							b.top, amountFull, barRectHeight, fillColorScheme, false);
//				}
//			} else { // VERTICAL
//				// fix for issue 95. Vertical bar is growing from the bottom
//				SubstanceImageCreator.paintProgressBar(g, b.left, progressBar
//						.getHeight()
//						- b.bottom - amountFull, barRectWidth, amountFull, fillColorScheme,
//						true);
//			}
//		}
//
//		// Deal with possible text painting
//		if (progressBar.isStringPainted()) {
//			paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
//		}
//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicProgressBarUI#paintIndeterminate(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void paintIndeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

		int valComplete = animationIndex;// int)
		// (progressBar.getPercentComplete()
		// * barRectWidth);
		Graphics2D graphics = (Graphics2D) g;

		// background
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			SubstanceImageCreator.paintRectangularStripedBackground(graphics, b.left,
					b.top, barRectWidth, barRectHeight + 1, SubstanceLookAndFeel
							.getGrayColorScheme(), null, 0, false);
		} else { // VERTICAL
			SubstanceImageCreator.paintRectangularStripedBackground(graphics, b.left,
					b.top, barRectWidth + 1, barRectHeight, SubstanceLookAndFeel
							.getGrayColorScheme(), null, 0, true);
		}

		ColorScheme fillColorScheme = progressBar.isEnabled() ? SubstanceLookAndFeel
				.getColorScheme()
				: SubstanceLookAndFeel.getMetallicColorScheme();
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			SubstanceImageCreator.paintRectangularStripedBackground(graphics, b.left,
					b.top, barRectWidth, barRectHeight + 1, fillColorScheme, getStripe(
							barRectHeight + 1, false), valComplete, false);
		} else { // VERTICAL
			// fix for issue 95. Vertical progress bar rises from the bottom.
			SubstanceImageCreator
					.paintRectangularStripedBackground(graphics, b.left, b.top,
							barRectWidth + 1, barRectHeight, fillColorScheme, getStripe(
									barRectWidth + 1, true), 2 * barRectWidth + 1 - valComplete,
							true);
		}

		// Deal with possible text painting
		if (progressBar.isStringPainted()) {
			paintString(g, b.left, b.top, barRectWidth, barRectHeight, barRectWidth,
					b);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicProgressBarUI#incrementAnimationIndex()
	 */
	@Override
	protected void incrementAnimationIndex() {
		int newValue = animationIndex + 1;

		Insets b = progressBar.getInsets(); // area for border
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int threshold = 0;
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			threshold = 2 * barRectHeight + 1;
		} else {
			threshold = 2 * barRectWidth + 1;
		}
		animationIndex = newValue % threshold;
		progressBar.repaint();
	}

	public static String getMemoryUsage() {
		StringBuffer sb = new StringBuffer();
		sb.append("SubstanceProgressBarUI: \n");
		sb.append("\t" + stripeMap.size() + " stripes");
		return sb.toString();
	}

}
