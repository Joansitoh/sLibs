package devs.skillclub.utils.menu.items;

import devs.skillclub.utils.xseries.XMaterial;
import devs.skillclub.utils.menu.actions.ItemClickEvent;

public class CloseMenuItem extends StaticMenuItem {

    public CloseMenuItem() {
        super("&c&lCLOSE", XMaterial.MUSIC_DISC_BLOCKS.parseItem());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }
}