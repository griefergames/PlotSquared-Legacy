package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MathMan;
import com.plotsquared.general.commands.CommandDeclaration;

import java.util.*;
import java.util.Set;
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
            player.sendMessage( "§8[§6GrieferGames§8] §cDieses Grundstück hat keinen Besitzer." );
            return false;
        }

        if ( !MathMan.isInteger( value ) ) {
            player.sendMessage( "§8[§6GrieferGames§8] §7Bitte gib eine Zahl an." );
            return false;
        }

        int ordering = Integer.parseInt( value );
        if ( ordering <= 0 ) {
            player.sendMessage( "§8[§6GrieferGames§8] §cBitte gebe eine Zahl ab der 1. an" );
            return false;
        }

        if ( basePlot.getOrder() != null && basePlot.getOrder() == ordering ) {
            player.sendMessage( "§8[§6GrieferGames§8] §cDieses Grundstück ist bereits auf Position " + ordering );
            return false;
        }

        UUID plotOwner = new ArrayList<>( basePlot.getOwners() ).get( 0 );
        Set<Plot> plots = PS.get().getPlots( basePlot.hasOwner() ? plotOwner : player.getUUID() );

        if ( ordering <= plots.size() ) {
            Optional<Plot> optional = plots.stream()
                    .filter( plotValue -> !plotValue.equals( basePlot ) )
                    .filter( plotValue -> plotValue.getOrder() != null )
                    .filter( plotValue -> plotValue.getOrder() == ordering ).findFirst();
            optional.ifPresent( findPlot -> findPlot.setOrder( null ) );
            basePlot.setOrder( ordering );
            player.sendMessage( "§8[§6GrieferGames§8] §aDu hast die Reihenfolge erfolgreich geändert." );
            return true;
        } else {
            player.sendMessage( "§8[§6GrieferGames§8] §cBitte gebe eine Zahl zwischen 1 und " + plots.size()  + " an." );
            return false;
        }
    }
}
