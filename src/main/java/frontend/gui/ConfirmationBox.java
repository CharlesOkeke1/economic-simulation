package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmationBox {
    static boolean answer;
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label(message);

        //Create two buttons
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setOnAction(e -> {
            answer = true;
            AppMain.closeWindow();
            window.close();
        });

        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox horLayout = new HBox(5);
        horLayout.setAlignment(Pos.CENTER);
        horLayout.getChildren().addAll(yes, no);

        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(label, horLayout);

        Scene newScene = new Scene(mainLayout);
        window.setScene(newScene);
        window.showAndWait();

        return answer;

    }

    public static boolean restart(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label(message);

        //Create two buttons
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setOnAction(e -> {
            answer = true;
            window.close();
            AppMain.getInstance().restart();

        });

        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox horLayout = new HBox(5);
        horLayout.setAlignment(Pos.CENTER);
        horLayout.getChildren().addAll(yes, no);

        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(label, horLayout);

        Scene newScene = new Scene(mainLayout);
        window.setScene(newScene);
        window.showAndWait();

        return answer;

    }

    public static boolean switchView(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label(message);

        //Create two buttons
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setOnAction(e -> {
            answer = true;
            window.close();
            AppMain.getDisplayConfig().setDisplayState(AppMain.getConfig().getChosenState());
            if (AppMain.getConfig().getDevType()) { AppMain.resumeLoop(); }
        });

        no.setOnAction(e -> {
            answer = false;
            window.close();
            AppMain.switchRoot(SettingScreen.build());
        });

        HBox horLayout = new HBox(5);
        horLayout.setAlignment(Pos.CENTER);
        horLayout.getChildren().addAll(yes, no);

        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(label, horLayout);

        Scene newScene = new Scene(mainLayout);
        window.setScene(newScene);
        window.showAndWait();

        return answer;

    }




}
