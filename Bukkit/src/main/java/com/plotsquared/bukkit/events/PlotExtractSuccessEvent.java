package com.plotsquared.bukkit.events;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlotExtractSuccessEvent extends PlotEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final PlotPlayer plotPlayer;
    private final Player player;

    public PlotExtractSuccessEvent(Plot plot, PlotPlayer plotPlayer, Player player) {
        super(plot);
        this.plotPlayer = plotPlayer;
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlotPlayer getPlotPlayer() {
        return this.plotPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
