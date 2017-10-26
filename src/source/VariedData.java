package source;

/*
 * 这个类用来存储Excel中的可变信息。
 */
public class VariedData {

    private double q = 0; // 排出排量。先简单化为单位为1的体积。
    private int kind = 0; // 当前加液类型。

    public VariedData() {

    }

    public VariedData(double q, int kind) {
        this.q = q;
        this.kind = kind;
    }

    public VariedData(double q, int kind, int pre_kind) {
        this.q = q;
        this.kind = kind;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

}
