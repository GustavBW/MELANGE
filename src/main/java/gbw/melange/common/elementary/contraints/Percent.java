package gbw.melange.common.elementary.contraints;

/**
 * Abstract percentile representation assuring consistent sizing based on viewport width and height, whichever is smaller.
 */
public record Percent(double abstractPercent) {

    public static Percent of(double percent){
        return new Percent(percent);
    }

    public double fromAbsolute(int width, int height){
        if(width < height){
            return this.abstractPercent * width;
        }
        return this.abstractPercent * height;
    }

}
