# RowTool implementation reference

## Finalization verification — 2026-09-27

The integrated implementation includes named manual/main-linked additional counters and atomic Undo, configurable repeat start, reminders with persistent acknowledgements, project notes, recent counting history, project-specific widgets, Copy counter setup, and offline language selection. Room schemas 1–5, migrations, backup v5, dependencies, verification metadata, regression tests, Windows host support and instrumentation capture/readiness helpers belong to the same tested source state.

Fresh finalization checks: the complete unfiltered host suite passed **217/217**, with zero failures/errors/skips and exactly the previous identities (**32/32 tasks executed**). Debug app/test builds, direct ktlint and Detekt passed (**91/91 tasks executed**); inspected ktlint/Detekt reports have zero findings. Debug lint has **0 errors / 9 warnings**, identical by ID, message and location to the latest preserved report. The initial sandbox wrapper attempt stopped before Gradle/test execution because its cache lock path was inaccessible; the normal-permission run above is the actual test execution. Local logs and XML are retained in ignored `reports/finalization-20260927/`.

The complete API 36 evidence in `reports/target-input-20260927/` is reused: **122 reached / 121 passed / 1 assumption skip / 0 failures/errors/unreached**. The skipped `WidgetLauncherProbeTest.verifyLauncherScenario` requires the manual `operation` argument, which the unfiltered run does not supply. Raw runner statuses and discovery identities agree. All 215 frozen source/resource files still match, and fresh forced builds reproduce both tested APKs byte for byte: app SHA-256 `D04FE5C8B6090F6928B9082360B705B897B863F784850521F13FA7BC9F2598AC`; test SHA-256 `3C967878A9A6A204022E7463CCE96CB1FCABD45CCD55EF38DCA569102E9F8DF0`. No new device run was needed.

The dated investigations below preserve historical results and causal limits. A direct `onCompositionError` invocation remains distinct from automatic delivery of a genuinely thrown composition exception. This finalization does not establish all Android versions, physical-device behavior, human accessibility review, release shrinking/signing or Google Play delivery. CI results must be checked separately for the exact pushed commit; local success is not CI evidence. Generated reports and private local files remain outside the commit.

## Project editor target-input investigation — 2026-09-27

**A missing target-toggle viewport precondition was demonstrated and repaired in instrumentation tests.** The unchanged complete editor class reproduced the missing `Target count` lookup before oversized text entry. In a controlled keyboard transition, the previously scrolled `Set a target` row fell below the dialog viewport; one original pointer click left it Off, so no field existed. The capture shows en-US, valid name `Project`, repeat Off and no target input. This demonstrates the test-contract defect; the exact uncontrolled/historical failed-touch timing remains unproven.

The original method now reuses the existing bounded keyboard/layout observation before scrolling and activating the named switch once, asserts On, and selects the editable field by its localized resource label plus editable action. The entire original `1000000` remains in EditableText with an error, disabled Save and no callback. Changing only the target to raw `999999` removes the error and enables Save. Text is injected with the original `performTextInput`; native clipboard paste is not covered. One focused regression proves the failed old viewport precondition and successful activation after normal scrolling. No production code changed. The existing Save correction/regression are preserved; its helper only gains an optional localized Save label with the old default.

Final unchanged source/APKs: original method **1/1**, new regression **1/1**, complete editor class **11/11 twice**. One complete unfiltered API 36 execution: **122 reached / 121 passed / 0 failures / 0 errors / 1 assumption skip / 0 unreached**. The skip is the existing `WidgetLauncherProbeTest.verifyLauncherScenario` requirement for a manual `operation` argument. All eligible identities match fresh discovery; existing API restrictions remain. The original 9/10 class failure, three passing capture-only classes and controlled red experiment are retained separately, not overwritten by final passes.

Fresh unfiltered host suite **217/217**, unchanged identities; all **102/102** build/check tasks executed. Debug app/test builds, direct ktlint and Detekt pass; debug lint **0 errors / 9 exactly unchanged warnings**. App APK remains identical to task start. Source stayed frozen through final runtime checks. Room 5, migrations, backup v5, dependencies, permissions and locale ownership remain unchanged. Historical Save, DIRECT START, WIDGET TARGET and direct `onCompositionError` versus genuinely thrown-composition-exception qualifications remain; this passing run does not retroactively prove those historical causes. No commit, push, PR or publication.

Evidence: [focused target-input investigation](reports/target-input-20260927/INVESTIGATION.md), including before-teardown controlled capture, all runner logs, APK hashes, host/analyzer XML and preservation checks.


## Project editor Save reachability follow-up — 2026-09-27

**A missing interaction precondition was demonstrated and repaired in the test.** The installed Compose 1.12.1 Android `performClick` uses injected down/up events on the dialog Compose root. Text input can return before the IME changes that root's viewport. A controlled keyboard transition proved that an enabled Save, reachable after a completed scroll with the keyboard hidden, becomes unreachable when the keyboard opens. Scrolling with the keyboard already settled restores reachability and one pointer activation delivers exactly the original Scarf/ROWS/0/null optional values. This establishes a test-contract defect; the historical failed tap's exact geometry and cause remain unproven. No production defect or production change is claimed.

`ProjectEditorDialogTest.kt` now observes the dialog's keyboard/layout condition before its original scroll, checks a fresh enabled Save against the unobstructed viewport, and retains one down/up activation and every exact default/callback assertion. The narrow test-only `EditorSaveProbe.kt` records geometry/insets and activation timestamps without replacing touch dispatch or inset handling. One regression rejects the still-enabled but unreachable Save after controlled keyboard appearance, then verifies the original callback once after scrolling. The original scenario keeps the keyboard open; the regression alone dismisses/reopens it to control the transition. Existing failure capture remains.

On unchanged final source/APKs: original method **1/1**, regression **1/1**, first complete class **9/10**, second declared class **10/10**, separate nearby edit/save **1/1**. Both changed scenarios passed in both classes. The first class's case 4, unchanged `outOfRangePastedTargetRemainsInvalidInsteadOfBeingTruncated`, failed because `Target count` was absent at its scroll request. Its preceding method was the new regression; the cause and any cross-test timing contribution remain unproven. The failure and logs are preserved, and that separate method was not repaired. **The conditional complete instrumentation run was not performed because focused verification was not entirely clean.** Discovery lists **121 eligible API 36 cases**, exactly one more regression; discovery is not execution. The last actual complete result remains **120 reached / 118 passed / 1 failed / 1 assumption skip**.

One fresh unfiltered normal host suite **217/217**, unchanged identities. Debug app/test builds, ktlint and Detekt pass; inspected debug lint reports **0 errors / 9 unchanged warnings**. The initial formatting failure is retained separately; final source checks and `git diff --check` pass. The app APK remains byte-identical to baseline. Dedicated `RowTool_Copy_20260924` / `emulator-5592` retained API 36, font scale 1.0 and its existing display settings. Production behavior, Room 5/migrations, backup v5, dependencies, permissions, locale ownership and widgets remain unchanged. Historical DIRECT START/WIDGET TARGET and direct `onCompositionError` qualifications remain unchanged. No commit, push, PR or publication.

Evidence, exact activation coordinates/timestamps, every execution and remaining limitations: [Save reachability follow-up](reports/save-reachability-20260927/INVESTIGATION.md). The earlier investigation below remains historical evidence and is not rewritten.

## Project editor Save callback investigation — 2026-09-27

**The missing Save callback reproduced; its exact cause remains unresolved. No behavior repair or new complete instrumentation run is claimed.** On dedicated API 36 `RowTool_Copy_20260924` / `emulator-5592`, unchanged method **1/1** and class **9/9** passed. With failure capture only, the method passed **1/1**, the first class passed **9/9**, and a separate second class passed **8/9**: `createDefaultsAndSaveValuesAreUnchanged` again observed `saves=[]`. The existing onSave seam was not entered. Pre-teardown evidence shows the correct `  Scarf  ` input, Rows/0, both optional switches off, enabled Save with OnClick, an open IME, and Save outside the later visible viewport with scroll offset zero. The dialog owned window focus and there was no ANR. Screenshot/semantics are later observations, not proof of the failed down/up geometry. Three temporary touch-geometry class diagnostics passed **9/9 each**, with visible Save 116×80 px; they do not explain or erase the reproduced failure.

Only `ProjectEditorDialogTest.kt` changes executable source: existing failure capture and timestamped callback/assertion evidence. Original `performClick`, one Save activation, defaults and exact singleton callback/value equality remain; temporary geometry instrumentation was removed. Final source is byte-identical to the capture-only source tested above. Production code/APK, Room 5/migrations, backup v5, dependencies, resources, locale ownership and widget safeguards are unchanged. No wait, keyboard workaround or production change was prescribed without causal proof. This is a callback-only fixture, not database-persistence evidence.

Fresh unfiltered host suite **217/217**, unchanged inventory; debug app/test builds, direct ktlint and Detekt pass with zero findings. Debug lint **0 errors / 9 unchanged warnings**; release lint was **not rerun** (the previous twelve release warnings remain a separate historical result). All **102/102 actionable tasks executed**. Evidence and each preserved attempt: [Save callback investigation](reports/save-callback-20260927/INVESTIGATION.md). The conditional complete instrumentation run was not performed because no demonstrated repair was established. The previous **120 reached / 118 passed / 1 failed / 1 assumption skip** complete result remains authoritative. Historical DIRECT START/WIDGET TARGET causal limits and direct `onCompositionError` versus genuine thrown-composition-exception coverage remain unchanged. No commit, push, PR, publication or automatic next task.

## Complete current API 36 integration verification — 2026-09-26

**The complete current instrumentation run finished with one failure.** One unfiltered invocation on dedicated `RowTool_Copy_20260924` / `emulator-5592`, Android 16/API 36 Google APIs x86_64, Pixel Launcher, user 0 reached all **120** discovered cases: **118 passed, 1 failed, 1 existing assumption skip, 0 runner errors, 0 unreached**. Current sources contain 119 test methods; four methods have two theme cases, and three existing API 29–32 cases are excluded. The 120-case inventory includes all previous 114 cases plus six regressions. The skipped manual launcher probe still requires its separate `operation` scenario.

Case 91, `ProjectEditorDialogTest.createDefaultsAndSaveValuesAreUnchanged`, failed in the test body at line 152: after enabled Save was clicked, the expected single Scarf/ROWS/0 callback value was absent (`saves=[]`). The original method has no failure-boundary screenshot/semantics capture; neither production, fixture/readiness nor environment causality is established. Its predecessor was `optionalNumberFieldsExposeNamedToggleState`. One declared isolated diagnostic on the same frozen APK pair passed **1/1**, with video showing the form, keyboard and scrolled Save. This does not erase the complete-run failure or explain its original touch/layout state. No full-suite retry, source repair or further diagnostic selection was made.

The repaired CopySetupFlowTest **5/5**, DirectCounterNavigationTest **3/3**, SettingsScreenContentTest **7/7**, WidgetHostTest **10/10**, capture-helper **3/3**, AdditionalCountersUiTest **6/6**, and all eight parameterized dialog cases passed within this actual full run. Existing handled negative-control captures remain distinct from failures; no crash/ANR markers were found and the crash buffer was empty. Historical DIRECT START, WIDGET TARGET, New project and closed-connection causal limits remain unproven by a current pass. The widget fallback test directly invokes `onCompositionError` after rendering work finishes: it verifies that callback's hosted fallback, not automatic delivery of a genuine thrown composition exception.

Fresh unfiltered host suite **217/217**, unchanged inventory; debug app/test builds, direct ktlint and Detekt pass with zero findings. Debug lint **0 errors / 9 exactly unchanged warnings**; additionally executed release lint **0 errors / 12 warnings**, reported separately. All **116/116 actionable Gradle tasks executed** and whitespace checks pass. All 293 baseline files stayed byte-identical through test execution; only this leading documentation update and new reports/evidence persist. Production/test behavior, Room 5/migrations, backup v5, resources, dependencies and permissions are unchanged.

Evidence: [integration report](reports/integration-api36-20260926/INVESTIGATION.md), including frozen/installed APK hashes, exact commands, source/runner inventory, complete order/status bundles, logs/captures, isolated video, host/analyzer XML and preservation checks. Recommended next bounded scope is only this ProjectEditorDialogTest Save input/scroll/click boundary, with missing failure-state evidence collected before choosing a repair; no next task was started. Task-owned temporary processes were stopped. No commit, push, PR or publication.

## Widget configuration / Retry fixture repair — 2026-09-26

**The remaining `WidgetHostTest.configurationRequiresChoiceAndHostedRetryNeverRepeatsCount` timeout is repaired at its fault-fixture lifecycle boundary.** A capture-only class reproduced the second 15-second wait for **Could not load the widget. Try again.** (8/9): the correct attached, focused en-US host displayed Only project / 1. The direct `onCompositionError` callback had returned, followed by another widget publication 4 ms later. Two passing diagnostic classes recorded the correct fallback being replaced by normal content after 34–49 ms. The original fixture manually invokes the callback while Glance can still render; it does not inject an actual thrown read/composition exception. Exact fallback inflation timing in the failing frame and the two older uncaptured failures remains unproven.

The test now waits for this widget's existing Glance worker to finish successfully before its original fallback callback, matching the callback's closing-session contract. This bounded lifecycle condition leaves normal updates enabled and retains the 15-second error assertion. The actual current host must show the localized fallback and Choose project action. A new regression rejects an active session. Explicit project choice, cancellation, original real hosted plus/Retry, no-repeated-count and touch-size checks remain. Raw transactional snapshots prove main/linked **0→1**, manual **0**, exactly one history entry and linked effect; Retry and later fallback/recovery preserve the complete committed snapshot and binding. This covers post-commit recovery through the existing failed-binding seam and direct fallback callback, not discovery of a naturally thrown render exception.

Only `WidgetHostTest.kt` and the Activity-compatible `AssertionFailureCapture.kt` change executable source. All other original class methods, including WIDGET TARGET and language-host repairs, remain unchanged. On frozen source: target **1/1**, new regression **1/1**, complete class **10/10**, separate repeated class **10/10**, capture-helper class **3/3**. Unfiltered host suite **217/217** with unchanged inventory; debug app/test builds, direct ktlint and Detekt pass, lint **0 errors / 9 identical warnings**, **102/102 tasks executed**, whitespace checks clean. Production APK is byte-identical to baseline. No independent failure occurred in final selections. Evidence: [focused investigation](reports/widget-retry-20260926/INVESTIGATION.md), preserved captures, runner logs and raw persisted snapshots. Historical WIDGET TARGET and DIRECT START qualifications remain. **The complete instrumentation suite was not rerun or declared passing.** No commit, push, PR, publication or automatic next task.

## WIDGET TARGET readiness investigation — 2026-09-26

**The widget-open test's missing readiness condition is repaired; the historical transient cause remains unproven.** The recorded `WidgetHostTest.explicitOpenDefersForNoteDraftAndReminderOpenDoesNotAcknowledge` failure is its first **WIDGET TARGET** assertion immediately after the actual hosted project-name click, before Note opens or any draft exists. The unchanged method passed 1/1 and a clean capture-only class passed 8/8. A controlled experiment using the existing binding-validation mutex reproduced the exact immediate visibility assertion: focused/resumed MainActivity displayed the last-active **Other target** Counter, with no editor and the explicit target request still pending. Releasing the gate opened Widget target through the same request. This proves a valid asynchronous ordering the test must handle; it does not retroactively prove a mutex delay caused the uncaptured historical third-class failure.

Only `WidgetHostTest.kt` changes executable source. Its bounded readiness condition requires consumed intent, closed Note editor, the intended project's Heading and Counter controls. The deferred request is observed while its original draft remains unsaved on the original project. Back/Cancel retains that draft; the original UI Save persists it only on the source and releases navigation to Other target. Actual Activity recreation checks the cleared intent and absent completed editor. Reminder opening preserves complete acknowledgement/project snapshots and widget binding before the original explicit acknowledgement. One controlled regression rejects the pending/fallback state before releasing validation. All eight other original methods, `RecreatedWidgetReadiness.kt`, production source, schema 5/migrations, backup v5, resources and dependencies remain unchanged.

Frozen final source: requested method **1/1**, new regression **1/1**, complete class **8/9**, separate repeated class **8/9**; requested method and regression each pass **3/3** overall. Both class failures are the unchanged `configurationRequiresChoiceAndHostedRetryNeverRepeatsCount`, waiting 15 seconds for widget error text after injected composition failure (`WidgetHostTest.kt:607`). This separate cause remains open and was not repaired. Two earlier diagnostic invocations were affected by this task's overlapping System UI/runner operations and are explicitly excluded from clean verification. No unrestricted order search or further retry was used.

Fresh unfiltered host suite **217/217**, unchanged inventory; debug app/test builds, direct ktlint and Detekt pass with **0 findings**; lint **0 errors / 9 identical warnings**. All **102/102 Gradle tasks executed**; whitespace checks pass. Production APK is byte-identical to baseline. Dedicated API36 `RowTool_Copy_20260924` / `emulator-5592` exercised the actual **AppWidgetHost**, then was stopped. Evidence: [focused investigation](reports/widget-target-20260926/INVESTIGATION.md), original/controlled captures, separate runner logs, source/APK preservation and inspected analyzer XML. Preserve historical DIRECT START/New project/closed-connection and copy-timeout qualifications. **The complete instrumentation suite was not rerun or declared passing.** No commit, push, PR, publication or automatic next task.

## Copied-counter timeout fixture repair — 2026-09-26

**Only `CopySetupFlowTest.archivedCopyOpensIndependentCounterAndUndo` is repaired.** The original full-run failure at line 95 waited 10 seconds for the copied main count **2 after Add one row**, after successful creation at 1 and before Undo. On dedicated API36 `RowTool_Copy_20260924` / `emulator-5592`, the unchanged isolated method passed, but failure-only capture reproduced the same timeout in the class and in the original draft-cancellation predecessor pair. Both persisted and displayed copy values remained 1, with copied additional counters at zero and no copied history; source remained 75 with linked 74/manual 0. Creation committed exactly one independent copy and opened the correct project.

The failing pair's video shows the fixture archiving the source before its outgoing Counter route has finished leaving. The resulting source warning, “Restore this project before changing its count,” survives navigation and covers the copied plus. A negative control with the new assertions but without the readiness repair captures that warning before it expires: snackbar bounds (24,928)-(616,1064) cover the main-plus touch center (460,988). The later timeout screenshot alone cannot show this transient obstruction. This establishes a test fixture/navigation readiness defect; the historical full-run instant still lacks its own capture and is not retroactively treated as an atomic snapshot.

Only `CopySetupFlowTest.kt` changes executable source. Before the out-of-UI archival mutation, the affected method waits for the source-specific Counter heading to disappear and requires visible ACTIVE PROJECTS. It retains the original production editor/Create/count/Undo/recreation flow, existing timeout, source independence and exactly-two-project checks. Failure-only diagnostics, absence of the source warning, exact visible main values **1→2→1**, fresh copied additional IDs/modes/ownership, linked **0→1→0**, manual zero, copied history **0→1→0**, and unchanged source counters/history are checked. The other four class methods remain unchanged. Temporary diagnostic production logs were removed; production source, Room 5/migrations, backup v5, locales, resources, dependencies and widget contracts remain preserved.

On frozen final sources: original method **1/1**, same previously failing predecessor pair **2/2**, complete class **5/5**, separate repeated complete class **5/5**: five distinct methods, 13 executions, no failures/errors/skips. The new regression assertions are within the original method; no parameter cases were added. Complete unfiltered host suite executed **217/217**, identical inventory; debug app/test builds and all direct checks passed, **102/102 Gradle tasks executed**, ktlint/Detekt **0**, lint **0 errors / 9 exactly unchanged warnings**, `git diff --check` passed. The forced full rebuild's production APK is byte-identical to baseline; the report separately records the earlier frozen installed pair used for final device runs. Task-owned temporary processes have exited.

Evidence: [focused investigation](reports/copy-timeout-20260926/INVESTIGATION.md), with original/captured failures, video, negative-control screenshot/semantics, raw persisted IDs/history, per-probe timestamps, separate runner results, APK hashes, source preservation and host/analyzer XML. Keep **WIDGET TARGET** (`WidgetHostTest.explicitOpenDefersForNoteDraftAndReminderOpenDoesNotAcknowledge`) open. Preserve the historical DIRECT START qualifications and unexplained New project/closed-connection boundaries. The complete instrumentation suite was not rerun and is not declared passed. No commit, push, PR, publication or automatic next task.

## Settings locale-heading fixture repair — 2026-09-26

**Only `SettingsScreenContentTest.sectionHeadingsUseTheAppLocaleWithLocalizedFallbackText`'s invalid fallback setup is repaired.** The original exact `T\u0130ETOJA SOVELLUKSESTA` lookup failed alone as well as in the previously preserved 5/6 class run. Failure-only capture on dedicated API36 `RowTool_Copy_20260924` / `emulator-5592` shows a focused/resumed ComponentActivity and a fully visible About heading reading **ABOUT**. Compose configuration and LocalResources both have `tr,fi`, but `settings_about` already resolves to `About` before Turkish casing. APK resource inspection and post-failure comparisons prove that dependency Turkish resources make `tr` an asset locale: `tr,fi` yields default English, while `fi` and diagnostic `zz,fi` yield Finnish. The unchanged en-US LocalContext did not cause the supplied LocalResources to be ignored.

Only the Settings content test changes executable source. The fixture now explicitly supplies Finnish resources requested as `fi-FI` (parent `values-fi` fallback), while preserving `tr,fi` for casing and the original exact, visible, Heading-semantic **TİETOJA SOVELLUKSESTA** assertion. An independent raw-resource literal verifies Finnish. This proves Turkish casing of supplied Finnish fallback text, not automatic Finnish selection from a production `tr,fi` request. A new regression reuses the existing fixture and checks exact English **ABOUT** and Finnish **TIETOJA SOVELLUKSESTA** with their independent raw strings. No production helper, resource packaging, language authority, supported-language list or persisted locale state changes.

On frozen final sources/APKs: original method **1/1**, new regression **1/1** (English/Finnish iterations inside one method), complete class **7/7**, separate repeated complete class **7/7**. Seven distinct methods, 16 executions, all passed, no skips/errors. Both classes retain the callback-selector repair and duplicate-label regression. Complete unfiltered host suite executed once, **217/217**, identical inventory; debug app/test builds and all direct checks passed, **102/102 tasks executed**, ktlint/Detekt **0**, lint **0 errors / 9 exactly unchanged warnings**, and `git diff --check` passed. Production APK is byte-identical to baseline; all other existing source work, Room 5, backup v5, dependencies and permissions remain preserved. Task-owned processes have exited.

Evidence: [focused heading report](reports/settings-heading-20260926/INVESTIGATION.md), separate original/diagnostic/final runner logs, screenshot, merged/unmerged semantics, locale/resource evidence, Unicode records, frozen APK hashes, host/analyzer reports and source-preservation checks. The full instrumentation suite was not rerun or declared passed. Keep CopySetupFlowTest's copied-counter timeout and WidgetHostTest's separate WIDGET TARGET visibility failure open. Existing DIRECT START readiness work remains valid without claiming every historical intermittent cause solved; historical New project and closed-connection qualifications remain unchanged. No commit, push, PR, publication or automatic next task.

## Settings theme callback selector repair — 2026-09-26

**Only the demonstrated ambiguous System default selector is repaired.** `SettingsScreenContentTest.preferenceRowsAndActionsInvokeEachCallbackOnce` failed its original global `scrollToText("System default")` / `onNodeWithText` assertion because the theme radio row and language-summary Button both contain that label. The same exception was reproduced on dedicated API36 `RowTool_Copy_20260924` / `emulator-5592`; an unobscured screenshot, focused RESUMED Activity and merged/unmerged semantics establish both legitimate actions. An earlier diagnostic run with this task's notification shade still open is separately retained and qualified.

Only `SettingsScreenContentTest.kt` changes executable source. Exact localized text plus selectable/clickable `RadioButton` semantics under the Settings lazy list identify the theme action; normal scrolling and displayed/enabled/exact-label checks remain. Original SYSTEM → LIGHT → DARK → SYSTEM rendering, 48 dp controls, non-actionable children, haptics, keep-awake, Export, Import and Back assertions are preserved. Immediate checks prove each deliberate theme tap adds exactly its expected callback, with no other Settings callback or language dialog/override change. The two affected methods own and restore only their app-language override, with composition-local English/Finnish resources. One regression keeps both translated labels visibly present, proves one SYSTEM callback and isolation, and explicitly retains its controlled DARK state rather than claiming persistence.

On unchanged final executable sources: original method **1/1**, new regression **1/1** (English and Finnish cases inside one method), separate repeated pair with incoming Finnish app override **2/2**, entire class without exclusions **5/6**. Both affected methods pass all three executions. Six distinct methods, ten executions, nine passes/one failure. The independent unchanged `sectionHeadingsUseTheAppLocaleWithLocalizedFallbackText` still fails because `TİETOJA SOVELLUKSESTA` is not found; it was not repaired or retried. The nonempty incoming language override was restored, and the task restored the emulator baseline afterward.

Fresh complete unfiltered host suite executed once: **217/217**, no failures/errors/skips and unchanged inventory. Debug app/test builds and direct checks passed; **102/102 Gradle tasks executed**, ktlint/Detekt **0**, lint **0 errors / 9 unchanged warnings**, `git diff --check` passed. Production source/APK, Room schema 5 and migrations, backup v5, resources, dependencies and permissions remain unchanged from task start. Source hashes remained unchanged across all final device/host checks; the report distinguishes the frozen installed test APK from the later forced rebuild. The task-owned emulator and build processes have exited.

Evidence: [focused selector report](reports/settings-selector-20260926/INVESTIGATION.md), original/final source and APK copies, failure capture, raw per-invocation runner logs, callback/locale checks and preservation hashes. The complete historical instrumentation run remains **114 reached / 108 passed / 5 failed / 1 assumption skip** and was not rerun. Keep the copied-counter value timeout, missing locale heading and separate WIDGET TARGET visibility failure open. Existing widget recreation, Import scrolling, AtomicFile and DIRECT START readiness work is preserved; the causes of older unexplained DIRECT START/New project/closed-connection failures are not newly claimed solved. No commit, push, PR, publication, CI change or automatic next task.

## Direct-counter Settings import scrolling repair — 2026-09-26

**Only the demonstrated Import data viewport assumption is repaired.** `DirectCounterNavigationTest.emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack` originally failed its later `Import data` display assertion (:90), after DIRECT START readiness and return to Projects. A diagnostic-only capture reproduced that same failure on dedicated API36 `RowTool_Copy_20260924` / `emulator-5592`, 640x1280 / 320 dpi / fontScale 1. Pre-teardown screenshot, focused/resumed Activity state and semantics show loaded Settings at the top of its LazyColumn, with Export data at the viewport bottom and Import not yet composed. Normal container scrolling exposes the correct clickable Import row fully inside the viewport; a saved post-scroll screenshot and semantics confirm it.

Only `DirectCounterNavigationTest.kt` changes executable source: `performScrollToNode` searches the existing lazy container for the localized `R.string.action_import` click action under that container, then the test asserts displayed/enabled/clickable and invokes the original import flow. The existing failure-only capture helper wraps scroll/visibility checks; temporary success probes were removed. DIRECT START readiness, fallback to Projects, ACTION_OPEN_DOCUMENT monitor, empty replacement backup, confirmation, resulting empty Projects assertion and final Back/lifecycle check remain unchanged. Production APK is byte-identical to the original full-run APK; Room 5, backup v5, resources, dependencies and previous test repairs are preserved.

On unchanged final executable source, separate invocations passed: original method **1/1**, complete class **3/3**, second complete class **3/3**, same import method at **320 dp / 200%: 1/1**. Complete unfiltered host suite executed once, **217/217**, identical inventory and no failures/errors/skips. Debug app/test builds passed; **102/102 Gradle tasks executed**; direct ktlint/Detekt **0**, lint **0 errors / 9 known warnings**; whitespace check passed. The earlier format-only failed check and diagnostic failure are retained. Font scale restored; task-owned emulator stopped.

Evidence: [focused repair report](reports/import-scroll-20260926/INVESTIGATION.md), linked to [original complete-run evidence](reports/full-instrumentation-20260925/INVESTIGATION.md), with separate runner logs, before/after images, source preservation and host/analyzer reports. The complete 114-case instrumentation suite was not rerun and is not declared passed. CopySetupFlowTest's copied-counter timeout, SettingsScreenContentTest's missing locale heading and ambiguous System default selector, and WidgetHostTest's separate WIDGET TARGET visibility failure remain open. Historical unexplained failures are not relabeled fixed. No commit, push, PR, publication or automatic next task.

## Widget language-change host repair — 2026-09-25

**The host recreation race is repaired in androidTest; repeated class verification has one separately unresolved failure.** Original `WidgetHostTest.externalLanguageChangesRefreshHostedTextWithoutRebindingOrChangingTargets` threw `NoSuchElementException: Key 5 is missing in the map` in `waitText` (:467). Diagnostic isolated/class runs passed 1/1 and 6/6, but one controlled main-thread schedule reproduced the same failure (ID 10): the empty-language predicate accepted the old Swedish host, Android then destroyed it and created a new host with an empty view map, and the next lookup used that new map. Pre-teardown screenshot, state and lifecycle trace prove the ordering; no production binding loss was established.

Only `WidgetHostTest.kt` and new androidTest `RecreatedWidgetReadiness.kt` change executable source. The API >=33 language test records the previous host identity, reacquires the current host, restores normal attachment for the same widget ID, and waits for a different host, expected effective language, correct view ID/current window, real attachment and visible localized unit text. A bounded failure describes host, widget, attachment and actual synthetic text; framework/database exceptions are not swallowed. Real LocaleManager requests, Finnish/Swedish/default hosted rendering, unchanged binding/project/count and real plus-action targeting remain tested. Two regressions cover early default acceptance, missing attachment, stale/wrong-ID/wrong-text/detached rejection, delayed reattachment and an inspected expected timeout. Common debug host and API29–32 code are unchanged; no new API32 run was needed or claimed.

Final dedicated API36 `RowTool_Copy_20260924` / `emulator-5592`: original method **1/1**; complete class **8/8, 8/8, 7/8** in three predefined separate invocations; explicit new regressions **2/2**. Eight distinct methods, 27 executions, 26 passes/one failure. Original language method passes all four executions; each regression passes four. The third class run fails the unchanged `explicitOpenDefersForNoteDraftAndReminderOpenDoesNotAcknowledge` at `WIDGET TARGET` visibility after opening MainActivity (:331). Its cause is unresolved; no failure-boundary snapshot exists for that method and no ordering-independence claim is made. No retry or second repair was attempted. Thus **the entire class is not claimed stable/passing**.

Fresh complete host/build/static run: **217/217 host tests**, no failures/errors/skips and identical inventory; **102/102 Gradle tasks executed**, debug app/test builds pass, ktlint/Detekt **0**, lint **0 errors / 9 unchanged warnings**, whitespace check passes. Final app APK is byte-identical to the original full-run app. Evidence: `reports/widget-language-host-20260925/INVESTIGATION.md`, frozen APKs/installed hashes, all raw per-run logs, controlled capture, original/final source records, host/analyzer XML and aggregate results. All four other original full-suite failures remain open; the complete 114-case instrumentation suite was not rerun and is not declared passed. Prior source/report work, Room 5, backup v5, AtomicFile host support and production behavior remain preserved. No commit, push, PR, publication or automatic follow-up.

## First complete current API 36 instrumentation integration check — 2026-09-25

**The complete configured run finished with five failures; it did not pass.** One unfiltered `AndroidJUnitRunner` execution on dedicated `RowTool_Copy_20260924` / `emulator-5592`, Android 16 API 36 Google APIs x86_64, Pixel Launcher, user 0. Source inventory: 113 methods plus four extra Boolean-theme parameter cases = 117 potential cases. Runner discovery and actual execution both contain 114 identical cases; three existing API 29–32-only language/widget cases are excluded, not verified. Actual terminal statuses: **108 passed, 5 failed, 0 runner errors, 1 existing assumption skip, 0 not reached**. The skipped `WidgetLauncherProbeTest` requires a separate manual launcher operation. All eight parameterized dialog cases passed. No full-suite retry was performed.

Failures, each in the test body:
- `CopySetupFlowTest.archivedCopyOpensIndependentCounterAndUndo`: count==2 timeout after increment (:95). Original failing state was not captured; one predefined same-artifact targeted rerun passed. Cause remains unresolved and the full-run failure stands.
- `DirectCounterNavigationTest.emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack`: later Import data visibility assertion (:90), after successful DIRECT START readiness. One targeted rerun reproduced it; pre-teardown video shows the correct Settings screen with Import data below the viewport. Confirmed test scrolling/visibility assumption, separate from the earlier readiness repair.
- `SettingsScreenContentTest.sectionHeadingsUseTheAppLocaleWithLocalizedFallbackText`: expected Turkish-uppercase Finnish heading not found (:179). Actual heading/resource-locale evidence missing; unresolved, not declared a production defect.
- `SettingsScreenContentTest.preferenceRowsAndActionsInvokeEachCallbackOnce`: global System default selector finds both theme and language-summary nodes (:99/:261). Confirmed test selector ambiguity.
- `WidgetHostTest.externalLanguageChangesRefreshHostedTextWithoutRebindingOrChangingTargets`: test-host `views.getValue(5)` throws after locale-driven host recreation (:467). Confirmed test-host attachment/readiness failure; not proof of production binding loss. Recommended next task is narrowly repairing this test's default-language recreation/attachment boundary while retaining binding/project/count assertions; it has not been started.

Initial emulator boot was unusable (black screen, null focus, System UI/phone ANRs), so no instrumentation was started on it. After preserving evidence and stopping that boot, a cold restart passed boot/unlock/focus/launcher/System UI checks. Baseline: en-US, no app locale override, 640x1280 at 320 dpi, fontScale 1, night=no, animations 0/0/0. Only the dedicated RowTool app/test installation received a one-time clean synthetic baseline. The actual complete-run log has no ANR, crash event or FATAL EXCEPTION. No test isolation mode or per-test wipe was introduced.

Exact complete command: `adb -s emulator-5592 shell am instrument --user 0 -w -r com.finnvek.rowtool.test/androidx.test.runner.AndroidJUnitRunner`. Frozen/installed SHA256: app `D04FE5C8B6090F6928B9082360B705B897B863F784850521F13FA7BC9F2598AC`; test `EFC25912B1F5C56284BD8D0D7D72F7E043A9FCC3DD682EEB7BDB4FF3252EAC53`. All full/targeted executions used this pair. Only diagnostic code change: existing `captureAssertionFailure` wraps the unchanged New project click in `RowToolFlowTest`; no success-path probes/waits, assertions, fixtures or production behavior changed. It was built before the source freeze. All three RowToolFlowTest cases passed, so no new missing-New-project failure snapshot exists.

Fresh unfiltered host/build/static checks on the same final executable sources: **217/217 host tests**, zero failures/errors/skips, identical inventory to the prior 217; debug app and Android-test builds passed; **102/102 Gradle tasks executed**; direct ktlint **0**, Detekt **0**, lint **0 errors / 9 unchanged warnings**. Source hashes prove no code changes during/after device execution. Final whitespace and preservation results are retained with the report.

Evidence and complete per-failure triage: `reports/full-instrumentation-20260925/INVESTIGATION.md`; raw `full-run/runner.log`, `full-run/logcat.txt`, derived ordered case records, discovery/source reconciliation, frozen APK copies/hashes, host/analyzer XML, targeted-run videos, and source-preservation records. Prior widget Space and additional-counter selector repairs remain documented. DIRECT START's earlier demonstrated readiness repair remains valid without claiming every historical intermittent cause. Historical New project and closed-connection causes remain unresolved; neither recurred here. No full API matrix, CI, release, human accessibility review or Google Play delivery verification. No commit/push/PR/publication or automatic follow-up.

## Manual-count instrumentation investigation — 2026-09-25

**F: historical `New project` failure remains unresolved; no repair is claimed.** The original 37/38 grouped run and its case-27 failure in `RowToolFlowTest.tappingCountSetsAnUndoableManualValue` are preserved. It followed the same class's reset/delete-confirmation test. No assertion-boundary screenshot or state snapshot exists for that historical failure, so the actual destination, loading state, Room rows and preference state cannot be established. The later unmerged-node hint alone does not prove stale semantics or readiness.

The existing outer `PrepareApplicationStateRule` awaits Room `clearAllTables()` and last-active null plus fixture-owned settings before the inner Compose rule launches MainActivity. Both use the same application container/database/DataStore. The scenario requires an empty library and UI creation: loaded-empty Projects displays the text button, loading displays a spinner, and a nonempty list exposes an image-backed action by content description. Startup resolution and the first Projects Room result are asynchronous. This supports a readiness hypothesis but does not prove the captured historical cause; no wait, selector change, broader wipe or production change was justified.

Dedicated API 36 `RowTool_Navigation_5592` results are retained individually. Initial method **0/1** and class **0/3** failed after successful New project/name entry at `Espresso.closeSoftKeyboard`: `RootViewPicker.RootViewWithoutFocusException`. A foreground **System UI isn't responding** dialog is proven by window state, screenshot and OS ANR log; this is a separate **E: environment issue**. After selecting its Wait action, the exact original ten-class group passed **38/38** on the original APK pair. Failure-only diagnostics then passed class **3/3**, exact group **38/38**, and three predefined additional class runs **3/3 each**. Both groups retain the original 38-case order. None reproduced the target assertion, so none produced a target failure-state capture. No reproducing predecessor set was established; no permutation search was run. These passes do not cancel the original failure or establish a repair.

Temporary diagnostics reused `AssertionFailureCapture` around the unchanged actual click, with no success-path probes/waits and original-exception preservation. The test was restored byte-for-byte from its task-start copy; production and test code remain unchanged. The actual create → Counter zero → edit count → save 12 → verify 12 → Undo → verify zero flow remains intact. Only this document changes source; reports retain the diagnostic version, exact APK identities, raw logs, derived summaries, source hashes and environment evidence in `reports/manual-count-20260925/INVESTIGATION.md`. Final restored-original method passed **1/1**; final installed app/test APKs are byte-identical to task start. Fresh unfiltered final verification executed **102/102 Gradle tasks**: host **217/217**, zero failures/errors/skips and unchanged inventory; debug app and Android-test builds/compilation passed, ktlint **0**, Detekt **0**, lint **0 errors / 9 known warnings** (five version notices, three Overdraw, one UseCompoundDrawables), and `git diff --check` passed. These are verification of the preserved baseline, not repaired-source results. The task-owned emulator was stopped.

DIRECT START history is unchanged: its independently demonstrated readiness defect remains corrected; the precise cause of every older intermittent failure remains unproven. No full Android campaign, other emulator/phone, CI, external scan, dependency update, release, commit, push or publication was performed. The historical New project failure remains open; the other previously recorded independent grouped failures did not recur in either group.

## Additional-counter instrumentation repair — 2026-09-25

**Confirmed test formatting/selector mismatch, repaired with production unchanged.** `AdditionalCountersUiTest.narrowLargeFontRowsFitBothThemesAndExposeOnlyManualButtons` supplies a content-only fixture: project `p` (main 1), manual counter `manual` (999999, original long name), and main-linked `linked` (42). Its original global `onNodeWithText("999999").assertTextFits()` failed both alone (0/1) and in its class (4/5). Failure-only capture at that unchanged assertion showed the intended manual value visibly rendered as **999,999**, with U+002C grouping under effective en-US. The screenshot, merged/unmerged trees, fixture, scroll position, bounds and timestamped probes establish a stale expected representation, not missing data, clipping or startup readiness. No evidence attributes the failure to a language integration defect or locale leak.

Only `AdditionalCountersUiTest.kt` changes executable code. The test supplies English and Finnish composition-local resources/configuration, checks independent literals `999,999` and `999\u00A0999` plus raw MAX_COUNT/formatting, and scopes exact displayed text to the intended counter's existing unique count-button description. Clicking each selected value verifies its counter ID. The new regression rejects a wrong value and a matching maximum in another row. Application/device locale and configuration are never mutated. The first new Finnish iteration exposed a test-owned pre-recomposition resource read; normal Compose synchronization after changing fixture locale/theme was added, with that intermediate failure retained.

Original 320 dp width, 200% font scale, maximum value, long name, both themes, unchanged text-fit/ellipsis helper, >=48 dp targets, Add counter and scrolling assertions remain. Both English/Finnish themes pass (four cases within one method). Actual records show 640 px width/density 2.0/fontScale 2.0, complete 259/260 px-wide numeric text, no text overflow/ellipsis, enabled manual minus, present-but-disabled manual plus at maximum, readable linked 42 and no linked plus/minus. All four controls screenshots were inspected: long name, full number and controls do not overlap; the plus wraps onto another line. Each row/control is reached through normal scrolling.

Final dedicated API 36 `RowTool_Navigation_5592` results: original method **1/1**; complete class **6/6 in each of three unchanged runs** (18 executions of six distinct methods). The previous ten-class group ran once with existing method order preserved: **37/38 passed**, zero errors/skips (38 parameterized runner cases from 34 distinct methods). One added instrumentation regression explains 37 → 38. All additional-counter and DIRECT START methods pass. Independent remaining failure: `RowToolFlowTest.tappingCountSetsAnUndoableManualValue`, `Failed to inject touch input`, missing merged `New project` node (runner reports one unmerged match), at `createProject:86` / caller `:58`. It was preserved without repair or rerun; no cause is claimed. The old closed-connection failure did not recur.

Fresh unfiltered checks executed **102/102 Gradle tasks**: host **217/217**, zero failures/errors/skips, unchanged host identities; debug app and Android-test builds/compilation passed, ktlint **0**, Detekt **0**, lint **0 errors / 9 known warnings**; `git diff --check` passed. Production APK is byte-identical to baseline. Room schema **5**, backup **v5**, locale ownership/resources, AtomicFile host support, direct-start readiness/capture regressions and all prior work remain intact. DIRECT START's demonstrated missing readiness contract remains corrected in tests; the precise cause of every historical intermittent failure remains unproven.

Evidence: `reports/additional-counter-20260925/INVESTIGATION.md`, preserved `original-group/`, `baseline-apks/`, timestamped `capture-method/assertion-failures/`, per-run raw logs/APK hashes and `rows/`, `instrumentation-summary.json`, `group-order-comparison.json`, `final-host-xml/`, `final-analyzers/` and `final-verification.json`. Runner-derived XML is labeled as derived; capture pulls are cumulative and must be matched by timestamp. Only this document and the named test changed source. No report cleanup/deletion, phone/other-project emulator, full Android suite, other API campaign, external scans, CI, commit, push, PR or publication. The task-owned emulator was stopped.

## DIRECT START readiness follow-up — 2026-09-25

**Confirmed test synchronization defect, repaired and verified under controlled loading. Historical intermittent failures are not individually root-caused.** The five predefined natural attempts with failure-only capture each passed 3/3. Unlike a passing rerun, the controlled experiment demonstrates the missing contract: a committed, preselected project can still have `CounterUiState(isLoading=true, project=null)` while Compose is idle. The real CounterViewModel awaits its first last-active DataStore write before publishing project data. Suspending that write through the existing PreferencesRepository/DataStore injection boundary reproduced the exact immediate DIRECT START assertion failure. A whole-screen capture showed the Counter shell, blank heading and loading indicator; releasing the gate made the correct Counter appear without navigation. This is evidence for the test's readiness defect, not proof of every historical failure's cause. Production code is unchanged.

`AssertionFailureCapture.kt` remains in androidTest. It wraps the actual assertion before Activity/Compose teardown, does no diagnostic work on success, captures the whole screen before Compose inspection, timestamps each probe, reads only an already initialized startup ViewModel, and independently preserves probe errors while rethrowing the original exception. It does not open/close the shared database or add Flow subscriptions. Merged/unmerged tree inspection can synchronize and is explicitly recorded as a later observation. Three helper regressions verify capture before teardown, unchanged exception identity despite a broken probe, missing-root independence, and the zero-probe success path. The first helper check caught omitted child semantics; that print-depth defect was corrected and its failed run retained.

`DirectCounterNavigationTest` now uses `CounterReadiness.kt` at its initial startup boundary: a bounded 5-second condition requires the expected selected project ID, the exact heading semantic, clickable zero count, Add/Remove row controls and Back. It does not navigate or manually invoke the startup resolver. All subsequent import, fallback, deletion/recreation and Back-stack assertions remain intact. `CounterLoadingContractTest` retains the suspending-gate regression and proves rejection of a different project even with the same Counter title, a matching Projects row, and loading that never finishes. These use real Counter/Projects routes and an owned Room database; cleanup runs after the Activity rule closes. No permanent deliberately failing test was introduced.

Final API 36 verification on the dedicated `RowTool_Navigation_5592` emulator: original import method **1/1**, complete class **3/3 in each of three unchanged runs** (nine executions of three distinct methods), and new capture/readiness regressions **7/7**, all with zero failures/errors/skips. The exact previously recorded ten-class grouped selection ran once: **36 passed / 1 assertion failure / 0 errors / 0 skipped**, with all DIRECT START methods passing. The independent failure remains `AdditionalCountersUiTest.narrowLargeFontRowsFitBothThemesAndExposeOnlyManualButtons`: `Failed to perform GetTextLayoutResult action`, missing `999999` at line 77. The earlier `DialogReadabilityTest.replacementImportRetainsCancelAndConfirmBehavior[darkTheme=true]` closed-connection error did not recur in this run; no repair of that separate issue is claimed. Group membership and all 37 method identities match the prior recorded group. This is not a full Android-suite pass.

Fresh unfiltered final checks ran **102/102 Gradle tasks**, including debug app/Android-test assembly and actual host execution with `--no-daemon --no-configuration-cache --rerun-tasks --console=plain`: **217/217 host tests**, zero failures/errors/skips, no host inventory changes; **ktlint 0**, **Detekt 0**, **lint 0 errors / 9 warnings**. The seven new tests are instrumentation tests, so the host count remains 217. Evidence, original exceptions, per-probe timestamps, device settings, exact commands, test order and APK hashes are preserved in `reports/direct-start-20260925/INVESTIGATION.md`, per-run folders, `instrumentation-summary.json`, `final-host-xml/`, `final-analyzers/` and `final-verification.json`. Runner-derived XML is labeled as such; raw runner logs remain authoritative. Android capture pulls are cumulative and must be interpreted by capture timestamp, not folder presence alone.

Only this document, DirectCounterNavigationTest and four new androidTest files changed. Room schema **5**, backup **v5**, AtomicFile host support, production/resources/dependencies/permissions and all prior work remain intact. The task-owned emulator was stopped; no phone, other project's emulator, API 32 campaign, external scanners, CI, commit, push or publication was used. The earlier report-copy deletion denial was respected; no cleanup retry was attempted.

## DIRECT START investigation — 2026-09-24

**Unresolved, with the exact remaining failure.** The original test is `DirectCounterNavigationTest.emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack`, line 84: `onNodeWithText("DIRECT START").assertIsDisplayed()`. This is the synthetic `Direct start` project's uppercase Counter title, checked before Back, Settings or replacement import. The original `selection-35-first.log` was recovered and preserved: this was test 35, immediately after the deletion/recreation method. No original DIRECT START screenshot, semantics dump, XML or failing APK hashes were recovered; the historical navigation screenshots concern the separate, already corrected deletion expectation.

Fresh evidence is in `reports/direct-start-20260924/`, with the complete account in `INVESTIGATION.md`. The task-start dirty tree, staged/unstaged patches, source hashes, original logs and previous host/analyzer reports were preserved before execution. Only the dedicated original API 36 AVD `RowTool_Navigation_5592` (`emulator-5592`) was used. Each run records the APK pair's SHA-256, exact instrumentation command, raw runner output, logcat and timestamps.

- On unchanged current source, the original method passed **1/1**, but the complete class passed **1/3**: both the import method's initial line-84 DIRECT START assertion and the deletion method's initial line-58 DIRECT START assertion failed. These precede import/deletion and are not the old ACTIVE PROJECTS failure.
- The recovered ten-class selection now contains **37 cases**: baseline **35 passed / 1 assertion failure / 1 error / 0 skipped**. All three DirectCounterNavigationTest methods passed. Separate failures were the `999999` text selector in `AdditionalCountersUiTest.narrowLargeFontRowsFitBothThemesAndExposeOnlyManualButtons` and `connection is closed` in `DialogReadabilityTest.replacementImportRetainsCancelAndConfirmBehavior[darkTheme=true]`. Neither was changed. Two added existing methods explain the increase from 35: repeat-editor content coverage and reminder/target haptics; no historical identity is missing.
- Temporary failure-only diagnostics retained the original assertion and rethrew its failure after collecting screen/startup/fixture state. **Five class runs each passed 3/3** (15 executions of three distinct methods). The diagnostic grouped run passed **36/37**, with the same unrelated `999999` assertion failure, zero errors/skips. One final original-method run after a cold emulator reboot passed **1/1**. No failure occurred with diagnostics, so no failing-state screenshot or semantics tree was obtained. These passes do not cancel the baseline failures or establish a repair.

Source tracing confirms the outer fixture rule awaits Room transaction completion and all preference writes before the inner Compose rule launches MainActivity. Fixture and Activity obtain the same application's container, repositories and database. Activity-owned startup resolution and the Counter's combined Room flows then complete asynchronously; the initial assertion does not explicitly await the project becoming visible. Cached Compose source confirms that Compose idleness does not itself establish completed Room work. This supports a synchronization hypothesis, but the actual failed state could not be distinguished from a wrong destination/project or visibility problem within the bounded investigation. No causal claim about locale, widgets, AppCompat, DataStore or the environment is made.

No repair was justified. The temporary diagnostics were removed by restoring this task's byte-identical saved starting test; production and test source, resources, schema **5**, backup **v5**, dependencies and all earlier user work remain intact. The five diagnostic repetitions are not final-source repair verification. `instrumentation-summary.json` and per-run `runner-derived.xml` summarize actual runner statuses; these derived XML files are not historical runner XML. No full Android suite, API 32 follow-up, external scans, CI or publication was run.

Fresh final verification used the unfiltered normal host suite plus debug app/Android-test builds and direct `ktlintCheck`, `lintDebug`, `detekt`, with `--no-daemon --no-configuration-cache --rerun-tasks --console=plain`: **102/102 tasks executed**, **217/217 host tests**, **0 failures/errors/skips**, and no host inventory changes. Actual analyzer XML reports **ktlint 0**, **Detekt 0**, **lint 0 errors / 9 warnings** (five version notices, three Overdraw, one UseCompoundDrawables). `final-checks.log`, `final-host-xml/`, `final-analyzers/` and `final-verification.json` preserve the evidence. The task-owned emulator was stopped. Only this document is a lasting tracked change; no commit or push was made. DIRECT START remains open, along with the separately recorded grouped-test failures.

## Widget picker preview repair — 2026-09-24

**Confirmed app resource defect, repaired and verified.** The original API 32 screenshot in `reports/language-followup-20260924/widget-picker-preview.png` shows the error inside Pixel Launcher's widget picker, before placement/configuration. Fresh evidence is in `reports/widget-preview-20260924/`. Long-press empty home space → Widgets → search RowTool → expand Project counter reproduced it on the unchanged installed bundle APK set. It remained visible at least 23 seconds without interaction, and reproduced after closing/reopening and a separate cold emulator boot. The first immediate screenshot captures the expanding list before its error view appears; it is not a passing preview. A first automated reopen missed the picker; that attempt is retained separately from the corrected, failing reopen.

### Cause and minimal correction

At `21:59:31.755`, launcher PID 1007 reported `Class not allowed to be inflated android.widget.Space` at `layout/widget_preview` line 20. Its stack runs through `WidgetCell.applyPreviewOnAppWidgetHostView` and `AppWidgetHostView.getDefaultView`. The same exception recurred at `22:01:19.511` on reopen and `22:02:51.064` after cold boot. This is the launcher's error view for the declared XML preview, not RowTool's `widget_error`, the placed widget's `widget_loading`, or Glance runtime content. Drag/drop was not the trigger. The older observation's description as brief does not establish spontaneous recovery; the reproduced error persisted.

Pulled installed APKs matched the previous follow-up's three hashes. `installed-provider.txt`, `installed-preview.txt`, and `installed-resources.txt` establish the actual v31 provider selection, `previewLayout=0x7f0c04b5`, separate `initialLayout=0x7f0c04b4`, static `previewImage`, and the disallowed Space. There is one unqualified preview layout and one unqualified edit vector; no alternate night/API layout or theme attribute is involved in that preview. API 36 Pixel Launcher also reproduced the same Space rejection (`modern-before-logcat.txt`). Both launchers selected XML previewLayout; neither result is evidence from a generated or static-image preview.

Only `app/src/main/res/layout/widget_preview.xml` changed in production: the empty 48 dp Space was removed and replaced by a 48 dp top margin on the following controls TextView. Content, colors, control positions, 280 × 280 dp minimum, provider metadata and fallback image are preserved. The preview remains synthetic, with no project selection, binding or count operation. No application theme, Glance runtime, locale catalog/packaging, schema 5, migration or backup v5 change was made.

This follows the official [preview contract](https://developer.android.com/develop/ui/views/appwidgets/previews), [provider metadata](https://developer.android.com/reference/android/appwidget/AppWidgetProviderInfo), and [RemoteViews supported view classes](https://developer.android.com/reference/android/widget/RemoteViews), checked during this task. Space is not supported. A static-image fallback is unnecessary.

### Regression, launcher flows and artifact identity

`WidgetPreviewTest.pickerPreviewInflatesInPlatformHostWithSampleContent` reads installed provider metadata, clones it with the preview as the default layout, and uses an unbound platform `AppWidgetHostView.updateAppWidget(null)`, matching the observed launcher default-view path. It positively asserts all four sample strings. It does not use an ordinary Activity LayoutInflater or create a binding. The original bundle installation failed this test with the same Space exception; the final bundle installation passed **1/1 on API 32 and 1/1 on API 36**. These are instrumentation additions; the normal host inventory remains 217.

- API 32: dedicated `RowTool_Legacy_20260924`, `emulator-5588`, Pixel Launcher **12 / 906**, fingerprint `google/sdk_gphone64_x86_64/emulator64_x86_64_arm64:12/SE1B.240122.005/11418786:userdebug/dev-keys`. SDK `package.xml` registers image revision **8**, while its bundled `source.properties` says **4**; both originals are recorded, so those metadata values are not conflated.
- API 36: existing dedicated `RowTool_Language_20260924`, `emulator-5594`, Pixel Launcher **16 / 907**, fingerprint `google/sdk_gphone64_x86_64/emu64xa:16/BE2A.250530.026.F3/13894323:userdebug/dev-keys`. `modern-fixed*` records the corrected XML sample presentation and installed hashes.
- API 32 final picker checks cover ordinary package update, existing configured widget, close/reopen, cold boot, light/dark and **200% device font scale**. Theme/font changes collapse the launcher group; the final `large-light-top.png` and `large-dark-top.png` show its expanded, scrolled sample content, not merely the collapsed header. The preview retains its existing light palette in both host themes. Native launcher sizing was used; the earlier 320 dp hosted-widget campaign was not repeated.
- A disposable Android user 10 on the same dedicated AVD provided a separate state with no project creation or configured widget. `fixed-empty-user-final*` positively shows the sample and `empty-user-appwidget.txt` records zero widgets for that user. The user was removed after verification; owner data and launcher data were not cleared. Its first check, during package replacement, produced a **different** ResourcesNotFoundException: launcher PID 4272 tried to open the exact deleted pre-update APK path. That diagnostic is preserved in `fixed-empty-user-light-logcat.txt`; after cold boot the same final package renders correctly. This explains that test-state failure, not the original Space defect, and is not a user-facing reinstall/clear-data solution. The owner and API 36 upgrade paths passed without clearing data or restarting their launcher.
- Actual API 32 addition/configuration produced widget **7**, bound to synthetic Legacy widget. Opening it targeted that project; plus changed main and linked counts **0 → 1**, and app Undo restored both **1 → 0**. `new-widget-*`, `widget-plus-after*`, `app-from-widget.xml`, `app-undo*`, and `widget-after-undo*` preserve the flow. Existing binding 5 remained intact; preview-only binding snapshots and post-add/post-Undo binding snapshots are identical within each comparison.

The final debug AAB SHA-256 is `C5C7DB3F69C3AC3AFDB1864AB730F1A28BDBD3A233F5D428E791A9E7D9CF6D23`. Bundletool used the original equivalent English-only API 32/x86_64/440 dpi device specification and existing debug key. The new `fixed-english-api32.apks` SHA-256 is `D704809B15F118C7605281E343C4E9C835D8A8865BAA84FC9723EC4780A1BD97`. Both emulators' three installed hashes match its archive: base `D799238E6CDAB76A462D83EA621775B78D7F31845BF50DFB5C8BE148AE810E3A`, with unchanged x86_64 and xxhdpi split hashes. No language splits were introduced. `final-artifact-hashes.json`, `apks-inventory.json`, `installed-final-hashes.json`, and `modern-final-hashes.txt` give full identities. Runtime verification used this rebuilt bundle set, not the standalone APK.

### Fresh final checks and limits

The unfiltered command in `final-checks.log` ran `:app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:bundleDebug :app:ktlintCheck :app:lintDebug :app:detekt` with `--no-daemon --no-configuration-cache --rerun-tasks --console=plain`: **110/110 tasks executed**, **217/217 host methods**, zero failures/errors/skips and no missing/added host identities relative to the previous 217. The Windows AtomicFile support and its three passing tests are preserved. Copied runner/analyzer XML and `quality-summary.json` establish ktlint **0**, Detekt **0**, lint **0 errors / 9 warnings**: five version notices, three Overdraw, one UseCompoundDrawables. No unrelated warning was fixed. The first check's new-test line-format failure remains in `final-checks-first.log`; only formatting was corrected before the final full run.

`task-start-*` records branch main, HEAD `e05d691996c4289313e2de83c76824fc5697ee48`, staged/unstaged patches and all source hashes. Original report files were not overwritten. Final source preservation and `git diff --check` are recorded alongside the evidence. Only this document, the preview XML and the new instrumentation test are task source changes. Temporary emulators were stopped. No commit, push, PR, publication, full unrelated Android suite, scanner stack, CI or eleven-language campaign was performed. The separate **DIRECT START remains open**. This focused result is not release certification.

## In-app language selection — 2026-09-24

Settings → Appearance now includes **Language**. The scrollable radio dialog offers System default, English, Suomi, Svenska, Deutsch, Français, Español, Português, Italiano, Norsk bokmål, Dansk and Nederlands, in the existing `locales_config.xml` order (`en, fi, sv, de, fr, es, pt, it, nb, da, nl`). A radio changes only the pending selection; Apply commits it and Cancel/dismissal discards it. The summary observes AppCompat/platform selection on configuration changes and resume. An existing regional/multiple-locale override is displayed as an additional current choice without rewriting its tags. An empty override remains distinct from explicit English. Applying the same exact override does not call the setter or request a widget refresh. Radio rows are single accessible actions with a 48 dp minimum; Apply/Cancel remain outside the scrolling list.

### Ownership, initialization and lifecycle

AppCompat **1.8.0** is the only newly declared integration dependency. MainActivity and WidgetConfigurationActivity now extend AppCompatActivity with `Theme.AppCompat.DayNight.NoActionBar`; Compose, Outfit, app-selected theme, splash installation before `super.onCreate`, edge-to-edge, touch filtering, saved-state ownership and trusted widget intents remain in place. No `configChanges` suppression was introduced. Setters run only from Apply after Activity creation, on the UI thread. A rejected synchronous request retains the pending choice; an accepted change is not resubmitted or rolled back for a subsequent widget-refresh error.

On API 29–32, AndroidX `autoStoreLocales` owns the override and its private locale file. On API 33+, framework LocaleManager owns it, including changes/reset from Android Settings. There is no DataStore language preference, custom locale file or locale field in project backups. System default clears the override; resource fallback never stores the resolved device language as an explicit choice. The disabled, non-exported `AppLocalesMetadataHolderService` metadata opts into AndroidX storage and its one-time OS-upgrade handoff. AndroidX storage may perform the disk access documented by Android; no `runBlocking`, global resource mutation or app-written `Locale.setDefault` was introduced.

A **reproduced AppCompat 1.8.0 startup race** required `preserveFrameworkLanguage`: its asynchronous migration can inspect the active-delegate list before the first Activity is registered, mistake an existing framework selection for empty, and overwrite it from the legacy file. Both Robolectric and the isolated API 36 device reproduced `pt-BR → de`. Before constructing AppContainer, startup now checks the framework directly; when a nonempty framework selection already exists, it sets AndroidX's existing metadata-service migration marker to completed. It does not write a language. With no framework override, AndroidX still performs its normal one-time handoff. Tests cover handoff, preservation and a later external reset without legacy replay. This narrow workaround depends on the inspected, pinned AppCompat 1.8.0 migration marker. The device case simulates legacy storage; it is not an actual OS upgrade.

Normal recreation retains the Settings destination and Back behavior; the applied dialog request is cleared before the setter. Editor keys remain project/session IDs, raw numeric drafts and optional selections keep the existing saveable state, and note/copy operation state remains in existing ViewModels/SavedStateHandles. No counting, Undo, acknowledgement, Room schema 5, migration or backup v5 behavior was changed. User-authored text and stored values are not rewritten. Counter/extra-counter numbers use the effective resource locale; note and backup timestamps reuse the existing locale/time-zone/12–24-hour-aware history presentation helper.

### Widgets and offline packaging

Widget rendering and error RemoteViews use `ContextCompat.getContextForLanguage`, including AndroidX's stored override outside an Activity on older platforms. An Application-owned in-memory Configuration flow invalidates live Glance rendering; it is a rendering snapshot, never a source that writes locale settings. The existing update broadcast handles app/system configuration changes, successful Apply and changed configuration on Activity resume. No polling or new service was added. A forced host run exposed an early theme emission before AppContainer assignment; a deterministic regression reproduced it, and moving this rendering snapshot out of AppContainer removed that startup dependency.

Widget IDs, binding generations/tokens, action targets and repository mutations remain unchanged. The error layout now assigns localized text explicitly. The launcher-only preview ImageView retains framework `android:tint`, with a single documented `UseAppTint` exception: RemoteViews is not inflated by AppCompat. Widget sans-serif fallback and configuration-Activity Outfit remain unchanged.

`android.bundle.language.enableSplit = false` packages all eleven languages at installation. Density and ABI settings remain unchanged, and the existing manual locale declaration remains the only declaration mechanism. Five strings were added to each of the eleven catalogs; `resource-preservation.json` proves every task-start catalog byte is unchanged after removing those additions. All 1,101 task-start dependency-verification component blocks remain intact; new dependency metadata and narrowly scoped trust for the already-used AndroidX signing key were added without disabling verification.

Official platform references checked for this integration: [per-app languages](https://developer.android.com/guide/topics/resources/app-languages), [AppCompatDelegate](https://developer.android.com/reference/androidx/appcompat/app/AppCompatDelegate), [ContextCompat](https://developer.android.com/reference/androidx/core/content/ContextCompat), and [bundle language packaging](https://developer.android.com/guide/app-bundle/configure-base).

### Fresh verification

Evidence is under `reports/language-selection-20260924/` (local ignored reports). The final forced command was:

```powershell
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:bundleDebug :app:ktlintCheck :app:lintDebug :app:detekt --rerun-tasks --console=plain
```

- **217/217 host methods passed**, 0 failed/errors/skipped; all original 209 identities remain, plus four picker and four Android locale/startup regressions. `final-host-xml/`, `host-results.json`, `verified-final-fixed.log` record the unfiltered suite and **110/110 tasks executed**. Robolectric covered SDK 32 and 36; that does not establish API 32 device behavior.
- Debug APK, Android-test APK, debug bundle and direct checks passed. Current Check reports show **ktlint 0, Detekt 0, lint 0 errors / 9 warnings**. The warnings are five version notices, three Overdraw and one UseCompoundDrawables; two version notices are new compared with the earlier seven-warning snapshot. `quality-results.json` and copied `final-*Check.xml`, `final-detekt.xml`, `final-lint.xml` contain the actual reports. Old Format reports are not fresh Check results.
- Isolated **API 36**, AVD `RowTool_Language_20260924`, serial `emulator-5594`, synthetic data only. `final-runtime.log`: **4/4 methods** on the final bundle installation cover Apply/Cancel, Settings/Back/project identity, external reset, raw note draft/optional state, unchanged project rows, all eleven languages, backup import preserving language and real hosted widget localization/targeting. The eleven-language loop is one method, not eleven tests.
- `runtime-dialog-final.log`: **1/1 method**, two theme scenarios, actual 320 dp device width and Compose 200% font scale; text-fit/touch assertions and inspected `language-dialog-light.png` / `language-dialog-dark.png`. `runtime-existing-lifecycle-widget.log`: **14/14 existing copy/note/widget methods**, including recreation, non-replay, targets and replacement behavior, at the integration checkpoint before the final startup-owner correction. The affected hosted language scenario was rerun in the final four-method run. `final-editor-restoration.log` records the additional targeted project/reminder draft restoration checks.
- Device Settings UI changed system language English → Swedish: explicit Finnish stayed Finnish; choosing System default inside RowTool produced Swedish. Android's app-language UI selected German/Germany; RowTool displayed `Deutsch (Deutschland)` and preserved `de-DE` after a cold process restart. The `system-*`, `explicit-finnish-*`, `after-android-settings-*` XML/text and `process-restart-german.txt` record these separate manual scenarios.
- **Actual Pixel Launcher cold widget**, final installation: PID 9864 was stopped, verified absent, and a language-change broadcast started PID 9976. Without MainActivity present, widget 12 rendered Finnish, with binding-file bytes identical. Its plus changed count 1 → 2, opening it targeted `LOCALE WIDGET`, and app Undo restored the hosted count to 1. `cold-*` XML, PID, binding and Activity snapshots plus `cold-widget-finnish.png` record this. The launcher cold test used native device size; the separate constrained UI/host tests used 320 dp. At 320 dp the launcher did not offer this existing widget size; no widget sizing redesign was made.
- The final debug AAB was converted with bundletool to an **English-only device specification**, using only the existing debug signing key. `english-final.apks` contains base-master, x86_64 and xxhdpi APKs, with no language APKs. `final-bundle-identity.json` matches all three installed hashes to that archive. Airplane mode on, Wi-Fi/data off: all eleven app languages resolved and switched locally. `bundle-config.json`, `bundle-manifest.xml`, `manifest-inspection.json` and `final-bundle-*.log` record packaging and installation. The inspected merged debug manifest has no INTERNET permission, keeps backup disabled and cleartext disabled, and retains the expected debug-only tooling components and protected widget/WorkManager services. This is debug-bundle delivery evidence, not production signing or Play delivery proof.

Failures were retained rather than overwritten: the initial missing implementation, dependency-signature trust rejection, initial framework-handoff regression, lint/format/Detekt findings, UI selector collisions and a Compose test wait interrupted during recreation all have separate logs. The note test now closes the IME and asserts the checkbox is off before changing locale as well as afterward. `verified-final-failed-xml/` preserves the 216-method run with the startup race; `startup-race-red-xml/` reproduces that exact cause independently before the fix. No original host test or Windows AtomicFile repair was weakened or replaced.

**Limits at this integration checkpoint:** no API 29–32 emulator image was then installed. The focused API 32 follow-up below closes that specific gap. The full Android suite, CI, production signing/Play and a real OS upgrade were not run. The older intermittent DIRECT START observation remains open; this task did not diagnose or dismiss it. Project/reminder optional-field restoration used existing state-restoration tests; actual external-language recreation was exercised specifically with the note editor. No physical phone, other project's emulator, commit, push or publication was used.


## In-app language selection verification follow-up — 2026-09-24

Task-start state (branch `main`, HEAD `e05d691996c4289313e2de83c76824fc5697ee48`, tracked diff hash, full status and untracked source hashes) is recorded in `reports/language-followup-20260924/task-start-*`. All existing dirty work was preserved. This follow-up added only focused Android instrumentation tests and this documentation; production locale, widget, persistence and resource code did not change. The earlier API 36 and debug-AAB reports remain in `reports/language-selection-20260924/`.

### API 32 runtime and real widget

The official `system-images/android-32/google_apis/x86_64` revision 8 image was installed using `android sdk install system-images/android-32/google_apis/x86_64`; the installed package was recognized by SDK Manager and booted as AVD `RowTool_Legacy_20260924`, serial `emulator-5588`, fingerprint `google/sdk_gphone64_x86_64/emulator64_x86_64_arm64:12/SE1B.240122.005/11418786:userdebug/dev-keys`. The CLI exited abnormally after reporting 100% unpack, so package registration and the booted API 32 runtime, rather than that exit code, establish successful provisioning. No new license prompt was accepted. `sdk-install-api32.log`, `android-cli-install-api32.log`, `avd-create-api32.log` and emulator logs record provisioning. API 29–31 were not run.

On the installed app, `LegacyLanguageSelectionTest.systemCancelFinnishSameChoiceAndSystemAgain` checked English System default, a cancelled pending Finnish choice, Finnish Apply, the unchanged-override no-recreation case, System default again, and unchanged project rows. The actual Pixel Launcher was used to add widget ID 5 and bind synthetic `Legacy widget`. It rendered Finnish `Kerrokset` while the device language changed to Swedish; after selecting System default, Settings and the widget rendered Swedish `Språk`/`Varv`. With the launcher in front, `am kill com.finnvek.rowtool` terminated PID 7763 without force-stopping the package; tapping the widget's plus started PID 9426 and changed 0→1. Opening the widget targeted `LEGACY WIDGET`, and the app's Undo restored 1→0. The widget binding file SHA-256 remained `E015EAA0CF7BECA01DC05C1B68CC443F0FB21A8FD4774A61968BC772742EDA68` throughout locale, theme and counting changes. `WidgetHostTest.legacyLanguageChangesRefreshHostedTextWithoutRebindingOrCounting` also used a real `AppWidgetHost` and verified Finnish, Swedish and System default labels, unchanged binding/other project, plus/minus targeting and linked-counter counts 0→1→0. Its first attempt used an inactive AppCompat delegate; its second called the setter off the main thread. Both fixture failures are preserved in `legacy-widget-first.log` and `legacy-widget-second.log`; the corrected test passed.

With System default selected, a note draft `Unsaved draft on v32` and its checked attach-count option survived the device-language change Swedish→English; the same `Legacy widget` project remained and a copied Room database had zero `project_notes` rows. The draft was explicitly discarded afterward. German was then selected, and a targeted process termination (PID 9426, not force-stop) followed by `am start` created PID 12087 and restored German Settings text and selection. `am kill` did not terminate the process, including when Pixel Launcher was foreground; `legacy-german-process-restart.txt` and `legacy-german-process-kill-corrected.txt` retain those attempts, while `legacy-german-process-terminated-root.txt` records the actual process-death test. Light Finnish/Swedish and dark German launcher screenshots are `widget-fi-device-sv-stable.png`, `widget-system-sv.png` and `widget-dark-de.png`. The API 32 widget picker briefly showed “Can't load widget” in its preview; the configured launcher widget rendered and operated. No preview change was made in this language-focused task. The earlier DIRECT START observation remains open.

### Device-specific debug AAB, offline

The current `app/build/outputs/bundle/debug/app-debug.aab` SHA-256 is `E312B35927A21344BF070DC688CE7E4EB724EF06D66B679DCDF949F5586B8CAA`. Official bundletool 1.18.3 generated `english-api32.apks` from it with `english-api32-device.json` (`sdkVersion:32`, `x86_64`, density 440, `supportedLocales:["en"]`) using its existing debug key. The APK set SHA-256 is `DC64CF50187A4848A1A1ED05DE4D784FFE6035DDC6FDD54A84C87DD40393B16E`, byte-identical to the earlier `english-final.apks`; it contains only base-master, x86_64 and xxhdpi APKs. The three APKs installed on API 32 match the archive hashes in `installed-api32-hashes.json`. The bundle config has `LANGUAGE` splitting disabled, and the installed base APK's `string/settings_language` resources contain default English plus `da, nb, de, fi, nl, fr, es, it, pt, sv` (`settings-language-resource-inventory.txt`).

Airplane mode was on and Wi-Fi/data disabled during `offline-aab-eleven-language-final.log`. Its one instrumentation method switched all eleven supported app languages and asserted distinctive localized `settings_language` strings from the installed app. It also seeded and compared a project, history entries including timestamps, a reminder acknowledgement, and saved note contents before and after the switches. This is device-specific **debug** AAB installation and runtime evidence on API 32, in addition to the earlier API 36 run. It does not establish minified release signing or Google Play delivery.

### Nine lint warnings

The final `final-lint.xml` has zero errors and these nine warnings. Every location is app-owned source/configuration; none points to generated or dependency-owned source. The earlier seven-warning snapshot is `reports/widget-host-repair-20260924/quality-final/lint-results-debug.xml`.

| ID | Current location | Message / classification | Earlier seven |
|---|---|---|---|
| `AndroidGradlePluginVersion` | `gradle/libs.versions.toml:4` | AGP 9.4.0 → 9.4.1 notice | Yes |
| `GradleDependency` | `gradle/libs.versions.toml:6` | KSP 2.3.11 → 2.3.12 notice | Yes |
| `GradleDependency` | `gradle/libs.versions.toml:9` | AndroidX Core 1.19.0 → 1.19.1 notice | No |
| `GradleDependency` | `gradle/libs.versions.toml:11` | Navigation Compose 2.10.1 → 2.10.2 notice | No |
| `NewerVersionAvailable` | `gradle/libs.versions.toml:26` | Compose stability analyzer 0.13.0 → 0.15.0 notice | Yes |
| `UseCompoundDrawables` | `app/src/main/res/layout/widget_preview.xml:9` | Preview ImageView + TextView layout performance suggestion | Yes |
| `Overdraw` | `app/src/main/res/layout/widget_error.xml:4` | Root background versus inferred theme | Yes |
| `Overdraw` | `app/src/main/res/layout/widget_loading.xml:4` | Root background versus inferred theme | Yes |
| `Overdraw` | `app/src/main/res/layout/widget_preview.xml:8` | Root background versus inferred theme | Yes |

The two extra warnings are version notices that were absent from the earlier XML, not demonstrated language-selection defects. The widget-layout suggestions also predate language integration. None was changed or suppressed.

### Final checks and remaining limits

The unfiltered `--rerun-tasks` command in `final-gradle-passing.log` executed 110/110 tasks: **217/217 host methods**, zero failures/errors/skips, debug APK, Android-test APK, debug AAB, ktlint zero findings, Detekt zero findings, lint zero errors/nine warnings. `final-host-xml/`, `final-lint.xml`, `final-detekt.xml`, `final-ktlint-android-test.xml` and `final-artifact-hashes.json` preserve the reports. The final installed AAB hash remained identical to the freshly built AAB. The final API 32 targeted run passed **3/3** (`final-api32-targeted.log`), and `git diff --check` passed. An intermediate full Gradle run failed only on formatting in the two newly edited instrumentation files; `final-gradle.log` retains it, and the final run passed after the formatting correction.

Scope limits: API 29–31, physical hardware, the full unrelated Android suite, CI, release/Play delivery and an actual OS upgrade were not checked here. No production defect in the language-selection path was reproduced, so no production repair was made. No commit, push, PR or publication was performed.


## Widget host-test repair — 2026-09-24

The seven failures reported after Copy counter setup have one demonstrated **Windows host-runtime cause**, not seven production defects. In the actual SDK 36 jar (`16-robolectric-13921718-i7`) used by Robolectric 4.17 on Oracle JDK 21.0.12, `AtomicFile.finishWrite` calls a private `rename` helper using `File.renameTo`. Replacing an existing destination fails on this host; Android's helper logs the failure and returns. The first binding is persisted, subsequent `.new` files are not committed, and the next `openRead` discards the pending file while retaining the old base. This explains both accepted stale actions and the missing ten increments.

Before running Gradle, the actual dirty working tree was recorded (branch `main`, HEAD `e05d691996c4289313e2de83c76824fc5697ee48`, tracked/staged patches, hashes of all 276 existing tracked/untracked files, and copies of untracked sources). Existing XML and Copy-task console evidence were copied into `reports/widget-host-repair-20260924/baseline/`. Its `current-xml` is the final **206-test / 199-pass / 7-failure** baseline. The older `copy-setup/first-full-host` directory is an intermediate **204-test** report and is not substituted for that baseline.

### Exact original failures

Classes below are in `com.finnvek.rowtool.widget`. All are test-body failures, not initialization/teardown errors. Full stacks are preserved in `baseline/failures.json`; the meaningful frame is in the named test unless stated otherwise. The injected rollback/interruption exceptions are deliberate inputs, not additional runner errors.

| Class and method | Exception, expected / actual, original frame | Operation reached and actual cause |
|---|---|---|
| `WidgetBindingsTest.independentBindingsRejectOldActionsAndSurviveRestart` | `AssertionError`, null / `a`, line 32 | Both binds and token comparison reached. The second write did not replace the original binding; the stale action ran. Later restart/removal checks were not reached. |
| `WidgetBindingsTest.interruptedReplacementFailsClosedAcrossRestart` | `AssertionError`, null / `a`, line 65 | Replacement callback and injected unknown outcome reached. The pending marker was not committed, so the restarted store accepted the old action. |
| `WidgetBindingsTest.reconfigurationCannotInterleaveValidatedAction` | `AssertionError`, null / `a`, line 90 | The validated action and awaited rebind completed. Mutex coordination worked; the persisted token stayed old. |
| `WidgetBindingsTest.queuedOldActionIsRejectedAfterReplacement` | `IllegalStateException: Old action executed`, line 107; `WidgetBindings.withBinding` line 74 | Replacement completed, but the queued old callback actually ran because neither pending nor cleared state replaced the base. |
| `WidgetBindingsTest.replacementInvalidatesEvenIdenticalIdsAndRollbackPreservesBinding` | `AssertionError`, null / `same/id`, line 50 | SQL rollback and preserved-binding assertion passed; the successful replacement callback ran, but binding invalidation was not persisted. |
| `WidgetCountingTest.simultaneousAppAndTwoWidgetsUseCurrentRoomCountAndHistory` | `AssertionError`, 30 / 20, line 104 | All 30 asynchronous calls completed. App and first widget performed ten mutations each. The second widget's ten calls returned null before `repository.mutate` because its binding was not persisted. Subsequent history/Undo checks were not reached. |
| `WidgetCountingTest.noOpArchiveDeleteAndRebindingCannotChangeAnotherProject` | `AssertionError`, null / `ProjectArchived`, line 133 | No-op, archive and rebind operations ran. The stale action reached the still-bound first archived project; the repository correctly returned `ProjectArchived`. Later deletion checks were not reached. |

An unchanged single-method run reproduced the first failure, followed by an unchanged two-class run reproducing **the same seven failures in nine tests**. A synchronous `AtomicFileHostTest` then reproduced the lower-level cause without Room, coroutines or copy operations: after two completed writes, `base=first`, `new=second`, with `Failed to rename ...` in ShadowLog. Applying only the host rename correction made that same test pass (`base=second`, no `.new`, no error log). This rules out copy-test ordering, asynchronous fixture cleanup and repository contention as necessary causes. No trustworthy pre-copy code/environment snapshot was available: the historical 194-pass summary does not establish when the incompatibility began or blame Copy counter setup.

### Minimal repair and scope

Only host-test support changed: `src/test/java/com/finnvek/rowtool/test/ShadowAtomicFile.kt`, `AtomicFileHostTest.kt`, and `src/test/resources/robolectric.properties`. The shared [Robolectric shadow](https://robolectric.org/extending/) replaces only the SDK 30+ `AtomicFile.rename` platform operation with `Files.move(ATOMIC_MOVE, REPLACE_EXISTING)`. Real files, Android's remaining AtomicFile implementation, binding serialization, generation checks, mutexes, replacement coordination and Room/repository behavior remain exercised. There is no delete-before-replace gap or non-atomic fallback. Actual host I/O failures propagate instead of reporting a successful commit; that fault reporting is intentionally stricter than Android's log-only helper.

Three boundary regressions verify replacement of an existing file, preservation of a committed file after `failWrite`, and an actual rename obstruction that must fail. All original test bodies/assertions remain byte-identical. The sole local Detekt suppression (`UtilityClassWithPublicConstructor`) documents Robolectric's required public no-argument shadow constructor; the inspected `ShadowMetadata` implementation calls `Class.getConstructor()`, so converting the shadow to a Kotlin object is invalid. No check task was disabled.

Production code, Gradle build configuration, dependencies/verification, schema **5**, migrations, backup **v5**, all eleven resource catalogs, UI, permissions and prior user work are unchanged. Coverage remained enabled throughout. No emulator or physical device was used: this is a host-runtime fixture correction, with no fresh hosted-widget/launcher or copy-flow device claim. The older intermittent **DIRECT START remains open and separate**.

### Fresh verification and evidence

All paths below are under `reports/widget-host-repair-20260924/`. XML directories preserve each invocation separately, including failures. Counts come from runner XML, not a union of passing subsets.

| Invocation | Executed | Passed | Failed | Errors | Skipped | Evidence |
|---|---:|---:|---:|---:|---:|---|
| Unchanged single-method reproduction | 1 | 0 | 1 | 0 | 0 | `repro-one.log`, `repro-one-xml/` |
| Unchanged original affected classes | 9 | 2 | 7 | 0 | 0 | `repro-group.log`, `repro-group-xml/` |
| Isolated AtomicFile before / after correction | 1 / 1 | 0 / 1 | 1 / 0 | 0 / 0 | 0 / 0 | `atomic-red*`, `atomic-green*` |
| All widget classes, copy repository/ViewModel, backup/preferences and host-file regressions | 43 | 43 | 0 | 0 | 0 | `targeted.log`, `targeted-xml/` |
| Complete normal host run 1 | 209 | 209 | 0 | 0 | 0 | `full-1.log`, `full-1-xml/` |
| Complete normal host run 2 | 209 | 209 | 0 | 0 | 0 | `full-2.log`, `full-2-xml/` |

Both complete runs include all original 206 class/method identities plus exactly the three `AtomicFileHostTest` methods, across 32 XML suites. Source hashes were checked before and after the second invocation; no test filter or diagnostic build override remains. Every host invocation used `--rerun-tasks`; both full logs show actual `:app:testDebugUnitTest` execution and **32/32 actionable tasks executed**, not UP-TO-DATE. `results.json` records counts, timestamps, added methods and empty missing-baseline comparisons; `final-source.csv` and `full-2-source-check.txt` record source identity.

Exact PowerShell commands (run from the repository root; `--no-daemon` makes each invocation's Gradle process exit):

```powershell
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest --tests 'com.finnvek.rowtool.widget.WidgetBindingsTest.independentBindingsRejectOldActionsAndSurviveRestart' --rerun-tasks --console=plain
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest --tests 'com.finnvek.rowtool.widget.WidgetBindingsTest' --tests 'com.finnvek.rowtool.widget.WidgetCountingTest' --rerun-tasks --console=plain
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest --tests 'com.finnvek.rowtool.test.AtomicFileHostTest' --rerun-tasks --console=plain
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest --tests 'com.finnvek.rowtool.widget.*' --tests 'com.finnvek.rowtool.test.AtomicFileHostTest' --tests 'com.finnvek.rowtool.data.repository.CopyCounterSetupTest' --tests 'com.finnvek.rowtool.ui.screens.projects.CopySetupViewModelTest' --tests 'com.finnvek.rowtool.data.repository.BackupAndPreferencesRepositoryTest' --rerun-tasks --console=plain
# Two separate invocations, unchanged final source, no filters:
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest --rerun-tasks --console=plain
.\gradlew.bat --no-daemon --no-configuration-cache :app:testDebugUnitTest --rerun-tasks --console=plain
.\gradlew.bat --no-daemon --no-configuration-cache :app:assembleDebug :app:assembleDebugAndroidTest :app:ktlintCheck :app:lintDebug :app:detekt --rerun-tasks --console=plain
git diff --check
```

The forced build/quality command passed with **91/91 actionable tasks executed**, including debug APK, Android-test APK/compilation and direct checks. Inspected reports: **ktlint 0 findings, Detekt 0 findings, lint 0 errors and 7 warnings** — AndroidGradlePluginVersion (1), GradleDependency (1), NewerVersionAvailable (1), Overdraw (3), UseCompoundDrawables (1). Existing Kotlin/Compose-test deprecation and manifest-removal warnings remain. `build-quality-final.log`, `quality-final/` and `quality-results.json` retain the evidence. The initial Detekt constructor finding is preserved in `detekt-first.xml`; it was not treated as clean based on the task's nonblocking exit status.

Further causal detail and actual SDK bytecode are in `causality.md`, `AtomicFile-bytecode.txt` and `shadow-constructor-bytecode.txt`. Final hash comparison found only this document changed among the 276 original files, plus the three new host-test support files; the staged diff is unchanged. The final task diff was inspected and `git diff --check` passed (existing LF/CRLF conversion notices are not whitespace errors). No task-owned Gradle/test-worker process remained. No external scanner stack, CI change, publication, commit, push or new feature was performed. Historical Copy/widget verification below remains a record of those earlier runs, not the current host-suite result.

## Copy counter setup — 2026-09-24

Projects now offers **Copy counter setup** in every active and archived project menu. It opens a new-project review with an empty required name, the source name, the existing editable unit/start/target/repeat fields, a read-only ordered extra-counter review, and an explanation of excluded data. Create and Cancel stay outside the scrolling copy form and have explicit 48 dp minimum height. Actual edits require discard confirmation; loading defaults alone does not. The ordinary editor retains its original scrolling actions and spacing.

`CounterRepository.copySetups` captures the source and visible extra-counter configuration in one Room transaction. `CopySetupStore` creates the reviewed project and children in a single transaction under the existing mutation mutex. Ordinary creation and copying share the in-transaction project limit and project validation helper; additional names use the existing validator. The limit includes archived projects. Child failures roll back the project too. Display order remains insertion/rowid order, including distinct counters with duplicate names.

The copied settings are unit, reset/start value, optional target, repeat length and explicit repeat start, plus visible extra-counter names and manual/follow-main modes. The new active project has a fresh ID, clock timestamps, its main count equal to the reviewed reset/start value and fresh owned extra-counter IDs with counts zero. A linked counter starts at zero even when the main starts at one. The 75-to-1 example retains target 120, repeat 8/start 11, and linked/manual counters at zero. Source progress, IDs, timestamps, hidden counters, history/effects, reminders/revisions/acknowledgements, note/count/time, archive state, widget bindings/tokens/generations and app settings are excluded. Source rows remain unchanged; counting and Undo use the ordinary implementation and remain isolated after source deletion.

`CopySetupViewModel` distinguishes loading, read error, reviewed snapshot, saving and committed identity. SavedStateHandle stores primitive configuration Bundles and the operation identity, not Room entities or the library; field input uses the existing saveable editor state. Later source edits never repopulate the draft. The source must still exist at commit. Backup replacement rotates a database-instance copy epoch inside its existing transaction, rejecting an old snapshot even when restored IDs match. New database instances conservatively invalidate unfinished old source snapshots as well. No persistent relationship, schema column or second import mutex was added.

The new project identity is allocated with the draft. Repeat submissions return that committed project instead of inserting again. The ViewModel synchronously guards submission, retains write failures for retry, records successful identity before preference/navigation work, and retains pending/completed state through Activity recreation. Preference I/O failure does not turn a committed copy into a failed creation. Completion is scoped to its source/session and waits for the resumed destination. Widget-open requests defer while the copy editor is visible, using the existing navigation deferral approach. Copying never invokes widget replacement or creates a binding; the copy is available through the ordinary active-project picker.

### Verification actually performed for this feature

- Full `testDebugUnitTest`: **206 methods, 199 passed, 7 failed, 0 errors**. All **12 new methods** passed: eight real Room/repository tests and four ViewModel tests. Coverage includes empty and archived sources, exact settings/counts/order/IDs, hidden-row exclusion, unchanged source rows, snapshot ownership, deleted source, project limit, invalid input, SQL-trigger child rollback/retry, repeated submissions, read/write failures, saved snapshot/completion, preference failure, source deletion independence, widgets and normal v5 export/restore.
- The seven failures are existing `WidgetBindingsTest` (five methods) and `WidgetCountingTest` (two methods). Running only those two unchanged test classes reproduced **7 failures out of 9**, without running copy tests. Observed failures concern stale bindings still being accepted and expected 30 increments producing 20. Their root cause was not established or fixed in this feature. Both full and isolated failing logs are retained; the full host suite is **not green**.
- `assembleDebug`, `assembleDebugAndroidTest`, direct `ktlintCheck`, `lintDebug`, and `detekt` completed successfully. Parsed ktlint/Detekt reports have zero findings. Lint has zero errors and seven warnings: **AndroidGradlePluginVersion (1)**, AGP 9.4.1 available; **GradleDependency (1)**, KSP 2.3.12 available; **NewerVersionAvailable (1)**, stability analyzer 0.15.0 available; **UseCompoundDrawables (1)**, widget preview layout suggestion; **Overdraw (3)**, widget layout root backgrounds also painted by the theme. These are distinct update/layout warnings, not all dependency notices. Existing Compose test-rule deprecation warnings also remain. Final Gradle checks used `--no-configuration-cache`; no build configuration or dependency was changed.
- On the task-created isolated **RowTool_Copy_20260924 / emulator-5596 / API 36**, **27 distinct Android test methods** eventually passed: five `CopySetupFlowTest`, nine `ProjectEditorDialogTest`, nine `ProjectsScreenContentTest`, three `DirectCounterNavigationTest`, and `WidgetHostTest.hostedWidgetsConfigureCountRefreshRebindAndReplace`. The last combined editor/copy run passed all 14 methods; the other 13 passed in the preceding combined run. This is a union of explicit method results, not a claim that every earlier run passed.
- The five new device methods cover active review/cancel without writes or selection changes, edited draft recreation/discard, archived source creation/open/count/Undo, pending Room creation across recreation without duplicates, and real widget configuration selecting the copy without changing the source binding. The layout method loops over two themes; those loops are **not additional test methods**. It was additionally run at **320 dp width / 200% font scale with keyboard visible**, checking visible Create/Cancel and minimum action height. Screenshots of both themes were inspected.
- Initial failures remain in the evidence directory: an over-limit synthetic source name was shortened to a valid long name; ViewModel tests were changed to await real Room completion instead of only draining the test scheduler; copy buttons gained explicit minimum height; a newly added separator encoding issue was replaced with an ASCII colon. One existing editor selector now explicitly selects the `1` choice rather than also matching repeat input `1`. The repeat-save regression now closes the keyboard, asserts Save visible/enabled, and checks the unchanged expected offset on idle. An idle wait alone did not fix its first retry. No assertions were removed. DIRECT START's historical root cause remains open despite the three passing methods.
- Resource baselines were captured from the actual working tree: **211 existing base-catalog keys and 209 in each of ten translated catalogs**. Exactly eight feature strings were added to each. Parsed keys, text, placeholders, plural content and attributes were preserved; removing the eight added lines also reproduces every original catalog **byte for byte**. No unexpected resource difference was found.
- Room remains **5**, exported schemas/migrations are unchanged, backup remains **v5**, and no backup codec/history-limit/widget-binding implementation changes were made. Source and copied projects pass normal v5 round-trip assertions. The installed debug APK identity was checked against the local artifact. Final diff checks are clean; prior build and feature work remains intact.

Evidence: `app/build/reports/copy-setup/`, especially `final-checks.log`, `widget-host-isolated.log`, `verified-build.log`, `device-final.log`, `device-editor-final.log`, `device-layout-final.log`, resource comparison JSON and preserved early failures. The full Android suite, CI, other API levels, physical devices, all locales' rendered layouts, and actual process-death/power-loss recovery were not exercised. Activity recreation and injected SQL/preference failures are separate from those boundaries. Task-owned temporary emulator/Gradle processes were stopped after verification. No commit, push, pull request, publication or second feature was performed.


## Project home-screen widget — 2026-09-23

The project widget is implemented with stable AndroidX Glance **1.2.0**. Add **Project counter** from the launcher, explicitly select an active project, and press Save. There is no automatic selection, even for one project. The 48 dp pencil action changes only that widget's project; cancellation preserves its binding. The supported minimum is **280 × 280 dp**, with horizontal and vertical resizing. Pixel Launcher on the tested Pixel 7 profile allocated a minimum 4 × 4 grid; actual spans remain launcher-dependent. Picker previews use only a synthetic scarf project.

The widget displays the name, localized main count, rows/rounds, plus/minus, and the existing due-reminder count. Name/count open the selected Counter; the reminder indicator opens its existing reminder dialog without acknowledgement. The reminder area retains its height and uses a compact localized label such as "Reminders: 1". The Finnish 200% test exposed vertical clipping with the longer app plural; the compact widget label fixes it without changing reminder rules or button positions. Plus/minus call the existing `CounterRepository.mutate` transaction: following counters, exact Undo effects, no-op rules, and the 100-entry history limit are shared with the app. Manual counters remain unchanged. No widget Undo, direct count editor, note content, notification, or separate count persistence was added.

`widget/WidgetBindings.kt` persists per-instance project IDs and random generation tokens with AtomicFile in `noBackupFilesDir`. A shared mutex covers binding validation through mutation, reconfiguration, and replacement. Explicit immutable PendingIntent identities include widget ID, operation, and generation. A durable pending marker is written before replacement starts; committed replacement invalidates all bindings, including identical restored IDs. A known SQL rollback restores the old state; an interrupted/unknown result fails closed until explicit selection. Binding I/O failure before replacement returns an import failure without changing Room. Room remains version **5**, manual backup remains **v5**, and widget bindings are never exported.

Archiving retains the binding, disables counting, and opens Projects; restoring re-enables counting. Missing projects require a new selection and never select another project automatically. Removing a widget removes only its binding. Application-wide Room invalidations request post-commit updates independently of Counter/ViewModels. Glance manages rendering/lifecycle updates, while live Room/binding flows supply current snapshots. Theme/configuration changes also request updates. Data/read failure shows an error and an em dash; retry refreshes only. Composition failure uses a safe project-selection fallback and never repeats a committed mutation.

Widget activity requests are generation-validated and use the existing project route encoding. Requests wait while the note editor is visible, so saving/exiting the draft precedes navigation. Consumed requests are cleared across Activity recreation. Navigation runs on Main after asynchronous reads. MainActivity and widget configuration retain obscured-touch filtering. Configuration uses Outfit and existing Compose theme/components; Glance text uses the platform sans-serif fallback. Both normal widget themes, localized text/numbers, and all eleven string catalogs are implemented.

Only the Glance dependency was added to the application build; the pre-existing dependency-check edits remain. The resolved additions include Glance modules 1.2.0, core-remoteviews 1.1.0, and transitive WorkManager runtime/ktx 2.7.1. Locking and signature/checksum verification metadata were updated without unrelated version upgrades. Manifest findings are recorded in the manifest section below.

### Widget verification actually performed

- **194 host test methods, 29 XML suites, zero failures/errors**: existing tests plus binding generation/rollback/interruption/I/O, concurrent app/two-widget mutations, shared effects/history/Undo, count limits, URI parsing, and replacement binding-write failure.
- `assembleDebug`, `assembleDebugAndroidTest`, direct `ktlintCheck`, `lintDebug`, and `detekt` passed. Inspected ktlint and Detekt reports contain zero findings. Lint has zero errors and seven warnings: three existing available-version notices, three static widget-layout Overdraw warnings, and one preview UseCompoundDrawables suggestion. No checks were disabled.
- On isolated **API 36 / Android 16**, all **five WidgetHostTest methods** passed with actual AppWidgetHostView/RemoteViews and real pending-intent clicks. They cover explicit selection/recreation/cancellation/empty state, two distinct and then shared targets, counting/app refresh, stale generation rejection, archive/restore/replacement, failure/retry/render fallback without repeat counting, note draft preservation, reminder opening without acknowledgement, and minimum-size touch bounds.
- The minimum-size method also passed separately with **320 dp device width / 200% font scale**. Its actual host stayed **280 × 280 dp**. Long name, 999,999, reminder, and both themes were captured and visually inspected; count text was not ellipsized, and both count targets remained at least 48 dp and inside the host. Configuration selected the long project and confirmed Save at those settings. The same minimum-size test additionally passed in Finnish at 320 dp / 200% on API 37.1 after the compact reminder-label fix; both Finnish theme screenshots were inspected.
- **13 existing device regression methods** passed: HistoryNavigationTest, ProjectNoteLifecycleTest, ReminderRouteTest, and DirectCounterNavigationTest. The historical DIRECT START issue remains open: this passing selection does not establish its root cause or a fix.
- A separate **Pixel Launcher 16 (versionCode 907)** path added two widgets through the real picker/configuration UI. Each counted its own project, with Room assertions confirming 1 and 2; opening Beta reached its Counter, and an app increment appeared on the launcher. The widget's own project-selection action reconfigured the second instance. Launcher resizing changed the host from 946 × 1218 px to 946 × 1533 px at density 420. Archive/open-Projects, restore, and identical-ID replacement requiring selection were observed.
- On the newest installed system image, **android-37.1/google_apis_playstore_ps16k/x86_64** (device reports **API 37, full API 37.1 / Android 17, REL**), the same five real WidgetHostTest methods also passed. API 36 was the oldest locally installed supported image; minSdk remains 29. The first API 37 emulator attempt stayed offline; a kernel-logged restart completed and the tests ran. API 37 uses the app-owned test host; the manual Pixel Launcher path above was API 36.
- Actual process death was tested by terminating only the debug app PID (8425); a subsequent launcher plus started PID 8557 and changed the intended Room count once. This is distinct from configuration Activity recreation; an intentional force-stop product scenario was not tested.

Evidence is under `app/build/reports/widget-feature/`: `compact-label-checks.log`, `device-final-api36-compact.log`, `device-regressions-api36.log`, `layout-320-200-api36.log`, `device-final-api37.log`, `layout-fi-320-200-api37-final.log`, launcher XML/screenshots/probe logs, and preserved failed runs. Early failures included test setup/selector mistakes, an invalid layout start value, and the widget navigation main-thread issue; they remain in their original logs. The first short launcher data probe exited before asynchronous archive rendering completed; the settled launcher state and actual host archive test were verified separately.

The whole Android suite, hosted CI, signed release, physical phone, every launcher, and every locale's rendered layout were not tested. Storage and interruption failures use targeted test injection; there is no claim of a real disk-full or power-loss run. During resource editing, the base catalog was recovered after an encoding/write failure using the last successful resource-merge artifact; all 199 pre-existing string/plural values were checked against that artifact. Text was preserved, but byte-for-byte formatting/order identity is not claimed for that catalog. Task-owned emulators and the task-created idle Gradle daemon were stopped after verification. No commit or push was made.


Feature update **2026-09-23**: **Recent counting actions** is a read-only project subview opened from the Counter menu or any Projects row menu, including archived projects. It exposes existing retained Undo records, not a complete event log. The history verification record below is the authority for this feature's checks.

## Recent counting actions: implementation and verification

`Screen.history(projectId)` URI-encodes the explicit project ID. The navigation destination retains that argument across Activity recreation and returns to its caller. It never selects the project as last active. A confirmed missing project returns to Projects; loading and read errors do not trigger that return. Archived projects remain archived and do not enter Counter.

`CounterHistoryDao.observeHistory` reads the project, history headers, effects and current counter names in **one SQL statement**. Its subquery limits headers to `CounterConstants.MAX_HISTORY_ENTRIES` before joining effects. Headers are ordered by descending history ID, matching Undo; timestamps do not determine ordering. The Flow invalidates on project, history, effect and counter changes. `RecentHistoryStore` groups by history ID and preserves each counter's identity and actual before/after values. It omits unchanged values, including the main counter for manual additional-counter actions. No new history is reconstructed or written.

`HistoryViewModel` distinguishes loading, content (including a successfully empty list), error/retry and missing project. The UI uses stable history IDs as list keys and shows every stored changed value. Known persisted reasons have localized labels; unknown reasons use “Counter change”. There are no historical name snapshots: the screen explains that names are current saved names, marks hidden counters “Deleted”, and provides a localized fallback when a name is unavailable. Date/time uses the app resource locale, current device timezone, 12/24-hour preference and seconds. Missing, negative or out-of-display-range timestamps show a neutral label, never the current time. The stored historical timezone is not known.

The explanation formats the shared retention limit and states that Undo removes the reversed action. Empty history says “No retained counting actions.” All 17 new strings exist in all 11 language catalogs. The view uses existing Outfit/theme styles and dividers, stacked before/after labels, scrolling and a back button. No new main tab, editing, Undo control, search, statistics or export was added.

Room remains **5**, backup remains **v5**, and v1-v4 support, schema files, migrations, retention, counter mutation writers and atomic Undo are unchanged. SHA-256 comparisons against the task's starting files confirmed unchanged `app/build.gradle.kts`, database/migrations, all five exported schemas, and backup codec/models/repository. Existing uncommitted work was preserved; no commit or push was made.

Verification performed on **2026-09-23**:

| Check | Actual result |
|---|---|
| `:app:testDebugUnitTest` | **183 test methods**, zero failures/errors in 26 JUnit XML suites; includes **9 new methods**: 6 Room/repository, 2 presentation, 1 ViewModel. Multiple assertions/scenarios within a method are not counted as separate tests. |
| Host history coverage | Empty/missing/archived reads; increment/decrement/set/reset; grouped effects; clipped counts; manual counter without fabricated main change; rename/delete and same-name identities; history-ID ordering despite equal/backward times; project isolation; Undo; no-op; 101 actions retaining 100 with effects; old retained data without age pruning; read-only project state; loading/error/retry; unknown reason; date/time formatting. |
| `:app:assembleDebug`, `:app:assembleDebugAndroidTest` | Passed, including `:app:compileDebugAndroidTestKotlin`. |
| Direct `:app:ktlintCheck` | All four check XML reports have zero findings. |
| Direct `:app:detekt` | XML report has zero findings. Exit status alone is insufficient because the existing Gradle configuration ignores Detekt failures. |
| Direct `:app:lintDebug` | Zero errors, **3 version-availability warnings** (AGP, KSP, Compose stability analyzer). No dependency upgrades were made. |
| Isolated Android instrumentation | **8 test methods passed**: 6 `HistoryNavigationTest`, 2 `HistoryLayoutTest`, on own API 36 `RowTool_History_20260923`, serial `emulator-5596`. No physical phone or other emulator was used. |
| Device scenarios | Counter and Projects entry/back; encoded non-UUID ID; last-active changes and Activity recreation; archived read-only state; actual repository count/Undo/rename/delete emissions; same-ID v5 replacement with effects; v1 replacement clearing old rows; project deletion with recreation and while stopped; loading/error/retry; both themes at 320 dp and 200% font, long names, large values, deleted counter, empty state and scrolling to the last action. |
| Render checks | Compose text-layout/display assertions and six emulator screenshots (header/effects/empty in each theme); screenshots inspected. |
| Final source checks | `git diff --check`; 17 history resource keys in each of 11 catalogs; protected-file hashes unchanged. |

Repository tests were introduced before the read implementation; the initial red test compilation is preserved. The first device run was **6/7**, with the new archive test incorrectly searching for merged “ARCHIVED” text. Its selector was corrected to the existing “Show archived projects” accessibility action; archived content, return destination and unchanged data assertions were retained. A stopped/resumed deletion test was then added; the final run was **8/8**. The original failure is retained in `app/build/reports/history-feature/rowtool-history-device-first.log`, and the final instrumentation result is `device-final.log` in the same directory. Build logs and six screenshots are also there. Earlier formatting/name findings were corrected and the reports rerun.

Limits: the complete Android suite, hosted CI and external scans were not run. The previously reported intermittent **DIRECT START** navigation failure remains open; its separate test was not rerun or weakened in this task. Translation resources compile, but no native-speaker review or full eleven-language rendered matrix was performed. The task's temporary emulator was stopped after verification.

Feature update **2026-09-23**: one local project note is available from the Counter menu, with a two-line preview below additional counters. Archived projects expose existing notes for reading in their Projects menu. Room schema 5 and backup v5 add notes; the project-note verification record below is the authority for this feature's checks.

Feature update **2026-09-23**: project-specific row/round reminders are available from the Counter menu. Room schema 4 adds `reminders` without changing existing project rows, and backup v4 includes their settings and acknowledgements. The reminder verification record below distinguishes host, emulator, and unrun checks.

Feature update **2026-09-23**: each current repeat now has an optional `repeatStartCount`, the main-counter reading where its first row or round appears. The repeat editor is available from the Counter menu and repeat display; project editing uses the same fields. This update adds Room migration 2 to 3 and backup v3. Verification for this change is recorded below; earlier feature checks remain historical.

Feature update **2026-09-22**: named additional counters, atomic multi-counter undo, Room migration 1 to 2, and backup format v2 are described below. The older broad inspection and verification records remain historical; they are not evidence for this feature.

This document describes the implementation currently present in the repository. It is not a feature roadmap.

Source inspection date: **2026-09-14** (Europe/Helsinki). Inspected branch: **`main`**. Pre-commit baseline HEAD: **`866f53bda73e0940d41520fc1b24cf1d6258ede8`**. The working tree already contained relevant uncommitted production, resource, build, lock/verification, test, stability-snapshot, and `PROJECT.md` changes, plus untracked production, font, debug-manifest, host-test, and Android-test files. **These live working-tree files are included in this reference; The baseline commit alone does not reproduce this inspected state.** The inspected application source contains **48 production Kotlin files**. This reference covers those files, Android resources, persistence, all host/device test sources, build configuration, lock and verification metadata, local check wrappers, CI, and release-support documents. Version values below are repository declarations, not claims about the newest externally available release or a freshly resolved dependency graph. Final local verification and its execution limits are recorded below; source descriptions do not imply device or hosted-CI verification.

Use the implementation paths and test map below when drafting review questions. A documented review boundary is not a confirmed defect. Historical material in `RowTool-koodintarkistus/` contains five prompt files and five result files spanning prompts 001–301; it is supporting history, not authority over current source. This document does not certify that every historical finding remains fixed.

Freshness was established from live files, including untracked files, rather than inferred from commit dates. That documentation inspection changed only `PROJECT.md` and preserved the pre-existing work. Generated reports, private local configuration, and external shared-engine internals are not implementation authorities. The root `.gitignore` ignores the repository-local `/.kotlin/` tree.

Production Kotlin paths abbreviated as `data/`, `domain/`, `ui/`, `MainActivity.kt`, or `RowToolApplication.kt` are relative to `app/src/main/java/com/finnvek/rowtool/`. Test paths are relative to their stated source set. Build/report paths describe configured outputs unless explicitly called inspected source files.

## Contents

- [Identity and platform](#identity-and-platform)
- [Architecture](#architecture)
- [Technology versions](#technology-versions)
- [Database schema](#database-schema)
- [Domain rules and transaction contracts](#domain-rules-and-transaction-contracts)
- [Screens and behavior](#screens-and-behavior)
- [State ownership, startup, navigation, and failures](#state-ownership-startup-navigation-and-failures)
- [Persistent settings](#persistent-settings)
- [Backup format and replacement semantics](#backup-format-and-replacement-semantics)
- [UI implementation, assets, accessibility, and localization](#ui-implementation-assets-accessibility-and-localization)
- [Detailed UI reference](#detailed-ui-reference)
- [Manifest and privacy boundary](#manifest-and-privacy-boundary)
- [Build and verification commands](#build-and-verification-commands)
- [Test inventory and evidence limits](#test-inventory-and-evidence-limits)
- [Code-review invariants and quality tooling](#code-review-invariants-and-quality-tooling)
- [Build tooling and local check details](#build-tooling-and-local-check-details)
- [Source-of-truth locations](#source-of-truth-locations)
- [Change-impact and review map](#change-impact-and-review-map)
- [External release steps](#external-release-steps)
- [Documentation conflicts and inspection limits](#documentation-conflicts-and-inspection-limits)

## Identity and platform

| Item | Value |
|---|---|
| Display name | RowTool |
| Publisher identity | Finnvek |
| Gradle module | `:app` |
| Namespace / application ID | `com.finnvek.rowtool` |
| Version | `versionCode 1`, `versionName 1.0.0` |
| Android SDK | `minSdk 29`, `compileSdk 37`, `targetSdk 37` |
| Java source/target and Kotlin JVM target | 17 |
| Business model | Store documentation describes a paid Play download; implementation has no billing, subscription, trial, ads, or feature gates. Actual store configuration/publication is external. |
| Supported platform | Android 10/API 29 and later; no non-Android application target |

The product counts rows and rounds for knitting and crochet. There is no account, backend, remote API, synchronization service, onboarding flow, pattern editor, timer, statistics screen, or notification flow in the application. Settings includes an in-app language selector. A target is informational and does not stop counting. Project names need not be unique. The intended store title is `RowTool: Row Counter`; the numeric price is a Play Console decision, not an application constant.

## Architecture

RowTool uses MainActivity for its Compose application and a separate WidgetConfigurationActivity for the launcher configuration contract. Projects, Counter, and Settings remain the three main places; history is a project subview. Screen-specific ViewModels expose immutable Flow-backed state and call a small repository layer. `AppContainer` in `RowToolApplication.kt` constructs Room and repositories and supplies the Context-owned DataStore. MainActivity and navigation destinations use the ViewModels' explicit companion factories; no dependency-injection framework is involved.

Room is the persistent source of truth for projects, additional counters, undo history, reminders and project notes. Preferences DataStore is the persistent source of truth for app settings and the last-active project. Counter mutations are serialized by a repository `Mutex` and performed in Room transactions; accepted count changes are persisted immediately and retain the newest 100 undo records per project.

Startup remains behind the splash screen until `RowToolAppViewModel` resolves the last-active project. An `IOException` during that resolution falls back to the Projects screen, but Room/database and other non-recoverable failures are not converted into a successful resolved state.

The UI follows a Route/Content split. `ProjectsRoute`, `CounterRoute`, and `SettingsRoute` collect lifecycle-aware `StateFlow` values, consume transient effects, own dialogs and Android platform launchers/side effects, and translate ViewModel operations into action bundles. `ProjectsScreenContent`, `CounterScreenContent`, and `SettingsScreenContent` render state and invoke those actions without resolving repositories or navigation themselves. Projects and Settings use buffered channels for one-shot navigation/messages. Counter messages remain buffered, mandatory return navigation is derived from `CounterUiState`, and haptics use a separate non-replaying `SharedFlow` collected only while the destination is resumed. `RowToolApp` owns a lifecycle-aware FIFO queue for root snackbar presentation.

`ProjectsRoute` stores edit/delete dialog selection as saveable project-ID strings and resolves the current `CounterProject` from the latest Room-backed UI state. Domain model instances are not placed directly in saved-instance state.

## Technology versions

| Component | Declared version |
|---|---|
| Gradle wrapper | 9.7.1 |
| Android Gradle Plugin | 9.4.0 |
| Kotlin / Compose plugin | 2.4.20 |
| KSP | 2.3.11 |
| Compose BOM | 2026.09.00 |
| Activity Compose | 1.13.0 |
| AndroidX Core | 1.19.0 |
| Lifecycle | 2.11.0 |
| Navigation Compose | 2.10.1 |
| Room | 2.8.5 |
| Preferences DataStore | 1.2.1 |
| SplashScreen | 1.2.0 |
| Kotlin serialization | 1.11.0 |
| Coroutines | 1.11.0 |

The version catalog at `gradle/libs.versions.toml` is authoritative for dependency and plugin versions. `app/build.gradle.kts` is authoritative for Android SDK levels, application identity, Java compatibility, lint, Room schema export, and release shrinking.

## Database schema

Database: `rowtool.db`, Room schema version `5` (Room library `2.8.5`).

### `projects`

| Column | Storage | Meaning |
|---|---|---|
| `id` | non-null text primary key | Locally generated UUID; validated imports may supply another nonblank string |
| `name` | non-null text | Trimmed project name, maximum 60 Unicode code points |
| `counterUnit` | non-null text | `ROWS` or `ROUNDS`; unknown persisted values map safely to `ROWS` |
| `count` | non-null integer | Current count, `0..999999` |
| `startValue` | non-null integer | Reset value, `0` or `1` |
| `targetCount` | nullable integer | Optional target, `1..999999` |
| `repeatLength` | nullable integer | Optional repeat length, `2..999` |
| `repeatStartCount` | nullable integer | Main-counter reading for the first repeat row/round, `1..999999` when repeat is enabled; otherwise null |
| `isArchived` | non-null integer/boolean | Active/archive state |
| `createdAt` | non-null integer | Epoch milliseconds |
| `updatedAt` | non-null integer | Epoch milliseconds |

The table has a nonunique index `index_projects_isArchived_updatedAt` on `isArchived, updatedAt`. There are no SQL column defaults. Nullable target/repeat fields are supplied by callers; domain validation requires `repeatLength` and `repeatStartCount` to be both present or both null.

### `counter_history`

| Column | Storage | Meaning |
|---|---|---|
| `id` | auto-generated integer primary key | History ordering identity |
| `projectId` | non-null text foreign key | Owning project |
| `previousCount` | non-null integer | Count before the mutation |
| `newCount` | non-null integer | Count after the mutation |
| `changeReason` | non-null text | `INCREMENT`, `DECREMENT`, `MANUAL_SET`, or `RESET` |
| `createdAt` | non-null integer | Epoch milliseconds |

`projectId` references `projects.id` with `ON DELETE CASCADE` and `ON UPDATE NO ACTION`. The nonunique `index_counter_history_projectId_id` index on `projectId, id` supports newest-history lookup. History ID is Kotlin `Long` with constructor default `0` to request auto-generation; other history fields have no constructor defaults. Both history counts and timestamp are `Long`, and project ID/reason are `String`; all history columns are non-null. There are no SQL column defaults. Undo consumes the newest history row and does not add another row.

Exported schemas `1.json` through `5.json` remain under `app/schemas/com.finnvek.rowtool.data.local.RowToolDatabase/`. `MIGRATION_1_2` creates the two additional-counter tables and indexes. `MIGRATION_2_3` adds nullable `repeatStartCount` and sets it to 1 only where `repeatLength` is present. `MIGRATION_3_4` creates `reminders` and its project index. `MIGRATION_4_5` creates only `project_notes`; it does not rewrite any old row or timestamp and creates no notes for existing projects.

Numeric domain ranges are enforced in Kotlin, not by SQL `CHECK` constraints. Counts, repeat start, and timestamps use Kotlin `Long`; `startValue` and `repeatLength` use `Int`. The database builder registers all four migrations; there is no destructive fallback, encryption library, or custom journal-mode override. There is no project-name uniqueness constraint. Project inserts use `OnConflictStrategy.ABORT`, including bulk replacement inserts; IDs alone enforce project uniqueness. `@Update` returns an affected-row count, while the repository first loads the row inside its transaction. The original v1 schema identity is `929fe220bd5bf1864da2e8bda3cdeb39`; v2 and v3 have four application tables, v4 has five, and v5 has six.

### Additional counters and history effects

`additional_counters` stores a UUID `id`, owning `projectId`, normalized `name`, independent `count`, `followsMain`, and `isDeleted`. Project deletion cascades counters. Active counters appear in insertion order. Names reuse the project's 60-code-point validation; counts reuse `0..999999`. New counters start at zero with manual mode by default. Metadata updates load the current row and update only name/mode, preserving the latest count.

`counter_history_effects` has composite primary key `(historyId, counterId)` and exact `previousCount`/`newCount`. History deletion cascades effects; counter foreign keys prevent losing referenced rows. A deleted counter is hidden and stops following the main counter. It remains stored while retained history references it; pruning or undo removes unreferenced hidden rows. Undo can restore its stored count but never its visibility.

### Project reminders

`reminders` stores a persistent ID, indexed owning `projectId`, trimmed `message` (1..200 Unicode code points), `firstCount` (1..999999), optional `intervalCount` (1..999999), `enabled`, optional `acknowledgedThrough`, and positive `revision`. Its project foreign key cascades deletion. Acknowledgement is either null or an occurrence on the reminder's schedule; it may exceed the current project count after Undo or reset. Reminder writes update only this table, not the project timestamp, count, repeat settings, additional counters, or count history.

### Project note

`project_notes` has one row at most per project: `projectId` is both the primary key and a project foreign key with `ON DELETE CASCADE`. Other columns are a random UUID `version`, raw multiline `text`, epoch-millisecond `savedAt`, and nullable `savedCount`. `ProjectNoteRules` is the shared editor/repository/import authority: normalize only CRLF/CR to LF, count at most 5,000 Unicode code points, and reject blank persisted content. No old project gains a note automatically.

`CounterRepository.notes` shares the counter mutation mutex and uses Room transactions. Every save/delete checks the current active project and the expected note version; ownership is the original project ID. Actual changes get a fresh version and timestamp. A save with unchanged normalized text and unchanged count-attachment state is a no-op, retaining both timestamp and old reading. Attaching reads the main count inside the same transaction, including zero; detaching clears it. Later counting, reset, Undo, additional counters and repeat edits cannot change this snapshot. Note writes never update project rows, other tables, or count history. Empty new saves create no row; empty existing saves require the same explicit delete confirmation as Delete. Stale versions, including delete/recreate or backup replacement, cannot silently overwrite current content.

Counter's Note menu opens `NoteEditorHost`, a full-screen project subview. Existing notes have a clickable two-line preview below additional counters; the complete text remains selectable and editable in the editor. The saved reading is labelled as the count **when saved**, and the saved time and number are formatted for the device locale. Archived project menus offer an existing note for reading; restoring the project is required for editing, and this route does not enter Counter.

The editor loads its baseline before enabling edits and distinguishes read failure from an absent note. Its project/session-scoped ViewModel retains draft text, attachment selection and original baseline using `SavedStateHandle`; live Room emissions do not replace the draft. It serializes save requests, keeps failed/conflicting drafts, confirms unsaved exit and reload, and clears the recoverable draft on success. Completion closes only the owning editor session, including across Activity recreation. Keyboard dismissal alone does not exit. External archive/delete emissions do not navigate away from an open draft: Counter waits for the editor to close, external archive makes its content read-only, and even a restored archived draft requires confirmed discard. The full-screen surface uses existing Outfit/theme/action components, scrollable content, fixed visible Save with IME padding, minimum 48 dp actions and all eleven resource catalogs. There is no new main destination, note history, network feature or Undo integration.

## Domain rules and transaction contracts

Sources: `domain/model/CounterModels.kt`, `domain/model/ProjectValidation.kt`, `domain/counter/CounterCalculators.kt`, `data/repository/CounterRepository.kt`, and `data/local/*Dao.kt` under the production Kotlin root.

| Operation | Exact behavior |
|---|---|
| Create | Counts all active and archived records; rejects creation at 1,000 projects with `ProjectLimitReachedException`. Validates values, generates a UUID, initializes count from start value, and assigns the same clock value to both timestamps. |
| Edit metadata | Revalidates using the existing count; changes name, unit, start value, target, and repeat. Preserves current count, creation timestamp, archive state, and history. A byte-for-byte equivalent candidate returns without advancing `updatedAt` or calling the clock. Missing project returns `null`. Archived metadata can also be edited. |
| Edit repeat only | `repeatSettings.save(projectId, length, startCount)` shares the counter mutex and a Room transaction. It reads the current row and changes only repeat length, repeat start, and `updatedAt`; missing/archived projects return `null`, and identical settings are a no-op. |
| Archive/restore | Returns `false` for missing ID. Changes `updatedAt` only when archive state changes; preserves count and history. Repeating the same archive state returns `true` without writing. |
| Delete | Deletes by ID inside a transaction; the foreign key cascades history deletion. Missing ID is effectively a no-op. There is no undo for project deletion. |
| Increment | `min(count + 1, 999999)`. Reaching a target does not disable increment. |
| Decrement | `max(count - 1, 0)`. A start value of 1 does not change this lower bound. |
| Manual set | Accepts `0..999999`; otherwise returns `Invalid` containing `INVALID_COUNT`. |
| Reset | Uses the project's current `startValue`, even if it was edited after creation. |
| Accepted count change | Inserts one history row and its additional-counter effects, updates all changed counts and project `updatedAt`, then trims to the newest 100 operations in one transaction. Additional-only changes store equal main before/after counts and remain undoable. |
| Unchanged count | Returns `NoOp`; does not add history or update the timestamp. Applies to boundary taps, setting the same value, and resetting an already-reset count. |
| Undo | Loads highest history ID, restores exact main and recorded additional-counter before-values, updates timestamp, deletes the history row and effects, and returns `Changed` with the original reason. Unknown stored reason falls back to `MANUAL_SET`. Empty history returns `NoOp`. There is no redo stack. |

Main and additional count operations share `mutate(projectId, mutation, counterId?)`. Main changes apply the actual signed main delta to active following counters and clamp each independently. Reset still uses the main `startValue`; additional reset uses zero. Manual counters do not follow; changing mode preserves the count without replaying past events. Undo uses stored snapshots, never an opposite delta. A historical operation cannot affect a counter created later.

All modifying methods on the shared `CounterRepository` and its `additionalCounters`, `repeatSettings`, `reminders`, and `notes` stores share the same instance-local `mutationMutex` and use Room transactions. The additional-counter store manages observation, names, mode and deletion; all count changes and undo remain in `CounterRepository`. `mutate` and `undo` first reject missing/archived projects with `ProjectMissing`/`ProjectArchived`. Reminder writes also require a current, active owner and an ID belonging to that project. This guard does not prohibit metadata editing, restoring, or deleting archived projects. `BackupRepository` has its own Room transaction and does **not** acquire this mutex; do not describe the mutex as a global lock across both repositories.

`ReminderRules` derives the latest due and next occurrence directly from the visible main count, first count, interval, and acknowledgement boundary. One-time reminders stay due after their target until acknowledged; recurring reminders expose only the latest reached unacknowledged target plus the number of earlier unacknowledged occurrences. Decrement, reset, manual set, and Undo change the displayed status but do not erase acknowledgements. Editing only the message preserves them; editing the schedule clears them. Save/delete/reset requests compare the visible revision. Acknowledge requests compare project, reminder ID, revision, and exact visible target; an older request cannot acknowledge a later occurrence or move the boundary backward. A repeated acknowledgement is a no-op. Acknowledgement and reset do not create count-history rows.

`CounterConstants` is the shared public authority for `MIN_COUNT = 0`, `MAX_COUNT = 999_999`, the 100-entry history cap, and the 1,000-project backup/library cap. `ProjectValidation` is the semantic project-field authority. Its `validate(...)` returns normalized values or independent errors including `INVALID_REPEAT_START`. Repeat length is `2..999`; repeat start is `1..MAX_COUNT` when enabled, and both fields are null when disabled. Name length counts Unicode code points, not UTF-16 code units or grapheme clusters. Target may be below the current count.

`ProjectEditorInputValidation` and the separate repeat editor share `validateRepeatSettingsInput`: repeat length is parsed as `Int`, repeat start as `Long`, and disabled values resolve to null. The editors expose field validity and aggregate `canSave`. Repository validation failures throw `IllegalArgumentException` for invalid create/edit input; import validates through the enforced backup factory.

Project queries order by `isArchived ASC, updatedAt DESC, id ASC`. The newest-active fallback uses `updatedAt DESC, id ASC`. Consequently count changes, undo, actual metadata changes, and archive transitions affect ordering; merely opening a project does not update its timestamp. Equal timestamps have deterministic lexical ID ordering.

### Concurrency, dispatchers, and cancellation

The single application container shares one counter repository, but its mutex covers only that instance's create/edit/repeat-setting/reminder/archive/delete/mutate/undo calls. Reads and Flow observation, all preference writes, and backup export/import do not acquire it. Room transactions prevent partial project/history updates; there is no cross-store transaction joining Room and DataStore and no app-wide import lock. Import may order before or after a counter transaction, and subsequent operations see the resulting database; there is no generation token rejecting an operation because it was requested before replacement.

ViewModels launch count operations in `viewModelScope` on Main; the repeat dialog awaits its save result and closes only on success. Room suspend DAO/transaction APIs handle database execution; `CounterRepository` adds no explicit dispatcher switch. Backup snapshot/encoding and stream decoding/replacement switch to the injected dispatcher (default IO). Settings external-stream I/O also runs on its injected IO dispatcher. The byte-array `prepareImport` overload decodes on the caller's thread.

Counter's atomics are per ViewModel: `routeResolved` distinguishes the first observed project from later emissions, and `unavailableFeedbackHandled` limits unavailable-project feedback to one optional message. Neither atomic owns or acknowledges return navigation; mandatory return is derived by the route from observable state. They are not mutation locks or durable event acknowledgements. Accepted main-count mutation results include repeat and target settings from the same transaction for haptic milestone evaluation.

Import's sequence suppresses stale prepare results/errors; it does not cancel earlier stream reads. `dismissImport` clears only the preview, without incrementing the sequence or cancelling work. The confirm atomic covers replacement attempts within one Settings ViewModel and is released in `finally`; it does not disable every UI action, serialize exports, block new selections, or coordinate other ViewModels. A successful confirm clears the preview unconditionally.

No broad cancellation catch or `NonCancellable` completion section is present. Cancellation propagates through coroutine boundaries; a cancelled coroutine can miss feedback/preference work after a database commit. Stream `use` closes opened resources, but the bounded synchronous read loop has no explicit cancellation poll or special handling for repeated zero-byte reads. Room rollback concerns the replacement transaction, not later selection lookup/write or external export-file contents.

### Repeat and target calculations

- Without repeat length, repeat progress is absent. With main count `c`, repeat start `a`, and length `L`, progress is `p = max(0, c - a + 1)`. The step is 0 when `p = 0`, otherwise `((p - 1) % L) + 1`; completed repeats are `p / L` by integer division.
- With `a = 11` and `L = 8`, counts `10, 11, 15, 18, 19, 26` show steps `0, 1, 5, 8, 1, 8` and completed totals `0, 0, 0, 1, 1, 2`. Setting `a = 1` preserves prior results, including count 0.
- Example with repeat length 6: counts `0, 1, 5, 6, 7, 12` produce steps `0, 1, 5, 6, 1, 6` and completed totals `0, 0, 0, 1, 1, 2`. A boundary displays the final step, not the first step of the next repeat.
- Without a target, target progress is absent. Otherwise fraction is `count / targetCount` using floating-point arithmetic clamped to `0..1`, and reached means `count >= targetCount`.
- The repeat calculator subtracts `repeatStartCount`, never `startValue`; `startValue` remains the reset count. The target calculator still uses the absolute main count. A strong repeat haptic occurs only on an accepted increment ending at a positive repeat boundary. Direct set, reset, and opening/editing settings do not trigger repeat haptics; target feedback retains its prior rule.

## Screens and behavior

### Projects

- Lists active projects separately from an expandable archived section.
- Creates rows or rounds projects with a start value and optional target/repeat.
- Opens, renames/edits, archives, restores, and deletes projects; deletion requires confirmation.
- Automatically opens a newly created project.

### Counter

- Displays the project name, row/round label, and a responsive large count.
- Uses the supplied plus, minus, and undo WebP resources in distinct accessible touch targets.
- Prevents decrement below zero and increment above `999,999`.
- Supports persistent multi-step undo, direct count editing, and confirmed undoable reset.
- Shows optional target progress and repeat position/completed-repeat information.
- Provides edit, set-count, reset, archive, and delete actions; archived projects cannot be counted or undone and are redirected to Projects.
- Performs optional light haptics for accepted taps and stronger feedback at a repeat boundary or target.

### Settings

- Theme: system, light, or dark.
- Language: System default or one of eleven bundled languages, applied through AppCompat/platform locale ownership.
- Haptic feedback toggle.
- Keep-screen-awake toggle, applied while an active counter is open.
- Manual JSON export and validated replacement import through the system document picker.
- App version, Finnvek identity, local-data privacy summary, and business-model summary.

The navigation routes are `projects`, `counter/{projectId}`, `history/{projectId}`, and `settings`. `Screen` owns `PROJECTS`, `SETTINGS`, the private `COUNTER_BASE`, internal `COUNTER_PROJECT_ID_ARG = "projectId"`, public `COUNTER_PATTERN`, and `counter(projectId)`. Route construction applies `Uri.encode(projectId)`. `RowToolNavHost` registers the same pattern/argument constant as a `NavType.StringType` and reads it through that constant; it does not repeat a literal argument name or manually decode the result. Startup validates the stored last-active ID against Room; if it is missing or archived, the most recently updated active project is selected, or the Projects screen opens when none exists. Archiving or deleting the selected project clears the matching preference immediately when possible, while startup remains the repair path for a stale or failed preference write.

## State ownership, startup, navigation, and failures

### Construction and observation

`RowToolApplication.onCreate()` creates one `AppContainer`. Construction order is Room database, counter repository, preferences repository using the project DAO, then backup repository. Production uses the Context `preferencesDataStore` delegate with `ReplaceFileCorruptionHandler { emptyPreferences() }`. Corrupted preference files therefore reset settings through that handler; Room is not given an equivalent destructive recovery policy. There is no Hilt/Koin service locator or external dependency-injection container.

| Owner | State and lifetime |
|---|---|
| `RowToolAppViewModel` | Activity-owned; preferences use `SharingStarted.Eagerly`. `StartupState` begins unresolved and is assigned once startup lookup completes. |
| `ProjectsViewModel` | Destination-owned; maps Room's ordered list into active and archived lists. Initial state has empty lists and `isLoading = true`. |
| `CounterViewModel` | Destination-owned with key `counter:$projectId`; combines project, additional-counter and history-count observations. Initial state is loading; preferences are a separate state flow. |
| `SettingsViewModel` | Destination-owned; observes preferences and holds a nullable in-memory import preview plus import coordination atomics. |
| Screen observation | Projects `uiState`, Counter `uiState`/`preferences`, and Settings `preferences` use `WhileSubscribed(5000)`; preference initial values are `AppPreferences()`. Counter combines project and undo availability, sets loading false on combined emission. Settings `importPreview` is a `MutableStateFlow` initially null, exposed read-only. Routes call `collectAsStateWithLifecycle()`. |
| Buffered one-shot effects | `ProjectsViewModel` sends `OpenProject` and `ShowMessage`; `SettingsViewModel` sends `ShowMessage` and `ImportComplete`; `CounterViewModel`'s buffered channel currently sends `ShowMessage`. Each uses `Channel.BUFFERED` plus `receiveAsFlow()`. Routes collect with `LaunchedEffect(viewModel)` and keep navigation/message callbacks current with `rememberUpdatedState`. These are one-consumer transient events, not replayed or persisted acknowledgements. |
| Counter return navigation | `CounterRoute` derives `mustReturnToProjects` from non-loading state whose project is missing or archived. A keyed `LaunchedEffect` invokes the current Projects callback. Because the cause remains in `CounterUiState`, an uncompleted navigation is attempted again when the route composition is recreated. There is no navigation effect subtype. |
| Counter haptics | `CounterViewModel` emits `CounterEffect.Haptic` through a separate default `MutableSharedFlow`: `replay = 0`, `extraBufferCapacity = 0`, suspending overflow. `CounterRoute` collects it only inside `repeatOnLifecycle(RESUMED)` and checks exact resumed state plus the current haptic preference before calling the current `View`. Feedback emitted with no resumed collector is dropped and never replayed. |
| Root snackbar presentation | `RowToolApp` remembers a `SnackbarHostState` and an insertion-ordered `mutableStateListOf<String>`. Routes enqueue resolved text; a root presenter consumes only while the activity lifecycle is `RESUMED`. The queue is independent of destination composition but is remembered, not saved across activity recreation/process death. |
| Projects saved UI | Archive expansion, create-dialog visibility, edit ID, and delete ID use `rememberSaveable`. Selected IDs resolve against the latest active/archive lists. |
| Counter saved UI | The nullable `CounterDialog` enum records set/edit/reset/archive/delete dialog selection with `rememberSaveable`. The dialog is rendered only when a project exists. |
| Editor saved UI | Name, unit name, start value, toggles, and numeric text use `rememberSaveable(project?.id)`. Count editor saves `TextFieldValue` using its Saver, keyed by current count. |
| Temporary UI | Dropdown expansion uses `remember`; import preview is ViewModel state, not a `SavedStateHandle` or backup field. Activity recreation and OS process death are different lifetimes. |

Projects/Settings buffered effects and Counter messages are collected by composition-scoped `LaunchedEffect`s, not lifecycle-gated collectors; lifecycle-aware StateFlow collection does not itself gate those channels. Counter haptics are the deliberate exception: their separate lossy flow is destination-lifecycle-gated at `RESUMED`. Root snackbar display is independently activity-lifecycle-gated at `RESUMED`, so receiving a message and visibly presenting it are separate steps. `rememberUpdatedState` refreshes navigation/message/haptic preference callbacks; the haptic collector is also keyed to the current View and lifecycle. `NoteEditorViewModel` uses `SavedStateHandle` for its project-bound baseline, draft, attachment selection, and completion marker. `CopySetupViewModel` also uses it for the captured setup and operation/completion identity. Room/history/settings survive process recreation as disk data; ViewModels, atomics, snackbar queue, import preview, pending work and effects do not. Saveable editor fields and route IDs can participate in Android saved-instance-state restoration; this is not a guarantee for force-stop, abrupt kill, or an unsaved OS state.

### Startup and back-stack contract

1. `MainActivity` installs SplashScreen before `super.onCreate`, then enables obscured-touch filtering and edge-to-edge layout. The splash remains while startup is unresolved; the app NavHost is composed only after resolution.
2. Preferences return a stored ID only if Room says the project exists and is active. Otherwise the newest active ID (or `null`) is returned. Repair uses compare-and-set-style DataStore editing: a newer concurrently stored selection is not overwritten by a stale repair/clear.
3. `RowToolNavHost` owns `rememberNavController()`. Start destination is the concrete encoded counter route when an ID is available, otherwise Projects. The counter destination uses `Screen.COUNTER_PATTERN` and declares/reads `Screen.COUNTER_PROJECT_ID_ARG` as `NavType.StringType`; application code does not repeat the argument literal or manually decode it again. There are no declared external deep links; an internal direct counter start is not an Android deep-link entry point.
4. Projects opens a counter or Settings with `launchSingleTop`. Counter handles both toolbar and system Back through `navigateToProjects()`: pop to an existing Projects entry, or navigate there after inclusive `popUpTo(graph.id)` if no Projects entry exists.
5. Settings toolbar Back calls `popBackStack()`; system Back uses the navigation/activity default, with no Settings-specific BackHandler. Projects has no custom BackHandler and leaving its root follows default activity Back behavior. Successful import navigates to the newest active imported counter or Projects, using inclusive `popUpTo(navController.graph.id)` and `launchSingleTop` to remove the complete previous application flow, regardless of which destination was the graph's dynamic start destination. `DirectCounterNavigationTest.emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack` is the regression source for this graph-anchor contract.
6. Counter's first observed project is classified once using `routeResolved.compareAndSet`. A valid first project writes the last-active preference; an initially missing project requests the missing message, and an initially archived project requests the archived message. Later missing/archived emissions use the same feedback path. `unavailableFeedbackHandled` permits at most one non-null unavailable message, but explicit archive/delete calls may mark feedback handled without a message so their observer emission cannot add a duplicate. Mandatory navigation is not an effect: after loading, `CounterRoute` derives it from missing/archived state and calls Projects from a keyed `LaunchedEffect`. The persistent cause makes navigation recoverable after route composition or activity recreation. `CounterRouteNavigationTest` covers deletion/archive retry after composition recreation and the valid-project non-return case; `DirectCounterNavigationTest` covers deletion while stopped followed by activity recreation.

### Failure and feedback boundaries

- Preference reads recover from `IOException` with empty defaults; other exceptions propagate. Unknown stored theme names map to `SYSTEM`. Selection writes tolerate `IOException` in the specific startup/create/open/archive/delete repair paths.
- Startup catches only `IOException`. Other failures do not set `isResolved = true`; there is no implemented fatal-startup recovery screen.
- Project/counter write operations catch Android `SQLException` and emit `error_database_write`. Project creation maps the project limit to `error_project_limit`. Validation/programming exceptions are not broadly converted to generic success or swallowed.
- `CounterMutationResult.Invalid` maps to the set-count error. Missing/archived results navigate with the relevant message. A no-op increment can emit the maximum message, although the normal plus control is disabled at maximum. Other no-op operations are silent.
- Changed increment, decrement, and undo may emit haptic feedback on the separate lossy flow. Manual set and reset do not. Only increment uses strong feedback: a positive repeat multiple or crossing from below to exactly the target. Decrement/undo are light. Delivery additionally requires the Counter destination to be `RESUMED` and the current haptic setting to be enabled; feedback completed while no resumed collector exists is discarded. `CounterHapticLifecycleTest` covers navigation-away loss, non-replay on return, fresh resumed delivery, and preference disabling.
- Haptic constants are `CLOCK_TICK` for light, `CONFIRM` for strong on API 30+, and `LONG_PRESS` for strong on API 29. No vibration permission is requested because this uses `View.performHapticFeedback`.
- Missing/archive messages remain one-shot buffered feedback, while the required return to Projects is state-derived and independent of message delivery. Snackbars belong to the app root and have no undo action; persistent counter undo is a separate operation.
- Project and counter dialogs close before dispatching save/confirm operations. They do not remain as retry forms after a database error. The import confirmation has different failure retention, described below.

## Persistent settings

Preferences DataStore name: `rowtool_preferences`.

| Key | Default | Purpose |
|---|---|---|
| `theme_mode` | `SYSTEM` | String enum name: `SYSTEM`, `LIGHT`, `DARK`; unknown string falls back to `SYSTEM` |
| `haptic_feedback_enabled` | `true` | Boolean; counter haptic feedback |
| `keep_screen_awake` | `true` | Boolean; keep the display awake on the Counter screen |
| `last_active_project_id` | absent | String project ID; nullable API removes the key for `null`; validated against Room at startup |

`PreferencesRepository` owns one `defaults = AppPreferences()` value and derives every absent theme/haptic/keep-awake value from it rather than repeating literals. Unknown stored theme strings also fall back to `defaults.themeMode` (`SYSTEM`) without rewriting the stored value. All setters use DataStore `edit`; ordinary setters propagate failures. Conditional selection clear/repair catches only `IOException`. Reads emit empty preferences on `IOException`, which then map through the same defaults; corruption is separately handled by the application's replacement handler. There is no extra per-key repair layer. Theme/haptics/keep-awake are unchanged by import; last-active selection is updated after database replacement. These settings are not part of the JSON project backup.

## Backup format and replacement semantics

`BackupFormat` is the shared internal format-identity authority: `CURRENT_SCHEMA_VERSION = 5` and `APPLICATION_ID = "RowTool"`. Export writes v5; import accepts v1, v2, v3, v4, and v5 and checks application identity. v1/v2 repeat lengths gain start count 1; absent repeats keep null. v1 restores empty additional-counter and history collections, while v2 retains them. v1/v2/v3 restore no reminders. v1-v4 restore no notes and remove any existing notes during replacement, even when project IDs match.

The v5 UTF-8 JSON root contains `schemaVersion`, `application`, `exportedAt`, `projects`, `counters`, `history`, required `reminders`, and required `notes`. Each note carries `projectId`, raw `text`, `savedAt`, and nullable `savedCount`. Import generates a fresh version token, invalidating editors opened before replacement. Projects retain `repeatStartCount` alongside `repeatLength`; v3-v5 require the field even when null and reject invalid field pairs before replacement. Reminder entries retain IDs, owner, message, schedule, enabled state, acknowledgement boundary, and revision. Counter entries include hidden counters required by retained history. History entries retain their IDs, project, main before/after counts, reason and timestamp, with nested `effects` containing counter ID and exact before/after counts. DataStore settings remain excluded.

Import accepts unknown JSON keys for forward-compatible optional additions, but rejects malformed JSON, files over 5 MiB, unsupported schema versions, another application identity, more than 1,000 projects, duplicate/blank IDs, unknown counter units, and invalid project/reminder/note values. The UI presents active/archive counts and requires confirmation before replacement. Confirmed replacement replaces projects, counters, history, effects, reminders, and notes together in one Room transaction, then resolves the last-active imported project. Validation checks unique nonblank counter and reminder IDs, project ownership, reminder messages/schedules/acknowledgements/revisions, history IDs, per-project history caps, effect references, count bounds, reasons and reverse-chain continuity from current counts. Hidden counters must be referenced by history. Invalid preparation never writes to the live database; a failed replacement rolls back every table.

The Storage Access Framework supplies file access, so export/import requires no broad storage permission.

### Exact JSON and I/O contract

Example of a valid payload (illustrative data, not bundled project content):

```json
{
  "schemaVersion": 5,
  "application": "RowTool",
  "exportedAt": 1788825600000,
  "projects": [
    {
      "id": "example-project",
      "name": "Garden scarf",
      "counterUnit": "ROWS",
      "count": 42,
      "startValue": 0,
      "targetCount": 120,
      "repeatLength": 6,
      "repeatStartCount": 11,
      "isArchived": false,
      "createdAt": 1788820000000,
      "updatedAt": 1788825000000
    }
  ],
  "counters": [],
  "history": [],
  "reminders": [],
  "notes": []
}
```

`BackupFile.counters`, `.history`, `.reminders`, and `.notes` have null defaults for decoding legacy files. v2/v3-v5 require counters and history; v4 also requires reminders. v3-v5 require each project to carry `repeatStartCount`, validate it against `repeatLength`, and reject nonnull start counts under v1/v2 labels. All original root/project fields must be present, including nullable project values; `explicitNulls = true`. Production `encode` emits compact JSON with defaults, including a null repeat start when repeats are disabled. `ignoreUnknownKeys = true` allows other extra keys, not absent required fields or arbitrary schema changes.

Imported IDs must be nonblank and distinct but are not required to be UUIDs, trimmed, or checked against a separate length limit. Imported names are normalized by `ProjectValidation`; units must match an enum name exactly. Existing project/history timestamps retain their previous `Long` validation semantics; note `savedAt` must be nonnegative. No note count-to-current-count or timestamp chronology constraint is imposed. A nonpositive export timestamp is shown as an unknown date in preview. An empty project list is valid and confirmed import clears the library.

`BackupCodec.MAX_BACKUP_MIB` is the internal displayed-size authority and currently equals `5`; private `MAX_BACKUP_BYTES` derives `MAX_BACKUP_MIB * 1024 * 1024`, exactly **5,242,880 bytes**. The byte-array decoder rejects anything larger before parsing. The stream decoder allocates that byte threshold plus one and reads only until EOF or the extra byte proves oversize. UTF-8 decoding is strict (`throwOnInvalidSequence = true`); malformed UTF-8 maps to `MALFORMED_JSON`. Validation order after parsing is schema version, application identity, project-count/duplicate-ID precedence, then per-project ID and domain values. Individual failures collapse to `INVALID_PROJECT`; structural/type/missing-field parsing failures map to `MALFORMED_JSON`. No existing rows are changed during preparation.

| Flow | Implementation detail |
|---|---|
| Export picker | `CreateDocument("application/json")`; date-based filename from `backup_file_name` and ISO local date. A cancelled picker does nothing. |
| Export snapshot | `BackupRepository.exportJson()` reads all six tables in one Room transaction and encodes afterward. It checks the complete output with the import decoder before returning it for writing. Oversized or invalid output fails explicitly; no counters, history, or reminders are silently omitted. |
| Export write | `SettingsViewModel` opens the selected URI with mode `"w"`, writes UTF-8 with a buffered writer, and closes it with `use`. File-not-found, IO, security, or SQL exceptions emit export failure. No provider-independent rollback or cleanup of a partially written external file is implemented. |
| Import picker | `OpenDocument()` with `application/json` and `text/json`. Opens the selected stream on IO and closes it with `use`; no persisted URI grant is requested. |
| Competing selections | Each prepare request increments `importRequestSequence` and clears old preview. Completion/error from an older request is ignored. This orders selections; it is not a durable import queue. |
| Preview | Contains the validated backup and active/archive counts. The route formats export date with localized medium-date/short-time formatting and shows total/active/archive plurals plus replacement warning. |
| Confirm | `importInProgress.compareAndSet(false, true)` suppresses duplicate concurrent confirms; `finally` resets it. `replaceWith` consumes the already validated object; it does not decode or revalidate the object again. |
| Database failure | Android `SQLException` inside replacement returns `DATABASE_WRITE_FAILED`; Room rolls back deletes/inserts. ViewModel retains preview and shows the write error, permitting another confirmation. |
| Database success | Clears preview, sends success message, then `ImportComplete`. Last-active selection uses newest active with the DAO tie-break. Failure of the following preference write with `IOException` does not undo committed projects. |

`ValidatedBackup` is a constructor-enforced validation wrapper. It is a public `data class` with a private constructor and `@ConsistentCopyVisibility`, so generated `copy` retains the constructor's private visibility. Its only construction path is `ValidatedBackup.create(exportedAt, projects, counters, history, reminders)`, which returns `BackupDecodeResult`: it checks the 1,000-project cap and duplicate IDs, rejects blank IDs, reuses `ProjectValidation.validate` for every project, normalizes accepted names into copied immutable `CounterProject` values, and builds a new mapped list so later mutation/clearing of the caller's collection cannot change `backup.projects`. It also validates and copies reminder values and their ownership. IDs remain otherwise untrimmed and need not be UUIDs; timestamps remain unrestricted `Long` values. `BackupCodec` converts DTOs and delegates to this same factory. `BackupRepository.replaceWith` can trust the enforced `ValidatedBackup` type and maps it directly to entities without a second validation pass. Export applies the same 5 MiB import limit to the complete encoded backup and reports failure if it cannot round-trip. Counter, nested history/effect, and reminder collections are copied during validation. No app-level encryption, checksum, signature, compression, merge import, or automatic backup schedule is implemented. A user-selected document provider controls the destination, potentially including its own cloud storage; the app itself has no network upload path.

### Additional-counter workspace

The existing Counter workspace has an additional-counter section below the unchanged main WebP controls. The empty state has a direct **Add counter** action. Each counter has its name, value and manual/following label, with a local edit/reset/delete menu. Manual counters have smaller WebP plus/minus buttons; tapping either mode's count opens its count editor. Reset and deletion require confirmation. The three destinations and current project menu remain unchanged.

Names wrap, controls have at least 48 dp touch targets, and the value/button row can wrap on narrow screens. The section uses existing Outfit, spacing and theme colors. New text and the updated backup replacement warning are present in all eleven locales. New counter/name editors retain drafts on SQL failure, close only on success, and disable repeated saves while pending. Route selections keep their owning project ID; name/mode edits never submit an editor's stale count. The count editor also retains its draft across live count emissions.

Counter's existing menu now opens a project reminder list with Add reminder. Its editor explains that the target is the visible main count and uses the project's row/round wording. The list shows full messages, next/due targets, individual acknowledgement, edit, enable/disable, and confirmed reset/delete. A compact section in Counter remains allocated for enabled reminders, so reaching a target does not move the main buttons; when no reminders are enabled it occupies no large empty card. It shows the due count and a route to all messages without opening a modal on count changes. The editor keeps a saveable draft across Activity recreation and a failed SQL save, reports the failure, and closes only on success. All new text and plurals are present in the existing eleven resource catalogs. These are in-app reminders only: no notification permission, background scheduler, sound, or new network path was added.

## UI implementation, assets, accessibility, and localization

Routes own state collection, effects, dialogs, navigation callbacks and platform integration. Screen content receives explicit state/action objects. The project editor is shared by Projects and Counter; dropdown rendering is shared by both menus. Three small shared UI authorities avoid duplicating specific presentation contracts without constituting a generic design-system framework:

- `RowToolDialogActionColors.kt` owns ordinary secondary text-button colors and destructive error-container/on-error-container text-button colors. Project and count editor actions use the ordinary helper; the shared confirmation dialog uses ordinary or destructive confirmation plus ordinary dismissal; Settings' richer replacement-import dialog calls both helpers directly.
- `RowToolSectionHeadingText.kt` owns locale-aware uppercase, `labelSmall`, secondary-colored section-label text. Projects uses it for active and archived headings; Settings uses it for each settings section. It does not add heading semantics and is not used for the Counter toolbar title.
- `RowToolConfirmationDialog.kt` owns the common title/message/confirm/cancel `AlertDialog` shape and optional destructive confirm styling. Projects uses it for deletion; Counter uses it for reset, archive, and deletion. Settings retains its custom import-summary dialog.

Source constraints below are not measured-device acceptance evidence.

## Detailed UI reference

### Screen composition and responsive behavior

| Surface | Current layout and interaction |
|---|---|
| Projects toolbar | App name and Settings icon; background-colored TopAppBar. No bottom navigation or project-detail destination. |
| Projects loading | Centered progress indicator, no empty-library content while loading. |
| Empty library | Scrollable centered title, explanatory body, New project button and privacy text. No floating creation control when both lists are empty. |
| Nonempty library | Centered, maximum 600 dp LazyColumn; 20 dp horizontal, 12 dp top, 104 dp bottom content padding. Floating untinted plus image is 64 dp artwork in a 72 dp touch box, with New project description. No extended text FAB. |
| Active items | Uppercase localized header, labelSmall/secondary with 20 dp top and 8 dp bottom padding; individually keyed lazy items (`project.id`). Active row opens the project; separate overflow offers edit/archive/delete. |
| Archived items | At least 48 dp expansion row, localized total and show/hide description, expand icon rotated 180 degrees when open. AnimatedVisibility contains a Column in one lazy item, using `key(project.id)`; expanded rows are not individually virtualized. Restore/edit/delete remain available; row body does not open the counter. |
| Project rows | Cardless rows with 14 dp vertical padding and top-aligned content. Name uses titleLarge, at most two lines with ellipsis; plural count uses titleMedium/secondary; optional target/repeat use bodySmall/onSurfaceVariant. Archived status is explicit text. Between rows: 1 dp divider at onSurface alpha 0.15. The legacy `ProjectCardActions` name remains, but no Card surface is rendered. |
| Project operations | Create opens the new counter after persistence. Edit/delete selection is resolved by saved ID. Projects archive/restore is immediate and stays on Projects; Counter archive requires confirmation and returns to Projects. Delete requires confirmation in both routes. |
| Counter toolbar | Project name uppercased with the first configuration locale, bold titleLarge, heading semantics, one line/ellipsis and 8 dp end padding. Back, Settings, then overflow edit/repeat/set/reset/archive/delete. Repeat settings are reachable even when tracking is off. Overflow is enabled/expanded only for an active project. |
| Counter loading/missing | Null project shows a centered spinner only while `isLoading`; after loading, null renders no workspace while state-derived return navigation runs. Archived content, if briefly present, disables count controls/edit/menu before return. |
| Counter workspace | Maximum 600 dp outer width; scroll modifier precedes minimum viewport height, then 20 dp side and 24 dp top/bottom padding. Centered column fills the available height where content fits and scrolls when taller. |
| Counter hierarchy | Unit label at 25 sp; editable number in a minimum 128 dp region; optional target status at 19 sp plus 4 dp progress bar; optional tappable repeat text (titleMedium) and completed-repeat count (bodySmall when positive); controls after 32 dp top space. Starts above 1 add a short row/round-specific start label; default start 1 keeps the former display. No repeat badge. |
| Target/repeat layout | Target status has 8 dp top gap; bar follows 8 dp gap, capped at 360 dp, primary progress over surfaceContainerHighest. Repeat has 16 dp top gap, a minimum 48 dp tap target, and completed-repeat text 4 dp below. Target reached is textual; counting remains possible above target. |
| Count sizing | Bases: 115 sp for 0–3 digits, 102 for 4, 90 for 5, 76 otherwise; multiply by `clamp(width / 340, 0.78, 1)` and, above font scale 1.3, `1.3 / fontScale`. Grouping separators do not count (`Char.isDigit`). Uses displayMedium with Bold, tabular-number feature `tnum`, one line, centered onSurface text; clickable text padding is 8/4 dp. |
| Counter controls | Maximum 360 dp column. Minus and plus share a SpaceBetween row with equal 144 dp reference touch sizes, artwork 123/125 dp. Undo is centered below, reference size 92 dp, 16 dp gap. Minus optical Y offset is 1 dp. Scale is `clamp(availableWidth / 288, 48 / 144, 1)`; very narrow widths below 96 dp cannot accommodate two 48 dp targets without exceeding width. |
| Image interaction | Click box is at least 48 dp; artwork capped at requested touch size; ContentScale.Fit and no tint. 90 ms tween changes scale to 0.982 and translates down by 1.5 dp when pressed. No ripple; disabled alpha 0.46 with disabled click semantics. |
| Settings | Maximum 600 dp LazyColumn with 32 dp bottom padding. Uppercase labelSmall/secondary heading rows use 16 dp horizontal and 8 dp vertical padding. Radio/toggle/action rows use 16/12 dp padding and at least 48 dp height; section dividers use outlineVariant. No section Cards. Toolbar title uses headlineMedium. |
| Settings contents | Appearance: system/light/dark radios. Counter: haptic/keep-awake switches with summaries. Data: export/import rows with summaries. About: app name, installed package version, Finnvek, privacy and business-model summaries, in a 16/12 dp padded column with 8 dp gaps. No busy-state UI disables these actions during I/O. |
| Shared menus | Material dropdown item with 22 dp decorative image tinted onSurface; menu expansion is remembered in composition and closes before dispatching its action. |
| Root snackbar | Shared `SnackbarHost`; horizontal/bottom safeDrawing insets and 72 dp bottom padding on all destinations. `RowToolApp` appends resolved strings to a remembered FIFO list. A single presenter calls `showSnackbar` only inside activity `repeatOnLifecycle(RESUMED)`. Dropping below resumed cancels and removes the active snackbar from the host but leaves the queue head; returning to resumed presents it again with a fresh default visible duration. Messages arriving while paused/stopped wait, identical messages are not deduplicated, completed entries do not replay, and destination removal/navigation does not cancel the root-owned queue. The queue and host are not saveable, so activity recreation/process death may lose an already consumed-but-not-completed root message. There is no action label or explicit duration override. Fixed clearance is not a measured non-overlap guarantee. |

Scaffolds provide content insets. No alternate two-pane/tablet navigation or orientation-specific screen exists. Projects and Counter use the shared 20 dp phone padding; Settings pads individual rows by 16 dp. `RowToolDimens` contains spacing tokens 4/8/12/16/20/24/32 dp, a 600 dp content cap, the shared 48 dp `MinimumTouchSize`, and Counter-specific sizing tokens. `ArchivedProjectsHeader`, settings rows, `CounterImageButton`, counter scaling, and tests all reuse the same minimum-touch authority. Counter font compensation limits number growth beyond font scale 1.3; it does not cap other text.

### Editors and accessibility semantics

`ProjectEditorDialog` is a custom `Dialog(usePlatformDefaultWidth = false)`. Its Surface modifier order is `fillMaxWidth(0.92f) → wrapContentWidth() → widthIn(max = 560.dp) → heightIn(max = windowHeight * 0.88) → imePadding()`. The wrap modifier relaxes the exact fill-width constraint before the cap. Shape, container/content/title colors and tonal elevation come from `AlertDialogDefaults`, not a hard-coded 16 dp dialog shape. The entire column, including actions, scrolls with 24 dp padding and 16 dp gaps.

New defaults are empty name, ROWS, start 0, target/repeat disabled. Choice FlowRows wrap FilterChips with 8 dp gaps. Name and enabled numeric fields are filled TextFields with large (16 dp) shapes, surfaceVariant background, transparent ordinary indicators, secondary focused label/cursor. Error container and readable error foreground roles are explicit. Optional toggle rows own Role.Switch and give text remaining width with weight; child Switch callbacks are null. Disabled optional text remains saved in the editor but emits null. Numeric parsing uses `toLongOrNull`/`toIntOrNull`, no input truncation, grouping parser or automatic whitespace cleanup. Name is trimmed and checked by code-point count. Save requires every enabled input to be valid; invalid enabled target/repeat blocks independently. Actions wrap in a FlowRow aligned end, 8 dp horizontal/12 dp vertical gaps and 8 dp top padding.

The project editor's repeat fields include an own start count alongside the repeat length. A separate `RepeatSettingsDialog` opens from the Counter menu or the repeat display. It shares repeat validation and field labels with the project editor, offers the current count plus one as a shortcut when within bounds, and preserves its draft across recreation. Its scrollable content and IME padding accommodate narrow screens and large text. Save remains pending until the repeat-only repository transaction succeeds; a failed write keeps the dialog and draft available for retry.

`CountEditorDialog` uses AlertDialog and an OutlinedTextField, selects the whole current count via saveable TextFieldValue, and requests focus. Number keyboard/Done are hints, not an installed keyboard submit callback. Save is disabled outside 0..999999 or on parse failure. Focused border/label/cursor use secondary; errors explicitly use errorContainer/onErrorContainer. All ordinary dialog Save/Cancel/reset/archive actions use secondary text. Project delete and replacement-import confirmation use errorContainer/onErrorContainer; reset/archive remain ordinary actions. Project/count editor dismissal happens before their mutations; repeat settings close only after a successful save, and import failure can retain its preview.

- Count exposes Role.Button, heading, formatted state description and labeled OnClick set-count semantics (not a separate custom-actions list). Disabled archived count has no custom enabled callback.
- Counter image boxes merge descendants, name their action and expose disabled state; inner images have null descriptions. Plus disables at 999999, minus at zero, undo without history, all three when archived.
- Counter title, editor title and Settings section labels have explicit heading semantics. Projects labels are styled as section labels but do not explicitly call heading().
- Project row retains deprecated `isContainer` semantics under a local suppression to preserve its former Card accessibility container. Active row and overflow are distinct actions; archived row does not claim a count-opening action.
- Settings radio/switch rows own selectable/toggleable semantics; child controls have null callbacks. The code does not add a separate selectableGroup modifier.
- Target completion, archive state, repeat totals, validation errors and selected controls communicate state beyond color. Localized/pluralized descriptions are source evidence, not a completed TalkBack audit.

CounterRoute sets `View.keepScreenOn` only for enabled preference plus active project and clears it in DisposableEffect disposal. It is scoped to composition, with no explicit lifecycle-resumed test. MainActivity enables edge-to-edge and updates status/navigation icon lightness from theme selection. XML launch themes use system day/night resources before DataStore is read, so splash and an explicitly selected theme may differ temporarily. XML platform font is sans; Compose typography is separate. RTL is declared, but the back vector has no explicit autoMirrored attribute. Runtime focus, TalkBack traversal, all font scales/locales, system bars and touch filtering were not exercised by this documentation task.

### Material color roles

Explicit mappings from `Color.kt` and `Theme.kt`; values are opaque RGB hex. Roles not overridden there retain Material 3 defaults.

| Role | Light | Dark |
|---|---|---|
| primary / onPrimary | `#C45100` / `#FFFFFF` | `#C45100` / `#FFFFFF` |
| primaryContainer / onPrimaryContainer | `#D4722A` / `#161610` | `#D4722A` / `#161610` |
| secondary / onSecondary | `#394B18` / `#FFFFFF` | `#93AE4F` / `#161610` |
| secondaryContainer / onSecondaryContainer | `#D0DDB5` / `#2E2A1E` | `#3A4020` / `#E8E4D0` |
| tertiary / onTertiary | `#9A7B18` / `#161610` | `#C9A435` / `#161610` |
| tertiaryContainer / onTertiaryContainer | `#E8DFB5` / `#2E2A1E` | `#3A3520` / `#E8E4D0` |
| error / onError | `#8B3030` / `#FFFFFF` | `#C44D4D` / `#FFFFFF` |
| errorContainer / onErrorContainer | `#EAD0D0` / `#2E2A1E` | `#3A2020` / `#E8E4D0` |
| background / onBackground | `#E8E4D0` / `#2E2A1E` | `#1E1E12` / `#E8E4D0` |
| surface / onSurface | `#D2CDB5` / `#2E2A1E` | `#2E2E20` / `#E8E4D0` |
| surfaceVariant / onSurfaceVariant | `#BBB59A` / `#4C4634` | `#3A3A2A` / `#B8B4A0` |
| surfaceContainerLowest | `#E8E4D0` | `#1E1E12` |
| surfaceContainer | `#C8C3A8` | `#3A3A2A` |
| surfaceContainerLow | `#D2CDB5` | `#2E2E20` |
| surfaceContainerHigh | `#C8C3A8` | `#3A3A2A` |
| surfaceContainerHighest | `#A49D80` | `#454535` |
| outline / outlineVariant | `#4A473C` / `#C5C0A8` | `#A8A491` / `#3A3A2A` |
| inverseSurface / inverseOnSurface | `#2E2E20` / `#E8E4D0` | `#D2CDB5` / `#2E2A1E` |
| inversePrimary | `#D4722A` | `#C45100` |
| scrim | `#000000` | `#000000` |

### Typography, shapes, and previews

All 15 typography styles use the bundled variable Outfit font at `app/src/main/res/font/outfit.ttf`. `Type.kt` creates OptionalLocal font entries with explicit variation weights 400/500/600/700/800, each paired with a device `sans-serif` fallback. No downloadable font provider is used. ExtraBold 800 is available in the family but no configured typography style requests it. Custom line heights are not specified.

| Style | Weight | Size sp | Letter spacing sp |
|---|---|---|---|
| displayLarge | Bold | 57 | -0.25 |
| displayMedium | Bold | 45 (counter overrides responsively) | 0 |
| displaySmall | SemiBold | 36 | 0 |
| headlineLarge | Bold | 32 | 0 |
| headlineMedium | SemiBold | 28 | 0 |
| headlineSmall | SemiBold | 24 | 0 |
| titleLarge | SemiBold | 22 | 0 |
| titleMedium | SemiBold | 16 | 0.15 |
| titleSmall | Medium | 14 | 0.1 |
| bodyLarge | Normal | 16 | 0.5 |
| bodyMedium | Normal | 14 | 0.25 |
| bodySmall | Normal | 12 | 0.4 |
| labelLarge | SemiBold | 14 | 0.1 |
| labelMedium | SemiBold | 12 | 0.5 |
| labelSmall | SemiBold | 11 | 1.5 |

`Color.kt` now contains only the palette values consumed by `Theme.kt`; previously unused alternate-background, secondary-muted, disabled, and success declarations are absent. Material roles not explicitly passed (including surfaceTint, surfaceBright/dim and fixed accent roles) retain library defaults; no dynamic-color branch exists.

Custom shape radii are small 8 dp, medium 12 dp, and large 16 dp; other shape slots retain defaults. Spacing tokens are 4, 8, 12, 16, 20, 24, and 32 dp, with 600 dp maximum content width.

`ui/RowToolPreviews.kt` provides eight `@Preview` configurations: empty Projects, active/archived Projects, basic Counter, target Counter, repeat Counter, reached-target dark Counter, light Settings, and dark Settings. Preview projects are sample-only values constructed by `previewProject`; they do not seed the database. Previews call content composables with inert actions and do not exercise repositories, lifecycle effects, file pickers, or navigation.

### Localization and asset inventory

There are **100 string keys and 8 plural groups in each of 11 locale resource files** at inspection time. Base `values/strings.xml` is English. The other locales are Finnish `fi`, Swedish `sv`, German `de`, French `fr`, Spanish `es`, Portuguese `pt`, Italian `it`, Norwegian Bokmål `nb`, Danish `da`, and Dutch `nl`. `locales_config.xml` lists exactly these locale codes. XML parsing and resource-key parity were verified in this task; neither is a linguistic review. Plural groups: `counter_target_rows`, `counter_target_rounds`, `row_count`, `round_count`, `repeat_count`, `project_count`, `active_project_count`, `archived_project_count`. Every file supplies one/other; es/fr/it/pt also supply many.

Six limit messages are formatted at the call site instead of embedding independent bounds: `backup_import_too_large` receives `BackupCodec.MAX_BACKUP_MIB`; `project_name_too_long`, `project_target_error`, and `project_repeat_error` receive `ProjectValidation` limits (with target maximum from `CounterConstants.MAX_COUNT`); `counter_set_error` and `counter_max_reached` receive `CounterConstants.MIN_COUNT`/`MAX_COUNT` as applicable. All 11 catalogs retain the same placeholders. Android resource formatting localizes the grouped large-count placeholders (`%,d`) for target/set/maximum messages; the MiB/name/repeat values use ordinary integer placeholders. Base `project_name_too_long` alone has a narrow `tools:ignore="PluralsCandidate"` because its numeric argument is the fixed validation bound 60, not a varying quantity needing plural selection. This is not a general plurals policy. `LimitMessageResourcesTest` substitutes deliberately different bounds in every locale and checks argument use, localized grouping, and absence of unresolved/old literal limits.

Counter count and target use `NumberFormat.getIntegerInstance()` remembered against configuration; project list counts/summaries and repeat text use Android string/plural formatting. Count input uses plain `Long.toString()` and Kotlin number parsing rather than parsing locale-specific grouping separators. Import preview dates use device-locale/time-zone DateFormat; exported filenames are `rowtool-backup-YYYY-MM-DD.json` using local ISO date. Counter title and Projects/Settings section capitalization use the first configuration locale, even when displayed resources fall back to another language. The Settings content test explicitly supplies Finnish regional fallback resources with Turkish casing; it does not prove automatic Finnish resource selection from `tr,fi`, which resolves to default English for this heading in the current APK because dependency assets include Turkish. This is not an additional supported Turkish translation. These formatting paths should be considered separately when reviewing locale behavior.

The three root and packaged WebP pairs were SHA-256-compared and match. Vector action drawables include archive, back, delete, edit, expand, more, restore, and settings (24 dp); the old add vector is absent from the working tree; adaptive launcher definitions exist for regular/round icons, including API 33 monochrome support. Source manifest removes `androidx.profileinstaller.ProfileInstallReceiver` during merging. Launcher foreground reuses the plus WebP through an 18% inset drawable. Base adaptive icons suppress MonochromeLauncherIcon because API 33 variants provide the explicit monochrome plus silhouette. Day/night launcher and splash backgrounds are #E8E4D0/#1E1E12, and XML system_bar is #E8E4D0/#161610. Artwork is also used for Projects creation. Binary pair equality was checked in this task; this does not establish artwork licensing.

Asset paths: root `counter_plus_button.webp`, `counter_minus_button.webp` and `counter_undo_button.webp` each have a same-named packaged copy under `app/src/main/res/drawable-nodpi/`. The font is `app/src/main/res/font/outfit.ttf`; launcher definitions are `app/src/main/res/mipmap-anydpi/` and `app/src/main/res/mipmap-anydpi-v33/`. The root artwork originals are not Android source-set resources.

## Manifest and privacy boundary

The widget addition was checked against the merged debug manifest. Runtime permissions are WAKE_LOCK and RECEIVE_BOOT_COMPLETED (Glance/WorkManager), plus the existing signature-protected dynamic-receiver permission. There is no Internet, notification, storage, camera, microphone, or vibration permission. Merge directives remove unnecessary transitive ACCESS_NETWORK_STATE and FOREGROUND_SERVICE permissions and SystemForegroundService. MainActivity and WidgetConfigurationActivity are exported; the configuration activity validates that the supplied ID belongs to the RowTool provider. Both app widget receivers are non-exported. Library remote-view/job/diagnostic components are protected by BIND_REMOTEVIEWS, BIND_JOB_SERVICE, or DUMP where exported. This is debug-artifact evidence, not a signed release audit.

The application sets `android:allowBackup="false"` and `android:usesCleartextTraffic="false"`. `backup_rules.xml` and `data_extraction_rules.xml` exclude app files, databases, shared preferences, and external files from system backup and device transfer. Manual JSON export is the supported portability path.

Production source has no network client, Firebase, analytics, advertising, billing, crash-reporting, WebView, FileProvider or notification implementation. Test/build/scanner configurations include networking libraries and must not be confused with runtime features. The stability plugin's runtime dependency is present in the lockfile; no app telemetry call path is implemented.

This is a production-source boundary. Build/scanner tools do access external services and are not part of the offline product behavior. The source manifest alone does not prove every component or permission in a merged release artifact. There are no app-defined services or content providers. The widget configuration activity and two receivers implement only the widget contract; the explicit profile-installer removal remains a merge directive.

The `app/src/debug/AndroidManifest.xml` overrides the Compose test host `androidx.activity.ComponentActivity` theme to `Theme.RowTool` with `tools:replace="android:theme"`. This is a debug overlay, not an extra production application screen. The widget task inspected the merged debug manifest and installed the debug APK in its own emulators; no release AAB was inspected.

`MainActivity` and `WidgetConfigurationActivity` reject obscured-window touches: the decor view enables `filterTouchesWhenObscured`, and `dispatchTouchEvent` drops events marked `FLAG_WINDOW_IS_PARTIALLY_OBSCURED`. Changes to activity-level touch handling must preserve this tapjacking boundary.

## Build and verification commands

Use JDK 21 for the full local build and test commands and Android SDK Platform 37. Application Java source/target compatibility and the Kotlin JVM target remain 17. The primary Android CI workflow also runs Gradle on Temurin JDK 21, while the CodeQL build runs on Temurin JDK 17. On Windows PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat --no-configuration-cache :app:testDebugUnitTest
.\gradlew.bat :app:lintDebug
.\gradlew.bat :app:kspDebugKotlin
.\gradlew.bat :app:assembleDebugAndroidTest
```

Additional configured checks (run from repository root with the same JDK/SDK prerequisites):

```powershell
.\gradlew.bat :app:lintRelease
.\gradlew.bat :app:compileDebugAndroidTestKotlin
.\gradlew.bat :app:ktlintCheck
.\gradlew.bat :app:detekt
.\gradlew.bat :app:stabilityCheck
.\gradlew.bat --no-configuration-cache :app:createDebugUnitTestCoverageReport
.\gradlew.bat :app:dependencyCheckAnalyze
.\tools\lc.ps1
.\tools\tests\android-check-routing.tests.ps1
.\tools\tests\sonar-token.tests.ps1
```

`ktlintFormat` is a source-mutating formatter, not a documentation verification command. JDK 21 is the repository-documented full build/test baseline; there is no Java toolchain block proving all Gradle tasks work on each other JDK. SDK path must be available through normal AGP configuration (for example ANDROID_HOME or local.properties), with platform 37 installed. Test coverage tasks explicitly opt out of configuration cache; use the shown flag to avoid relying on the global enabled default.

Run device/emulator tests only when a target is available:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

Release tasks are configured with code/resource shrinking:

```powershell
.\gradlew.bat :app:assembleRelease
.\gradlew.bat :app:bundleRelease
```

No release `signingConfig` or key reference is declared in the inspected build files; configure signing externally before treating an AAB as Play-ready. In a Git checkout, also run `git diff --check` before handoff.

Test sources are split between host-side JUnit tests under `app/src/test/` and AndroidJUnit4/Compose device tests under `app/src/androidTest/`. The host suite includes pure domain/presentation tests plus Robolectric-backed Room, repository, DataStore, resource, and ViewModel tests. The device suite covers isolated screen semantics, lifecycle delivery, and full-activity navigation/persistence flows. `assembleDebugAndroidTest` compiles the device tests; `connectedDebugAndroidTest` or an explicit targeted `adb shell am instrument` command executes them. CI in `.github/workflows/android.yml` runs the debug build, host suite, debug lint, and Android-test compilation with JDK 21. These command descriptions do not assert a result for a run. Final local verification is recorded below. Instrumentation execution, helper regressions, and hosted CI are separate checks.

## Test inventory and evidence limits

Current source inventory on 2026-09-27: **92 production Kotlin files**, **37 host Kotlin files / 34 test classes / 217 @Test methods**, and **40 Android-test Kotlin files / 33 test classes / 121 @Test methods**. Four parameterized methods add four runner cases; three existing API 29–32 restrictions leave **122 eligible API 36 cases**. Execution and the conditional skip are recorded in the finalization section above. The following table is a retained partial feature-era map, not the complete current inventory; later feature and investigation sections describe subsequent additions. Paths are relative to the indicated source-set package root `java/com/finnvek/rowtool/`.

| Source set / file | Tests | Source cases |
|---|---:|---|
| test: `data/repository/ProjectNoteStoreTest.kt` | 3 | Note CRUD/isolation, Unicode/raw-text bounds, current save reading, no-op, attachment changes, conflicts, archive/cascade, and preservation of counter/repeat/reminder/history state. |
| test: `data/repository/ProjectNoteBackupTest.kt` | 3 | v5 round-trip and normalization, invalid field/owner/duplicate collections, 5,000-code-point boundary, and v1-v4 compatibility. |
| test: `ui/screens/note/NoteEditorViewModelTest.kt` | 5 | Read failure and loading guard, failed writes and restored drafts, duplicate/pending saves and another editor, conflict/confirmed reload/exit/delete, and completed-draft clearing. |
| androidTest: `ui/screens/counter/ProjectNoteUiTest.kt` | 2 | Counter menu, scoped preview and delete assertions, unsaved exit cancellation, and both-theme 320 dp / 200% long-text/keyboard/Save/confirmation checks. |
| androidTest: `ui/ProjectNoteLifecycleTest.kt` | 3 | Actual Activity recreation across draft/save, archived note reading/restoration through Projects, and draft protection on external archive/delete. |
| androidTest: `ui/ProjectNoteDocumentFlowTest.kt` | 1 | Real v5 system document-picker export and local-provider restore with exact text, saved reading and saved timestamp. |
| test: `data/repository/AdditionalCounterRepositoryTest.kt` | 13 | CRUD/isolation, current-count preservation during metadata edits, following/manual mutations, exact multi-counter undo, legacy undo, hidden-counter retention/pruning, bounds/no-ops, concurrent changes, missing/archive guards, transactional failure rollback and project deletion. |
| test: `data/repository/AdditionalCounterBackupTest.kt` | 12 | v5 round-trip including repeat starts, hidden counters/history and reminders; v1/v2/v3 compatibility; invalid identities/ownership/values/history and repeat starts; import rollback, oversized export rejection and defensive collection copies. |
| test: `data/local/RowToolMigrationTest.kt` | 6 | Real exported v1/v2/v3/v4 SQLite schema migration through v5 with project settings/history preserved, legacy undo, empty reminders on old data, and close/reopen persistence. Uses Robolectric native SQLite. |
| test: `data/repository/ReminderStoreTest.kt` | 4 | Exact owner/version/occurrence acknowledgement, stale edits and duplicate create, archive/delete/cascade, and count/Undo/reset recalculation without erasing acknowledgement. |
| test: `data/repository/ReminderBackupTest.kt` | 3 | v4 round-trip including acknowledgement above current count, invalid reminders rejected before import, and v1/v2/v3 import without reminders. |
| test: `domain/model/ReminderRulesTest.kt` | 3 | One-time due/acknowledged status, recurring 32/6 and direct jump, missed counts, interval one, bounds, and reset behavior. |
| androidTest: `ui/screens/counter/AdditionalCountersUiTest.kt` | 5 | Empty add action, manual/following rows at narrow width and large text in both themes, editor draft/mode restoration, busy-save guard, current-count changes, and long-name editor fit. |
| androidTest: `ui/screens/counter/AdditionalCounterSaveTest.kt` | 1 | Real SQLite write failures preserve name/count drafts and dialog state; retry after removing the failure closes only on success. |
| test: `domain/counter/CounterCalculatorsTest.kt` | 10 | Absent progress, default and shifted repeat boundaries, maximum count, below/exact/above target, clamping. |
| test: `domain/model/ProjectValidationTest.kt` | 14 | Blank/trimmed/Unicode-length names; start, count, target, repeat limits; aggregate errors; persisted-unit fallback. |
| test: `data/repository/CounterRepositoryTest.kt` | 21 | Mutations, shifted repeat metadata across count/undo changes, repeat-only save/no-op, boundary no-ops, undoable manual/reset, 100-history cap, cascade deletion, archived/missing rejection, rapid and concurrent increments, project cap, metadata changes, and no-op edit preservation. |
| test: `data/repository/BackupCodecTest.kt` | 19 | Roundtrip, v3 repeat-start requirements, extra/missing fields, exact version/app/unit checks, invalid numeric values, duplicate/project-cap precedence, malformed JSON/UTF-8, displayed 5 MiB boundary, early bounded-stream rejection, and trimmed names. |
| test: `data/repository/ValidatedBackupTest.kt` | 9 | Factory acceptance boundaries, normalization, blank/duplicate IDs, project-count limit, invalid domain fields, and defensive snapshot of a mutable caller collection. |
| test: `data/repository/BackupAndPreferencesRepositoryTest.kt` | 17 | Defaults including unknown-theme fallback, valid/missing/archived startup selection, compare-and-set clear, preference failures, validation boundary, atomic replacement/rollback/history clearing, empty/archived-only imports, imported ordering, export inclusion of undo history, note round-trip and legacy note replacement. |
| test: `test/InMemoryPreferencesDataStoreTest.kt` | 1 | Concurrent updates to the test DataStore implementation are serialized. |
| test: `ui/LimitMessageResourcesTest.kt` | 1 | All 11 locale catalogs consume supplied name/target/repeat/count/backup bounds, group large counts by locale, and leave no stale literals/placeholders. |
| test: `ui/screens/counter/CounterPresentationTest.kt` | 6 | Control scaling at ordinary/extreme widths and minimum targets, unit resource mapping, count sizing, SDK-dependent haptic constant, and unavailable-project message handling without navigation effect. |
| test: `ui/screens/counter/CounterViewModelTest.kt` | 7 | Deletion publishes missing state without a navigation effect; archived state/message remains observable; haptics do not replay without a collector; shifted repeat, target and reminder strong/light classification with silent manual/reset behavior. |
| test: `ui/screens/projects/ProjectEditorInputValidationTest.kt` | 8 | Domain/editor name and optional-bound parity, repeat-start bounds, normalization, ignored disabled values, combined invalid input, and independently invalid enabled target/repeat. |
| test: `ui/screens/projects/ProjectsViewModelTest.kt` | 1 | Dedicated project-limit message. |
| test: `ui/screens/settings/SettingsViewModelTest.kt` | 1 | Latest selected import wins when file reads finish out of order. |
| test: `ui/theme/ThemeTest.kt` | 7 | Selected palette/surface mappings, primary-container consistency, tertiary/project-menu/error contrast, dialog accent contrast non-regression. Not a full rendered palette certification. |
| androidTest: `ui/RowToolFlowTest.kt` | 3 | Create/count/correct/undo/activity recreation, editable count and undo, reset/delete confirmation. |
| androidTest: `ui/CounterHapticLifecycleTest.kt` | 2 | A mutation completing after navigation does not replay haptics; a fresh resumed mutation delivers once; disabling preference suppresses delivery; coincident reminder/target milestone has one strong feedback. |
| androidTest: `ui/CounterRouteNavigationTest.kt` | 3 | Missing/archive return retries after route composition recreation when navigation did not complete; a valid project stays on Counter. |
| androidTest: `ui/DirectCounterNavigationTest.kt` | 3 | Deleted direct-start project returns after stop/recreation; empty import clears Settings/direct-counter flow through graph anchor; ordinary direct-counter Back leaves no stale Counter. |
| androidTest: `ui/screens/projects/ProjectsScreenContentTest.kt` | 9 | Empty/loading/active opening, 72 dp image creation action, active/archived menu identity after reorder, archive expansion/restore/delete, large text and metadata, locale-aware casing. |
| androidTest: `ui/screens/projects/ProjectEditorDialogTest.kt` | 9 | Toggle ownership/IME hints, invalid pasted target/repeat start, defaults/save/edit/cancel, saveable restoration including disabled numeric text, narrow large French/Italian forms. |
| androidTest: `ui/screens/counter/CounterScreenContentTest.kt` | 9 | Control hierarchy/roles/targets/callbacks, repeat menu/display opening, maximum/zero/archive disablement, count edit action, target/repeat values and visibility, locale title, 320/400 dp and 1×/2× text in both themes with 0/999999 counts. |
| androidTest: `ui/screens/counter/RepeatSettingsDialogTest.kt` | 3 | Count-plus-one shortcut, saveable draft/error retry, maximum-count shortcut state, narrow 320 dp / 200% text and keyboard interaction in both themes. |
| androidTest: `ui/screens/counter/RepeatSettingsSaveTest.kt` | 1 | Real SQLite write failure retains the repeat draft through recreation and a successful retry closes the dialog. |
| androidTest: `ui/screens/counter/ReminderUiTest.kt` | 3 | Full long messages and per-reminder acknowledgement in light/dark themes, editor failure/draft restoration/visible keyboard at 320 dp and 200% text, and stable main-button position on reminder arrival. |
| androidTest: `ui/screens/counter/ReminderRouteTest.kt` | 1 | Counter menu to editor/list, save and acknowledgement using the real Room state. |
| androidTest: `ui/screens/counter/CounterImageButtonTest.kt` | 2 | Enabled description/callback; disabled semantics and ignored clicks. |
| androidTest: `ui/screens/counter/CountEditorDialogTest.kt` | 1 | Out-of-range pasted count disables Save. |
| androidTest: `ui/screens/settings/SettingsScreenContentTest.kt` | 7 | Exactly-once preference/action callbacks, duplicate-label isolation in English/Finnish, row semantics and minimum height, About text, Turkish casing of supplied Finnish fallback resources, English/Finnish heading comparison, narrow French/Italian text growth/fit. |
| androidTest: `ui/DialogReadabilityTest.kt` | 4 | Parameterized light/dark count-editor selection/errors, ordinary versus destructive dialog actions, delete cancellation/confirmation, replacement-import cancellation/confirmation via temporary file. |
| androidTest: `ui/SnackbarLifecycleTest.kt` | 6 | Active/default duration restarts after stop, background messages wait for resume, FIFO/duplicate behavior, pause handling, no replay after completion, and root ownership across source-destination removal. |
| androidTest: `ui/TextLayoutAssertionsTest.kt` | 1 | The text-fit helper accepts fitting/wrapped text and rejects deliberately clipped or ellipsized text. |

The Robolectric classes use SDK 36. Repository/ViewModel fixtures use in-memory Room and test dispatchers where applicable. Migration/persistence tests use on-disk databases created from the checked-in v1, v2, v3, and v4 schemas. `BackupAndPreferencesRepositoryTest` also creates a real temporary Preferences DataStore file plus failure/stale-read fakes; ViewModel fixtures use `InMemoryPreferencesDataStore`. That test DataStore has a Mutex and MutableStateFlow; it is not the production on-disk delegate or its corruption handler. Counter repository accepts injected clock/ID generator; backup/settings accept IO dispatchers for controlled execution. `LimitMessageResourcesTest` is Robolectric-backed and iterates all 11 resource locales inside one test method.

Device content tests use `createComposeRule`; full activity tests use `createAndroidComposeRule<MainActivity>()`; `DialogReadabilityTest` and `SnackbarLifecycleTest` use `createAndroidComposeRule<ComponentActivity>()`; lifecycle/navigation probes may use the v2 Compose rule with explicit NavHost/ViewModel setup. `PrepareApplicationStateRule` clears Room and applies scenario setup before the wrapped activity rule. Activity/composition recreation tests are not actual OS process-kill tests. The haptic lifecycle test observes calls through a test `View`, not physical vibration. No screenshot-golden suite, all-locale visual matrix, production DataStore corruption test, or real system document-provider end-to-end import/export test is established by this inventory.

Test helpers `test/InMemoryPreferencesDataStore.kt` and `test/ProjectEntityFixtures.kt` have no test annotations. Android helpers `ui/PrepareApplicationStateRule.kt`, `ui/LayoutDiagnostics.kt` and `ui/TextLayoutAssertions.kt` likewise do not add test counts. `TextLayoutAssertions` rebuilds `MultiParagraph` using measured width before checking overflow/ellipsis; its comment attributes the workaround to Compose 1.12.1, but this task did not independently reproduce upstream behavior. `LayoutDiagnostics` now contains only `ComposeTestRule.captureDiagnostic`; the removed `TextLayoutResult.diagnostics()` extension is not part of current source. Diagnostics write screenshots under app external-files directories `diagnostics`, `dialog-readability`, and `counter-presentation`. These captures have no checked-in expected-image comparisons and are not screenshot goldens. Import-dialog tests use a temporary file URI/intent monitor, not a real user-selected document-provider flow.

### Project-note verification, 2026-09-23

- JDK 21 / user Gradle cache: `:app:testDebugUnitTest`, `:app:assembleDebug`, `:app:assembleDebugAndroidTest` (including `compileDebugAndroidTestKotlin`), direct `:app:ktlintCheck`, `:app:lintDebug`, and `:app:detekt` completed. Parsed host XML: **174 methods in 23 suites, 0 failures/errors/skips**. Parsed ktlint and Detekt reports: **0 findings**. Lint: **0 errors, 3 unchanged version warnings** (`AndroidGradlePluginVersion`, `GradleDependency`, `NewerVersionAvailable`). Logs are in ignored `reports/project-note/final-gradle.txt` and `final-test-checks.txt`; report contents, not just exit codes, were checked.
- The **13 added host methods** cover note CRUD/isolation, whitespace/newlines/Unicode bounds, blank/no-op/attachment semantics, current transaction reading, separate counter/reminder/repeat/history preservation, archive/restore/cascade, stale edit/delete/recreate, read/write failure, over-limit draft retention, save overlap and another editor's state, confirmed exit/reload/delete, saved-state restoration and completed-draft clearing. They also cover v1-v4 imports, v5 round-trip, malformed/duplicate/orphan/out-of-range note rejection, legacy replacement clearing notes, replacement rollback, fresh versions after import, the real exported v4-to-v5 migration and reopen persistence. The existing v1-v3 migration tests now exercise the full chain to v5. These migration checks use Robolectric native SQLite, not an Android-device legacy migration.
- **6 distinct Android test methods** passed on the dedicated API 36 `RowToolNote` emulator, explicitly selected as `emulator-5596`. The final system configuration was 640×1280 pixels at 320 dpi (320 dp wide), with `font_scale=2.0`. The methods contain **8 scenario paths** when counting the two light/dark loops separately: Counter menu/draft-discard/preview/delete in each theme (2), long multiline text/visible keyboard/Save/delete cancellation in each theme (2), actual Activity recreation and no saved-draft resurrection (1), archive/read-only/restore through Projects (1), external archive/delete with preserved draft and confirmed exit (1), and real document export/restore (1). This is not a claim to have run the entire Android suite. All six methods passed together in `reports/project-note/emulator-final.txt`; the earlier focused document rerun is also retained in `document-system-320-200.txt`. Screenshots in `system-screenshots/` were inspected for preview, keyboard/Save and delete confirmation in both themes; the final combined run also retained six screenshots in `final-screenshots/`. The dedicated RowToolNote emulator was stopped after verification; no other emulator was used or stopped.
- **Real SAF file flow passed** through Android's system document picker and its local Downloads provider, using synthetic data only. The exported v5 sample is `reports/project-note/document-flow-v5.json`. The test deletes the live note after export, then selects the real file and confirms replacement. It verifies exact multiline/indented Unicode text, saved count **74** while the restored main counter is **0**, and the exact original `savedAt`. Both ordinary-device settings and the final 320 dp / 200% configuration passed this flow; no intent monitor or fake URI substituted for the picker.
- Retained development failures: `emulator-first.txt` contains a Delete assertion that matched both the editor and confirmation dialog; it was scoped to the confirmation dialog without changing the expected deletion. The first system-scale document attempt in `emulator-system-320-200.txt` could not find an offscreen lazy item; the test now scrolls the Settings `LazyColumn` to the actual Export/Import item. An initial host overlap fixture suspended inside a Room transaction on the test dispatcher and timed out; the revised fixture gates the repository mutex while another real transaction advances the count, retaining the same assertions. Old export/migration expectations changed from 4 to 5, and unsupported schema from 5 to 6, solely because v5 is now supported. The archive-draft regression first failed with expected DISCARD but actual null (`archive-draft-red.txt`); removing the read-only exemption and keeping the Counter editor mounted until explicit closure fixed it. Early test-first runs recorded missing new APIs as compile failures; they are not claimed as executed assertion failures.
- The earlier intermittent **DIRECT START remains open** and was not rerun. The full Android suite, hosted CI, physical phone, release build, other document providers, runtime checks in all eleven languages and external security scan stack were not run. Resource-key parity was checked; translated wording was not professionally reviewed. Earlier reminder/repeat verification records remain historical. No dependencies were updated, and no commit or push was made. The pre-existing `app/build.gradle.kts` content and unrelated baseline files were preserved byte-for-byte.

### Project-reminder verification, 2026-09-23

- JDK 21 Gradle checks completed `:app:testDebugUnitTest`, `:app:assembleDebug`, `:app:compileDebugAndroidTestKotlin`, `:app:assembleDebugAndroidTest`, direct `:app:ktlintCheck`, `:app:detekt`, and `:app:lintDebug`. The final host XML reports show **161 tests in 20 suites, 0 failures/errors/skips**. The four ktlint check XML reports and Detekt XML have **0 findings**. Debug lint XML has **0 errors and 3 warnings** (`AndroidGradlePluginVersion`, `GradleDependency`, `NewerVersionAvailable`); dependency/tool versions were left as found. All 11 resource catalogs contain the same 30 reminder string/plural names. The checks and build logs are retained in ignored `reports/reminder-*.log` files. `git diff --check` passed after the implementation.
- Host tests cover one-time and recurring 32/6 scheduling, jumps and interval one, missed counts, count decrease/reset/direct set/Undo, persistent acknowledgement and reset, exact target/revision/owner checks, duplicate create, schedule edits, archive/delete cascade, and reminder-triggered haptics. Real exported v1/v2/v3 SQLite schemas migrate through v4 under Robolectric; v4 reopen preserves reminders while old projects acquire none. Backup tests cover v1/v2/v3 compatibility, v4 round-trip and invalid reminder rejection before replacement. This is host SQLite evidence, not an Android-device migration run.
- A dedicated API 36 `RowTool_Reminders_20260923` emulator (`emulator-5596`, 320 dp width, 200% font) ran **6 distinct instrumentation test methods**, all passing in the final runs: 4 reminder UI/route methods in `reports/reminder-instrumentation-rerun.log` and 2 haptic lifecycle methods in `reports/reminder-haptic-instrumentation.log`. Two of the six methods were rerun after strengthening their assertions: the keyboard/editor method passed in `reports/reminder-keyboard-instrumentation.log`, and the long-message list method passed in `reports/reminder-long-message-instrumentation.log`. These reruns add no distinct test methods. Across the six methods, **18 separately asserted situations** were exercised: full long-message text in light/dark and per-item acknowledgement (3); failed editor save, saved-state draft restoration, visible soft keyboard with accessible Save at 320 dp/200%, and dark-theme retry (4); stable main-button position on arrival (1); menu/editor opening, saved list state, and persisted acknowledgement through Room (3); delayed haptic without replay, fresh resumed feedback, and disabled-preference suppression (3); no haptic on reminder save, one strong feedback for coincident reminder/target, no navigation replay, and silent reset (4). The test harness supplies 320 dp/200% in the reminder dialog tests; the isolated emulator used those system settings. These counts describe exercised assertions, not the size of the unfiltered Android suite.
- The first 4-method reminder selection had **2 failures**. One exposed a real saveable-draft loss after restoration; the draft state was moved to the stable dialog owner. The other assertion incorrectly expected one text node even though the same message intentionally appears in both the compact section and list; it now expects two. The unchanged selection then passed 4/4, and the haptic class passed 2/2. The initial failure log is retained as `reports/reminder-instrumentation.log`. No functional requirement was relaxed.
- The earlier intermittent `DIRECT START` test was outside this selection and remains open. The complete Android suite, physical phone, hosted CI, release build, external scan stack, and real document-provider import/export were not run. The dedicated emulator process was stopped after testing; other emulators were not used or stopped. No commit or push was made.

### Repeat-start verification, 2026-09-23

- The final JDK 21 Gradle run completed `:app:testDebugUnitTest`, `:app:assembleDebug`, `:app:assembleDebugAndroidTest`, direct `:app:ktlintCheck`, `:app:detekt`, and `:app:lintDebug`. Host XML reports contain **144 tests in 17 suites, 0 failures/errors/skips**. Ktlint and Detekt report no findings. Debug lint XML has **0 errors and 3 dependency/tool-version warnings** (`AndroidGradlePluginVersion`, `GradleDependency`, `NewerVersionAvailable`); versions were not changed for this feature. `git diff --check` passed.
- Host tests cover repeat arithmetic, shifted boundary haptics and retained target feedback, repeat-only writes/no-op and Undo behavior, real exported v2→3 and v1→3 Room migrations and reopen, and v1/v2/v3 backup compatibility including invalid v3 rejection before replacement. Existing counter, additional-counter, and backup regressions ran in the full host suite. Robolectric SQLite and in-memory repository tests are host evidence, not Android-device migration evidence.
- A dedicated API 36 `RowTool_Repeat_20260923` emulator at `emulator-5594` ran eight distinct feature UI scenarios: repeat menu/display opening, disabled-repeat setup and write-failure retry after recreation, shortcut and bounds, French/Italian project forms at 320 dp / 200% text, and repeat editor keyboard use in both themes at 320 dp / 200% text. The first selection ran 7 cases with 2 test-assertion failures; the corrected French/Italian assertions passed separately. A new responsive test first failed because its stub kept Save pending after a successful call; the corrected test passed separately. Raw logs remain in ignored `reports/repeat-instrumentation*.log` and `reports/repeat-editor-responsive*.log`. No feature failure remained in these selected runs; the full Android suite was not run.
- The earlier occasional `DIRECT START` assertion failure was not investigated or exercised by these feature tests and remains open. No physical phone, hosted CI, release build, external scan stack, or real document-provider import/export was exercised. No commit or push was made.

### Additional-counter verification, 2026-09-22

- JDK 21, offline Gradle, configuration cache disabled: `:app:testDebugUnitTest` ran **133 tests in 17 suites, 0 failures/errors/skips**, including the existing counter and backup regressions. XML reports were inspected. The two migration/persistence tests ran under Robolectric native SQLite; this is actual database migration/reopen evidence, not Android-device migration execution.
- `:app:assembleDebug` and `:app:assembleDebugAndroidTest` succeeded. Direct `:app:ktlintCheck`, `:app:detekt` and `:app:lint` completed. All four ktlint XML reports and Detekt XML have zero findings. Debug lint XML has **0 errors and 1 `GradleDependency` warning** about KSP 2.3.11 versus 2.3.12. Dependency versions were preserved. Android-test compilation also reports the existing Compose-rule API deprecation.
- Initial verification on a dedicated API 36 emulator on port 5580 ran **35 selected instrumentation tests: 34 passed and 1 failed** after the final editor changes. The failure was `DirectCounterNavigationTest.deletedProjectWhileStoppedReturnsToProjectsAfterRecreation`, which expected `ACTIVE PROJECTS` after deleting the last project. This was left open at that stage and is resolved by the runtime investigation and test correction recorded in the navigation-test follow-up below. The complete unfiltered Android suite was not run.
- Rendered rows and editors were inspected in light/dark themes with a long name at **320 dp / 200% font scale**. Manual activity checks used the real software keyboard and successfully saved in both themes; the editors apply IME padding and scroll their content. Screenshots are in ignored `reports/additional-ui/`, including `rowtool-ime-light.png` and `rowtool-ime-dark.png`. These are inspection evidence, not screenshot goldens or an all-locale rendering matrix.
- New strings exist in all eleven locale catalogs. External security/scanning wrappers, release lint/build, physical-device installation and hosted CI were not run for this feature. The pre-existing `app/build.gradle.kts` change was preserved byte-for-byte. No commit or push was made.

Local execution logs: `reports/additional-verified-checks.log` (host tests), `reports/additional-final-checks.log` (final build/direct quality tasks), and `reports/additional-instrumentation-final.log` (targeted emulator run). Reports are ignored build artifacts.

An earlier expanded instrumentation run reported six failures while an Android System UI ANR dialog owned window focus. The failed output is retained in `reports/additional-instrumentation-systemui-failure.log`, with the obstruction captured in `reports/additional-ui/rowtool-test-focus.png`. The system dialog was dismissed before repeating the same test selection; no application code or test assertions were changed to accommodate that obstruction. The repeat separately exposed the count editor's focus request outside its dialog composition. Moving the request inside the dialog restored automatic focus/selection, and all eight light/dark dialog regression cases then passed. The existing navigation expectation described above remained the only failure.

### Navigation-test follow-up, 2026-09-22

- The original test was restored byte-for-byte and reproduced on the separate API 36 AVD `RowTool_Navigation_5592` (`emulator-5592`): **1 test, 1 failure**, at the `ACTIVE PROJECTS` assertion. Before that assertion, temporary diagnostics confirmed **0 projects**, a resumed activity, and a rendered empty Projects screen with `Create your first project` and the actionable `New project` button. Both positive UI markers were absent from the preceding Counter screen. The clean screenshot and Compose tree are saved as `reports/navigation-verification/isolated-navigation-screen.png` and `isolated-navigation-state.txt`. This establishes a wrong empty-state expectation, not a navigation or timing failure. No pre-feature application build was executed or claimed as historical regression evidence.
- Only the failing test was corrected: it now asserts the empty project table, visible empty-state text, and visible `New project` click action. The stopped-activity deletion/recreation sequence, original conditional wait, Back/popped-stack check, and the other two navigation/import tests remain unchanged. Temporary diagnostics were removed. No production, schema, backup, dependency or appearance changes were made.
- Separate corrected runs passed **1/1** for the individual test and **3/3** for the navigation class, with **0 failures, errors or skips** in both. Direct `:app:ktlintAndroidTestSourceSetCheck` has **0 XML findings**, and `:app:assembleDebugAndroidTest` succeeded. The existing Compose test-rule deprecation warning remains. Host tests, lint and Detekt were not rerun in this follow-up because production code did not change; their earlier results above remain historical.
- The recovered **35-case selection passed 35/35, with 0 failures, errors or skips**, on an unchanged repeat. The first full follow-up run was **34/35**: the corrected deletion/recreation test passed, but `emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack` failed at its initial `DIRECT START` visibility assertion, before the import flow. That other test passed in the separate class run and unchanged full repeat. Its intermittent startup failure was not root-caused or hidden by a test change; it remains a verification caveat. Both full-run logs are retained as `selection-35-first.log` and `selection-35-repeat.log`. This is the original targeted selection, not the entire Android test suite.
- The original 10-class selection was recovered from `reports/additional-instrumentation-final.log` and saved in `reports/navigation-verification/selected-classes.txt`. Logs for reproduction, the individual corrected test, the complete navigation class and the successful 35-case selection are respectively `isolated-baseline-test.log`, `fixed-test.log`, `navigation-class.log` and `selection-35-repeat.log` under `reports/navigation-verification/`.
- An initial shared-emulator attempt was excluded from the isolated verification after fonecheck became active there. Emulator 5580 was left for that other task at the user's request. The separate 5592 AVD was used for the reproduction and correction runs, then stopped with its task processes; no physical-device installation or instrumentation was performed. System UI's cold-start ANR dialog was dismissed before the clean isolated diagnostic run. Final hash comparison of 209 baseline files found changes only in this document and `DirectCounterNavigationTest.kt`; all pre-existing implementation and build changes were preserved. No commit or push was made.

### Historical repository-local verification, 2026-09-14

Final local verification on 2026-09-14 used JDK 21 and the current source tree:

- `testDebugUnitTest` was explicitly rerun with configuration cache disabled: **111 tests, 0 failures, 0 errors, 0 skipped** across 14 XML suites.
- `assembleDebug`, `lintDebug`, `lintRelease`, `ktlintMainSourceSetCheck`, `ktlintTestSourceSetCheck`, `detekt`, and `assembleDebugAndroidTest` completed successfully. Gradle reused up-to-date outputs where applicable. Both lint XML reports and Detekt XML/SARIF contain zero findings.
- `tools/lc.ps1` and `tools/bc.ps1` returned CLEAN; their input checks confirmed stable inputs. The lint wrapper also checked Android-test Kotlin. Android-test compilation reports legacy Compose-rule deprecation warnings.
- `tools/dc.ps1` returned CLEAN for Gradle dependency verification, OSV (1,600 packages; zero findings without exceptions), and OWASP Dependency-Check (zero unsuppressed vulnerabilities under the current suppression configuration).
- `adb devices -l` listed no connected target. The current **58-case Android suite remains pending** on a device/emulator; successful Android-test assembly is compilation evidence only. Older connected-test outputs predate the lifecycle/navigation additions.

Two standalone PowerShell regression scripts exist: `tools/tests/android-check-routing.tests.ps1` checks explicit engine-root resolution and rejection of an invalid override; `tools/tests/sonar-token.tests.ps1` checks rejection of a whitespace-only token property. They use temporary fixtures and are separate from Gradle/JUnit and the Android CI command.

## Code-review invariants and quality tooling

- `ProjectValidation` is the repository-level authority for names and numeric bounds. Editor validation provides immediate UI feedback, but repository and import paths must continue to validate independently.
- Project persistence changes normally cross `CounterProject`, `ProjectEntity`, entity mappers, DAOs, the exported Room schema, backup DTO/codec logic, UI mapping, and focused tests. A schema change requires an explicit Room version/migration decision; destructive fallback is not configured.
- Count changes and undo must remain serialized through `CounterRepository` and atomic Room transactions. Archived or missing projects return structured results instead of being mutated, no-op boundary taps add no history, undo consumes rather than appends history, and history remains capped at 100 entries per project.
- Replacement import constructs `ValidatedBackup` only through its private-constructor factory, validates the entire payload before mutation, replaces projects and history in one Room transaction, and only then updates the last-active preference. A failed preference write does not roll back an already committed database replacement.
- Startup changes must preserve the exception boundary: recoverable preference `IOException` may fall back safely, while Room/database and programming failures must not release the splash into a false-ready empty UI.
- Navigation review must preserve `Screen`'s route/argument identity, URI encoding, the direct-counter fallback, import clearing through inclusive `graph.id`, and Counter's state-derived unavailable-project return. UI review must preserve the Route/Content seam, lifecycle-aware state collection, current callbacks around long-lived collectors, and accessibility that does not rely on color alone.
- Recreation/process-state review must keep `rememberSaveable` payloads Bundle-compatible; project dialog selections are IDs resolved from current state rather than saved `CounterProject` objects.
- Feedback/lifecycle review must keep buffered messages/navigation distinct from Counter's lossy resumed-only haptics and state-derived return. Root snackbar review must preserve FIFO order, `RESUMED` presentation, active-message duration restart, destination independence, horizontal/bottom safe area, and 72 dp Projects-FAB clearance, while recognizing its non-saveable recreation boundary.
- Window review must keep `keepScreenOn` limited to an enabled preference plus a present, non-archived project with disposal cleanup.
- Privacy/security review must re-check the manifest, dependencies, backup rules, SAF-only file access, and obscured-touch filtering whenever permissions, SDKs, storage, networking, analytics, billing, backup, or activity touch handling changes.
- `ktlintCheck` is blocking. Detekt uses the repository config and Compose rules but has `ignoreFailures = true`, so its exit code alone is not proof of a clean report. Android lint aborts on errors, checks release builds when requested, and includes the Google Android security lint ruleset. `app/lint.xml` is currently an empty `<lint>` document with no project-specific issue suppression.
- Sonar is configured by `sonar-project.properties`; the `sonar` task depends on the debug build and JaCoCo XML generation. Coverage excludes `MainActivity`, `RowToolApplication`, and all `ui/**`, so the coverage percentage is not evidence of UI coverage.
- The short check entry points in `tools/` are thin project wrappers around the shared Android-check installation with project ID `rowtool`; `tools/sonar.ps1` is the local Sonar wrapper. `tools/Resolve-RowToolAndroidCheck.ps1` resolves an explicit `ANDROID_CHECK_ROOT` first, then the established `C:\Dev\Android-check` or sibling checkout. A plan-only wrapper run proves routing/configuration, not that the underlying scan executed or was clean; use the produced report artifact for result claims.

## Build tooling and local check details

### Gradle setup and dependency boundary

- `settings.gradle.kts` includes only `:app`; plugin repositories are Google, Maven Central, and Gradle Plugin Portal. Dependency repositories are Google/Maven Central with `FAIL_ON_PROJECT_REPOS`.
- AGP supplies the built-in Kotlin compilation path; there is no separate `org.jetbrains.kotlin.android` plugin alias applied. Compose and serialization plugins are applied explicitly. Java/JVM target 17 is distinct from the JDK running Gradle.
- `gradle.properties` configures `-Xmx3g`, UTF-8, configuration cache enabled, build cache disabled, AndroidX enabled, nontransitive R classes, and official Kotlin style. CI disables configuration cache on the command line. There is no assertion here that the CodeQL JDK 17 configuration passed against the current toolchain.
- Wrapper distribution is `gradle-9.7.1-bin.zip` with a configured SHA-256 checksum, URL validation, 10-second network timeout, zero retries, and 500 ms retry backoff setting.
- Dependency locking applies to all configurations. Root `settings-gradle.lockfile` and `app/gradle.lockfile` coexist with `gradle/verification-metadata.xml` and `gradle/verification-keyring.keys`. Verification enables metadata/signature checks with explicit trusted/ignored key configuration; do not equate this with every artifact having a verified publisher signature. The settings lock contains only an empty catalog configuration. The app lock records Compose UI 1.12.1, Material 3 1.4.0, Robolectric 4.17 and application Kotlin stdlib 2.4.20, while some tooling configurations retain older stdlib lines including 2.4.10. These are inspected lock entries, not a fresh dependency resolution.
- Root buildscript resolution overrides jose4j to 0.9.6, Bouncy Castle `*-jdk18on` to 1.84, and jdom2 to 2.0.6.1. All-project configuration rules override Logback to 1.5.34, requested Netty 4.1.x to 4.1.137.Final, commons-lang3 to 3.20.0, HttpClient to 4.5.14, and Bouncy Castle `*-jdk18on` to 1.84. These are build resolution rules, not proof those libraries ship in the app.
- Compose is enabled and BuildConfig generation disabled. Settings reads installed package version through PackageManager. Packaged resources exclude `/META-INF/{AL2.0,LGPL2.1}`. App-specific ProGuard rules are currently empty apart from a comment; release uses the optimized default rules.
- `ui-tooling-preview` is an `implementation` dependency; interactive tooling and test manifest are `debugImplementation`. JUnit/Robolectric/coroutines-test are host-test dependencies; AndroidX test/Compose test/Room testing belong to androidTest. Room compiler is KSP. Security lint and Compose rules are check dependencies, not ordinary runtime implementation declarations.

Additional declared tool/test versions:

| Component | Version |
|---|---|
| JUnit | 4.13.2 |
| Robolectric | 4.17 |
| AndroidX Test Core / Runner | 1.7.0 / 1.7.0 |
| AndroidX Test JUnit extension / Espresso | 1.3.0 / 3.7.0 |
| ktlint Gradle plugin / engine | 14.2.0 / 1.8.0 |
| Detekt | 2.0.0-alpha.6 (declared prerelease) |
| Compose rules | 0.6.6 |
| Google Android security lints | 1.0.4 |
| OWASP Dependency-Check | 13.0.0 |
| Compose Stability Analyzer | 0.13.0 |
| Sonar Gradle plugin | 7.5.0.8588 |
| Explicit JaCoCo Ant instrumenter | 0.8.14 |

### Coverage and static-check behavior

Debug enables unit-test coverage. Custom `instrumentDataForCoverage` depends on `compileDebugKotlin`, reads `intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes`, and offline-instruments only `com/finnvek/rowtool/data/**/*.class` into `build/jacoco/data-classes` using JaCoCo Ant. `testDebugUnitTest` depends on this task and prepends the instrumented directory to its execution classpath. Every Gradle `Test` task adds `--add-opens=java.base/jdk.internal.access=ALL-UNNAMED`. The instrumenter registers compiled-class inputs and the instrumented-directory output and deletes/recreates that output when executing. It includes all data-package classes, with no further generated-class exclusion inside that fileset. The Ant configuration is `jacocoOfflineAnt`; AGP coverage and the explicit Ant artifacts are locked to 0.8.14. This is selective extra offline instrumentation for data classes, not a statement that only those packages appear in all coverage reports.

Both task paths explicitly opt out of configuration-cache compatibility for their execution-time operations. This AGP output-path dependency matters when changing toolchains.

Sonar reads root properties, overrides the app JaCoCo XML path to `app/build/reports/coverage/test/debug/report.xml`, and excludes `MainActivity.kt`, `RowToolApplication.kt`, and `ui/**` from coverage. The root `sonar` task depends on debug assembly and `createDebugUnitTestCoverageReport`. Root properties exclude generated/build paths, use native Git blame, and exclude WebP from secret-file scanning. Analysis targets SonarCloud project `Insaner1980_row-tool`, organization `insaner1980`; a successful local upload is not by itself evidence of the remote quality-gate outcome.

ktlint emits plain and Checkstyle reports and excludes generated/build paths. Detekt emits Checkstyle and SARIF, not HTML/Markdown; uses default config plus `config/detekt/detekt.yml`; exempts Composable functions from naming, cyclomatic complexity, long-method, and magic-number rules; sets maximum line length 140. `.editorconfig` also exempts Composable function names from ktlint naming rules. Detekt's nonblocking exit behavior still requires report inspection.

Dependency-Check scans debug/release runtime classpaths, skips test groups, disables OSS Index, writes HTML/JSON/SARIF to root `reports`, and rejects unused suppression rules. Defaults are auto-update enabled and failure CVSS threshold 7. Environment configuration names are `DEPENDENCY_CHECK_DATA_DIRECTORY`, `DEPENDENCY_CHECK_AUTO_UPDATE`, `DEPENDENCY_CHECK_FAIL_BUILD_ON_CVSS`, `NVD_API_KEY`, `NVD_API_DELAY_MS`, and `NVD_API_MAX_RETRY_COUNT`. Default data directory is `.gradle/dependency-check-data`. Secret values must remain outside documentation and version control.

### Other scanner and reporting boundaries

| System | Local authority and limits |
|---|---|
| Android lint / Google security lint | Blocking abortOnError and checkReleaseBuilds; debug/full tasks in config. Standard configured lint outputs under app/build/reports, including lint-results-debug.xml/html and release equivalents; not inspected as fresh results. |
| ktlint / Compose rules | Blocking plugin/engine and Compose ruleset; reports under app/build/reports/ktlint. Generated/build files excluded. |
| Detekt / Compose rules | Nonblocking ignoreFailures; Checkstyle/SARIF under app/build/reports/detekt. Exit zero is insufficient to call findings clean. |
| Compose stability | Applied plugin, configured stabilityCheck, committed app/stability snapshots. Snapshot descriptions of stable/runtime parameters are not device performance measurements; report generation belongs to the plugin. |
| Sonar | Root properties/build configuration and separate tools/sonar.ps1. Coverage report and upload log paths described above; remote quality gate not checked here. |
| Dependency-Check | Runtime classpath scope, CVSS/unused-suppression failure policy, root reports output described above. |
| OSV / MobSF / secret scan / PMD / local CodeQL | Thin wrapper routes and project configuration establish invocation names. Engine versions, exact scan coverage, blocking policy and per-tool output filenames cannot be established from these wrappers alone. OSV and MobSF local exception sources are inventoried below. |
| Semgrep | 13 local rules in config/semgrep/rowtool-security.yml; path is passed through project configuration. No local CLI version or full invocation is pinned in RowTool. |
| DeepSec | Separate .deepsec/package.json, pnpm-lock.yaml and deepsec.config.ts workspace, project rowtool, root parent directory. Declares deepsec 2.3.9 and pnpm 9.15.4; overrides fast-uri 3.1.7, qs 6.16.0 and undici 8.5.0 to 8.10.0. It is tooling, not an app module. |

DeepSec scripts provide scan, process, revalidate (minimum HIGH), and Markdown-directory export to `.deepsec/findings/`; process/revalidate configure Codex with gpt-5.6-luna. `deepsec:report` chains scan → process → export with shell success gating; `deepsec:report:custom` aliases it. Configuration prioritizes backup validation/rollback, local integrity, manifest/XML, file/URI handling and sensitive logging. Its ignore file excludes node_modules, local env files, generated data/runs/reports/revalidation and findings. No finding status, successful external execution or agent upload/privacy behavior can be inferred from these declarations.

### Project wrapper map

The wrappers resolve the shared engine and pass explicit repository root and project ID `rowtool`. Arguments are forwarded via `@args`; wrappers pass through `$LASTEXITCODE`; `sc` and `tc` additionally force `-Full`. These thin wrappers do not implement PlanOnly themselves, so its shared-engine behavior cannot be proven from RowTool source. Do not treat a printed plan as scan execution. This table records local routing, not the external engine's complete implementation or current installation status.

| Script under `tools/` | Shared command |
|---|---|
| `ac.ps1` | `android-check` |
| `bc.ps1` | `build-check` |
| `tc.ps1` | `test-check` with `-Full` |
| `lc.ps1` | `lint-check` |
| `cr.ps1` | `compose-rules` |
| `cs.ps1` | `compose-stability` |
| `dc.ps1` | `dependency-check` |
| `db.ps1` | `dependabot-check` |
| `ds.ps1` | `deep-sec` |
| `ga.ps1` | `google-android-security` |
| `ms.ps1` | `mobsf-scan` |
| `os.ps1` | `osv-scan` |
| `pc.ps1` | `pmd-check` |
| `ql.ps1` | `codeql-check` |
| `sc.ps1` | `security-check` with `-Full` |
| `ss.ps1` | `secret-scan` |

`config/android-check.json` declares main/debug/test/androidTest for required app module, debug/release variants, no included builds, `dependabotEnabled = false`, debug build/host-test/device-test tasks, debug/release full lint, stability check, runtime dependency configurations, and the custom Semgrep path. Committed stability snapshots are `app/stability/app-debug.stability` and `app/stability/app-release.stability`; a snapshot is not a current rerun result.

`Resolve-RowToolAndroidCheck.ps1` uses explicit `ANDROID_CHECK_ROOT` first and throws `ANDROID_CHECK_ROOT_INVALID` if it lacks the requested file; it does not silently fall back from a bad explicit override. Without override it tries `C:\Dev\Android-check`, then a sibling `Android-check`, and throws `ANDROID_CHECK_ENGINE_NOT_FOUND` if neither works. Ordinary wrappers request `tools/InvokeProjectCheck.ps1`.

`sonar.ps1` instead imports local `SonarToken.psm1` and the shared `tools/CheckRuntime.psm1`, then runs managed `gradlew.bat sonar --console=plain --no-configuration-cache`. Timeout defaults to 3,600 seconds and is configurable from 1 to 86,400. Log destination is `reports/sonar.txt`; missing token, timeout, or nonzero Gradle exit produces wrapper exit 2. It accepts a nonblank process/user `SONAR_TOKEN` or a nonblank `systemProp.sonar.token` assignment in user/project Gradle properties. Its `-PlanOnly` branch describes the upload without running it. This documentation task did not read user credential values or invoke an upload.

### Local security rules and exception ledger

`config/semgrep/rowtool-security.yml` checks enabled Android backup/cleartext, nonlauncher exported components, broad FileProvider paths, literal signing credentials/secrets, weak crypto patterns, unsafe WebView settings, and potentially sensitive Android logging. Rule existence does not imply the corresponding feature exists in RowTool or that a scan ran.

The following are **configured exceptions and their recorded expiry dates**, not fresh endorsements of their external advisory rationale:

| File | Entry / scope | Expiry |
|---|---|---|
| `config/check-exceptions.json` | `rowtool-mobsf-viewmodel-key`, mobsfscan `android_kotlin_hardcoded`; counter ViewModel key in RowToolNavHost; recorded reason: identifier, not credential | 2026-11-10 |
| `config/check-exceptions.json` | `rowtool-mobsf-target-sdk`, mobsfscan `android_task_hijacking2`; manifest finding with build selector `targetSdk = 37`; recorded reason: source scanner does not resolve Gradle target | 2026-11-10 |
| `config/dependency-check/suppressions.xml` | Two entries: pkg:maven/androidx.sqlite/sqlite-android@2.6.2 and sqlite-framework-android@2.6.2 → cpe:/a:sqlite:sqlite:2.6.2; recorded rationale distinguishes AndroidX version from SQLite engine version | 2026-11-09Z |
| `config/dependency-check/suppressions.xml` | pkg:maven/com.github.skydoves/compose-stability-runtime-android@0.13.0 → cpe:/a:github:github:0.13.0; recorded rationale: unrelated GitHub server product | 2026-11-14Z |

`app/lint.xml` contains no issue entries. README's AGP 9.3.2 line remains older than the catalog's 9.4.0; this reference follows the catalog. Neither file was changed in this documentation-only task.

No dated exception is expired as of 2026-09-14. `gradle/osv-scanner.toml` has no active vulnerability exceptions, and the former Kotlin stdlib 2.4.10 Dependency-Check suppression is absent. The catalog and application runtime lock use Kotlin 2.4.20; older Kotlin entries remain in tooling configurations.

Other source-level suppressions/opt-ins, without expiry:

- Production manifest: MissingClass only on the ProfileInstallReceiver removal directive.
- Base regular/round adaptive icon XML: MonochromeLauncherIcon, with monochrome supplied by API 33 resources.
- CounterRoute: InlinedApi on SDK-guarded haptic constant selection.
- ProjectsScreen: DEPRECATION on legacy isContainer semantics.
- Counter/Projects/Settings screens: ExperimentalMaterial3Api for app bars; editor: ExperimentalLayoutApi for FlowRow; Type: ExperimentalTextApi for font setup.
- BackupModels: CPD-OFF/ON around intentionally mirrored serialization fields. CounterViewModelTest and SettingsViewModelTest mark shared fixture structure; CounterScreenContentTest marks explicit no-op callbacks. Whether a particular external duplication scanner honors those comments is not established here.
- Detekt and ktlint Composable-specific rule exclusions and verification trust configuration are described above. No TODO/FIXME markers were found in current production Kotlin; this absence is not evidence that every review concern has been resolved.

### CI and repository-generated files

Android CI in `.github/workflows/android.yml` runs on every push/pull request without branch/path filters, Ubuntu latest, contents-read permission and Temurin 21. Exact invocation: `./gradlew --no-daemon --no-configuration-cache :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:assembleDebugAndroidTest`. CodeQL runs on pushes/PRs targeting `main` and Mondays at 04:23 UTC, with Java/Kotlin manual debug build, Temurin 17, contents-read and security-events-write permissions. CodeQL's exact build is `./gradlew --no-daemon --no-configuration-cache :app:assembleDebug`; initialization selects java-kotlin/manual. Both disable checkout credential persistence and pin checkout, setup-java, setup-gradle and (CodeQL only) init/analyze actions to commit SHAs. Neither workflow runs connected device tests or publishes a signed release.

`.gitignore` excludes `.gradle`, IDE metadata, build directories, `local.properties`, captures/native build state, `reports`, and `.scannerwork`. A local ignored report is not committed evidence. Release signing configuration is absent; do not infer that generic ignore patterns protect every possible signing-key filename.

## Source-of-truth locations

| Concern | Source |
|---|---|
| Versions and dependencies | `gradle/libs.versions.toml` |
| Android identity, SDK, release, lint, Room export | `app/build.gradle.kts` |
| Permissions and Android components | `app/src/main/AndroidManifest.xml` |
| App construction and persistence wiring | `RowToolApplication.kt` |
| Navigation | `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt` |
| Room entities/schema | `data/local/`, `app/schemas/com.finnvek.rowtool.data.local.RowToolDatabase/1.json` |
| Counter transactions and undo | `data/repository/CounterRepository.kt` |
| Project semantic bounds and normalization | `domain/model/CounterModels.kt`, `domain/model/ProjectValidation.kt`, `ui/screens/projects/ProjectEditorInputValidation.kt` |
| Backup schema, validation, and replacement | `data/repository/BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt` |
| Persistent settings and startup selection | `data/preferences/`, `ui/RowToolAppViewModel.kt` |
| Projects UI | `ui/screens/projects/` |
| Counter UI | `ui/screens/counter/` |
| Settings and SAF flows | `ui/screens/settings/` |
| Theme and font fallback | `ui/theme/`, `app/src/main/res/font/outfit.ttf`, `app/src/main/res/values/themes.xml`, `app/src/main/res/values-night/themes.xml` |
| UI state/effect seams and platform ownership | `ui/RowToolApp.kt`, `ui/screens/*/*Route.kt`, `ui/screens/*/*ViewModel.kt`, `ui/screens/*/*Screen.kt` |
| Shared UI dimensions and components | `ui/theme/RowToolDimens.kt`, `ui/RowToolDialogActionColors.kt`, `ui/RowToolSectionHeadingText.kt`, `ui/RowToolConfirmationDialog.kt`, `ui/RowToolDropdownMenuItem.kt`, `ui/screens/counter/CounterImageButton.kt` |
| Localized text and locale declaration | `app/src/main/res/values*/`, `app/src/main/res/xml/locales_config.xml` |
| Tests | `app/src/test/`, `app/src/androidTest/` |
| CI | `.github/workflows/android.yml`, `.github/workflows/codeql.yml` |
| Static analysis, security, and Sonar | `app/build.gradle.kts`, `build.gradle.kts`, `config/`, `sonar-project.properties`, `tools/` |
| Play handoff | `docs/PLAY_STORE_LISTING.md`, `docs/DATA_SAFETY.md`, `docs/PRIVACY_POLICY.md`, `docs/privacy-policy.html`, `docs/RELEASE_CHECKLIST.md` |
| Quality-engine routing / scanner exceptions | `tools/Resolve-RowToolAndroidCheck.ps1`, `config/android-check.json`, `config/check-exceptions.json`, `config/dependency-check/suppressions.xml`, `gradle/osv-scanner.toml` |
| Offline coverage / report upload | `app/build.gradle.kts`, `build.gradle.kts`, `sonar-project.properties`, `tools/sonar.ps1` |
| Debug test-host theme / diagnostic helpers | `app/src/debug/AndroidManifest.xml`, `app/src/androidTest/java/com/finnvek/rowtool/ui/TextLayoutAssertions.kt`, `app/src/androidTest/java/com/finnvek/rowtool/ui/LayoutDiagnostics.kt` |
| Separate DeepSec workspace | `.deepsec/package.json`, `.deepsec/pnpm-lock.yaml`, `.deepsec/deepsec.config.ts` |

All Kotlin paths in this table are below `app/src/main/java/com/finnvek/rowtool/` unless an explicit root is shown.

## Change-impact and review map

Use each row as a focused starting point and verify the current implementation before treating a concern as a defect. Tests named above are evidence sources to inspect and, when needed, run; their presence alone is not proof of all listed boundaries.

| Proposed area | Source path to trace | Questions grounded in the current behavior |
|---|---|---|
| Count operation or undo | `CounterMutation` → `CounterRepository` transaction → project/history DAO flows → `CounterViewModel` → Counter content | Does every accepted change persist once? Do no-ops avoid timestamp/history changes? Are archived/missing results, cap, concurrent updates, and undo history consumption preserved? |
| Project editing | `ProjectEditorDialog` → `ProjectEditorInputValidation` → `ProjectValidation` → `ProjectEditorValues` → repository validation | Do UI fields reuse semantic authorities without losing parsing/enablement-specific validity? Are Unicode names, disabled optional values, pasted invalid input, unchanged edits, archived metadata, and start-value semantics preserved? |
| New persisted field | Domain/entity/mappers → Room schema/DAO → backup DTO/codec/export/import → presentation/tests | What default/migration is required? Is backup compatibility explicit? Are import and creation independently validated? |
| Import | SAF route → request sequence → codec → `ValidatedBackup.create` → preview → confirm atomic → trusted replacement transaction → preference selection → graph-anchor navigation | Can old reads replace a newer preview? Is validation construction unbypassable? Does failure preserve old DB rows? Is the whole replacement committed before preference handling? Are empty/archived-only payloads, equal timestamp ordering, and dynamic-start back-stack clearing handled? |
| Startup and recreation | Application/DataStore → app ViewModel → splash → `Screen`/NavHost → route saved IDs/state | Are fatal DB failures distinguished from recoverable preferences? Does direct-counter Back clear stale entries? Does missing/archive state retrigger mandatory return after recreation? Are saved values Bundle-compatible? Which state survives activity recreation versus process death? |
| Archive/delete | Projects/Counter actions → repository → conditional preference clear → observed Counter state plus one-shot feedback | Does clearing avoid overwriting a newer selection? Is mandatory return retried until navigation removes the route while optional feedback stays one-shot? Does delete cascade only the selected project's history? |
| UI hierarchy or sizing | `*Screen.kt`, `CounterImageButton`, `ProjectEditorDialog`, theme files | Do count and controls fit width/font scale? Are touch targets independent of artwork? Do dialogs fit with IME? Are project IDs retained during list reorder? |
| Accessibility/localization | Semantic modifiers, `values*/strings.xml`, number/date parsing, theme roles | Are actions uniquely labeled? Is state communicated beyond color? Are plurals/placeholders correct? Are locale display formatting and numeric input parsing intentionally different? |
| Snackbar/haptics | `RowToolApp.rememberSnackbarPresenter`, `CounterViewModel.haptics`, `CounterRoute` lifecycle collectors | Are durable-enough messages queued FIFO until resumed? Does stop restart visible duration? Are stale haptics dropped and fresh resumed haptics classified correctly? Is the activity-recreation limitation acceptable? |
| Theme/window behavior | XML themes → `MainActivity` → `RowToolTheme` and Counter disposable effect | Do system-bar icons follow the selected mode? Does keep-screen-on clear on route exit or archive? Does splash differ from selected app theme before preferences load? |
| Security/privacy | Manifest merge directives, backup exclusions, SAF streams, dependency scopes, activity touch filtering | Do source and final artifact still match? Are exported JSON and provider access described truthfully? Are logging/permissions/SDK changes evaluated on actual call paths? |
| Toolchain/check changes | Catalog, root/app Gradle, locks, verification files, coverage output paths, workflows, wrappers | Do declared versions resolve compatibly? Does offline instrumentation still target real class paths? Are nonblocking reports and exceptions included in result interpretation? |

Documentation precedence: production/configuration source first, executable test assertions for tested contracts second, this reference as a navigational summary, then README/release/history material for context. Product/store intent is not evidence of Play publication, actual paid price, a deployed privacy URL, or real-device acceptance.

## External release steps

The following are intentionally not completed by source code:

1. Check application-name and trademark availability and configure the Finnvek Play Console application.
2. Create or select a secure upload key, store it outside the repository, register it with Play App Signing, and provide release-signing configuration without committing key material or passwords.
3. Configure the Play app as a paid download around EUR 1.99 and review regional equivalents. No Play Billing integration is needed.
4. Produce and inspect the signed release AAB and merged manifest, verify the final dependency graph and permissions, and test the release on a real device.
5. Review every translation and store asset, capture current screenshots, and host `docs/privacy-policy.html` at a stable public URL.
6. Complete the Play listing, Data safety form, privacy-policy URL, content rating, target audience, category, and release notes.
7. Upload the inspected AAB and perform the selected test/production rollout. Increment `versionCode` for every later upload.

The operational checklist is `docs/RELEASE_CHECKLIST.md`. Its unchecked items are handoff guidance, not proof of which external actions have or have not occurred.

## Documentation conflicts and inspection limits

| Other source | Conflict or qualification found during this inspection |
|---|---|
| README.md technology | Lists AGP 9.3.2 / Kotlin 2.4.10; catalog declares 9.4.0 / 2.4.20. |
| README.md typography | Says Outfit is absent and SansSerif is the only implementation; live resource and Type.kt now wire bundled Outfit plus fallback. |
| README.md backup wording | Calls undo history transient even though Room persists it; v2 now includes it in manual export. Says import reads at most 5 MiB, while the decoder probes one extra byte to establish oversize. |
| Privacy/Data safety and Play support files | Describe app-owned offline behavior and intended declarations, not an inspected merged release, published listing or configured price. SAF provider may itself use cloud storage. No implementation contradiction was found in the local-only app call paths. |

Only PROJECT.md was edited by the earlier documentation-inspection task. The document incorporates and retains useful pre-existing documentation while replacing stale implementation descriptions. Source inventories, XML/resource structure, declared colors/dimensions, test annotations, asset hashes, paths and internal links were checked; these are static documentation checks. Full final-document and diff review, plus git diff --check, are the completion checks for this task.

The historical September 14 verification does not establish connected-device behavior or rendered UI acceptance; the September 22 emulator and rendering checks are limited to the feature scenarios recorded above. Neither run establishes hosted CI, a signed release artifact, final merged-release manifest, translation quality, latest available releases, Play Console state, or hosted privacy-policy availability.
