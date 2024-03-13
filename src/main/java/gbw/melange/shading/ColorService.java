package gbw.melange.shading;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import gbw.melange.shading.templating.gradients.IGradientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@Service
public class ColorService implements Colors, IShaderPipeline{

    private static final Logger log = LoggerFactory.getLogger(ColorService.class);
    private final Queue<IWrappedShader> unCompiled = new ConcurrentLinkedQueue<>(
            //TODO: Find a better way to register the premade ones. Iterating through enum values?
            List.of(WrappedShader.TEXTURE, WrappedShader.DEFAULT, WrappedShader.NONE)
    );
    private static int nextId = 0;

    @Override
    public IWrappedShader constant(Color color) {
        float r = color.r, g = color.g, b = color.b, a = color.a;
        IWrappedShader wrapped = new WrappedShader("CONSTANT_RGBA_"+(nextId++)+"("+r+","+g+","+b+","+a+")", VertexShader.DEFAULT, FragmentShader.constant(color));
        registerForCompilation(wrapped);
        return wrapped;
    }

    @Override
    public IWrappedShader image(FileHandle src) throws IOException {
        return image(new Texture(src));
    }

    @Override
    public IWrappedShader image(Texture src) {
        Consumer<ShaderProgram> bindTexture = sp -> {
            src.bind(0); //This might be mildly unsafe
            sp.setUniformi("u_texture", 0);
        };
        IWrappedShader wrapped = new WrappedShader("TEXTURE_"+(nextId++), VertexShader.DEFAULT, FragmentShader.TEXTURE, bindTexture);
        registerForCompilation(wrapped);
        return wrapped;
    }

    @Override
    public IGradientBuilder linearGradient() {
        return new GradientFragmentShaderBuilder("GRADIENT_"+(nextId++), this);
    }

    @Override
    public IGradientBuilder radialGradient() {
        //TODO: Implement a radial gradient builder
        return null;
    }

    @Override
    public IWrappedShader fromFragment(FragmentShader fragmentShader) {
        IWrappedShader wrapped = new WrappedShader("CUSTOM_FRAGMENT_"+(nextId++), VertexShader.DEFAULT, fragmentShader);
        registerForCompilation(wrapped);
        return wrapped;
    }

    @Override
    public void compileAll() throws ShaderCompilationIssue {
        List<String> recentlyCompiled = new ArrayList<>(unCompiled.size());
        while(unCompiled.peek() != null){
            IWrappedShader wrapped = unCompiled.poll();
            wrapped.compile();
            recentlyCompiled.add(wrapped.shortName());
        }
        log.info("Compile step complete without issues for: " + recentlyCompiled);
    }

    @Override
    public void registerForCompilation(IWrappedShader shader) {
        //The program is null
        if(shader.getProgram() != null && shader.getProgram().isCompiled()) return;

        unCompiled.add(shader);
    }
}
