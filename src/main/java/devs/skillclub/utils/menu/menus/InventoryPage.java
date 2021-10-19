package devs.skillclub.utils.menu.menus;

import devs.skillclub.utils.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
import devs.skillclub.utils.menu.items.ActionMenuItem;
import devs.skillclub.utils.menu.items.MenuItem;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InventoryPage extends ItemMenu {

    private static final HashMap<Player, HashMap<String, Integer>> playerInventoryPage = new HashMap<>();
    private final HashMap<MenuItem, Integer> locked = new HashMap<>();

    @Getter
    @Setter
    private InventoryAction inventoryAction;

    public InventoryPage(String title, Player player, List<MenuItem> items) {
        super(title + " - Pag. " + playerInventoryPage.getOrDefault(player, new HashMap<>()).getOrDefault(title, 1), Size.SIX_LINE);

        fillList(getAroundSlots(), EMPTY_SLOT_ITEM);

        playerInventoryPage.putIfAbsent(player, new HashMap<>());
        HashMap<String, Integer> playerPage = playerInventoryPage.get(player);
        playerPage.putIfAbsent(title, 1);

        int page = playerPage.get(title);
        int index = page * 20 - 20;

        if (page != 1) {
            setItem(45, new ActionMenuItem("§a<-- (" + (page - 1) + ")", itemClickEvent -> {
                playerPage.put(title, playerPage.get(title) - 1);
                playerInventoryPage.put(player, playerPage);

                InventoryPage inventoryPage = new InventoryPage(title, player, items);
                if (inventoryAction != null) {
                    inventoryAction.onChangePage(player, inventoryPage);
                    inventoryPage.setInventoryAction(inventoryAction);
                }

                for (MenuItem menuItem : locked.keySet()) inventoryPage.addLockedItem(menuItem, locked.get(menuItem));
                inventoryPage.open(itemClickEvent.getPlayer());
            }, XMaterial.ARROW.parseItem()));
        }

        int x = 11;
        for (; index < items.size(); index++) {
            if (x == 16 || x == 25 || x == 34) x += 4;

            if (x == 43) {
                setItem(53, new ActionMenuItem("§a--> (" + (page + 1) + ")", itemClickEvent -> {
                    playerPage.put(title, playerPage.get(title) + 1);
                    playerInventoryPage.put(player, playerPage);

                    InventoryPage inventoryPage = new InventoryPage(title, player, items);
                    if (inventoryAction != null) {
                        inventoryAction.onChangePage(player, inventoryPage);
                        inventoryPage.setInventoryAction(inventoryAction);
                    }

                    for (MenuItem menuItem : locked.keySet()) inventoryPage.addLockedItem(menuItem, locked.get(menuItem));
                    inventoryPage.open(itemClickEvent.getPlayer());
                }, XMaterial.ARROW.parseItem()));
                continue;
            }

            if (x > 43) continue;

            setItem(x, items.get(index));
            x++;
        }
    }

    public void addLockedItem(MenuItem item, int slot) {
        locked.put(item, slot);
        setItem(slot, item);
    }

    public void fillList(List<Integer> list, MenuItem item) {
        for (int x : list) {
            if (getItems()[x] == null) setItem(x, item);
        }
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

    public List<Integer> getAroundSlots() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44));
        if (getSize().equals(Size.FIVE_LINE)) list.addAll(Arrays.asList(37, 38, 39, 40, 41, 42, 43));
        else if (getSize().equals(Size.SIX_LINE)) list.addAll(Arrays.asList(45, 46, 47, 48, 49, 50, 51, 52, 53));

        list.removeIf(integer -> getSize().getSize() <= integer);
        return list;
    }

    public interface InventoryAction {

        void onChangePage(Player player, InventoryPage page);

    }

}
