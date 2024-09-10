package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.Permissions;
import com.plotsquared.general.commands.CommandDeclaration;

@CommandDeclaration(
        command = "extract",
        usage = "/plot extract",
        requiredType = RequiredType.NONE,
        category = CommandCategory.SETTINGS,
        confirmation = true
)
public class Extract extends SubCommand {

    @Override
    public boolean onCommand(PlotPlayer player, String[] args) {
        Location loc = player.getLocation();
        Plot plot = loc.getPlotAbs();

        if (plot == null) {
            return !sendMessage(player, C.NOT_IN_PLOT);
        }
        if (!plot.hasOwner()) {
            return !sendMessage(player, C.PLOT_UNOWNED);
        }
        if (!plot.isOwner(player.getUUID()) && !Permissions.hasPermission(player, C.PERMISSION_ADMIN_COMMAND_UNLINK)) {
            return sendMessage(player, C.NO_PLOT_PERMS);
        }
        if(plot.getArea().TERRAIN == 3) {
            player.sendMessage("§cThis command is disabled for area TERRAIN=3.");
            return true;
        }
        if (!plot.isMerged()) {
            return sendMessage(player, C.UNLINK_IMPOSSIBLE);
        }

        if(!plot.unlinkSinglePlot(true, true)) {
            return sendMessage(player, C.UNLINK_IMPOSSIBLE);
        }

        return sendMessage(player, C.UNLINK_SUCCESS);
    }
}
