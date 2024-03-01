package gbw.melange;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gbw.melange.observance.IPristineObservableValue;
import gbw.melange.observance.ObservableValue;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class Application extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private final IPristineObservableValue<Dimension2D> screenDim = ObservableValue.pristine(null,
			(old, newer) -> {
				if(newer == null && old != null) return true;
				if(newer != null && old == null) return false;
				if(newer != null) return (old.getWidth() == newer.getWidth()) && (old.getHeight() == newer.getHeight());
				return true;
			});
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("melange-icon-v1.png");
	}

	@Override
	public void render () {
		screenDim.set(Toolkit.getDefaultToolkit().getScreenSize());

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
