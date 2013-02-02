package com.thevoxelbox.voxelguest.modules.regions;

import com.thevoxelbox.voxelguest.modules.regions.listener.PlayerEventListener;
import com.thevoxelbox.voxelguest.modules.regions.listener.BlockEventListener;
import com.thevoxelbox.voxelguest.modules.GuestModule;
import com.thevoxelbox.voxelguest.modules.regions.command.RegionCommand;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;

/**
 * @author Butters
 */
public class RegionModule extends GuestModule
{
	private List<Region> regions = new ArrayList<>();
	private BlockEventListener blockEventListener;
        private PlayerEventListener playerEventListener;
        private RegionCommand regionCommand;

	public RegionModule()
	{
		setName("Region Module");

		blockEventListener = new BlockEventListener(this);
                playerEventListener = new PlayerEventListener(this);
                regionCommand = new RegionCommand(this);
	}

	@Override
	public final void onEnable()
	{
		super.onEnable();
	}

	@Override
	public final void onDisable()
	{
		super.onDisable();
	}

	@Override
	public final HashSet<Listener> getListeners()
	{
		final HashSet<Listener> listeners = new HashSet<>();
		listeners.add(blockEventListener);
                listeners.add(blockEventListener);

		return listeners;
	}

	@Override
	public Object getConfiguration()
	{
		return null;
	}

	@Override
	public String getConfigFileName()
	{
		return "region";
	}
        
        @Override
        public HashMap<String, CommandExecutor> getCommandMappings()
        {
                HashMap<String, CommandExecutor> commandMappings = new HashMap<>();
                commandMappings.put("vgregion", regionCommand);
                return commandMappings;
        }
        
        public boolean addRegion(Region region)
        {
            if(region != null)
            {
                regions.add(region);
                Bukkit.getLogger().info("Created region: " + region.getRegionName());
                return true;
            }
            return false;
        }

	public final Region getRegionAtLocation(final Location regionLocation)
	{
		for (Region region : regions)
		{
			if (region.isLocationInRegion(regionLocation))
			{
				return region;
			}
		}
		return null;
	}

}
