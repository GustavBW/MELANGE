package gbw.melange.shading.generative.voronoi;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.Vector2;
import gbw.melange.shading.*;
import gbw.melange.shading.constants.InterpolationType;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.constants.Vec2DistFunc;
import gbw.melange.shading.services.IShaderPipeline;
import gbw.melange.shading.generative.noise.NoiseProvider;
import gbw.melange.shading.generative.noise.PerlinNoise;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>VoronoiFragmentBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class VoronoiFragmentBuilder implements IVoronoiFragmentBuilder {
    private static final Logger log = LogManager.getLogger();

    private final List<Vector2> points = new ArrayList<>(); // Vec2 could be a custom class or a placeholder for 2D points
    private final String localName;
    private final IShaderPipeline pipeline;
    private Vec2DistFunc distanceType = Vec2DistFunc.EUCLIDEAN;
    private InterpolationType interpolationType = InterpolationType.HERMIT;

    public VoronoiFragmentBuilder(String localName, IShaderPipeline pipeline) {
        this.localName = localName;
        this.pipeline = pipeline;
    }

    @Override
    public IVoronoiFragmentBuilder addPoint(double x, double y) {
        this.points.add(new Vector2((float) x, (float) y));
        return this;
    }
    @Override
    public IVoronoiFragmentBuilder vertsAsPoints(Mesh mesh){
        int numVertices = mesh.getNumVertices();
        int vertexSize = mesh.getVertexSize() / 4; // Vertex size in floats (4 bytes per float)
        float[] vertices = new float[numVertices * vertexSize];
        mesh.getVertices(vertices);

        VertexAttribute posAttr = mesh.getVertexAttribute(VertexAttributes.Usage.Position);
        int offset = posAttr.offset / 4; // Offset in floats

        for (int i = 0; i < numVertices; i++) {
            float x = vertices[i * vertexSize + offset];
            float y = vertices[i * vertexSize + offset + 1];
            // Ignore z: float z = vertices[i * stride + offset + 2];
            this.addPoint(x, y); // Add the extracted point to your Voronoi diagram
        }

        return this;
    }

    @Override
    public IVoronoiFragmentBuilder addRandomPoints(int num){
        return addRandomPoints(num, new PerlinNoise());
    }

    @Override
    public IVoronoiFragmentBuilder addRandomPoints(int num, NoiseProvider provider){
        for(int i = 0; i < num; i++){
            addPoint(provider.getNext(), provider.getNext());
        }
        return this;
    }

    @Override
    public IVoronoiFragmentBuilder setDistanceType(Vec2DistFunc type) {
        this.distanceType = type;
        return this;
    }

    @Override
    public IVoronoiFragmentBuilder setInterpolationType(InterpolationType interpolationType){
        this.interpolationType = interpolationType;
        return this;
    }

    private static String generateDistanceFunction(Vec2DistFunc distanceType){
        return "\nfloat distance(vec2 pointA, vec2 pointB){ // " + distanceType + " \n" +
                    "\treturn " + distanceType.glslCode + "\n" +
                "}\n";
    }
    private static String generateColorInterpolationFunction(InterpolationType type){
        if (type == InterpolationType.NONE){
            return """
            float interpolateDistance(float dist, float min, float max) { // InterpolationType.NONE
                return dist;
            }
            """;
        }

        String functionBody = "//" + type + "\n" + """
        float interpolateDistance(float dist, float min, float max) {
            float normalizedDist = (dist - min) / (max - min); // Normalize dist to 0..1 based on min and max
            normalizedDist = clamp(normalizedDist, 0.0, 1.0); // Ensure normalizedDist stays within 0..1
        """;

        return switch (type) {
            case NONE -> functionBody + "\treturn normalizedDist;\n}\n";
            case HERMIT, QUADRATIC -> functionBody + "\treturn smoothstep(0.0, 1.0, normalizedDist);\n}\n";
            case LINEAR -> functionBody + "\treturn normalizedDist;\n}\n"; // Linear normalization is directly equivalent
            case LOGARITHMIC -> functionBody + "\treturn log(1.0 + normalizedDist) / log(2.0);\n}\n"; // Adjusted for a more suitable logarithmic interpolation
        };
    }

    @Override
    public IVoronoiShader build() {
        String code = glslDeclarations +
                generateDistanceFunction(distanceType) +
                generateColorInterpolationFunction(interpolationType) +
                glslMainLoop;

        FragmentShader fragment = new FragmentShader(localName, code, ShaderClassification.COMPLEX, true);
        log.trace("Generated fragment shader henceforth known as: " + localName + "\n " + fragment.code());
        IVoronoiShader wrapped = new VoronoiShader(localName, VertexShader.DEFAULT, fragment, !points.isEmpty());
        if (pipeline != null) {
            log.trace(localName + " registered to pipeline " + pipeline);
            pipeline.registerForCompilation(wrapped);
        }

        if(!points.isEmpty()){
            VecUtil.redistribute(points, 0, 1);
            wrapped.setPoints(points);
        }
        return wrapped;
    }

    private static final String glslDeclarations =
    """
    #ifdef GL_ES
        precision mediump float;
    #endif
    """ +
    "uniform vec2 "+ VoronoiShaderAttr.POINTS+"["+IVoronoiFragmentBuilder.MAX_NUM_POINTS+"];\n" +
    "uniform int u_num"+VoronoiShaderAttr.POINTS+";\n" +
    """
    varying vec2 v_texCoords;
    """;
    private static final String glslMainLoop =
    """
    void main() {
        vec2 fragCoord = v_texCoords;
        float minDist = 1.0e10;
        float maxDist = 1.0e-10;
    """ +
    "\tfor (int i = 0; i < u_num"+VoronoiShaderAttr.POINTS+"; i++) {\n" +
      "\t\tfloat dist = distance(fragCoord, "+VoronoiShaderAttr.POINTS+"[i]);\n" +
    """
            if (dist < minDist) {
                minDist = dist;
            }
            if(dist > maxDist) {
                maxDist = dist;
            }
        }
        float mapped = interpolateDistance(minDist, 0.0, maxDist);
        vec4 color = vec4(mapped, mapped, mapped, 1.0);
        gl_FragColor = color;
    }
    """;
}
