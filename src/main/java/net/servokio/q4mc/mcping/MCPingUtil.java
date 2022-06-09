package net.servokio.q4mc.mcping;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class MCPingUtil {
    public static final byte PACKET_HANDSHAKE = 0x00,
            PACKET_STATUSREQUEST = 0x00,
            PACKET_PING = 0x01;
    public static final int PROTOCOL_VERSION = 4;
    public static final int STATUS_HANDSHAKE = 1;

    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + COLOR_CHAR + "([0-9A-FK-OR]|#[0-9A-Fa-f]{3,6})");

    /**
     * Strips the given message of all color codes
     *
     * @param input String to strip of color
     * @return A copy of the input string, without any coloring
     */
    public static String stripColors(final String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static void io(final boolean b, final String m) throws IOException {
        if (b) {
            throw new IOException(m);
        }
    }

    /**
     * @author thinkofdeath See:
     * https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     */
    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }

            if ((k & 0x80) != 128) {
                break;
            }
        }

        return i;
    }

    /**
     * @author thinkofdeath See:
     * https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     * @throws IOException
     */
    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }

            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}
