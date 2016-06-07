package game.model;

import game.gameMain;
import javafx.scene.media.AudioClip;

public class Audio {
	
	AudioClip Audio ;
	
	public Audio(int times,String loc) {
		Audio = new AudioClip(gameMain.class.getResource(loc).toString());
		Audio.setCycleCount(times);
		Audio.setVolume(0.5f);
	}
	
	public void play() {
		Audio.play();
	}
	
	public void stop() {
		Audio.stop();
	}
	
	public boolean isPlaying() {
		return Audio.isPlaying();
	}
}
