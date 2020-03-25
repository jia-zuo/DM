import java.awt.Frame;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Created by jzuo on 2020/03/25
 */
public class DMain{
  public static void main(String[] args){
    new DMFrame();
  }
}

/**
 * Created by jzuo on 2020/03/25
 */
class DMFrame extends Frame implements ActionListener{
  private static final long serialVersionUID = 1L;
  int checkMethod;
  Panel p1;
  TextField dtText;
  TextField deltaText;
  Label l1;
  Label l2;
  Button DM_button;
  Button SDM_button;
  Button GDM_button;
  Button ZDM_button;
  Font f1;

  int dt;
  int delta;
  int Maxdelta;
  int Mindelta;
  int Maxtime;
  byte[] code;
  int[] a;

  public DMFrame(){
    p1 = new Panel();
    p1.setLayout(new GridLayout(1,8));

    l1 = new Label("dt:", 2);
    l1.setForeground(Color.black);
    p1.add(l1);
    dtText = new TextField("6", 5);
    p1.add(dtText);
    l2 = new Label("delta:", 2);
    l2.setForeground(Color.black);
    p1.add(l2);
    deltaText = new TextField("18", 5);
    p1.add(deltaText);

    DM_button = new Button("DM");
    DM_button.setForeground(Color.black);
    p1.add(DM_button);
    SDM_button = new Button("Song");
    SDM_button.setForeground(Color.black);
    p1.add(SDM_button);
    GDM_button = new Button("Green");
    GDM_button.setForeground(Color.black);
    p1.add(GDM_button);
    ZDM_button = new Button("Geo");
    ZDM_button.setForeground(Color.red);
    p1.add(ZDM_button);
    DM_button.addActionListener(this);
    SDM_button.addActionListener(this);
    GDM_button.addActionListener(this);
    ZDM_button.addActionListener(this);
  
    add(p1,BorderLayout.SOUTH);

    //p2 = new Panel();
    //add(p2,BorderLayout.CENTER);

    pack();
    setSize(1024,768);
    setVisible(true);

    checkMethod = 0;
    dt = Integer.parseInt(dtText.getText());
    delta = Integer.parseInt(deltaText.getText());
    Maxtime = 830;
    code = new byte[Maxtime / dt + 5];
    a = new int[Maxtime / dt + 5];
    Maxdelta = 2 * dt + delta;
    Mindelta = delta / 2;
    a[0] = 0;
    
    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        setVisible(false);
        System.exit(0);
      }
    });
  }

  @Override
  public void paint(Graphics g) {
    delta = Integer.parseInt(deltaText.getText());
    dt = Integer.parseInt(dtText.getText());
    f1 = new Font("Helvetica", 1, 10 + dt / 3);
    code = new byte[Maxtime + 5];
    a = new int[Maxtime + 5];
    Maxdelta = 2 * dt + delta;
    Mindelta = delta / 2;
    g.setColor(Color.white);
    g.fillRect(0, 0, 1017, 650);
    g.setColor(Color.black);
    for (byte b = 0; b < 28; b++) {
      g.drawString(String.valueOf(b * 30), 95 + b * 30, 620);
      g.fillOval(100 + b * 30, 600, 2, 8);
      g.drawString(String.valueOf(b * 18), 70, 605 - b * 18);
      g.fillOval(92, 600 - b * 18, 8, 2);
    }
    g.setColor(Color.black);
    g.drawLine(95, 605, 95, 30);
    g.drawLine(95, 605, 950, 605);
    int[] arrayOfInt1 = { 95, 98, 92, 95 };
    int[] arrayOfInt2 = { 18, 30, 30, 18 };
    g.fillPolygon(arrayOfInt1, arrayOfInt2, 4);
    int[] arrayOfInt3 = { 962, 950, 950, 962 };
    int[] arrayOfInt4 = { 605, 608, 602, 605 };
    g.fillPolygon(arrayOfInt3, arrayOfInt4, 4);
  }

  public void actionPerformed(ActionEvent e) {
    if (checkMethod == 0) {
      // repaint();
      if (e.getSource() == this.DM_button) {
        //start();
        //this.checkMethod = 1;
        System.out.println("DM");
      } else if (e.getSource() == this.SDM_button) {
        //start();
        //this.checkMethod = 2;
        System.out.println("SDM");
      } else if (e.getSource() == this.GDM_button) {
        //start();
        //this.checkMethod = 3;
        System.out.println("GDM");
      } else if (e.getSource() == this.ZDM_button) {
        //start();
        //this.checkMethod = 5;
        System.out.println("ZDM");
      } else {
        checkMethod = 0;
      }
    }
  }
}