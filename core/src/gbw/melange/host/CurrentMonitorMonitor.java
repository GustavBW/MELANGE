package gbw.melange.host;

import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class CurrentMonitorMonitor {

    private Dimension2D screenDim = new Dimension();


    public void onDrawCall()
    {
        ScreenUtils.clear(1, 0, 0, 1);

        screenDim = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public Dimension2D getScreenDim(){
        return screenDim;
    }

}
