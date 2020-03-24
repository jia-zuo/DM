import java.applet.*;
import java.awt.*;
import java.awt.event.*;   
import java.awt.Graphics;

public class DM extends Applet implements ActionListener,Runnable   
//根据该测试程序，可以发明并测试自己的编码算法
{
  Button DM_button,SDM_button,GDM_button,button;
  Thread O_sp,DM_sp;
  Graphics Oshow,DMshow;
  int checkMethod;
  
  int dt,Maxtime;                      
  int delta,Maxdelta,Mindelta;
  byte[] code;
  int[] a;
  int wait;
  Font f1;
  
  public void init()
  {
    DM_button=new Button("Delta Modulation"); add(DM_button);
    SDM_button=new Button("Song adaptiveDM"); add(SDM_button);
    GDM_button=new Button("Greefkes adaptiveDM"); add(GDM_button);
    button=new Button("Refresh!!!!!!!!!"); add(button);
    DM_button.addActionListener(this);
    SDM_button.addActionListener(this);
    GDM_button.addActionListener(this);
    button.addActionListener(this);
    Oshow=getGraphics();
    DMshow=getGraphics();
    checkMethod=0;                                                                  

    dt=30;     //每隔原sample的30个时间单位 30/1020*getSize().width
    delta=18; //量化阶  18/680*getSize().height
    Maxtime=732;
    Maxdelta=88;
    Mindelta=12;
    code=new byte[Maxtime/dt+5]; 
    a=new int[Maxtime/dt+5];     
    a[0]=0;
    wait=5;
    f1=new Font("Helvetica",Font.BOLD,21);
    setBackground(Color.gray);
  }
  public void actionPerformed(ActionEvent e)
  {
    if(checkMethod==0)    //“该按钮的监听触发事件”和“线程的运行”互斥发生
    {
       if(e.getSource()==DM_button)           {start();checkMethod=1;}
       else if(e.getSource()==SDM_button)     {start();checkMethod=2;}
       else if(e.getSource()==GDM_button)     {start();checkMethod=3;}
       else {checkMethod=0;repaint();}
    }  
  }
  public void start()
  {
    
      checkMethod=0;
      delta=18;//delta为全局变量，防止repaint时，某个算法已经将delta值更改
      O_sp=new Thread(this);
      DM_sp=new Thread(this);
      O_sp.start();
      DM_sp.start();
  }
  public void paint(Graphics g)
  {     
    delta=18;//delta为全局变量，防止repaint时，某个算法已经将delta值更改
    g.setColor(Color.gray);
    g.fillRect(100,50,820,550);
    g.setColor(Color.blue);
    for(int i=0;i<28;i++)
    {
           g.drawString(String.valueOf(i),95+i*dt,620);
           g.fillOval(100+i*dt,600,2,8);
           g.drawString(String.valueOf(i),80,605-i*delta);
           g.fillOval(92,600-i*delta,8,2);              
    }
    g.setColor(Color.black);
    g.drawLine(95,605,95,30);
    g.drawLine(95,605,950,605);
    int px1[]={95,98,92,95};
    int py1[]={18,30,30,18};
    g.fillPolygon(px1,py1,4);
    int px2[]={962,950,950,962};
    int py2[]={605,608,602,605};
    g.fillPolygon(px2,py2,4);
  }
  public void run()
  {
    while(checkMethod!=0)     //“该线程的运行”和“按钮的监听触发事件”互斥发生
    {
          if(Thread.currentThread()==O_sp) 
          {
               Oshow.setColor(Color.gray);
               Oshow.fillRect(100,50,820,550);
               int tempa;
               for(int t=0;t<732&&checkMethod!=0;t++)
               {
                   tempa=-getOa(t)+599;   //y2=-(y1-?)+2
                   Oshow.setColor(Color.blue); 
                   Oshow.fillOval(t+100,tempa,3,3);
                    try{    O_sp.sleep(wait);   }
                        catch(InterruptedException e){ }  
               } 
               O_sp=null;         checkMethod=0;
           }
           else if(Thread.currentThread()==DM_sp)
           {
                 if(checkMethod==1)                     
                 {
                         int tempa1,tempa2=599;                          //add
                         for(int t=0;t<732&&checkMethod!=0;t+=dt)
                         {
                              tempa1=tempa2; 
                              tempa2=-getDMa(getOa(t),t)+599;   //y2=-(y1-?)+2
                              DMshow.setColor(Color.red);
                              DMshow.drawLine(t+100,tempa1,t+100,tempa2);
                              DMshow.fillOval(t+100,tempa2,3,3);  DMshow.setFont(f1);
                              DMshow.drawString(String.valueOf(code[t/dt]),t+98,65);
                              DMshow.drawLine(t+100,tempa2,t+100+dt,tempa2);
                              try{    O_sp.sleep(dt*wait);   }
                                  catch(InterruptedException e){ }  
                         }
                         DM_sp=null;    checkMethod=0;                     
                 } 
                 
                 else if(checkMethod==2)        
                 //delta后来变得太小,但是抖动的确被消除了！          
                 {
                         int tempa1,tempa2=599;
                         for(int t=0;t<732&&checkMethod!=0;t+=dt)
                         {
                              tempa1=tempa2;
                              tempa2=-getSDMa(getOa(t),t)+599;   //y2=-(y1-?)+2
                              DMshow.setColor(Color.white);
                              DMshow.drawLine(t+100,tempa1,t+100,tempa2);
                              DMshow.fillOval(t+100,tempa2,3,3);
                              DMshow.drawString(String.valueOf(code[t/dt]),t+98,65);
                              DMshow.drawLine(t+100,tempa2,t+100+dt,tempa2);
                              try{    O_sp.sleep(dt*wait);   }
                                  catch(InterruptedException e){ }  
                         }
                         DM_sp=null;      checkMethod=0;                 
                 }
                 
                 else if(checkMethod==3)                     
                 {
                         int tempa1,tempa2=599;
                         for(int t=0;t<732&&checkMethod!=0;t+=dt)
                         {
                              tempa1=tempa2;
                              tempa2=-getGDMa(getOa(t),t)+599;   //y2=-(y1-?)+2
                              DMshow.setColor(Color.black);
                              DMshow.drawLine(t+100,tempa1,t+100,tempa2);
                              DMshow.fillOval(t+100,tempa2,3,3);
                              DMshow.drawString(String.valueOf(code[t/dt]),t+98,65);
                              DMshow.drawLine(t+100,tempa2,t+100+dt,tempa2);
                              try{    O_sp.sleep(dt*wait);   }
                                  catch(InterruptedException e){ }  
                         }
                         DM_sp=null;    checkMethod=0;                   
                 }
                 else   DM_sp=null;  checkMethod=0;//为了让按钮的监听重新有效（不再等待）
           }
    }
  }
  
  public void stop()
  {
     O_sp=null;
     DM_sp=null;
     checkMethod=0;
     Oshow.clearRect(0,0,910,650);
     DMshow.clearRect(0,0,910,650);
  }
  
/***************************************************************************/
//以下为算法描述
/*
  int getOa(int t)//在该时刻的振幅，获取当前的时间，返回一个幅度值t  value  max is  ?
  {
     if(t<200)
     {
        return   2*t;	         
     }
     else if(t<300)
     {
        return  (-2)*t+800;	              
     }
     else if(t<600)
     {
        return 200;	
     }
     else
     {
        return (int)(2.5*t)-1300;	
     }
  }//原始的输入幅度值,可以进行任意区间的线段分割，并且定义该区间上线段的斜率，最自然和有效的测试方式
*/  
//
  int getOa(int t)
  {
       if(t<100)
             return t*t/500+t/2+100;
       else if(t<250)
             return (-2)*t*t/500+2*t+10;//t=200时造成相同就可以
       else if(t<300)
             return (-2)*t+760;//再制造锯齿,不需要sin 和cos
       else if(t<400) 
              return t*t/500-t+280;
       else if(t<480)
              return -t+600;
       else if(t<600)
              return t*t/600-2*t+696;
       else return 96;
  }//当然也可以定义任意离散的点，用该函数返回数组中每一个值
//
/*
  int getOa(int t)
  {
       return (int)((Math.cos(t/5) + Math.sin(t/7) + 2) * getSize().height / 4);
  }
*/
/*
  int getOa(int t)
  {
       return (int)(Math.sin((double)t/60)*200+200 );
  }
*/
  int getDMa(int Oa,int t)
  {
     if(Oa>=a[t/dt])
     {
        code[t/dt]=1;
        a[t/dt+1]=a[t/dt]+delta;//只能这样，这次判断直接决定下次的预取值
                                            //且为下次的判断提供可能
      }
      else 
      {
         code[t/dt]=0;
         a[t/dt+1]=a[t/dt]-delta;
      }
      return a[t/dt];//返回的实际上是上一个时间点算出的公共值
   }//用增量调制算法预计出下一个时间点的幅度值 
   

   int getSDMa(int Oa,int t)
   {
      if(Oa>=a[t/dt])
         code[t/dt]=1;
      else 
          code[t/dt]=0;    
      
      if(t/dt>=1&&code[t/dt]==code[t/dt-1])
          delta+=(int)(0.5*delta);  
       else if(t/dt>=1)
          delta-=(int)(0.5*delta);
       else 
          delta=delta;
  
       if(code[t/dt]==1)        
           a[t/dt+1]=a[t/dt]+delta;//只能这样，这次判断直接决定下次的预取值
                                               //且为下次的判断提供可能
       else
            a[t/dt+1]=a[t/dt]-delta;
          
       return a[t/dt];
   }//用Song增量调制算法预计出下一个时间点的幅度值
      
    
   int getGDMa(int Oa,int t)
   {
       if(Oa>=a[t/dt])
          code[t/dt]=1;
       else 
          code[t/dt]=0;
    
       if(t/dt>=2&&code[t/dt]==code[t/dt-1]&&code[t/dt-1]==code[t/dt-2])
          delta=Maxdelta; 
       else if(t/dt>=2)
          delta=Mindelta;
       else 
          delta=delta;
    
        if(code[t/dt]==1)        
           a[t/dt+1]=a[t/dt]+delta;//只能这样，这次判断直接决定下次的预取值
                                               //且为下次的判断提供可能
       else
            a[t/dt+1]=a[t/dt]-delta;
       
       return a[t/dt];
   }//用Greefkes增量调制算法预计出下一个时间点的幅度值

}











