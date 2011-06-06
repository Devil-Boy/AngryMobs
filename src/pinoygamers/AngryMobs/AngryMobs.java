package pinoygamers.AngryMobs;

import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import pinoygamers.AngryMobs.Configuration;

/**
 * AngryMobs for Bukkit
 *
 * @author pinoygamers
 */
public class AngryMobs extends JavaPlugin {
    private final AngryMobsPlayerListener playerListener = new AngryMobsPlayerListener(this);
    private final AngryMobsBlockListener blockListener = new AngryMobsBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    Configuration pluginSettings;
    String pluginMainDir = "./plugins/AngryMobs";
    String pluginConfigLocation = pluginMainDir + "/AngryMobs.cfg";
    public boolean debug = false;
    public HashMap<String, Configuration> worldConfigs = new HashMap<String, Configuration>();

    public AngryMobs() {
        super();
        // TODO: Place any custom initialization code here

        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }

   

    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
        PluginManager pm = getServer().getPluginManager();
       

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println("AngryMobs disabled!");
    }
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}

