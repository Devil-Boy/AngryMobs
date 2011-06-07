package pinoygamers.AngryMobs;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Flying;

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
				CreatureType ct = CreatureType.fromName(
					Functions.properMonsterCase(config.spawnableMonsters.get(rand.nextInt(config.spawnableMonsters.size())).trim()));
				if(ct == CreatureType.GHAST) {
					Block theblock = Functions.randomAirBlock(world, plugin.getServer(), config.monsterSpawnDistance);
					world.spawnCreature(theblock.getLocation(), ct);
				}else {
					Block theblock = Functions.randomGroundBlock(world, plugin.getServer(), config.monsterSpawnDistance);
					world.spawnCreature(theblock.getLocation(), ct);
				}
			}
		}

	}

}
