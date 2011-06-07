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
	}

}
