package gbw.melange.common.elementary;

/**
 * Used to represents either an elements: <br/>
 * extends -> scale in all direction in abstract screenspace units <br/>
 * position -> x,y,z offset into the current space from the current anchor point. This assumes that 0,0 is the top right corner of the screen <br/>
 * theres a third one here... but I've forgotten it.
 */
public interface IElementTransform {

    double getX();
    double getY();
    double getZ();

}
