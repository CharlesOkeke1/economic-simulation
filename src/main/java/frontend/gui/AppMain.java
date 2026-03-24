package gui;
import config.GameConfig;
import economies.StateEconomy;
import economies.FederalEconomy;
import election.ElectionEngine;
import engine.SimulationEngine;
import data.EconomyInitializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.util.*;

public class AppMain extends Application {
        public static Timeline gameLoop;
        private static AppMain instance;
        private static Scene scene;
        private static Stage window;
        public static GameConfig config = new GameConfig();
        private static HashMap<String, StateEconomy> states = EconomyInitializer.createStates();
        private static FederalEconomy fed = EconomyInitializer.createFederal();
        public static SimulationEngine engine = new SimulationEngine(states, fed, config);
        public static ElectionEngine election = new ElectionEngine();

        @Override
        public void start(Stage primaryStage) {
            instance = this;
            window = primaryStage;
            window.initStyle(StageStyle.DECORATED);
            scene = new Scene(MenuScreen.build(), 1600, 900);
            scene.getStylesheets().add(
                    AppMain.class.getResource("/game/gui/App.css").toExternalForm()
            );

            window.setTitle("Game");
            window.setScene(scene);
            window.show();
        }

        public static void tick() {
            engine.run();
            HomeScreen.refreshUI();
        }

        public static void gameLoop() {
            if (gameLoop != null) {
                gameLoop.stop();
            }

            gameLoop = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> tick())
            );

            gameLoop.setCycleCount(Timeline.INDEFINITE);

            if (config.getDevType()) gameLoop.play();
        }

        public void restart() {
            fed = EconomyInitializer.createFederal();
            config = new GameConfig();
            states = EconomyInitializer.createStates();
            engine = new SimulationEngine(states, fed, config);

            switchRoot(MenuScreen.build());
            HomeScreen.resetUI();
        }

        public static AppMain getInstance() { return instance; }

        public static void switchRoot(Parent layout) {
            scene.setRoot(layout);
        }

        public static void closeWindow() {
            window.close();
        }

        public static void createTitle(String title) {
            window.setTitle(title);
        }

        public static GameConfig getConfig() {
            return config;
        }

        public static FederalEconomy getFederal() {
            return fed;
        }

        public static SimulationEngine getSim() { return engine; }

        public static HashMap<String, StateEconomy> getStateMap() {
            return states;
        }

        public static int getCurrentMonth() {
            return engine.getCurrentMonth();
        }



    }