package pinoygamers.AngryMobs;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
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
    public Boolean listenerDebug = true;

    public AngryMobsEntityListener(AngryMobs instance) {
        plugin = instance;
    }

    public void onCreatureSpawn(CreatureSpawnEvent event) {
    	if (listenerDebug) {
			System.out.println("A creature wishes to spawn! Current allowed: " + Functions.arrayToString(plugin.mobSpawns.toArray(), ", "));
		}
    	LivingEntity theBorn = (LivingEntity) event.getEntity();
    	if (plugin.worldConfigs.get(theBorn.getWorld().getName()).disableNormalMonsters) {
    		if (listenerDebug) {
    			System.out.println("Pass judgement on him!");
    		}
    		if (theBorn instanceof Monster) {
    			if (plugin.mobSpawns.contains(theBorn.getEntityId())) {
    				if (listenerDebug) {
            			System.out.println("You may live...");
            		}
    				plugin.mobSpawns.removeLastOccurrence(theBorn.getEntityId());
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
    }
}

