package pinoygamers.AngryMobs;

import java.io.File;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

public class AngryMobsWorldListener extends WorldListener {
	
	private AngryMobs plugin;

	/**
	 * AngryMobsWorldListener Constructor, creates a new instance of AngryMobsWorldListener
	 * @param plugin The plugin
	 */
	public AngryMobsWorldListener(AngryMobs plugin) {
		this.plugin = plugin;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Does certain things when the world loads, such as loading the configuration file for the world, and starting the MobSpawner class.
	 */
	public void onWorldLoad( WorldLoadEvent event )
	{
		File conffile = new File(plugin.pluginMainDir + "/" + plugin.configPrefix + event.getWorld().getName() + ".ini");
		plugin.worldConfigs.put(event.getWorld().getName(), new Configuration(conffile, event.getWorld().getEnvironment()));
		if(plugin.spawnerThreads.containsKey(event.getWorld().getName())) {
			AngryMobsMobSpawner am = plugin.spawnerThreads.get(event.getWorld().getName());
			am.stopIt();
			plugin.spawnerThreads.remove(event.getWorld().getName());
		}
		AngryMobsMobSpawner ms = new AngryMobsMobSpawner(plugin, plugin.worldConfigs.get(event.getWorld().getName()), event.getWorld().getName());
		ms.setWaitTime(plugin.worldConfigs.get(event.getWorld().getName()).monsterSpawnFrequency);
		Thread dispatchThread = new Thread(ms);
        dispatchThread.start();
        plugin.spawnerThreads.put(event.getWorld().getName(), ms);
        if(plugin.lockdownThreads.containsKey(event.getWorld().getName())) {
			AngryMobsLockdown ld = plugin.lockdownThreads.get(event.getWorld().getName());
			ld.stopIt();
			plugin.spawnerThreads.remove(event.getWorld().getName());
		}
        AngryMobsLockdown ml = new AngryMobsLockdown(plugin, plugin.worldConfigs.get(event.getWorld().getName()), event.getWorld().getName());
        //ml.setWaitTime(cf.)
        Thread dt = new Thread(ms);
        dt.start();
        plugin.lockdownThreads.put(event.getWorld().getName(), ml);
	}
	
	/* Waiting for the RB of bukkit to include this event.
	public void onWorldUnload(WorldUnloadEvent event) {
		
	}
	*/

}
