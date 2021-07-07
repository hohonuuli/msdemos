package msdemos.shared.jdk;

public class RequestCounts {

  private Integer i;
  private Integer j;
  private Integer k;
  private Long delayMillis = 0L;

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

  
  public Long getDelayMillis() {
    return delayMillis;
  }

  public void setDelayMillis(Long delayMillis) {
    this.delayMillis = delayMillis;
  }

  public msdemos.shared.RequestCounts asScala() {
    return msdemos.shared.RequestCounts.apply(getI(), getJ(), getK(), getDelayMillis());
  }

  
}
