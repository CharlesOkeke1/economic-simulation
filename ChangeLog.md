# Changelog

_This project uses **MAJOR.MINOR.PATCH** versioning:_
- **MAJOR** increments for foundational system stages
- **MINOR** increments for new features or significant system expansions
- **PATCH** increments for bug fixes, balancing, tooling, and minor improvements

---

***Version 1.0.0*** - *2026-02-20*
- Added core month-by-month simulation loop (states + federal)
- Added basic policy application flow (player + AI)
- Added state/federal reporting outputs 

---

***Version 1.1.0*** - *2026-02-20*
- Refactored simulation into separate engine modules (SimulationEngine, PolicyEngine, Accounting, etc.)
- Added state ranking + position system (sorted by performance)
- Initial upload of core simulation engine to GitHub
- Established repository structure and baseline project state

---

***Version 1.2.0*** - *2026-02-21*
- Added economic crash model (risk-based GDP/stability/infrastructure reductions)
- Added bailout logic trigger (federal support under crash conditions)
- Updated AI policy decision logic to be more strategic and deterministic
- Expanded policy set (infrastructure, education, security, borrow, austerity, subsidies, trader support)

---

***Version 1.2.1*** - *2026-02-21*
- Fixed policy-selection “same output every time” behaviour
- Tuned stability/debt/profit threshold conditions
- Minor balancing adjustments
- Added all the states with real world scaled down figures

---

***Version 1.3.0*** - *2026-02-22*
- Introduced Government Type system per state
- Added `GovType` enum under `game.economies`
- Integrated government type as a field in `StateEconomy`
- Added government ideology biasing to policy scoring

---

***Version 1.3.1*** - *2026-02-22*
- Fixed enum placement/import issues
- Cleaned government type output formatting
- Minor structural cleanup in state initialization

---

***Version 1.4.0*** - *2026-02-22*
- Added policy scoring system via `PolicyClass`
- Implemented score accumulation model per policy
- Introduced ranked policy sorting using `Comparator`

---

***Version 1.4.1*** - *2026-02-22*
- Fixed comparator/generics inference issue (lambda treated items as `Object`)
- Corrected incorrect policy score wiring
- Fixed GDP accumulation inconsistencies
- Corrected state/federal cash dispersion logic

---

***Version 1.4.2*** - *2026-02-22*
- Implemented probabilistic AI selection tiers (best/mid/worst bands)
- Added weighted randomness control (single-roll selection logic)
- Made AI decision-making more realistic and less deterministic

---

***Version 1.4.3*** - *2026-02-22*
- Tuned probabilistic tier weights (e.g., 75% / 20% / 5%)
- Minor cleanup of AI indexing and selection assumptions
- Cleaned AI policy engine comments and improved readability

---

***Version 1.5.0*** - *2026-02-22*
- Added balanced initialization pass for states (normalized GDP/debt/stability/reserve ranges)
- Preserved population, tax rate, and government type consistency
- Introduced State Remittances feature to strengthen federal reserve mechanics

---

***Version 1.5.1*** - *2026-02-22*
- Fixed duplicate-file and restructuring compile issues
- Improved repository hygiene (.gitignore cleanup, structural organization)

---

***Version 1.6.0*** - *2026-02-22*
- Implemented Git versioning workflow (commit discipline + release tagging)
- Added structured `ChangeLog.md` documentation system

---

***Version 1.6.1*** - *2026-02-23*
- Enhanced upload script with commit message prompt
- Added optional tag creation in upload workflow
- Added developer admin check to reduce wait time during testing

---

***Version 1.6.2*** - *2026-02-23*
- Added a function to properly display government types
- Updated the report printer with the new function

---

***Version 1.7.0*** - *2026-02-23*
- Added Inflation Rate and Real GDP to each state
- Inflation affects GDP, debt, base expenditure and stability
- Updated the states file to have gov type and GDP growth
- Updated national GDP to be based of real GDP and not nominal
- Added inflation as a metric in state ranking
- Updated report printing to display real and not nominal GDP
- Minor bug fixes - Adjusted revenue and expenditure sources to be more realistic

---

***Version 1.7.1*** - *2026-02-23*
- File Updates - updated fields in state creation
- Minor Bug Fix - To improve realism adjusted reserve growth rate, adjusted base spend, adjusted inflation metric and adjusted gdp growth rate.

---