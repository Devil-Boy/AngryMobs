package pinoygamers.AngryMobs;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
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
    private final AngryMobsEntityListener entityListener = new AngryMobsEntityListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    Configuration pluginSettings;
    String pluginMainDir = "plugins/AngryMobs";
    String pluginConfigLocation = pluginMainDir + "/AngryMobs.cfg";
    String configPrefix = "world-";
    public boolean debug = false;
    public HashMap<String, Configuration> worldConfigs = new HashMap<String, Configuration>();
    public HashMap<String, AngryMobsMobSpawner> spawnerThreads = new HashMap<String, AngryMobsMobSpawner>();
    public LinkedList<Integer> mobSpawns = new LinkedList<Integer>();

    public AngryMobs() {
        super(); // We have no idea what this does, but we have it here anyways.
        // TODO: Place any custom initialization code here

        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }

   

    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

    	File folder = new File(pluginMainDir);
    	if(!folder.exists()) {
    		try {
    			folder.mkdir();
    		}catch (Exception e) {
    			System.out.println("AngryMobs: Could not create directory, do you have the correct permissions?");
    		}
    	}
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        final AngryMobsWorldListener worldL = new AngryMobsWorldListener( this );
        
        pm.registerEvent( Event.Type.WORLD_LOAD, worldL, Event.Priority.Monitor, this );
        pm.registerEvent( Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Monitor, this );
       
        List<World> worlds = getServer().getWorlds();
		
        spawnerThreads.clear();
        
		for( World world : worlds ) {
			File conffile = new File(pluginMainDir + "/" + configPrefix + world.getName() + ".ini");
			Configuration cf = new Configuration(conffile, world.getEnvironment());
			worldConfigs.put(world.getName(), cf);
			AngryMobsMobSpawner ms = new AngryMobsMobSpawner(this, worldConfigs.get(world.getName()), world.getName());
			ms.setWaitTime(cf.monsterSpawnFrequency);
			Thread dispatchThread = new Thread(ms);
            dispatchThread.start();
            spawnerThreads.put(world.getName(), ms);
		}

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
    	//Let's clear all the mob spawning threads.
    	Collection<AngryMobsMobSpawner> stcollect = spawnerThreads.values();
    	for(AngryMobsMobSpawner am : stcollect) {
    		am.stopIt();
    	}
    	spawnerThreads.clear();

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

