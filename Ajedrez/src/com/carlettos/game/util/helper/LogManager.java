package com.carlettos.game.util.helper;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author Carlettos
 */
public final class LogManager {
    private static boolean started = false;
    private static final Logger LOG = Logger.getLogger("ola");
    private static final Formatter LOG_FORMATTER = new Formatter() {
        @Override
        public String format(LogRecord lr) {
            var sb = new StringBuilder();
            sb.append(lr.getLevel()).append("[").append(lr.getSourceClassName()).append("::")
                    .append(lr.getSourceMethodName()).append("][")
                    .append(DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneId.systemDefault()).format(lr.getInstant()))
                    .append("]: ");
            sb.append(lr.getMessage());
            return sb.append('\n').toString();
        }
    };

    private LogManager() {}

    public static final void startLogger() {
        if (!started) {
            LOG.setLevel(Level.FINE);
            try {
                FileHandler fh = new FileHandler(FileHelper.LOG_FILE);
                LOG.addHandler(fh);
                fh.setFormatter(LOG_FORMATTER);
                for (var handler : LOG.getParent().getHandlers()) {
                    handler.setFormatter(LOG_FORMATTER); // setting formatter to the console
                }
            } catch (IOException | SecurityException ex) {
                LOG.log(Level.SEVERE, "Exception starting logger", ex);
            }
            LOG.info("Logger start");
        } else {
            LOG.warning("Trying to start an already started logger");
        }
    }

    public static void log(Level level, String str, Object... objects) {
        LOG.log(level, str.formatted(objects));
    }

    public static void severe(String str, Object... objects) {
        LOG.severe(str.formatted(objects));
    }

    public static void warning(String str, Object... objects) {
        LOG.warning(str.formatted(objects));
    }

    public static void info(String str, Object... objects) {
        LOG.info(str.formatted(objects));
    }

    public static void config(String str, Object... objects) {
        LOG.config(str.formatted(objects));
    }

    public static void fine(String str, Object... objects) {
        LOG.fine(str.formatted(objects));
    }

    public static void finer(String str, Object... objects) {
        LOG.finer(str.formatted(objects));
    }

    public static void finest(String str, Object... objects) {
        LOG.finest(str.formatted(objects));
    }
}
