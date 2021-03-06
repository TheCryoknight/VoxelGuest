package com.thevoxelbox.voxelguest.modules.asshat.mute;

import com.google.common.base.Preconditions;
import com.thevoxelbox.voxelguest.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a list of muted players.
 *
 * @author Monofraps
 */
public final class Mutelist
{
    /**
     * Mutes a player and stores the reason he was muted for.
     *
     * @param playerName The name of the player to mute.
     * @param muteReason The reason the player is muted for.
     *
     * @return Returns true if the mute operation was successful. False indicates that the player is already muted.
     */
    public static boolean mute(final String playerName, final String muteReason, final boolean selfUngag)
    {
        if (isPlayerMuted(playerName))
        {
            return false;
        }

        Persistence.getInstance().save(new MutedPlayer(playerName.toLowerCase(), muteReason, selfUngag));
        return true;
    }

    /**
     * Mutes a player and stores the reason he was muted for.
     *
     * @param playerName The name of the player to mute.
     * @param muteReason The reason the player is muted for.
     *
     * @return Returns true if the mute operation was successful. False indicates that the player is already muted.
     */
    public static boolean mute(final String playerName, final String muteReason)
    {
        return mute(playerName, muteReason, false);
    }

    /**
     * Unmutes a player.
     *
     * @param playerName The name of the player to unmute.
     *
     * @return Returns true if the unmute operation was successful. False indicates that the player is not muted.
     */
    public static boolean unmute(final String playerName)
    {
        if (!isPlayerMuted(playerName))
        {
            return false;
        }

        Persistence.getInstance().delete(getMutedPlayer(playerName));
        return true;
    }

    /**
     * Generates a list of names containing all people currently muted.
     *
     * @return Returns a list of names of people muted
     */
    public static List<String> getMutedNames()
    {
        final List<MutedPlayer> mutedPlayers = Persistence.getInstance().loadAll(MutedPlayer.class);
        final List<String> mutedNames = new ArrayList<>();
        for (MutedPlayer mutePlayer : mutedPlayers)
        {
            mutedNames.add(mutePlayer.getPlayerName());
        }
        return mutedNames;
    }

    private static MutedPlayer getMutedPlayer(final String playerName)
    {
        HashMap<String, Object> selectRestrictions = new HashMap<>();
        selectRestrictions.put("playerName", playerName.toLowerCase());

        final List<MutedPlayer> mutedPlayers = Persistence.getInstance().loadAll(MutedPlayer.class, selectRestrictions);

        for (MutedPlayer mutedPlayer : mutedPlayers)
        {
            if (mutedPlayer.getPlayerName().equalsIgnoreCase(playerName))
            {
                return mutedPlayer;
            }
        }

        return null;
    }

    /**
     * Checks if a player is muted.
     *
     * @param playerName The name of the player to check.
     *
     * @return Returns true if the player is muted, otherwise false.
     */
    public static boolean isPlayerMuted(final String playerName)
    {
        return getMutedPlayer(playerName) != null;
    }

    /**
     * Gets the reason a player is muted for.
     *
     * @param playerName The name of the player.
     *
     * @return Returns the reason a player is banned for.
     */
    public static String getPlayerMutereason(final String playerName)
    {
        Preconditions.checkState(isPlayerMuted(playerName), "Player %s must be muted in order to get the mute reason.", playerName);
        return getMutedPlayer(playerName).getMuteReason();
    }


    public static boolean isSelfUngaggable(final String playerName)
    {
        if (!isPlayerMuted(playerName))
        {
            return false;
        }

        return getMutedPlayer(playerName).isSelfUngag();
    }

    /**
     * Returns the number of muted players.
     *
     * @return Returns the number of muted players.
     */
    public static int getMuteCount()
    {
        return getMutedNames().size();
    }
}
