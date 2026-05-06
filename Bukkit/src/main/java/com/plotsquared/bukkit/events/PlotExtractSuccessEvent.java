package com.plotsquared.bukkit.events;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class PlotExtractSuccessEvent extends PlotEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Set<Plot> previousConnectedPlots;
    private final PlotPlayer plotPlayer;
    private final Player player;

    public PlotExtractSuccessEvent(Plot plot, PlotPlayer plotPlayer, Player player, Set<Plot> previousConnectedPlots) {
        super(plot);
        this.plotPlayer = plotPlayer;
        this.player = player;
        this.previousConnectedPlots = previousConnectedPlots;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlotPlayer getPlotPlayer() {
        return this.plotPlayer;
    }

    public Set<Plot> getPreviousConnectedPlots() {
        return this.previousConnectedPlots;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
