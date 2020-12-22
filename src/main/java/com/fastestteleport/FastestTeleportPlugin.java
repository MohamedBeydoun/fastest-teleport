package com.fastestteleport;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.RenderOverview;
import net.runelite.api.Point;
import net.runelite.api.ChatMessageType;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.input.MouseManager;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

@Slf4j
@PluginDescriptor(
		name = "Fastest Teleport",
		description = "Gives the fastest teleport to a pinged location on the map"
)

public class FastestTeleportPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private FastestTeleportConfig config;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private FastestTeleportInputListener inputListener;

	@Inject
	private WorldMapOverlay worldMapOverlay;

	private FastestTeleportMapPoint mapPointHelper;

	@Override
	protected void startUp() throws Exception {
		mouseManager.registerMouseListener(inputListener);
		this.mapPointHelper = new FastestTeleportMapPoint(client, worldMapOverlay);
	}

	@Override
	protected void shutDown() throws Exception {
		mouseManager.unregisterMouseListener(inputListener);
	}

	@Provides
	FastestTeleportConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(FastestTeleportConfig.class);
	}

	//	execute runs the code to find the fastest teleport to a pinged location
	public void execute() {
		if (mapPointHelper.isMouseInWorldMap()) {
			final Point mousePos = client.getMouseCanvasPosition();
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Map Point", mousePos.toString(), null);

			final RenderOverview renderOverview = client.getRenderOverview();
			final float zoom = renderOverview.getWorldMapZoom();
			final WorldPoint destination = mapPointHelper.toWorldPoint(renderOverview, mousePos, zoom);

			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "World Point", destination.toString(), null);
		}
	}
}
