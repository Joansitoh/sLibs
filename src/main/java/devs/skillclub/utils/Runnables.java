package devs.skillclub.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

public class Runnables {

    public static ThreadFactory newThreadFactory(String name) {
        return (new ThreadFactoryBuilder()).setNameFormat(name).build();
    }

    public static void run(Callable callable) {
        SkillManager.INSTANCE.getServer().getScheduler().runTask(SkillManager.INSTANCE, callable::call);
    }

    public static void runAsync(Callable callable) {
        SkillManager.INSTANCE.getServer().getScheduler().runTaskAsynchronously(SkillManager.INSTANCE, callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        SkillManager.INSTANCE.getServer().getScheduler().runTaskLater(SkillManager.INSTANCE, callable::call, delay);
    }

    public static void runAsyncLater(Callable callable, long delay) {
        SkillManager.INSTANCE.getServer().getScheduler().runTaskLaterAsynchronously(SkillManager.INSTANCE, callable::call, delay);
    }

    public static void runTimer(Callable callable, long delay, long interval) {
        SkillManager.INSTANCE.getServer().getScheduler().runTaskTimer(SkillManager.INSTANCE, callable::call, delay, interval);
    }

    public static void runAsyncTimer(Callable callable, long delay, long interval) {
        SkillManager.INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(SkillManager.INSTANCE, callable::call, delay, interval);
    }

    public interface Callable {
        void call();
    }
}
