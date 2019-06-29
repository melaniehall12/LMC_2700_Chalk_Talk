import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import processing.core.PApplet; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Chalky_Chalk extends PApplet {

//Team 12

//0: Start Screen
//1: Instructions Screen
//2: P1 Screen
//3: P2 Screen
//4: P1/P2 Screen - Not Used;
//5: Restart Screen



ArrayList drawing = new ArrayList();
PImage startScreen;
PImage insScreen;
PImage resScreen;
PImage p1Screen;
PImage p2Screen;
PFont font;
SoundFile file;
int offset;
int startCX, startCY; //Position of 'Start' button.
int insCX, insCY; //Position of 'Instructions' button.
int backCX, backCY; //Position of 'Back' button.
int nextCX, nextCY; //Position of 'Next' button.
int noCX, noCY; //Position of the 'No' button.
int buttonSize = 45; //Diameter of the buttons.
int startColor, insColor, backColor, nextColor, noColor; //Colors of the buttons.
int startHighlight, insHighlight, backHighlight, nextHighlight, noHighlight; //Highlight when mouse is over button.
boolean startOver = false; //Whether mouse is over 'Start' button.
boolean insOver = false; //Whether mouse is over 'Instructions' button.
boolean backOver = false; //Whether mouse is over 'Back' button.
boolean nextOver = false; //Whether mouse is over 'Next' button.
boolean noOver = false; //Whether mouse is over 'No' button.
boolean end = false;

String[] themes = {"pets", "Miss America", "Trump", "music",
  "America", "fear", "duel", "ballerina", "Royalty", "Christmas",
  "unicorn", "pirate", "Thanksgiving", "Winter", "war", "barnyard",
  "school", "France", "peace", "Spring", "Summer", "Autumn", 
  "GA Tech", "crying", "Spongebob", "circus", "wizards", "mermaids",
  "Disney", "fire", "nature", "robots", "freestyle", "death", "zombies",
  "ghosts", "cooking", "art", "Abe Linc", "Harry Potter", "Justin Bieber",
  "Asia", "Valentine's Day", "romance", "sports", "space", "city", "Zelda"};
int pick1;
int pick2;
String p1Theme;
String p2Theme;

int currScreen = 0; //The current screen.
int turn;

//Color Choices
int chalkCol; //Color of the chalk.
int c1 = color(255, 204, 255);
int c2 = color(179, 217, 255);
int c3 = color(255, 255, 153);
int c4 = color(204, 255, 153);
int c5 = color(217, 179, 255);
int c6 = color(255);
int c7 = color(255, 102, 102);
int c8 = color(128);
int c9 = color(162, 92, 0);

//Timer
int time;
int wait = 1000;
int countdown = 20;
int countdown2 = 5;

public void setup() {
  
  time = millis();
  startScreen = loadImage("Start Screen.jpg");
  insScreen = loadImage("Instructions Screen.jpg");
  resScreen = loadImage("Restart Screen.jpg");
  p1Screen = loadImage("Sidewalk.jpg");
  p1Screen.resize(1000, 700);
  p2Screen = loadImage("Player 2 Screen.jpg");
  p2Screen.resize(1000, 700);
  font = createFont("SqueakyChalkSound.ttf", 45);
  file = new SoundFile(this, "music.mp3");
  file.loop();
  startColor = color(51, 204, 51);
  startHighlight = color(153, 230, 153);
  startCX = 645;
  startCY = 515;
  insColor = color(255, 0, 102);
  insHighlight = color(255, 153, 194);
  insCX = 340;
  insCY = 610;
  backCX = 795;
  backCY = 40;
  backColor = color(0, 102, 255);
  backHighlight = color(128, 179, 255);
  nextCX = 190;
  nextCY = 640;
  nextColor = color(255, 255, 0);
  nextHighlight = color(255, 255, 102);
  noCX = 780;
  noCY = 545;
  noColor = color(255, 153, 0);
  noHighlight = color(255, 204, 128);
  
  frameRate(150);
  chalkCol = c6;
  turn = 1;
  strokeWeight(10);
}

public void draw() {
  //Checks which screen is active updating currScreen variable.
  if(currScreen == 0) {
    startScreen();
  } else if(currScreen == 1) {
    insScreen();
  } else if(currScreen == 2) {
    p1Screen();
  } else if(currScreen == 3) {
    p2Screen();
  } else if(currScreen == 5) {
    restartScreen();
  }
}

//Draw method.

public void drawDrawing() {
  strokeWeight(10);
  
  for(int i = 0; i < drawing.size(); i++) {
    chalkDrawing cd = (chalkDrawing)drawing.get(i);
    cd.drawCD();
  }

}
public void drawChalk() {
  
  if(mousePressed == true) {
    drawing.add(new chalkDrawing(mouseX, mouseY, pmouseX, pmouseY, color(chalkCol)));
  }
  
  fill(chalkCol);
  noStroke();
  ellipse(mouseX,mouseY,13,13);
  stroke(6);
  quad(mouseX-5+offset,mouseY-5-offset,
      mouseX+40+offset,mouseY-120-offset,
      mouseX+77+offset,mouseY-92-offset,
      mouseX+5+offset,mouseY+5-offset);
  ellipse(mouseX+60+offset,mouseY-109-offset,53,34);
}

//Picks a theme.
public void pickTheme(String[] themes) {
  int pick1 = PApplet.parseInt(random(themes.length));
  int pick2 = PApplet.parseInt(random(themes.length));
  p1Theme = themes[pick1];
  p2Theme = themes[pick2];
}

//Gets P1's theme.
public void getP1Theme() {
  fill(0);
  textFont(font, 60);
  text(p1Theme, 30, 70);  
}

//Gets P2's theme.
public void getP2Theme() {
  fill(0);
  textFont(font, 60);
  text(p2Theme, 30, 135);
}

public void celebratory() {
  fill(0);
  textFont(font, 50);
  text("Next", 20, 660);
  end = true;
}

//Chalkbox/Chalk
public void drawChalkbox() {
  stroke(0);
  //Chalkbox
  fill(0, 64, 128);
  rect(830, 540, 150, 150, 15);
  //Pink Chalk
  fill(255, 204, 255);
  ellipse(860, 616, 40, 40);
  //Blue Chalk
  fill(179, 217, 255);
  ellipse(905, 616, 40, 40);
  //Yellow Chalk
  fill(255, 255, 153);
  ellipse(950, 616, 40, 40);
  //Green Chalk
  fill(204, 255, 153);
  ellipse(860, 661, 40, 40);
  //Purple Chalk
  fill(217, 179, 255);
  ellipse(905, 661, 40, 40);
  //White Chalk
  fill(255);
  ellipse(950, 661, 40, 40);
  //Red Chalk
  fill(255, 102, 102);
  ellipse(860, 571, 40, 40);
  //Gray Chalk
  fill(128);
  ellipse(905, 571, 40, 40);
  //Brown Chalk
  fill(162, 92, 0);
  ellipse(950, 571, 40, 40);
}

//Checks which button is pressed.
public void mouseClicked() {
  if(startOver) {
    currScreen = 2;
  }
  if(insOver) {
    currScreen = 1;
  }
  if(backOver) {
    currScreen = 0;
  }
  if(nextOver) {
    currScreen = 5;
  }
  if(noOver) {
    turn = 1;
    countdown = 20;
    drawing.clear();
    getP1Theme();
    currScreen = 0;
    end = false;
  }
}

//Shows the Start Screen.
public void startScreen() {
  image(startScreen, 0, 0); //Displays image at point (0,0).
  image(startScreen, 0, 0, 1000, 700); //Fits image to screen.
  
  pick1 = PApplet.parseInt(random(themes.length));
  pick2 = PApplet.parseInt(random(themes.length));
  
  p1Theme = themes[pick1]; //The theme chosen at random to draw.
  p2Theme = themes[pick2];
  
  update(mouseX, mouseY);
  
  if(startOver) {
    fill(startHighlight);
  } else {
    fill(startColor);
  }
  stroke(0);
  ellipse(startCX, startCY, buttonSize, buttonSize);
  
  if(insOver) {
    fill(insHighlight);
  } else {
    fill(insColor);
  }
  stroke(0);
  ellipse(insCX, insCY, buttonSize, buttonSize);
}

//Shows the Instructions Screen.
public void insScreen() {
  image(insScreen, 0, 0); //Displays image at point (0,0).
  image(insScreen, 0, 0, 1000, 700); //Fits image to screen.
  
  update(mouseX, mouseY);
  
  if(backOver) {
    fill(backHighlight);
  } else {
    fill(backColor);
  }
  stroke(0);
  ellipse(backCX, backCY, buttonSize, buttonSize);
}

//Shows P1's Screen.
public void p1Screen() {
  background(p1Screen);
  update(mouseX, mouseY);
  if(turn == 1) {
    getP1Theme();
  } else if (turn == 2) {
    getP2Theme();
  } else if (turn == 3) {
    getP1Theme();
  } else if (turn == 4) {
    getP2Theme();
  }
  
  //Pink
  if(mousePressed) {
    float disXP = 860 - mouseX;
    float disYP= 616 - mouseY;
    if(sqrt(sq(disXP) + sq(disYP)) < 20){
      chalkCol = c1;
    }
  }
  //Blue
  if(mousePressed) {
    float disX = 905 - mouseX;
    float disY = 616 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c2;
    }
  }
  //Yellow
  if(mousePressed) {
    float disX = 950 - mouseX;
    float disY = 616 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c3;
    }
  }
  //Green
  if(mousePressed) {
    float disX = 860 - mouseX;
    float disY = 661 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c4;
    }
  }
  //Purple
  if(mousePressed) {
    float disX = 905 - mouseX;
    float disY = 661 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c5;
    }
  }
  //White
  if(mousePressed) {
    float disX = 950 - mouseX;
    float disY = 661 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c6;
    }
  }
  //Red
  if(mousePressed) {
    float disX = 860 - mouseX;
    float disY = 571 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c7;
    }
  }
  //Gray
  if(mousePressed) {
    float disX = 905 - mouseX;
    float disY = 571 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c8;
    }
  }
  //Brown
  if(mousePressed) {
    float disX = 950 - mouseX;
    float disY = 571 - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < 20){
      chalkCol = c9;
    }
  }
  
  drawChalkbox();
  drawDrawing();
  if (end == false) {
    drawChalk();
  }
  
  String s1 = str(countdown);
  fill(0);
  textFont(font, 60);
  text(s1, 900, 80);
  if(millis() - time >= wait) {
    if(countdown > 0) {
      time = millis();
      countdown--;
    }
  }  
  
  if((countdown == 0) && (turn == 1)) {
    countdown2 = 5;
    currScreen = 3;
  }
  if((countdown == 0) && (turn == 2)) {
    countdown2 = 5;
    currScreen = 3;
  }
  if((countdown == 0) && (turn == 3)) {
    countdown2 = 5;
    currScreen = 3;
  }
  
  if((countdown == 0) && (turn == 4)) {
    celebratory();
    getP1Theme();

    if(nextOver) {
      fill(nextHighlight);
    } else {
      fill(nextColor);
    }
    stroke(0);
    ellipse(nextCX, nextCY, 45, 45);
  }
}

//Shows P2's Screen.
public void p2Screen() {
  background(p2Screen);
  
  String s1 = str(countdown2);
  textFont(font, 80);
  text(s1, 485, 450);
  if(millis() - time >= wait) {
    if(countdown2 > 0) {
      time = millis();
      countdown2--;
    }
  }
  
  if(countdown2 == 0 && turn == 1) {
    turn++;
    countdown = 20;
    currScreen = 2;  
  } else if(countdown2 == 0 && turn == 2) {
    turn++;
    countdown = 20;
    currScreen = 2;  
  } else if(countdown2 == 0 && turn == 3) {
    turn++;
    countdown = 20;
    currScreen = 2;  
  }
}


//Asks the player if they'd liked to play again or not.
public void restartScreen() {
  image(resScreen, 0, 0); //Displays image at point (0,0).
  image(resScreen, 0, 0, 1000, 700); //Fits image to screen.
  file.stop();
  
  update(mouseX, mouseY);
  
  if(noOver) {
    fill(noHighlight);
  } else {
    fill(noColor);
  }
  stroke(0);
  ellipse(noCX, noCY, 80, 80);
}

//Finds position of mouse and checks if it's over a button.
public void update(int x, int y) {
  if(startOver(startCX, startCY, buttonSize)) {
    startOver = true;
    insOver = false;
    backOver = false;
    noOver = false;
    nextOver = false;
  } else if(insOver(insCX, insCY, buttonSize)) {
    insOver = true;
    startOver = false;
    backOver = false;
    noOver = false;
    nextOver = false;
  } else if(backOver(backCX, backCY, buttonSize)) {
    backOver = true;
    startOver = false;
    insOver = false;
    noOver = false;
    nextOver = false;
  } else if(noOver(noCX, noCY, buttonSize)) {
    noOver = true;
    startOver = false;
    insOver = false;
    backOver = false;
    nextOver = false;
  } else if(nextOver(nextCX, nextCY, buttonSize)) {
    nextOver = true;
    startOver = false;
    insOver = false;
    backOver = false;
    noOver = false;
  } else {
    startOver = insOver = backOver = noOver = nextOver = false;
  }
}

//Checks if over the 'Start' button.
public boolean startOver(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if(sqrt(sq(disX) + sq(disY)) < diameter/2) {
    return true;
  } else {
    return false;
  }
}

//Checks if over the 'Instructions' button.
public boolean insOver(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if(sqrt(sq(disX) + sq(disY)) < diameter/2) {
    return true;
  } else {
    return false;
  }
}

//Checks if over the 'Back' button.
public boolean backOver(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if(sqrt(sq(disX) + sq(disY)) < diameter/2) {
    return true;
  } else {
    return false;
  }
}

//Checks if over the 'No' button.
public boolean noOver(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if(sqrt(sq(disX) + sq(disY)) < diameter/2) {
    return true;
  } else {
    return false;
  }
}
  
//Checks if over the 'Next' button.
public boolean nextOver(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if(sqrt(sq(disX) + sq(disY)) < diameter/2) {
    return true;
  } else {
    return false;
  }
}
class chalkDrawing {
  private int x;
  private int y;
  private int px;
  private int py;
  private int col;
  
  chalkDrawing(int x, int y, int px, int py, int col) {
    this.x = x;
    this.y = y;
    this.col = col;
    this.px = px;
    this.py = py;
  }
  
  public void drawCD() {
    stroke(col);
    line(x,y, px, py);
  }
  
  public int getColor(){
    return this.col;
  }
  
}
  public void settings() {  size(1000,700);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Chalky_Chalk" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
