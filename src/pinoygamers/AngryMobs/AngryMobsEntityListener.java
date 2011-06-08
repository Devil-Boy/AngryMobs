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
 * Handle events for all Player related events
 * @author pinoygamers
 */
public class AngryMobsEntityListener extends EntityListener {
    private final AngryMobs plugin;

    public AngryMobsEntityListener(AngryMobs instance) {
        plugin = instance;
    }

    public void onCreatureSpawn(CreatureSpawnEvent event) {
    	if (plugin.debug) {
			System.out.println("A creature wishes to spawn!");
		}
    	LivingEntity theBorn = (LivingEntity) event.getEntity();
    	if (plugin.worldConfigs.get(theBorn.getWorld().getName()).disableNormalMonsters) {
    		if (plugin.debug) {
    			System.out.println("Pass judgement on him!");
    		}
    		if (theBorn instanceof Monster) {
    			if (plugin.mobSpawns.contains(theBorn)) {
    				if (plugin.debug) {
            			System.out.println("You may live...");
            		}
    			} else {
    				event.setCancelled(true);
            		if (plugin.debug) {
            			System.out.println("Normal Spawn canceled!");
            		}
    			}
    		} else {
    			if (plugin.debug) {
        			System.out.println("Just an innocent ");
        		}
    		}
    	}
    	plugin.mobSpawns.removeLastOccurrence(theBorn);
    }
}

