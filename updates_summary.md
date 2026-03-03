# Nigeria Economy Sim — Today’s Updates & Next Steps (Checklist)

## 1) Major bug fix: tax distortion was killing GDP
**Problem:** taxDistortion was ~10× too large at common tax rates (e.g., 0.30), forcing `growthRate` into the negative clamp each month.

**Fix (current):**
- Linear distortion above threshold with a small slope
- Optional quadratic penalty above very high tax
- Cap distortion to prevent runaway

Example (your current block):
```java
double t = state.taxRate;
if (t > 0.18) taxDistortion = (t - 0.18) * 0.08;
if (t > 0.30) taxDistortion += Math.pow(t - 0.30, 2) * 2.0;
taxDistortion = Math.min(0.02, taxDistortion);
```

**Result:** growth stopped slamming into the cap; macro behavior became sane.

---

## 2) Growth model: what we learned & how to tune
### Key insight
Your monthly growth is basically:
- + (infraEffect + baseGrowth term)  
- − taxDistortion  
- × small multipliers (stability, infraFactor, debtPenalty)

When distortion is scaled correctly, the engine becomes policy-responsive rather than “always capped”.

### Annual targets (useful conversions)
- **8.8% annually** ≈ **0.705% monthly**
- **10% annually** ≈ **0.797% monthly**
- **10.5% annually** ≈ **0.835% monthly**
- Doubling in 5 years ≈ **~15% annually** (too fast if you’re targeting ~10%)

### Design note
Realistic ≠ fun. Pacing should feel responsive monthly. You tuned toward believable growth while keeping the game playable.

---

## 3) Ranking engine: fixes needed (important)
Your current ranking formula has 3 key issues:

1) **Inflation is rewarded**  
You add `(0.10 * s.inflationRate)` which boosts score when inflation rises.  
 Change to a penalty:
- `-0.10 * Math.abs(inflationRate)` or normalize into a 0–1 “low inflation” score.

2) **Population score rewards size for free**  
`popScore` is basically “bigger population = better ranking” which double-counts scale (GDP per cap already measures prosperity).  
Remove popScore or replace with a more meaningful metric (e.g., population growth trend, employment proxy).

3) **Stability can exceed 1.0**  
`stabilityScore = stability/100` but stability can exceed 100.  
Clamp: `Math.min(100, stability)/100`.

Also recommended:
- Change fiscal normalization from `fiscalHealth / maxFiscal` to min–max normalization to avoid sign/weird scaling.
- Add a small **momentum/growthScore** term so rankings reflect recent performance, not just accumulated wealth.

Suggested structure:
- GDP per cap (core)
- Fiscal health (profit/reserve/debt)
- Growth momentum (rolling)
- Stability
- Infrastructure
- Inflation penalty

---

## 4) AI behavior: why it was spamming Infra/Education
Once distortion got fixed, Expert AI rationally converged on the policies with:
- immediate GDP multipliers
- manageable costs
- no cooldown / no diminishing returns

**Conclusion:** AI is fine. Incentives are too clean.
Solution is to add **friction** (not necessarily hard cooldowns).

### Best friction options (in order)
1) **Diminishing returns** on repeated infra/education
2) **Maintenance cost** scaling with infrastructure (ongoing burden)
3) **Policy fatigue** (repeating same policy reduces stability / increases cost)
4) Optional **implementation lag** (policy benefits delayed)
5) Hard cooldown (least elegant—use only if needed)

---

## 5) Add structural asymmetry: StateAttributes (state characteristics)
Goal: stop “universal optimal policy” by making states structurally different.

### Current class (done)
`game.economies.StateAttributes` with getters:
- infraEfficiency
- educationEfficiency
- taxSensitivity
- securitySensitivity
- corruptionFactor
- populationElasticity

### How to use it (next)
Multiply policy effects and/or growth penalties/benefits:
- `infraEffect *= infraEfficiency`
- education GDP bump / productivity gains scaled by `educationEfficiency`
- `taxDistortion *= taxSensitivity`
- security stability gains scaled by `securitySensitivity`
- policySpend “waste” factor using `corruptionFactor` (e.g., only (1−corruption) of spend is effective)
- population response (growth/flight) scaled by `populationElasticity`

Keep multipliers modest (e.g., 0.7–1.3) to avoid recreating runaway gaps.

---

## 6) Add analysis + “history”: AIMemory / Metrics object
Goal: store last-month values, streaks, timers, rolling averages for:
- AI smarter decisions
- cooldown/fatigue logic
- analytics + debugging

### AIMemory class (done)
Fields:
- lastPolicy, samePolicyCount
- lastNominalGdp, lastRealGdp, lastDebt, lastStability
- rollingGrowth12M, rollingInflation12M
- monthsSinceInfra, monthsSinceEducation

Includes:
- updatePolicyTracking(policy)
- incrementPolicyTimers()
- resetInfraTimer(), resetEducationTimer()

### Startup rule
At game start, initialize memory to avoid “empty spikes”:
- lastNominalGdp = current gdp
- lastRealGdp = current realGdp
- lastDebt = current debt
- lastStability = current stability
- lastPolicy = NO_POLICY (sentinel)

### NO_POLICY sentinel (recommended)
- `public static final String NO_POLICY = "NO_POLICY";`
- AI ignores cooldown/streak logic when lastPolicy is NO_POLICY.

### Integration pattern (recommended month loop)
1) AI reads memory from last month → chooses policy  
2) PolicyEngine applies policy  
3) Metrics updater stores:
   - lastPolicy / streak
   - timers
   - last values
   - rolling averages

---

## 7) Basic seeding mechanics (next major step)
### Why seed?
Without seeding, all states converge to the same strategies.
Seeding creates replayability and realistic divergence.

### Minimal viable seeding (recommended start)
Seed 2 layers:
1) **Initial GDP / population differences** (compressed so not extreme)
2) **StateAttributes profile** (structural multipliers)

### GDP & population compression idea
Use weights (real-ish or hand-tuned) and compress gaps:
- `adj = pow(weight, alpha)` where alpha ~ 0.5 (sqrt compression)
- Normalize so average stays same:
  - `seedValue = baseValue * (adj / avgAdj)`

### Mapping StateAttributes to government type
Government type can bias attributes slightly:
- GROWTH_FOCUSED: higher infraEfficiency/educationEfficiency
- SECURITY_ORIENTED: higher securitySensitivity, higher taxSensitivity (or higher maintenance cost)
- SOCIALIST: higher populationElasticity (welfare effects), possibly higher taxSensitivity
- FISCAL_CONSERVATIVE/DEBT_FIGHTER: lower corruptionFactor (optional v1), higher taxSensitivity (optional)

But keep government type as **bias**, not destiny.

---

## 8) Quick “do next” list
1) Update RankingEngine:
   - inflation becomes penalty
   - remove popScore (or replace)
   - clamp stabilityScore
   - fix fiscal normalization
   - add momentum/growth term (optional but recommended)

2) Wire StateAttributes into PolicyEngine:
   - scale infra/edu effects, tax distortion, security effects
   - add corruption “waste” to policySpend effectiveness (optional v1)

3) Wire AIMemory into:
   - AI scoring (fatigue/cooldown)
   - a MetricsEngine updater after each month

4) Add initial seeding:
   - compressed GDP/pop
   - per-state attributes (archetypes or simple table)

5) Re-run 60–120 month Expert AI simulation:
   - check: no single policy dominates
   - check: national growth target ~10–10.5% annual
   - check: variance across states is believable (not all converging, not one archetype always winning)

---

