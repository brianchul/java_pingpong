package game;

import java.io.IOException;

import game.model.Audio;
import game.model.Ball;
import game.model.Labels;
import game.model.NumberShow;
import game.model.Paddle;
import game.model.Wall;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class gameMain extends Application {
	Stage primaryStage;

	AnimationTimer bound;

	Group root;

	Timeline ballAnimation;
	Timeline ballWaitAnimation;

	Audio AudioBGMPlay;
	Audio AudioBGMWait;
	Audio AudioReady;
	Audio AudioPressReady;
	Audio AudioHit;
	Audio AudioHitWall;
	Audio AudioP1;
	Audio AudioP2;
	Audio AudioFire;
	Audio AudioLogin = new Audio(1, "view/sound/SD_BGM_TITLE.m4a");
	Audio AudioDraw;

	Button startButton;
	Button showHelpButton;
	Button hideHelpButton;

	Ball ball;

	Paddle Bar1;
	Paddle Bar2;

	Wall topWall;
	Wall LeftWall;
	Wall RightWall;
	Wall bottomWall;
	Wall CenterWall;

	Labels speedOf;
	Labels helpTextlabel;
	private String labelColor = "#ffedfe";

	String p1name;
	String p2name;

	int p1 = 0, p2 = 0;
	int p1FBs, p2FBs;
	int H = 500, W = 1000;

	double dx = 1.0, dy = 1.0;
	boolean isFiring = false,
			isPlaying;

	static final String READY_IMAGE_LOC = "game/view/picture/RUready.png";
	Image readyImage;
	ImageView areReadyiv;
	static final String LOGO_IMAGE_LOC = "game/view/picture/logo_ok.png";
	Image logoImage;
	ImageView logoShow;
	static final String BACKGROUND_IMG_LOC = "game/view/picture/background_image.png";
	Image backGroundImage;
	Node background;
	static final String RED_BAR0_LOC = "game/view/picture/red_0.png";
	static final String RED_BAR1_LOC = "game/view/picture/red_1.png";
	static final String RED_BAR2_LOC = "game/view/picture/red_2.png";
	static final String RED_BAR3_LOC = "game/view/picture/red_full.png";
	static final String BLUE_BAR0_LOC = "game/view/picture/blue_0.png";
	static final String BLUE_BAR1_LOC = "game/view/picture/blue_1.png";
	static final String BLUE_BAR2_LOC = "game/view/picture/blue_2.png";
	static final String BLUE_BAR3_LOC = "game/view/picture/blue_full.png";
	ImageView red0barIV;
	ImageView red1barIV;
	ImageView red2barIV;
	ImageView red3barIV;
	ImageView blue0barIV;
	ImageView blue1barIV;
	ImageView blue2barIV;
	ImageView blue3barIV;
	static final String PLAYBACKGROUND_LOC = "game/view/picture/gm_bg_clear_eff_colette.png";
	ImageView playBackgroundIV;

	NumberShow red0;
	NumberShow red1;
	NumberShow red2;
	NumberShow red3;
	NumberShow red4;
	NumberShow red5;
	NumberShow red6;
	NumberShow red7;
	NumberShow red8;
	NumberShow red9;
	NumberShow red11;
	NumberShow red22;
	NumberShow red33;
	NumberShow red99;
	NumberShow blue0;
	NumberShow blue1;
	NumberShow blue2;
	NumberShow blue3;
	NumberShow blue4;
	NumberShow blue5;
	NumberShow blue6;
	NumberShow blue7;
	NumberShow blue8;
	NumberShow blue9;
	NumberShow blue11;
	NumberShow blue22;
	NumberShow blue33;
	NumberShow blue99;

	Image win;
	ImageView winner;

	FadeTransition ft;
	FadeTransition logo;
	FadeTransition playBackgnd;

	EventHandler<KeyEvent> onKeyPressedEventHandler;
	EventHandler<KeyEvent> onKeyReleasedEventHandler;

	public void loginView() {
		try {
			AudioLogin.play();
			BorderPane root;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(gameMain.class.getResource("view/loginView.fxml"));
			root = loader.load();

			Scene loginScene = new Scene(root, W, H);
			loginScene.getStylesheets().add(getClass().getResource("view/loginView.css").toExternalForm());
			primaryStage.setTitle("Ping Pong Game");
			primaryStage.setScene(loginScene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pingpong(Stage game_stage, String p1name, String p2name, String p1Path, String p2Path) {
		try {
			// 遊戲物件設置
			this.p1name = p1name;
			this.p2name = p2name;
			setupImageForNumber();
			setupBar();
			setupBall();
			setupWall();
			setupAudio();
			setupImage();
			setupWaitAnimation();
			setupPlayAnimation(p1Path, p2Path);
			setupBound(); // 設定邊界
			setupKeyboardEvent(); // 按鍵控制物件的事件
			setupButtons(); // 開始紐設置
			setupHelpLabel();
			AudioLogin.stop();
			AudioBGMWait.play();
			ballWaitAnimation.playFromStart();

			// 物件加入群組
			setupGroup();

			// 設置畫面
			Scene scene = new Scene(root, W, H);

			// 事件加入畫面
			scene.setOnKeyPressed(onKeyPressedEventHandler);
			scene.setOnKeyReleased(onKeyReleasedEventHandler);
			game_stage.setScene(scene);
			game_stage.show();
			bound.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setupGroup() {
		root = new Group(
				background,
				playBackgroundIV,
				logoShow,
				helpTextlabel.getLabelType(),
				topWall.getShape(),
				LeftWall.getShape(),
				RightWall.getShape(),
				bottomWall.getShape(),
				CenterWall.getShape(),
				areReadyiv,
				winner,
				red0barIV,
				red1barIV,
				red2barIV,
				red3barIV,
				blue0barIV,
				blue1barIV,
				blue2barIV,
				blue3barIV,
				red0.getImageViewType(),
				red1.getImageViewType(),
				red2.getImageViewType(),
				red3.getImageViewType(),
				red4.getImageViewType(),
				red5.getImageViewType(),
				red6.getImageViewType(),
				red7.getImageViewType(),
				red8.getImageViewType(),
				red9.getImageViewType(),
				red11.getImageViewType(),
				red22.getImageViewType(),
				red33.getImageViewType(),
				red99.getImageViewType(),
				blue0.getImageViewType(),
				blue1.getImageViewType(),
				blue2.getImageViewType(),
				blue3.getImageViewType(),
				blue4.getImageViewType(),
				blue5.getImageViewType(),
				blue6.getImageViewType(),
				blue7.getImageViewType(),
				blue8.getImageViewType(),
				blue9.getImageViewType(),
				blue11.getImageViewType(),
				blue22.getImageViewType(),
				blue33.getImageViewType(),
				blue99.getImageViewType(),
				Bar1.getShape(),
				Bar2.getShape(),
				ball.getShape(),
				startButton,
				hideHelpButton,
				showHelpButton);
	}

	public void setupAudio() {
		AudioBGMPlay = new Audio(AudioClip.INDEFINITE, "view/sound/SD_BGM_CUSTOM_QRISPY.m4a");
		AudioHit = new Audio(1, "view/sound/SD_SHOT_JAPAN_JUST.m4a");
		AudioHitWall = new Audio(1, "view/sound/SD_SHOT_TABLETENNIS_JUST.m4a");
		AudioPressReady = new Audio(1, "view/sound/SD_SE_CUSTOM_AUTUMN.m4a");
		AudioReady = new Audio(1, "view/sound/SD_CV_AREYOUREADY.m4a");
		AudioP1 = new Audio(1, "view/sound/SD_CV_REDWIN.m4a");
		AudioP2 = new Audio(1, "view/sound/SD_CV_BLUEWIN.m4a");
		AudioBGMWait = new Audio(AudioClip.INDEFINITE, "view/sound/SD_BGM_CUSTOM_AUTUMN.m4a");
		AudioFire = new Audio(1, "view/sound/SD_SHOT_ELECTRO2_JUST.m4a");
		AudioDraw = new Audio(1, "view/sound/SD_CV_DRAW.m4a");
	}

	public void setupImageForNumber() {
		red0 = new NumberShow('r', 0);
		red1 = new NumberShow('r', 1);
		red2 = new NumberShow('r', 2);
		red3 = new NumberShow('r', 3);
		red4 = new NumberShow('r', 4);
		red5 = new NumberShow('r', 5);
		red6 = new NumberShow('r', 6);
		red7 = new NumberShow('r', 7);
		red8 = new NumberShow('r', 8);
		red9 = new NumberShow('r', 9);
		red11 = new NumberShow('r', 11);
		red22 = new NumberShow('r', 22);
		red33 = new NumberShow('r', 33);
		red99 = new NumberShow('r', 99);
		blue0 = new NumberShow('b', 0);
		blue1 = new NumberShow('b', 1);
		blue2 = new NumberShow('b', 2);
		blue3 = new NumberShow('b', 3);
		blue4 = new NumberShow('b', 4);
		blue5 = new NumberShow('b', 5);
		blue6 = new NumberShow('b', 6);
		blue7 = new NumberShow('b', 7);
		blue8 = new NumberShow('b', 8);
		blue9 = new NumberShow('b', 9);
		blue11 = new NumberShow('b', 11);
		blue22 = new NumberShow('b', 22);
		blue33 = new NumberShow('b', 33);
		blue99 = new NumberShow('b', 99);
	}

	public void setupImage() {
		win = new Image("game/view/picture/win.png");
		winner = new ImageView(win);
		winner.setVisible(false);

		background = new ImageView(new Image(BACKGROUND_IMG_LOC));

		playBackgroundIV = new ImageView(new Image(PLAYBACKGROUND_LOC));
		playBackgroundIV.setVisible(false);

		areReadyiv = new ImageView(new Image(READY_IMAGE_LOC));
		areReadyiv.setLayoutX(249);
		areReadyiv.setLayoutY(H / 2 - 90);
		areReadyiv.setVisible(false);

		ft = new FadeTransition();
		ft.setNode(areReadyiv);
		ft.setDuration(new Duration(2100));
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.setCycleCount(1);
		ft.setAutoReverse(true);

		logoImage = new Image(LOGO_IMAGE_LOC);
		logoShow = new ImageView(logoImage);
		logoShow.setVisible(true);
		logo = new FadeTransition();
		logo.setNode(logoShow);
		logo.setAutoReverse(true);
		logoSetting(2000, 0.5, 1.0, 0);

		playBackgnd = new FadeTransition();
		playBackgnd.setNode(playBackgroundIV);

		double x = W / 4 - 100, y = H / 4 * 3 - 70;
		red0barIV = new ImageView(new Image(RED_BAR0_LOC));
		red0barIV.setLayoutX(x);
		red0barIV.setLayoutY(y);
		red1barIV = new ImageView(new Image(RED_BAR1_LOC));
		red1barIV.setLayoutX(x);
		red1barIV.setLayoutY(y);
		red2barIV = new ImageView(new Image(RED_BAR2_LOC));
		red2barIV.setLayoutX(x);
		red2barIV.setLayoutY(y);
		red3barIV = new ImageView(new Image(RED_BAR3_LOC));
		red3barIV.setLayoutX(x);
		red3barIV.setLayoutY(y);
		blue0barIV = new ImageView(new Image(BLUE_BAR0_LOC));
		blue0barIV.setLayoutX(x * 3 + 200);
		blue0barIV.setLayoutY(y);
		blue1barIV = new ImageView(new Image(BLUE_BAR1_LOC));
		blue1barIV.setLayoutX(x * 3 + 200);
		blue1barIV.setLayoutY(y);
		blue2barIV = new ImageView(new Image(BLUE_BAR2_LOC));
		blue2barIV.setLayoutX(x * 3 + 200);
		blue2barIV.setLayoutY(y);
		blue3barIV = new ImageView(new Image(BLUE_BAR3_LOC));
		blue3barIV.setLayoutX(x * 3 + 200);
		blue3barIV.setLayoutY(y);
		initilizeRedBar();
		initilizeBlueBar();
	}

	public void setupWall() {
		topWall = new Wall(0, 0, W, 10, Color.GOLD);
		LeftWall = new Wall(0, 0, 10, H, Color.ROYALBLUE);
		RightWall = new Wall(W - 10, 0, 10, H, Color.RED);
		bottomWall = new Wall(0, H - 10, W, 10, Color.BLUEVIOLET);
		CenterWall = new Wall((W / 2) - 3, 0, 5, H, Color.BLACK);
	}

	public void setupBall() {
		ball = new Ball(W / 2, H / 2, 10, Color.WHITE);
	}

	public void setupBar() {
		Bar1 = new Paddle(20, (H / 2) - 50, 10, 100, Color.RED);
		Bar2 = new Paddle(W - 30, (H / 2) - 50, 10, 100, Color.ROYALBLUE);
	}

	public void setupButtons() {
		startButton = new Button("Start!");
		startButton.setLayoutX((W / 2) - 35);
		startButton.setLayoutY(450);
		startButton.setOnAction(e -> {
			ballWaitAnimation.stop();
			ball.setCenterX(W / 2);
			ball.setCenterY(H / 2);
			isPlaying = true;
			logo.stop();
			logoSetting(5000, 0.7, 0.0, 1);
			playBackgndSetting(3000, 0.0, 0.5);
			playBackgnd.playFromStart();
			playBackgroundIV.setVisible(true);
			startButton.setVisible(false);
			hideHelpButton.setVisible(false);
			showHelpButton.setVisible(false);
			helpTextlabel.SetVisible(false);
			winner.setVisible(false);
			AudioBGMWait.stop();
			makePaddleReturn();
			AudioPressReady.play();
			initilizeScore();
			initilizeBar();
			Timeline timeline1 = new Timeline(new KeyFrame(
					Duration.millis(6000),
					ae1 -> {
						AudioReady.play();
						ballWaitAnimation.stop();
						ft.play();
						areReadyiv.setVisible(true);
						ShowRedScore(p1);
						ShowBlueScore(p2);
						setFBpower('r', p1FBs);
						setFBpower('b', p2FBs);
						Timeline timeline2 = new Timeline(new KeyFrame(
								Duration.millis(2000),
								ae2 -> {
									ballAnimation.playFromStart();
									AudioBGMPlay.play();
								}));
						timeline2.play();
					}));
			timeline1.play();
			startButton.setVisible(false);
		});

		showHelpButton = new Button("顯示遊戲說明");
		showHelpButton.setLayoutX((W / 2) - 40);
		showHelpButton.setLayoutY(400);
		showHelpButton.setOnAction(h -> {
			helpTextlabel.SetVisible(true);
			showHelpButton.setVisible(false);
			hideHelpButton.setVisible(true);
		});
		hideHelpButton = new Button("隱藏遊戲說明");
		hideHelpButton.setLayoutX((W / 2) - 40);
		hideHelpButton.setLayoutY(400);
		hideHelpButton.setOnAction(h -> {
			helpTextlabel.SetVisible(false);
			showHelpButton.setVisible(true);
			hideHelpButton.setVisible(false);
		});
	}

	public void setupWaitAnimation() {
		ballWaitAnimation = new Timeline(120, new KeyFrame(Duration.millis(10.0),
				t -> {
					isPlaying = false;
					ball.move(dx, dy);
					WaitingWallCollision(dx);
					BallAndWallCollision(dy);
					BallCollision();
				}));
		ballWaitAnimation.setCycleCount(Timeline.INDEFINITE);
	}

	public void setupPlayAnimation(String p1Path, String p2Path) {
		ballAnimation = new Timeline(120, new KeyFrame(Duration.millis(10.0),
				t -> {
					ball.move(dx, dy);
					BallAndWallCollision(dy);
					BallCollision();
					GameOver(p1Path, p2Path);
				}));
		ballAnimation.setCycleCount(Timeline.INDEFINITE);
	}

	public void setupKeyboardEvent() {
		onKeyPressedEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				Bar1.Pressed(event, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, KeyCode.SHIFT);
				Bar2.Pressed(event, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SHIFT);
			}
		};

		onKeyReleasedEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				Bar1.Released(event, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, KeyCode.SHIFT);
				Bar2.Released(event, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SHIFT);
			}
		};
	}

	public void setupBound() {
		bound = new AnimationTimer() {
			@Override
			public void handle(long now) {
				Bounds topB = topWall.getBounds();
				Bounds bottomB = bottomWall.getBounds();
				Bounds LeftB = LeftWall.getBounds();
				Bounds RightB = RightWall.getBounds();
				Bounds CenterB = CenterWall.getBounds();
				Bar1.boundary(Bar1, LeftB.getMaxX(), CenterB.getMinX() - Bar1.getWidth(), topB.getMaxY(), bottomB.getMinY() - Bar1.getHeight());
				Bar2.boundary(Bar2, CenterB.getMaxX(), RightB.getMinX() - Bar2.getWidth(), topB.getMaxY(), bottomB.getMinY() - Bar2.getHeight());
			}
		};
	}

	public void setupHelpLabel() {
		helpTextlabel = new Labels(helpText(), W / 2, H / 3);
		helpTextlabel.SetVisible(false);
		helpTextlabel.TextFill(Color.web(labelColor));
	}

	public void ShowBlueScore(int x) {
		initilizeBlueScore();
		int tenth, digi;
		digi = x % 10;
		tenth = x / 10;
		char color = 'b';
		// System.out.println("color = " + color + " tenth = " + tenth + " digi
		// = " + digi);

		// returnTheNumber(color, digi).setRedDigitalXY();
		// returnTheNumber(color, tenth).setRedTenXY();

		returnTheNumber(color, digi).setBlueDigitalXY();
		returnTheNumber(color, tenth).setBlueTenXY();

		returnTheNumber(color, digi).setVisible(true);
		returnTheNumber(color, tenth).setVisible(true);
		if (x == 0) {
			returnTheNumber(color, 99).setBlueDigitalXY();
			returnTheNumber(color, 99).setVisible(true);
		} else if (digi == tenth) {
			returnTheNumber(color, digi + digi * 10).setBlueDigitalXY();
			returnTheNumber(color, digi + digi * 10).setVisible(true);
		}

		/*
		 * if (x > 0) { if (x % 10 == 0) returnTheNumber(color,
		 * 9).setVisible(false); returnTheNumber(color, (digi) -
		 * 1).setVisible(false); //returnTheNumber(color, (tenth) -
		 * 1).setVisible(false); }
		 */
	}

	public void ShowRedScore(int x) {
		initilizeRedScore();
		int tenth, digi;
		digi = x % 10;
		tenth = x / 10;
		char color = 'r';
		returnTheNumber(color, digi).setRedDigitalXY();
		returnTheNumber(color, tenth).setRedTenXY();

		returnTheNumber(color, digi).setVisible(true);
		returnTheNumber(color, tenth).setVisible(true);
		if (x == 0) {
			returnTheNumber(color, 99).setRedDigitalXY();
			returnTheNumber(color, 99).setVisible(true);
		} else if (digi == tenth) {
			returnTheNumber(color, digi + digi * 10).setRedDigitalXY();
			returnTheNumber(color, digi + digi * 10).setVisible(true);
		}

		/*
		 * if (x > 0) { if (x % 10 == 0) returnTheNumber(color,
		 * 9).setVisible(false); returnTheNumber(color, (digi) -
		 * 1).setVisible(false); //returnTheNumber(color, (tenth) -
		 * 1).setVisible(false); }
		 */
	}

	public void initilizeScore() {
		initilizeBlueScore();
		initilizeRedScore();
	}

	public void initilizeBlueScore() {
		blue0.setVisible(false);
		blue1.setVisible(false);
		blue2.setVisible(false);
		blue3.setVisible(false);
		blue4.setVisible(false);
		blue5.setVisible(false);
		blue6.setVisible(false);
		blue7.setVisible(false);
		blue8.setVisible(false);
		blue9.setVisible(false);
		blue11.setVisible(false);
		blue22.setVisible(false);
		blue33.setVisible(false);
		blue99.setVisible(false);
	}

	public void initilizeRedScore() {
		red0.setVisible(false);
		red1.setVisible(false);
		red2.setVisible(false);
		red3.setVisible(false);
		red4.setVisible(false);
		red5.setVisible(false);
		red6.setVisible(false);
		red7.setVisible(false);
		red8.setVisible(false);
		red9.setVisible(false);
		red11.setVisible(false);
		red22.setVisible(false);
		red33.setVisible(false);
		red99.setVisible(false);
	}

	public void initilizeBar() {
		initilizeBlueBar();
		initilizeRedBar();
	}
	
	public void initilizeBlueBar() {
		blue0barIV.setVisible(false);
		blue1barIV.setVisible(false);
		blue2barIV.setVisible(false);
		blue3barIV.setVisible(false);
	}

	public void initilizeRedBar() {
		red0barIV.setVisible(false);
		red1barIV.setVisible(false);
		red2barIV.setVisible(false);
		red3barIV.setVisible(false);
	}

	public NumberShow returnTheNumber(char color, int x) {
		if (color == 'r') {
			switch (x) {
			case 0:
				return red0;
			case 1:
				return red1;
			case 2:
				return red2;
			case 3:
				return red3;
			case 4:
				return red4;
			case 5:
				return red5;
			case 6:
				return red6;
			case 7:
				return red7;
			case 8:
				return red8;
			case 9:
				return red9;
			case 11:
				return red11;
			case 22:
				return red22;
			case 33:
				return red33;
			case 99:
				return red99;
			default:
				return red0;
			}
		} else if (color == 'b') {
			switch (x) {
			case 0:
				return blue0;
			case 1:
				return blue1;
			case 2:
				return blue2;
			case 3:
				return blue3;
			case 4:
				return blue4;
			case 5:
				return blue5;
			case 6:
				return blue6;
			case 7:
				return blue7;
			case 8:
				return blue8;
			case 9:
				return blue9;
			case 11:
				return blue11;
			case 22:
				return blue22;
			case 33:
				return blue33;
			case 99:
				return blue99;
			default:
				return blue0;
			}
		} else {
			return red0;
		}
	}

	public String helpText() {
		return "123123\n" +
				"1232343465\n";
	}

	// 球與牆的碰撞

	public void BallAndWallCollision(double YSpeed) {
		if (ball.isCollision(ball, topWall) && ball.isMovingUp()) {
			ball.moveDown(YSpeed);
			AudioHitWall.play();
		}

		if (ball.isCollision(ball, bottomWall) && ball.isMovingDown()) {
			ball.moveUp(YSpeed);
			AudioHitWall.play();
		}

	}

	public void WaitingWallCollision(double XSpeed) {
		if (ball.isCollision(ball, LeftWall) && ball.isMovingLeft()) {
			ball.moveRight(XSpeed);
			AudioHitWall.play();
		}

		if (ball.isCollision(ball, RightWall) && ball.isMovingRight()) {
			ball.moveLeft(XSpeed);
			AudioHitWall.play();
		}
	}

	// 球與桿子的碰撞

	public void BallCollision() {
		if (ball.isCollision(ball, Bar1) && ball.isMovingLeft()) {
			if (isPlaying) {
				p1++;
				if (isFiring) {
					isFiring = false;
					dx -= 2;
				}
				if (p1FBs == 3) {
					if (Bar1.p1fire) {
						p1 += 2;
						dx += 2;
						isFiring = true;
						Bar1.p1fire = false;
						p1FBs = 0;
						setFBpower('r', p1FBs);
						AudioFire.play();
					}
				} else {
					dx *= 1.15;
					if (p1FBs <= 3) {
						p1FBs++;
						setFBpower('r', p1FBs);
					}
				}
				updateScore();
			}
			ball.moveRight(dx);
			AudioHit.play();
		}
		if (ball.isCollision(ball, Bar2) && ball.isMovingRight()) {
			if (isPlaying) {
				p2++;
				if (isFiring) {
					isFiring = false;
					dx -= 2;
				}
				if (p2FBs == 3) {
					if (Bar2.p2fire) {
						dx += 2;
						p2 += 2;
						isFiring = true;
						Bar2.p2fire = false;
						p2FBs = 0;
						setFBpower('b', p2FBs);
						AudioFire.play();
					}
				} else {
					dx *= 1.15;
					if (p2FBs <= 3) {
						p2FBs++;
						setFBpower('b', p2FBs);
					}
				}
				updateScore();
			}
			ball.moveLeft(dx);
			AudioHit.play();
		}
	}

	private void logoSetting(int duration, double from, double to, int cyclecount) {
		logo.setDuration(new Duration(duration));
		logo.setFromValue(from);
		logo.setToValue(to);
		if (cyclecount == 0)
			logo.setCycleCount(FadeTransition.INDEFINITE);
		else
			logo.setCycleCount(cyclecount);
		logo.play();
	}

	private void playBackgndSetting(int duration, double from, double to) {
		playBackgnd.setDuration(new Duration(duration));
		playBackgnd.setFromValue(from);
		playBackgnd.setToValue(to);
		playBackgnd.setCycleCount(1);
	}

	private void setFBpower(char color, int x) {
		if (color == 'r') {
			initilizeRedBar();
			if (x == 0)
				red0barIV.setVisible(true);
			else if (x == 1)
				red1barIV.setVisible(true);
			else if (x == 2)
				red2barIV.setVisible(true);
			else if (x == 3)
				red3barIV.setVisible(true);
		} else {
			initilizeBlueBar();
			if (x == 0)
				blue0barIV.setVisible(true);
			else if (x == 1)
				blue1barIV.setVisible(true);
			else if (x == 2)
				blue2barIV.setVisible(true);
			else if (x == 3)
				blue3barIV.setVisible(true);
		}
	}

	// 遊戲結束

	private void GameOver(String p1Path, String p2Path) {
		if (ball.isCollision(ball, RightWall) && ball.isMovingRight() ||
				ball.isCollision(ball, LeftWall) && ball.isMovingLeft()) {
			if (ball.isCollision(ball, RightWall) && ball.isMovingRight()) {
				p1 += 5;
				ShowRedScore(p1);
			} else {
				p2 += 5;
				ShowBlueScore(p2);
			}
			if (p1 > p2) {
				winner.setLayoutX(W / 4 - win.getWidth() - 50);
				winner.setLayoutY(H / 2 - 50 - win.getWidth() / 2);
				winner.setVisible(true);
				AudioP1.play();
			} else if (p1 < p2) {
				winner.setLayoutX(W / 4 * 3 - win.getWidth() - 50);
				winner.setLayoutY(H / 2 - 50 - win.getHeight() / 2);
				winner.setVisible(true);
				AudioP2.play();
			} else
				AudioDraw.play();

				setFBpower('r', 0);
			setFBpower('b', 0);
			p1FBs = 0;
			p2FBs = 0;
			dx = 1;
			p1 = 0;
			p2 = 0;
			ballAnimation.stop();
			startButton.setVisible(true);
			logoShow.setVisible(true);
			ball.setCenterX(W / 2);
			ball.setCenterY(H / 2);
			AudioBGMPlay.stop();
			AudioBGMWait.play();
			makePaddleReturn();
			ballWaitAnimation.play();
			logoSetting(2000, 0.5, 1.0, 0);
			playBackgndSetting(1000, 0.5, 0.0);
			playBackgnd.playFromStart();
			// updateScoreAndSpeed();
		}
	}

	private void updateScore() {
		ShowRedScore(p1);
		ShowBlueScore(p2);
	}

	private void makePaddleReturn() {
		Bar1.move(20, (H / 2) - 50);
		Bar2.move(W - 30, (H / 2) - 50);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loginView();
		// pingpong(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

}