package msdemos.shared.jdk;



public class RequestCounts {

  private Integer i;
  private Integer j;
  private Integer k;
  private int readCount = 0;

  public RequestCounts(int i, int j, int k) {
    this.i = i;
    this.j = j;
    this.k = k;
  }

  public RequestCounts() {}

  public Integer getI() {
    return i;
  }

  public void setI(Integer i) {
    this.i = i;
  }

  public Integer getJ() {
    return j;
  }

  public void setJ(Integer j) {
    this.j = j;
  }

  public Integer getK() {
    return k;
  }

  public void setK(Integer k) {
    this.k = k;
  }

  
  public int getReadCount() {
    return readCount;
  }

  public void setReadCount(int readCount) {
    this.readCount = readCount;
  }

  public msdemos.shared.RequestCounts asScala() {
    return msdemos.shared.RequestCounts.apply(i, j, k, scala.Option.apply(readCount));
  }

  
}
