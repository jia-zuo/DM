import java.awt.Frame;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
class DMFrame extends Frame implements ActionListener, Runnable, ItemListener {
  private static final long serialVersionUID = 1L;
  int checkMethod;
  Panel p1;
  TextField dtText;
  TextField deltaText;
  Label l1;
  Label l2;
  Label l3;
  Label zl;
  Button DM_button;
  Button SDM_button;
  Button GDM_button;
  Button ZDM_button;
  Font f1;
  Choice choice1;

  Thread O_sp;
  Thread DM_sp;
  Graphics Oshow;
  Graphics DMshow;

  int choice;
  int dt;
  int delta;
  int Maxdelta;
  int Mindelta;
  int Maxtime;
  byte[] code;
  int[] a;
  int wait;
  int index;
  double s;
  double p;
  double z;

  public DMFrame(){
    p1 = new Panel();
    p1.setLayout(new GridLayout(1,11));

    l3 = new Label("Z score+:", 2);
    l3.setForeground(Color.red);
    p1.add(l3);
    zl = new Label("                  ", 0);
    zl.setForeground(Color.black);
    p1.add(zl);
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

    choice1 = new Choice();
    choice1.add("change");
    choice1.add("peace");
    choice1.add("rapid");
    choice1.add("sin");
    choice1.add("quiet");
    p1.add(choice1);
    choice1.addItemListener(this);
  
    add(p1,BorderLayout.SOUTH);

    //p2 = new Panel();
    //add(p2,BorderLayout.CENTER);

    pack();
    setSize(1024,768);
    setVisible(true);

    checkMethod = 0;
    Oshow = getGraphics();
    DMshow = getGraphics();
    choice = 1;
    dt = Integer.parseInt(dtText.getText());
    delta = Integer.parseInt(deltaText.getText());
    Maxtime = 830;
    code = new byte[Maxtime / dt + 5];
    a = new int[Maxtime / dt + 5];
    Maxdelta = 2 * dt + delta;
    Mindelta = delta / 2;
    a[0] = 0;
    wait = 1;
    index = 0;
    s = p = z = 0.0D;

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
    // g.fillRect(0, 70, 1017, 650);
    g.fillRect(0, 0, 1017, 650+70);
    g.setColor(Color.black);
    for (byte b = 0; b < 28; b++) {
      g.drawString(String.valueOf(b * 30), 95 + b * 30, 605+70+15);
      g.fillOval(100 + b * 30, 600+70, 2, 8);
      g.drawString(String.valueOf(b * 18), 70, 605+70 - b * 18);
      g.fillOval(92, 600+70 - b * 18, 8, 2);
    }
    g.setColor(Color.black);
    g.drawLine(95, 605+70, 95, 30+70);
    g.drawLine(95, 605+70, 950, 605+70);
    int[] arrayOfInt1 = { 95, 98, 92, 95 };
    int[] arrayOfInt2 = { 18+70, 30+70, 30+70, 18+70 };
    g.fillPolygon(arrayOfInt1, arrayOfInt2, 4);
    int[] arrayOfInt3 = { 962, 950, 950, 962 };
    int[] arrayOfInt4 = { 605+70, 608+70, 602+70, 605+70 };
    g.fillPolygon(arrayOfInt3, arrayOfInt4, 4);
  }

  public void actionPerformed(ActionEvent e) {
    if (checkMethod == 0) {
      repaint();
      if (e.getSource() == DM_button) {
        start();
        checkMethod = 1;
      } else if (e.getSource() == SDM_button) {
        start();
        checkMethod = 2;
      } else if (e.getSource() == GDM_button) {
        start();
        checkMethod = 3;
      } else if (e.getSource() == ZDM_button) {
        start();
        checkMethod = 5;
      } else {
        checkMethod = 0;
      }
    }
  }

  public void itemStateChanged(ItemEvent paramItemEvent) {
    if (paramItemEvent.getItemSelectable() == choice1 && checkMethod == 0){
      if (choice1.getSelectedIndex() == 0) {
        choice = 1;
      } else if (choice1.getSelectedIndex() == 1) {
        choice = 2;
      } else if (choice1.getSelectedIndex() == 2) {
        choice = 3;
      } else if (choice1.getSelectedIndex() == 3) {
        choice = 4;
      } else {
        choice = 5;
      }
    }
  }

  public void start() {
    s = p = z = 0.0D;
    index = 0;
    checkMethod = 0;
    delta = Integer.parseInt(deltaText.getText());
    dt = Integer.parseInt(dtText.getText());
    O_sp = new Thread(this);
    DM_sp = new Thread(this);
    O_sp.start();
  }

  public void run() {
    while (checkMethod != 0) {
      if (Thread.currentThread() == O_sp) {
        DM_sp.start();
        Oshow.setColor(Color.white);
        // Oshow.fillRect(100, 50+70, 820+30, 550);
        Oshow.fillRect(100, 0, 820+30, 550+120);
        int i = -getOa(0, choice) + 599 + 70;
        for (int b = 0; b < Maxtime && checkMethod != 0; b++) {
          int j = -getOa(b, choice) + 599 + 70;
          Oshow.setColor(Color.black);
          Oshow.drawLine(b + 99, i, b + 100, j);
          Oshow.fillOval(b + 100, j, 2, 2);
          i = j;
          try {
              O_sp.sleep(wait);
              // Thread.sleep(wait);
          } catch (InterruptedException interruptedException) {
          }
        }
        O_sp = null;
        continue;
      }
      if (Thread.currentThread() == DM_sp) {
        int i = 599 + 70;
        int j;
        if (checkMethod == 1) {
          for (j = 0; j < Maxtime && checkMethod != 0; j += dt) {
            int m = getOa(j, choice);
            int n = getDMa(m, j);
            int k = i;
            i = -n + 599 + 70;
            DMshow.setColor(Color.blue);
            DMshow.drawLine(j + 100, k, j + 100, i);
            DMshow.fillOval(j + 100, i, 3, 3);
            DMshow.setFont(f1);
            DMshow.drawString(String.valueOf(code[j / dt]), j + 98, 65);
            s++;
            p += (m > n) ? (m - n) : (n - m);
            DMshow.drawLine(j + 100, i, j + 100 + dt, i);
            try {
              O_sp.sleep(dt * wait);
              // Thread.sleep((dt * wait));
            } catch (InterruptedException interruptedException) {
            }
          }
          p /= s;
          zl.setText(String.valueOf(s + p));
          DM_sp = null;
          checkMethod = 0;
          index = 0;
          s = p = z = 0.0D;
        } else if (checkMethod == 2) {
          i = 599 + 70;
          for (j = 0; j < Maxtime && checkMethod != 0; j += dt) {
            int m = getOa(j, choice);
            int n = getSDMa(m, j);
            int k = i;
            i = -n + 599 + 70;
            deltaText.setText(String.valueOf(delta));
            DMshow.setColor(Color.blue);
            DMshow.drawLine(j + 100, k, j + 100, i);
            DMshow.fillOval(j + 100, i, 3, 3);
            DMshow.setFont(f1);
            DMshow.drawString(String.valueOf(code[j / dt]), j + 98, 65);
            s++;
            p += (m > n) ? (m - n) : (n - m);
            DMshow.drawLine(j + 100, i, j + 100 + dt, i);
            try {
              O_sp.sleep(dt * wait);
              // Thread.sleep((dt * wait));
            } catch (InterruptedException interruptedException) {
            }
          }
          p /= s;
          zl.setText(String.valueOf(s + p));
          DM_sp = null;
          checkMethod = 0;
          index = 0;
          s = p = z = 0.0D;
        } else if (checkMethod == 3) {
          i = 599 + 70;
          for (j = 0; j < Maxtime && checkMethod != 0; j += dt) {
            int m = getOa(j, choice);
            int n = getGDMa(m, j);
            int k = i;
            i = -n + 599 + 70;
            deltaText.setText(String.valueOf(delta));
            DMshow.setColor(Color.blue);
            DMshow.drawLine(j + 100, k, j + 100, i);
            DMshow.fillOval(j + 100, i, 3, 3);
            DMshow.setFont(f1);
            DMshow.drawString(String.valueOf(code[j / dt]), j + 98, 65);
            s++;
            p += (m > n) ? (m - n) : (n - m);
            DMshow.drawLine(j + 100, i, j + 100 + dt, i);
            try {
              O_sp.sleep(dt * wait);
              // Thread.sleep((dt * wait));
            } catch (InterruptedException interruptedException) {
            }
          }
          p /= s;
          zl.setText(String.valueOf(s + p));
          DM_sp = null;
          checkMethod = 0;
          index = 0;
          s = p = z = 0.0D;
        } else if (checkMethod == 5) {
          i = 599 + 70;
          for (j = 0; j < Maxtime && checkMethod != 0; j += dt) {
            int m = getOa(j, choice);
            int n = getZDMa(m, j);
            int k = i;
            i = -n + 599 + 70;
            deltaText.setText(String.valueOf(delta));
            dtText.setText(String.valueOf(dt));
            DMshow.setColor(Color.red);
            DMshow.drawLine(j + 100, k, j + 100, i);
            DMshow.fillOval(j + 100, i, 3, 3);
            f1 = new Font("Helvetica", 1, 10 + dt / 3);
            DMshow.setFont(f1);
            DMshow.drawString(String.valueOf(code[index - 1]), j + 98, 65);
            s++;
            p += (m > n) ? (m - n) : (n - m);
            DMshow.drawLine(j + 100, i, j + 100 + dt, i);
            try {
              O_sp.sleep(dt * wait);
              // Thread.sleep((dt * wait));
            } catch (InterruptedException interruptedException) {
            }
          }
          p /= s;
          zl.setText(String.valueOf(s + p));
          DM_sp = null;
          checkMethod = 0;
          index = 0;
          s = p = z = 0.0D;
        } else {
            this.DM_sp = null;
            this.checkMethod = 0;
        }
      }
    }
  }

    public void stop() {
        this.O_sp = null;
        this.DM_sp = null;
        this.checkMethod = 0;
        this.Oshow.clearRect(0, 0, 910, 650);
        this.DMshow.clearRect(0, 0, 910, 650);
    }

    int getOa(int paramInt1, int paramInt2) {
        switch (paramInt2) {
            case 1:
                if (paramInt1 < 200)
                    return 2 * paramInt1;
                if (paramInt1 < 300)
                    return -2 * paramInt1 + 800;
                if (paramInt1 < 600)
                    return 200;
                return (int) (2.5D * paramInt1) - 1300;
            case 2:
                if (paramInt1 < 100)
                    return paramInt1 * paramInt1 / 500 + paramInt1 / 2 + 100;
                if (paramInt1 < 250)
                    return -2 * paramInt1 * paramInt1 / 500 + 2 * paramInt1 + 10;
                if (paramInt1 < 300)
                    return -2 * paramInt1 + 760;
                if (paramInt1 < 400)
                    return paramInt1 * paramInt1 / 500 - paramInt1 + 280;
                if (paramInt1 < 480)
                    return -paramInt1 + 600;
                if (paramInt1 < 600)
                    return paramInt1 * paramInt1 / 600 - 2 * paramInt1 + 696;
                return 96;
            case 3:
                return (int) ((Math.cos((paramInt1 / 5)) + Math.sin((paramInt1 / 7)) + 2.0D) * (getSize()).height
                        / 5.0D);
            case 4:
                return (int) (Math.sin(paramInt1 / 60.0D) * 200.0D + 200.0D);
        }
        return 5;
    }

    int getDMa(int paramInt1, int paramInt2) {
        if (paramInt1 >= this.a[paramInt2 / this.dt]) {
            this.code[paramInt2 / this.dt] = 1;
            this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] + this.delta;
        } else {
            this.code[paramInt2 / this.dt] = 0;
            this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] - this.delta;
        }
        return this.a[paramInt2 / this.dt];
    }

    int getSDMa(int paramInt1, int paramInt2) {
        if (paramInt1 >= this.a[paramInt2 / this.dt]) {
            this.code[paramInt2 / this.dt] = 1;
        } else {
            this.code[paramInt2 / this.dt] = 0;
        }
        if (paramInt2 / this.dt >= 1 && this.code[paramInt2 / this.dt] == this.code[paramInt2 / this.dt - 1]) {
            this.delta += (int) (0.5D * (this.delta + 2));
        } else if (paramInt2 / this.dt >= 1) {
            this.delta -= (int) (0.5D * this.delta);
        } else {
            // this.delta = this.delta;
        }
        if (this.code[paramInt2 / this.dt] == 1) {
            this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] + this.delta;
        } else {
            this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] - this.delta;
        }
        return this.a[paramInt2 / this.dt];
    }

    int getGDMa(int paramInt1, int paramInt2) {
        if (paramInt1 >= this.a[paramInt2 / this.dt]) {
            this.code[paramInt2 / this.dt] = 1;
        } else {
            this.code[paramInt2 / this.dt] = 0;
        }
        if (paramInt2 / this.dt >= 2 && this.code[paramInt2 / this.dt] == this.code[paramInt2 / this.dt - 1]
                && this.code[paramInt2 / this.dt - 1] == this.code[paramInt2 / this.dt - 2]) {
            this.delta = this.Maxdelta;
        } else if (paramInt2 / this.dt >= 2) {
            this.delta = this.Mindelta;
        } else {
            // this.delta = this.delta;
        }
        if (this.code[paramInt2 / this.dt] == 1) {
            this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] + this.delta;
        } else {
            this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] - this.delta;
        }
        return this.a[paramInt2 / this.dt];
    }

    int getZDMa(int paramInt1, int paramInt2) {
        if (paramInt1 >= this.a[this.index]) {
            this.code[this.index] = 1;
        } else {
            this.code[this.index] = 0;
        }
        if (this.index >= 2 && this.code[this.index] == this.code[this.index - 1]
                && this.code[this.index - 1] == this.code[this.index - 2]) {
            this.delta += (int) (0.5D * (this.delta + 2));
            if (this.dt > 64) {
                this.dt /= 10;
            } else if (this.dt > 16) {
                this.dt = this.dt / 2 - this.dt / 4;
            } else {
                this.dt -= this.dt / 2;
            }
        } else if (this.index >= 2) {
            this.delta -= (int) (0.5D * this.delta) - 1;
            if (this.dt <= 4) {
                this.dt *= 2;
            } else if (this.dt <= 16) {
                this.dt += this.dt / 5;
            } else if (this.dt <= 64) {
                this.dt += this.dt / 10;
            } else {
                this.dt += 10;
            }
        } else {
            // this.delta = this.delta;
        }
        if (this.code[this.index] == 1) {
            this.a[this.index + 1] = this.a[this.index] + this.delta;
        } else {
            this.a[this.index + 1] = this.a[this.index] - this.delta;
        }
        this.index++;
        return this.a[this.index - 1];
    }
}