package gbw.melange.mesh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShapeService {

    private final IMeshPipeline pipeline;

    @Autowired
    public ShapeService(IMeshPipeline pipeline){
        this.pipeline = pipeline;
    }

}
