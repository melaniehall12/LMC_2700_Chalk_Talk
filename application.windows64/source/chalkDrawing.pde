class chalkDrawing {
  private int x;
  private int y;
  private int px;
  private int py;
  private color col;
  
  chalkDrawing(int x, int y, int px, int py, color col) {
    this.x = x;
    this.y = y;
    this.col = col;
    this.px = px;
    this.py = py;
  }
  
  void drawCD() {
    stroke(col);
    line(x,y, px, py);
  }
  
  color getColor(){
    return this.col;
  }
  
}