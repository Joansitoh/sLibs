package club.skilldevs.utils;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatUtils {

    private static double PROGRESS_BAR_LENGTH = 20.0;
    private static String PROGRESS_BAR_BLOCK = "█";

    public static String MENU_BAR = ChatColor.STRIKETHROUGH.toString() + "------------------------";
    public static String M_BAR = ChatColor.STRIKETHROUGH.toString() + "-------------";
    public static String CHAT_BAR = ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";
    public static String MEDIUM_CHAT_BAR = ChatColor.STRIKETHROUGH.toString() + "------------------------------";

    ////////////////////////////////////////////////////////////

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    public static void send(CommandSender sender, List<String> messages) {
        translate(messages).forEach(sender::sendMessage);
    }

    ////////////////////////////////////////////////////////////

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> translate(List<String> s) {
        List<String> list = new ArrayList<>();
        for (String x : s) list.add(translate(x));
        return list;
    }

    ////////////////////////////////////////////////////////////

    public static String upperFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    ////////////////////////////////////////////////////////////

    public static String format(long time) {
        if (DurationFormatUtils.formatDuration(time, "H:mm:ss").startsWith("0:"))
            return DurationFormatUtils.formatDuration(time, "mm:ss");
        else return DurationFormatUtils.formatDuration(time, "H:mm:ss");
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        return date.getHours() + ":" + date.getMinutes() + " - " + date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
    }

    public static String formatMoney(double money) {
        BigDecimal big = new BigDecimal(money);
        if (big.compareTo(new BigDecimal(1000)) < 0) return String.valueOf(big);
        BigDecimal var2 = null, var3 = null;
        String symbol = "", format = "";
        if (big.compareTo(new BigDecimal("1000000000000000000000000000")) > 0) {
            var2 = new BigDecimal(100000000000000000L);
            var3 = new BigDecimal(1.0E10D);
            symbol = "OC";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal("1000000000000000000000000")) > 0) {
            var2 = new BigDecimal(1000000000000000L);
            var3 = new BigDecimal(1.0E9D);
            symbol = "SP";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal("100000000000000000000")) == 1) {
            var2 = new BigDecimal(10000000000000L);
            var3 = new BigDecimal(1.0E8D);
            symbol = "SX";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal(1000000000000000000L)) == 1) {
            var2 = new BigDecimal(100000000000L);
            var3 = new BigDecimal(1.0E7D);
            symbol = "QT";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal(1000000000000000L)) == 1) {
            var2 = new BigDecimal(1000000000L);
            var3 = new BigDecimal(1000000.0D);
            symbol = "Q";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal(1000000000000L)) > 0) {
            var2 = new BigDecimal(100000000);
            var3 = new BigDecimal(10000.0D);
            symbol = "T";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal(1000000000L)) > 0) {
            var2 = new BigDecimal(1000000);
            var3 = new BigDecimal(1000.0D);
            symbol = "B";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        if (big.compareTo(new BigDecimal(1000000)) > 0) {
            var2 = new BigDecimal(10000);
            var3 = new BigDecimal(100.0D);
            symbol = "M";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }

        if (big.compareTo(new BigDecimal(1000)) > 0) {
            var2 = new BigDecimal(100);
            var3 = new BigDecimal(10.0D);
            symbol = "k";
            format = String.valueOf(big.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }

        return money + "";
    }

    ////////////////////////////////////////////////////////////

    public static String getProgressBar(int max, double secondsLeft) {
        return getProgressBar(PROGRESS_BAR_BLOCK, PROGRESS_BAR_LENGTH, max, secondsLeft);
    }

    public static String getProgressBar(String block, double length, int max, double secondsLeft) {
        return getProgressBar(block, length, max, secondsLeft, "§c", "§a");
    }

    public static String getProgressBar(String block, double length, int max, double secondsLeft, String char1, String char2) {
        String chrr = char1 + block, chrr2 = char2 + block;

        StringBuilder bar = new StringBuilder();
        for (int x = 0; x < length; x++) bar.append(chrr);

        double percent = (secondsLeft / max) * 100.0;
        int bar_length = (int) (percent / 100.0 * length);
        int bar_length2 = (int) (length - bar_length);

        StringBuilder progressBar = new StringBuilder();
        for (int x = 0; x < bar_length2; x++) progressBar.append(chrr2);
        for (int x = 0; x < bar_length; x++) progressBar.append(chrr);

        return ChatUtils.translate(progressBar.toString());
    }

    /**
     * We recommend the use of async on this function.
     * @param player
     * @return the player country
     */
    public static String getCountry(Player player) {
        InetSocketAddress ip = new InetSocketAddress(player.getAddress().getAddress().getHostAddress(), 0);
        try {
            URL url = new URL("http://ip-api.com/json/" + ip.getHostName());
            BufferedReader stream;
            stream = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();
            if (!(entirePage.toString().contains("\"country\":\""))) return null;

            return entirePage.toString().split("\"country\":\"")[1].split("\",")[0];
        } catch (IOException exception) {
            return "Narnia";
        }
    }

    ////////////////////////////////////////////////////////////

    public static String getSplitResult(String message, String split) {
        if (message == null) return "ERROR";
        StringBuilder builder = new StringBuilder();
        for (String s : message.split(split))
            builder.append(s).append("\n");

        return builder.toString();
    }

    public static List<String> getReverseList(List<String> path) {
        if (path != null) {
            int size = path.size();
            List<String> toReturn = new ArrayList<>();
            for (int i = size - 1; i >= 0; i--) {
                toReturn.add(path.get(i));
            }
            return toReturn;
        }
        return null;
    }

    ////////////////////////////////////////////////////////////

    public static boolean isNumeric(String input) {
        if (input == null) return false;
        try {
            double d = Double.parseDouble(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
