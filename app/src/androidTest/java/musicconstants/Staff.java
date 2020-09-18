package musicconstants;

public class Staff {
    private static final String TAG = "Staff";
    private int beatsPerMinute;
    private int beatsPerMeasure;
    private int beatUnit;


    public Staff(int beatsPerMinute, int beatsPerMeasure, int beatUnit) {
        this.beatsPerMinute = beatsPerMinute;
        this.beatsPerMeasure = beatsPerMeasure;
        this.beatUnit = beatUnit;

    }


    private float findBeatsPerSecond(int beatsPerMinute) {
        return (float) beatsPerMinute / 60;
    }


}
