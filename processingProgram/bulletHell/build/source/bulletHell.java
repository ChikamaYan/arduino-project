import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bulletHell extends PApplet {

//Bullet hell game for arduino coursework
//Created by Walter Wu on 28/10/17

ArrayList bullets = new ArrayList();
Player player;
Enemy enemy;

public void setup(){
  fill(255);
  
  background(0);
  noStroke();
  player = new Player();
  enemy = new Enemy();
}

public void draw(){
  background(0);
  player.draw();
  enemy.draw();

  //remove the bullets that are out of screen
  for(int i =0; i < bullets.size();i++){
    Bullet bInstance = (Bullet) bullets.get(i);
    if (! bInstance.checkValidity()){
      bullets.remove(i);
    }
  }

  for(int i =0; i < bullets.size();i++){
    Bullet bInstance = (Bullet) bullets.get(i);
    bInstance.draw();
  }
}


class SpaceShip{
  int x,y;

  public void draw(){
    update();
    drawShip();
  }

  public void update(){}
  public void drawShip(){}

}


class Player extends SpaceShip{
  //for player: x,y are centre of circle
  Player(){
    x = width/2;
    y = height - 50;
  }

  public void update(){
    //update position of player
    if (keyPressed && keyCode == LEFT && x > 80) {
      x-=15;
    }
    if (keyPressed && keyCode == RIGHT && x < width - 80) {
      x+=15;
    }
  }

  public void drawShip(){
    ellipse(x-20,y-20,40,40);
  }

  public void ifHit(){

  }
}


class Enemy extends SpaceShip{
  //for enemy, x,y will be bottom point of triangle
  boolean hasTarget = false;
  boolean canMove = false;
  int targetx = 0, moveScale = 2, moveDelay = 0;

  Enemy(){
    x = width/2;
    y = 100;
  }

  public void update(){
    //if can move, move enemy towards chosen target, or select new target
    if (canMove){
      if (abs(targetx-x) < moveScale){
        hasTarget = false;
      }
      if (hasTarget){
        if (targetx > x){
          x += moveScale;
          }else if (targetx < x){
            x -= moveScale;
          }
          }else{
            //ensure next target point is far away from last one
            if (targetx < 800){
              targetx = PApplet.parseInt(random(1100,1300));
              }else{
                targetx = PApplet.parseInt(random(300,500));
              }
              hasTarget = true;
              canMove = false;
            }
          }else{
            moveDelay += 1;
            if (moveDelay >= 80){
              moveDelay = 0;
              canMove = true;
            }
          }
          if (frameCount%30 == 0){
            shoot();
          }

        }

        public void drawShip(){
          triangle(x,y,x-20,y-30,x+20,y-30);
        }

        public void shoot(){
          for (int i =0;i<20;i++){
          bullets.add(new Bullet(PApplet.parseFloat(x),PApplet.parseFloat(y),random(-2,3),random(1,3)));
          }
        }

      }

class Bullet{
  //for bullets, x,y will be centre of circle
  float x,y,xSpeed, ySpeed;

  Bullet(float startx,float starty,float xspeed,float yspeed){
    x = startx;
    y = starty;
    xSpeed = xspeed;
    ySpeed = yspeed;
  }

  public void draw(){
    update();
    checkValidity();
    drawBullet();
  }

  public void update(){
    x += xSpeed;
    y += ySpeed;
  }

  public void drawBullet(){
    ellipse(PApplet.parseInt(x-4),PApplet.parseInt(y-4),PApplet.parseInt(8),PApplet.parseInt(8));
  }

  public boolean checkValidity(){
    if (x < -4 || x > width+4 || y < -4 || y > height+4){
      //bullet has left screen
      return false;
    }
    return true;
  }

}


















      //
  public void settings() {  size(1600, 1200); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bulletHell" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
