# sLibs (SkillLibs)
### This is a library for SkillTeam Devleopment plugins.


## API initialization

> Call **SkillManager** class for API functioning.
```
new SkillManager(plugin);
```

## MENU CREATOR
> For create new menu.
`public ItemMenu(String name, Size size, ItemMenu parent)`
```
ItemMenu menu = new ItemMenu("&6Inventory name", ItemMenu.Size.FIVE_LINE);
```
&nbsp;
> For create new item for menu.
`public ItemMenu setItem(int position, MenuItem menuItem)`
```
ItemMenu menu = new ItemMenu("&6Inventory name", ItemMenu.Size.FIVE_LINE);

menu.setItem(position, new MenuItem("&6Item name", new ItemStack(Material.WATCH)));
```

&nbsp; &nbsp;

> For create action item.
`public ActionMenuItem(String displayName, ItemClickHandler handler, ItemStack icon, String... lore)`
```
ItemMenu menu = new ItemMenu("&6Inventory name", ItemMenu.Size.FIVE_LINE);

menu.setItem(position, new ActionMenuItem("&6Item name", new ItemClickHandler() {
    @Override
    public void onItemClick(ItemClickEvent event) {
        event.getPlayer().sendMessage("You clicked this item!");
    }
}, new ItemStack(Material.WATCH)));
```

> With java +8 (lambda)
```
menu.setItem(position, new ActionMenuItem("&6Item name", event -> {
    event.getPlayer().sendMessage("You clicked this item!");
}, new ItemStack(Material.WATCH)));
```

