import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
/**
 * @author EthanHuynh
 * MainFrame creates a frame to contain the calendar.
 */
public class MainFrame extends JFrame {
	private DataModel dataModel;
	private ArrayList<Event> data;
	/**
	 * Constructs a MainFrame by taking in a dataModel. Within the ctor,
	 * all the JComponents are written on on the frame.
	 * @param model receives dataModel
	 */
	public MainFrame(DataModel model) {
		this.dataModel = model;
		data = dataModel.getData();
		JPanel nav = new JPanel();
		nav.setBackground(Color.WHITE);
		JPanel nextPrev = new JPanel();
		JButton next = new JButton(">");
		JButton prev = new JButton("<");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setCalendar(dataModel.getCalendar().plusDays(1));
			}
		});
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setCalendar(dataModel.getCalendar().minusDays(1));
			}
		});
		nextPrev.add(prev);
		nextPrev.add(next);
		nav.add(BorderLayout.EAST, nextPrev);
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedWriter print = new BufferedWriter(new FileWriter(
							"C:/Users/trung/OneDrive/Desktop/Java/CS151HW4/events.txt"));
					for (Event event : data) {
						if (data.isEmpty())
							break;
						print.write(event.printToFile());
						print.newLine();
					}
					print.close();
				} catch (IOException x) {
					System.out.println(x.getMessage());
				}
				System.exit(0);
			}
		});
		nav.add(BorderLayout.WEST, quit);
		MonthViewPanel monthView = new MonthViewPanel(dataModel);
		DayViewPanel dayView = new DayViewPanel(dataModel);
		this.dataModel.attach(dayView);
		add(BorderLayout.NORTH, nav);
		add(BorderLayout.WEST, monthView);
		
		add(BorderLayout.CENTER, dayView);
		setBackground(Color.WHITE);
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

}
