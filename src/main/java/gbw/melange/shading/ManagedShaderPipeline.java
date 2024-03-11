package gbw.melange.shading;

import gbw.melange.common.errors.ShaderCompilationIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ManagedShaderPipeline {

    private static final Logger log = LoggerFactory.getLogger(ManagedShaderPipeline.class);
    private static final ManagedShaderPipeline instance = new ManagedShaderPipeline();

    private final Queue<ShaderProgramWrapper> unCompiled = new ConcurrentLinkedQueue<>();

    public static void add(ShaderProgramWrapper uncompiled){
        log.info("Registered: " + uncompiled);
        instance.unCompiled.add(uncompiled);
    }

    public static void run() throws ShaderCompilationIssue {
        List<ShaderProgramWrapper> recentlyCompiled = new ArrayList<>();
        while(instance.unCompiled.peek() != null){
            ShaderProgramWrapper wrapped = instance.unCompiled.poll();
            wrapped.compile();
            recentlyCompiled.add(wrapped);
        }
        log.info("Compile step for: " + recentlyCompiled.stream().map(ShaderProgramWrapper::shortName).toList());
    }

}
