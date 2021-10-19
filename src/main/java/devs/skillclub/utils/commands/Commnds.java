package devs.skillclub.utils.commands;

import devs.skillclub.utils.ChatUtils;
import devs.skillclub.utils.Runnables;
import devs.skillclub.utils.SkillManager;
import devs.skillclub.utils.texts.FancyMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Setter
public abstract class Commnds extends BukkitCommand {

    public JavaPlugin INSTANCE = SkillManager.INSTANCE;

    private boolean forPlayersOnly, async;
    private String permission;
    @Getter
    private int arguments, timer;

    public Commnds(String name) {
        this(name, new ArrayList<>());
    }

    public Commnds(String name, List<String> aliases) {
        this(name, aliases, null, false);
    }

    public Commnds(String name, String permission) {
        this(name, new ArrayList<>(), permission);
    }

    public Commnds(String name, String permission, List<String> aliases) {
        this(name, aliases, permission);
    }

    public Commnds(String name, List<String> aliases, String permission) {
        this(name, aliases, permission, false);
    }

    public Commnds(String name, boolean forPlayersOnly) {
        this(name, new ArrayList<>(), null, forPlayersOnly);
    }

    public Commnds(String name, List<String> aliases, boolean forPlayersOnly) {
        this(name, aliases, null, forPlayersOnly);
    }

    public Commnds(String name, String permission, boolean forPlayersOnly) {
        this(name, new ArrayList<>(), permission, forPlayersOnly);
    }

    public Commnds(String name, List<String> aliases, String permission, boolean forPlayersOnly) {
        super(name);
        setAliases(aliases);
        this.permission = permission;
        this.forPlayersOnly = forPlayersOnly;
        this.arguments = 0;
        this.timer = 0;
    }

    public String getUsage() {
        return ChatUtils.translate("Usage: &c/" + getName() + " " + super.getUsage());
    }

    public String getUsageArgument() {
        if (super.getUsage().equalsIgnoreCase("/" + getName())) return "";
        return super.getUsage();
    }

    public String getSenderName(CommandSender sender) {
        return sender instanceof Player ? sender.getName() : "Console";
    }

    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            if (this.forPlayersOnly) {
                sender.sendMessage("Only players can execute this command."); // Language.ONLY_FOR_PLAYERS.toString()
                return true;
            }

            if (this.async) Runnables.runAsync(() -> execute(sender, args, alias));
            else execute(sender, args, alias);
            return true;
        }

        Player player = (Player) sender;
        if (!checkPermission(sender, this.permission)) {
            sender.sendMessage("You don't have permission to execute this commmand."); // Language.NO_PERMISSION.toString()
            return false;
        }

        if (this.async) {
            Runnables.runAsync(() -> {
                execute(player, args, alias);
                execute(sender, args, alias);
            });
        } else {
            execute(player, args, alias);
            execute(sender, args, alias);
        }

        return true;
    }

    public void execute(CommandSender sender, String[] args, String alias) {
    }

    public void execute(Player player, String[] args, String alias) {
    }

    public boolean checkPermission(CommandSender sender, String permission) {
        if (!(sender instanceof Player)) return true;
        if (permission == null || permission.equals("")) return true;
        return sender.hasPermission(permission);
    }

    /**
     *
     * @param command Command without arguments.
     * @param description Description of command.
     * @return Get fancy message api return.
     */

    public FancyMessage getCommandMessage(String command, String description) {
        return getCommandMessage(command, null, description);
    }

    public FancyMessage getCommandMessage(String command, String suggestion, String description) {
        FancyMessage message = new FancyMessage(ChatUtils.translate("&f* "));
        message.then(ChatUtils.translate("&b/" + command)).tooltip(ChatUtils.translate("&aClick to suggest")).suggest("/" + (suggestion == null ? command : suggestion))
                .then(ChatUtils.translate(" &7| &f" + description));
        return message;
    }
}