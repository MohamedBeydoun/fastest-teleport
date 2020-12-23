package com.fastestteleport;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("fastestteleport")
public interface FastestTeleportConfig extends Config
{
	@Range(
			min = 1,
			max = 3
	)
	@ConfigItem(
			keyName = "closestTeleportCount",
			name = "Closest Teleport Count",
			description = "How many nearby teleports will be shown on the map?"
	)
	default int closestTeleportCount() {
		return 1;
	}
}
