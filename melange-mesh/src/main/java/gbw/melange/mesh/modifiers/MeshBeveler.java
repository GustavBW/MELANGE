package gbw.melange.mesh.modifiers;

import gbw.melange.common.mesh.modifiers.IBevelConfig;
import gbw.melange.common.mesh.modifiers.IBevelModifier;
import gbw.melange.common.mesh.modifiers.MeshModifier;
import gbw.melange.common.mesh.formatting.IMeshDataTable;

/**
 * @author GustavBW
 */
public class MeshBeveler implements IBevelModifier {

    public static final MeshBeveler DEFAULT = new MeshBeveler(BevelConfig.DEFAULT);
    public static final MeshBeveler NOOP = new MeshBeveler(BevelConfig.NONE);
    private int vertNum;
    private double angleThreshDeg;
    private double absRelDist;
    public MeshBeveler(IBevelConfig config){
        vertNum = config.subdivs();
        angleThreshDeg = config.angleThreshold();
        absRelDist = config.absoluteRelativeDistance();
    }

    @Override
    public void setResolution(int num) {
        this.vertNum = num;
    }
    @Override
    public void setAngleThreshold(double angleDeg) {
        this.angleThreshDeg = angleDeg;
    }
    @Override
    public void setWidth(double absoluteRelativeDistance) {
        this.absRelDist = absoluteRelativeDistance;
    }

    @Override
    public IMeshDataTable apply(IMeshDataTable table) {



        return null; //New table, a MeshDataTable is immutable
    }

    @Override
    public MeshModifier copy() {
        return new MeshBeveler(new BevelConfig(vertNum, angleThreshDeg, absRelDist));
    }
}
