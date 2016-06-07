package game.view;

import game.gameMain;
import game.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginViewController {
	
	@FXML	private TextField p1U;
	@FXML	private PasswordField p1P;
	@FXML	private Button p1LG;
	@FXML	private Button p1C;
	@FXML	private Label p1L;
	
	@FXML	private TextField p2U;
	@FXML	private PasswordField p2P;
	@FXML	private Button p2LG;
	@FXML	private Button p2C;
	@FXML	private Label p2L;
	
	@FXML	private Label playL;
	@FXML	private Button playButton;
	
	public String player1Path;
	public String player2Path;
	
	
	public void p1LGclick() throws Exception{
		LoginModel player1 = new LoginModel(p1U,p1P,p1L);
		player1.userLogin(p2U.getText());
		player1Path = player1.setPath();
	}
	public void p2LGclick() throws Exception{
		LoginModel player2 = new LoginModel(p2U,p2P,p2L);
		player2.userLogin(p1U.getText());
		player2Path =  player2.setPath();
	}

	public void p1Cclick() throws Exception{
		LoginModel player1 = new LoginModel(p1U,p1P,p1L);
		player1.createUserFile();
	}
	public void p2Cclick() throws Exception{
		LoginModel player2 = new LoginModel(p2U,p2P,p2L);
		player2.createUserFile();
	}
	
	public void playGame(ActionEvent event){
		gameMain main = new gameMain();
		String result = "login error,so you can't play the game!";
		if(player1Path == null && player2Path == null){
			Stage changeView = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
			main.pingpong(changeView,p1U.getText(),p2U.getText(),player1Path,player2Path);
		}
		playL.setText(result);
	}
}
