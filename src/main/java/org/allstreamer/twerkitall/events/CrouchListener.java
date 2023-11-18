package org.allstreamer.twerkitall.events;

import org.allstreamer.twerkitall.TwerkItAll;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Random;

public class CrouchListener implements Listener {
    final int x_radius = TwerkItAll.config.getInt("x_range");
    final int y_radius = TwerkItAll.config.getInt("y_range");
    final int z_radius = TwerkItAll.config.getInt("z_range");

    final double grow_chance = TwerkItAll.config.getDouble("grow_chance");

    final Random rng = new Random();

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) return;

        final Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) return;

        final World world = player.getWorld();

        for (int x = -x_radius; x <= x_radius; x++) {
            for (int y = -y_radius; y <= y_radius; y++) {
                for (int z = -z_radius; z <= z_radius; z++) {
                    Location location = new Location(world,
                            x+player.getLocation().getX(),
                            y+player.getLocation().getY(),
                            z+player.getLocation().getZ()
                    );
                    final Block block = world.getBlockAt(location);

                    if (rng.nextDouble() < grow_chance) {
                        growBlock(block);
                        spawnParticles(world, block.getBlockData(), location);
                    }
                }
            }
        }
    }

    public void spawnParticles(World world, BlockData block_data, Location location) {
        if (!(block_data instanceof Sapling || block_data instanceof Hatchable || block_data instanceof Ageable)) {
            return;
        }

        world.playEffect(location, Effect.BONE_MEAL_USE, 0);
    }

    public void growBlock(Block block) {
        BlockData block_data = block.getBlockData();
        if (!(block_data instanceof Sapling || block_data instanceof Hatchable || block_data instanceof Ageable)) {
            return;
        }
        try {
            block.applyBoneMeal(BlockFace.UP);
        }catch (NullPointerException e) {
            // TODO: Find out why this throws a null pointer exception
            // TwerkItAll.logger.info(e.toString());
            // TwerkItAll.logger.info(block.toString());
        }
    }
}
