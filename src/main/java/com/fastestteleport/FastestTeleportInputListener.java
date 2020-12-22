package com.fastestteleport;

import net.runelite.client.input.MouseListener;
import javax.inject.Inject;
import java.awt.event.MouseEvent;

public class FastestTeleportInputListener implements MouseListener {
    private final FastestTeleportPlugin plugin;
    private final FastestTeleportConfig config;

    @Inject
    private FastestTeleportInputListener(FastestTeleportPlugin plugin, FastestTeleportConfig config)
    {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public MouseEvent mouseClicked(MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton() == 1 && (mouseEvent.getClickCount() == 2))
        {
            plugin.execute();
        }

        return mouseEvent;
    }

    @Override
    public MouseEvent mousePressed(MouseEvent mouseEvent) { return mouseEvent; }

    @Override
    public MouseEvent mouseReleased(MouseEvent mouseEvent) { return mouseEvent; }

    @Override
    public MouseEvent mouseEntered(MouseEvent mouseEvent) { return mouseEvent; }

    @Override
    public MouseEvent mouseExited(MouseEvent mouseEvent) { return mouseEvent; }

    @Override
    public MouseEvent mouseDragged(MouseEvent mouseEvent) { return mouseEvent; }

    @Override
    public MouseEvent mouseMoved(MouseEvent mouseEvent) { return mouseEvent; }
}
