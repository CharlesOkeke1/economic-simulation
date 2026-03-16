# Nigerian Economy Simulation
## Version 2.0.0 -- Graphical Interface Update *(Pre-Release)*
**Release Date:** 12 March 2026

Version **2.0.0** marks the largest update to the Nigerian Economy
Simulation so far.

The game has moved away from a command-line interface and now runs
entirely inside a **graphical dashboard built with JavaFX**. This change
introduces a real interactive environment where economic data updates
live while the simulation runs.

This version is released as a **pre-release** while the interface and
simulation integration continue to be refined.

------------------------------------------------------------------------

# Major Highlight -- The Game Now Has a GUI

The simulation is no longer controlled through terminal input.

Instead, the game now launches with a **graphical interface** where you
can configure and run the simulation directly from the window.

The interface includes:

-   A configuration menu
-   A real-time simulation dashboard
-   Economic charts
-   Live statistics panels
-   Event notifications

This update lays the foundation for the full strategy experience planned
for future versions.

------------------------------------------------------------------------

# New Features

## Interactive Start Menu

The game now launches with a configuration screen where players can
choose their starting parameters.

Available options include:

-   Selecting the Nigerian state you will govern
-   Choosing the difficulty level
-   Setting how many months the simulation should run
-   Enabling developer mode for testing purposes

This replaces the previous command-line setup.

------------------------------------------------------------------------

## Real-Time Simulation Dashboard

Once the game begins, players are taken to a new **dashboard interface**
where the economy evolves in real time.

The screen is divided into several panels to organize information
clearly.

### Top Bar

Displays session information including:

-   State
-   Difficulty
-   Current month
-   Active policy
-   Government type
-   Economic crash counter

### Economic Statistics Panel

The left side of the screen shows detailed economic indicators such as:

-   Population
-   Real GDP
-   Nominal GDP
-   Operating cash
-   State debt
-   Inflation rate
-   State reserves

These values update automatically as the simulation progresses.

### Live Economic Charts

The center of the dashboard now includes graphs that visualize economic
trends during gameplay.

Current charts include:

-   Real GDP growth
-   Inflation rate
-   Population growth

Instead of only seeing numbers, players can now watch the economy evolve
visually over time.

### Strategy Indicator Cards

Key performance indicators are displayed in the center of the dashboard.

These include:

-   National ranking position
-   Tax rate
-   Debt-to-GDP ratio
-   Economic growth rate
-   Stability level
-   Infrastructure level

These indicators help players quickly understand the health of their
state.

### Event Feed

The right side of the screen now includes a **scrollable event feed**.

This panel displays notifications about political or economic
developments such as:

-   upcoming elections
-   worker protests
-   infrastructure damage
-   market changes

The event system will expand further in future versions.

### Policy Selection

Players can now choose policies directly through the interface.

Policies are selected using a dropdown and then confirmed through the
dashboard.

The selected policy is applied during the next simulation step.

------------------------------------------------------------------------

# Simulation Changes

## Real-Time Game Loop

Previously, the simulation ran instantly and printed results to the
console.

Now the game progresses **one month at a time**.

Every few seconds the engine advances the simulation and the dashboard
updates automatically.

This allows players to watch the economy evolve live.

------------------------------------------------------------------------

## Dynamic UI Updates

The interface now refreshes automatically after each simulation step.

Charts grow, statistics change, and the event feed updates as the
economy develops.

------------------------------------------------------------------------

# Visual Improvements

This update introduces a full **dark dashboard theme** with reusable UI
components.

The interface now uses a centralized styling system that controls:

-   panel appearance
-   chart styling
-   notification cards
-   dashboard layout

The result is a cleaner and more consistent visual design.

------------------------------------------------------------------------

# Technical Improvements

Several architectural changes were made internally to support the
graphical interface.

These include:

-   a new application controller (`AppMain`)
-   modular screen system (`MenuScreen`, `HomeScreen`)
-   shared configuration access through `GameConfig`
-   structured resource loading for assets and stylesheets
-   reusable UI component builders for cards, notifications, and charts

These improvements make the codebase easier to extend as the game grows.

------------------------------------------------------------------------

# Project Status

Version **2.0.0** marks the beginning of the **graphical phase of the
project**.

The core dashboard and simulation loop are now in place, but many
gameplay systems are still under development.

This release is therefore marked as a **pre-release build**.

------------------------------------------------------------------------

# Planned Next Improvements

Future versions will expand the interface and gameplay with features
such as:

-   election systems
-   expanded economic events
-   policy impact visualizations
-   modal event dialogs
-   performance improvements for long simulations
-   additional charts and analytics

------------------------------------------------------------------------

# Developer Notes

Version **2.0.0** introduces a major architectural shift in the project.

The codebase has transitioned from a console-driven simulation into a
modular **JavaFX application architecture**.

## Application Entry Point

The application now launches through:

`AppMain.java`

Responsibilities include:

-   Launching the JavaFX application
-   Managing the primary Stage and Scene
-   Switching between interface screens
-   Initializing the simulation engine
-   Providing global access to configuration and state objects

## Scene Management

Screens are now modular and dynamically switched using:

`AppMain.switchRoot()`

Current screens:

-   MenuScreen
-   HomeScreen

Future screens may include:

-   election results
-   policy summaries
-   crisis alerts
-   endgame screens

## Simulation Loop Integration

The simulation now runs through a JavaFX **Timeline** rather than
executing all months instantly.

Runtime flow:

Timeline Tick → SimulationEngine.run() → Economy State Updates →
HomeScreen.refreshUI()

This allows the UI to update live during the simulation.

## UI Component Architecture

Reusable helper methods were introduced to standardize layout creation:

-   createCard()
-   createLargeCard()
-   createNotification()
-   createChart()

This keeps the UI code maintainable and reduces duplicated layout code.

## Resource System

Assets and styles are now loaded from the classpath using the
`/resources` directory.

This allows the project to run correctly when packaged into runnable JAR
builds.

## Styling Framework

A centralized stylesheet (`App.css`) defines the interface appearance
including:

-   card styles
-   panel styles
-   chart appearance
-   dashboard layout

Separating styling from layout code simplifies visual changes.

## Engine and UI Separation

The simulation engine remains independent from the GUI.

The UI reads data from economic state objects but does not perform
economic calculations itself.

This ensures:

-   engine logic stays modular
-   the UI acts purely as a presentation layer
-   future interfaces could be added without rewriting the simulation
    engine

--

# Event Feed System

A scrollable notification panel has been introduced to display economic events such as:

- elections
- protests
- infrastructure damage
- market shifts

These will later connect directly to the simulation event engine.

---

# Developer Notes

This version introduces major structural changes and remains under active development.

Areas currently being expanded include:

- UI refresh synchronization
- event engine integration
- policy feedback visualization
- performance optimization for long simulations
- modal interaction windows
- election cycle implementation

---

# Status

Version **2.0.0** is released as a **pre-release build** and should not yet be considered feature complete.

The update establishes the graphical framework that future gameplay systems will build upon.

---

# Affected Components

Core files introduced or modified include:

```
AppMain.java
MenuScreen.java
HomeScreen.java
SimulationEngine.java
GameConfig.java
EconomyInitializer.java
App.css
```

---

The project has now officially entered its **graphical interface phase**, marking a major milestone in the development of the Nigerian Economy Simulation.

------------------------------------------------------------------------

# Economic Simulation Improvements

Version **2.0.0** introduces several improvements to the internal economic model to improve realism and long‑term stability.

### Centralized GDP Mutation

GDP is now mutated in a single location inside the simulation pipeline. Policies no longer modify GDP directly.  
Instead they contribute to a **policy growth variable** which is applied during the monthly economic update step.

This model follows:

Total Growth = Structural Growth + Policy Growth

This improves simulation stability, makes economic behaviour easier to understand, and ensures growth indicators displayed in the dashboard match the actual simulation results.

### Growth Calculation Fix

An issue where the **displayed GDP growth differed from the true realized growth** has been corrected.  
Growth is now calculated from the change in **Real GDP between monthly snapshots**, ensuring the dashboard and telemetry output reflect the true economic performance of the state.

### Inflation and GDP Interaction Improvements

The relationship between inflation and GDP has been adjusted.

Real economic output is now updated first, after which **inflation is applied when deriving nominal GDP**.  
This prevents inflation from artificially generating economic growth.

### Fiscal Stability Improvements

Several adjustments were made to the fiscal model to stabilize state finances:

- Improved debt interest calculations using debt‑to‑GDP ratios
- Improved handling of deficit months and negative profits
- Adjusted repayment behaviour during surplus periods
- Improved federal remittance flow handling

These changes significantly reduce sudden debt spikes and improve economic realism across longer simulations.

### Telemetry Logging System

A telemetry system has been introduced allowing the simulation to export monthly economic data in JSON format.

Logged values include:

- GDP
- inflation
- cash
- debt
- stability
- population

These logs are used for simulation analysis, debugging and balancing.

------------------------------------------------------------------------

# Release Status (Testing Build)

Although Version **2.0.0** introduces the graphical interface and major structural improvements, the project is **not yet considered fully stable**.

This release is published primarily for **testing purposes** while development continues on the graphical interface and economic systems.

Some areas of the game are still undergoing refinement including:

- long‑term economic balancing
- UI interaction polish
- event system expansion
- interface performance during long simulations

Users should therefore treat this build as an **experimental release**.

------------------------------------------------------------------------

# Planned Next Improvements

Development will continue following the 2.0.0 release with several major improvements planned.

### Interface Improvements
- Proper **settings screen** for simulation configuration
- Improved UI layout consistency
- Better chart formatting and scaling
- Additional dashboard indicators

### Expanded State View
- Ability to **view multiple states simultaneously**
- National overview panels
- Additional economic analytics tools

### Simulation Improvements
- Further economic stability improvements
- Expanded policy impact modelling
- Improved fiscal balancing for long simulations

### Event System Expansion
- Expanded economic and political events
- Election cycle integration
- Crisis and shock scenarios

### Stability Improvements
- Continued bug fixes
- improved long‑run simulation stability
- additional performance improvements

Version 2.0.0 marks the start of the graphical phase of the project and serves as the foundation for future gameplay systems.
