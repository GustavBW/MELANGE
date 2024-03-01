package gbw.melange;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gbw.melange.host.CurrentMonitorMonitor;

import java.awt.geom.Dimension2D;

public class Application extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private final CurrentMonitorMonitor hostMonitor = new CurrentMonitorMonitor();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("melange-icon-v1.png");
	}

	@Override
	public void render () {
		hostMonitor.onDrawCall();
		Dimension2D screenDim = hostMonitor.getScreenDim();

		batch.begin();
		batch.draw(img, 0, 0, (float) screenDim.getWidth(), (float) screenDim.getHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
