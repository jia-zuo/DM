import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

public class Main{
	public static void main(String[] args){
		DM applet = new DM();
		Frame frame = new Frame("DM");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		frame.add(applet,BorderLayout.CENTER);
		frame.setSize(1024,768);
		applet.init();
		applet.start();
		frame.setVisible(true);
	}
}