package club.skilldevs.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadFactory;

public class Runnables {

    public static ThreadFactory newThreadFactory(String name) {
        return (new ThreadFactoryBuilder()).setNameFormat(name).build();
    }

    public static JavaPlugin plugin;

    public static void run(Callable callable) {
        Bukkit.getServer().getScheduler().runTask(plugin, callable::call);
    }

    public static void runAsync(Callable callable) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        Bukkit.getServer().getScheduler().runTaskLater(plugin, callable::call, delay);
    }

    public static void runAsyncLater(Callable callable, long delay) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, callable::call, delay);
    }

    public static void runTimer(Callable callable, long delay, long interval) {
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, callable::call, delay, interval);
    }

    public static void runAsyncTimer(Callable callable, long delay, long interval) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, callable::call, delay, interval);
    }

    public interface Callable {
        void call();
    }
}
