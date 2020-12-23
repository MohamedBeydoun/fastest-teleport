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
import java.util.List;
import java.util.ArrayList;

public class FastestTeleportMapOverlay extends Overlay {

    @Inject
    private WorldMapOverlay worldMapOverlay;

    private final Client client;
    private final FastestTeleportPlugin plugin;
    private final FastestTeleportConfig config;
    private final List<Color> colors = new ArrayList<>();

    @Inject
    private FastestTeleportMapOverlay(Client client, FastestTeleportPlugin plugin, FastestTeleportConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.colors.add(new Color(0, 255, 0, 150));
        this.colors.add(new Color(255, 255, 0, 150));
        this.colors.add(new Color(255, 0, 0, 150));
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
            for (int i = 0; i < plugin.starts.size(); i++) {
                graphics.setColor(this.colors.get(i));
                graphics.setStroke(new BasicStroke(5));
                Point start = worldMapOverlay.mapWorldPointToGraphicsPoint(plugin.starts.get(i));
                Point end = worldMapOverlay.mapWorldPointToGraphicsPoint(plugin.destinations.get(i));
                graphics.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            }
        }

        return null;
    }
}
