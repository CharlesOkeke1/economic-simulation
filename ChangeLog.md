# Changelog

_This project uses **MAJOR.MINOR.PATCH** versioning:_
- **MAJOR** increments for foundational system stages
- **MINOR** increments for new features or significant system expansions
- **PATCH** increments for bug fixes, balancing, tooling, and minor improvements

---

***Version 1.0.0*** - *20-02-2026*
- Added core month-by-month simulation loop (states + federal)
- Added basic policy application flow (player + AI)
- Added state/federal reporting outputs

---

***Version 1.1.0*** - *20-02-2026*
- Refactored simulation into separate engine modules (SimulationEngine, PolicyEngine, Accounting, etc.)
- Added state ranking + position system (sorted by performance)
- Initial upload of core simulation engine to GitHub
- Established repository structure and baseline project state

---

***Version 1.2.0*** - *21-02-2026*
- Added economic crash model (risk-based GDP/stability/infrastructure reductions)
- Added bailout logic trigger (federal support under crash conditions)
- Updated AI policy decision logic to be more strategic and deterministic
- Expanded policy set (infrastructure, education, security, borrow, austerity, subsidies, trader support)

---

***Version 1.2.1*** - *21-02-2026*
- Fixed policy-selection “same output every time” behaviour
- Tuned stability/debt/profit threshold conditions
- Minor balancing adjustments
- Added all the states with real world scaled down figures

---

***Version 1.3.0*** - *22-02-2026*
- Introduced Government Type system per state
- Added `GovType` enum under `game.economies`
- Integrated government type as a field in `StateEconomy`
- Added government ideology biasing to policy scoring

---

***Version 1.3.1*** - *22-02-2026*
- Fixed enum placement/import issues
- Cleaned government type output formatting
- Minor structural cleanup in state initialization

---

***Version 1.4.0*** - *22-02-2026*
- Added policy scoring system via `PolicyClass`
- Implemented score accumulation model per policy
- Introduced ranked policy sorting using `Comparator`

---

***Version 1.4.1*** - *22-02-2026*
- Fixed comparator/generics inference issue (lambda treated items as `Object`)
- Corrected incorrect policy score wiring
- Fixed GDP accumulation inconsistencies
- Corrected state/federal cash dispersion logic

---

***Version 1.4.2*** - *22-02-2026*
- Implemented probabilistic AI selection tiers (best/mid/worst bands)
- Added weighted randomness control (single-roll selection logic)
- Made AI decision-making more realistic and less deterministic

---

***Version 1.4.3*** - *22-02-2026*
- Tuned probabilistic tier weights (e.g., 75% / 20% / 5%)
- Minor cleanup of AI indexing and selection assumptions
- Cleaned AI policy engine comments and improved readability

---

***Version 1.5.0*** - *22-02-2026*
- Added balanced initialization pass for states (normalized GDP/debt/stability/reserve ranges)
- Preserved population, tax rate, and government type consistency
- Introduced State Remittances feature to strengthen federal reserve mechanics

---

***Version 1.5.1*** - *22-02-2026*
- Fixed duplicate-file and restructuring compile issues
- Improved repository hygiene (.gitignore cleanup, structural organization)

---

***Version 1.6.0*** - *22-02-2026*
- Implemented Git versioning workflow (commit discipline + release tagging)
- Added structured `ChangeLog.md` documentation system

---

***Version 1.6.1*** - *23-02-2026*
- Enhanced upload script with commit message prompt
- Added optional tag creation in upload workflow
- Added developer admin check to reduce wait time during testing

---

***Version 1.6.2*** - *23-02-2026*
- Added a function to properly display government types
- Updated the report printer with the new function

---

***Version 1.7.0*** - *23-02-2026*
- Added Inflation Rate and Real GDP to each state
- Inflation affects GDP, debt, base expenditure and stability
- Updated the states file to have gov type and GDP growth
- Updated national GDP to be based of real GDP and not nominal
- Added inflation as a metric in state ranking
- Updated report printing to display real and not nominal GDP
- Minor bug fixes - Adjusted revenue and expenditure sources to be more realistic

---

***Version 1.7.1*** - *23-02-2026*
- **File Updates** - updated fields in state creation
- **Minor Bug Fix** - To improve realism adjusted reserve growth rate, adjusted base spend, adjusted inflation metric and adjusted gdp growth rate.
- **Affected Files** - `EconomyInitializer.java` and `PolicyEngine.java`.

---

***Version 1.7.2*** - *24-02-2026*
- **Minor Bug Fixes** - NaN and infinity number issues resolved with minimum federalAllocation, cash and gdp.
- **Realism Updates** - Infrastructure and Austerity Policy updated to be more realistic.
- **Realism Updates** - GDP, Cash growth, state stability, inflation rate, state remittance and other variables have been made more realistic and capped to prevent overflow where necessary.
- **Report now shows oil price per barrel, nominal gdp and inflation rate.
- **Affected Files** - `PolicyEngine.java`, `ReportPrinter.java` and `FederalAccountingEngine.java`
---

***Version 1.7.3*** - *24-02-2026*
- **Minor Bug Fixes** - Stability now grows realistically.
- **Realism Updates** - Policies have been made more realistic. Made AI decision less efficient for realism. The AI's now choose the best policies less.
- **Realism Updates** - GDP, reserve, expenditure and cash growth are now more realistic.
- **Minor Update** - Each month, the federal economy report is displayed.
- **Affected Files** - `PolicyEngine.java`, `AiPolicyMaker.java` and `NigerianEconomyGame.java`

---

***Version 1.7.3.1*** - *24-02-2026*
- ChangeLog file now updated for both this version and `v1.7.3`.
- **Minor Bug Fixes** - GDP growth was undershooting and is now stable again.
- **Affected Files** - `PolicyEngine.java` and `NigerianEconomyGame.java`

---

***Version 1.7.3.2*** - *25-02-2026*
- Starting values for each state adjusted, economic equations adjusted.
- **Affected Files** - `PolicyEngine.java` and `EconomyInitializer.java`

---

***Version 1.7.4*** - *25-02-2026*
- **Minor Update** - Comments under the `game.economies` package have been cleaned up. Report Printer got an update for the federal economy.
- **Minor Bug Fix** - Stability, GDP and cash growth stabilization. Major values are now protected against `isInfinite()` and `isNaN()`. All state inflation rates have been adjusted.
- **Affected Files/Packages** - `PolicyEngine.java`, `EconomyInitializer.java` the whole `game.economies` package and `PrintReports.java`

---

***Version 1.7.4.1*** - *25-02-2026*
- **Minor File Update** - `ChangeLog.md` file got a formatting update.
- **Minor Bug Fix** - Cash growth has been stabilized on par with gdp
- **Affected Files/Packages** - `PolicyEngine.java`.

---

***Version 1.7.5*** - *25-02-2026*
- **Minor Update** - Comments under the `game.ui` and `game.utils` package have been cleaned up.
- **New File** - `game.initialization` renamed to `game.data`. A `Constants.java` file with constants has been added and all files affected files have been modified.
- **Minor Bug Fix** - GDP and cash growth got economic tweaks to improve realism. Main game file now prevents user from choosing an unavailable state.
- **Affected Files/Packages** - `PolicyEngine.java`, `FederalAccounting.java`, `SimulationEngine.java`, `NigerianEconomyGame.java` and `PrintReports.java`.

---

***Version 1.8.0*** - *26-02-2026*
- **Big Update (Architectural Refactor)** - Simulation lifecycle has been centralized inside `SimulationEngine`. The month loop, policy execution flow, crash handling, ranking updates, and reporting are now internally controlled via `run()` and `simulateMonth()`.
- **New Feature** - Introduced `GameConfig` to encapsulate chosen state, total months, and difficulty as immutable configuration data.
- **New File** - Added `GameSetup.java` to separate user input collection and validation from the main entry point. Added `GameDifficulty.java` as an enum to help set difficulties which influence how good the AI is and updated in the `AiPolicyMaker.java`. `EconomicCrash.java` was added to free up some space in the sim engine file.
- **Structural Improvement** - `SimulationEngine` converted from static-style simulation to instance-based lifecycle control. External month counter and parameter-heavy simulation calls have been removed.
- **Encapsulation Improvement** - Configuration data now accessed through getters instead of static calls. Clear separation between setup, engine, and UI responsibilities has been established.
- **Codebase Simplification** - `NigerianEconomyGame.java` reduced to bootstrapping logic only (data creation to setup to engine initialization to `engine.run()`).
- **Affected Files/Packages** - `SimulationEngine.java`, `GameConfig.java`, `GameSetup.java`, `NigerianEconomyGame.java`, `AiPolicyMaker.java`, the whole `game.config` package,  the whole `game.ui` package and other related files.

---

***Version 1.8.1*** - *26-02-2026*
- **Minor Update** - `devType` added to game config to check if players are developers. `GameSetup.java` fetches this information.
- **Minor File Updates** - Game setup now skips initialization for developers and `SimulationEngine.run()` now uses the `TurnRandomizer.randomP()` for testing purposes if player is a developer.
- **Affected Files/Packages** - `GameConfig.java`, `GameSetup.java`, `SimulationEngine.java` and `EnumToString.java`.

---

***Version 1.8.2*** - *26-02-2026*
- **Minor New Feature** - `FederalEconomy.java` now has a new field called Operating Cash which is 20% of monthly allocation pool, while federal reserve dropped to 15%. The new field will be a key player in disaster management and might possibly get a bigger share as time goes on. `EconomyInitializer.java` has been updated.
- **Minor Bug Fixes** - Current month has been adjusted to start from 1 and not 0. Report printer adjusted for this cause. `AiPolicyMaker.java` now has the difficulties adjusted to make the expert level a bit tougher.
- **Minor File Update** - The `SimulationEngine.run()` now displays game difficulty.
- **Affected Files/Packages** - `AiPolicyMaker.java`, `FederalEconomy.java`, `SimulationEngine.java`, `EconomyInitializer.java`, `FederalAccounting.java` and `EnumToString.java`.

---

***Version 1.8.3*** - *26-02-2026*
- **Project Update** - The project has been moved out of uni vm to main system.
- **File Updates** - Some files got some tuning to improve UI and economic computations.
- **Repository Update** - All files are to be uploaded to facilitate move.

---

***Version 1.8.3.1*** - *26-02-2026*
- Upload file now pushes tags and doesnt just keep them locally

---

***Version 1.8.4*** - *26-02-2026*
- Upload file now pushes tags and doesnt just keep them locally

---

***Version 1.9.0*** - *26-02-2026*
- **Build System Upgrade** - Introduced automated build pipeline inside the upload script. Compilation, manifest generation, JAR packaging, tagging, and pushing are now executed in one controlled release flow.
- **New Distribution Format** - Implemented runnable JAR packaging for each versioned release. Game builds are now exportable as `NigerianEconomy-vX.X.X.jar` artifacts and attached manually to GitHub Releases.
- **Release Workflow Enhancement** - Structured GitHub Releases process finalized. Version tags now represent stable distribution milestones with corresponding compiled artifacts.
- **Directory Restructure** - Added dedicated `/build` directory for generated artifacts and `/out` for compiled classes. Updated `.gitignore` to exclude compiled binaries and build outputs from source control.
- **GDP Growth Calculation Fix** - Resolved growth distortion caused by GDP being modified before baseline capture. Growth is now calculated from the correct previous-month snapshot.
- **Economic Reporting Correction** - Adjusted reporting logic to ensure displayed GDP growth reflects actual realized monthly change.
- **Output Formatting Improvement** - Standardized percentage formatting to remove floating-point precision artifacts in reports.
- **Simulation Stability Improvements** - Cleaned monthly update sequencing to prevent unintended state drift during economic calculations.
- **Affected Files** - `PolicyEngine.java`, `.gitignore`, `PrintReports.java` and upload script.

---

***Version 2.0.0*** - *16-03-2026*  
*(Pre-Release – Major GUI Transition)*

- **Full Graphical Interface Migration** - Replaced the command-line interface with a fully interactive JavaFX graphical environment. The simulation is now driven through a windowed UI rather than terminal output.
- **Application Architecture Overhaul** - Introduced `AppMain` as the central application controller responsible for scene management, simulation orchestration, configuration access, and UI lifecycle management via a game loop.
- **Scene Management System** - Implemented `AppMain.switchRoot()` allowing seamless transitions between UI screens without restarting the application.
- **Interactive Main Menu** - Added `MenuScreen` interface enabling pre-simulation configuration including state selection, difficulty selection, simulation duration, and developer mode toggling.
- **Gameplay Dashboard Implementation** - Created the `HomeScreen` interface providing a multi-panel dashboard displaying real-time economic metrics, charts, and game controls.
- **Live Chart Visualization** - Integrated JavaFX `LineChart` components to track Real GDP, Inflation, and Population growth dynamically as the simulation progresses.
- **Simulation Tick Engine** - Introduced a `Timeline` driven simulation loop allowing the economy to advance one month at a time instead of executing the entire simulation instantly.
- **Real-Time UI Refresh System** - Implemented a centralized UI refresh mechanism allowing dashboard values and charts to update dynamically after each simulation tick using the `HomeScreen.refreshUI()` method.
- **Policy Interaction System** - Added interactive policy selection through dropdown controls allowing players to apply policies directly from the GUI rather than command-line input.
- **Policy Update** - Policy is now a variable belonging to the `StateEconomy` object.
- **Dashboard Indicator Cards** - Implemented center dashboard metrics including position ranking, tax rate, debt-to-GDP ratio, growth rate, stability, and infrastructure indicators.
- **Event Feed Panel** - Added a scrollable event notification panel to display economic and political developments during gameplay.
- **Styling Framework Introduction** - Implemented centralized CSS styling (`App.css`) introducing reusable classes for cards, panels, charts, and interface elements.
- **Reusable UI Components** - Created standardized UI builders including `createCard`, `createLargeCard`, `createNotification`, and `createChart` to simplify layout construction.
- **Resource System Implementation** - Introduced a structured `/resources/game` directory enabling classpath-based loading of assets and stylesheets.
- **Background Rendering System** - Implemented blurred menu backgrounds and overlay tinting to visually separate UI components from background imagery.
- **Project Layout Improvements** - Standardized layout architecture using `BorderPane`, `HBox`, `VBox`, and `StackPane` containers to support scalable UI composition.
- **Engine-UI Synchronization Model** - Finalized runtime data flow allowing the simulation engine to update the UI in real time through shared state objects.
- **Modal Interaction Foundation** - Introduced groundwork for modal dialogs and confirmation windows for future gameplay events.
- **Development Status** - Version 2.0.0 marks the beginning of the graphical interface phase of the project and is released as a **pre-release build** pending further stability improvements.

- **Affected Packages** - `economies`, `analytics`, `metrics`, `gui`, `config` and `engine`

---

***Version 2.1.0*** - *25-03-2026*
- **Bug Fixes** - Fixed GDP, Cash and inflation spikes.
- **Major Development Update** - All state owned variables have been properly encapsulated.
- **File Structure Update** - All files have been ordered and put in their appropriate packages, `backend`, `frontend` and `utils`.
- **GUI Updates** - The events and elections under the recent events panel now update appropriately.
- **Potential Weaknesses** - `Some errors may still arise but that is expected`.

- **Affected Packages** - `backend`, `frontend` and `utils`.

---