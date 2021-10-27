
# sLibs (SkillLibs)
### This is a library for SkillTeam Devleopment plugins.


## API initialization

> Call **sLoader** class for API functioning.
```
new sLoader(plugin);
```

## CUSTOM EVENTS
### PlayerAttackPlayerEvent
> This event is called when Player get hitted by entity.
> Actually detects Wolf, Player and Arrow hits.
```
@EventHandler
public void onPlayerAttack(PlayerAttackPlayerEvent event) {
	if (event.isUsingBow()) {
		event.getPlayer().sendMessage("You shot " + event.getEnemy());
	} else {
		event.getPlayer().sendMessage("You hit " + event.getEnemy());
	}
	event.getPlayer().sendMessage("Damage dealt: " + event.getDamage());
}
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

## CHATUTILS API
> Translate method short ChatColor.translateAlternateColorCodes() in one String.
`public static String translate(String s)`

> You can do the same with Array. 
```
String message = ChatUtils.translate("&6This message is GOLD!");

///////////////////////////////////////////////////

List<String> list = new ArrayList<>();
list.add("&6GOLD message.");
list.add("&cRED message.");
list.add("&9BLUE message.");

list = ChatUtils.translate(list);
```
&nbsp;
> Upper the fist letter on String.
`public static String upperFirst(String s)`
```
String message = ChatUtils.upperFirst("hello world");
OUTPUT = Hello world
```

&nbsp; 
> Format method rename long value into String with format 'HH:mm:ss'.
> Depending the long of the time, the format change for short.
`public static String format(long time)`
```
long time = 7200000;

String format = ChatUtils.format(time);
OUTPUT: 2:00:00

time = 1920000;
format = ChatUtils.format(time);
OUTPUT: 32:00
```

&nbsp; 
> Format money make a shortcut of value in String like '1000 = 1K'
`public static String formatMoney(double money)`
```
double money = 72500000;

String format = ChatUtils.formatMoney(money);
OUTPUT: 72.5M
```

&nbsp; 
> Check if String is a valid number.
`public static boolean isNumeric(String input)`
```
String number = 356f;

boolean isNumeric = ChatUtils.isNumeric(number);
OUTPUT: false

///////////////////////////////////////

number = 366;
isNumeric = ChatUtils.isNumeric(number);
OUTPUT: true
```
