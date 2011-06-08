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
	String world;
	Random rand = new Random();
	
	public AngryMobsMobSpawner(AngryMobs plugin, Configuration config, String world) {
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
			if(config.debug) {
				System.out.println("Looking for a spot... and the time is: " + plugin.getServer().getWorld(world).getTime());
			}
			boolean blockfound = false;
			int tries = 0;
			if(plugin.getServer().getWorld(world).getLoadedChunks().length > 0) {
				if(config.debug) {
					System.out.println("Let's find a spot...");
				}
				CreatureType ct = CreatureType.fromName(
					Functions.properMonsterCase(config.spawnableMonsters.get(rand.nextInt(config.spawnableMonsters.size())).trim()));
				if(ct == CreatureType.GHAST) {
					boolean notfound = true;
					while(notfound) {
						Block theblock = Functions.randomAirBlock(plugin.getServer().getWorld(world), plugin.getServer(), config.monsterSpawnDistance);
						if(Functions.isLowerThanLightLevel(theblock, 7)) {
							if(config.debug) {
								System.out.println("Spawning a " + ct.getName() + " at " + theblock.getX() + ", " + theblock.getY() + ", " + theblock.getZ());
							}
							plugin.getServer().getWorld(world).spawnCreature(theblock.getLocation(), ct);
							notfound = false;
						}
					}
					
				}else {
					boolean notfound = true;
					while(notfound) {
						Block theblock = Functions.randomGroundBlock(plugin.getServer().getWorld(world), plugin.getServer(), config.monsterSpawnDistance);
						if(Functions.isLowerThanLightLevel(theblock, 7)) {
							if(config.debug) {
								System.out.println("Spawning a " + ct.getName() + " at " + theblock.getX() + ", " + theblock.getY() + ", " + theblock.getZ());
							}
							plugin.getServer().getWorld(world).spawnCreature(theblock.getLocation(), ct);
							notfound = false;
						}
					}
					
				}
			}
		}

	}

}
