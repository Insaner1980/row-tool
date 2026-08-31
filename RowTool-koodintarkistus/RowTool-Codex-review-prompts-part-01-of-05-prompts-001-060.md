# RowTool Exhaustive Sequential Code Review and Safe Minimal-Repair Prompts

Codex must process every numbered prompt in order, record each result, make a change only for a verified real defect, validate any correction, and continue automatically.

Every prompt is intentionally self-contained. Each prompt repeats the required `PROJECT.md` context rules, actual-repository evidence standard, hallucination warning, minimal-correction principle, Git-safety restrictions, validation requirements, reporting format, and interruption boundary. No prompt depends on this introduction, a category description, or another prompt for its safe execution.

Finding no issue and making no change is a correct outcome. The document must not be compressed into a generic review. If execution limits prevent completion, Codex must stop at a precise numbered boundary and report the exact last fully completed prompt and all work and validation performed.

## Table of contents

### 1. Repository boundaries, baseline preservation, and audit execution safety

- Prompt 1: Repository root and active worktree resolution
- Prompt 2: Original staged, unstaged, and untracked baseline preservation
- Prompt 3: Nested repositories, submodules, and linked worktree boundaries
- Prompt 4: Repository-local instruction hierarchy and scope
- Prompt 5: Generated files, build outputs, and source ownership
- Prompt 6: Line endings, text encoding, executable bits, and file modes
- Prompt 7: Symlinks, junctions, canonical paths, and path escape
- Prompt 8: External validation wrapper side effects and trust boundary
- Prompt 9: Temporary files, local servers, emulators, and process cleanup
- Prompt 10: Windows PowerShell command correctness and shell portability
- Prompt 11: Diff scope, accidental formatting, and unrelated churn
- Prompt 12: Conflicting sources of truth inside the repository
- Prompt 13: Secrets, credentials, signing material, and sensitive artifacts in the tree
- Prompt 14: No commit, branch switch, rebase, or history mutation
- Prompt 15: Sequential audit interruption, resume boundary, and truthful completion state

### 2. Application identity, Gradle foundation, SDK levels, and generated source integration

- Prompt 16: Gradle module inclusion and project naming
- Prompt 17: Namespace, application ID, source package, and component class alignment
- Prompt 18: Display name, Finnvek identity, and backup application identity
- Prompt 19: Version code, version name, runtime display, and release documents
- Prompt 20: Minimum, compile, and target SDK declarations
- Prompt 21: Java source, target compatibility, Kotlin JVM target, and CI JDKs
- Prompt 22: Gradle wrapper version and distribution configuration
- Prompt 23: AGP, Kotlin, Compose plugin, and KSP compatibility
- Prompt 24: Plugin application scope, aliases, and ordering
- Prompt 25: Version catalog aliases, bundles, and dependency coordinates
- Prompt 26: Plugin repositories and dependency repositories
- Prompt 27: Gradle properties, daemon settings, configuration cache, and build reproducibility
- Prompt 28: Main, unit-test, and Android-test source set wiring
- Prompt 29: Manifest and resource merge priority across build variants
- Prompt 30: KSP generated sources, Room schema export, and incremental build integration

### 3. Build variants, release packaging, dependencies, CI, and quality-tool evidence

- Prompt 31: Debug and release build type behavior
- Prompt 32: R8 code shrinking and runtime reachability
- Prompt 33: Resource shrinking and required asset retention
- Prompt 34: Packaging options, duplicate resources, and native libraries
- Prompt 35: Release signing boundary and absence of committed key material
- Prompt 36: Resolved dependency graph conflicts and duplicate versions
- Prompt 37: Accidental networking, Firebase, analytics, advertising, billing, or crash SDK capability
- Prompt 38: Transitive manifest permissions, components, and authorities
- Prompt 39: Dependency declarations, scopes, and unused capability risk
- Prompt 40: Primary Android CI workflow parity with documented local checks
- Prompt 41: CodeQL workflow build mode, JDK 17, and analyzed language scope
- Prompt 42: ktlint blocking task wiring and scope
- Prompt 43: Detekt configuration, Compose rules, ignoreFailures, and report interpretation
- Prompt 44: Android lint, release checks, and Google security lint rules
- Prompt 45: Sonar, JaCoCo exclusions, wrapper routing, and evidence semantics

### 4. Application bootstrap, dependency construction, activity lifecycle, and platform security

- Prompt 46: Application class declaration and manifest instantiation
- Prompt 47: AppContainer lifetime and single-instance ownership
- Prompt 48: Room construction order, configuration, and application context use
- Prompt 49: Preferences DataStore construction and singleton access
- Prompt 50: Explicit ViewModel factory mapping and dependency completeness
- Prompt 51: Bootstrap exception visibility and recoverability
- Prompt 52: MainActivity `onCreate` ordering and Compose content setup
- Prompt 53: SplashScreen installation, keep condition, and exit behavior
- Prompt 54: Edge-to-edge configuration and content inset ownership
- Prompt 55: Status and navigation bar icon appearance across themes
- Prompt 56: Theme state availability before first composition
- Prompt 57: Configuration changes and Activity recreation safety
- Prompt 58: Process death and cold-process reconstruction
- Prompt 59: Keep-screen-awake window flag lifecycle
- Prompt 60: Obscured-window touch filtering and tapjacking boundary

### 5. Navigation routes, startup selection, back-stack behavior, and destination transitions

- Prompt 61: Route constants and navigation pattern agreement
- Prompt 62: Project ID URI encoding when building counter routes
- Prompt 63: Counter argument decoding and lookup identity
- Prompt 64: Malformed or absent counter route argument handling
- Prompt 65: Direct counter launch for a missing project
- Prompt 66: Direct counter launch for an archived project
- Prompt 67: Stored last-active project validation against Room
- Prompt 68: Most-recently-updated active fallback ordering
- Prompt 69: Startup behavior when there are no active projects
- Prompt 70: Automatic navigation to a newly created project
- Prompt 71: Startup counter back-stack clearing
- Prompt 72: Counter-to-Projects back navigation
- Prompt 73: Archiving the currently displayed or last-active project
- Prompt 74: Deleting the currently displayed or last-active project
- Prompt 75: Duplicate navigation effects, replay, and collector restart

### 6. Route and Content architecture, immutable state, effects, coroutines, and ViewModel ownership

- Prompt 76: Route and Content responsibility boundary
- Prompt 77: No repository or database resolution from rendering composables
- Prompt 78: Lifecycle-aware StateFlow collection in Routes
- Prompt 79: Immutable UI state exposure and mutation containment
- Prompt 80: StateFlow initial values and loading or empty-state distinction
- Prompt 81: `stateIn` and `shareIn` scope, sharing policy, and upstream lifetime
- Prompt 82: Buffered Channel capacity and overflow behavior
- Prompt 83: One-shot effect separation from persistent state
- Prompt 84: `rememberUpdatedState` around long-lived effect collectors
- Prompt 85: LaunchedEffect, DisposableEffect, and SideEffect key correctness
- Prompt 86: ViewModelScope cancellation and operation lifetime
- Prompt 87: Dispatcher use for Room, DataStore, JSON, and file I/O
- Prompt 88: Exception propagation and structured concurrency
- Prompt 89: Rapid repeated UI actions and reentrancy control
- Prompt 90: Action bundle identity, freshness, and stale lambda capture

### 7. Domain identity, project validation, numeric invariants, and result semantics

- Prompt 91: Project ID generation and persisted identity stability
- Prompt 92: Project name trimming at every authoritative write path
- Prompt 93: Blank project name rejection
- Prompt 94: Maximum 60 Unicode code point name limit
- Prompt 95: Persisted counter-unit decoding fallback for unknown database values
- Prompt 96: Strict rejection of unknown counter units in imported backups
- Prompt 97: Counter count lower bound of zero
- Prompt 98: Counter count upper bound of 999,999
- Prompt 99: Start value restricted to zero or one
- Prompt 100: Optional target count presence and range
- Prompt 101: Optional repeat length presence and range
- Prompt 102: Cross-field count, start, target, and repeat consistency
- Prompt 103: Archived project immutability invariant
- Prompt 104: Timestamp validity and update semantics
- Prompt 105: Structured repository result and error semantics

### 8. Room database schema, entities, mappers, DAO queries, and transaction boundaries

- Prompt 106: Database filename and schema version identity
- Prompt 107: Projects entity table name, columns, and nullability
- Prompt 108: Project primary key and identity conflict behavior
- Prompt 109: Projects active/archive ordering index
- Prompt 110: Counter-history entity columns and change-reason storage
- Prompt 111: History foreign key and cascade deletion
- Prompt 112: History projectId and ID index for newest lookup
- Prompt 113: Committed Room schema JSON consistency and reproducibility
- Prompt 114: Counter-unit and change-reason storage conversion
- Prompt 115: Domain-to-entity and entity-to-domain round-trip fidelity
- Prompt 116: Active-project DAO query filtering and ordering
- Prompt 117: Archived-project DAO query filtering and ordering
- Prompt 118: Single-project lookup and active-only lookup semantics
- Prompt 119: DAO insert, update, and conflict strategy correctness
- Prompt 120: Database transactions, callbacks, and migration boundary

### 9. CounterRepository serialization, mutation atomicity, boundaries, and concurrent callers

- Prompt 121: Single effective Mutex instance for all counter mutations
- Prompt 122: Increment read-check-write transaction
- Prompt 123: Decrement read-check-write transaction
- Prompt 124: Direct manual-set transaction and same-value handling
- Prompt 125: Reset transaction to persisted start value
- Prompt 126: No-op boundary mutations and history suppression
- Prompt 127: Archived-project guard inside each mutation transaction
- Prompt 128: Missing-project guard and structured result
- Prompt 129: Lost-update prevention with stale UI state
- Prompt 130: History row field correctness for every accepted mutation
- Prompt 131: Project `updatedAt` change within mutation atomicity
- Prompt 132: Mutation result mapping from DAO outcomes
- Prompt 133: Concurrent increment burst exactness
- Prompt 134: Mutation racing with archive, delete, or import replacement
- Prompt 135: Cancellation and exception rollback during mutation

### 10. Undo history, retention, archive and delete semantics, and project ordering

- Prompt 136: Newest history row selection for undo
- Prompt 137: Undo atomically restores previous count and consumes history
- Prompt 138: Persistent multi-step undo order
- Prompt 139: Undo with no available history
- Prompt 140: Undo after process restart and database reopen
- Prompt 141: Newest 100 history rows retention rule
- Prompt 142: History retention isolation per project
- Prompt 143: Project deletion cascades all and only its history
- Prompt 144: Replacement import clears old history before new project set becomes visible
- Prompt 145: Archive preserves history while blocking mutation
- Prompt 146: Restore re-enables mutation without altering count or history
- Prompt 147: Rename preserves counter state and history
- Prompt 148: Editing target, repeat, unit, or start preserves unrelated state
- Prompt 149: Delete confirmation target identity under list changes
- Prompt 150: UpdatedAt ordering after create, edit, count, archive, and restore

### 11. Preferences DataStore keys, settings propagation, and startup consistency repair

- Prompt 151: Preferences file name and single key namespace
- Prompt 152: Theme mode key, default, and safe enum decoding
- Prompt 153: Haptic feedback key and default true
- Prompt 154: Keep-screen-awake key and default true
- Prompt 155: Last-active project key absence and blank-value handling
- Prompt 156: DataStore singleton and file corruption behavior
- Prompt 157: Theme write persistence and reactive propagation
- Prompt 158: Haptic toggle persistence and Counter propagation
- Prompt 159: Keep-awake toggle persistence and immediate window update
- Prompt 160: Writing last-active project on successful open
- Prompt 161: Clearing matching last-active ID on archive
- Prompt 162: Clearing matching last-active ID on delete
- Prompt 163: Database commit followed by preference-write failure
- Prompt 164: Startup repair of stale last-active preference
- Prompt 165: Preferences Flow exceptions, fallback state, and user messaging

### 12. JSON backup export schema, snapshot integrity, encoding, and document creation

- Prompt 166: Backup root field contract
- Prompt 167: Exported schemaVersion value 1
- Prompt 168: Exported application identity literal RowTool
- Prompt 169: ExportedAt epoch-millisecond semantics
- Prompt 170: Exported project field completeness
- Prompt 171: Intentional exclusion of counter undo history
- Prompt 172: Intentional exclusion of DataStore settings
- Prompt 173: UTF-8 encoding and Unicode project-name fidelity
- Prompt 174: JSON syntax, escaping, and finite numeric output
- Prompt 175: Consistent export snapshot under concurrent mutations
- Prompt 176: Export ordering determinism and semantic irrelevance
- Prompt 177: Export size and memory behavior with up to 1,000 projects
- Prompt 178: Create-document contract, MIME type, and suggested filename
- Prompt 179: Output stream acquisition, write, flush, and close
- Prompt 180: Export cancellation, failure messaging, and no false success

### 13. JSON backup import parsing, limits, identity checks, and complete pre-mutation validation

- Prompt 181: Five MiB import size limit enforcement strategy
- Prompt 182: Import byte-count boundary and early termination
- Prompt 183: Malformed JSON rejection and exception containment
- Prompt 184: Required root fields and type validation
- Prompt 185: Unknown JSON keys accepted for forward-compatible optional additions
- Prompt 186: Unsupported backup schema version rejection
- Prompt 187: Wrong application identity rejection
- Prompt 188: Maximum 1,000 imported projects
- Prompt 189: Blank imported project IDs
- Prompt 190: Duplicate imported project IDs
- Prompt 191: Imported project name validation
- Prompt 192: Imported numeric field validation
- Prompt 193: Unknown imported counter-unit rejection
- Prompt 194: Imported booleans, timestamps, and nullable field types
- Prompt 195: Complete payload validation before any database or preference mutation

### 14. Import preview, replacement transaction, last-active resolution, and SAF input handling

- Prompt 196: Preview active and archived project counts
- Prompt 197: Preview identity bound to the exact validated payload
- Prompt 198: Explicit confirmation before replacement
- Prompt 199: Stale preview after configuration change or process recreation
- Prompt 200: Cancel or dismiss confirmation causes zero mutation
- Prompt 201: Replacement transaction clears and inserts projects atomically
- Prompt 202: Rollback on inserted-project failure
- Prompt 203: History clearing in the same replacement transaction
- Prompt 204: Last-active project resolution after committed import
- Prompt 205: Preference update occurs only after database commit
- Prompt 206: Preference failure after successful replacement
- Prompt 207: ContentResolver input stream null and open failure
- Prompt 208: URI grant lifetime and no broad storage permission
- Prompt 209: Repeated import selection, concurrent operations, and reentrancy
- Prompt 210: Import error messages and privacy-preserving logs

### 15. Projects screen state, list rendering, editor validation, and project actions

- Prompt 211: Active project list reactive updates and ordering
- Prompt 212: Archived section expansion state and data changes
- Prompt 213: Projects empty, active-only, archived-only, and mixed states
- Prompt 214: Lazy list item keys and identity preservation
- Prompt 215: Create-dialog initial values and clean reopening
- Prompt 216: Edit-dialog field population from current project
- Prompt 217: Shared ProjectEditorDialog create and edit state isolation
- Prompt 218: Project name input and 60-code-point feedback
- Prompt 219: Numeric text parsing without crash or locale ambiguity
- Prompt 220: Optional target and repeat blank-to-null semantics
- Prompt 221: Rows versus rounds selector state and accessibility
- Prompt 222: Successful creation and exactly-once auto-open
- Prompt 223: Opening a project from a stale list row
- Prompt 224: Archive and restore actions from Projects
- Prompt 225: Delete confirmation and stale project handling from Projects

### 16. Counter screen state, mutations, dialogs, progress, repeat display, and rapid interaction

- Prompt 226: Authoritative project Flow collection for the open counter
- Prompt 227: Project name and row or round label correctness
- Prompt 228: Plus control enabled state at upper bound
- Prompt 229: Minus control enabled state at zero
- Prompt 230: Undo availability and enabled semantics
- Prompt 231: Archived counter read-only state
- Prompt 232: Direct set-count dialog initialization and parsing
- Prompt 233: Reset confirmation and current start value
- Prompt 234: Editing project metadata from Counter
- Prompt 235: Archiving from Counter and destination transition
- Prompt 236: Deleting from Counter and destination transition
- Prompt 237: Target progress calculation and clamping
- Prompt 238: Repeat position calculation
- Prompt 239: Completed-repeat count and combined target or repeat display
- Prompt 240: Rapid taps, press animation, and exactly-once mutation dispatch

### 17. Haptic feedback, Settings UI, launchers, operation state, and app information

- Prompt 241: Haptic master toggle respected for every counter effect
- Prompt 242: Light haptic only for accepted ordinary increment and decrement
- Prompt 243: No haptic for rejected, missing, archived, or boundary no-op actions
- Prompt 244: Stronger haptic at repeat boundary
- Prompt 245: Target-reached haptic condition
- Prompt 246: Repeat and target boundary precedence and duplicate suppression
- Prompt 247: Haptic API compatibility and graceful hardware absence
- Prompt 248: Theme option control state and persistence
- Prompt 249: Haptic and keep-awake toggle UI consistency
- Prompt 250: Keep-awake setting explanation and active-screen semantics
- Prompt 251: Export and import launcher ownership in Settings Route
- Prompt 252: Import preview and destructive confirmation UI clarity
- Prompt 253: Backup operation progress, button enablement, and retry
- Prompt 254: App version and Finnvek information accuracy
- Prompt 255: Privacy and paid-download business-model summaries

### 18. Responsive Compose layout, accessibility semantics, assets, and localization integrity

- Prompt 256: Screen content width cap and phone-side padding
- Prompt 257: Project editor maximum width, height, scrolling, and IME
- Prompt 258: Responsive counter count scaling by digits and width
- Prompt 259: Extreme font-scale behavior
- Prompt 260: Landscape and short-height layout behavior
- Prompt 261: Tablet and large-window behavior
- Prompt 262: System bar, scaffold, navigation, and IME inset composition
- Prompt 263: Minimum 48 dp interactive touch targets
- Prompt 264: Button roles, accessible names, and state descriptions
- Prompt 265: Focus order, keyboard traversal, and dialog focus restoration
- Prompt 266: Information and state not conveyed by color alone
- Prompt 267: Counter WebP root originals and packaged-copy binary integrity
- Prompt 268: Counter image rendering, no tint, ContentScale.Fit, and press feedback
- Prompt 269: Localized resource set parity and locale declaration
- Prompt 270: Format placeholders, plurals, number formatting, and direction-safe text

### 19. Theme system, recomposition, resource efficiency, privacy boundary, and security hardening

- Prompt 271: Semantic light and dark color mapping
- Prompt 272: Fixed theme policy and absence of dynamic color
- Prompt 273: Typography fallback and absence of missing Outfit asset
- Prompt 274: Shared shapes, spacing, and dimension source consistency
- Prompt 275: Compose recomposition scope and unstable state reads
- Prompt 276: List item identity and derived state efficiency
- Prompt 277: Press and visibility animation lifetime
- Prompt 278: Room Flow query and mapping efficiency
- Prompt 279: Backup file I/O memory bounds and stream release
- Prompt 280: Coroutine, Context, Activity, and NavController reference leaks
- Prompt 281: Merged manifest no-permission boundary
- Prompt 282: System backup and device-transfer exclusions
- Prompt 283: No cleartext traffic, networking client, or hidden network path
- Prompt 284: Secrets, production logging, and local-data privacy
- Prompt 285: Tapjacking boundary and untrusted document-provider input together

### 20. Host and device tests, coverage evidence, release builds, Play documents, and handoff readiness

- Prompt 286: Pure domain and ProjectValidation test completeness and determinism
- Prompt 287: Robolectric Room schema, foreign-key, and transaction tests
- Prompt 288: CounterRepository concurrency, boundary, history, and retention tests
- Prompt 289: Preferences DataStore persistence and failure tests
- Prompt 290: Backup codec, size, validation, round-trip, and atomicity tests
- Prompt 291: ViewModel state and one-shot effect tests
- Prompt 292: Projects screen isolated Compose semantics tests
- Prompt 293: Counter screen isolated Compose semantics tests
- Prompt 294: Full-activity navigation, startup, recreation, and persistence tests
- Prompt 295: Android-test compilation versus execution evidence
- Prompt 296: CI results, local results, and baseline failure separation
- Prompt 297: JaCoCo and Sonar coverage exclusions and claim accuracy
- Prompt 298: Release APK and AAB build, shrinking, and smoke readiness
- Prompt 299: Play listing, Data Safety, privacy policy, and app behavior consistency
- Prompt 300: Release checklist, signing, versioning, translation, and external-step boundary

### 21. Final repository-wide verification

- Prompt 301: Final repository-wide verification and truthful audit completion report

# 1. Repository boundaries, baseline preservation, and audit execution safety

## Prompt 1: Repository root and active worktree resolution

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Repository root and active worktree resolution

Establish that every inspection and possible edit is operating in the intended RowTool repository and active worktree, not a similarly named directory, parent repository, nested checkout, or stale terminal location.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Resolve the top-level path with Git and compare it with the current working directory before reading or changing files.
2. Inspect branch or detached-HEAD state and linked worktrees only to understand location and ownership, without switching anything.
3. Confirm that paths referenced by Gradle, scripts, and documentation resolve inside the intended repository unless an explicit external validation tool is documented.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A command runs in a parent, sibling, nested, or obsolete RowTool checkout.
2. An edit is applied to the wrong linked worktree while the expected worktree remains unchanged.
3. A validation script silently targets a different project path than the repository under review.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Record the resolved root, current worktree, and status baseline. No build is required unless a correction is actually made.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 2. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
## Prompt 2: Original staged, unstaged, and untracked baseline preservation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Original staged, unstaged, and untracked baseline preservation

Capture and preserve all work that existed before this audit so later Codex changes can be distinguished from user work at every numbered step.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Record staged, unstaged, and untracked paths before the first modification.
2. Use diffs and timestamps only as evidence, not as permission to rewrite or discard existing work.
3. Track Codex-created changes incrementally so a later prompt does not misclassify earlier audit changes as unrelated user work.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A pre-existing modified file is reformatted or overwritten as part of an unrelated correction.
2. An untracked user file is deleted, moved, or added to a broad generated-file cleanup.
3. Later reporting claims all current changes were produced by Codex even though the baseline proves otherwise.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use `git status --short`, `git diff`, and `git diff --cached` as read-only evidence. Do not stage files.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 3. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 3: Nested repositories, submodules, and linked worktree boundaries

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Nested repositories, submodules, and linked worktree boundaries

Determine whether nested Git metadata, submodules, or linked worktrees exist and prevent cross-boundary review or edits unless the current task and repository configuration explicitly require them.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `.gitmodules`, nested `.git` entries, and worktree metadata if present.
2. Check whether project scripts recurse into sibling or nested repositories.
3. Treat each independently versioned repository as a separate ownership boundary.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A recursive formatter or scanner modifies a nested repository that was not part of RowTool.
2. A submodule working tree is treated as ordinary source and overwritten.
3. A linked-worktree path is mistaken for generated output and removed or altered.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. No submodule update, checkout, or initialization is permitted. Report unresolved boundaries rather than changing them.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 4. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 4: Repository-local instruction hierarchy and scope

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Repository-local instruction hierarchy and scope

Find and apply repository-local instructions such as `AGENTS.md`, README guidance, tool configuration, or directory-scoped rules without allowing them to override the user request or silently expand scope.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Locate instruction files from the root through relevant source directories.
2. Determine which instructions apply to each inspected file and whether instructions conflict.
3. Verify command examples against actual scripts and configuration before relying on them.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A directory-specific instruction is missed, causing an invalid edit or validation command.
2. A stale README command is treated as authoritative despite conflicting build configuration.
3. An instruction intended for a different project or directory is applied globally.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. If instructions conflict materially and intent is not verifiable, make no speculative change and report the conflict.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 5. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 5: Generated files, build outputs, and source ownership

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Generated files, build outputs, and source ownership

Distinguish checked-in source and authoritative generated artifacts from disposable local build output so the audit does not edit the wrong representation or commit noise.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `.gitignore`, Gradle output locations, Room schema export, generated KSP sources, reports, and packaged resources.
2. Identify generated artifacts that are intentionally versioned, especially the exported Room schema.
3. Trace whether a generated file must be regenerated from source or whether it is itself an authoritative committed artifact.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A generated KSP file is patched instead of its source declaration.
2. An authoritative Room schema JSON change is omitted after a real schema change.
3. Disposable build reports or binaries are added or modified as visible work.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. After any correction, inspect the file-level diff and ensure no build output or report artifact entered the change set unintentionally.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 6. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 6: Line endings, text encoding, executable bits, and file modes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Line endings, text encoding, executable bits, and file modes

Verify that edits preserve repository conventions for UTF-8 text, line endings, scripts, and file modes without creating whole-file churn or platform-specific breakage.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `.gitattributes`, existing line endings, BOM usage, and executable bits for scripts touched by a justified correction.
2. Compare actual semantic diff with file-size and line-count changes.
3. Check that localized resources and JSON remain valid UTF-8.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A one-line edit rewrites an entire file due to CRLF/LF conversion.
2. A PowerShell or shell script loses the file mode required by its environment.
3. A resource gains an invalid BOM or encoding that breaks Android parsing.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use `git diff --check` and a focused parser or build task when an edited format has a parser. Do not normalize unrelated files.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 7. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 7: Symlinks, junctions, canonical paths, and path escape

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Symlinks, junctions, canonical paths, and path escape

Check whether repository paths, scripts, or test fixtures use symlinks or Windows junctions that could redirect reads or writes outside the intended repository.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Resolve canonical destinations for unusual linked paths before allowing a write.
2. Inspect script path joins and deletion or copy targets for traversal or path escape.
3. Verify that backup tests and generated-output helpers do not operate on user data directories.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A cleanup path follows a junction into another project or user directory.
2. A relative path such as `..` escapes the repository during copy, delete, or report creation.
3. A validation helper writes to a canonical path different from the displayed path.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Do not remove or recreate links. If a linked target is unsafe or unavailable, skip the operation and report it.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 8. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 8: External validation wrapper side effects and trust boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: External validation wrapper side effects and trust boundary

Audit repository wrapper scripts that call tools outside the repository, especially `tools/*.ps1` and `C:\Dev\Android-check`, before treating a wrapper invocation as safe evidence.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Read the local wrapper and determine exact arguments, project ID, working directory, output paths, network behavior, and mutation modes.
2. Distinguish a plan-only routing check from an executed scan.
3. Verify report artifacts rather than trusting a zero exit code when the documented tool semantics require report inspection.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A wrapper plan is reported as a completed clean scan.
2. An external script modifies source, dependencies, or files in another project.
3. A missing external tool is misreported as a successful validation.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Prefer plan-only execution first. Run a real wrapper only when its behavior is understood, safe, relevant, and available.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 9. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 9: Temporary files, local servers, emulators, and process cleanup

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Temporary files, local servers, emulators, and process cleanup

Prevent validation from leaving repository temp files, Gradle daemons beyond normal behavior, local servers, emulators, or helper processes that interfere with other projects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Identify commands that start long-lived processes or devices.
2. Use existing project conventions for temporary directories and cleanup.
3. After a command started specifically for this audit, confirm that only audit-owned temporary resources are stopped or removed.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A validation task leaves an emulator or server running and changes later device selection.
2. Temporary exported backups are left inside tracked source directories.
3. Cleanup kills a process owned by the user or another development task.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Do not kill broad process classes. Clean up only resources demonstrably created by this prompt.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 10. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 10: Windows PowerShell command correctness and shell portability

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Windows PowerShell command correctness and shell portability

Verify that commands used on the documented Windows environment use correct PowerShell syntax, quoting, path separators, and Gradle wrapper invocation without assuming Bash behavior.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare documented PowerShell commands with actual wrapper filenames and task names.
2. Quote paths containing spaces and avoid shell operators unsupported by the active shell.
3. When presenting or running an alternative shell command, state the shell and preserve equivalent behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A Bash-only command fails or partially executes in PowerShell.
2. A path is parsed incorrectly because of unescaped backslashes, parentheses, or spaces.
3. A command invokes system Gradle instead of the repository wrapper.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use `./gradlew` or `gradlew.bat` according to the active environment and record the exact command actually run.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 11. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 11: Diff scope, accidental formatting, and unrelated churn

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Diff scope, accidental formatting, and unrelated churn

Ensure every changed hunk is directly necessary for a confirmed issue in the current prompt and that no formatter, import optimizer, or IDE action produced unrelated churn.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect the diff immediately after each correction, not only at the end.
2. Compare changed imports, whitespace, resource ordering, and generated files with the bounded root cause.
3. Revert only Codex-created accidental edits through a precise manual patch, never by destructive Git restoration.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. An autoformatter changes dozens of unrelated files.
2. Imports or resources are reordered without relation to the fix.
3. A broad search-and-replace changes user-visible text or APIs outside the verified defect.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use file-specific diffs and `git diff --check`. The correction is not complete until accidental Codex churn is removed safely.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 12. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 12: Conflicting sources of truth inside the repository

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Conflicting sources of truth inside the repository

Identify and resolve only proven inconsistencies among code, Gradle configuration, generated schemas, tests, and release documents while respecting that `PROJECT.md` can be stale and must not be edited.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Determine which source is authoritative for the specific fact, such as version catalog for dependency versions or `app/build.gradle.kts` for SDK levels.
2. Trace whether duplicate values are runtime inputs, tests, display-only documentation, or intentionally independent.
3. Do not force all documents to repeat identical details unless a real contradiction affects behavior or handoff accuracy.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A stale duplicate constant causes import/export identity mismatch.
2. A test asserts an obsolete configuration and gives false confidence.
3. A documentation-only difference is treated as permission to change working code.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Validate the actual consumer of any corrected value. Leave ambiguous working behavior unchanged.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 13. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 13: Secrets, credentials, signing material, and sensitive artifacts in the tree

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Secrets, credentials, signing material, and sensitive artifacts in the tree

Inspect the current repository for accidental secrets or signing material relevant to RowTool without fabricating a need for credentials or adding a signing configuration.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Check tracked and current untracked configuration for obvious keys, passwords, keystores, service files, or private URLs.
2. Verify ignore rules and release documentation boundaries.
3. Distinguish placeholders and public identifiers from actual secrets.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A keystore or password file is tracked or about to enter the diff.
2. A local properties file with machine paths or secrets is committed.
3. A scanner warning on a harmless test string is treated as a confirmed secret without context.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Do not rotate, delete, or expose a secret. Report a confirmed exposure and make only a safe repository-internal containment change when unambiguous.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 14. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 14: No commit, branch switch, rebase, or history mutation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: No commit, branch switch, rebase, or history mutation

Verify that the audit process and project wrappers do not commit, stage, switch branches, amend, rebase, or otherwise alter Git history without explicit user instruction.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect scripts for hidden Git write operations before running them.
2. Monitor branch and status when a tool claims to prepare a handoff.
3. Keep all Codex corrections as ordinary working-tree changes.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A wrapper commits generated changes automatically.
2. A tool checks out a branch or resets files as part of cleanup.
3. The audit stages files and obscures the original staged baseline.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use only read-only Git commands except precise working-tree file edits required by confirmed defects.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 15. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 15: Sequential audit interruption, resume boundary, and truthful completion state

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Sequential audit interruption, resume boundary, and truthful completion state

Make the long audit resumable without skipped prompts, duplicate untracked corrections, or false claims that all numbered areas were processed.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Start from the Git-resolved repository root. Inspect repository-local instruction files, worktree metadata, scripts, generated-output locations, and the original `git status --short` baseline before considering any edit.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Maintain the exact last fully completed prompt number and the next or incomplete prompt.
2. Record every file changed and validation run before stopping for an execution limit.
3. On resume, re-read `PROJECT.md`, re-establish Git baseline context, and verify earlier audit changes before continuing.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Codex silently skips prompts to reach the final report.
2. A partially inspected prompt is marked complete.
3. A resumed run repeats a correction without recognizing the existing audit change.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. No code validation is required solely for bookkeeping, but the status and diff record must be accurate and reproducible.
2. Use read-only Git inspection commands and `git diff --check` where relevant. Run no destructive Git command. If a repository script is examined, prefer a documented plan-only or dry-run mode before any execution and verify its actual side effects.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 16. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 2. Application identity, Gradle foundation, SDK levels, and generated source integration

## Prompt 16: Gradle module inclusion and project naming

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Gradle module inclusion and project naming

Verify that the Gradle settings define the intended RowTool build, include module `:app` exactly once, and do not reference stale or missing modules.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect project name, plugin management, dependency resolution, and module includes.
2. Confirm every included project directory exists and belongs to RowTool.
3. Check CI and wrapper commands for the same module path.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. `:app` is omitted, duplicated, or redirected to the wrong directory.
2. A stale module include breaks configuration or CI.
3. Scripts invoke a module name that does not exist.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run a configuration or `:app:tasks` command only if needed to prove a correction.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 17. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 17: Namespace, application ID, source package, and component class alignment

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Namespace, application ID, source package, and component class alignment

Verify that `com.finnvek.rowtool` is consistently used where Android namespace, application ID, source packages, generated classes, tests, and manifest component resolution require it.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `app/build.gradle.kts`, package declarations, manifest class names, test packages, and Room schema path.
2. Distinguish namespace from user-visible app name and backup identity.
3. Trace references to generated `R`, `BuildConfig`, and application or activity classes.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Manifest cannot instantiate `MainActivity` or the Application because package resolution is wrong.
2. Generated sources use a different namespace and compilation fails.
3. Changing application ID would sever installed-app data continuity without necessity.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Compile the affected variant and inspect the merged manifest if any identity correction is made.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 18. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 18: Display name, Finnvek identity, and backup application identity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Display name, Finnvek identity, and backup application identity

Verify that the user-visible RowTool name, Finnvek publisher text, and backup field `application: "RowTool"` are correct in their distinct contexts and not accidentally conflated.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect string resources, Settings app information, backup codec/models, tests, and Play-facing documents.
2. Trace export and import identity comparison end to end.
3. Check that localization does not translate the literal backup identity when it must remain stable.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Export emits one application identity while import requires another.
2. A localized display string is reused as a machine-readable backup identifier.
3. The app displays an incorrect publisher or product name because of stale hard-coding.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused backup identity tests and a resource build if changed.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 19. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 19: Version code, version name, runtime display, and release documents

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Version code, version name, runtime display, and release documents

Verify that versionCode 1 and versionName 1.0.0 are sourced and displayed consistently where accuracy matters, without incrementing either during review.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect build configuration, any BuildConfig or PackageInfo access, Settings display, tests, and release checklist.
2. Identify stale hard-coded version text.
3. Distinguish a planned future version in documentation from the current build value.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Settings shows a version different from the installed build.
2. Release documentation instructs upload with an obsolete version code.
3. A test passes only because it duplicates the same stale literal.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Build or run focused version-display tests after a justified correction. Never alter version values solely for cleanliness.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 20. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 20: Minimum, compile, and target SDK declarations

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Minimum, compile, and target SDK declarations

Verify that minSdk 29, compileSdk 37, and targetSdk 37 are declared in the authoritative build configuration and are compatible with manifest, resources, and code paths.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Android configuration, version catalog only where relevant, lint baselines, and API usages.
2. Check source-set or flavor overrides if they exist.
3. Verify documentation and CI use an installed Platform 37 without assuming environment availability.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A source-set override produces a different SDK level than expected.
2. Code calls a newer API without a valid minSdk path or guard.
3. CI installs or compiles against a different platform and masks local failure.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Android lint or compilation for any SDK-related correction and inspect actual API guard behavior.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 21. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 21: Java source, target compatibility, Kotlin JVM target, and CI JDKs

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Java source, target compatibility, Kotlin JVM target, and CI JDKs

Verify that Java source/target and Kotlin JVM target 17 align with AGP, generated code, unit tests, and the deliberate JDK 21 primary CI and JDK 17 CodeQL environments.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect compile options, Kotlin compiler options, Gradle toolchain declarations, workflows, and local wrapper scripts.
2. Check for per-task target overrides.
3. Verify bytecode target consistency rather than assuming the running JDK determines it.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Java and Kotlin emit incompatible bytecode targets.
2. A task works on JDK 21 but fails on the documented JDK 17 CodeQL build.
3. A local toolchain override silently uses an unsupported JDK.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run the smallest compile tasks needed under available JDKs and report untested environment combinations honestly.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 22. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 22: Gradle wrapper version and distribution configuration

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Gradle wrapper version and distribution configuration

Verify that wrapper files consistently select Gradle 9.7.1 and are complete, valid, and repository-owned.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `gradle-wrapper.properties`, wrapper scripts, and wrapper JAR presence.
2. Check distribution URL, checksum if configured, and script references.
3. Ensure documentation invokes the wrapper rather than a global Gradle installation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Wrapper properties and scripts refer to different or missing distributions.
2. A malformed URL or absent wrapper file prevents reproducible builds.
3. A proposed audit change upgrades Gradle without a verified defect.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use `gradlew.bat --version` only if safe and available. Do not regenerate or upgrade the wrapper without necessity.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 23. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 23: AGP, Kotlin, Compose plugin, and KSP compatibility

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: AGP, Kotlin, Compose plugin, and KSP compatibility

Verify the declared AGP 9.3.2, Kotlin and Compose plugin 2.4.10, and KSP 2.3.11 integration against the actual build logic and generated Room sources.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect aliases and plugin declarations in the version catalog and build files.
2. Check KSP configurations and generated source consumption.
3. Use actual build errors or official compatibility encoded by the build, not a speculative preference for newer versions.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A plugin alias resolves to the wrong plugin ID or version.
2. KSP does not run for the required variant and Room implementation is missing.
3. Duplicate Compose compiler configuration conflicts with the Kotlin plugin.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `:app:kspDebugKotlin` and a focused compile after any confirmed plugin wiring correction.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 24. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 24: Plugin application scope, aliases, and ordering

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Plugin application scope, aliases, and ordering

Verify that Android, Kotlin, Compose, serialization, KSP, and quality plugins are applied only where needed and in a configuration order that actually works.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect root `apply false` declarations and module plugin blocks.
2. Trace plugin-provided extensions before they are configured.
3. Check for duplicate legacy and alias application.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A plugin extension is configured before the plugin is applied.
2. The same plugin is applied twice through two IDs.
3. A root plugin leaks task behavior into unrelated modules.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Gradle configuration and the smallest task owned by any corrected plugin.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 25. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 25: Version catalog aliases, bundles, and dependency coordinates

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Version catalog aliases, bundles, and dependency coordinates

Verify that `gradle/libs.versions.toml` aliases resolve to the intended libraries and that build files do not bypass or contradict the authoritative catalog without reason.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect version references, library coordinates, plugin aliases, and bundles.
2. Find unused or duplicate aliases only when they cause real confusion or dependency duplication.
3. Confirm that test and production dependencies use the intended artifacts.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. An alias points to the wrong group or artifact.
2. Two aliases bring incompatible versions of the same library.
3. A missing version reference prevents catalog parsing.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use dependency or configuration reports and compile tasks after a real catalog correction. Do not update versions speculatively.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 26. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 26: Plugin repositories and dependency repositories

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Plugin repositories and dependency repositories

Verify that plugin and dependency repositories are sufficient, deterministic, and limited to what the current RowTool dependencies actually require.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `pluginManagement`, `dependencyResolutionManagement`, repository modes, and module-level repositories.
2. Check repository ordering only where resolution behavior is affected.
3. Confirm no credentialed or obsolete private repository is required.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A required plugin cannot resolve in CI because its repository is missing.
2. Module repositories bypass the intended repository mode.
3. An unnecessary insecure repository expands supply-chain risk.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Gradle dependency resolution only when needed and avoid network-dependent claims if dependencies are unavailable.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 27. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 27: Gradle properties, daemon settings, configuration cache, and build reproducibility

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Gradle properties, daemon settings, configuration cache, and build reproducibility

Inspect project Gradle properties for settings that alter memory, parallelism, warnings, AndroidX, Kotlin, or configuration-cache behavior in a way that affects correctness.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare properties with actual plugin support and CI invocation.
2. Check for machine-specific paths or secrets.
3. Verify that a performance property is not masking race-sensitive task behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A project property references a local machine path and breaks another environment.
2. Configuration cache is enabled while a custom task violates it and produces stale results.
3. A deprecated property is silently ignored while documentation assumes it works.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use `--configuration-cache` diagnostics only if the repository claims support. Do not add tuning properties without evidence.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 28. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 28: Main, unit-test, and Android-test source set wiring

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Main, unit-test, and Android-test source set wiring

Verify that production, host-side test, and device-test sources and resources are assigned to the intended source sets and do not leak test-only code or fixtures into the app.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect source directories, dependency configurations, manifest overlays, and generated sources.
2. Check package discovery and runner configuration.
3. Confirm host tests that require Robolectric are not accidentally configured as pure JVM tests without Android resources.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Test code is packaged in release output.
2. Android tests compile against the wrong application ID or runner.
3. A source directory is omitted so meaningful tests never compile.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `:app:testDebugUnitTest` or `:app:assembleDebugAndroidTest` when a source-set correction is made.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 29. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 29: Manifest and resource merge priority across build variants

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Manifest and resource merge priority across build variants

Verify that main and variant overlays merge into the intended final manifest and resources without hidden overrides to permissions, exported components, labels, backup, or cleartext settings.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect source-set manifests and resource directories if present.
2. Generate or read the merged manifest for debug and release where behavior may differ.
3. Trace placeholders and tools namespace merge directives.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A debug overlay adds a permission or exported component unexpectedly.
2. A release resource overrides a privacy statement or application label with stale text.
3. A `tools:replace` hides an unsafe library manifest contribution.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Inspect merged artifacts for affected variants and run a focused assemble or manifest process task.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 30. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 30: KSP generated sources, Room schema export, and incremental build integration

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: KSP generated sources, Room schema export, and incremental build integration

Verify that KSP generates and compiles Room code for the intended variants, that schema export points to the committed location, and that incremental builds do not rely on stale generated output.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `settings.gradle.kts`, root and app Gradle files, `gradle/libs.versions.toml`, Gradle wrapper files, source sets, manifests, generated KSP output, and identity-dependent code or tests. Treat identity and version changes as high impact.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect KSP arguments, Room plugin or processor configuration, schema directory wiring, and generated source inclusion.
2. Compare source entities with generated schema only as evidence, not by patching generated Kotlin.
3. Check clean and incremental task dependencies if a stale-output failure is reproducible.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Room implementation is missing because KSP is configured on the wrong configuration.
2. Schema export writes outside the committed `app/schemas` location.
3. An incremental build succeeds using stale output while a clean build fails.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `:app:kspDebugKotlin` and the smallest clean-equivalent verification that does not discard user work. Do not delete shared caches casually.
2. Use focused Gradle configuration, `:app:kspDebugKotlin`, `:app:assembleDebug`, or merged-manifest inspection only when relevant to a confirmed correction. Never increment versions or update dependencies merely because the audit is running.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 31. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 3. Build variants, release packaging, dependencies, CI, and quality-tool evidence

## Prompt 31: Debug and release build type behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Debug and release build type behavior

Verify that debug and release build types differ only in deliberate ways and that production behavior is not accidentally debug-only or release-broken.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect debuggable, minify, resource shrink, BuildConfig, manifest, and resource differences.
2. Trace any code branches on build type.
3. Compare test assumptions with the actual variant under test.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A feature works only because debug keeps a class removed in release.
2. Debug-only logging or data is packaged in release.
3. Release-specific resources or manifest entries break startup.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run the affected variant assemble task and inspect merged or packaged artifacts when needed.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 32. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 32: R8 code shrinking and runtime reachability

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: R8 code shrinking and runtime reachability

Verify that release minification preserves classes, serializers, Room-generated code, navigation, and reflection-dependent behavior actually used by RowTool.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect minify configuration, consumer rules from dependencies, serialization usage, and generated keep rules.
2. Look for reflection or class-name lookup before adding keep rules.
3. Check release warnings and mapping evidence.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. R8 removes a serializer or model reached only through generated lookup.
2. An overly broad keep rule disables useful shrinking without need.
3. A release-only missing-class warning is ignored despite runtime impact.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `:app:assembleRelease` after a justified shrinker correction and inspect warnings. A successful compile alone is not proof of every runtime path.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 33. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 33: Resource shrinking and required asset retention

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Resource shrinking and required asset retention

Verify that release resource shrinking keeps all launcher, counter-control, localization, XML, and dynamically referenced resources required at runtime.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect resource references, generated resource usage, keep/discard rules, and release shrink reports if available.
2. Check resources referenced by name or metadata.
3. Compare debug and release packaging for critical assets.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Counter WebP assets are removed in release due to an indirect reference.
2. Locale resources or monochrome icon are omitted.
3. A broad keep file preserves unrelated resources and masks dead references.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Build release and inspect packaged resources only when a real retention concern exists.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 34. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 34: Packaging options, duplicate resources, and native libraries

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Packaging options, duplicate resources, and native libraries

Verify packaging exclusions, pick-first rules, JNI handling, and resource merges do not remove required files or conceal incompatible duplicates.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect packaging blocks and the resolved dependency graph.
2. Identify the actual source of any duplicate resource or native library.
3. Check APK/AAB contents if a packaging rule is changed.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A pick-first chooses an incompatible license, metadata, or native file.
2. A broad exclusion removes runtime service metadata.
3. Duplicate native libraries target incompatible ABIs.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run the affected assemble task and inspect package contents for any changed rule.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 35. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 35: Release signing boundary and absence of committed key material

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Release signing boundary and absence of committed key material

Verify that the repository intentionally lacks upload signing credentials while still producing unsigned or locally signable release outputs as documented.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect signing configs, properties, environment lookups, ignore rules, and release checklist.
2. Check that no test or debug key is presented as production upload signing.
3. Distinguish build success from Play readiness.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A password or keystore is committed.
2. Release silently uses the debug key and is described as upload-ready.
3. Build logic fails configuration when external signing is intentionally absent.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Do not create signing material. Validate only repository-internal build configuration and report the external step.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 36. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 36: Resolved dependency graph conflicts and duplicate versions

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Resolved dependency graph conflicts and duplicate versions

Inspect the actual resolved graph for conflicting AndroidX, Kotlin, coroutines, serialization, Room, or Compose artifacts that cause runtime or compile risk.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Use dependency insight for suspicious modules rather than reading declarations alone.
2. Check BOM alignment and forced versions.
3. Trace a conflict to a concrete API, binary, or packaging failure before changing it.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two incompatible versions of a library are resolved across configurations.
2. Compose artifacts bypass the BOM and create binary mismatch.
3. A harmless evicted version is misreported as a defect.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused dependency reports and compile tests. Do not update dependencies merely to make versions look uniform.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 37. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 37: Accidental networking, Firebase, analytics, advertising, billing, or crash SDK capability

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Accidental networking, Firebase, analytics, advertising, billing, or crash SDK capability

Verify that production and transitive dependencies preserve the deliberate RowTool local-only, paid-download boundary with no network, analytics, ads, billing, or crash-reporting SDK.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect all production dependency configurations and transitive graph.
2. Search manifests and resources for provider, service, receiver, permission, or initialization contributions.
3. Distinguish a general-purpose AndroidX library from an actual external service SDK.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A newly added SDK initializes or sends data despite no Internet feature.
2. A transitive manifest adds components or permissions inconsistent with the privacy claim.
3. A billing library is included even though the app is a paid download with no in-app purchases.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Inspect resolved dependencies and merged manifests for debug and release. Do not remove a dependency without proving it is unnecessary and safe to remove.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 38. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 38: Transitive manifest permissions, components, and authorities

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Transitive manifest permissions, components, and authorities

Verify that dependency manifests do not add permissions, exported components, content providers, or authorities that violate the intended RowTool capability boundary.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect merged manifest blame or merger report.
2. Trace every non-app component and permission to its source.
3. Check Android 12+ exported requirements and authority uniqueness.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A library adds Internet, storage, advertising ID, or notification permission.
2. A provider is exported or uses a colliding authority.
3. A tools override suppresses a component without understanding dependency behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use merged-manifest evidence and a variant build after any correction.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 39. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 39: Dependency declarations, scopes, and unused capability risk

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Dependency declarations, scopes, and unused capability risk

Verify that dependencies are declared in the narrowest correct configuration and that test-only tools are not packaged into production.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `implementation`, `api`, `compileOnly`, `testImplementation`, and `androidTestImplementation` usage.
2. Confirm each suspicious production library has a current code or resource consumer.
3. Do not remove libraries based only on a static unused warning when generated code or manifest use exists.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A test framework is packaged in the APK.
2. A runtime library is mistakenly compile-only and causes device failure.
3. An apparently unused processor is removed even though it generates required code.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use dependency reports, compile, and package inspection for any scope correction.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 40. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 40: Primary Android CI workflow parity with documented local checks

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Primary Android CI workflow parity with documented local checks

Verify that `.github/workflows/android.yml` actually runs the documented debug build, host tests, debug lint, and Android-test compilation on the intended JDK and paths.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect triggers, checkout, JDK setup, Android SDK setup, caching, Gradle commands, and working directories.
2. Check that failures are not ignored.
3. Distinguish Android-test compilation from device execution.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. CI skips a command due to a wrong path or conditional.
2. A failing step has `continue-on-error` and is reported as blocking.
3. CI claims connected tests while no emulator is started.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Validate YAML syntax and run equivalent local tasks when available. Do not claim hosted CI ran unless evidence exists.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 41. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 41: CodeQL workflow build mode, JDK 17, and analyzed language scope

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: CodeQL workflow build mode, JDK 17, and analyzed language scope

Verify that the CodeQL workflow builds the actual Android/Kotlin/Java source under its documented JDK 17 environment and does not give false confidence from an empty or failed build.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect language matrix, build mode, Gradle command, permissions, and error handling.
2. Check whether generated sources are available to analysis.
3. Distinguish workflow syntax success from a completed scan.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. CodeQL autobuild cannot build the Android project but the workflow still appears green.
2. The wrong working directory yields no relevant source.
3. A JDK mismatch prevents generated code analysis.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use workflow inspection and local equivalent compilation. External GitHub execution must not be fabricated.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 42. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 42: ktlint blocking task wiring and scope

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: ktlint blocking task wiring and scope

Verify that `ktlintCheck` is truly blocking, scans intended Kotlin sources, respects generated-source exclusions, and is not confused with an auto-fix task.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect plugin configuration, task dependencies, filters, and CI or wrapper invocation.
2. Read actual reports when a task fails.
3. Confirm generated code is not being manually reformatted.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. ktlint is configured but never run by the claimed check.
2. Generated sources produce noise that leads to unsafe edits.
3. A formatting fix rewrites unrelated files.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `ktlintCheck` only when appropriate and inspect every formatter-caused diff. Do not run broad format automatically.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 43. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 43: Detekt configuration, Compose rules, ignoreFailures, and report interpretation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Detekt configuration, Compose rules, ignoreFailures, and report interpretation

Verify that Detekt uses the repository configuration and Compose rules, and that `ignoreFailures = true` is not misinterpreted as a clean result.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect task configuration, config files, baselines, source scope, reports, and wrapper behavior.
2. Read report findings even when Gradle exits successfully.
3. Confirm each proposed fix against full code context.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A successful Gradle task is reported as zero Detekt findings despite a populated report.
2. A stale baseline hides newly relevant defects.
3. A style-only finding triggers architecture churn.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run the configured task or plan-only wrapper as appropriate and cite the report artifact, not merely exit status.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 44. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 44: Android lint, release checks, and Google security lint rules

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Android lint, release checks, and Google security lint rules

Verify that Android lint aborts on errors as intended, includes the configured security ruleset, and examines the relevant variants without blanket suppression.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect lint options, dependencies, baselines, source scope, and release-check behavior.
2. Trace each suppression to a concrete false positive or accepted design.
3. Check report output rather than task name alone.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A lint baseline hides a real current security issue.
2. A suppression is broader than the specific false positive.
3. Only debug lint runs while release-specific resources or manifest remain unchecked.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `:app:lintDebug` or the relevant release lint task after a confirmed correction and inspect reports.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 45. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 45: Sonar, JaCoCo exclusions, wrapper routing, and evidence semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Sonar, JaCoCo exclusions, wrapper routing, and evidence semantics

Verify that Sonar configuration, JaCoCo XML generation, exclusions, and `tools/sonar.ps1` produce evidence that is described accurately.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/build.gradle.kts`, ProGuard/R8 files, packaging rules, dependency reports, merged manifests, `.github/workflows`, quality configuration, Sonar properties, JaCoCo tasks, and project wrapper scripts.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `sonar-project.properties`, task dependencies, coverage report paths, exclusions, and wrapper arguments.
2. Confirm that UI, MainActivity, and RowToolApplication exclusions are understood.
3. Distinguish a plan-only wrapper result from a completed server analysis.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Coverage percentage is presented as UI coverage despite explicit UI exclusion.
2. Sonar reads a stale or missing JaCoCo report.
3. A wrapper routes correctly but no analysis executes, yet the audit reports clean Sonar results.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use local report artifacts and task output. Do not require network or credentials when unavailable.
2. Use variant-specific assemble, bundle, lint, dependency, report, or dry-run tasks only when relevant. Distinguish compilation from execution, a tool exit code from its report contents, and unsigned build output from a Play-ready artifact.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 46. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 4. Application bootstrap, dependency construction, activity lifecycle, and platform security

## Prompt 46: Application class declaration and manifest instantiation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Application class declaration and manifest instantiation

Verify that the manifest names the actual RowTool Application class and that Android can instantiate it with the configured namespace in every relevant variant.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect the application class package, manifest declaration, merged manifest, and constructor shape.
2. Trace any initialization performed before `onCreate`.
3. Check test manifests and Robolectric configuration for a different Application.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The manifest resolves to a missing or wrongly packaged Application class.
2. A test-only Application leaks into production.
3. Initialization assumes state unavailable during process startup.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Compile the affected variant and inspect the merged manifest after any correction.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 47. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 47: AppContainer lifetime and single-instance ownership

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: AppContainer lifetime and single-instance ownership

Verify that `AppContainer` is constructed once per application process and that Room, DataStore, repositories, and factories do not gain accidental duplicate instances.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace all construction sites and property initialization paths.
2. Check whether configuration changes or recomposition can recreate the container.
3. Verify tests intentionally replace or construct dependencies without modifying production lifetime.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Multiple repository instances weaken the intended Mutex serialization.
2. Multiple Room or DataStore instances target the same file unexpectedly.
3. An Activity reference is retained in an application-scoped container.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused construction tests or runtime logging only if needed. Do not introduce a DI framework.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 48. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 48: Room construction order, configuration, and application context use

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Room construction order, configuration, and application context use

Verify that Room database construction uses the application context, correct database name, schema settings, and no destructive fallback contrary to the documented migration boundary.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect builder options, callbacks, executors, journal mode, and schema export configuration.
2. Trace when the database first opens and how exceptions surface.
3. Check that no Activity or short-lived context is captured.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The database uses an Activity context and leaks it.
2. A destructive migration fallback silently destroys user projects.
3. A wrong database name creates a second empty store.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused Room construction and persistence tests after any correction.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 49. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 49: Preferences DataStore construction and singleton access

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preferences DataStore construction and singleton access

Verify that Preferences DataStore is created once with the documented `rowtool_preferences` name and is accessed through a safe application-scoped path.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect property delegates or factory construction and all call sites.
2. Check for duplicate DataStore instances using the same file.
3. Trace test replacement and corruption behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two DataStore instances for one file cause runtime exceptions or race risk.
2. The name differs and settings appear reset.
3. An Activity context or repeated route creation owns DataStore.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused DataStore tests and app construction tests when changed.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 50. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 50: Explicit ViewModel factory mapping and dependency completeness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Explicit ViewModel factory mapping and dependency completeness

Verify that explicit factories create the correct ViewModel type with the complete intended dependency set and reject unsupported model classes safely.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect every factory branch, requested class comparison, constructor call, and cast.
2. Trace route or navigation ownership of each factory.
3. Check tests for production-equivalent dependency wiring.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A factory returns the wrong ViewModel for a requested class.
2. A new required dependency is omitted and replaced with a hidden singleton.
3. An unsafe broad cast masks an unsupported class until runtime.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Compile and run focused ViewModel construction tests when changed.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 51. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 51: Bootstrap exception visibility and recoverability

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Bootstrap exception visibility and recoverability

Verify that failures opening Room, DataStore, or constructing required dependencies are not swallowed into an unusable blank screen or falsely reported as success.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace exceptions from application initialization to activity content.
2. Inspect any catch-all handlers and user-visible fallback.
3. Distinguish recoverable preference corruption from nonrecoverable database or programming errors.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. An exception is swallowed and the app displays permanent loading.
2. A fallback silently creates a different database and loses apparent data.
3. Sensitive stack traces are shown to users or logged unnecessarily.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use a focused failure-injection test only if existing seams support it. Avoid inventing a broad recovery framework.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 52. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 52: MainActivity `onCreate` ordering and Compose content setup

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: MainActivity `onCreate` ordering and Compose content setup

Verify that splash, edge-to-edge, obscured-touch protection, theme collection, and Compose content are initialized in a safe and deterministic order.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect the full `onCreate` sequence and any pre-super or post-super requirements.
2. Trace values collected before `setContent`.
3. Check whether activity recreation duplicates setup or collectors.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The window protection is applied too late for early touches.
2. Compose content reads uninitialized application dependencies.
3. Repeated setup adds duplicate listeners or side effects.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run an activity launch test or device smoke test after a confirmed lifecycle correction.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 53. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 53: SplashScreen installation, keep condition, and exit behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: SplashScreen installation, keep condition, and exit behavior

Verify that SplashScreen use on minSdk 29 through target 37 neither hangs nor disappears before required startup state is ready.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect installation timing, keep-on-screen condition, startup state source, and any animation listener.
2. Check process recreation and no-project startup.
3. Verify that a failure cannot leave the condition permanently true.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Splash remains indefinitely because state never resolves.
2. A condition captures stale state after recreation.
3. The splash is installed after `super.onCreate`, producing unsupported behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use an activity startup test and, if necessary, a device smoke test on an available target.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 54. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 54: Edge-to-edge configuration and content inset ownership

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Edge-to-edge configuration and content inset ownership

Verify that edge-to-edge is enabled once and that Compose layouts consume system insets correctly without double padding or obscured controls.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Activity window setup, scaffold/content insets, top bars, bottom navigation, dialogs, and IME handling.
2. Test light and dark themes and at least one gesture-navigation configuration if available.
3. Check whether screens apply both scaffold padding and system bar padding.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Top content is hidden under the status bar.
2. Bottom controls are obstructed by gesture navigation.
3. Insets are applied twice and waste space or break responsive scaling.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use Compose layout tests where possible and a visual device check only when needed.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 55. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 55: Status and navigation bar icon appearance across themes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Status and navigation bar icon appearance across themes

Verify that system bar icon lightness follows the selected light or dark theme during startup, runtime theme changes, and activity recreation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect WindowInsetsController or compatibility APIs and theme state collection.
2. Trace whether navigation bar contrast enforcement or transparent colors affect legibility.
3. Check system theme changes when RowTool is in SYSTEM mode.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Dark icons remain on a dark bar or light icons on a light bar.
2. Runtime theme change updates Compose but not system bars.
3. An unsupported API is used without the compatibility layer required by minSdk 29.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused activity tests and a device check if the API behavior cannot be proven host-side.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 56. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 56: Theme state availability before first composition

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Theme state availability before first composition

Verify that the first composition uses a deliberate theme value and does not visibly flash an incorrect theme while DataStore loads.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace initial `StateFlow` values, DataStore defaults, and Activity collection.
2. Check SYSTEM mode resolution against current configuration.
3. Distinguish a harmless single-frame transition from a reproducible incorrect state.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The app always flashes light theme before dark preference is read.
2. An invalid persisted enum prevents any theme value from emitting.
3. Activity and Compose derive system dark mode from different sources.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused state tests or screenshot observation only when a real flash is reproducible.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 57. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 57: Configuration changes and Activity recreation safety

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Configuration changes and Activity recreation safety

Verify that rotation, locale, font scale, night mode, and other configuration changes preserve persistent state and do not replay destructive or navigation effects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace what is Activity-owned, ViewModel-owned, saved, or reloaded from Room/DataStore.
2. Check dialog and launcher state across recreation.
3. Inspect effect collection restart behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A delete or import confirmation replays as an action after recreation.
2. Current project navigation is duplicated.
3. An Activity reference retained in state leaks the destroyed instance.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused recreation tests or Compose activity tests for any correction.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 58. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 58: Process death and cold-process reconstruction

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Process death and cold-process reconstruction

Verify that a fresh process reconstructs dependencies, validates last-active state, and restores only persisted data rather than relying on in-memory singletons or stale navigation state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace startup from empty process with and without projects.
2. Check persisted Room and DataStore sources independently.
3. Inspect assumptions in ViewModel initial state and channels.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The app opens a nonexistent project because only in-memory validation existed.
2. Undo history disappears despite Room persistence.
3. A stale effect channel event is incorrectly assumed to survive process death.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use existing persistence tests or a full activity relaunch test when available.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 59. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 59: Keep-screen-awake window flag lifecycle

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Keep-screen-awake window flag lifecycle

Verify that the keep-awake setting applies only while an active counter is open and is removed when disabled, archived, navigated away, or the Activity stops.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace state from DataStore through Counter route to the Activity window flag.
2. Inspect add and clear operations and lifecycle cleanup.
3. Check route changes, project state changes, and runtime toggle updates.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The flag remains set on Projects or Settings after leaving Counter.
2. Disabling the setting does not clear an already set flag.
3. An archived or missing project keeps the screen awake.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused route or Activity tests and inspect window flags after transitions.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 60. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 60: Obscured-window touch filtering and tapjacking boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Obscured-window touch filtering and tapjacking boundary

Verify that `filterTouchesWhenObscured` and `dispatchTouchEvent` reject fully or partially obscured touches as intended without disabling ordinary input.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `RowToolApplication.kt`, `MainActivity`, `AndroidManifest.xml`, theme setup, window APIs, splash handling, and explicit ViewModel factories. Verify actual lifecycle and process behavior on the supported Android range.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Activity decor setup and MotionEvent flag checks.
2. Confirm both `FLAG_WINDOW_IS_OBSCURED` and the documented partial-obscuration boundary as applicable.
3. Trace event return values and unaffected keyboard or accessibility input.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Partially obscured touches reach destructive actions.
2. All touches are dropped because the flag test is incorrect.
3. A refactor bypasses Activity-level filtering for Compose.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use a focused Activity unit or instrumentation test if possible. Preserve the security behavior unless a concrete compatibility failure is proven.
2. Use focused unit tests, Robolectric tests, manifest inspection, `:app:assembleDebug`, or a device test only when the relevant runtime path requires it. Preserve the obscured-touch and no-permission boundaries.
3. After any change, inspect the exact file-level diff, run `git diff --check`, and confirm that no unrelated file, generated output, user change, dependency, schema, migration, permission, or configuration was altered.
4. If no change is made, do not run heavy validation merely to create activity. Record the repository evidence that established the area as correct and continue.

MANDATORY OPERATING RULES FOR THIS PROMPT

1. Before inspecting this audit area, read or re-read the repository-root file named exactly `PROJECT.md`. Use that exact filename, not a project-prefixed variant.
2. Use `PROJECT.md` as important context for RowTool's purpose, intended behavior, architecture, constraints, supported Android versions, privacy boundary, and deliberate decisions.
3. Do not assume `PROJECT.md` is complete or fully current. Verify implementation claims against the current code, configuration, tests, dependency graph, generated artifacts, and actual repository state.
4. If `PROJECT.md` and the implementation conflict, determine the nature of the conflict. If the document appears stale or the intended requirement remains ambiguous, do not change working behavior based on an assumption.
5. Do not modify `PROJECT.md` during this review-and-repair process unless a separate later task explicitly instructs you to do so.
6. Review the actual current repository, not an imagined, assumed, or idealized implementation.
7. Inspect the complete context relevant to each possible finding. Trace the call chain, state origin, read and write paths, configuration, platform versions, version guards, tests, validation, error handling, and existing safeguards as applicable.
8. Never interpret one line, function, warning, or isolated snippet without checking the surrounding implementation and all materially relevant callers and consumers.
9. Inspect first and change only afterward. Do not begin a correction merely because something looks suspicious or differs from a generic best practice.
10. Verify every claimed issue before correcting it. Be able to explain concretely how the failure occurs or exactly which real project requirement the implementation violates.
11. Distinguish a confirmed defect from a possible but unverified risk, a style preference, and an alternative implementation.
12. If an issue cannot be confirmed from the repository or through safe available validation, do not present it as a certain bug and do not make a speculative change.
13. Do not invent findings in order to make changes. There is no minimum number of findings or corrections.
14. Finding no issue is a valid and desirable result. If this audit area is already correct, leave the code unchanged and continue.
15. A style preference, alternative architecture, or preferred best practice is not by itself a defect that should be corrected.
16. Do not refactor, abstract, rename, reorganize files, create helper classes, add layers, introduce frameworks, or add dependencies merely because the code could be written differently.
17. Do not perform opportunistic cleanup or any change unrelated to this prompt's tightly bounded audit area.
18. Do not make a working solution more complex. Prefer the simplest adequate solution. Less is better.
19. Leave code alone when it is correct, safe, understandable, and fit for RowTool's actual purpose.
20. If a real issue is confirmed, correct its root cause with the smallest safe change that actually resolves it.
21. Preserve intentional behavior, public interfaces, persisted data formats, Room schema, UI behavior, and compatibility unless changing one is explicitly necessary to fix a confirmed defect.
22. Do not make broad architecture changes, add dependencies, change the database schema, create migrations, alter the build system, or change a public API without direct and strong technical necessity.
23. If a small local correction is sufficient, do not replace it with a broad rewrite.
24. Correct one root cause once. Do not apply several near-duplicate patches in different places when the problem can be safely resolved at its true shared source.
25. Even if a correction made elsewhere in the same audit run appears to resolve this area, still inspect this prompt fully and record that the issue is already resolved. Do not skip the verification.
26. Assess severity realistically. Do not label a theoretical edge case, style issue, or minor optimization opportunity as a serious defect without concrete evidence.
27. For performance, memory-leak, concurrency, and security claims, describe the actual mechanism by which the problem can occur. A generic warning is not enough to justify a correction.
28. Account for the project's real versions and settings, including `minSdk`, `compileSdk`, `targetSdk`, Java and Kotlin targets, library versions, build variants, and existing version guards.
29. Check whether existing validation, tests, exception handling, lifecycle management, platform constraints, transaction boundaries, or another safeguard already prevents the alleged problem.
30. Before the first modification made under this prompt, run `git status --short`. If this prompt is executed within the sequential document, distinguish changes already logged as Codex changes in this audit from the original baseline. Treat every original-baseline change and every current change not demonstrably produced and logged by Codex in this audit as user work. If this prompt is executed alone, treat every existing change as user work.
31. Never use a command or operation that can discard, revert, overwrite, or hide existing work. In particular, do not use `git reset`, `git restore`, `git checkout`, `git clean`, or `git stash` to handle user changes.
32. Do not revert, edit, reformat, or clean up changes unrelated to this prompt.
33. Do not commit, switch branches, rebase, amend, or otherwise change Git history unless the user separately and explicitly requests it.
34. Do not deploy, publish, upload a build, create an external account, change a cloud service, rotate credentials, modify real production data, or perform another external or difficult-to-reverse action.
35. If a correction requires credentials, secrets, an external service, a manual user decision, or safety confirmation, leave that external step undone and report it clearly. Complete only safe repository-internal work that does not require the external action.
36. Add or modify a test only when it is genuinely needed to prove the correction or prevent a realistic regression. Do not add tests merely to increase test count.
37. After every correction, run appropriate targeted validation, such as the relevant tests, Android lint, ktlint, type or compile checks, KSP, or a focused build used by this repository.
38. A heavy whole-project validation is not required after a prompt that made no change. Use targeted validation after actual changes, then perform the project-appropriate broader validation in the final numbered prompt.
39. Do not use an automatic fixer that can make broad or unreviewed edits. Inspect every changed file and every resulting diff.
40. If a validation command already fails at baseline or fails for a reason unrelated to this prompt, do not repair it as side work. Separate the pre-existing or unrelated failure from the result of your own change and report it.
41. If validation cannot run because of the environment, state exactly which command could not run and why. Do not claim the correction is fully verified.
42. For every confirmed finding, report the most precise available location, including file path, class, function, symbol, and relevant lines where practical.
43. For every change, explain the observed defect, technical root cause, concrete impact, smallest correction made, and actual validation result.
44. If the implementation is already correct, safe, sufficiently simple, and fit for purpose, say so explicitly and make zero changes for this prompt. If a later numbered prompt exists, continue to it.

Mandatory hallucination warning:

"AI-assisted code review can hallucinate issues, misread context, or recommend unnecessary changes. Base every finding on the actual current repository and verify the full relevant code path, configuration, tests, version constraints, and existing safeguards before reporting or changing anything. It is completely acceptable to find no issue and make no change. Do not invent findings, force a quota, treat a preference as a defect, or modify code merely to produce visible work. Do not over-engineer, add speculative abstractions, or refactor code that is already correct, safe, clear, and fit for purpose. Prefer the smallest adequate solution. Less is better."

Mandatory correction principle:

"Inspect first. If and only if a real issue is confirmed, implement the smallest safe correction necessary to address its root cause. If no issue is confirmed, make no change. Do not perform opportunistic cleanup, unrelated refactoring, dependency updates, broad rewrites, or speculative improvements."

REQUIRED RESULT RECORD FOR THIS PROMPT

Record the result under this prompt number before continuing. Include all of the following:

1. The exact audit area and the materially relevant files, symbols, call paths, configurations, tests, or generated artifacts inspected.
2. Whether any confirmed issue was found.
3. If no confirmed issue was found, an explicit statement that no change was made for this prompt.
4. If an issue was confirmed, its precise evidence, location, root cause, concrete impact, and realistic severity.
5. Every file changed and the reason for each change.
6. Every validation command actually run and its actual result. Do not imply that an unrun command passed.
7. Any pre-existing or unrelated failures, clearly separated from the effects of this prompt.
8. Anything that could not be verified, including the exact reason.
9. A clear statement that processing will continue automatically to the next numbered prompt after this prompt is complete.

CONTINUATION AND INTERRUPTION RULE

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 61. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
