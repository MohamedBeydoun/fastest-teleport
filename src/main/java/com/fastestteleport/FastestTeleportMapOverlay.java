package com.fastestteleport;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

import javax.inject.Inject;
import java.awt.*;

public class FastestTeleportMapOverlay extends Overlay {

    @Inject
    private WorldMapOverlay worldMapOverlay;

    private final Client client;
    private final FastestTeleportPlugin plugin;
    private final FastestTeleportConfig config;

    @Inject
    private FastestTeleportMapOverlay(Client client, FastestTeleportPlugin plugin, FastestTeleportConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (client.getWidget(WidgetInfo.WORLD_MAP_VIEW) == null) {
            return null;
        }

        if (plugin.drawLine) {
            graphics.setColor(new Color(255, 0, 0, 150));
            graphics.setStroke(new BasicStroke(5));
            Point start = worldMapOverlay.mapWorldPointToGraphicsPoint(plugin.start);
            Point end = worldMapOverlay.mapWorldPointToGraphicsPoint(plugin.destination);
            graphics.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }

        return null;
    }
}
