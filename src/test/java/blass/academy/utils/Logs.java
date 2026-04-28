package blass.academy.utils;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Formatter;
import java.util.Map;

public class Logs {
    private static final Logger log = LogManager.getLogger("AUTOMATION");

    // Niveles de log
    private enum Level {TRACE, DEBUG, INFO, WARN, ERROR, FATAL}

    // Buffers separados por nivel
    private static final ThreadLocal<Map<Level, StringBuilder>> allureBuffers = ThreadLocal.withInitial(() -> {
        final var map = new EnumMap<Level, StringBuilder>(Level.class);
        for (var lvl : Level.values()) {
            map.put(lvl, new StringBuilder());
        }
        return map;
    });

    private static void append(Level level, String message) {
        for (var lvl : Level.values()) {
            if (shouldInclude(lvl, level)) {
                allureBuffers.get().get(lvl).append(String.format("[%-5s] %s%n", level, message));
            }
        }
    }

    private static boolean shouldInclude(Level bufferLevel, Level messageLevel) {
        return messageLevel.ordinal() >= bufferLevel.ordinal();
    }

    public static void flushToAllure(Scenario scenario) {
        final var buffers = allureBuffers.get();
        for (var entry : buffers.entrySet()) {
            final var content = entry.getValue().toString();
            if (!content.isEmpty()) {
                final var nombre = String.format("%s-Logs", entry.getKey());
                final var contentBytes = content.getBytes(StandardCharsets.UTF_8);
                scenario.attach(contentBytes, "text/plain", nombre);
            }
        }
        allureBuffers.remove(); // limpiar para el próximo test
    }

    public static void trace(String message) {
        log.trace(message);
        append(Level.TRACE, message);
    }

    public static void debug(String message) {
        log.debug(message);
        append(Level.DEBUG, message);
    }

    public static void info(String message) {
        log.info(message);
        append(Level.INFO, message);
    }

    public static void warning(String message) {
        log.warn(message);
        append(Level.WARN, message);
    }

    public static void error(String message) {
        log.error(message);
        append(Level.ERROR, message);
    }

    public static void fatal(String message) {
        log.fatal(message);
        append(Level.FATAL, message);
    }

    // --- Sobrecargas con formato ---
    public static void info(String format, Object... args) {
        final var mensaje = new Formatter().format(format, args).toString();
        info(mensaje);
    }

    public static void debug(String format, Object... args) {
        final var mensaje = new Formatter().format(format, args).toString();
        debug(mensaje);
    }

    public static void warning(String format, Object... args) {
        final var mensaje = new Formatter().format(format, args).toString();
        warning(mensaje);
    }

    public static void error(String format, Object... args) {
        final var mensaje = new Formatter().format(format, args).toString();
        error(mensaje);
    }

    public static void fatal(String format, Object... args) {
        final var mensaje = new Formatter().format(format, args).toString();
        fatal(mensaje);
    }

    public static void trace(String format, Object... args) {
        final var mensaje = new Formatter().format(format, args).toString();
        trace(mensaje);
    }
}
