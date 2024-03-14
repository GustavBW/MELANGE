package gbw.melange.common.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>Selector class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class Selector {

    /** Constant <code>FALL_BACK_DIR="/fallback"</code> */
    public static final String FALL_BACK_DIR = "/system/fallback";
    /** Constant <code>ERR_IMAGE_DIR="/errors"</code> */
    public static final String ERR_IMAGE_DIR = "/errors";
    /** Constant <code>INTERNAL_ASSET_DIR="/assets"</code> */
    public static final String INTERNAL_ASSET_DIR = "/assets";

    /**
     * <p>randomErrFallBack.</p>
     *
     * @return a {@link com.badlogic.gdx.files.FileHandle} object
     * @throws java.io.IOException if any.
     */
    public static FileHandle randomErrFallBack() throws IOException {
        //"." is prj root
        File[] unfiltered = new File("/assets" + FALL_BACK_DIR + ERR_IMAGE_DIR).listFiles();
        assert unfiltered != null;
        List<File> filtered = acceptOnly(unfiltered, Set.of("png", "jpeg", "jpg", "gif"));
        if(filtered.size() == 1){
            return Gdx.files.internal(isolateAsPathFromInternal(filtered.get(0)));
        }
        int rndIndex = MathUtils.round(MathUtils.random() * (filtered.size() -1));
        return Gdx.files.internal(isolateAsPathFromInternal(filtered.get(rndIndex)));
    }

    private static List<File> acceptOnly(File[] files, Set<String> fileExtensions){
        final List<File> out = new ArrayList<>();
        for(File file : files){
            if(fileExtensions.contains(isolateExtension(file))){
                out.add(file);
            }
        }
        return out;
    }

    private static String isolateExtension(File file){
        return file.getName().split("\\.")[1];
    }

    private static String isolateAsPathFromInternal(File file){
        String fullPath = file.getPath();
        List<String> pathParts = Arrays.stream(fullPath.split("/")).toList();
        int indexOfAssets = pathParts.indexOf(INTERNAL_ASSET_DIR);
        List<String> pathFromAssets = pathParts.subList(indexOfAssets, pathParts.size() - 1);
        StringBuilder sb = new StringBuilder();
        for(String s : pathFromAssets){
            sb.append("/").append(s);
        }
        return sb.toString();
    }

}
