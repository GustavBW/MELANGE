package gbw.melange.welcomeapp;

import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.MelangeConfig;
import gbw.melange.core.MelangeApplication;
import gbw.melange.mesh.constants.EVertexAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author GustavBW
 * @version $Id: $Id
 */
public class WelcomeExample {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        Set<IMelangeConfig.LogLevel> logLevel = new HashSet<>(Set.of(IMelangeConfig.LogLevel.values()));

        IMelangeConfig config = new MelangeConfig()
            .enableGLDebug(true)
            .setLoggingAspects(logLevel)
            .clearGeneratedOnStart(true)
            .useCaching(true);

        MelangeApplication.run(WelcomeExample.class, config);
    }

}
