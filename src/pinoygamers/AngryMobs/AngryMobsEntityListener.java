package pinoygamers.AngryMobs;

import org.bukkit.Location;
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
    	
    }
}

