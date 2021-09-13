import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Alarm {
	private Event anE;
	private boolean stillAlarm;
	private Calendar currentTime=Calendar.getInstance();
	private MediaPlayer mediaPlayer;
	public Alarm(Event e) {
		anE=e;
		stillAlarm=checkAlarm();
		final JFXPanel fxPanel = new JFXPanel();
		try {
			Media sound = new Media(new File("C:/Users/trung/OneDrive/Desktop/Java/CS151HW4/ringing_old_phone.mp3").toURI().toString());
			mediaPlayer = new MediaPlayer(sound);
			
		} catch(Exception x) {
			x.printStackTrace();
		}

	}
	public boolean checkAlarm() {
		if (anE.getStartingHour()<currentTime.get(Calendar.HOUR_OF_DAY)) {
			return false;
		}else {
			return true;
		}
	}
	public void alarm() {
		if (stillAlarm) {
			stillAlarm=false;
			mediaPlayer.play();
			int option = JOptionPane.showConfirmDialog(new JFrame(),"Please Press Ok to stop sound.", anE.getEventName(),JOptionPane.DEFAULT_OPTION);	
			if (option==JOptionPane.OK_OPTION||option==JOptionPane.CLOSED_OPTION) {
				mediaPlayer.stop();
			}
		}
	}
}
