package pinoygamers.AngryMobs;

import java.io.File;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

public class AngryMobsWorldListener extends WorldListener {
	
	private AngryMobs plugin;

	public AngryMobsWorldListener(AngryMobs plugin) {
		this.plugin = plugin;
		// TODO Auto-generated constructor stub
	}
	
	public void onWorldLoad( WorldLoadEvent event )
	{
		File conffile = new File(plugin.pluginMainDir + "/" + plugin.configPrefix + event.getWorld().getName() + ".ini");
		plugin.worldConfigs.put(event.getWorld().getName(), new Configuration(conffile, event.getWorld().getEnvironment()));
		if(plugin.spawnerThreads.containsKey(event.getWorld().getName())) {
			AngryMobsMobSpawner am = plugin.spawnerThreads.get(event.getWorld().getName());
			am.stopIt();
			plugin.spawnerThreads.remove(event.getWorld().getName());
		}
		AngryMobsMobSpawner ms = new AngryMobsMobSpawner(plugin, plugin.worldConfigs.get(event.getWorld().getName()), event.getWorld());
		Thread dispatchThread = new Thread(ms);
        dispatchThread.start();
        plugin.spawnerThreads.put(event.getWorld().getName(), ms);
	}

}
