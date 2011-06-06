package pinoygamers.AngryMobs;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;
import java.lang.Math;

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
    
    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
    	double distance=0;

        distance=Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2)+Math.pow((z2-z1),2));
        return distance;
    }

}
