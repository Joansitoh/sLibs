package me.skillteam.utils.menu.menus;

import lombok.Getter;
import lombok.Setter;
import me.skillteam.utils.menu.items.ActionMenuItem;
import me.skillteam.utils.menu.items.MenuItem;
import me.skillteam.utils.xseries.XMaterial;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomPage extends ItemMenu {

    private static final HashMap<Player, HashMap<String, Integer>> playerInventoryPage = new HashMap<>();
    private final HashMap<MenuItem, Integer> locked = new HashMap<>();

    @Getter @Setter
    private InventoryAction inventoryAction;

    public CustomPage(String title, Player player, List<MenuItem> items) {
        super(title + " - Pag. " + playerInventoryPage.getOrDefault(player, new HashMap<>()).getOrDefault(title, 1), Size.SIX_LINE);

        for (int x = 1; x < 49; x += 9) setItem(x, EMPTY_SLOT_ITEM);

        playerInventoryPage.putIfAbsent(player, new HashMap<>());
        HashMap<String, Integer> playerPage = playerInventoryPage.get(player);
        playerPage.putIfAbsent(title, 1);

        int page = playerPage.get(title);
        int index = page * 35 - 35;

        if (page != 1) {
            setItem(45, new ActionMenuItem("§a<-- (" + (page - 1) + ")", itemClickEvent -> {
                playerPage.put(title, playerPage.get(title) - 1);
                playerInventoryPage.put(player, playerPage);

                CustomPage customPage = new CustomPage(title, player, items);
                if (inventoryAction != null) {
                    inventoryAction.onChangePage(player, customPage);
                    customPage.setInventoryAction(inventoryAction);
                }

                for (MenuItem menuItem : locked.keySet()) customPage.addLockedItem(menuItem, locked.get(menuItem));
                customPage.open(itemClickEvent.getPlayer());
            }, XMaterial.ARROW.parseItem()));
        }

        int x = 2;
        for (; index < items.size(); index++) {
            if (x == 9 || x == 18 || x == 27 || x == 36) x += 2;
            if (x == 44) {
                setItem(53, new ActionMenuItem("§a--> (" + (page + 1) + ")", itemClickEvent -> {
                    playerPage.put(title, playerPage.get(title) + 1);
                    playerInventoryPage.put(player, playerPage);

                    CustomPage customPage = new CustomPage(title, player, items);
                    if (inventoryAction != null) {
                        inventoryAction.onChangePage(player, customPage);
                        customPage.setInventoryAction(inventoryAction);
                    }

                    for (MenuItem menuItem : locked.keySet()) customPage.addLockedItem(menuItem, locked.get(menuItem));
                    customPage.open(itemClickEvent.getPlayer());
                }, XMaterial.ARROW.parseItem()));
                x++;
                continue;
            }

            if (x > 44) continue;

            setItem(x, items.get(index));
            x++;
        }
    }

    public void addLockedItem(MenuItem item, int slot) {
        locked.put(item, slot);
        setItem(slot, item);
    }

    private Integer[] getAround() {
        return new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    }

    public List<Integer> getMarginLeft() {
        return Arrays.asList(0, 9, 18, 27, 36, 45);
    }

    public List<Integer> getMarginRight() {
        return Arrays.asList(8, 17, 26, 35, 45, 53);
    }

    public List<Integer> getInteriorMargin() {
        return Arrays.asList(10, 16, 19, 25, 28, 34, 37, 43);
    }

    public interface InventoryAction {

        void onChangePage(Player player, CustomPage page);

    }

}
