import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class panel extends JPanel implements ActionListener {
    static int width=1200;
    static int height=600;
    static int unit=50;

    int totalunit=(width*height)/unit;
    int xsnake[]=new int[totalunit];
    int ysnake[]=new int[totalunit];



    int fx,fy,score,length=3;
    char dir='R';
    boolean flag=false;

    Random random;

    Timer timer;

    static int delay=160;
    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new MyKey());
        this.setFocusable(true);
        random=new Random();

        gameStart();
    }


    public void gameStart(){
        flag=true;
        spawnFood();
        timer =new Timer(delay,this);
    timer.start();
    }

    public void spawnFood(){
    fx=random.nextInt((int)width/unit)*unit;
    fy=random.nextInt((int)height/unit)*unit;


    }

     public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);

    }

     public void draw(Graphics graphic){
        if(flag){
            graphic.setColor(Color.orange);
            graphic.fillOval(fx,fy,unit,unit);


            for(int i=0;i<length;i++){
                if(i==0){
                    graphic.setColor(Color.RED);
               graphic.fillRect(xsnake[0],ysnake[0],unit,unit);
                }
                else{
                    graphic.setColor(Color.green);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
            }
            //For Score Display
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics fmc=getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+score,(width-fmc.stringWidth("Score:"+score) )/2,graphic.getFont().getSize());
        }else{
            gameover(graphic);
        }
     }

    public   void gameover(Graphics graphic){
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics fmc=getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+score,(width-fmc.stringWidth("Score:"+score) )/2,graphic.getFont().getSize());

        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics fmc1=getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over:",(width-fmc1.stringWidth("Game Over:") )/2,height/2);

        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics fmc2=getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Replay",(width-fmc2.stringWidth("Press R to Replay") )/2,height/2-150);
    }

    public void move(){
    // for all other body parts
        for(int i=length;i>0;i--) {
            xsnake[i] = xsnake[i - 1];
            ysnake[i]=ysnake[i-1];
        }

        switch (dir){
            case 'U':
                ysnake[0]= ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]= ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;


        }

    }
    void check(){
        //Checking if head has Hit the body
        for(int i=length;i>0;i--){
            if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i])){
                flag=false;
            }
        }
        //Checking Hit with Walls

        if(xsnake[0]<0)
            flag=false;
        else if(xsnake[0]>width)
            flag=false;
        else if (ysnake[0]<0)
            flag=false;
        else if(ysnake[0]>height)
            flag=false;

        if(flag==false)
            timer.stop();
    }
    public  void foodEaten(){
        if(xsnake[0]==fx && ysnake[0]==fy){
            length++;
            score++;
            spawnFood();
        }
    }
    public class MyKey extends KeyAdapter{

        public void  keyPressed(KeyEvent k){
            switch (k.getKeyCode()){
                case VK_UP:
                    if(dir!='D')
                        dir='U';
                    break;

                case VK_DOWN:
                    if(dir!='U')
                        dir='D';
                    break;
                case VK_RIGHT:
                    if(dir!='L')
                        dir='R';
                    break;
                case VK_LEFT:
                    if(dir!='R')
                        dir='L';
                    break;
                case VK_R:
                    if(!flag) {
                        score = 0;
                        length = 3;
                        dir = 'R';
                        Arrays.fill(xsnake,0 );
                        Arrays.fill(ysnake, 0);
                        gameStart();
                    }
                    break;

            }
        }
    }




    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            foodEaten();
            check();
         }
        repaint();
        }
}
