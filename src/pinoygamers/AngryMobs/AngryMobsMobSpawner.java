package pinoygamers.AngryMobs;

import org.bukkit.World;
import org.bukkit.block.Block;

public class AngryMobsMobSpawner implements Runnable {

	private AngryMobs plugin;
	private boolean running = true;
	int waittime = 10000;
	Configuration config;
	World world;
	
	public AngryMobsMobSpawner(AngryMobs plugin, Configuration config, World world) {
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
	public synchronized void run() {
		while (running) {
			try {
				wait(waittime);
			} catch (InterruptedException e) {
				
			}
			boolean blockfound = false;
			int tries = 0;
			if(world.getLoadedChunks().length > 0 && Functions.isNight(world.getTime())) {
				while(!blockfound || tries < 5) {
					Block theblock = Functions.randomBlock(world);
					if(Functions.safeSpawn(theblock)) {
						
					}
				}
			}
		}

	}

}
