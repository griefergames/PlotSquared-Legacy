package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MathMan;
import com.intellectualcrafters.plot.util.StringMan;
import com.plotsquared.general.commands.CommandDeclaration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
public class SetOrder extends SubCommand {

    @Override
    public boolean onCommand( PlotPlayer player, String[] args ) {
        Location loc = player.getLocation();
        Plot plot = loc.getPlotAbs();
        if ( plot == null ) {
            return !sendMessage( player, C.NOT_IN_PLOT );
        }
        Plot basePlot = plot.getBasePlot( false );

        if ( !basePlot.hasOwner() ) {
            player.sendMessage( "§8[§6GrieferGames§8] §cDieses Grundstück hat keinen Besitzer." );
            return false;
        }

        if ( !basePlot.isOwner( player.getUUID() ) ) {
            player.sendMessage( "§8[§6GrieferGames§8] §cDiese Aktion kann nur der Besitzer des Grundstücks ausführen." );
            return false;
        }

        String value = StringMan.join( args, " " );
        if ( !MathMan.isInteger( value ) ) {
            player.sendMessage( "§8[§6GrieferGames§8] §7Bitte gib eine Zahl an." );
            return false;
        }

        try {
            int ordering = Integer.parseInt( value );
            if ( ordering <= 0 ) {
                player.sendMessage( "§8[§6GrieferGames§8] §7Bitte gib eine Zahl ab der Zahl 1 an." );
                return false;
            }

            if ( ordering > PS.get().getBasePlots( player ).size() ) {
                player.sendMessage( "§8[§6GrieferGames§8] §7Bitte gib für die Position eine Zahl zwischen 1 und " + PS.get().getBasePlots( player ).size() + " an." );
                return false;
            }

            if ( basePlot.getOrder() != null && basePlot.getOrder() == ordering ) {
                player.sendMessage( "§8[§6GrieferGames§8] §7Dieses Grundstück befindet sich bereits auf Position §e" + ordering + "§7.");
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
                player.sendMessage( "§8[§6GrieferGames§8] §7Bitte gib eine Zahl zwischen 1 und " + plots.size() + " an." );
                return false;
            }
        } catch ( Exception e ) {
            player.sendMessage( "§8[§6GrieferGames§8] §7Bitte gib für die Position eine Zahl zwischen 1 und " + PS.get().getBasePlots( player ).size() + " an." );
        }
        return false;
    }
}
