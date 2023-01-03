package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MathMan;
import com.plotsquared.general.commands.CommandDeclaration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@CommandDeclaration (
        command = "setorder",
        permission = "plots.set.order",
        description = "Set the plot order",
        usage = "/plot setorder <value>",
        category = CommandCategory.SETTINGS,
        requiredType = RequiredType.PLAYER )
public class SetOrder extends SetCommand {

    @Override
    public boolean set( PlotPlayer player, Plot plot, String value ) {
        Plot basePlot = plot.getBasePlot( false );

        if ( !basePlot.hasOwner() ) {
            player.sendMessage( "§cDieses Grundstück hat keinen Besitzer." );
            return false;
        }

        if ( !MathMan.isInteger( value ) ) {
            player.sendMessage( "§cBitte gebe eine Zahl an." );
            return false;
        }

        UUID plotOwner = new ArrayList<>( basePlot.getOwners() ).get( 0 );
        List<Plot> plotList = PS.get()
                .getPlots( basePlot.hasOwner() ? plotOwner : player.getUUID() )
                .stream().filter( p -> !p.equals( basePlot ) )
                .sorted( Comparator.comparingLong( Plot::getTimestamp ) )
                .collect( Collectors.toList() );

        int index = Math.min(Math.max(Integer.parseInt(value), 1) - 1, plotList.size());

        if (plotList.size() > 0) {
            long targetTime;
            if (index == 0) {
                targetTime = plotList.get(0).getTimestamp();
            } else {
                targetTime = plotList.get(index - 1).getTimestamp() + 1;
            }

            basePlot.setTimestamp(targetTime);
            plotList.stream()
                    .filter(p -> p.getTimestamp() >= targetTime)
                    .forEach(p -> {
                        long nextTimestamp = p.getTimestamp() + 1;
                        p.setTimestamp(nextTimestamp);
                        DBFunc.setTimestamp(p, nextTimestamp);
                    });
            DBFunc.setTimestamp(basePlot, basePlot.getTimestamp());
        }

        player.sendMessage( "§aDu hast die Reihenfolge erfolgreich gesetzt." );

        return true;
    }
}
