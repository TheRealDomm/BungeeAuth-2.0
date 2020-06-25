package org.endertools.bungeeauth.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.endertools.bungeeauth.AuthenticatorPlugin;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
@RequiredArgsConstructor
public class PlayerBlockListener implements Listener {

    private final AuthenticatorPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        Location location = new Location(event.getFrom().getWorld(), event.getFrom().getBlockX(),
                event.getFrom().getBlockX(), event.getFrom().getBlockZ());
        event.getPlayer().teleport(location);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event) {
        if (!plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getBlockedPlayer().contains(event.getPlayer().getUniqueId())) {
            plugin.getBlockedPlayer().remove(event.getPlayer().getUniqueId());
        }
    }

}
