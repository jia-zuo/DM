import java.applet.*;
import java.awt.*;
import java.awt.event.*;   
import java.awt.Graphics;

public class DM extends Applet implements ActionListener,Runnable   
//���ݸò��Գ��򣬿��Է����������Լ��ı����㷨
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

    dt=30;     //ÿ��ԭsample��30��ʱ�䵥λ 30/1020*getSize().width
    delta=18; //������  18/680*getSize().height
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
    if(checkMethod==0)    //���ð�ť�ļ��������¼����͡��̵߳����С����ⷢ��
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
      delta=18;//deltaΪȫ�ֱ�������ֹrepaintʱ��ĳ���㷨�Ѿ���deltaֵ����
      O_sp=new Thread(this);
      DM_sp=new Thread(this);
      O_sp.start();
      DM_sp.start();
  }
  public void paint(Graphics g)
  {     
    delta=18;//deltaΪȫ�ֱ�������ֹrepaintʱ��ĳ���㷨�Ѿ���deltaֵ����
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
    while(checkMethod!=0)     //�����̵߳����С��͡���ť�ļ��������¼������ⷢ��
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
                 //delta�������̫С,���Ƕ�����ȷ�������ˣ�          
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
                 else   DM_sp=null;  checkMethod=0;//Ϊ���ð�ť�ļ���������Ч�����ٵȴ���
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
//����Ϊ�㷨����
/*
  int getOa(int t)//�ڸ�ʱ�̵��������ȡ��ǰ��ʱ�䣬����һ������ֵt  value  max is  ?
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
  }//ԭʼ���������ֵ,���Խ�������������߶ηָ���Ҷ�����������߶ε�б�ʣ�����Ȼ����Ч�Ĳ��Է�ʽ
*/  
//
  int getOa(int t)
  {
       if(t<100)
             return t*t/500+t/2+100;
       else if(t<250)
             return (-2)*t*t/500+2*t+10;//t=200ʱ�����ͬ�Ϳ���
       else if(t<300)
             return (-2)*t+760;//��������,����Ҫsin ��cos
       else if(t<400) 
              return t*t/500-t+280;
       else if(t<480)
              return -t+600;
       else if(t<600)
              return t*t/600-2*t+696;
       else return 96;
  }//��ȻҲ���Զ���������ɢ�ĵ㣬�øú�������������ÿһ��ֵ
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
        a[t/dt+1]=a[t/dt]+delta;//ֻ������������ж�ֱ�Ӿ����´ε�Ԥȡֵ
                                            //��Ϊ�´ε��ж��ṩ����
      }
      else 
      {
         code[t/dt]=0;
         a[t/dt+1]=a[t/dt]-delta;
      }
      return a[t/dt];//���ص�ʵ��������һ��ʱ�������Ĺ���ֵ
   }//�����������㷨Ԥ�Ƴ���һ��ʱ���ķ���ֵ 
   

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
           a[t/dt+1]=a[t/dt]+delta;//ֻ������������ж�ֱ�Ӿ����´ε�Ԥȡֵ
                                               //��Ϊ�´ε��ж��ṩ����
       else
            a[t/dt+1]=a[t/dt]-delta;
          
       return a[t/dt];
   }//��Song���������㷨Ԥ�Ƴ���һ��ʱ���ķ���ֵ
      
    
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
           a[t/dt+1]=a[t/dt]+delta;//ֻ������������ж�ֱ�Ӿ����´ε�Ԥȡֵ
                                               //��Ϊ�´ε��ж��ṩ����
       else
            a[t/dt+1]=a[t/dt]-delta;
       
       return a[t/dt];
   }//��Greefkes���������㷨Ԥ�Ƴ���һ��ʱ���ķ���ֵ

}











