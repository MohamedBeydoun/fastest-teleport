package com.fastestteleport;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.RenderOverview;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

import java.awt.Rectangle;

public class FastestTeleportMapPoint {

    private Client client;
    private WorldMapOverlay worldMapOverlay;

    FastestTeleportMapPoint(Client client, WorldMapOverlay worldMapOverlay) {
        this.client = client;
        this.worldMapOverlay = worldMapOverlay;
    }

    //	isMouseInWorldMap checks if the mouse position is inside the world map widget boundaries
    public boolean isMouseInWorldMap() {
        final Point mousePos = client.getMouseCanvasPosition();
        final Widget map = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);
        if (map == null) {
            return false;
        }

        final Rectangle worldMapBounds = map.getBounds();

        return worldMapBounds.contains(mousePos.getX(), mousePos.getY());
    }

    // mapPointToWorldPoint converts a MapPoint to a WorldPoint
    public WorldPoint toWorldPoint(RenderOverview renderOverview, Point mousePos, float zoom) {
        final WorldPoint mapPoint = new WorldPoint(renderOverview.getWorldMapPosition().getX(), renderOverview.getWorldMapPosition().getY(), 0);
        final Point middle = worldMapOverlay.mapWorldPointToGraphicsPoint(mapPoint);

        final int dx = (int) ((mousePos.getX() - middle.getX()) / zoom);
        final int dy = (int) ((-(mousePos.getY() - middle.getY())) / zoom);

        return mapPoint.dx(dx).dy(dy);
    }
}
