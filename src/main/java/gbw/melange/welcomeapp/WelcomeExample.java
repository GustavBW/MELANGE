package gbw.melange.welcomeapp;

import gbw.melange.core.MelangeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>WelcomeExample class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class WelcomeExample {

    private static final Logger log = LoggerFactory.getLogger(WelcomeExample.class);

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        MelangeApplication.run(WelcomeExample.class);
    }

}
