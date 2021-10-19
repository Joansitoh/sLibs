
package devs.skillclub.utils.menu.menus;

import devs.skillclub.utils.SkillManager;
import devs.skillclub.utils.menu.ItemMenuHolder;
import devs.skillclub.utils.menu.ItemMenuListener;
import devs.skillclub.utils.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import devs.skillclub.utils.menu.actions.ItemClickEvent;
import devs.skillclub.utils.menu.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ItemMenu {

    public static final MenuItem EMPTY_SLOT_ITEM = new MenuItem(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());
    public static final MenuItem ERROR_SLOT_ITEM = new MenuItem(" ", XMaterial.RED_STAINED_GLASS_PANE.parseItem());

    private String name;
    private Size size;

    private MenuItem[] items;
    private ItemMenu parent;

    private boolean canClick;

    public ItemMenu(String name, Size size, ItemMenu parent) {
        this.canClick = false;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.size = size;
        this.items = new MenuItem[size.getSize()];
        this.parent = parent;
    }

    public ItemMenu(String name, Size size) {
        this(name, size, null);
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public ItemMenu setItem(int position, MenuItem menuItem) {
        this.items[position] = menuItem;
        return this;
    }

    public ItemMenu clearItem(int position) {
        this.items[position] = null;
        return this;
    }

    public ItemMenu clearAllItems() {
        Arrays.fill(this.items, null);
        return this;
    }

    public ItemMenu fillAroundSlots(MenuItem menuItem) {
        for (int i = 0; i < this.items.length; ++i) {
            if (i == 4) continue;
            if (i > 9 && i < 17) continue;
            if (i > 18 && i < 26) continue;
            if (i > 27 && i < 35) continue;
            if (i > 36 && i < 44) continue;
            try {
                if (this.items[i] != null) continue;
                this.items[i] = menuItem;
            } catch (Exception ignore) {}
        }
        return this;
    }

    public ItemMenu fillEmptySlots(MenuItem menuItem) {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) continue;
            this.items[i] = menuItem;
        }
        return this;
    }

    public ItemMenu fillEmptySlots() {
        return this.fillEmptySlots(EMPTY_SLOT_ITEM);
    }

    public ItemMenu fillAroundSlots() {
        return this.fillAroundSlots(EMPTY_SLOT_ITEM);
    }

    public void open(Player player) {
        if (ItemMenuListener.getInstance().isRegistered(SkillManager.INSTANCE))
            ItemMenuListener.getInstance().register(SkillManager.INSTANCE);

        Inventory inventory = Bukkit.createInventory(new ItemMenuHolder(this, Bukkit.createInventory(player, this.size.getSize())), this.size.getSize(), this.name);
        this.apply(inventory, player);
        player.openInventory(inventory);
    }

    public void update(Player player) {
        Inventory inventory;
        if (player.getOpenInventory() != null && (inventory = player.getOpenInventory().getTopInventory()).getHolder() instanceof ItemMenuHolder && ((ItemMenuHolder) inventory.getHolder()).getMenu().equals(this)) {
            this.apply(inventory, player);
            player.updateInventory();
        }
    }

    private void apply(Inventory inventory, Player player) {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] == null) continue;
            inventory.setItem(i, this.items[i].getFinalIcon());
        }
    }

    private boolean cancelled(Player player, ClickType type, int slot, ItemStack item, Inventory inventory) {
        boolean cancelled = false;

        if (!this.canClick) {
            List<ClickType> blocked = Arrays.asList(
                    ClickType.CONTROL_DROP, ClickType.CREATIVE, ClickType.MIDDLE,
                    ClickType.NUMBER_KEY, ClickType.DOUBLE_CLICK, ClickType.DROP
            );

            if (type.isKeyboardClick()) cancelled = true;
            for (ClickType types : blocked) if (type.equals(types)) cancelled = true;

            if (type == ClickType.LEFT || type == ClickType.RIGHT || type == ClickType.SHIFT_LEFT || type == ClickType.SHIFT_RIGHT) {
                if (slot >= 0 && slot < this.size.getSize() && this.items[slot] != null) {
                    ItemClickEvent itemClickEvent = new ItemClickEvent(player, item, type, inventory);

                    this.items[slot].onItemClick(itemClickEvent);
                    if (itemClickEvent.isCanClick()) return false;

                    cancelled = true;
                    if (itemClickEvent.isUpdate()) {
                        this.update(player);
                    } else {
                        player.updateInventory();
                        if (itemClickEvent.isClose() || itemClickEvent.isGoBack()) {
                            String playerName = player.getName();
                            Bukkit.getScheduler().scheduleSyncDelayedTask(SkillManager.INSTANCE, () -> {
                                Player p = Bukkit.getPlayerExact(playerName);
                                if (p != null) p.closeInventory();
                            }, 1L);
                        }

                        if (itemClickEvent.isGoBack() && this.hasParent()) {
                            String playerName = player.getName();
                            Bukkit.getScheduler().scheduleSyncDelayedTask(SkillManager.INSTANCE, () -> {
                                Player p = Bukkit.getPlayerExact(playerName);
                                if (p != null) this.parent.open(p);
                            }, 3L);
                        }
                    }
                }
            }
        }
        return cancelled;
    }

    public void onInventoryClose(InventoryCloseEvent event) {
    }

    public void onInventoryClick(InventoryClickEvent event) {
        if (cancelled((Player) event.getWhoClicked(), event.getClick(), event.getRawSlot(), event.getCurrentItem(), event.getClickedInventory()))
            event.setCancelled(true);
    }

    public void onInventoryCreativeClick(InventoryCreativeEvent event) {
        if (cancelled((Player) event.getWhoClicked(), event.getClick(), event.getRawSlot(), event.getCurrentItem(), event.getClickedInventory()))
            event.setCancelled(true);
    }

    public void destroy() {
        this.name = null;
        this.size = null;
        this.items = null;
        this.parent = null;
    }

    @Getter
    @AllArgsConstructor
    public enum Size {

        ONE_LINE(9),
        TWO_LINE(18),
        THREE_LINE(27),
        FOUR_LINE(36),
        FIVE_LINE(45),
        SIX_LINE(54);

        private final int size;

        public static Size fit(int slots) {
            if (slots < 10) return ONE_LINE;
            if (slots < 19) return TWO_LINE;
            if (slots < 28) return THREE_LINE;
            if (slots < 37) return FOUR_LINE;
            if (slots < 46) return FIVE_LINE;
            return SIX_LINE;
        }
    }

}

