package game.gui;

import game.config.GameConfig;
import game.config.GameDifficulty;
import game.gui.AppMain;
import game.gui.HomeScreen;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuScreen {
    private static String state = "Lagos";
    private static int numMonths = 12;
    private static boolean devType = false;
    private static GameDifficulty difficulty = GameDifficulty.MEDIUM;


    Stage window;

    private static final String[] nigerianStates = {
            "Abia","Abuja","Adamawa","Akwa Ibom","Anambra","Bauchi","Bayelsa","Benue",
            "Borno","Cross River","Delta","Ebonyi","Edo","Ekiti","Enugu",
            "Gombe","Imo","Jigawa","Kaduna","Kano","Katsina","Kebbi",
            "Kogi","Kwara","Lagos","Nasarawa","Niger","Ogun","Ondo","Osun",
            "Oyo","Plateau","Rivers","Sokoto","Taraba","Yobe","Zamfara"
    };

    public static Parent build() {
        AppMain.createTitle("Nigerian Economy Simulation");
        VBox menuCard = buildLayout();

        /* -------- Background Image -------- */
        ImageView bg = new ImageView(AppMain.class.getResource("/game/assets/menu_bg.png").toExternalForm());
        bg.setFitWidth(1600);
        bg.setFitHeight(900);
        bg.setEffect(new GaussianBlur(7));

        Pane tint = new Pane();
        tint.setStyle("-fx-background-color: rgba(0,0,0,0.5);");

        StackPane root = new StackPane(bg, tint, menuCard);
        StackPane.setAlignment(menuCard, Pos.CENTER_LEFT);
        StackPane.setMargin(menuCard, new Insets(100));

        return root;
    }

    private static VBox buildLayout() {
        Label title = new Label("Are you better than your Govenor? Take on their burden");
        title.setStyle(
                "-fx-font-size:20;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-family:Cambria;"
        );
        title.setTextAlignment(TextAlignment.CENTER);
        title.setAlignment(Pos.TOP_CENTER);
        title.setWrapText(true);
        title.setMaxWidth(300);


        /* -------- Difficulty -------- */
        ToggleGroup diffGroup = new ToggleGroup();

        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        RadioButton expert = new RadioButton("Expert");

        easy.setToggleGroup(diffGroup);
        medium.setToggleGroup(diffGroup);
        hard.setToggleGroup(diffGroup);
        expert.setToggleGroup(diffGroup);

        medium.setSelected(true);

        String radioStyle =
                "-fx-text-fill:white;" +
                        "-fx-font-size:13;";

        easy.setStyle(radioStyle);
        medium.setStyle(radioStyle);
        hard.setStyle(radioStyle);
        expert.setStyle(radioStyle);

        easy.setOnAction(e -> difficulty = GameDifficulty.EASY);
        medium.setOnAction(e -> difficulty = GameDifficulty.MEDIUM);
        hard.setOnAction(e -> difficulty = GameDifficulty.HARD);
        expert.setOnAction(e -> difficulty = GameDifficulty.EXPERT);

        Text diffLabel = new Text("Difficulty:");
        diffLabel.setStyle("-fx-font-weight: bold; -fx-fill:white; -fx-font-family: Cambria;");

        HBox difficultyRow = new HBox(10, diffLabel, easy, medium, hard, expert);
        difficultyRow.setAlignment(Pos.CENTER_LEFT);


        /* -------- State Selection -------- */
        ComboBox<String> stateDrop = new ComboBox<>();
        stateDrop.getItems().addAll(nigerianStates);
        stateDrop.setPromptText("Select state");

        /* Visible value styling */
        stateDrop.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("Select state");
                } else {
                    setText(item);
                    setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Cambria;");
                }

                setStyle("-fx-text-fill: white;");
            }
        });

        /* Dropdown list styling */
        stateDrop.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);

                }

                setStyle(
                        "-fx-text-fill: white;" +
                                "-fx-background-color: #1f2937;"
                );
            }
        });

        /* Box styling */
        stateDrop.setStyle(
                "-fx-background-color:#1f2937;" +
                        "-fx-border-color:#3b82f6;" +
                        "-fx-border-radius:6;" +
                        "-fx-background-radius:6;"
        );

        stateDrop.setOnAction(e -> {
            state = stateDrop.getValue();
        });

        Text stateLabel = new Text("State:");
        stateLabel.setStyle("-fx-fill:white;" + "-fx-font-family: Cambria;" + "-fx-font-weight:bold;");

        HBox stateRow = new HBox(10, stateLabel, stateDrop);
        stateRow.setAlignment(Pos.CENTER_LEFT);


        /* -------- Months -------- */
        TextField monthsField = new TextField("12");
        monthsField.setMaxWidth(80);

        monthsField.setStyle(
                "-fx-background-color:#1f2937;" +
                        "-fx-text-fill:white;" +
                        "-fx-border-color:#3b82f6;" +
                        "-fx-border-radius:6;" +
                        "-fx-background-radius:6;"
        );

        monthsField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                numMonths = Integer.parseInt(newVal);
            } catch (NumberFormatException ignored) {}
        });

        Text monthLabel = new Text("Months:");
        monthLabel.setStyle("-fx-fill:white;" + "-fx-font-family: Cambria;" + "-fx-font-weight:bold;");

        HBox monthRow = new HBox(10, monthLabel, monthsField);
        monthRow.setAlignment(Pos.CENTER_LEFT);


        /* -------- Dev Mode -------- */

        ToggleGroup devGroup = new ToggleGroup();

        RadioButton devYes = new RadioButton("Yes");
        RadioButton devNo = new RadioButton("No");

        devYes.setToggleGroup(devGroup);
        devNo.setToggleGroup(devGroup);

        devNo.setSelected(true);

        devYes.setStyle(radioStyle);
        devNo.setStyle(radioStyle);

        devYes.setOnAction(e -> devType = true);
        devNo.setOnAction(e -> devType = false);

        Text devLabel = new Text("Developer Mode:");
        devLabel.setStyle("-fx-fill:white;" + "-fx-font-family: Cambria;" + "-fx-font-weight:bold;");

        HBox devRow = new HBox(10, devLabel, devYes, devNo);
        devRow.setAlignment(Pos.CENTER_LEFT);



        /* -------- Start Button -------- */

        Button start = new Button("Start Game");
        start.setStyle(
                "-fx-background-color:#2563eb;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-weight:bold;" +
                        "-fx-font-family: Cambria;" +
                        "-fx-padding:8 22;" +
                        "-fx-font-size:15;" +
                        "-fx-background-radius:8;"
        );

        start.setOnAction(e -> {
            AppMain.getConfig().setDifficulty(difficulty);
            AppMain.getConfig().setTotalMonths(numMonths);
            AppMain.getConfig().setDevType(devType);
            AppMain.getConfig().setChosenState(state);

            System.out.println("Game starting with:");
            System.out.println("State: " + AppMain.getConfig().getChosenState());
            System.out.println("Months: " + AppMain.getConfig().getTotalMonths());
            System.out.println("Dev Mode: " + AppMain.getConfig().getDevType());
            System.out.println("Difficulty: " + AppMain.getConfig().getDifficulty());

            AppMain.switchRoot(HomeScreen.build());
            //HomeScreen.refreshUI(); A screen reset method should be made to reset the screen on every restart.
            AppMain.gameLoop();
        });


        /* -------- Card Layout -------- */

        VBox layout = new VBox(20,
                title,
                difficultyRow,
                stateRow,
                monthRow,
                devRow,
                start
        );

        layout.setPadding(new Insets(30));
        layout.setMaxWidth(420);
        layout.setMaxHeight(350);

        layout.setStyle(
                "-fx-background-color:#1f2937;" +
                        "-fx-background-radius:12;" +
                        "-fx-border-radius:12;" +
                        "-fx-border-color:#3b82f6;" +
                        "-fx-border-width:1;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 30, 0.3, 0, 6);"
        );

        layout.setAlignment(Pos.CENTER);

        return layout;
    }
}