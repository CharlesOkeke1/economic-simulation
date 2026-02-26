/**
 * ================================================================
 * NIGERIAN FEDERATION ECONOMIC ENGINE — POST-REFACTOR SPEC
 * ================================================================
 
// Higher Priority >>>>
// Fix NaN issues
/**
 * =========================
 * INFLATION SYSTEM DESIGN
 * =========================
 *
 * Overview:
 * Inflation represents the percentage increase in the general price level
 * of a state’s economy. It affects real economic performance and political stability,
 * but does NOT directly inflate profit calculations within the same month.
 *
 * Core Variables:
 *
 * double inflationRate;
 *      - Current monthly inflation rate (used during month calculations).
 *      - Recalculated at end of month.
 *
 * double priceLevel = 1.0;
 *      - Cumulative price index.
 *      - Updated monthly using:
 *            priceLevel *= (1 + inflationRate);
 *
 * double realGdp;
 *      - Inflation-adjusted GDP.
 *      - Calculated as:
 *            realGdp = nominalGdp / priceLevel;
 *
 * =========================
 * UPDATE ORDER
 * =========================
 *
 * Month Flow:
 *
 * 1. Use existing inflationRate in all monthly calculations.
 * 2. Apply policy effects.
 * 3. Compute revenue.
 * 4. Compute expenditure.
 * 5. Compute profit.
 * 6. Handle debt payment and reserves.
 * 7. Calculate new inflationRate.
 * 8. Update priceLevel.
 * 9. Recalculate realGdp.
 * 10. Apply stability adjustments.
 * 11. Clamp / safety checks.
 *
 * Inflation must be calculated AFTER accounting logic
 * to avoid double-counting effects within the same month.
 *
 * =========================
 * EFFECTS OF INFLATION
 * =========================
 *
 * 1. Real GDP Adjustment:
 *      realGdp = nominalGdp / priceLevel;
 *
 * 2. Stability Impact:
 *      stability -= inflationRate * 1.5;
 *
 * 3. Debt Pressure (Optional):
 *      If inflationRate > threshold:
 *          interestRate increases;
 *          debtPayment = debt * interestRate;
 *
 * 4. Inflation Smoothing (Recommended):
 *      inflationRate =
 *          (previousInflation * 0.6)
 *        + (calculatedInflation * 0.4);
 *
 * This prevents extreme volatility and runaway spirals.
 *
 * =========================
 * DESIGN PRINCIPLES
 * =========================
 *
 * - Inflation modifies next month’s environment.
 * - Inflation does NOT directly inflate revenue and spending
 *   within the same accounting cycle.
 * - Inflation affects real values, not nominal bookkeeping.
 * - All values must be clamped to prevent instability.
 *
 * =========================
 * FUTURE EXTENSIONS
 * =========================
 *
 * - Inflation volatility tracking
 * - Investor confidence modifier
 * - Central bank interest rate system
 * - Currency strength index
 *
 
* ----------------------------------------------------------------
 * ======================== 2. EVENT SYSTEM =======================
 * ----------------------------------------------------------------
 *
 * Events occur randomly with probability P per month.
 * Some events are national, others state-specific.
 *
 * Events modify GDP, stability, infrastructure, reserves,
 * debt levels, oil prices, or growth rate multipliers.
 *
 * ========================
 * BALANCED EVENTS (2)
 * ========================
 *
 * 1) Commodity Price Adjustment
 *      + Oil price may rise or fall by ±8%
 *      + Federal allocation fluctuates accordingly
 *      + No direct GDP destruction
 *
 * 2) Regional Investment Conference
 *      + Infrastructure +2
 *      + Stability +1
 *      + PolicySpend increases temporarily
 *
 *
 * ========================
 * FAIRLY BAD EVENTS (3)
 * ========================
 *
 * 3) Flooding Incident (State-Specific)
 *      GDP *= 0.96
 *      Infrastructure -= 2
 *      Stability -= 4
 *      EmergencySpend += 0.03 * gdp
 *
 * 4) Oil Revenue Dip (National)
 *      Oil price index -15%
 *      Federal allocation reduced
 *
 * 5) Inflation Surge
 *      Stability -5
 *      MonthlySpend +10%
 *      GDP growth rate penalty -0.005
 *
 *
 * ========================
 * CATASTROPHIC EVENTS (2)
 * ========================
 *
 * 6) Armed Conflict
 *      GDP *= 0.82
 *      Infrastructure -= 6
 *      Stability -= 15
 *      Debt += 0.10 * gdp
 *      CrashCount++
 *
 * 7) Sovereign Debt Crisis
 *      If debtToGdpRatio > 0.75:
 *          Stability -= 20
 *          Federal bailout triggered
 *          GDP *= 0.88
 *          DebtInterestRate += 0.02
 *
 *
 * ========================
 * FAIRLY GOOD EVENTS (2)
 * ========================
 *
 * 8) Tech Sector Boom
 *      GDP *= 1.05
 *      Stability +4
 *      MonthlyRevenue +8%
 *
 * 9) Infrastructure Grant
 *      Infrastructure +4
 *      StateReserve +0.05 * gdp
 *
 *
 * ========================
 * VERY GOOD EVENT (1)
 * ========================
 *
 * 10) Oil Boom Supercycle
 *      OilPriceIndex +25%
 *      FederalAllocation +20%
 *      NationalGDP *= 1.04
 *      Stability +6
 *
 *
 * ----------------------------------------------------------------
 * ======================= 3. ELECTION SYSTEM =====================
 * ----------------------------------------------------------------
 *
 * Elections occur every 10 months:
 *
 *      if (counter % 10 == 0)
 *          triggerElection();
 *
 * Election Effects:
 *
 * 1. All states randomly reassign GovType
 * 2. Stability resets toward neutral baseline (e.g., clamp 40–60)
 * 3. Player may optionally:
 *      - Switch to another state
 *      - Keep current state
 *
 * Optional realism extensions:
 *
 * - States with low stability are more likely to shift toward POPULIST.
 * - States with high debt are more likely to shift toward DEBT_HAWK.
 * - Re-election bonus:
 *      If state stability > 60 → +2 stability post-election.
 *
 *
 * ----------------------------------------------------------------
 * =================== 4. AI + EVENTS INTEGRATION =================
 * ----------------------------------------------------------------
 *
 * Event impacts modify:
 *      gdp
 *      infrastructure
 *      stability
 *      monthlySpend
 *      federalAllocation
 *
 * GovType modifies:
 *      policy scoring weights
 *
 * Elections modify:
 *      long-term ideological drift
 *
 *
 * ----------------------------------------------------------------
 * ======================== DESIGN OBJECTIVES =====================
 * ----------------------------------------------------------------
 *
 * - Prevent identical state behavior
 * - Introduce macro volatility
 * - Simulate political ideology shifts
 * - Create boom-bust cycles
 * - Encourage strategic long-term play
 *
 * NigerianEconomy 1.3 transforms the engine from a fiscal sandbox
 * into a political-economic simulation framework.
 *
 * ================================================================
 */

/**
 * ================================================================
 *                  NIGERIAN ECONOMY 1.3.1
 *         Political Economy, Events & Structural Growth Model
 * ================================================================
 *
 * This revision introduces:
 *
 * 1. Government Ideology System (GovType)
 * 2. Event Engine (Macro Volatility)
 * 3. Election Cycle (Every 10 Months)
 * 4. Structural GDP Feedback Mechanism
 * 5. State Multiplier Field (Long-Term State Bias)
 * 6. Cash Explosion Control & Liquidity Transmission
 *
 *
 * ================================================================
 * 1. NEW FIELD – STATE MULTIPLIER
 * ================================================================
 *
 * Add to StateEconomy:
 *
 *      public double structuralMultiplier;
 *
 * Purpose:
 * Represents long-term structural efficiency of the state.
 * Accounts for geography, institutions, corruption, bureaucracy,
 * business climate, and sectoral advantage.
 *
 * Range:
 *      0.85  – structurally weak
 *      1.00  – neutral baseline
 *      1.15  – highly efficient economy
 *
 * Effect:
 *
 *      growthRate *= structuralMultiplier;
 *
 * Also influences revenue efficiency:
 *
 *      monthlyRevenue *= structuralMultiplier;
 *
 *
 * ================================================================
 * 2. LIQUIDITY → GDP FEEDBACK FIX
 * ================================================================
 *
 * Problem:
 * Cash grows through borrowing and revenue, but GDP does not
 * respond proportionally.
 *
 * Solution:
 * Introduce liquidity-driven growth channel.
 *
 * Add to GDP Growth Model:
 *
 *      double liquidityRatio = state.cash / state.gdp;
 *
 *      if (liquidityRatio > 0.15)
 *          growthRate += 0.002 * Math.min(liquidityRatio, 0.40);
 *
 *      if (liquidityRatio < 0.02)
 *          growthRate -= 0.003;
 *
 * Interpretation:
 * - High liquidity increases investment and private spending.
 * - Extremely low liquidity slows economic activity.
 *
 *
 * ================================================================
 * 3. CASH EXPLOSION CONTROL
 * ================================================================
 *
 * Problem:
 * Cash accumulates infinitely when revenue > spending.
 *
 * Add capital leakage:
 *
 *      double idlePenalty = 0.01 * state.cash;
 *      state.cash -= idlePenalty;
 *
 * This represents:
 * - Corruption
 * - Capital flight
 * - Inflationary inefficiency
 *
 *
 * Add soft cap:
 *
 *      if (state.cash > 0.75 * state.gdp)
 *          state.cash = 0.75 * state.gdp;
 *
 * (Prevents unrealistic liquidity dominance.)
 *
 *
 * ================================================================
 * 4. REVISED GDP GROWTH STRUCTURE
 * ================================================================
 *
 * Replace simple:
 *      state.gdp *= (1 + growthRate);
 *
 * With:
 *
 *      growthRate =
 *          baseGrowth
 *          + infraEffect
 *          + stabilityEffect
 *          + liquidityEffect
 *          - debtDrag
 *          - taxDistortion;
 *
 *      growthRate *= structuralMultiplier;
 *
 *      growthRate = clamp(growthRate, -0.05, 0.06);
 *
 *      state.gdp *= (1 + growthRate);
 *
 *
 * Where:
 *
 * Debt Drag:
 *      double debtDrag =
 *          0.02 * Math.max(0, state.debtToGdpRatio - 0.50);
 *
 * Stability Effect:
 *      (state.stability - 50) * 0.0008
 *
 * Infrastructure Effect:
 *      0.0015 * Math.log(1 + state.infrastructure)
 *
 *
 * ================================================================
 * 5. EVENT SYSTEM UPDATE – MULTIPLIER INTERACTION
 * ================================================================
 *
 * Events now modify structuralMultiplier temporarily.
 *
 * Example:
 *
 * Flood:
 *      structuralMultiplier -= 0.05 (for 3 months)
 *
 * Tech Boom:
 *      structuralMultiplier += 0.07 (for 4 months)
 *
 * Armed Conflict:
 *      structuralMultiplier = 0.80 temporarily
 *
 *
 * ================================================================
 * 6. ELECTION REBALANCE EFFECT
 * ================================================================
 *
 * Every 10 months:
 *
 *      if (month % 10 == 0)
 *          triggerElection();
 *
 * Effects:
 *
 * 1. All states re-roll GovType.
 *
 * 2. structuralMultiplier drifts toward 1.00:
 *
 *      structuralMultiplier =
 *          0.85 * structuralMultiplier + 0.15;
 *
 * (Institutional reset effect.)
 *
 * 3. Player may switch states.
 *
 *
 * ================================================================
 * 7. LONG-TERM MACRO STABILITY OBJECTIVE
 * ================================================================
 *
 * Desired emergent behavior:
 *
 * - High liquidity → growth but risk inflation.
 * - High debt → drag on GDP.
 * - Good infrastructure → compounding effect.
 * - Stability influences productivity.
 * - Structural multiplier differentiates states.
 * - Events create divergence.
 * - Elections shift ideology.
 *
 *
 * ================================================================
 * DESIGN GOAL
 * ================================================================
 *
 * Move from:
 *      Static Fiscal Simulator
 *
 * Toward:
 *      Dynamic Political-Macroeconomic Engine
 *
 * Where:
 * - States diverge naturally.
 * - Boom-bust cycles occur.
 * - Liquidity feeds production.
 * - Debt constrains expansion.
 * - Ideology alters trajectory.
 *
 * ================================================================
 */


 /**
 * ============================================================
 * FUTURE ROADMAP & CURRENT STATUS — Nigerian Economy Simulation
 * ============================================================
 *
 * ------------------------------------------------------------
 * 1. EVENT ENGINE SYSTEM
 * ------------------------------------------------------------
 *
 * What I Said It Should Be:
 * - Structured event engine with categorized events:
 *   • 2 Balanced events
 *   • 3 Fairly bad events
 *   • 2 Catastrophic events
 *   • 2 Fairly good events
 *   • 1 Very good event
 * - Election cycle every 10 months
 * - Government type switching during elections
 * - Event weighting influenced by stability, debt, or reserves
 * - Potential scaling of event impact using state multiplier
 *
 * What Has Been Done:
 * - Mini specification drafted for event engine behavior
 *
 * What Has NOT Been Done:
 * - Full implementation of categorized event pool
 * - Election timer and trigger logic
 * - Government type switching on election
 * - Event weighting logic
 * - State-multiplier-based event scaling
 *
 * ------------------------------------------------------------
 * 2. GDP GROWTH REBALANCING
 * ------------------------------------------------------------
 *
 * What I Identified:
 * - Cash accumulation can grow disproportionately
 * - GDP does not scale properly with investment spending
 *
 * What I Said I Would Do:
 * - Tie GDP growth directly to investment-related policy spend
 * - Introduce long-term growth mechanics
 * - Possibly introduce a state multiplier affecting efficiency
 *
 * What Has Been Done:
 * - Minor GDP accumulation fixes
 * - Cash dispersion logic corrections
 *
 * What Has NOT Been Done:
 * - Formal growth equation redesign
 * - Investment efficiency multiplier
 * - Long-term capital formation logic
 *
 * ------------------------------------------------------------
 * 3. STATE MULTIPLIER FIELD
 * ------------------------------------------------------------
 *
 * What I Proposed:
 * - Add a multiplier per state affecting:
 *   • GDP growth
 *   • Crash resilience
 *   • Investment efficiency
 *   • Policy effectiveness
 *
 * What Has Been Done:
 * - Concept discussed
 *
 * What Has NOT Been Done:
 * - Field added to StateEconomy
 * - Multiplier integrated into economic formulas
 *
 * ------------------------------------------------------------
 * 4. ENCAPSULATION REFACTOR
 * ------------------------------------------------------------
 *
 * What I Recognized:
 * - Too many public fields in StateEconomy
 * - Need for better mutation control
 *
 * What Has Been Done:
 * - Acknowledged need for improvement
 *
 * What Has NOT Been Done:
 * - Getter/setter migration
 * - Field access restriction
 * - Controlled mutation methods
 *
 * ------------------------------------------------------------
 * 5. STATE INITIALIZER REFACTOR
 * ------------------------------------------------------------
 *
 * What I Said I Would Do:
 * - Move hardcoded state definitions into a dedicated initializer file
 * - Clean up SimulationEngine
 *
 * What Has Been Done:
 * - Some structural cleanup
 * - States file partially reorganized
 *
 * What Has NOT Been Done:
 * - Fully modular initializer system
 * - Clean separation of data and simulation logic
 *
 * ------------------------------------------------------------
 * 6. AI BEHAVIOR EVOLUTION
 * ------------------------------------------------------------
 *
 * What Has Been Done:
 * - Smart policy scoring system (PolicyClass)
 * - Comparator-based ranking
 * - Government type biasing
 * - Probabilistic tier selection (best/mid/worst)
 * - Threshold tuning and balancing
 *
 * What I Considered Adding:
 * - Policy memory (avoid repeating same action consecutively)
 * - Long-term AI strategic behavior
 * - Popularity-based decision modifiers
 * - Election-influenced AI behavior shifts
 *
 * What Has NOT Been Done:
 * - Policy memory system
 * - Long-term adaptive AI strategy
 *
 * ------------------------------------------------------------
 * 7. FEDERAL–STATE STRUCTURAL MODEL
 * ------------------------------------------------------------
 *
 * What Exists:
 * - States as primary economic units
 * - Federal collects oil/forex/VAT
 * - Federal bailouts under crash conditions
 * - Federal reserve updates
 *
 * Potential Expansions:
 * - Clearer separation of productive GDP vs fiscal revenue
 * - Conditional bailout logic based on ideology
 * - Federal stress events
 *
 * ------------------------------------------------------------
 * 8. REPORTING & OUTPUT SYSTEM
 * ------------------------------------------------------------
 *
 * What Exists:
 * - Stepped printing system
 * - Monthly summaries
 * - Ranking display
 *
 * Possible Future Enhancements:
 * - Structured simulation history tracking
 * - Month-by-month archival object
 * - CSV export
 * - Visual analytics integration
 *
 * ------------------------------------------------------------
 * 9. VERSIONING & WORKFLOW (COMPLETED)
 * ------------------------------------------------------------
 *
 * Implemented:
 * - Git repository setup
 * - Tag-based versioning
 * - Structured CHANGELOG.md
 * - Upload script with commit + tag prompts
 * - Admin/dev fast-mode toggle
 *
 * ------------------------------------------------------------
 * OVERALL STATUS
 * ------------------------------------------------------------
 *
 * Core simulation: COMPLETE
 * AI scoring & biasing: COMPLETE
 * Crash & bailout model: COMPLETE
 * Probabilistic AI selection: COMPLETE
 *
 * Event engine: SPECIFIED, NOT FULLY IMPLEMENTED
 * Election cycle: NOT IMPLEMENTED
 * Growth rebalance system: PARTIALLY FIXED, NOT REDESIGNED
 * Encapsulation cleanup: PENDING
 *
 * ============================================================
 */