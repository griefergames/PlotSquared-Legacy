package com.plotsquared.bukkit.events;

import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.World;
import org.bukkit.event.HandlerList;

public class PlotMergeSuccessEvent extends PlotEvent {

    private static final HandlerList handlers = new HandlerList();
    private final World world;
    private final int dir;

    /**
     * PlotMergeSuccessEvent: Called when plots are merged successfully
     *
     * @param world World in which the event occurred
     * @param plot  Plot that was merged
     */

    public PlotMergeSuccessEvent(World world, Plot plot, int dir) {
        super(plot);
        this.world = world;
        this.dir = dir;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public World getWorld() {
        return this.world;
    }

    public int getDir() {
        return dir;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
