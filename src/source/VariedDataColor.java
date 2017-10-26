package source;


public class VariedDataColor {

    private double volume = 0; // 同一类型液体的体积。
    private int kind = 0;

    public VariedDataColor() {
    }

    public VariedDataColor(double volume, int kind) {
        this.volume = volume;
        this.kind = kind;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

}
