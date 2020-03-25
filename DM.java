import java.applet.Applet;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DM extends Applet implements ActionListener, Runnable, ItemListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Choice choice1;
    Button DM_button;
    Button SDM_button;
    Button GDM_button;
    Button ZDM_button;
    Button button;
    TextField dtText;
    TextField deltaText;
    Label l1;
    Label l2;
    Label l3;
    Label zl;
    Thread O_sp;
    Thread DM_sp;
    Graphics Oshow;
    Graphics DMshow;
    int checkMethod;
    int choice;
    int dt;
    int Maxtime;
    int delta;
    int Maxdelta;
    int Mindelta;
    byte[] code;
    int[] a;
    int wait;
    Font f1;
    int index;
    double s;
    double p;
    double z;

    public void init() {
        setBackground(Color.white);
        this.l3 = new Label("Z评分+:", 2);
        this.l3.setForeground(Color.red);
        add(this.l3);
        this.zl = new Label("                ", 0);
        this.zl.setForeground(Color.black);
        add(this.zl);
        this.l1 = new Label("量化间隔:", 2);
        this.l1.setForeground(Color.black);
        add(this.l1);
        this.dtText = new TextField("6", 5);
        add(this.dtText);
        this.l2 = new Label("量化阶:", 2);
        this.l2.setForeground(Color.black);
        add(this.l2);
        this.deltaText = new TextField("18", 5);
        add(this.deltaText);
        this.DM_button = new Button("增量调制");
        this.DM_button.setForeground(Color.black);
        add(this.DM_button);
        this.SDM_button = new Button("宋适应");
        this.SDM_button.setForeground(Color.black);
        add(this.SDM_button);
        this.GDM_button = new Button("格林适应");
        this.GDM_button.setForeground(Color.black);
        add(this.GDM_button);
        this.ZDM_button = new Button("Geo适应");
        this.ZDM_button.setForeground(Color.red);
        add(this.ZDM_button);
        this.DM_button.addActionListener(this);
        this.SDM_button.addActionListener(this);
        this.GDM_button.addActionListener(this);
        this.ZDM_button.addActionListener(this);
        this.choice1 = new Choice();
        this.choice1.add("突变");
        this.choice1.add("平缓");
        this.choice1.add("高频随机");
        this.choice1.add("正弦");
        this.choice1.add("安静");
        add(this.choice1);
        this.choice1.addItemListener(this);
        this.Oshow = getGraphics();
        this.DMshow = getGraphics();
        this.checkMethod = 0;
        this.choice = 2;
        this.dt = Integer.parseInt(this.dtText.getText());
        this.delta = Integer.parseInt(this.deltaText.getText());
        this.Maxtime = 830;
        this.code = new byte[this.Maxtime / this.dt + 5];
        this.a = new int[this.Maxtime / this.dt + 5];
        this.Maxdelta = 2 * this.dt + this.delta;
        this.Mindelta = this.delta / 2;
        this.a[0] = 0;
        this.wait = 5;
        this.index = 0;
        this.s = this.p = this.z = 0.0D;
    }

    public void actionPerformed(ActionEvent paramActionEvent) {
        if (this.checkMethod == 0) {
            repaint();
            if (paramActionEvent.getSource() == this.DM_button) {
                start();
                this.checkMethod = 1;
            } else if (paramActionEvent.getSource() == this.SDM_button) {
                start();
                this.checkMethod = 2;
            } else if (paramActionEvent.getSource() == this.GDM_button) {
                start();
                this.checkMethod = 3;
            } else if (paramActionEvent.getSource() == this.ZDM_button) {
                start();
                this.checkMethod = 5;
            } else {
                this.checkMethod = 0;
            }
        }
    }

    public void itemStateChanged(ItemEvent paramItemEvent) {
        if (paramItemEvent.getItemSelectable() == this.choice1 && this.checkMethod == 0)
            if (this.choice1.getSelectedIndex() == 0) {
                this.choice = 1;
            } else if (this.choice1.getSelectedIndex() == 1) {
                this.choice = 2;
            } else if (this.choice1.getSelectedIndex() == 2) {
                this.choice = 3;
            } else if (this.choice1.getSelectedIndex() == 3) {
                this.choice = 4;
            } else {
                this.choice = 5;
            }
    }

    public void start() {
        this.s = this.p = this.z = 0.0D;
        this.index = 0;
        this.checkMethod = 0;
        this.delta = Integer.parseInt(this.deltaText.getText());
        this.dt = Integer.parseInt(this.dtText.getText());
        this.O_sp = new Thread(this);
        this.DM_sp = new Thread(this);
        this.O_sp.start();
    }

    public void paint(Graphics paramGraphics) {
        this.delta = Integer.parseInt(this.deltaText.getText());
        this.dt = Integer.parseInt(this.dtText.getText());
        this.f1 = new Font("Helvetica", 1, 10 + this.dt / 3);
        this.code = new byte[this.Maxtime + 5];
        this.a = new int[this.Maxtime + 5];
        this.Maxdelta = 2 * this.dt + this.delta;
        this.Mindelta = this.delta / 2;
        paramGraphics.setColor(Color.white);
        paramGraphics.fillRect(0, 0, 1017, 650);
        paramGraphics.setColor(Color.black);
        for (byte b = 0; b < 28; b++) {
            paramGraphics.drawString(String.valueOf(b * 30), 95 + b * 30, 620);
            paramGraphics.fillOval(100 + b * 30, 600, 2, 8);
            paramGraphics.drawString(String.valueOf(b * 18), 70, 605 - b * 18);
            paramGraphics.fillOval(92, 600 - b * 18, 8, 2);
        }
        paramGraphics.setColor(Color.black);
        paramGraphics.drawLine(95, 605, 95, 30);
        paramGraphics.drawLine(95, 605, 950, 605);
        int[] arrayOfInt1 = { 95, 98, 92, 95 };
        int[] arrayOfInt2 = { 18, 30, 30, 18 };
        paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 4);
        int[] arrayOfInt3 = { 962, 950, 950, 962 };
        int[] arrayOfInt4 = { 605, 608, 602, 605 };
        paramGraphics.fillPolygon(arrayOfInt3, arrayOfInt4, 4);
    }

    public void run() {
        while (this.checkMethod != 0) {
            if (Thread.currentThread() == this.O_sp) {
                this.DM_sp.start();
                this.Oshow.setColor(Color.white);
                this.Oshow.fillRect(100, 50, 820, 550);
                int i = -getOa(0, this.choice) + 599;
                for (int b = 0; b < this.Maxtime && this.checkMethod != 0; b++) {
                    int j = -getOa(b, this.choice) + 599;
                    this.Oshow.setColor(Color.black);
                    this.Oshow.drawLine(b + 99, i, b + 100, j);
                    this.Oshow.fillOval(b + 100, j, 2, 2);
                    i = j;
                    try {
                        // this.O_sp.sleep(this.wait);
                        Thread.sleep(this.wait);
                    } catch (InterruptedException interruptedException) {
                    }
                }
                this.O_sp = null;
                continue;
            }
            if (Thread.currentThread() == this.DM_sp) {
                int i = 599;
                int j;
                if (checkMethod == 1) {
                    for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
                        int m = getOa(j, this.choice);
                        int n = getDMa(m, j);
                        int k = i;
                        i = -n + 599;
                        this.DMshow.setColor(Color.blue);
                        this.DMshow.drawLine(j + 100, k, j + 100, i);
                        this.DMshow.fillOval(j + 100, i, 3, 3);
                        this.DMshow.setFont(this.f1);
                        this.DMshow.drawString(String.valueOf(this.code[j / this.dt]), j + 98, 65);
                        this.s++;
                        this.p += (m > n) ? (m - n) : (n - m);
                        this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
                        try {
                            // this.O_sp.sleep(this.dt * this.wait);
                            Thread.sleep((this.dt * this.wait));
                        } catch (InterruptedException interruptedException) {
                        }
                    }
                    this.p /= this.s;
                    this.zl.setText(String.valueOf(this.s + this.p));
                    this.DM_sp = null;
                    this.checkMethod = 0;
                    this.index = 0;
                    this.s = this.p = this.z = 0.0D;
                } else if (checkMethod == 2) {
                    i = 599;
                    for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
                        int m = getOa(j, this.choice);
                        int n = getSDMa(m, j);
                        int k = i;
                        i = -n + 599;
                        this.deltaText.setText(String.valueOf(this.delta));
                        this.DMshow.setColor(Color.blue);
                        this.DMshow.drawLine(j + 100, k, j + 100, i);
                        this.DMshow.fillOval(j + 100, i, 3, 3);
                        this.DMshow.setFont(this.f1);
                        this.DMshow.drawString(String.valueOf(this.code[j / this.dt]), j + 98, 65);
                        this.s++;
                        this.p += (m > n) ? (m - n) : (n - m);
                        this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
                        try {
                            // this.O_sp.sleep(this.dt * this.wait);
                            Thread.sleep((this.dt * this.wait));
                        } catch (InterruptedException interruptedException) {
                        }
                    }
                    this.p /= this.s;
                    this.zl.setText(String.valueOf(this.s + this.p));
                    this.DM_sp = null;
                    this.checkMethod = 0;
                    this.index = 0;
                    this.s = this.p = this.z = 0.0D;
                } else if (checkMethod == 3) {
                    i = 599;
                    for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
                        int m = getOa(j, this.choice);
                        int n = getGDMa(m, j);
                        int k = i;
                        i = -n + 599;
                        this.deltaText.setText(String.valueOf(this.delta));
                        this.DMshow.setColor(Color.blue);
                        this.DMshow.drawLine(j + 100, k, j + 100, i);
                        this.DMshow.fillOval(j + 100, i, 3, 3);
                        this.DMshow.setFont(this.f1);
                        this.DMshow.drawString(String.valueOf(this.code[j / this.dt]), j + 98, 65);
                        this.s++;
                        this.p += (m > n) ? (m - n) : (n - m);
                        this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
                        try {
                            // this.O_sp.sleep(this.dt * this.wait);
                            Thread.sleep((this.dt * this.wait));
                        } catch (InterruptedException interruptedException) {
                        }
                    }
                    this.p /= this.s;
                    this.zl.setText(String.valueOf(this.s + this.p));
                    this.DM_sp = null;
                    this.checkMethod = 0;
                    this.index = 0;
                    this.s = this.p = this.z = 0.0D;
                } else if (checkMethod == 5) {
                    i = 599;
                    for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
                        int m = getOa(j, this.choice);
                        int n = getZDMa(m, j);
                        int k = i;
                        i = -n + 599;
                        this.deltaText.setText(String.valueOf(this.delta));
                        this.dtText.setText(String.valueOf(this.dt));
                        this.DMshow.setColor(Color.red);
                        this.DMshow.drawLine(j + 100, k, j + 100, i);
                        this.DMshow.fillOval(j + 100, i, 3, 3);
                        this.f1 = new Font("Helvetica", 1, 10 + this.dt / 3);
                        this.DMshow.setFont(this.f1);
                        this.DMshow.drawString(String.valueOf(this.code[this.index - 1]), j + 98, 65);
                        this.s++;
                        this.p += (m > n) ? (m - n) : (n - m);
                        this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
                        try {
                            // this.O_sp.sleep(this.dt * this.wait);
                            Thread.sleep((this.dt * this.wait));
                        } catch (InterruptedException interruptedException) {
                        }
                    }
                    this.p /= this.s;
                    this.zl.setText(String.valueOf(this.s + this.p));
                    this.DM_sp = null;
                    this.checkMethod = 0;
                    this.index = 0;
                    this.s = this.p = this.z = 0.0D;
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