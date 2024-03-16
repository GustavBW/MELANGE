package gbw.melange.welcomeapp;

import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.MelangeConfig;
import gbw.melange.core.MelangeApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * @author GustavBW
 * @version $Id: $Id
 */
public class WelcomeExample {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        Set<IMelangeConfig.LogLevel> logLevel = new HashSet<>(Set.of(IMelangeConfig.LogLevel.values()));

        IMelangeConfig config = new MelangeConfig()
            .enableGLDebug(true)
            .setLogLevel(logLevel)
            .clearGeneratedOnStart(true)
                .useCaching(false);

        MelangeApplication.run(WelcomeExample.class, config);
    }

}
