package gui;

import config.GameDifficulty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SettingScreen {
        private static String state = "Lagos";

        public static Parent build() {
            AppMain.createTitle("Settings");
            return buildLayout();
        }

        private static VBox buildLayout() {
            Label title = new Label("Settings");
            title.setStyle(
                    "-fx-font-size:20;" +
                            "-fx-font-weight:bold;" +
                            "-fx-text-fill:white;" +
                            "-fx-font-family:Cambria;"
            );
            title.setTextAlignment(TextAlignment.CENTER);
            title.setAlignment(Pos.CENTER);
            title.setWrapText(true);
            title.setMaxWidth(400);

            /* -------- State Selection -------- */
            ComboBox<String> stateDrop = new ComboBox<>();
            stateDrop.getItems().addAll(AppMain.getAllStates());
            stateDrop.setPromptText("Select state");

            /* Visible value styling */
            stateDrop.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Cambria;");
                    if (empty || item == null) {
                        setText(AppMain.getConfig().getChosenState());
                    } else {
                        setText(item);
                    }
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
                            "-fx-border-color:white;" +
                            "-fx-border-radius:6;" +
                            "-fx-background-radius:6;"
            );

            stateDrop.setOnAction(e -> state = stateDrop.getValue());

            Text stateLabel = new Text("State:");
            stateLabel.setStyle(
                    "-fx-fill:white;" +
                            "-fx-font-family: Cambria;" +
                            "-fx-font-weight:bold;"
            );

            HBox stateRow = new HBox(10, stateLabel, stateDrop);
            stateRow.setAlignment(Pos.CENTER);
            stateRow.setMaxWidth(400);

            /* -------- Confirm Button -------- */
            Button start = new Button("View Changes");
            start.getStyleClass().add("card");

            start.setStyle(
                            "-fx-text-fill:white;" +
                            "-fx-font-weight:bold;" +
                            "-fx-font-family: Cambria;" +
                            "-fx-font-size:15;" +
                            "-fx-background-radius:8;"
            );



            start.setOnAction(e -> {
                AppMain.getDisplayConfig().setDisplayState(state);
                System.out.println("Switching from Players state: " +
                        AppMain.getConfig().getChosenState() +
                        " to view another state: " +
                        AppMain.getDisplayConfig().getDisplayState());
                AppMain.switchRoot(HomeScreen.build());
                ConfirmationBox.switchView("View State Switch", "Are you done viewing " + AppMain.getDisplayConfig().getDisplayState());
            });

            /* -------- Card Layout -------- */
            VBox layout = new VBox(25, title, stateRow, start);
            layout.setPadding(new Insets(25));
            layout.setMaxWidth(420);
            layout.setMaxHeight(350);
            layout.getStyleClass().add("card");
            layout.setAlignment(Pos.TOP_CENTER);

            /* Center layout on screen size ~600x450 */
            VBox container = new VBox(layout);
            container.setAlignment(Pos.CENTER);
            container.setPrefSize(800, 600);
            container.setStyle("-fx-background-color: #111827;"); // optional background behind card

            return container;
        }
    }