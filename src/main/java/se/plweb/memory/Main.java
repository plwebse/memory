package se.plweb.memory;

import se.plweb.memory.gui.Gui;

import java.util.Arrays;
import java.util.logging.*;

/**
 * @author Peter Lindblom
 */

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Gui(getVersionInfo());

        debugLogging(args);
    }

    private static String getVersionInfo() {

        if (Package.getPackage("se.plweb.memory").getImplementationTitle() != null) {
            return "plweb.se / "
                    + Package.getPackage("se.plweb.memory")
                    .getImplementationTitle()
                    + " "
                    + Package.getPackage("se.plweb.memory")
                    .getImplementationVersion();
        }
        return "plweb.se";
    }

    private static void debugLogging(String[] args) {
        if (Arrays.stream(args).anyMatch("debug"::equalsIgnoreCase)) {
            try {
                Handler handler;
                Formatter formatter = new SimpleFormatter();

                handler = new ConsoleHandler();
                handler.setFormatter(formatter);
                Logger.getLogger("").addHandler(handler);
                Logger.getLogger("se.plweb").setLevel(Level.ALL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger.getLogger("se.plweb").setLevel(Level.OFF);
        }
    }
}
