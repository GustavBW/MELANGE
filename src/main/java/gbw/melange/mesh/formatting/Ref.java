package gbw.melange.mesh.formatting;

public class Ref {

    public record Vec2(float[] source, int indexSelf, int elementOffset){
        public float x(){
            return source[indexSelf * elementOffset];
        }
        public float y(){
            return source[indexSelf * elementOffset + 1];
        }
        public void x(float value){
            source[indexSelf * elementOffset] = value;
        }
        public void y(float value){
            source[indexSelf * elementOffset + 1] = value;
        }
    }

    public record Vec3(float[] source, int indexSelf, int elementOffset){
        public float x(){
            return source[indexSelf * elementOffset];
        }
        public float y(){
            return source[indexSelf * elementOffset + 1];
        }
        public float z(){
            return source[indexSelf * elementOffset + 2];
        }
        public void x(float value){
            source[indexSelf * elementOffset] = value;
        }
        public void y(float value){
            source[indexSelf * elementOffset + 1] = value;
        }
        public void z(float value){
            source[indexSelf * elementOffset + 2] = value;
        }
    }

    public record Vec4(float[] source, int indexSelf, int elementOffset){
        public float x(){
            return source[indexSelf * elementOffset];
        }
        public float y(){
            return source[indexSelf * elementOffset + 1];
        }
        public float z(){
            return source[indexSelf * elementOffset + 2];
        }
        public float w(){
            return source[indexSelf * elementOffset + 3];
        }
        public void x(float value){
            source[indexSelf * elementOffset] = value;
        }
        public void y(float value){
            source[indexSelf * elementOffset + 1] = value;
        }
        public void z(float value){
            source[indexSelf * elementOffset + 2] = value;
        }
        public void w(float value){
            source[indexSelf * elementOffset + 3] = value;
        }
    }
}
