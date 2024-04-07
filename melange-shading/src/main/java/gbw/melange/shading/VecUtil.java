package gbw.melange.shading;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Collection;
import java.util.List;

public class VecUtil {

    /**
     * In place translate all vector parameters to within a given range.
     */
    public static void redistribute(List<Vector2> vecs, double lowerRange, double upperRange){
        if (vecs == null || vecs.isEmpty()) return;

        // 1. Find current bounds
        float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
        for (Vector2 v : vecs) {
            if (v.x < minX) minX = v.x;
            if (v.x > maxX) maxX = v.x;
            if (v.y < minY) minY = v.y;
            if (v.y > maxY) maxY = v.y;
        }

        // Calculate current width and height
        float currentWidth = maxX - minX;
        float currentHeight = maxY - minY;

        // 2. Scale vectors
        float targetSize = (float) (upperRange - lowerRange);
        float scaleX = targetSize / currentWidth;
        float scaleY = targetSize / currentHeight;
        float scale = Math.min(scaleX, scaleY); // Ensure aspect ratio is maintained

        for (Vector2 v : vecs) {
            v.x = (v.x - minX) * scale;
            v.y = (v.y - minY) * scale;
        }

        // 3. Translate vectors
        // After scaling, recalculate bounds as they will have changed
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        for (Vector2 v : vecs) {
            if (v.x < minX) minX = v.x;
            if (v.y < minY) minY = v.y;
        }

        float translateX = (float) lowerRange - minX;
        float translateY = (float) lowerRange - minY;
        for (Vector2 v : vecs) {
            v.x += translateX;
            v.y += translateY;
        }

    }

    public static float[] flattenVec2(List<Vector2> vecList) {
        float[] flatArray = new float[vecList.size() * 2]; // Each Vec2 has 2 components
        for (int i = 0; i < vecList.size(); i++) {
            Vector2 vec = vecList.get(i);
            flatArray[i * 2] = vec.x;
            flatArray[i * 2 + 1] = vec.y;
        }
        return flatArray;
    }

    public static float[] flattenVec3(List<Vector3> vecList) {
        float[] flatArray = new float[vecList.size() * 3]; // Each Vec3 has 3 components
        for (int i = 0; i < vecList.size(); i++) {
            Vector3 vec = vecList.get(i);
            flatArray[i * 3] = vec.x;
            flatArray[i * 3 + 1] = vec.y;
            flatArray[i * 3 + 2] = vec.z;
        }
        return flatArray;
    }

}
