package gbw.melange.common.elementary;

public record Anchor(double x, double y) {

    public Anchor {
        //Constructor
        assert x > 0 && x <= 1 && y > 0 && y <= 1;
    }
    public Anchor(Anchor ref) {
        //Constructor
        this(ref.x, ref.y);
    }
}
