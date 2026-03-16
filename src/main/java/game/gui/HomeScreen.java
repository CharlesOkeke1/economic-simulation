package game.gui;

import com.sun.scenario.Settings;
import game.config.GameConfig;
import game.config.GameDifficulty;
import game.economies.StateEconomy;
import game.gui.ConfirmationBox;
import game.gui.MenuScreen;
import utils.EnumToString;
import game.events.EventTrigger;
import utils.MyUtils;
import java.util.Map;
import game.election.ElectionEngine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class HomeScreen {
    public static XYChart.Series<Number, Number> gdpSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> gdpChart = createChart("Month", "GDP (₦ Millions)", "Real GDP Growth", gdpSeries);


    public static XYChart.Series<Number, Number> inflationSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> inflaChart = createChart("Month", "Inflation", "Inflation Growth Rate", inflationSeries);

    public static XYChart.Series<Number, Number> popSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> popChart = createChart("Month", "Reserve (Millions)", "Reserve Growth Rate", popSeries);

    public static XYChart.Series<Number, Number> cashSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> cashChart = createChart("Month", "Cash (Millions)", "Cash Growth Rate", cashSeries);

    public static String electionMessage = "";
    public static String[] policyList = {
            "Raise Taxes",
            "Cut Taxes",
            "Invest Infrastructure",
            "Invest Education",
            "Boost Security",
            "Subsidise Transport & Food",
            "Market Trader Support",
            "Borrow",
            "Austerity"
    };

    /* ---- UI VALUE REFERENCES ---- */
    public static Label populationVal;
    public static Label realGdpVal;
    public static Label gdpVal;
    public static Label cashVal;
    public static Label debtVal;
    public static Label debtPymtVal;
    public static Label inflationVal;
    public static Label reserveVal;
    public static Label monthVal;
    public static Label policyVal;
    public static Label rankVal;
    public static Label taxVal;
    public static Label debtToGdpVal;
    public static Label growthVal;
    public static Label stabilityVal;
    public static Label infraVal;
    public static double completeness;
    public static Label compVal;
    public static Label profitVal;
    public static Label scoreVal;
    public static Label eventDispVal;
    public static Label eventTypeVal;
    public static Label crashVal;
    public static Label electionVal;
    private static Label revenueVal;
    private static Label expensesVal;
    private static Label fedAllocVal;


    public static Parent build() {
        //VBox root = new VBox();
        BorderPane root = buildLayout();
        return root;
    }

    private static BorderPane buildLayout() {
        Map<String, StateEconomy> states = AppMain.getStateMap();
        StateEconomy playerState = states.get(AppMain.getConfig().getChosenState());
        int currMonth = AppMain.getSim().getCurrentMonth();
        BorderPane root = new BorderPane();

        completeness = (double) currMonth/AppMain.getConfig().getTotalMonths();

        /*TOP BAR*/
        VBox stateBox = createCard("State", AppMain.getConfig().getChosenState());
        VBox diffBox = createCard("Difficulty",
                EnumToString.convert(AppMain.getConfig().getDifficulty(), "_"));
        VBox monthBox = createCard("Current Month", AppMain.getSim().getCurrentMonth() + "");
        monthVal = (Label) monthBox.getChildren().get(1);

        VBox policyBox = createCard("Current Policy", playerState.getPolicy());
        policyBox.setPrefWidth(200);
        policyVal = (Label) policyBox.getChildren().get(1);

        VBox gov = createCard("Government Type", EnumToString.convert(playerState.governmentType, "_"));
        gov.setPrefWidth(180);
        VBox crash = createCard("Economic Crashes", playerState.crashCount + "");
        crashVal = (Label) crash.getChildren().get(1);

        VBox comp = createCard("Game Progress", String.format("%.2f", (completeness * 100)) + "%");
        compVal = (Label) comp.getChildren().get(1);

        /* - COMING SOON
        VBox settings = createCard("Settings", "Settings");
        settings.setPadding(new Insets(0,0,0,10));
        */

        VBox restart = createCard("Restart", "Restart");
        restart.setOnMouseClicked(e -> ConfirmationBox.restart("Restart?", "Are you sure you want to restart?"));


        Button exit = new Button("Exit");
        exit.getStyleClass().add("card");
        exit.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: white;" + "-fx-font-size: 18;");
        exit.setOnAction(e -> {
            e.consume();
            ConfirmationBox.display("Leave?", "Are you sure you want to leave?");
        });

        HBox top = new HBox(15, stateBox, diffBox, monthBox, policyBox, gov, crash, comp, exit, restart);
        top.setAlignment(Pos.TOP_LEFT);

        //settings.setOnMouseClicked(e -> AppMain.switchRoot(MenuScreen.build()));
        HBox topBar = new HBox(top);
        topBar.getStyleClass().add("top-bar");




        /*BOTTOM BAR*/
        VBox stateBoxx = createCard("State", game.gui.AppMain.getConfig().getChosenState());
        VBox diffBoxx = createCard("Difficulty",
                EnumToString.convert(AppMain.getConfig().getDifficulty(), "_"));
        VBox monthBoxx = createCard("Current Month",  AppMain.getSim().getCurrentMonth() + "");
        VBox policyBoxx = createCard("Policy", "Raise Taxes");
        HBox bottomBar = new HBox(stateBoxx, diffBoxx, monthBoxx, policyBoxx);
        bottomBar.getStyleClass().add("top-bar");
        bottomBar.setAlignment(Pos.BOTTOM_CENTER);

        HBox bottomWrapper = new HBox(bottomBar);
        bottomWrapper.setAlignment(Pos.CENTER);
        bottomBar.setPrefWidth(600);
        BorderPane.setMargin(bottomWrapper, new Insets(0,0,0,0));




        /*LEFT BAR*/
        /*LEFT BAR*/
        HBox stats = createCardTitle("State Stats");

        VBox population = createCard("Population", MyUtils.formatNumber(playerState.population));
        populationVal = (Label) population.getChildren().get(1);

        VBox realGdp = createCard("Real GDP", "₦" + MyUtils.formatNumber(playerState.realGdp));
        realGdpVal = (Label) realGdp.getChildren().get(1);

        VBox gdp = createCard("Nominal GDP", "₦" + MyUtils.formatNumber(playerState.gdp));
        gdpVal = (Label) gdp.getChildren().get(1);

        VBox cash = createCard("Operating Cash", "₦" + MyUtils.formatNumber(playerState.cash));
        cashVal = (Label) cash.getChildren().get(1);

        VBox revenue = createCard("Monthly Revenue", "₦" + MyUtils.formatNumber(playerState.monthlyRevenue));
        revenueVal = (Label) revenue.getChildren().get(1);

        VBox expenses = createCard("Monthly Expenses", "₦" + MyUtils.formatNumber(playerState.monthlySpend));
        expensesVal = (Label) expenses.getChildren().get(1);

        VBox fedAlloc = createCard("Federal Allocation", "₦" + MyUtils.formatNumber(playerState.federalAllocation));
        fedAllocVal = (Label) fedAlloc.getChildren().get(1);

        VBox debt = createCard("Current Debt", "₦" + MyUtils.formatNumber(playerState.debt));
        debtVal = (Label) debt.getChildren().get(1);

        VBox debtPymt = createCard("Debt Payment", "₦" + MyUtils.formatNumber(playerState.debtPayment));
        debtPymtVal = (Label) debtPymt.getChildren().get(1);

        VBox reserve = createCard("State Reserve", "₦" + MyUtils.formatNumber(playerState.stateReserve));
        reserveVal = (Label) reserve.getChildren().get(1);

        VBox profit = createCard("Monthly Profit", "₦" + MyUtils.formatNumber(playerState.monthlyProfit));
        profitVal = (Label) profit.getChildren().get(1);

        /*Content section (mirrors rightBar)*/
        VBox leftBar = new VBox(
                10,
                population,
                realGdp,
                gdp,
                cash,
                revenue,
                expenses,
                fedAlloc,
                debt,
                debtPymt,
                reserve,
                profit
        );

        leftBar.setStyle("-fx-background-color: #1f2937;");
        leftBar.setAlignment(Pos.TOP_LEFT);

        /*Scrollable container (mirrors right side)*/
        ScrollPane scrollLeft = new ScrollPane(leftBar);
        scrollLeft.setFitToWidth(true);
        scrollLeft.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        /*Same height as right side*/
        scrollLeft.setPrefHeight(600);

        /*Same padding as right side*/
        scrollLeft.setPadding(new Insets(15,10,0,10));
        scrollLeft.getStyleClass().add("left-bar");

        /*Final wrapper identical to right bar structure*/
        VBox leftSection = new VBox(10, stats, scrollLeft);

        BorderPane.setMargin(leftSection, new Insets(15));

        /*RIGHT BAR*/
        HBox statss = createCardTitle("Recent Events");
        VBox populations = createNotification(
                EventTrigger.getEventType(),
                EventTrigger.getEventDisp()
        );

        HBox header = (HBox) populations.getChildren().get(0);
        eventTypeVal = (Label) header.getChildren().get(0);

        eventDispVal = (Label) populations.getChildren().get(1);

        if (AppMain.getSim().isElection()) {
            electionMessage = "This election saw a sweeping victory. The cost on the government was ₦" + MyUtils.formatNumber(AppMain.mainELectionCost()) + " and this was deducted from the state reserve";
        } else {
            electionMessage = "There is no election this month";
        }

        VBox realGdps = createNotification(
                "Election Review",
                electionMessage
        );
        electionVal = (Label) realGdps.getChildren().get(1);

        VBox gdps = createNotification(
                "Coming Soon!",
                "Monthly events and notifications coming soon"
        );

        VBox inflations = createNotification(
                "Coming Soon!",
                "Monthly events and notifications coming soon"
        );

        VBox rightBar = new VBox(10, populations, realGdps, gdps, inflations);
        rightBar.setStyle("-fx-background-color: #1f2937;");
        rightBar.setAlignment(Pos.TOP_RIGHT);

        ScrollPane scroll = new ScrollPane(rightBar);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scroll.setPrefHeight(600);
        scroll.setPadding(new Insets(15,10,0,10));
        scroll.getStyleClass().add("left-bar");
        VBox sb = new VBox(10, statss, scroll);
        BorderPane.setMargin(sb, new Insets(15));




        /* CENTER */
        gdpSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.realGdp/1_000_000)
        );
        gdpSeries.getNode().setStyle("-fx-stroke: #22c55e; -fx-stroke-width: 2px;");

        inflationSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.inflationRate * 100)
        );
        inflationSeries.getNode().setStyle("-fx-stroke: #ef4444; -fx-stroke-width: 2px;");
        popSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.stateReserve/1_000_000)
        );
        popSeries.getNode().setStyle("-fx-stroke: #3b82f6; -fx-stroke-width: 2px;");

        cashSeries.getData().add ( new XYChart.Data<>(currMonth, playerState.cash/1_000_000));
        cashSeries.getNode().setStyle("-fx-stroke: #3b82f6; -fx-stroke-width: 2px;");

        VBox rank = createLargeCard("Position", MyUtils.Ordinalize(playerState.position));
        rankVal = (Label) rank.getChildren().get(1);

        VBox tax = createLargeCard("Tax Rate", String.format("%.2f", playerState.taxRate * 100) + "%");
        taxVal = (Label) tax.getChildren().get(1);

        VBox debtToGdp = createLargeCard("Debt To Gdp", (playerState.debtToGdpRatio * 100) + "%");
        debtToGdpVal = (Label) debtToGdp.getChildren().get(1);

        VBox growth = createLargeCard("Growth Rate", (playerState.gdpGrowth * 100) + "%");
        growthVal = (Label) growth.getChildren().get(1);

        VBox stability = createLargeCard("Stability", String.format("%.2f", playerState.stability));
        stabilityVal = (Label) stability.getChildren().get(1);

        VBox infra = createLargeCard("Infrastructure", playerState.infrastructure + "");
        infraVal = (Label) infra.getChildren().get(1);

        VBox score = createLargeCard("Ranking Score", String.format("%.2f", playerState.rankingScore));
        scoreVal = (Label) score.getChildren().get(1);

        VBox inflation = createCard("Inflation Rate", String.format("%.2f", (playerState.inflationRate * 100)) + "%");
        inflationVal = (Label) inflation.getChildren().get(1);

        HBox center = new HBox(15, rank, score, tax, growth, stability, infra, debtToGdp, inflation);
        center.setPadding( new Insets(0,0,0,15));
        HBox chartLayer = new HBox(25, gdpChart, inflaChart, popChart);
        HBox center_bottom = new HBox(10, cashChart);
        center_bottom.setAlignment(Pos.CENTER);

        gdpChart.setCreateSymbols(false);
        inflaChart.setCreateSymbols(false);
        popChart.setCreateSymbols(false);
        cashChart.setCreateSymbols(false);



        chartLayer.setAlignment(Pos.CENTER);
        gdpChart.setAnimated(false);
        inflaChart.setAnimated(false);
        popChart.setAnimated(false);
        cashChart.setAnimated(false);

        ComboBox<String> policies = new ComboBox<>();
        policies.getItems().addAll(policyList);
        policies.setPromptText("Select Policy");

        /* Visible value styling */
        policies.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("Select Policy");
                } else {
                    setText(item);
                    setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Cambria;");
                }

                setStyle("-fx-text-fill: white;");
            }
        });

        /* Dropdown list styling */
        policies.setCellFactory(list -> new ListCell<>() {
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
        policies.setStyle(
                "-fx-background-color:#1f2937;" +
                        "-fx-border-color:#3b82f6;" +
                        "-fx-border-radius:6;" +
                        "-fx-background-radius:6;"
        );

        Button policyConfirm = new Button("Confirm");
        if (!AppMain.getConfig().getDevType()) {
            policyConfirm.setOnAction(e -> {
                playerState.setPolicy(policies.getValue());
                AppMain.tick();
            });
        }

        HBox policy = new HBox(10, policies, policyConfirm);
        center.setAlignment(Pos.CENTER);

        VBox chartPane = new VBox(30, chartLayer, center, policy, center_bottom);
        chartPane.setPadding(new Insets(50, 50, 0, 50));
        chartPane.setAlignment(Pos.TOP_CENTER);

        chartPane.getStyleClass().add("chart-pane");
        chartPane.setMaxSize(1075, 650);

        root.setTop(topBar);
        root.setLeft(leftSection);
        root.setRight(sb);
        root.setCenter(chartPane);
        //root.setBottom(bottomWrapper);
        root.setPadding(new Insets(15,40,70,40));

        return root;
    }

    private static VBox createCard(String title, String value) {

        Label t = new Label(title);
        t.getStyleClass().add("card-title");

        Label v = new Label(value);
        v.getStyleClass().add("card-value");

        VBox box = new VBox(t, v);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(125);
        //box.setPrefHeight(30);

        box.getStyleClass().add("card");

        return box;
    }

    private static VBox createLargeCard(String title, String value) {

        Label t = new Label(title + ":  ");
        t.getStyleClass().add("large-card-title");

        Label v = new Label(value);
        v.getStyleClass().add("card-value");

        VBox box = new VBox(t, v);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(200);
        box.setMaxHeight(75);

        box.getStyleClass().add("card");

        return box;
    }

    private static HBox createCardTitle(String title) {
        HBox box = new HBox(0);
        Label lab = new Label(title);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(125);
        box.getStyleClass().add("card");
        box.getChildren().add(lab);
        lab.getStyleClass().add("card-value");
        lab.setStyle("-fx-font-size: 20;");

        return box;
    }

    private static VBox createNotification(String title, String message) {

        Label t = new Label(title);
        t.getStyleClass().add("notif-title");
        t.setTextAlignment(TextAlignment.CENTER);

        HBox b = new HBox(10, t);
        b.setAlignment(Pos.TOP_CENTER);


        Label v = new Label(message);
        v.getStyleClass().add("notif-value");
        v.setWrapText(true);
        v.setMaxWidth(180);

        VBox box = new VBox(b, v);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(200);
        box.setPrefHeight(200);

        box.getStyleClass().add("card");

        return box;
    }

    private static LineChart<Number, Number> createChart(String xLabel, String yLabel, String title,
                                                         XYChart.Series<Number, Number> dataSeries)
    {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setAutoRanging(false);

        NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);

        chart.getData().add(dataSeries);

        chart.setPrefSize(360,245);

        chart.setTitle(title);
        chart.getStyleClass().add("chart");

        return chart;
    }

    private static void updateXAxis(LineChart<Number, Number> chart, int month, int window) {

        NumberAxis xAxis = (NumberAxis) chart.getXAxis();

        int lower = Math.max(0, month - window);
        int upper = Math.max(window, month);

        xAxis.setLowerBound(lower);
        xAxis.setUpperBound(upper);
        xAxis.setTickUnit(2);
    }

    public static void refreshUI() {
        Map<String, StateEconomy> states = AppMain.getStateMap();
        StateEconomy playerState = states.get(AppMain.getConfig().getChosenState());
        int currMonth = AppMain.getSim().getCurrentMonth();
        completeness = (double) currMonth/AppMain.getConfig().getTotalMonths();

        int window = 20;

        updateXAxis(gdpChart, currMonth, window);
        updateXAxis(inflaChart, currMonth, window);
        updateXAxis(popChart, currMonth, window);
        updateXAxis(cashChart, currMonth, window);

        gdpSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.realGdp/1_000_000)
        );
        gdpSeries.getNode().setStyle("-fx-stroke: #22c55e; -fx-stroke-width: 2px;");
        inflationSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.inflationRate * 100)
        );
        inflationSeries.getNode().setStyle("-fx-stroke: #ef4444; -fx-stroke-width: 2px;");
        popSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.stateReserve/1_000_000)
        );
        popSeries.getNode().setStyle("-fx-stroke: #3b82f6; -fx-stroke-width: 2px;");

        cashSeries.getData().add ( new XYChart.Data<>(currMonth, playerState.cash/1_000_000));
        cashSeries.getNode().setStyle("-fx-stroke: #3b82f6; -fx-stroke-width: 2px;");


        monthVal.setText(currMonth + "");
        compVal.setText(String.format("%.2f", (completeness * 100)) + "%");
        policyVal.setText(playerState.getPolicy());


        crashVal.setText(playerState.crashCount + "");
        populationVal.setText(MyUtils.formatNumber(playerState.population));
        realGdpVal.setText("₦" + MyUtils.formatNumber(playerState.realGdp));
        gdpVal.setText("₦" + MyUtils.formatNumber(playerState.gdp));
        cashVal.setText("₦" + MyUtils.formatNumber(playerState.cash));
        debtVal.setText("₦" + MyUtils.formatNumber(playerState.debt));
        inflationVal.setText(String.format("%.2f", playerState.inflationRate * 100) + "%");
        reserveVal.setText("₦" + MyUtils.formatNumber(playerState.stateReserve));
        rankVal.setText(MyUtils.Ordinalize(playerState.position));
        taxVal.setText(String.format("%.2f", playerState.taxRate * 100) + "%");
        debtToGdpVal.setText(String.format("%.2f", playerState.debtToGdpRatio * 100) + "%");
        growthVal.setText(String.format("%.2f", playerState.gdpGrowth * 100) + "%");
        stabilityVal.setText(String.format("%.2f", playerState.stability));
        debtPymtVal.setText("₦" + MyUtils.formatNumber(playerState.debtPayment));
        infraVal.setText(playerState.infrastructure + "");
        profitVal.setText("₦" + MyUtils.formatNumber(playerState.monthlyProfit));
        scoreVal.setText(String.format("%.2f", playerState.rankingScore));
        eventTypeVal.setText(EventTrigger.getEventType());
        eventDispVal.setText(EventTrigger.getEventDisp());
        if (AppMain.getSim().isElection()) {
            electionMessage = "This election saw a sweeping victory. The cost on the government was ₦" + MyUtils.formatNumber(AppMain.mainELectionCost()) + " and this was deducted from the state reserve";
        } else {
            electionMessage = "There is no election this month";
        }
        revenueVal.setText("₦" + MyUtils.formatNumber(playerState.monthlyRevenue));
        expensesVal.setText("₦" + MyUtils.formatNumber(playerState.monthlySpend));
        fedAllocVal.setText("₦" + MyUtils.formatNumber(playerState.federalAllocation));

        electionVal.setText(electionMessage);
    }

    public static void resetUI() {
        gdpSeries.getData().clear();
        inflationSeries.getData().clear();
        popSeries.getData().clear();
        cashSeries.getData().clear();
    }
}
