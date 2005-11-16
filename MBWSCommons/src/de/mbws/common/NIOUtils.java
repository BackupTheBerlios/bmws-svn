package de.mbws.common;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

import de.mbws.common.events.AbstractGameEvent;

/**
 * NIOUtils.java Misc utility functions to simplify dealing w/NIO channels and
 * buffers
 * 
 * @version 1.0
 */
public class NIOUtils {

	/**
	 * first, writes the header, then the event into the given ByteBuffer in
	 * preparation for the channel write
	 */
	public static void prepBuffer(AbstractGameEvent event,
			ByteBuffer writeBuffer, Integer sessionId) {
		writeBuffer.clear();
		writeBuffer.putInt(sessionId); //sessionid; not sure if we ever need it
		writeBuffer.putInt(0); //size
		writeBuffer.putInt(event.getEventType());
		int size = event.serialize(writeBuffer);
		// ... now owverwrite size
		if (size > 0) {
			writeBuffer.putInt(4, size - 8);	
		}
		System.out.println("packet size " + size);
		writeBuffer.flip();
	}

	/**
	 * write the contents of a ByteBuffer to the given SocketChannel
	 */
	public static void channelWrite(SocketChannel channel,
			ByteBuffer writeBuffer) {
		long nbytes = 0;
		long toWrite = writeBuffer.remaining();

		// loop on the channel.write() call since it will not necessarily
		// write all bytes in one shot
		try {
			while (nbytes != toWrite) {
				nbytes += channel.write(writeBuffer);
				if (toWrite != nbytes) {
    				try {
    					Thread.sleep(Globals.CHANNEL_WRITE_SLEEP);
    				} catch (InterruptedException e) {
    				}
                }
			}
		} catch (ClosedChannelException cce) {
		} catch (Exception e) {
		}

		// get ready for another write if needed
		writeBuffer.rewind();
	}

	/**
	 * write a String to a ByteBuffer, prepended with a short integer
	 * representing the length of the String
	 */
	public static void putStr(ByteBuffer buff, String str) {
		if (str == null) {
			buff.putShort((short) 0);
		} else {
			buff.putShort((short) str.length());
			buff.put(str.getBytes());
		}
	}

	/**
	 * read a String from a ByteBuffer that was written w/the putStr method
	 */
	public static String getStr(ByteBuffer buff) {
		short len = buff.getShort();
		if (len == 0) {
			return null;
		}

		byte[] b = new byte[len];
		buff.get(b);
		return new String(b);

	}

}
