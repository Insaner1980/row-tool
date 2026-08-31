# 17. Haptic feedback, Settings UI, launchers, operation state, and app information

## Prompt 241: Haptic master toggle respected for every counter effect

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Haptic master toggle respected for every counter effect

Verify that disabling haptic feedback suppresses light, repeat-boundary, and target feedback immediately while leaving count mutations unchanged.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace preference Flow to effect decision point.
2. Toggle during an active counter.
3. Check all mutation types and no-op results.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. One effect path ignores the toggle.
2. Disabling haptics disables clicks or repository calls too.
3. A stale collector uses the previous value.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel effect tests before and after runtime toggle.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 242. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 242: Light haptic only for accepted ordinary increment and decrement

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Light haptic only for accepted ordinary increment and decrement

Verify that ordinary accepted plus or minus actions produce the intended light feedback once, subject to the master toggle.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect result mapping and effect channel.
2. Test accepted changes away from repeat or target boundaries.
3. Check exactly-once collection.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Feedback fires before repository acceptance.
2. One tap produces two light haptics.
3. Manual set or reset incorrectly uses the ordinary tap effect if not intended.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run accepted-action effect tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 243. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 243: No haptic for rejected, missing, archived, or boundary no-op actions

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: No haptic for rejected, missing, archived, or boundary no-op actions

Verify that repository results representing no mutation never generate success haptics.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test decrement at zero, increment at maximum, same-value set, same-value reset, no-history undo, archived, and missing.
2. Trace messages separately.
3. Check stale UI callbacks.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Boundary no-op still vibrates and implies success.
2. Missing project produces strong target feedback from stale state.
3. Disabled control semantics suppress click visually but callback emits haptic directly.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run result-to-effect matrix tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 244. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 244: Stronger haptic at repeat boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Stronger haptic at repeat boundary

Verify that an accepted count change landing on the actual repeat boundary emits the stronger feedback once when repeatLength is present and valid.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect boundary calculation using committed new count.
2. Test just before, exactly at, and after boundaries.
3. Check decrement behavior according to current intended contract.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Feedback uses previous count and fires one tap early.
2. Repeat position 0 versus one-based display causes off-by-one.
3. Invalid or null repeat triggers strong feedback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run table-driven boundary effect tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 245. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 245: Target-reached haptic condition

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Target-reached haptic condition

Verify that stronger target feedback occurs at the intended event, such as reaching the target from below, and does not repeat on every count above target unless current behavior explicitly requires that.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect comparison of previous and new count.
2. Test exact reach, starting above, decrement across, manual set, reset, and undo according to implemented product rules.
3. Do not invent event semantics absent from code and tests.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Every increment above target vibrates strongly.
2. Direct set to target fails to provide intended feedback.
3. A target below current count triggers feedback on unrelated edit.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run explicit transition matrix tests based on verified current semantics.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 246. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 246: Repeat and target boundary precedence and duplicate suppression

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Repeat and target boundary precedence and duplicate suppression

Verify that when one accepted change is both a repeat boundary and target event, the app applies one deliberate feedback result rather than two overlapping effects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect condition ordering and effect type.
2. Test coincident and separate boundaries.
3. Check buffered Channel output count.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two strong haptics fire for one tap.
2. Light plus strong both fire.
3. Target condition suppresses repeat feedback on all later unrelated boundaries.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run coincident-boundary effect tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 247. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 247: Haptic API compatibility and graceful hardware absence

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Haptic API compatibility and graceful hardware absence

Verify that the selected Compose or Android haptic APIs are available on minSdk 29 and calls fail gracefully on devices that cannot produce the requested effect.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect API levels, compatibility helpers, and version guards.
2. Check no vibrator permission is introduced unnecessarily.
3. Distinguish API invocation from physical output quality.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A newer constant is called without guard.
2. Missing hardware throws or blocks count mutation.
3. Manifest gains vibration permission when the used API does not require it.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run lint and available device smoke tests; report hardware behavior limits honestly.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 248. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 248: Theme option control state and persistence

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Theme option control state and persistence

Verify that Settings exposes SYSTEM, LIGHT, and DARK as mutually exclusive localized choices whose selected state follows persisted DataStore state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect control semantics, callbacks, and state updates.
2. Test each selection, same-value tap, recreation, and system theme change under SYSTEM.
3. Check app-level theme application.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two theme options are selected.
2. Displayed selection changes before failed write and never recovers.
3. SYSTEM is omitted or mapped to LIGHT.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run semantics, DataStore, and Activity theme tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 249. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 249: Haptic and keep-awake toggle UI consistency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Haptic and keep-awake toggle UI consistency

Verify that both toggles display the current persisted value, expose correct labels and roles, and handle write failure without misleading permanent state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect switch row click and switch click for duplicate callbacks.
2. Test rapid toggles and lifecycle stop.
3. Check disabled or loading policy.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Tapping the row and switch dispatches twice and ends unchanged.
2. Labels are associated with the wrong toggle.
3. Failure leaves UI opposite to persisted value.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run toggle interaction and failure tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 250. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 250: Keep-awake setting explanation and active-screen semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Keep-awake setting explanation and active-screen semantics

Verify that Settings text accurately describes that keep-awake applies while an active counter is open and does not imply a global device setting.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare strings across locales with actual Activity behavior.
2. Check accessibility reading order.
3. Do not rewrite wording solely for style.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Text claims the display is always kept awake.
2. Translation changes the meaning to a system-wide setting.
3. UI says disabled while window flag remains active.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use resource tests and behavior tests after any necessary correction.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 251. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 251: Export and import launcher ownership in Settings Route

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Export and import launcher ownership in Settings Route

Verify that Android document launchers are created and invoked by the Route, not Content or ViewModel, and use the latest callbacks and operation state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect rememberLauncherForActivityResult keys and callbacks.
2. Recompose and recreate before result delivery.
3. Check Content receives plain actions.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A stale launcher callback sends the URI to a cleared ViewModel.
2. Launcher is recreated repeatedly and result is lost.
3. ViewModel holds ActivityResultLauncher or Context.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Route launcher tests or instrumentation.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 252. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 252: Import preview and destructive confirmation UI clarity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Import preview and destructive confirmation UI clarity

Verify that the preview displays validated active and archived counts, clearly states replacement semantics, and requires an explicit confirm action distinct from cancel.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect localized strings, button ordering, dismiss behavior, and semantics.
2. Test zero and large counts.
3. Check stale preview identity.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Dialog implies merge while implementation replaces.
2. Confirm and cancel labels are ambiguous or swapped.
3. Counts are not announced to accessibility services.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose semantics and full import confirmation tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 253. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 253: Backup operation progress, button enablement, and retry

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Backup operation progress, button enablement, and retry

Verify that export parsing, import parsing, and replacement operations expose enough in-flight state to prevent unsafe duplicate actions without trapping the screen after success, cancellation, or failure.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect operation state machine or flags.
2. Trigger repeated taps and navigate away.
3. Retry after each terminal result.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Both import and export run concurrently and effects cross.
2. Button remains disabled after picker cancellation.
3. Progress indicator shows while no operation is active due to stale state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run delayed-fake operation and retry tests.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 254. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 254: App version and Finnvek information accuracy

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: App version and Finnvek information accuracy

Verify that Settings displays the actual app version and intended Finnvek identity without stale hard-coded values or localization of machine identifiers.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect PackageInfo or BuildConfig use and fallback.
2. Compare build configuration and tests.
3. Check all locale resources for publisher spelling.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Displayed version differs from installed build.
2. Package lookup uses a deprecated or wrong package name and returns blank.
3. Publisher is mistranslated or misspelled.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run version-info tests and compile resources.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 255. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 255: Privacy and paid-download business-model summaries

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Privacy and paid-download business-model summaries

Verify that Settings accurately states local-data behavior, no ads or subscriptions, and manual backup scope without claiming capabilities absent from the current repository.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect haptic effect generation and consumption, Settings screen and ViewModel, theme controls, toggles, SAF launchers, import preview UI, app information, string resources, and related tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare UI strings with manifest, dependency graph, backup implementation, and Play-facing documents.
2. Check all locales structurally.
3. Distinguish paid Play download from Play Billing.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. UI claims automatic cloud backup despite allowBackup false.
2. Text implies free trial or in-app purchase.
3. Privacy summary omits a newly introduced capability that the repository actually contains.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use manifest and dependency evidence plus resource tests. Do not add marketing claims speculatively.
2. Use focused ViewModel and Compose tests. Device haptic waveform or vendor feel cannot be proven from repository alone, so verify API calls, conditions, and version compatibility without overstating hardware behavior.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 256. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 18. Responsive Compose layout, accessibility semantics, assets, and localization integrity

## Prompt 256: Screen content width cap and phone-side padding

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Screen content width cap and phone-side padding

Verify that Projects, Counter, and Settings content obey the intended 600 dp maximum width and 20 dp phone-side padding without inconsistent nested padding.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect shared dimension usage and each screen hierarchy.
2. Test narrow phone, exact cap, and wide tablet widths.
3. Check alignment of app bars, lists, and counter controls.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. One screen expands edge to edge on tablet while others stay bounded.
2. Padding is applied twice and leaves too little phone space.
3. A hard-coded width clips smaller devices.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run parameterized Compose layout tests and visual checks only where needed.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 257. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 257: Project editor maximum width, height, scrolling, and IME

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project editor maximum width, height, scrolling, and IME

Verify that `ProjectEditorDialog` respects the 560 dp width cap and 88 percent available height, keeps all fields and actions reachable, and handles the software keyboard.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test small height, landscape, large font, many validation errors, and IME visible.
2. Inspect vertical scrolling and button placement.
3. Check focus and dismissal.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Confirm button is pushed off-screen.
2. Dialog exceeds available height and clips fields.
3. Nested scrolling traps focus or prevents keyboard dismissal.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose tests with constrained dimensions and font scale.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 258. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 258: Responsive counter count scaling by digits and width

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Responsive counter count scaling by digits and width

Verify that the localized count text scales for one through six digits based on actual available width without clipping, overlap, or unusably small text.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect measurement logic, constraints, and font-size bounds.
2. Test counts 0, 9, 10, 999, 1000, and 999999 on narrow and wide screens.
3. Include locale formatting width.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Six-digit count clips or overlaps controls.
2. Single-digit count becomes excessively large and breaks layout.
3. Measurement causes an infinite recomposition loop.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run size assertions or screenshot tests across representative widths.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 259. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 259: Extreme font-scale behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Extreme font-scale behavior

Verify that text, controls, dialogs, lists, and count remain readable and operable at large system font scales without fixed-height clipping or lost actions.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test at least representative enlarged scales supported by Compose tests.
2. Inspect text maxLines, overflow, fixed heights, and scrolling.
3. Check accessibility labels independent from visible truncation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Project names overlap menus.
2. Settings rows clip descriptions.
3. Count scaling ignores fontScale and becomes unreadable or oversized.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run large-font Compose tests for all screens.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 260. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 260: Landscape and short-height layout behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Landscape and short-height layout behavior

Verify that essential counter controls, project actions, and Settings backup controls remain reachable in landscape or short windows.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test phone landscape and split-screen-like heights.
2. Inspect scrolling and weight usage.
3. Check system and IME insets.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Plus or undo falls below the viewport with no scroll.
2. A weighted spacer consumes all short-height space.
3. Dialogs cannot be confirmed.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run constrained-height Compose tests.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 261. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 261: Tablet and large-window behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Tablet and large-window behavior

Verify that width bounding, centering, touch targets, and information density remain intentional on tablets without stretching controls or leaving content misaligned.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test widths beyond 600 dp and different window aspect ratios.
2. Inspect counter control grouping and list width.
3. Check dialogs remain centered and capped.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Counter buttons stretch to huge distorted sizes.
2. Content anchors to the left despite intended centering.
3. A width cap is applied to background or scaffold incorrectly.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run wide-window Compose tests.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 262. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 262: System bar, scaffold, navigation, and IME inset composition

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: System bar, scaffold, navigation, and IME inset composition

Verify that system bars, Scaffold padding, app bars, bottom navigation if present, dialogs, and IME insets are consumed once in each screen.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace padding values through composable parameters.
2. Test gesture and three-button navigation if device tests are available.
3. Open numeric keyboard on counter and editor.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Bottom actions are obscured.
2. Content has duplicate top inset.
3. IME pan or resize leaves dialog buttons inaccessible.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run layout tests and targeted device checks.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 263. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 263: Minimum 48 dp interactive touch targets

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Minimum 48 dp interactive touch targets

Verify that plus, minus, undo, count set action, menu items, toggles, expansion controls, icon buttons, and dialog actions meet at least 48 dp interactive size even when visual assets are smaller.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect modifier order and semantics bounds.
2. Use Compose assertions on touch target size.
3. Check disabled states and shared components.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Image is 48 dp but clickable parent is smaller.
2. Menu icon has a tiny hit area.
3. Large-font layout compresses controls below minimum.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run semantics size assertions for every custom control.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 264. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 264: Button roles, accessible names, and state descriptions

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Button roles, accessible names, and state descriptions

Verify that custom image controls and count affordance expose appropriate button roles, unique localized names, enabled state, and selected or expanded state where relevant.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect merged and clearAndSet semantics usage.
2. Use accessibility-tree assertions.
3. Check visible text is not announced twice.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Plus and minus share the same description.
2. Count looks clickable but has no role or name.
3. Disabled state is hidden from accessibility.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose semantics tests in default and nondefault locales.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 265. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 265: Focus order, keyboard traversal, and dialog focus restoration

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Focus order, keyboard traversal, and dialog focus restoration

Verify that focus follows a logical order through screens and dialogs, initial dialog focus is deliberate, and focus returns safely after dismissal.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect focusRequester and traversal properties only where used.
2. Test hardware keyboard or Compose focus actions.
3. Open menus and nested dialogs.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Focus jumps to destructive confirm before fields.
2. Dismissed dialog leaves focus on a removed node.
3. Archived section content is focusable while collapsed.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focus traversal tests where supported.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 266. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 266: Information and state not conveyed by color alone

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Information and state not conveyed by color alone

Verify that archive state, disabled controls, errors, target completion, selection, progress, and destructive actions have text, semantics, iconography, shape, or enabled-state cues beyond color.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect light and dark themes and high-contrast-like conditions.
2. Check validation errors and progress semantics.
3. Do not demand decorative redundancy where standard component semantics already suffice.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Only a color change distinguishes disabled from enabled.
2. Target completion uses color with no text or semantics.
3. Validation field border changes color without error message.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run semantics tests and contrast review for confirmed issues.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 267. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 267: Counter WebP root originals and packaged-copy binary integrity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter WebP root originals and packaged-copy binary integrity

Verify that the three root original WebP files and packaged `drawable-nodpi` copies exist, correspond by role, and are byte-identical where the repository intends exact copies.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare filenames, hashes, dimensions, alpha, and binary contents.
2. Trace runtime resource IDs.
3. Check no generated optimization silently changed only one copy.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Packaged plus image is actually the minus asset.
2. Root and packaged copies drift.
3. A missing asset is masked by stale build output.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use binary hash comparison and resource compilation.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 268. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 268: Counter image rendering, no tint, ContentScale.Fit, and press feedback

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter image rendering, no tint, ContentScale.Fit, and press feedback

Verify that plus, minus, and undo images render with `ContentScale.Fit`, no unintended tint or crop, distinct clickable semantics, and brief press scaling that does not change layout.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Image modifiers, painter resources, tint parameters, animation state, and clipping.
2. Test enabled, disabled, pressed, and released states.
3. Check screen density and aspect ratio.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Material tint recolors branded assets.
2. Press scaling changes measured size and shifts adjacent controls.
3. ContentScale crop cuts off the image.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run image-control Compose tests and visual inspection if needed.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 269. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 269: Localized resource set parity and locale declaration

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Localized resource set parity and locale declaration

Verify that default resources and `values-fi`, `values-sv`, `values-de`, `values-fr`, `values-es`, `values-pt`, `values-it`, `values-nb`, `values-da`, and `values-nl` contain compatible required keys and match `locales_config.xml`.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare resource names, translatable flags, arrays, and locale tags.
2. Check Norwegian Bokmal uses the intended qualifier and config tag.
3. Distinguish acceptable default fallback from accidentally missing critical strings.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Declared locale has no matching resource directory.
2. A localized file defines a format-incompatible string.
3. A resource exists only in one locale and breaks compile or UI.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Android resource compilation and a locale-key parity check.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 270. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 270: Format placeholders, plurals, number formatting, and direction-safe text

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Format placeholders, plurals, number formatting, and direction-safe text

Verify that localized strings preserve placeholder count and type, plural or quantity logic where used, locale-aware count formatting, and layout text composition without hard-coded punctuation assumptions.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect shared dimensions and components, all screen Content composables, dialogs, counter image controls, Material components, `values*` resources, `locales_config.xml`, packaged WebP assets, and Compose semantics tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare every translated formatting string with default.
2. Test representative counts and optional fields in several locales.
3. Use locale APIs already in the project rather than introducing unsupported localization architecture.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A translation swaps `%d` for `%s` and crashes.
2. Count uses US grouping in every locale.
3. String concatenation produces wrong word order in another language.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run resource-format and localized rendering tests.
2. Use isolated Compose tests at multiple widths, heights, font scales, and locales, plus resource compilation and binary comparison where relevant. Accessibility must not rely on color alone and touch targets must be at least 48 dp.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 271. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 19. Theme system, recomposition, resource efficiency, privacy boundary, and security hardening

## Prompt 271: Semantic light and dark color mapping

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Semantic light and dark color mapping

Verify that semantic colors defined in `Color.kt` map correctly into fixed light and dark Material 3 schemes and that components use semantic roles rather than accidental raw values where correctness depends on them.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect scheme construction and custom component colors.
2. Check disabled, error, surface, on-surface, primary, and container pairings.
3. Compare both themes.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. On-color is mapped to the wrong background and text becomes unreadable.
2. A dark-theme component uses a light hard-coded surface.
3. A purely stylistic preference is mislabeled a bug without contrast or consistency evidence.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run theme rendering and contrast checks only for confirmed problem pairs.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 272. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 272: Fixed theme policy and absence of dynamic color

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Fixed theme policy and absence of dynamic color

Verify that RowTool consistently uses its fixed warm light and dark schemes and does not enable dynamic color accidentally through a template or platform branch.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Theme parameters, API-level branches, previews, and tests.
2. Check system theme mode changes only choose between fixed schemes.
3. Trace any dynamicDarkColorScheme call.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Android 12 devices use unrelated wallpaper colors despite brand policy.
2. Preview differs from production due to dynamic color default.
3. Removing an unused import is treated as a functional fix.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run theme tests on an API level supporting dynamic color if a real branch exists.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 273. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 273: Typography fallback and absence of missing Outfit asset

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Typography fallback and absence of missing Outfit asset

Verify that production typography deliberately uses centralized `FontFamily.SansSerif` fallback and never references a nonexistent Outfit font resource.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `Type.kt`, text styles, resource fonts, previews, and documentation.
2. Search for hard-coded font families.
3. Check release shrinking does not affect actual fonts.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Runtime font resource lookup fails.
2. One screen uses an unintended default because typography is bypassed.
3. Audit adds a font dependency or asset without a real requirement.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Compile resources and render representative text after a correction.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 274. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 274: Shared shapes, spacing, and dimension source consistency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Shared shapes, spacing, and dimension source consistency

Verify that `Shapes.kt` and `RowToolDimens.kt` provide intended shared values and that hard-coded deviations cause no concrete responsive or accessibility defect before changing them.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect usage across screens and shared components.
2. Trace duplicate values only when they lead to real inconsistency or breakage.
3. Avoid cosmetic mass replacement.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. One screen accidentally uses pixels or an incorrect unit.
2. A dimension is used for both width and height with unintended clipping.
3. A stylistic difference is standardized without need.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use layout tests for any functional dimension correction.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 275. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 275: Compose recomposition scope and unstable state reads

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Compose recomposition scope and unstable state reads

Verify that high-frequency count updates do not unnecessarily recompose entire navigation or unrelated screens because of broad state collection or unstable wrapper objects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect state collection locations, parameter stability, and derived values.
2. Use Compose recomposition tooling only if available and needed.
3. Identify a concrete expensive subtree before optimizing.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. App-level collection of full project state rebuilds NavHost on every count.
2. Action bundles are recreated with expensive work during each recomposition.
3. A harmless recomposition is called a severe performance bug without measurement.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use targeted recomposition counts or tracing before a performance change.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 276. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 276: List item identity and derived state efficiency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: List item identity and derived state efficiency

Verify that project lists use stable keys and avoid repeated sorting, filtering, or allocation during composition when the ViewModel or DAO already provides authoritative ordered lists.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace every list transformation and remember usage.
2. Test list updates and item local state.
3. Measure only if data size and code path make overhead plausible.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Sorting on every item composition creates avoidable quadratic work.
2. remember caches a list and misses new emissions.
3. Optimization duplicates business ordering logic.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run state-update tests and, if needed, simple benchmark evidence.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 277. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 277: Press and visibility animation lifetime

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Press and visibility animation lifetime

Verify that press-scale, expanded-section, progress, or other animations cancel and dispose correctly, do not launch unbounded coroutines, and preserve reduced-motion or disabled behavior offered by standard APIs.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect animation state ownership and keys.
2. Rapidly press, navigate, disable, and remove controls.
3. Check animations do not alter semantics or click count.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Each press launches a coroutine that survives navigation.
2. Removed list items retain animation state under unstable keys.
3. Animation blocks input or delays persistence.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run rapid interaction and disposal tests.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 278. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 278: Room Flow query and mapping efficiency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Room Flow query and mapping efficiency

Verify that reactive queries and mapping do not perform redundant full-database work on every count change beyond what the small app design requires, and that any performance claim has a measurable mechanism.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect DAO result shape, map operators, distinctUntilChanged use, and UI consumers.
2. Consider expected maximum 1,000 projects.
3. Check indexing before proposing caching.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A query returns all history rows just to compute undo availability.
2. Mapping performs blocking work on main for every emission.
3. A speculative cache introduces stale data.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use query plans or timing only when a real bottleneck is plausible and reproducible.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 279. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 279: Backup file I/O memory bounds and stream release

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Backup file I/O memory bounds and stream release

Verify that import and export stay bounded by documented limits, close streams and descriptors, and do not retain raw JSON or ContentResolver objects longer than needed.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace buffers, byte arrays, strings, decoded objects, and ViewModel state.
2. Test success, rejection, cancellation, and exception.
3. Check preview retains only the validated data needed for confirmation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Raw 5 MiB bytes, JSON string, and decoded object are all retained indefinitely.
2. A stream leaks after parser exception.
3. Optimization streams directly into Room before full validation and breaks safety.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use resource-close tests and memory reasoning grounded in actual object lifetimes.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 280. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 280: Coroutine, Context, Activity, and NavController reference leaks

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Coroutine, Context, Activity, and NavController reference leaks

Verify that application-scoped objects do not capture Activity or NavController, and long-lived coroutines or callbacks release screen and launcher references after lifecycle end.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect constructor types, lambdas in state, remember usage, and effect collectors.
2. Trace ContentResolver use through Route or repository context.
3. Use leak claims only with a concrete reference chain.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. AppContainer stores MainActivity.
2. ViewModel stores NavController or launcher.
3. Channel collector captures destroyed Activity through stale callback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use lifecycle tests or leak tooling evidence if available; do not add a framework speculatively.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 281. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 281: Merged manifest no-permission boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Merged manifest no-permission boundary

Verify that the final debug and release manifests contain no `uses-permission` entries, especially Internet, camera, microphone, notifications, vibration, or storage, unless an actual current repository requirement proves otherwise.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect source and merged manifests with merger blame.
2. Trace any transitive permission to its dependency.
3. Check tools removal directives.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A dependency adds Internet or advertising ID permission.
2. A debug permission is omitted from source review but packaged.
3. Audit adds storage permission for SAF unnecessarily.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Inspect merged manifests for relevant variants and assemble after any correction.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 282. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 282: System backup and device-transfer exclusions

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: System backup and device-transfer exclusions

Verify `allowBackup=false`, backup and data-extraction rules, and exclusions for files, databases, shared preferences, and external files preserve manual JSON export as the supported portability path.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect manifest attributes, XML syntax, API-version resources, and merged manifest.
2. Check rule coverage against actual storage locations.
3. Do not assume allowBackup alone covers every device-transfer behavior on all targets.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Room or DataStore data is included in system transfer contrary to policy.
2. Rule XML references wrong domains or is not linked from manifest.
3. Manual exported documents outside app storage are incorrectly claimed to be controlled by app backup rules.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Android resource and lint checks plus merged-manifest inspection.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 283. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 283: No cleartext traffic, networking client, or hidden network path

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: No cleartext traffic, networking client, or hidden network path

Verify `usesCleartextTraffic=false`, no network security relaxation, no networking dependency, and no code path opens sockets or HTTP requests in this local-only app.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect manifest, XML, dependency graph, source imports, WebView use if any, and platform intents.
2. Distinguish opening the system document picker from network access.
3. Check release variant.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A network client or telemetry SDK is packaged.
2. A network security config permits cleartext broadly.
3. Privacy text says offline while a hidden update check runs.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use source search, dependency reports, and merged manifest. Do not add network tests when no path exists.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 284. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 284: Secrets, production logging, and local-data privacy

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Secrets, production logging, and local-data privacy

Verify that production code and build artifacts contain no credentials and that logs do not expose project names, counts, backup JSON, content URIs, file paths, or database contents without a concrete diagnostic need.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Log, println, exception, analytics-like, and test-debug statements.
2. Check release BuildConfig or shrink behavior.
3. Distinguish safe generic operational messages from sensitive values.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Backup payload is logged on parse failure.
2. Content URI and project names enter release logs.
3. A scanner test string is mistaken for a live secret.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use static inspection and release build artifact checks for confirmed concerns.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 285. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 285: Tapjacking boundary and untrusted document-provider input together

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Tapjacking boundary and untrusted document-provider input together

Verify that obscured-touch filtering protects confirmation and destructive controls while imported document data remains treated as untrusted until full validation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/theme/`, shared dimensions and shapes, Compose state reads, list and animation behavior, Room Flow queries, file I/O, coroutine references, manifest and merged manifest, backup XML, dependencies, logging, and activity touch handling.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace Activity event filtering, dialog actions, URI handling, size limits, parser settings, and transaction entry.
2. Check accessibility actions remain usable.
3. Consider partially obscured touches specifically.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Overlay-assisted tap confirms replacement.
2. Trusted UI confirmation bypasses payload revalidation or payload identity binding.
3. Security handling blocks legitimate accessibility services without evidence and is changed speculatively.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused security tests where feasible and report platform limitations honestly.
2. Use profiling or performance claims only when a concrete mechanism is established. Use merged-manifest, dependency, resource, and focused runtime tests for privacy and security. Do not add security theater or speculative abstractions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 286. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 20. Host and device tests, coverage evidence, release builds, Play documents, and handoff readiness

## Prompt 286: Pure domain and ProjectValidation test completeness and determinism

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Pure domain and ProjectValidation test completeness and determinism

Verify that host-side tests cover actual name, numeric, unit, derived progress, and repeat semantics at important boundaries without duplicating implementation or relying on locale or wall clock accidentally.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect assertions, parameterization, test names, and production calls.
2. Check Unicode code-point cases and exact numeric boundaries.
3. Identify meaningful missing regression only when tied to a confirmed defect.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Tests call a copied helper instead of production validation.
2. Current time or default locale makes results flaky.
3. A test asserts an invented business rule absent from product behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `:app:testDebugUnitTest` or focused classes and report counts accurately.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 287. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 287: Robolectric Room schema, foreign-key, and transaction tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Robolectric Room schema, foreign-key, and transaction tests

Verify that host-side Room tests use a configuration close enough to production to exercise foreign keys, transactions, indices where relevant, and reopen behavior.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect database builder, executors, close handling, and cleanup.
2. Check cascade and rollback tests.
3. Compare entity set with production database.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Foreign keys are disabled in tests and orphan defects pass.
2. Transactions are mocked rather than executed.
3. Database files leak between tests.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused Room tests and inspect failures before changing test infrastructure.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 288. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 288: CounterRepository concurrency, boundary, history, and retention tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: CounterRepository concurrency, boundary, history, and retention tests

Verify that tests exercise serialization, lost-update prevention, no-op behavior, exact history chains, undo, 100-row cap, and cross-project isolation using real concurrency where needed.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect whether coroutines actually overlap or run sequentially.
2. Assert final persisted state and result counts.
3. Check repeatability.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A concurrency test never suspends and cannot expose races.
2. Only final count is asserted while history is corrupt.
3. Retention test checks count but not which rows remain.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run the focused suite repeatedly if nondeterminism is suspected.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 289. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 289: Preferences DataStore persistence and failure tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preferences DataStore persistence and failure tests

Verify that tests cover defaults, all setting values, unknown enum fallback, last-active repair, reopen persistence, and write failures without using production preference files.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect temporary scope and file cleanup.
2. Check Flow collection timing and dispatcher control.
3. Test database-commit then preference-failure ordering.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Tests share one DataStore file and influence each other.
2. Unknown enum case is untested and crashes production.
3. Fake writes cannot fail, so partial-commit behavior is unverified.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run DataStore tests with isolated temporary storage.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 290. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 290: Backup codec, size, validation, round-trip, and atomicity tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Backup codec, size, validation, round-trip, and atomicity tests

Verify that backup tests cover exact root contract, Unicode, unknown keys, every rejection rule, byte boundaries, preview, replacement rollback, settings and history exclusion, and own-export import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect fixture construction for default masking.
2. Assert zero mutation for rejected data.
3. Use real production codec and repository paths.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Tests deserialize directly into expected objects but never inspect emitted bytes.
2. Oversize test uses characters rather than UTF-8 bytes.
3. Rollback test mocks away Room transaction behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused backup host tests and any necessary device URI tests.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 291. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 291: ViewModel state and one-shot effect tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: ViewModel state and one-shot effect tests

Verify that ViewModel tests assert persistent state separately from navigation, message, and haptic effects, including success, failure, cancellation, and collector timing.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect test collectors, Turbine or equivalent use if present, and virtual time.
2. Check exactly-once event assertions.
3. Use production-like fake repository results.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Test leaves an unconsumed event and hangs or masks later output.
2. A state assertion accidentally consumes a Channel effect.
3. Only happy paths are tested for destructive actions.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused ViewModel tests with deterministic coroutine scheduling.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 292. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 292: Projects screen isolated Compose semantics tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Projects screen isolated Compose semantics tests

Verify that device or host Compose tests cover active and archived lists, empty states, editor modes, validation, menus, confirmations, keys, and accessibility semantics without depending on brittle pixel positions.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect node selectors and localized text assumptions.
2. Use stable test tags only where they serve a real ambiguity.
3. Change ordering and font scale.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Test finds the wrong duplicate text node.
2. Index-based clicks miss identity bugs.
3. Semantics tests pass while touch target is undersized.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run the relevant Compose test task and report whether it compiled or executed.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 293. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 293: Counter screen isolated Compose semantics tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter screen isolated Compose semantics tests

Verify that tests cover plus, minus, undo, count set, reset, archive, delete, boundaries, archived state, target and repeat display, touch targets, roles, and rapid interactions.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect fake state and action counters.
2. Test multiple count widths and locales.
3. Check disabled semantics and exactly-once callbacks.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Only visible images are asserted, not accessible names or enabled state.
2. Rapid click test cannot detect double dispatch.
3. Target and repeat calculations are stubbed and production path remains untested.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused counter Compose tests.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 294. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 294: Full-activity navigation, startup, recreation, and persistence tests

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Full-activity navigation, startup, recreation, and persistence tests

Verify that instrumentation tests exercise real NavHost, AppContainer or controlled database, startup selection, create auto-open, Back behavior, archive/delete navigation, process-like recreation, and persisted undo.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect test isolation and database cleanup.
2. Distinguish Activity recreation from actual process death.
3. Check last-active preference and Room together.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Test starts directly at a screen and misses NavHost behavior.
2. State is held in a fake singleton rather than persistence.
3. Back-stack assertions depend on implementation internals without user-visible outcome.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run `connectedDebugAndroidTest` only when an intended device or emulator is available.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 295. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 295: Android-test compilation versus execution evidence

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Android-test compilation versus execution evidence

Verify that reports and CI accurately distinguish `assembleDebugAndroidTest`, which compiles the test APK, from `connectedDebugAndroidTest`, which executes tests on a target.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect task commands and outputs.
2. Check CI emulator setup.
3. Review handoff wording.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Compiled tests are reported as passed.
2. No device is connected but audit claims runtime coverage.
3. A stale previous device report is attributed to the current build.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Record exact task names, target identity when executed, and actual pass or failure counts.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 296. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 296: CI results, local results, and baseline failure separation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: CI results, local results, and baseline failure separation

Verify that the final audit report attributes each validation result to the command and environment actually used and separates pre-existing or unrelated failures from Codex changes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Capture baseline where feasible before modifying relevant code.
2. Compare timestamps and current source revision.
3. Do not cite a workflow definition as a completed run.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A local pass is described as GitHub CI pass.
2. A baseline failure is silently fixed outside scope or blamed on the patch.
3. A command that never ran is listed as successful.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use truthful evidence records rather than adding code.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 297. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 297: JaCoCo and Sonar coverage exclusions and claim accuracy

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: JaCoCo and Sonar coverage exclusions and claim accuracy

Verify that coverage reports and documentation acknowledge exclusions for MainActivity, RowToolApplication, and all UI code, so the percentage is not used as proof of UI behavior.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect report configuration and generated XML if present.
2. Compare class directories and exclusions.
3. Check Sonar import paths.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. High coverage percentage is claimed as complete app coverage.
2. Report omits production non-UI packages unintentionally.
3. A missing report is treated as zero defects.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run report generation only when available and inspect actual contents.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 298. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 298: Release APK and AAB build, shrinking, and smoke readiness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Release APK and AAB build, shrinking, and smoke readiness

Verify that `assembleRelease` and `bundleRelease` complete with configured shrinking when the environment supports them, and inspect outputs without calling unsigned artifacts Play-ready.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Review R8 warnings, merged manifest, packaged permissions, assets, locales, and application identity.
2. Check output freshness and variant.
3. Run a release smoke test only with safe local signing or existing supported setup.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Release builds but omits counter assets or serializers.
2. Debug artifact is mistaken for release.
3. Unsigned AAB is described as ready to upload.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run release tasks and artifact inspection as supported, recording limitations.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 299. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 299: Play listing, Data Safety, privacy policy, and app behavior consistency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Play listing, Data Safety, privacy policy, and app behavior consistency

Verify that repository Play-facing documents accurately reflect paid download, no ads, no billing, no network SDK, no permissions, local Room and DataStore data, manual JSON export, and disabled system backup.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare each claim with merged manifest, dependency graph, source, and Settings text.
2. Check URLs and hosted status only from repository evidence unless external verification is explicitly requested.
3. Do not rewrite marketing style without a factual defect.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Data Safety says no collected data while a new analytics SDK exists.
2. Privacy policy claims automatic cloud backup.
3. Listing promises an unimplemented feature.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use repository evidence and report external hosting or Console checks as unverified.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 300. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 300: Release checklist, signing, versioning, translation, and external-step boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Release checklist, signing, versioning, translation, and external-step boundary

Verify that `RELEASE_CHECKLIST.md` distinguishes repository-complete work from external trademark, Play Console, upload key, pricing, signed AAB inspection, translation review, screenshots, privacy hosting, listing forms, and rollout steps.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `app/src/test/`, `app/src/androidTest/`, Gradle test tasks, CI workflows, JaCoCo and Sonar configuration, release build outputs, `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md`, and related resources.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect checklist ordering and prerequisites.
2. Confirm versionCode increments are required only for later uploads, not this audit.
3. Check no secrets are requested inside the repository.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Checklist marks signing or Play rollout complete without evidence.
2. It instructs committing a keystore or password.
3. Audit performs an external irreversible step.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Make only factual repository-document corrections when a concrete contradiction is verified. Leave all external steps undone.
2. Run only commands supported by the available environment and report exact results. Compilation is not execution. Unsigned artifacts are not Play-ready. External Play Console, signing, trademark, hosting, and rollout steps remain undone.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 301. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 21. Final repository-wide verification

## Prompt 301: Final repository-wide verification and truthful audit completion report

```text
ROLE AND BOUNDED OBJECTIVE

You are performing the final repository-wide verification of the current RowTool audit. Execute this prompt as a fully self-contained final audit area. Do not assume that earlier prompts were completed merely because this prompt is present. Verify their recorded results and the actual repository state.

Audit area: Final repository-wide verification and truthful audit completion report

The objective is to prove that every numbered audit area was processed in order, every retained change corrects a verified real issue with the smallest safe patch, the final repository remains coherent, no user work was damaged, and all validation claims are supported by actual current evidence.

FINAL REPOSITORY-WIDE VERIFICATION PROCEDURE

1. Re-read the repository-root file named exactly `PROJECT.md`, but do not modify it. Reconcile its important context with the actual final code, configuration, tests, generated artifacts, and repository state.
2. Run `git status --short` and compare it with the original baseline captured before the first audit correction. Treat all original baseline changes as user work.
3. Inspect the complete final unstaged and staged diff, including binary-file changes, file modes, new files, deleted files, and generated artifacts. Review every changed hunk and every changed file individually.
4. Verify that each Codex-created change is traceable to a confirmed defect recorded under a numbered prompt and that no unrelated cleanup, formatting churn, speculative abstraction, dependency update, broad rewrite, or accidental generated output remains.
5. Verify that no new dependency, plugin, repository, permission, Android component, build property, schema change, Room migration, backup-format change, public API change, architecture layer, external service, analytics, billing, advertising, networking, or crash-reporting capability was introduced without direct and documented technical necessity for a confirmed defect.
6. Verify that `PROJECT.md` is unchanged and that no user baseline change was reverted, overwritten, hidden, staged, committed, or cleaned.
7. Run `git diff --check`.
8. Run the project-appropriate final host validation supported by the environment, normally including `./gradlew.bat :app:assembleDebug`, `./gradlew.bat :app:testDebugUnitTest`, `./gradlew.bat :app:lintDebug`, `./gradlew.bat :app:kspDebugKotlin`, and `./gradlew.bat :app:assembleDebugAndroidTest` on Windows PowerShell, adapting wrapper syntax only to the actual shell.
9. Run `./gradlew.bat :app:connectedDebugAndroidTest` only if an intended device or emulator is actually available. Report the device or emulator identity and distinguish compilation from execution.
10. Run `./gradlew.bat :app:assembleRelease` and `./gradlew.bat :app:bundleRelease` when the local environment supports the configured release build. Do not describe unsigned output as Play-ready and do not create or request signing secrets.
11. Inspect the final merged manifest and resolved production dependency graph when any change could affect permissions, components, backup, cleartext, networking, analytics, billing, advertising, crash reporting, storage, or SDK behavior. Confirm the local-only no-permission privacy boundary from actual artifacts.
12. Inspect the committed Room schema and backup contract whenever any change touched persistence, entities, DAOs, mappers, validation, serialization, import, or export. Confirm that no unplanned schema or migration change entered the diff.
13. Inspect quality-tool reports according to their real semantics. A successful Detekt task with `ignoreFailures = true` is not proof of a clean report. A plan-only project wrapper is not proof that its scan executed. A Sonar or JaCoCo percentage excluding UI is not evidence of UI coverage. Do not claim external CI, CodeQL, Sonar server, Play Console, or hosted privacy-policy results without actual current evidence.
14. Confirm that every numbered Prompt 1 through Prompt 300 has its own recorded result and that no prompt was skipped, merged into a generic claim, or marked complete while partially processed.
15. If any correction fails final validation, investigate only the regression introduced by the audit change. Do not fix unrelated baseline failures. Apply the smallest safe correction or revert only the exact Codex-created faulty hunk through a precise manual edit, then rerun the relevant validation and inspect the diff again.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

Do not create new visible work merely to make the final diff appear substantial. If final review reveals a confirmed regression caused by a Codex-created audit change, correct only that regression with the smallest safe edit and validate it. If review reveals a possible unrelated issue that was not processed under its numbered area, record it as unverified or incomplete rather than expanding scope. Do not modify `PROJECT.md`, update dependencies, change schema, add migrations, rework architecture, or perform external release actions unless a previously confirmed defect made the exact repository-internal change technically unavoidable.

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

REQUIRED FINAL AUDIT REPORT

Record the final result under this prompt number. Include all of the following, without claiming evidence that was not produced:

1. The exact final `git status --short` output or an accurate path-by-path transcription of it.
2. The original pre-audit user-change baseline and a clear separation between those changes and Codex-created audit changes.
3. Every file changed and the reason for each change, including the prompt number or confirmed root cause that required it.
4. Every confirmed defect corrected, including evidence, precise location, root cause, concrete impact, realistic severity, smallest correction, and validation result.
5. Every numbered prompt that found no confirmed issue and therefore made no change. This may be grouped by prompt number ranges only when each prompt in the range was actually processed and recorded.
6. Every possible but unverified risk left unchanged, with the reason it could not be confirmed.
7. Every validation command actually run, its environment where material, and its actual pass, failure, skip, or unavailable result.
8. Every pre-existing or unrelated validation failure, clearly separated from failures caused by Codex changes.
9. Every validation that could not be run and the exact environmental reason.
10. Confirmation that `PROJECT.md` was not modified.
11. Confirmation that no original user change was reverted, overwritten, hidden, staged, committed, or otherwise damaged.
12. Confirmation that no external account, cloud service, Play Console entry, signing key, production data, deployment, publication, or rollout was changed.
13. The exact last numbered prompt completed. Do not state that the document was fully completed unless Prompts 1 through 301 were all processed in order.

COMPLETION AND INTERRUPTION RULE

This is the final numbered prompt. Do not continue to another prompt after its final report.

If execution limits prevent completing this final verification, do not claim the audit is complete. State the exact last fully completed verification step, every command already run with its result, every unresolved validation or diff issue, and the fact that Prompt 301 remains incomplete. If any earlier numbered prompt lacks a complete recorded result, state the exact missing prompt number and do not use wording that implies full completion.
```
