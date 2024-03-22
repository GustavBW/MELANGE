package gbw.melange.tooling;

import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.events.OnClick;
import gbw.melange.common.events.interactions.Key;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.MelangeApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevTools implements OnRender {
    private static final Logger log = LogManager.getLogger();

    private SuperDicyInternalReferences sdir = null;
    private float xTrans = 0, yTrans = 0, zTrans = 0;

    private boolean[] enabled = new boolean[]{false};

    @Autowired
    public DevTools(OnClick click){
        click.keyDown(Key.F1, () -> {
            log.info("Enabling Dev tools");
            enabled[0] = true;
        });
        click.whileKeyHeld(Key.W, () -> zTrans -= .1f);
        click.whileKeyHeld(Key.A, () -> xTrans += .1f);
        click.whileKeyHeld(Key.S, () -> zTrans += .1f);
        click.whileKeyHeld(Key.D, () -> xTrans -= .1f);
        click.whileKeyHeld(Key.SPACE, () -> yTrans += .1f);
        click.whileKeyHeld(Key.SHIFT_LEFT, () -> yTrans -= .1f);
    }

    public void setSdir(SuperDicyInternalReferences sdir){
        this.sdir = sdir;
    }

    @Override
    public void onRender(double deltaT) {
        if(sdir.mainCam() != null){
            sdir.mainCam().position.set(xTrans, yTrans, zTrans);
            sdir.mainCam().update();
        }
    }
}
