package game.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginModel {
	
	private String username;
	private String password;
	private Label label;
	private String filePath="D:/eclipse/ping/playerData/";
	private FileWriter fw;
	private String result;
	public String playerPath;
	
	public LoginModel(TextField username,PasswordField password,Label label){
		this.username = username.getText();
		this.password = password.getText();
		this.label = label ;
	}
	
	public void setLabel(String str){
		label.setText(str);
	}
	
	public void userLogin(String another) throws Exception{
		result="Login failed!";
		
		File file=new File(filePath+username);
		if(username.equals("") || password.equals("")){
			result = "input error";
		}else if(username.equals(another)){
			result = "the same player,Please try again";
		}else if(file.exists()){
			FileReader fr = new FileReader(filePath+username+"/password.txt");
			BufferedReader br = new BufferedReader(fr);
			String pwd = null; 
			while (br.ready()) {
				pwd=br.readLine();
			}
			fr.close();
			if(pwd.equals(password)){
				result = "Login successful!";
			}else{
				result = "Wrong password!";
			}
		}else{
			result ="No this user";
		}
		label.setText(result);
	}
	
	public void createUserFile() throws Exception{
		result="create failed!";
		
		File file=new File(filePath+username);
		if(username.equals("") || password.equals("")){
			result = "input error";
		}else if(!file.exists()){
			file.mkdir();
			File pwdFile=new File(filePath+username+"/password.txt");
			pwdFile.createNewFile();
			fw = new FileWriter(pwdFile);
			fw.write(password);
			fw.flush();
			fw.close();
			result = "create successful!";
		}else if(file.exists()){
			result = "user exist!Please try again!";
		}
		setLabel(result);
	}
	
	public String setPath(){
		if( label.getText().equals("Login successful!") )
			return "D:/eclipse/ping/playerData/"+username;
		return null;
	}
}