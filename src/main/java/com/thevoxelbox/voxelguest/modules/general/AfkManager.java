package com.thevoxelbox.voxelguest.modules.general;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AfkManager
{

    private final Set<String> playersAfk = new HashSet<String>();

    public synchronized void setPlayerAfk(final Player player, final boolean isAfk)
    {
        if (isAfk)
        {
            this.playersAfk.add(player.getName());
            return;
        }
        this.playersAfk.remove(player.getName());
    }

    public void toggleAfk(final Player player)
    {
        if (this.playersAfk.contains(player.getName()))
        {
            this.setPlayerAfk(player, false);
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.DARK_GRAY + " has returned");
            return;
        }
        this.setPlayerAfk(player, true);
    }
    
    public boolean isPlayerAfk(final Player player) {
        return this.playersAfk.contains(player.getName());
    }
}