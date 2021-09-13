import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author EthanHuynh DayViewPanel creates a day view of the calendar. This
 *         class is a customized JPanel.
 */
public class DayViewPanel extends JPanel implements ChangeListener {
	private DataModel dataModel;
	private ArrayList<Event> data;
	private LocalDate currentTime;
	private static final int COLUMN_SIZE = 26;
	private ImageIcon icon = new ImageIcon("C:/Users/trung/OneDrive/Desktop/Java/CS151HW4/redx2.png");

	/**
	 * Constructs an instance of the DayViewPanel by taking in a dataModel.
	 * 
	 * @param model receives a dataModel
	 */
	public DayViewPanel(DataModel model) {
		this.dataModel = model;
		data = this.dataModel.getData();
		currentTime = dataModel.getCalendar();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(400, 700));// 800/620S
		viewByDay(currentTime);
		setBackground(Color.WHITE);
	}

	/**
	 * Fill in the panel with events happening in the day.
	 * 
	 * @param local receives LocalDate
	 */
	public void viewByDay(LocalDate local) {
		ArrayList<JLabel> firstColumn = new ArrayList<>();
		ArrayList<JLabel> secondColumn = new ArrayList<>();
		ArrayList<JComponent> thirdColumn = new ArrayList<>();
		ArrayList<String> todaysEvents = new ArrayList<>();
		ArrayList<Event> todaysE=new ArrayList<>();
		currentTime = local;
		DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
		int year = Integer.valueOf(yearFormatter.format(currentTime));
		// goes over the MyCalendar's array list and place events happening today on
		// today's list
		// initialize
		for (int i = 0; i < COLUMN_SIZE; i++) {
			JLabel time = new JLabel(" ");
			time.setOpaque(true);
			firstColumn.add(time);
			JLabel descript = new JLabel(" ");
			descript.setMaximumSize(new Dimension(Integer.MAX_VALUE, descript.getMinimumSize().height));
			descript.setOpaque(true);
			secondColumn.add(descript);
			JLabel removeB = new JLabel(" ");
			thirdColumn.add(removeB);
		}
		int count = 0;
		for (int i = 2; i < COLUMN_SIZE; i++) {
			if (count < 10) {
				firstColumn.get(i).setText("0" + count + ":" + "00");
			} else {
				firstColumn.get(i).setText(count + ":" + "00");
			}
			count++;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMMM d, yyyy");
		firstColumn.set(1, new JLabel("Time"));
		secondColumn.set(0, new JLabel(formatter.format(currentTime)));
		secondColumn.get(0).setBackground(Color.ORANGE);
		secondColumn.get(0).setOpaque(true);
		secondColumn.set(1, new JLabel("Description"));
		thirdColumn.set(1, new JLabel("Remove"));
		for (Event s : data) { // extracts today's event list
			if ((currentTime.getDayOfWeek().name().equals(s.getDayOfWeek().toUpperCase())
					&& currentTime.getDayOfYear() == s.getStartingTotalDays()
					&& currentTime.getYear() == s.getYear())
					|| currentTime.getDayOfWeek().name().equals(s.getDayOfWeek().toUpperCase())
							&& (currentTime.getDayOfYear() >= s.getStartingTotalDays()
									&& currentTime.getDayOfYear() <= s.getEndingTotalDays()
									&& currentTime.getYear() == s.getYear() )) {
				// filter only for today's events
				todaysEvents.add(s.getStartingHour() + "");
				todaysEvents.add(s.toString());
				todaysE.add(s);
			}
		}
		if (!todaysEvents.isEmpty()) { // check corner case
			for (int i = 0; i < todaysEvents.size(); i += 2) {// iterate through event list and add to labels
				for (int j = 2; j < COLUMN_SIZE; j++) {
					if (Integer.valueOf(todaysEvents.get(i)) == Integer
							.valueOf(firstColumn.get(j).getText().substring(0, 2))) {
						secondColumn.get(j).setText(todaysEvents.get(i + 1));// sets description
						JButton remove = new JButton(icon);
						remove.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg) {
								// TODO Auto-generated method stub
								int startH=Integer.valueOf(firstColumn.get(thirdColumn.indexOf(remove)).getText().substring(0,2));
								for(Event e:todaysE) {
									if(e.getStartingHour()==startH) {
										dataModel.removeEvent(e);
									}
								}
							}

						});
						remove.setPreferredSize(new Dimension(30, 20));
						thirdColumn.set(j, remove);
					}
				}
			}
		}
		JPanel firstColumnPane = new JPanel();
		firstColumnPane.setLayout(new BoxLayout(firstColumnPane, BoxLayout.Y_AXIS));
		JPanel secondColumnPane = new JPanel();
		secondColumnPane.setLayout(new BoxLayout(secondColumnPane, BoxLayout.Y_AXIS));
		JPanel thirdColumnPane = new JPanel();
		thirdColumnPane.setLayout(new BoxLayout(thirdColumnPane, BoxLayout.Y_AXIS));

		for (int i = 0; i < COLUMN_SIZE; i++) {

			firstColumnPane.add(firstColumn.get(i));
			firstColumnPane.add(Box.createRigidArea(new Dimension(0, 11)));
			secondColumnPane.add(secondColumn.get(i));
			secondColumnPane.add(Box.createRigidArea(new Dimension(0, 11)));
			thirdColumnPane.add(thirdColumn.get(i));
			thirdColumnPane.add(Box.createRigidArea(new Dimension(0, 11)));
		}
		add(firstColumnPane);
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(secondColumnPane);
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(thirdColumnPane);
	}

	/**
	 * Overrides the stateChanged method from the implementation of the
	 * ChangeListener's method. Receives notification from the data source.
	 * 
	 * @param g receives the ChangeEvent from the dataModel.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		this.data = dataModel.getData();
		this.currentTime = dataModel.getCalendar();
		removeAll();
		viewByDay(currentTime);
		repaint();
	}
	/*	*//**
			 * Overrides the paintComponent of the JPanel.
			 * 
			 * @param g receives graphics
			 *//*
				 * @Override public void paintComponent(Graphics g) { super.paintComponent(g);
				 * RoundRectangle2D box=new
				 * RoundRectangle2D.Double(todaysDate.getX(),todaysDate.getY()+12, 800, 20, 10,
				 * 10); Graphics2D g2 = (Graphics2D) g; g2.setPaint(Color.ORANGE); g2.fill(box);
				 * }
				 */

}
