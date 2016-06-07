package game.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NumberShow {
	String IMAGE_LOC;
	Image image;
	ImageView imageIV;
	int W = 1000;
	int H = 500;

	public NumberShow(char color, int picnumber) {
		if (color == 'r')
			IMAGE_LOC = "game/view/picture/Red (" + picnumber + ").png";
		else if (color == 'b')
			IMAGE_LOC = "game/view/picture/Blue (" + picnumber + ").png";
		image = new Image(IMAGE_LOC);
		imageIV = new ImageView(image);
		imageIV.setVisible(false);
	}

	public ImageView getImageViewType() {
		return imageIV;
	}

	public void setLayoutX(int x) {
		imageIV.setLayoutX(x);
	}

	public void setLayoutY(int y) {
		imageIV.setLayoutY(y);
	}

	public void setVisible(boolean bool) {
		imageIV.setVisible(bool);
	}

	public void setRedDigitalXY() {
		imageIV.setLayoutX(W / 4);
		imageIV.setLayoutY(H / 2 - image.getHeight() / 2);
	}

	public void setRedTenXY() {
		imageIV.setLayoutX(W / 4 - image.getWidth());
		imageIV.setLayoutY(H / 2 - image.getHeight() / 2);
	}

	public void setBlueDigitalXY() {
		imageIV.setLayoutX(W / 4 * 3);
		imageIV.setLayoutY((H / 2) - 5 - image.getHeight() / 2);
	}

	public void setBlueTenXY() {
		imageIV.setLayoutX(W / 4 * 3 - image.getWidth());
		imageIV.setLayoutY((H / 2) - 5 - image.getHeight() / 2);
	}
}
