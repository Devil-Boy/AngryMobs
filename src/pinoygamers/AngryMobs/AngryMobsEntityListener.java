package pinoygamers.AngryMobs;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handles all entity related events
 * @author pinoygamers
 */
public class AngryMobsEntityListener extends EntityListener {
    private final AngryMobs plugin;
    /**
     * Whether or not this listener will output debug messages.
     */
    public Boolean listenerDebug = false;

    public AngryMobsEntityListener(AngryMobs instance) {
        plugin = instance;
    }

    /**
     * Called when a creature spawns.
     * @param event The CreatureSpawnEvent itself.
     */
    public void onCreatureSpawn(CreatureSpawnEvent event) {
    	if(!event.isCancelled() && event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
    		Entity theBorn = event.getEntity();
    		try{
        		if (plugin.worldConfigs.get(theBorn.getWorld().getName()).disableNormalMonsters) {
        			if (event.getSpawnReason() == SpawnReason.NATURAL) {
        				theBorn.remove();
        				if (listenerDebug) {
        					System.out.println("Natural spawn blocked!");
        				}
        			}
        		}
        	} catch(Exception e) {
        	}
    	}
    	
    	/* Old Code
    	if(!event.isCancelled() && event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
    		LivingEntity theBorn = (LivingEntity) event.getEntity();
        	if (listenerDebug) {
    			System.out.println("Creature at " + theBorn.getLocation().getBlock() + " wishes to spawn! Current allowed: " + Functions.arrayToString(plugin.mobSpawnLocations.toArray(), "; "));
    		}
        	try{
        		if (plugin.worldConfigs.get(theBorn.getWorld().getName()).disableNormalMonsters) {
            		if (listenerDebug) {
            			System.out.println("Pass judgement on him!");
            		}
            		if (Functions.isCreatureType(theBorn, CreatureType.MONSTER) || Functions.isCreatureType(theBorn, CreatureType.WOLF) || Functions.isCreatureType(theBorn, CreatureType.GHAST) || Functions.isCreatureType(theBorn, CreatureType.SLIME)) {
            			if (plugin.mobSpawnLocations.contains(theBorn.getLocation().getBlock())) {
            				if (listenerDebug) {
                    			System.out.println("You may live...");
                    		}
            				plugin.mobSpawnLocations.removeLastOccurrence(theBorn.getLocation().getBlock());
            			} else {
            				event.setCancelled(true);
                    		if (listenerDebug) {
                    			System.out.println("Normal Spawn canceled!");
                    		}
            			}
            		} else {
            			if (listenerDebug) {
                			System.out.println("Just an innocent weakling.");
                		}
            		}
            	} else {
            		if (listenerDebug) {
            			System.out.println("No judgement necessary....");
            		}
            	}
        	}catch(Exception e) {
        		
        	}
        	
    	}*/
    }
}

