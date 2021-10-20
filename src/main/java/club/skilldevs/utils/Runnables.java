package club.skilldevs.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

public class Runnables {

    public static ThreadFactory newThreadFactory(String name) {
        return (new ThreadFactoryBuilder()).setNameFormat(name).build();
    }

    public static void run(Callable callable) {
        sLoader.INSTANCE.getServer().getScheduler().runTask(sLoader.INSTANCE, callable::call);
    }

    public static void runAsync(Callable callable) {
        sLoader.INSTANCE.getServer().getScheduler().runTaskAsynchronously(sLoader.INSTANCE, callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        sLoader.INSTANCE.getServer().getScheduler().runTaskLater(sLoader.INSTANCE, callable::call, delay);
    }

    public static void runAsyncLater(Callable callable, long delay) {
        sLoader.INSTANCE.getServer().getScheduler().runTaskLaterAsynchronously(sLoader.INSTANCE, callable::call, delay);
    }

    public static void runTimer(Callable callable, long delay, long interval) {
        sLoader.INSTANCE.getServer().getScheduler().runTaskTimer(sLoader.INSTANCE, callable::call, delay, interval);
    }

    public static void runAsyncTimer(Callable callable, long delay, long interval) {
        sLoader.INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(sLoader.INSTANCE, callable::call, delay, interval);
    }

    public interface Callable {
        void call();
    }
}
