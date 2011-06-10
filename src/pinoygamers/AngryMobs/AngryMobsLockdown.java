package pinoygamers.AngryMobs;

import java.util.List;

import org.bukkit.entity.Player;

/**
 * This is a thread that runs in each world and runs through 
 * all the players alerting all nearby monsters that don't
 * already have a target.
 * @author joshua
 *
 */
public class AngryMobsLockdown implements Runnable {

	private AngryMobs plugin;
	private boolean running = true;
	int waittime = 5000;
	Configuration config;
	String world;
	
	public AngryMobsLockdown(AngryMobs plugin, Configuration config, String world) {
		this.plugin = plugin;
		this.config = config;
		this.world = world;
	}
	
	public synchronized void stopIt() {
		running = false;
	}
	
	public synchronized void setWaitTime(int wait) {
		waittime = wait;
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				wait(waittime);
			} catch (InterruptedException e) {
				List<Player> players = plugin.getServer().getWorld(world).getPlayers();
				for(Player theplayer : players) {
					Functions.alertNearbyMonsters(theplayer, config.alertRange);
				}
			}
		}
	}

}
