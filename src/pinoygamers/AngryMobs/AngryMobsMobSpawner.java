package pinoygamers.AngryMobs;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;

public class AngryMobsMobSpawner implements Runnable {

	private AngryMobs plugin;
	private boolean running = true;
	int waittime = 10000;
	Configuration config;
	World world;
	Random rand = new Random();
	
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
			if(world.getLoadedChunks().length > 0 && 
					(Functions.isNight(world.getTime()) 
							|| world.getEnvironment() == Environment.NETHER)) {
				while(!blockfound || tries < 5) {
					Block theblock = Functions.randomBlock(world);
					if(Functions.safeSpawn(theblock) && Functions.isLowerThanLightLevel(theblock, 7)) {
						CreatureType ct = CreatureType.fromName(
								Functions.properMonsterCase(((String)config.spawnableMonsters.get(rand.nextInt(config.spawnableMonsters.size()))).trim()));
						world.spawnCreature(theblock.getLocation(), ct);
					}
				}
			}
		}

	}

}
