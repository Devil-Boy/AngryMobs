package pinoygamers.AngryMobs;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Flying;
import org.bukkit.entity.LivingEntity;

public class AngryMobsMobSpawner implements Runnable {

	private AngryMobs plugin;
	private boolean running = true;
	int waittime = 10000;
	Configuration config;
	String world;
	Random rand = new Random();
	
	/**
	 * The constuctor creates a new AngryMobsMobSpawner object
	 * @param plugin The plugin instance
	 * @param config The configuration file
	 * @param world The world
	 */
	public AngryMobsMobSpawner(AngryMobs plugin, Configuration config, String world) {
		this.plugin = plugin;
		this.config = config;
		this.world = world;
	}
	
	/**
	 * Stops the mob spawner from spawning new mobs.
	 */
	public synchronized void stopIt() {
		running = false;
	}
	
	/**
	 * Sets the wait time before the mob spawner spawns the next mob
	 * @param wait How long should the mob spawner wait before spawning the next mob
	 */
	public synchronized void setWaitTime(int wait) {
		waittime = wait;
	}
	
	@Override
	/**
	 * Spawns monsters, determined by the config, at regular intervals, also determined by the config.
	 */
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
				String creature = config.spawnableMonsters.get(rand.nextInt(config.spawnableMonsters.size())).trim();
				int spawnedCreatureID = 0;
				if(creature.equalsIgnoreCase("ghast")) {
					boolean notfound = true;
					while(notfound) {
						Block theblock = Functions.randomBigAirBlock(plugin.getServer().getWorld(world), plugin.getServer(), config.monsterSpawnDistance);
						if(Functions.isLowerThanLightLevel(theblock, config.spawnMaxLight)) {
							if(config.debug) {
								System.out.println("Spawning a " + creature + " at " + theblock.getX() + ", " + theblock.getY() + ", " + theblock.getZ());
							}
							if (config.disableNormalMonsters) {
								plugin.mobSpawnLocations.add(theblock.getLocation().getBlock());
							}
							Functions.spawnMob(theblock.getLocation(), creature);
							notfound = false;
						}
					}
					
				}else {
					boolean notfound = true;
					while(notfound) {
						Block theblock = Functions.randomGroundBlock(plugin.getServer().getWorld(world), plugin.getServer(), config.monsterSpawnDistance);
						if(Functions.isLowerThanLightLevel(theblock, config.spawnMaxLight)) {
							if(config.debug) {
								System.out.println("Spawning a " + creature + " at " + theblock.getX() + ", " + theblock.getY() + ", " + theblock.getZ());
							}
							if (config.disableNormalMonsters) {
								plugin.mobSpawnLocations.add(theblock.getLocation().getBlock());
							}
							Functions.spawnMob(theblock.getLocation(), creature);
							notfound = false;
						}
					}
					
				}
			}
		}

	}

}
