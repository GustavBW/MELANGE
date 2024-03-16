package gbw.melange.welcomeapp;

import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.MelangeConfig;
import gbw.melange.core.MelangeApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>WelcomeExample class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class WelcomeExample {

    private static final Logger log = LogManager.getLogger();

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        Set<IMelangeConfig.LogLevel> logLevel = new HashSet<>(Set.of(IMelangeConfig.LogLevel.values()));
        logLevel.remove(IMelangeConfig.LogLevel.BOOT_SEQ_INFO);
        logLevel.remove(IMelangeConfig.LogLevel.SPRING_REFLECT_INFO);
        logLevel.remove(IMelangeConfig.LogLevel.VIEW_INFO);
        logLevel.remove(IMelangeConfig.LogLevel.HOOKS);
        logLevel.remove(IMelangeConfig.LogLevel.ELEMENT_UPDATES);

        IMelangeConfig config = new MelangeConfig()
            .enableGLDebug(true)
            .setLogLevel(logLevel)
            .clearGeneratedOnStart(true);

        MelangeApplication.run(WelcomeExample.class, config);
    }

}
