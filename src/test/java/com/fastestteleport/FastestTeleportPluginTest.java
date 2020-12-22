package com.fastestteleport;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class FastestTeleportPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(FastestTeleportPlugin.class);
		RuneLite.main(args);
	}
}