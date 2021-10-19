package devs.skillclub.utils.menu.items;

import org.bukkit.inventory.ItemStack;

public class StaticMenuItem extends MenuItem {

    public StaticMenuItem(String displayName, ItemStack icon, String... lore) {
        super(displayName, icon, lore);
        setNameAndLore(getIcon(), getDisplayName(), getLore());
    }

}
