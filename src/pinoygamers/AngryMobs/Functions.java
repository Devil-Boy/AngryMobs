package pinoygamers.AngryMobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

import java.lang.Math;
import java.util.List;

/**
 * All functions that will be used in more than one class should be put
 * here for easy access and central management.
 * @author Tux2
 *
 */
public class Functions {
	
	/**
	 * Spawns a mob at the block specified.
	 * 
	 * @param spawnblock The specific block that you want the mob to spawn at
	 * @param type A string with any valid minecraft mob type
	 * @return True if spawn was successful, false if the spawn wasn't.
	 */
	public static boolean SpawnMob(Block spawnblock, String type) {
    	
    	Location loc = spawnblock.getLocation();
    	if (type.equalsIgnoreCase("PigZombie")) {
    		type = "PigZombie";
    	}else {
    		type = Functions.capitalCase(type);
    	}
    	CreatureType ct = CreatureType.fromName(type);
    	
    	if (ct == null) {
        	return false;
        }
    	spawnblock.getWorld().spawnCreature(loc, ct);
        return true;
    }
	
	/**
	 * Spawns a mob at the location specified.
	 * 
	 * @param loc Location to spawn monster
	 * @param type A string with any valid minecraft mob type
	 * @return True if spawn was successful, false if the spawn wasn't.
	 */
	public static boolean SpawnMob(Location loc, String type) {
		
    	if (type.equalsIgnoreCase("PigZombie")) {
    		type = "PigZombie";
    	}else {
    		type = Functions.capitalCase(type);
    	}
    	CreatureType ct = CreatureType.fromName(type);
    	
    	if (ct == null) {
        	return false;
        }
    	loc.getWorld().spawnCreature(loc, ct);
        return true;
    }
	
	/**
	 * Returns the string with the first letter capitalized, the rest lowercase.
	 * @param s The String to capitalize
	 * @return The capitalized string
	 */
    public static String capitalCase(String s)
    {
        return s.toUpperCase().charAt(0) + s.toLowerCase().substring(1);
    }
    
    /**
     * Returns the distance between two points in a 3D world.
     * @param x1
     * @param y1
     * @param z1
     * @param x2
     * @param y2
     * @param z2
     * @return Distance between 1 and 2.
     */
    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
    	double distance=0;

        distance=Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2)+Math.pow((z2-z1),2));
        return distance;
    }
    
    /**
     * Returns the distance between two locations. Assumes the locations
     * are in the same world.
     * @param a
     * @param b
     * @return
     */
    public static double distance(Location a, Location b) {
    	return distance(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }
    
    /**
     * Returns a random chunk from a given world.
     * @param w The world
     * @return
     */
    public static Chunk randomChunk(World w){
    	java.util.Random generator = new java.util.Random();
    	Chunk[] loadedChunks = w.getLoadedChunks(); // an array of loaded chunks
    	
    	return loadedChunks[generator.nextInt(loadedChunks.length)];
    }
    
    /**
     * Returns a random block from a given world.
     * @param w the world
     * @return
     */
    public static Block randomBlock(World w){
    	return randomBlock(randomChunk(w));
    }
    
    /**
     * Returns a random block from a given chunk.
     * @param c The chunk
     * @return
     */
    public static Block randomBlock(Chunk c){
    	java.util.Random generator = new java.util.Random();
    	int randX = generator.nextInt(16);
    	int randZ = generator.nextInt(16);
    	int randY = generator.nextInt(128);
    	
    	return c.getBlock(randX, randY, randZ);
    }
    
    /**
     * Makes all monsters within the configured range of a player begin to attack him.
     * @param player The unlucky individual
     * @param range The dimensions of the safety box around the player.
     */
    public void alertNearbyMonsters(Player player, int range) {
    	Entity[] theCrowd = (Entity[]) player.getNearbyEntities(range, range, range).toArray(); // Radius will be configurable later on
    	for (int i=0; i<theCrowd.length; i++) {
			if (theCrowd[i] instanceof Monster) {
    			((Creature) theCrowd[i]).setTarget(player);
			}
    	}
    }
    
    /**
     * Changes the type of a certain monster
     * @param e1 The mob we want to change
     * @param mtype What are we going to change it to
     */
    public static void changeMob(Entity e1, String mtype) {
    	Location location = e1.getLocation();
    	e1.remove();
    	SpawnMob(location, mtype);
    }
    
    /**
     * Changes all the mobs in the player's area from one type to another
     * @param player The unlucky individual
     * @param ctype1 The type of mob you want to change
     * @param ctype2 The type of mob you want to change to
     */
    public static void changeAllNearbyMobs(Player player, CreatureType ctype1, CreatureType ctype2) {
    	
    	Entity[] ents = (Entity[]) player.getNearbyEntities(16, 16, 16).toArray(); // "Nearby" will be the same as "nearby" for alertNearbyMonsters
    	
    	for (int i = 0; i < ents.length; i++) {
			if(isCreatureType(ents[i], ctype1)) {
				changeMob(ents[i], ctype1.getName());
			}
		}
    }
    
    /**
     * Changes all the mobs in a world from one type to another
     * @param w The world
     * @param player The unlucky individual
     * @param ctype1 The type of mob you want to change
     * @param ctype2 The type of mob you want to change to
     */
    public static void changeAllMobs(World w, Player player, CreatureType ctype1, CreatureType ctype2) {
    	
    	Entity[] ents = (Entity[]) w.getEntities().toArray();
    	
    	for (int i = 0; i < ents.length; i++) {
			if(isCreatureType(ents[i], ctype1)) {
				changeMob(ents[i], ctype1.getName());
			}
		}
    }
    
    /**
     * To determine if an Entity is of a certain Creature type DOES NOT WORK WITH PLAYERS ONLY CREATURES 
     * @param e The entity we want to determine
     * @param c The creature type we want to compare
     * @return True if the entity is of the creature type, false if not
     */
    public static boolean isCreatureType(Entity e, CreatureType c) {
    	switch(c) {
    		case CHICKEN: return(e instanceof Chicken);
    		case COW: return(e instanceof Cow);
    		case GHAST: return(e instanceof Ghast);
    		case GIANT: return(e instanceof Giant);
    		case MONSTER: return(e instanceof Monster);
    		case PIG: return(e instanceof Pig);
    		case PIG_ZOMBIE: return(e instanceof PigZombie);
    		case SHEEP: return(e instanceof Sheep);
    		case SKELETON: return(e instanceof Skeleton);
    		case SLIME: return(e instanceof Slime);
    		case SPIDER: return(e instanceof Spider);
    		case SQUID: return(e instanceof Squid);
    		case WOLF: return(e instanceof Wolf);
    		case ZOMBIE: return(e instanceof Zombie);
    	}
		return false;
    }

}
