/*
 * Copyright (c) 2006
 *      MBWS Team.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Redistributions in any form must be accompanied by information on
 *    how to obtain complete source code for the MBWS software and any
 *    accompanying software that uses the MBWS software.  The source code
 *    must either be included in the distribution or be available for no
 *    more than the cost of distribution plus a nominal fee, and must be
 *    freely redistributable under reasonable conditions.  For an
 *    executable file, complete source code means the source code for all
 *    modules it contains.  It does not include source code for modules or
 *    files that typically accompany the major components of the operating
 *    system on which the executable file runs.
 * 4. Redistribution, use and modification must not be for any commercial 
 *    purpose. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE MBWS TEAM ``AS IS'' AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT, ARE DISCLAIMED.  IN NO EVENT SHALL THE MBWS TEAM
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.mbws.client.sound;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jmex.sound.openAL.SoundSystem;

import de.mbws.client.MBWSClient;
import de.mbws.client.data.ClientGlobals;

/**
 * SoundManager hold and preloads all needed sounds for the game
 * 
 * @author Kerim
 * 
 */
public class SoundManager {
	private static Logger logger = Logger.getLogger(SoundManager.class);

	public static final String INTRO = "intro";

	private static SoundManager soundManager;
	private HashMap<String, Integer> allSoundNodes = new HashMap<String, Integer>();
	private HashMap<String, Integer> allMusic = new HashMap<String, Integer>();

	private boolean playSounds = true;
	private boolean playMusic = true;

	private SoundManager() {
		logger.info("SoundManager initializing");
		SoundSystem.init(null, SoundSystem.OUTPUT_DEFAULT);
		playSounds = MBWSClient.mbwsConfiguration
				.getBoolean(ClientGlobals.OPTIONS_ENABLE_SOUND);
		playMusic = MBWSClient.mbwsConfiguration
				.getBoolean(ClientGlobals.OPTIONS_ENABLE_MUSIC);
		preLoadMainSounds();
		logger.info("SoundManager initializing done");
	}

	private void preLoadMainSounds() {
		logger.info("SoundManager preloading sounds");
		allMusic.put(INTRO, SoundSystem.createStream(
				"data/audio/music/intro.ogg", false));
	}

	public static SoundManager getInstance() {
		if (soundManager == null) {
			soundManager = new SoundManager();
		}
		return soundManager;
	}

	public void startMusic(String song, boolean loop) {
		if (!playMusic) {
			return;
		}
		Integer i = allMusic.get(song);
		if (i != null) {
			if (SoundSystem.isStreamOpened(i.intValue())) {
				SoundSystem.setStreamLooping(i.intValue(), loop);
				SoundSystem.playStream(i.intValue());
			}
		}
	}

	public void stopMusic(String song) {
		Integer i = allMusic.get(song);
		if (i != null) {
			if (SoundSystem.isStreamOpened(i.intValue())) {
				SoundSystem.stopStream(i.intValue());
			}
		}
	}

	public void removeMusic(String song) {
		stopMusic(song);
		allMusic.remove(song);
	}

}
