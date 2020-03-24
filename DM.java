import java.applet.Applet;
import java.awt.Button;
/*     */ import java.awt.Choice;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Label;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ 
/*     */ public class DM extends Applet implements ActionListener, Runnable, ItemListener {
/*     */   Choice choice1;
/*     */   
/*     */   Button DM_button;
/*     */   
/*     */   Button SDM_button;
/*     */   
/*     */   Button GDM_button;
/*     */   
/*     */   Button ZDM_button;
/*     */   
/*     */   Button button;
/*     */   
/*     */   TextField dtText;
/*     */   
/*     */   TextField deltaText;
/*     */   
/*     */   Label l1;
/*     */   
/*     */   Label l2;
/*     */   
/*     */   Label l3;
/*     */   
/*     */   Label zl;
/*     */   
/*     */   Thread O_sp;
/*     */   
/*     */   Thread DM_sp;
/*     */   
/*     */   Graphics Oshow;
/*     */   
/*     */   Graphics DMshow;
/*     */   
/*     */   int checkMethod;
/*     */   
/*     */   int choice;
/*     */   
/*     */   int dt;
/*     */   
/*     */   int Maxtime;
/*     */   
/*     */   int delta;
/*     */   
/*     */   int Maxdelta;
/*     */   
/*     */   int Mindelta;
/*     */   
/*     */   byte[] code;
/*     */   
/*     */   int[] a;
/*     */   
/*     */   int wait;
/*     */   
/*     */   Font f1;
/*     */   
/*     */   int index;
/*     */   
/*     */   double s;
/*     */   
/*     */   double p;
/*     */   
/*     */   double z;
/*     */   
/*     */   public void init() {
/*  38 */     setBackground(Color.white);
/*  40 */     this.l3 = new Label("Z评分:", 2);
/*  40 */     this.l3.setForeground(Color.red);
/*  40 */     add(this.l3);
/*  41 */     this.zl = new Label("                ", 0);
/*  41 */     this.zl.setForeground(Color.black);
/*  41 */     add(this.zl);
/*  42 */     this.l1 = new Label("量化间隔:", 2);
/*  42 */     this.l1.setForeground(Color.black);
/*  42 */     add(this.l1);
/*  43 */     this.dtText = new TextField("6", 5);
/*  43 */     add(this.dtText);
/*  44 */     this.l2 = new Label("量化阶:", 2);
/*  44 */     this.l2.setForeground(Color.black);
/*  44 */     add(this.l2);
/*  45 */     this.deltaText = new TextField("18", 5);
/*  45 */     add(this.deltaText);
/*  46 */     this.DM_button = new Button("增量调制");
/*  46 */     this.DM_button.setForeground(Color.black);
/*  46 */     add(this.DM_button);
/*  47 */     this.SDM_button = new Button("宋适应");
/*  47 */     this.SDM_button.setForeground(Color.black);
/*  47 */     add(this.SDM_button);
/*  48 */     this.GDM_button = new Button("格林适应");
/*  48 */     this.GDM_button.setForeground(Color.black);
/*  48 */     add(this.GDM_button);
/*  49 */     this.ZDM_button = new Button("Geo适应");
/*  49 */     this.ZDM_button.setForeground(Color.red);
/*  49 */     add(this.ZDM_button);
/*  50 */     this.DM_button.addActionListener(this);
/*  51 */     this.SDM_button.addActionListener(this);
/*  52 */     this.GDM_button.addActionListener(this);
/*  54 */     this.ZDM_button.addActionListener(this);
/*  55 */     this.choice1 = new Choice();
/*  56 */     this.choice1.add("突变");
/*  57 */     this.choice1.add("平缓");
/*  58 */     this.choice1.add("高频随机");
/*  59 */     this.choice1.add("正弦");
/*  60 */     this.choice1.add("安静");
/*  61 */     add(this.choice1);
/*  62 */     this.choice1.addItemListener(this);
/*  63 */     this.Oshow = getGraphics();
/*  64 */     this.DMshow = getGraphics();
/*  65 */     this.checkMethod = 0;
/*  66 */     this.choice = 2;
/*  68 */     this.dt = Integer.parseInt(this.dtText.getText());
/*  69 */     this.delta = Integer.parseInt(this.deltaText.getText());
/*  70 */     this.Maxtime = 830;
/*  71 */     this.code = new byte[this.Maxtime / this.dt + 5];
/*  72 */     this.a = new int[this.Maxtime / this.dt + 5];
/*  73 */     this.Maxdelta = 2 * this.dt + this.delta;
/*  74 */     this.Mindelta = this.delta / 2;
/*  75 */     this.a[0] = 0;
/*  76 */     this.wait = 5;
/*  77 */     this.index = 0;
/*  78 */     this.s = this.p = this.z = 0.0D;
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/*  82 */     if (this.checkMethod == 0) {
/*  84 */       repaint();
/*  85 */       if (paramActionEvent.getSource() == this.DM_button) {
/*  85 */         start();
/*  85 */         this.checkMethod = 1;
/*  86 */       } else if (paramActionEvent.getSource() == this.SDM_button) {
/*  86 */         start();
/*  86 */         this.checkMethod = 2;
/*  87 */       } else if (paramActionEvent.getSource() == this.GDM_button) {
/*  87 */         start();
/*  87 */         this.checkMethod = 3;
/*  88 */       } else if (paramActionEvent.getSource() == this.ZDM_button) {
/*  88 */         start();
/*  88 */         this.checkMethod = 5;
/*     */       } else {
/*  89 */         this.checkMethod = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void itemStateChanged(ItemEvent paramItemEvent) {
/*  94 */     if (paramItemEvent.getItemSelectable() == this.choice1 && this.checkMethod == 0)
/*  96 */       if (this.choice1.getSelectedIndex() == 0) {
/*  96 */         this.choice = 1;
/*  97 */       } else if (this.choice1.getSelectedIndex() == 1) {
/*  97 */         this.choice = 2;
/*  98 */       } else if (this.choice1.getSelectedIndex() == 2) {
/*  98 */         this.choice = 3;
/*  99 */       } else if (this.choice1.getSelectedIndex() == 3) {
/*  99 */         this.choice = 4;
/*     */       } else {
/* 100 */         this.choice = 5;
/*     */       }  
/*     */   }
/*     */   
/*     */   public void start() {
/* 105 */     this.s = this.p = this.z = 0.0D;
/* 106 */     this.index = 0;
/* 107 */     this.checkMethod = 0;
/* 108 */     this.delta = Integer.parseInt(this.deltaText.getText());
/* 109 */     this.dt = Integer.parseInt(this.dtText.getText());
/* 110 */     this.O_sp = new Thread(this);
/* 111 */     this.DM_sp = new Thread(this);
/* 113 */     this.O_sp.start();
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 117 */     this.delta = Integer.parseInt(this.deltaText.getText());
/* 118 */     this.dt = Integer.parseInt(this.dtText.getText());
/* 119 */     this.f1 = new Font("Helvetica", 1, 10 + this.dt / 3);
/* 120 */     this.code = new byte[this.Maxtime + 5];
/* 121 */     this.a = new int[this.Maxtime + 5];
/* 122 */     this.Maxdelta = 2 * this.dt + this.delta;
/* 123 */     this.Mindelta = this.delta / 2;
/* 124 */     paramGraphics.setColor(Color.white);
/* 125 */     paramGraphics.fillRect(0, 0, 1017, 650);
/* 126 */     paramGraphics.setColor(Color.black);
/* 127 */     for (byte b = 0; b < 28; b++) {
/* 129 */       paramGraphics.drawString(String.valueOf(b * 30), 95 + b * 30, 620);
/* 130 */       paramGraphics.fillOval(100 + b * 30, 600, 2, 8);
/* 131 */       paramGraphics.drawString(String.valueOf(b * 18), 70, 605 - b * 18);
/* 132 */       paramGraphics.fillOval(92, 600 - b * 18, 8, 2);
/*     */     } 
/* 134 */     paramGraphics.setColor(Color.black);
/* 135 */     paramGraphics.drawLine(95, 605, 95, 30);
/* 136 */     paramGraphics.drawLine(95, 605, 950, 605);
/* 137 */     int[] arrayOfInt1 = { 95, 98, 92, 95 };
/* 138 */     int[] arrayOfInt2 = { 18, 30, 30, 18 };
/* 139 */     paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 4);
/* 140 */     int[] arrayOfInt3 = { 962, 950, 950, 962 };
/* 141 */     int[] arrayOfInt4 = { 605, 608, 602, 605 };
/* 142 */     paramGraphics.fillPolygon(arrayOfInt3, arrayOfInt4, 4);
/*     */   }
/*     */   
/*     */   public void run() {
/* 146 */     while (this.checkMethod != 0) {
/* 148 */       if (Thread.currentThread() == this.O_sp) {
/* 150 */         this.DM_sp.start();
/* 151 */         this.Oshow.setColor(Color.white);
/* 152 */         this.Oshow.fillRect(100, 50, 820, 550);
/* 154 */         int i = -getOa(0, this.choice) + 599;
/* 155 */         for (byte b = 0; b < this.Maxtime && this.checkMethod != 0; b++) {
/* 157 */           int j = -getOa(b, this.choice) + 599;
/* 158 */           this.Oshow.setColor(Color.black);
/* 159 */           this.Oshow.drawLine(b + 99, i, b + 100, j);
/* 160 */           this.Oshow.fillOval(b + 100, j, 2, 2);
/* 161 */           i = j;
/*     */           try {
/* 162 */             //this.O_sp.sleep(this.wait);
/* 162 */             Thread.sleep(this.wait);
/* 163 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/* 165 */         this.O_sp = null;
/*     */         continue;
/*     */       } 
/* 167 */       if (Thread.currentThread() == this.DM_sp) {
/* 171 */         int i = 599;
/*     */         int j;
/* 172 */         for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
/* 174 */           int m = getOa(j, this.choice);
/* 175 */           int n = getDMa(m, j);
/* 176 */           int k = i;
/* 177 */           i = -n + 599;
/* 178 */           this.DMshow.setColor(Color.blue);
/* 179 */           this.DMshow.drawLine(j + 100, k, j + 100, i);
/* 180 */           this.DMshow.fillOval(j + 100, i, 3, 3);
/* 180 */           this.DMshow.setFont(this.f1);
/* 181 */           this.DMshow.drawString(String.valueOf(this.code[j / this.dt]), j + 98, 65);
/* 182 */           this.s++;
/* 183 */           this.p += (m > n) ? (m - n) : (n - m);
/* 184 */           this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
/*     */           try {
/* 185 */             //this.O_sp.sleep(this.dt * this.wait);
/* 185 */             Thread.sleep((this.dt * this.wait));
/* 186 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/* 188 */         this.p /= this.s;
/* 189 */         this.zl.setText(String.valueOf(this.s + this.p));
/* 190 */         this.DM_sp = null;
/* 190 */         this.checkMethod = 0;
/* 190 */         this.index = 0;
/* 190 */         this.s = this.p = this.z = 0.0D;
/* 196 */         i = 599;
/* 197 */         for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
/* 199 */           int m = getOa(j, this.choice);
/* 200 */           int n = getSDMa(m, j);
/* 201 */           int k = i;
/* 202 */           i = -n + 599;
/* 203 */           this.deltaText.setText(String.valueOf(this.delta));
/* 204 */           this.DMshow.setColor(Color.blue);
/* 205 */           this.DMshow.drawLine(j + 100, k, j + 100, i);
/* 206 */           this.DMshow.fillOval(j + 100, i, 3, 3);
/* 206 */           this.DMshow.setFont(this.f1);
/* 207 */           this.DMshow.drawString(String.valueOf(this.code[j / this.dt]), j + 98, 65);
/* 208 */           this.s++;
/* 209 */           this.p += (m > n) ? (m - n) : (n - m);
/* 210 */           this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
/*     */           try {
/* 211 */             //this.O_sp.sleep(this.dt * this.wait);
/* 211 */             Thread.sleep((this.dt * this.wait));
/* 212 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/* 214 */         this.p /= this.s;
/* 215 */         this.zl.setText(String.valueOf(this.s + this.p));
/* 216 */         this.DM_sp = null;
/* 216 */         this.checkMethod = 0;
/* 216 */         this.index = 0;
/* 216 */         this.s = this.p = this.z = 0.0D;
/* 221 */         i = 599;
/* 222 */         for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
/* 224 */           int m = getOa(j, this.choice);
/* 225 */           int n = getGDMa(m, j);
/* 226 */           int k = i;
/* 227 */           i = -n + 599;
/* 228 */           this.deltaText.setText(String.valueOf(this.delta));
/* 229 */           this.DMshow.setColor(Color.blue);
/* 230 */           this.DMshow.drawLine(j + 100, k, j + 100, i);
/* 231 */           this.DMshow.fillOval(j + 100, i, 3, 3);
/* 231 */           this.DMshow.setFont(this.f1);
/* 232 */           this.DMshow.drawString(String.valueOf(this.code[j / this.dt]), j + 98, 65);
/* 233 */           this.s++;
/* 234 */           this.p += (m > n) ? (m - n) : (n - m);
/* 235 */           this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
/*     */           try {
/* 236 */             //this.O_sp.sleep(this.dt * this.wait);
/* 236 */             Thread.sleep((this.dt * this.wait));
/* 237 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/* 239 */         this.p /= this.s;
/* 240 */         this.zl.setText(String.valueOf(this.s + this.p));
/* 241 */         this.DM_sp = null;
/* 241 */         this.checkMethod = 0;
/* 241 */         this.index = 0;
/* 241 */         this.s = this.p = this.z = 0.0D;
/* 246 */         i = 599;
/* 247 */         for (j = 0; j < this.Maxtime && this.checkMethod != 0; j += this.dt) {
/* 249 */           int m = getOa(j, this.choice);
/* 250 */           int n = getZDMa(m, j);
/* 251 */           int k = i;
/* 252 */           i = -n + 599;
/* 253 */           this.deltaText.setText(String.valueOf(this.delta));
/* 254 */           this.dtText.setText(String.valueOf(this.dt));
/* 255 */           this.DMshow.setColor(Color.red);
/* 256 */           this.DMshow.drawLine(j + 100, k, j + 100, i);
/* 257 */           this.DMshow.fillOval(j + 100, i, 3, 3);
/* 258 */           this.f1 = new Font("Helvetica", 1, 10 + this.dt / 3);
/* 259 */           this.DMshow.setFont(this.f1);
/* 260 */           this.DMshow.drawString(String.valueOf(this.code[this.index - 1]), j + 98, 65);
/* 261 */           this.s++;
/* 262 */           this.p += (m > n) ? (m - n) : (n - m);
/* 263 */           this.DMshow.drawLine(j + 100, i, j + 100 + this.dt, i);
/*     */           try {
/* 264 */             //this.O_sp.sleep(this.dt * this.wait);
/* 264 */             Thread.sleep((this.dt * this.wait));
/* 265 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/* 267 */         this.p /= this.s;
/* 268 */         this.zl.setText(String.valueOf(this.s + this.p));
/* 269 */         this.DM_sp = null;
/* 269 */         this.checkMethod = 0;
/* 269 */         this.index = 0;
/* 269 */         this.s = this.p = this.z = 0.0D;
/* 271 */         this.DM_sp = null;
/* 271 */         this.checkMethod = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 278 */     this.O_sp = null;
/* 279 */     this.DM_sp = null;
/* 280 */     this.checkMethod = 0;
/* 281 */     this.Oshow.clearRect(0, 0, 910, 650);
/* 282 */     this.DMshow.clearRect(0, 0, 910, 650);
/*     */   }
/*     */   
/*     */   int getOa(int paramInt1, int paramInt2) {
/* 290 */     switch (paramInt2) {
/*     */       case 1:
/* 293 */         if (paramInt1 < 200)
/* 294 */           return 2 * paramInt1; 
/* 295 */         if (paramInt1 < 300)
/* 296 */           return -2 * paramInt1 + 800; 
/* 297 */         if (paramInt1 < 600)
/* 298 */           return 200; 
/* 300 */         return (int)(2.5D * paramInt1) - 1300;
/*     */       case 2:
/* 304 */         if (paramInt1 < 100)
/* 305 */           return paramInt1 * paramInt1 / 500 + paramInt1 / 2 + 100; 
/* 306 */         if (paramInt1 < 250)
/* 307 */           return -2 * paramInt1 * paramInt1 / 500 + 2 * paramInt1 + 10; 
/* 308 */         if (paramInt1 < 300)
/* 309 */           return -2 * paramInt1 + 760; 
/* 310 */         if (paramInt1 < 400)
/* 311 */           return paramInt1 * paramInt1 / 500 - paramInt1 + 280; 
/* 312 */         if (paramInt1 < 480)
/* 313 */           return -paramInt1 + 600; 
/* 314 */         if (paramInt1 < 600)
/* 315 */           return paramInt1 * paramInt1 / 600 - 2 * paramInt1 + 696; 
/* 316 */         return 96;
/*     */       case 3:
/* 320 */         return (int)((Math.cos((paramInt1 / 5)) + Math.sin((paramInt1 / 7)) + 2.0D) * (getSize()).height / 5.0D);
/*     */       case 4:
/* 323 */         return (int)(Math.sin(paramInt1 / 60.0D) * 200.0D + 200.0D);
/*     */     } 
/* 326 */     return 5;
/*     */   }
/*     */   
/*     */   int getDMa(int paramInt1, int paramInt2) {
/* 331 */     if (paramInt1 >= this.a[paramInt2 / this.dt]) {
/* 333 */       this.code[paramInt2 / this.dt] = 1;
/* 334 */       this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] + this.delta;
/*     */     } else {
/* 339 */       this.code[paramInt2 / this.dt] = 0;
/* 340 */       this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] - this.delta;
/*     */     } 
/* 342 */     return this.a[paramInt2 / this.dt];
/*     */   }
/*     */   
/*     */   int getSDMa(int paramInt1, int paramInt2) {
/* 347 */     if (paramInt1 >= this.a[paramInt2 / this.dt]) {
/* 348 */       this.code[paramInt2 / this.dt] = 1;
/*     */     } else {
/* 350 */       this.code[paramInt2 / this.dt] = 0;
/*     */     } 
/* 352 */     if (paramInt2 / this.dt >= 1 && this.code[paramInt2 / this.dt] == this.code[paramInt2 / this.dt - 1]) {
/* 353 */       this.delta += (int)(0.5D * (this.delta + 2));
/* 354 */     } else if (paramInt2 / this.dt >= 1) {
/* 355 */       this.delta -= (int)(0.5D * this.delta);
/*     */     } else {
/* 357 */       this.delta = this.delta;
/*     */     } 
/* 359 */     if (this.code[paramInt2 / this.dt] == 1) {
/* 360 */       this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] + this.delta;
/*     */     } else {
/* 363 */       this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] - this.delta;
/*     */     } 
/* 365 */     return this.a[paramInt2 / this.dt];
/*     */   }
/*     */   
/*     */   int getGDMa(int paramInt1, int paramInt2) {
/* 370 */     if (paramInt1 >= this.a[paramInt2 / this.dt]) {
/* 371 */       this.code[paramInt2 / this.dt] = 1;
/*     */     } else {
/* 373 */       this.code[paramInt2 / this.dt] = 0;
/*     */     } 
/* 375 */     if (paramInt2 / this.dt >= 2 && this.code[paramInt2 / this.dt] == this.code[paramInt2 / this.dt - 1] && this.code[paramInt2 / this.dt - 1] == this.code[paramInt2 / this.dt - 2]) {
/* 376 */       this.delta = this.Maxdelta;
/* 377 */     } else if (paramInt2 / this.dt >= 2) {
/* 378 */       this.delta = this.Mindelta;
/*     */     } else {
/* 380 */       this.delta = this.delta;
/*     */     } 
/* 382 */     if (this.code[paramInt2 / this.dt] == 1) {
/* 383 */       this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] + this.delta;
/*     */     } else {
/* 386 */       this.a[paramInt2 / this.dt + 1] = this.a[paramInt2 / this.dt] - this.delta;
/*     */     } 
/* 388 */     return this.a[paramInt2 / this.dt];
/*     */   }
/*     */   
/*     */   int getZDMa(int paramInt1, int paramInt2) {
/* 393 */     if (paramInt1 >= this.a[this.index]) {
/* 394 */       this.code[this.index] = 1;
/*     */     } else {
/* 396 */       this.code[this.index] = 0;
/*     */     } 
/* 398 */     if (this.index >= 2 && this.code[this.index] == this.code[this.index - 1] && this.code[this.index - 1] == this.code[this.index - 2]) {
/* 400 */       this.delta += (int)(0.5D * (this.delta + 2));
/* 401 */       if (this.dt > 64) {
/* 402 */         this.dt /= 10;
/* 403 */       } else if (this.dt > 16) {
/* 404 */         this.dt = this.dt / 2 - this.dt / 4;
/*     */       } else {
/* 405 */         this.dt -= this.dt / 2;
/*     */       } 
/* 407 */     } else if (this.index >= 2) {
/* 409 */       this.delta -= (int)(0.5D * this.delta) - 1;
/* 410 */       if (this.dt <= 4) {
/* 411 */         this.dt *= 2;
/* 412 */       } else if (this.dt <= 16) {
/* 413 */         this.dt += this.dt / 5;
/* 414 */       } else if (this.dt <= 64) {
/* 415 */         this.dt += this.dt / 10;
/*     */       } else {
/* 416 */         this.dt += 10;
/*     */       } 
/*     */     } else {
/* 419 */       this.delta = this.delta;
/*     */     } 
/* 421 */     if (this.code[this.index] == 1) {
/* 422 */       this.a[this.index + 1] = this.a[this.index] + this.delta;
/*     */     } else {
/* 425 */       this.a[this.index + 1] = this.a[this.index] - this.delta;
/*     */     } 
/* 427 */     this.index++;
/* 428 */     return this.a[this.index - 1];
/*     */   }
/*     */ }


/* Location:              C:\D\tail\多媒体\演示\DM2.1\!\DM.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */