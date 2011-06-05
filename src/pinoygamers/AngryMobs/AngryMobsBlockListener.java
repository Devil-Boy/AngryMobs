package pinoygamers.AngryMobs;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * AngryMobs block listener
 * @author pinoygamers
 */
public class AngryMobsBlockListener extends BlockListener {
    private final AngryMobs plugin;

    public AngryMobsBlockListener(final AngryMobs plugin) {
        this.plugin = plugin;
    }

    //put all Block related code here
}
