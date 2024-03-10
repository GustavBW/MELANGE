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

public class Selector {

    public static final String FALL_BACK_DIR = "/fallback";
    public static final String ERR_IMAGE_DIR = "/errors";
    public static final String INTERNAL_ASSET_DIR = "/assets";

    public static FileHandle randomErrFallBack() throws IOException {
        //"." is prj root
        File[] unfiltered = new File("/assets" + FALL_BACK_DIR + ERR_IMAGE_DIR).listFiles();
        assert unfiltered != null;
        List<File> filtered = acceptOnly(unfiltered, Set.of("png", "jpeg", "gif"));
        if(filtered.size() == 1){
            return Gdx.files.internal(isolateAsPathFromInternal(filtered.get(0)));
        }
        int rndIndex = MathUtils.round(MathUtils.random() * (filtered.size() -1));
        return Gdx.files.internal(isolateAsPathFromInternal(filtered.get(rndIndex)));
    }

    private static List<File> acceptOnly(File[] files, Set<String> fileExtensions){
        List<File> out = new ArrayList<>();
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
