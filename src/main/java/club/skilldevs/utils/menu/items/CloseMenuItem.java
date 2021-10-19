package club.skilldevs.utils.menu.items;

import club.skilldevs.utils.xseries.XMaterial;
import club.skilldevs.utils.menu.actions.ItemClickEvent;

public class CloseMenuItem extends StaticMenuItem {

    public CloseMenuItem() {
        super("&c&lCLOSE", XMaterial.MUSIC_DISC_BLOCKS.parseItem());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }
}