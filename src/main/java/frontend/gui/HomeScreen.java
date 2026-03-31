package gui;

import data.Constants;
import economies.StateEconomy;
import engine.SimulationEngine;
import utils.EnumToString;
import events.EventTrigger;
import utils.MyUtils;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class HomeScreen {
    public static XYChart.Series<Number, Number> gdpSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> gdpChart = createChart( "GDP (₦ Millions)", "Real GDP Growth", gdpSeries);

    public static XYChart.Series<Number, Number> inflationSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> inflaChart = createChart("Inflation", "Inflation Growth Rate", inflationSeries);

    public static XYChart.Series<Number, Number> cashSeries = new XYChart.Series<>();
    public static LineChart<Number, Number> cashChart = createChart("Cash (Millions)", "Cash Growth Rate", cashSeries);

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
    private static Label playerEventDispVal;
    private static Label playerEventType;
    private static Label govVal;
    private static Label stateVal;


    public static Parent build() {
        return buildLayout();
    }

    private static BorderPane buildLayout() {
        Map<String, StateEconomy> states = AppMain.getStateMap();
        StateEconomy playerState = states.get(AppMain.getDisplayConfig().getDisplayState());
        int currMonth = AppMain.getSim().getCurrentMonth();
        BorderPane root = new BorderPane();

        completeness = (double) currMonth/AppMain.getConfig().getTotalMonths();

        /*TOP BAR*/
        VBox stateBox = createCard("State", playerState.getName());
        stateVal = (Label) stateBox.getChildren().get(1);
        VBox diffBox = createCard("Difficulty",
                EnumToString.convert(AppMain.getDisplayConfig().getDifficulty(), "_"));
        VBox monthBox = createCard("Current Month", AppMain.getSim().getCurrentMonth() + "");
        monthVal = (Label) monthBox.getChildren().get(1);

        VBox policyBox = createCard("Current Policy", playerState.getPolicy());
        policyBox.setPrefWidth(200);
        policyVal = (Label) policyBox.getChildren().get(1);

        VBox gov = createCard("Government Type", EnumToString.convert(playerState.getGovernmentType(), "_"));
        govVal = (Label) gov.getChildren().get(1);
        gov.setPrefWidth(180);
        VBox crash = createCard("Economic Crashes", playerState.getCrashCount() + "");
        crashVal = (Label) crash.getChildren().get(1);

        VBox comp = createCard("Game Progress", String.format("%.2f", (completeness * 100)) + "%");
        compVal = (Label) comp.getChildren().get(1);

        VBox settings = createCard("Settings", "Settings");
        settings.setPadding(new Insets(0,0,0,10));
        settings.setOnMouseClicked(e -> {
            AppMain.switchRoot(SettingScreen.build());
            AppMain.pauseLoop();
            //ConfirmationBox.switchView("View State Switch", "Are you done viewing");
        });

        VBox restart = createCard("Restart", "Restart");
        restart.setOnMouseClicked(e -> ConfirmationBox.restart("Restart?", "Are you sure you want to restart?"));


        Button exit = new Button("Exit");
        exit.getStyleClass().add("card");
        exit.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: white;" + "-fx-font-size: 18;");
        exit.setOnAction(e -> {
            e.consume();
            ConfirmationBox.display("Leave?", "Are you sure you want to leave?");
        });

        HBox top = new HBox(15, stateBox, diffBox, monthBox, policyBox, gov, crash, comp, exit, restart, settings);
        top.setAlignment(Pos.TOP_LEFT);

        HBox topBar = new HBox(top);
        topBar.getStyleClass().add("top-bar");




        /*BOTTOM BAR*/
        VBox stateBoxx = createCard("State", AppMain.getConfig().getChosenState());
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
        HBox stats = createCardTitle("State Stats");

        VBox population = createCard("Population", MyUtils.formatNumber(playerState.getPopulation()));
        populationVal = (Label) population.getChildren().get(1);

        VBox realGdp = createCard("Real GDP", "₦" + MyUtils.formatNumber(playerState.getRealGdp()));
        realGdpVal = (Label) realGdp.getChildren().get(1);

        VBox gdp = createCard("Nominal GDP", "₦" + MyUtils.formatNumber(playerState.getGdp()));
        gdpVal = (Label) gdp.getChildren().get(1);

        VBox cash = createCard("Operating Cash", "₦" + MyUtils.formatNumber(playerState.getCash()));
        cashVal = (Label) cash.getChildren().get(1);

        VBox revenue = createCard("Monthly Revenue", "₦" + MyUtils.formatNumber(playerState.getMonthlyRevenue()));
        revenueVal = (Label) revenue.getChildren().get(1);

        VBox expenses = createCard("Monthly Expenses", "₦" + MyUtils.formatNumber(playerState.getMonthlySpend()));
        expensesVal = (Label) expenses.getChildren().get(1);

        VBox fedAlloc = createCard("Federal Allocation", "₦" + MyUtils.formatNumber(playerState.getFederalAllocation()));
        fedAllocVal = (Label) fedAlloc.getChildren().get(1);

        VBox debt = createCard("Current Debt", "₦" + MyUtils.formatNumber(playerState.getDebt()));
        debtVal = (Label) debt.getChildren().get(1);

        VBox debtPymt = createCard("Debt Payment", "₦" + MyUtils.formatNumber(playerState.getDebtPayment()));
        debtPymtVal = (Label) debtPymt.getChildren().get(1);

        VBox reserve = createCard("State Reserve", "₦" + MyUtils.formatNumber(playerState.getStateReserve()));
        reserveVal = (Label) reserve.getChildren().get(1);

        VBox profit = createCard("Monthly Profit", "₦" + MyUtils.formatNumber(playerState.getMonthlyProfit()));
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

        VBox gdps = createNotification(
                EventTrigger.getPlayerEventType(),
                EventTrigger.getPlayerEventDisp()
        );
        HBox geader = (HBox) gdps.getChildren().get(0);
        playerEventType = (Label) geader.getChildren().getFirst();
        playerEventDispVal = (Label) gdps.getChildren().get(1);

        VBox populations = createNotification(
                EventTrigger.getEventType(),
                EventTrigger.getEventDisp()
        );

        HBox header = (HBox) populations.getChildren().get(0);
        eventTypeVal = (Label) header.getChildren().getFirst();
        eventDispVal = (Label) populations.getChildren().get(1);

        if (currMonth % Constants.ELECTION_SPACING == 0 && currMonth != 0) {
            electionMessage = "The last election saw a sweeping victory. The cost on the government was ₦" + MyUtils.formatNumber(SimulationEngine.getElectCost()) + " and this was deducted from the state reserve";
        } else {
            electionMessage = "There is no election this month";
        }

        VBox realGdps = createNotification(
                "Election Review",
                electionMessage
        );
        electionVal = (Label) realGdps.getChildren().get(1);

        VBox inflations = createNotification(
                "Coming Soon!",
                "Monthly events and notifications coming soon"
        );

        VBox rightBar = new VBox(10, populations, gdps, realGdps, inflations);
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
        updateGraphData(playerState, currMonth); //Update the graph data
        VBox rank = createLargeCard("Position", MyUtils.Ordinalize(playerState.getPosition()));
        rankVal = (Label) rank.getChildren().get(1);

        VBox tax = createLargeCard("Tax Rate", String.format("%.2f", playerState.getTaxRate() * 100) + "%");
        taxVal = (Label) tax.getChildren().get(1);

        VBox debtToGdp = createLargeCard("Debt To Gdp", (playerState.getDebtToGdpRatio() * 100) + "%");
        debtToGdpVal = (Label) debtToGdp.getChildren().get(1);

        VBox growth = createLargeCard("Growth Rate", (playerState.getGdpGrowth() * 100) + "%");
        growthVal = (Label) growth.getChildren().get(1);

        VBox stability = createLargeCard("Stability", String.format("%.2f", playerState.getStability()));
        stabilityVal = (Label) stability.getChildren().get(1);

        VBox infra = createLargeCard("Infrastructure", playerState.getInfrastructure() + "");
        infraVal = (Label) infra.getChildren().get(1);

        VBox score = createLargeCard("Ranking Score", String.format("%.2f", playerState.getRankingScore()));
        scoreVal = (Label) score.getChildren().get(1);

        VBox inflation = createCard("Inflation Rate", String.format("%.2f", (playerState.getInflationRate() * 100)) + "%");
        inflationVal = (Label) inflation.getChildren().get(1);

        HBox center = new HBox(15, rank, score, tax, growth, stability, infra, debtToGdp, inflation);
        center.setPadding( new Insets(0,0,0,15));
        HBox chartLayer = new HBox(25, gdpChart, inflaChart, cashChart);


        gdpChart.setCreateSymbols(false);
        inflaChart.setCreateSymbols(false);
        cashChart.setCreateSymbols(false);

        chartLayer.setAlignment(Pos.CENTER);
        gdpChart.setAnimated(false);
        inflaChart.setAnimated(false);
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

        VBox chartPane = new VBox(30, chartLayer, center, policy);
        chartPane.setPadding(new Insets(50, 50, 0, 50));
        chartPane.setAlignment(Pos.TOP_CENTER);

        chartPane.getStyleClass().add("chart-pane");
        chartPane.setMaxSize(1075, 650);

        root.setTop(topBar);
        root.setLeft(leftSection);
        root.setRight(sb);
        root.setCenter(chartPane);
        root.setPadding(new Insets(15,40,70,40));

        return root;
    }

    private static void updateGraphData(StateEconomy playerState, int currMonth) {
        gdpSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.getRealGdp()/1_000_000)
        );
        gdpSeries.getNode().setStyle("-fx-stroke: #22c55e; -fx-stroke-width: 2px;");

        inflationSeries.getData().add(
                new XYChart.Data<>(currMonth, playerState.getInflationRate() * 100)
        );
        inflationSeries.getNode().setStyle("-fx-stroke: #ef4444; -fx-stroke-width: 2px;");
        cashSeries.getData().add ( new XYChart.Data<>(currMonth, playerState.getCash()/1_000_000));
        cashSeries.getNode().setStyle("-fx-stroke: #3b82f6; -fx-stroke-width: 2px;");
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

    private static LineChart<Number, Number> createChart(String yLabel, String title,
                                                         XYChart.Series<Number, Number> dataSeries)
    {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        xAxis.setLabel("Month");
        yAxis.setLabel(yLabel);

        chart.getData().add(dataSeries);
        chart.setPrefSize(360,245);

        chart.setTitle(title);
        chart.getStyleClass().add("chart");

        return chart;
    }

    private static void updateXAxis(LineChart<Number, Number> chart, int month) {

        NumberAxis xAxis = (NumberAxis) chart.getXAxis();

        int lower = Math.max(0, month - Constants.CHART_WINDOW_SIZE);
        int upper = Math.max(Constants.CHART_WINDOW_SIZE, month);

        xAxis.setLowerBound(lower);
        xAxis.setUpperBound(upper);
        xAxis.setTickUnit(2);
    }

    public static void refreshUI() {
        Map<String, StateEconomy> states = AppMain.getStateMap();
        StateEconomy playerState = states.get(AppMain.getConfig().getChosenState());
        int currMonth = AppMain.getSim().getCurrentMonth();
        completeness = (double) currMonth / AppMain.getConfig().getTotalMonths();


        updateXAxis(gdpChart, currMonth);
        updateXAxis(inflaChart, currMonth);
        updateXAxis(cashChart, currMonth);

        updateGraphData(playerState, currMonth);

        monthVal.setText(currMonth + "");
        compVal.setText(String.format("%.2f", (completeness * 100)) + "%");
        policyVal.setText(playerState.getPolicy());

        crashVal.setText(playerState.getCrashCount() + "");
        populationVal.setText(MyUtils.formatNumber(playerState.getPopulation()));
        realGdpVal.setText("₦" + MyUtils.formatNumber(playerState.getRealGdp()));
        gdpVal.setText("₦" + MyUtils.formatNumber(playerState.getGdp()));
        cashVal.setText("₦" + MyUtils.formatNumber(playerState.getCash()));
        debtVal.setText("₦" + MyUtils.formatNumber(playerState.getDebt()));
        inflationVal.setText(String.format("%.2f", playerState.getInflationRate() * 100) + "%");
        reserveVal.setText("₦" + MyUtils.formatNumber(playerState.getStateReserve()));
        rankVal.setText(MyUtils.Ordinalize(playerState.getPosition()));
        taxVal.setText(String.format("%.2f", playerState.getTaxRate() * 100) + "%");
        debtToGdpVal.setText(String.format("%.2f", playerState.getDebtToGdpRatio() * 100) + "%");
        growthVal.setText(String.format("%.2f", playerState.getGdpGrowth() * 100) + "%");
        stabilityVal.setText(String.format("%.2f", playerState.getStability()));
        debtPymtVal.setText("₦" + MyUtils.formatNumber(playerState.getDebtPayment()));
        infraVal.setText(playerState.getInfrastructure() + "");
        profitVal.setText("₦" + MyUtils.formatNumber(playerState.getMonthlyProfit()));
        scoreVal.setText(String.format("%.2f", playerState.getRankingScore()));
        revenueVal.setText("₦" + MyUtils.formatNumber(playerState.getMonthlyRevenue()));
        expensesVal.setText("₦" + MyUtils.formatNumber(playerState.getMonthlySpend()));
        fedAllocVal.setText("₦" + MyUtils.formatNumber(playerState.getFederalAllocation()));
        stateVal.setText(states.get(AppMain.getDisplayConfig().getDisplayState()).getName());
        govVal.setText(EnumToString.convert(states.get(AppMain.getDisplayConfig().getDisplayState()).getGovernmentType(), "_"));

        if (currMonth % Constants.ELECTION_SPACING == 0 && currMonth != 0) {
            electionMessage = "The last election saw a sweeping victory. The cost on the government was ₦" + MyUtils.formatNumber(SimulationEngine.getElectCost()) + " and this was deducted from the state reserve";
        } else {
            electionMessage = "There is no election this month";
        }
        electionVal.setText(electionMessage);

        eventTypeVal.setText(EventTrigger.getEventType());
        eventDispVal.setText(EventTrigger.getEventDisp());
        playerEventDispVal.setText(EventTrigger.getPlayerEventDisp());
        playerEventType.setText(EventTrigger.getPlayerEventType());
    }

    public static void resetUI() {
        gdpSeries.getData().clear();
        inflationSeries.getData().clear();
        cashSeries.getData().clear();
    }


}
