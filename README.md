
# sLibs (SkillLibs)
### This is a library for SkillTeam Devleopment plugins.

&nbsp;
#### Quick Navigation
- [Downloads / API Initialization](#-downloads--api-initialization)
- [Custom events](#-custom-events)
- [File configuration](#-file-configuration)
- [Chat utilities](#-chat-utilities)
- [Discord](#-discord)
- [License](#-license)

## 💾 Downloads / API Initialization
The last release version of the sLibs can be found on [GitHub](hhtps://github.com/SkillTeam/sLibs).

Alternatively, you can build sLibs via **Maven**. Release versions of sLibs are built using **Java 8**.
````xml
<dependencies>
    <dependency>
        <groupId>club.skilldevs.utils</groupId>
        <artifactId>sLibs</artifactId>
        <version>1.4.9</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
````

For initialize the sLibs, you only have to register `sLoader` class.
```java
package me.example;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LibsTutorial extends JavaPlugin {

    @Override
    public void onEnable() {
        sLoader loader = new sLoader(this);
        
        // Register damage listener.
        loader.registerDamageListeners(this);
    }
}
```

## 📣 Custom events
#### PlayerAttackPlayerEvent `public PlayerAttackPlayerEvent(Player player, Player enemy, double damage)`

This event is called when **Player** get hitted by entity. 
Actually detects **Wolf**, **Player**, **Snowball** and **Arrow** hits.
```java
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

## 💽 File configuration
> FileConfig api is used to create config files more easily.
`public FileConfig(JavaPlugin plugin, String fileName)`
> 
> Get methods **_automatically_** translate **Strings** with chat color format.
> For other types of data, automatically get default value if it is **NOT** set.

#### File configuration basics.
For create a new file configuration, you have to create a new instance of `FileConfig`.
```java
FileConfig config = new FileConfig(this, "settings.yml");
```

**Note:** You can use `FileConfig` to create config files with default values, custom headers, and more.
```java
// Update header every time you save the config.
config.setUpdateHeader(true);

config.setHeader("This is an example of header on config file.");

// Set a default value for key if it is NOT set.
config.setDefaultIfNotSet("CONFIG-VERSION", "1.0");
config.setDefaultIfNotSet("DEFAULT-COMMAND", "/example");

config.getConfiguration().set("TEST-MSG", "Test message");

config.save();
```

Get a **String** value from config.
```java
String message = config.getString("TEST-MSG");
```

## 📝 Chat utilities
> ChatUtils api is used to translate messages with chat color format.
> Also, you have multiple methods to transform **Strings** to other formats.
> 
> More useful methods are
> - `public static String translate(String message)`:
>   Translate **String** with chat color format.
> - `public static String[] translate(String[] array)`:
>   Translate **String** array with chat color format.
> - `public static String format(long milliseconds)`:
>   Format **long** milliseconds to **String** (HH:mm:ss).
> - `public static String formatMoney(double money)`:
>   Format **money** to **String**. `("1000000" -> "1M")`
> - `public static boolean isNumeric(String number)`:
>   Check if **String** is **numeric**.

#### Example:
```java
String message = ChatUtils.translate("&cHello &6World!");
```

#### Other methods:
Progress var is used to get a progress bar in String. Blocks and length can be modified.
`public String getProgressBar(int max, double secondsLeft)`
```java
double cooldown_max = 60; // in seconds
double left = 30;

ChatUtils.PROGRESS_BAR_LENGTH = 30;
ChatUtils.PROGRESS_BAR_BLOCK = ":";

String bar = ChatUtils.getProgressBar(cooldown_max, left);
// OUT: &a:::::::::::::::&c:::::::::::::::
```

Get country is used to get country name from **Player IP** address.
`public static String getCountry(Player player)`

**Note:** This method is **NOT** thread-safe. Use it in **AsyncTask**.
```java
String country = ChatUtils.getCountry(player);
```

## ☎️ Discord
**For any question or suggestion you can join our Discord Server.**
You can join our server by clicking the following link.
- **https://discord.gg/XWQWQ**

## 📜 License
The **source code** for sLibs is licensed under the GNU General Public License v3.0, to view the license click
[here](https://github.com/Joansitoh/sLibs/blob/master/LICENSE).
