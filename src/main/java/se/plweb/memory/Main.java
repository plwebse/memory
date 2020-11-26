package se.plweb.memory;

import se.plweb.memory.gui.Gui;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Peter Lindblom
 */

public class Main {
    private static final Package MEMORY_PACKAGE = Main.class.getPackage();

    public static void main(String[] args) {
        new Gui(getVersionInfo());

        debugLogging(args);
    }

    private static String getVersionInfo() {
        return Stream.of(MEMORY_PACKAGE.getImplementationTitle(),
                MEMORY_PACKAGE.getImplementationVersion(),  "plweb.se")
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" / "));
    }

    private static void debugLogging(String[] args) {
        if (Arrays.stream(args).anyMatch("debug"::equalsIgnoreCase)) {
            try {
                Handler handler = new ConsoleHandler();
                Logger.getLogger("se.plweb").addHandler(handler);
                Logger.getLogger("se.plweb").setLevel(Level.ALL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger.getLogger("se.plweb").setLevel(Level.OFF);
        }
    }
}
