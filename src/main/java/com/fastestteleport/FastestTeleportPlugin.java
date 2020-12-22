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
import net.runelite.client.ui.overlay.OverlayManager;
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

	@Inject
	private FastestTeleportMapOverlay pathMapOverlay;

	@Inject
	private OverlayManager overlayManager;

	private FastestTeleportMapPoint mapPointHelper;
	public WorldPoint start = null;
	public WorldPoint destination = null;
	public boolean drawLine = false;

	@Override
	protected void startUp() throws Exception {
		mouseManager.registerMouseListener(inputListener);
		this.mapPointHelper = new FastestTeleportMapPoint(client, worldMapOverlay);
		overlayManager.add(pathMapOverlay);
	}

	@Override
	protected void shutDown() throws Exception {
		mouseManager.unregisterMouseListener(inputListener);
		overlayManager.remove(pathMapOverlay);
	}

	@Provides
	FastestTeleportConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(FastestTeleportConfig.class);
	}

	//	execute runs the code to find the fastest teleport to a pinged location
	public void execute() {
		// only execute if mouse is in the map
		if (mapPointHelper.isMouseInWorldMap()) {
			// find mouse position
			final Point mousePos = client.getMouseCanvasPosition();
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Map Point", mousePos.toString(), null);

			// convert mouse position to world position
			final RenderOverview renderOverview = client.getRenderOverview();
			final float zoom = renderOverview.getWorldMapZoom();
			final WorldPoint destination = mapPointHelper.toWorldPoint(renderOverview, mousePos, zoom);
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "World Point", destination.toString(), null);

			// find the fastest teleport
			double shortestDistance = Integer.MAX_VALUE;
			FastestTeleportLocations fastestTeleport = null;
			for (FastestTeleportLocations location : FastestTeleportLocations.values()) {
				int xLocation = location.getLocation().getX();
				int yLocation = location.getLocation().getY();
				int xDestination = destination.getX();
				int yDestination = destination.getY();

				double distance = Math.sqrt((xDestination-xLocation)*(xDestination-xLocation) + (yDestination-yLocation)*(yDestination-yLocation));

				if (distance < shortestDistance) {
					shortestDistance = distance;
					fastestTeleport = location;
				}
			}
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Closest Teleport", fastestTeleport.name(), null);

			// draw line on map
			this.start = fastestTeleport.getLocation();
			this.destination = destination;
			drawLine = true;
		}
	}
}
