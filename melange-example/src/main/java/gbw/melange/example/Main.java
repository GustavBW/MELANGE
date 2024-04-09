package gbw.melange.example;

import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.MelangeConfig;
import gbw.melange.core.app.MelangeApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Main {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        Set<IMelangeConfig.LogLevel> logLevel = new HashSet<>(Set.of(IMelangeConfig.LogLevel.values()));

        IMelangeConfig config = new MelangeConfig()
                .enableGLDebug(false)
                .setLoggingAspects(logLevel)
                .clearGeneratedOnStart(true)
                .useCaching(true);

        log.info("launching Melange - logged");
        System.out.println("launching Melange - sout");
        MelangeApplication.run(Main.class, config);
    }
}