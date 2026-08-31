# 5. Navigation routes, startup selection, back-stack behavior, and destination transitions

## Prompt 61: Route constants and navigation pattern agreement

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Route constants and navigation pattern agreement

Verify that the declared `projects`, `counter/{projectId}`, and `settings` routes, argument names, and navigation calls agree exactly.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect route constants, NavHost declarations, builders, deep link declarations if any, and tests.
2. Check literal duplicates for drift.
3. Trace destination arguments into ViewModel or repository lookup.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A navigation call uses a route literal that no destination matches.
2. The argument key differs between declaration and retrieval.
3. A stale test passes against a helper no longer used by production.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run navigation-focused tests and compile after a correction.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 62. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
## Prompt 62: Project ID URI encoding when building counter routes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project ID URI encoding when building counter routes

Verify that every project ID inserted into a route is encoded exactly once and cannot break path segmentation or route parsing.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect all route-building call sites, helper functions, and tests with reserved characters.
2. Confirm whether IDs are UUIDs in normal creation while still handling imported IDs according to actual validation.
3. Check for double encoding.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. An imported valid ID containing a reserved character creates the wrong route.
2. A route helper double-encodes `%` and lookup fails.
3. One navigation path bypasses the shared encoder.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused route-builder and navigation tests with representative IDs.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 63. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 63: Counter argument decoding and lookup identity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter argument decoding and lookup identity

Verify that the counter destination decodes the route argument once and passes the exact project ID to the authoritative lookup path.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect NavBackStackEntry argument access, decoding helper, null handling, and ViewModel factory arguments.
2. Compare with route encoding tests.
3. Trace failure behavior for malformed encodings.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Encoded text is used as the database ID without decoding.
2. Decoding twice changes a legitimate percent sequence.
3. A null argument becomes the literal string `null` and triggers misleading lookup.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused argument-decoding tests and destination launch tests.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 64. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 64: Malformed or absent counter route argument handling

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Malformed or absent counter route argument handling

Verify that a malformed, blank, or absent project argument fails safely through the intended fallback rather than crashing or constructing invalid state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect argument type, nullability, default handling, and exception boundaries.
2. Trace the exact destination transition after failure.
3. Check user-visible messaging only if the current design provides it.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. URI decoding throws and crashes the Activity.
2. Blank ID queries an unintended row or leaves permanent loading.
3. Fallback loops repeatedly between destinations.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use navigation tests with missing and malformed arguments.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 65. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 65: Direct counter launch for a missing project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Direct counter launch for a missing project

Verify that navigating directly to a counter whose project no longer exists returns to the correct safe destination and clears stale back-stack state as intended.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace repository missing result through ViewModel effect and NavHost handling.
2. Check cold start and in-app stale navigation separately.
3. Verify no null project UI remains interactive.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A missing project produces a blank counter screen.
2. Back returns to the same invalid counter in a loop.
3. A stale UI allows mutation calls with the missing ID.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run full navigation tests for missing-project direct launch.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 66. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 66: Direct counter launch for an archived project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Direct counter launch for an archived project

Verify the intended behavior when a route points to an archived project, including whether it is displayed read-only, redirected, or handled by the current documented flow.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect actual repository and `PROJECT.md` context without assuming archived navigation behavior beyond confirmed implementation.
2. Trace state and enabled actions.
3. Check startup last-active validation separately.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Archived project opens as fully mutable.
2. Navigation crashes because active-only lookup returns null without fallback.
3. A redirect leaves an invalid destination underneath the back stack.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused archived-project navigation and mutation-guard tests.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 67. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 67: Stored last-active project validation against Room

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Stored last-active project validation against Room

Verify that startup never trusts `last_active_project_id` without confirming the project exists and is active in Room.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace DataStore read, Room lookup, archive state check, and emitted start destination.
2. Check missing, blank, stale, and valid values.
3. Inspect exception handling for either store.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A deleted ID opens a broken counter.
2. An archived ID bypasses the active check.
3. A transient Room delay is mistaken for absence and overwrites a valid preference unnecessarily.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run startup selection tests across all value states.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 68. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 68: Most-recently-updated active fallback ordering

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Most-recently-updated active fallback ordering

Verify that an invalid or absent last-active ID falls back to the actual most recently updated active project using deterministic query ordering.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect DAO query ordering, tie behavior, Flow or suspend use, and ViewModel selection.
2. Check archived rows are excluded.
3. Use fixed timestamps in tests.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Fallback selects an archived project.
2. Ascending order chooses the oldest project.
3. Equal timestamps produce unstable selection that breaks tests or user expectations.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused DAO and startup-selection tests.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 69. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 69: Startup behavior when there are no active projects

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Startup behavior when there are no active projects

Verify that startup opens Projects when the database has no active project, including cases where archived projects or a stale preference exist.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace empty active query, archived-only database, and missing preference.
2. Check start destination initialization and splash completion.
3. Verify no counter ViewModel is created with a synthetic ID.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. The app remains on splash or loading forever.
2. It opens Settings or an invalid Counter route.
3. An archived project is treated as active fallback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run startup tests for empty and archived-only datasets.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 70. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 70: Automatic navigation to a newly created project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Automatic navigation to a newly created project

Verify that successful creation emits exactly one navigation event for the actual inserted project and that failed creation emits none.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace repository result, generated ID, ViewModel effect, collector, and NavController call.
2. Check rapid repeated submission and recreation.
3. Confirm the dialog closes only on accepted creation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two counter destinations are pushed for one creation.
2. Navigation occurs before insertion commits and lookup races.
3. Validation failure still opens a counter with an unpersisted ID.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel effect and full create-flow navigation tests.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 71. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 71: Startup counter back-stack clearing

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Startup counter back-stack clearing

Verify that an automatically selected startup counter has the intended back-stack so Back does not reveal a duplicate or synthetic start destination.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `popUpTo`, inclusive, saveState, restoreState, and launchSingleTop flags.
2. Test cold startup with a valid last-active project and fallback selection.
3. Compare system Back and top-app-bar navigation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Back exits unexpectedly when Projects should remain reachable.
2. Back reveals another identical counter.
3. Back loops between a startup placeholder and counter.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use full-activity navigation tests; inspect actual back-stack outcomes.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 72. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 72: Counter-to-Projects back navigation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter-to-Projects back navigation

Verify that leaving a counter through Back or explicit navigation reaches Projects once and does not retain stale mutable counter destinations.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect NavController actions and top bar callbacks.
2. Test normal open, startup open, and post-edit cases.
3. Check last-active preference behavior is independent from back-stack cleanup.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Multiple Projects destinations accumulate.
2. Returning to Counter reuses stale arguments or state.
3. Explicit Up behaves differently from system Back without intention.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run navigation tests for each entry path.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 73. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 73: Archiving the currently displayed or last-active project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archiving the currently displayed or last-active project

Verify that successful archive updates persistence, clears matching last-active preference when possible, and navigates away without leaving a mutable archived counter in the stack.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace archive transaction, preference clear, ViewModel effect, and NavHost transition.
2. Check preference-write failure semantics.
3. Test stale UI and repeated archive action.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Navigation occurs although archive failed.
2. Archived counter remains interactive underneath Projects.
3. Preference clearing failure rolls back a committed archive contrary to intended ordering.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run repository, ViewModel, and full navigation tests for archive.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 74. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 74: Deleting the currently displayed or last-active project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Deleting the currently displayed or last-active project

Verify that confirmed deletion removes the project and history, clears matching last-active preference when possible, and navigates safely exactly once.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace confirmation target, repository result, cascade, preference clear, and effect collection.
2. Check deletion from Projects and Counter.
3. Test already-deleted stale target.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A stale confirmation deletes a different selected project.
2. Back stack exposes the deleted counter again.
3. Preference failure incorrectly resurrects or rolls back database deletion.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused delete, cascade, and navigation tests.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 75. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 75: Duplicate navigation effects, replay, and collector restart

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Duplicate navigation effects, replay, and collector restart

Verify that transient navigation is delivered once per accepted operation and is not replayed after lifecycle stop, recomposition, or Activity recreation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt`, route builders, navigation effects, startup state in `RowToolAppViewModel`, and destination-specific ViewModel construction.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Channel capacity, receiveAsFlow, collector keys, and remembered callbacks.
2. Test collector cancellation and restart.
3. Check concurrent events preserve required ordering without duplication.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A buffered event navigates twice after two collectors overlap.
2. An event is lost because no collector exists during a short stop.
3. A stale callback navigates using an obsolete NavController or target.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel effect tests and lifecycle-aware navigation instrumentation when needed.
2. Use focused navigation unit tests and Compose full-activity tests. A compile-only task does not prove runtime back-stack behavior. Preserve URI encoding, direct-counter fallback, and deliberate back-stack clearing.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 76. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 6. Route and Content architecture, immutable state, effects, coroutines, and ViewModel ownership

## Prompt 76: Route and Content responsibility boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Route and Content responsibility boundary

Verify that Route composables own lifecycle collection, dialogs, platform launchers, side effects, and navigation translation while Content composables remain state-and-action renderers.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect all three screens for direct repository, NavController, Activity, ContentResolver, or launcher access inside Content.
2. Check shared dialogs and components preserve the same seam.
3. Distinguish a harmless UI-local remember state from platform ownership.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A Content composable opens a document picker directly and becomes untestable in isolation.
2. A Route duplicates domain rendering logic and diverges from Content tests.
3. Repository access occurs during composition.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run isolated Content Compose tests and compile after a justified ownership correction.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 77. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 77: No repository or database resolution from rendering composables

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: No repository or database resolution from rendering composables

Verify that rendering code never reaches AppContainer, Room, DAO, or repositories directly and receives all required data and callbacks explicitly.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Search composable call chains for service locators and application casts.
2. Trace previews and tests.
3. Check helper composables called only from Content as well as top-level screens.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Composition performs a database query or constructs a repository.
2. A helper captures an Application singleton and bypasses ViewModel guards.
3. Preview behavior differs because production dependencies are hidden.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use compilation and isolated UI tests. Do not add a DI framework.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 78. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 78: Lifecycle-aware StateFlow collection in Routes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Lifecycle-aware StateFlow collection in Routes

Verify that persistent screen state is collected with lifecycle-aware APIs and appropriate minimum lifecycle state rather than unrestricted collection.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `collectAsStateWithLifecycle` or equivalent usage and imports.
2. Check each Route and app-level theme/startup state.
3. Trace behavior while Activity is stopped and resumed.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A Flow continues expensive collection while the screen is stopped.
2. A plain `collectAsState` leaks lifecycle expectations into Activity teardown.
3. Different screens use inconsistent lifecycle thresholds that lose required state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run lifecycle-aware Compose tests only when a real collection issue is established.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 79. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 79: Immutable UI state exposure and mutation containment

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Immutable UI state exposure and mutation containment

Verify that ViewModels expose immutable state and callers cannot mutate internal collections or mutable state holders outside controlled operations.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect state data classes, collection types, backing MutableStateFlow visibility, and copy operations.
2. Trace entity-to-UI mapping.
3. Check tests do not mutate production state objects.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A mutable list escapes and changes without a Flow emission.
2. Public MutableStateFlow allows UI code to bypass repository operations.
3. State contains a DAO entity later mutated in place.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused state-emission tests after any correction.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 80. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 80: StateFlow initial values and loading or empty-state distinction

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: StateFlow initial values and loading or empty-state distinction

Verify that initial UI state cannot incorrectly present an empty database, missing project, or disabled action before the authoritative Flow has emitted.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect initial values, loading flags, nullable project semantics, and `stateIn` start behavior.
2. Trace fast and slow Room/DataStore emissions.
3. Check splash and destination fallback interactions.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A momentary initial empty list triggers destructive fallback navigation.
2. Counter shows zero for a project whose data has not loaded.
3. Loading never clears after an exception.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use ViewModel tests with controlled delayed flows.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 81. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 81: `stateIn` and `shareIn` scope, sharing policy, and upstream lifetime

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: `stateIn` and `shareIn` scope, sharing policy, and upstream lifetime

Verify that shared flows use the intended ViewModel scope, sharing policy, replay, and initial state without duplicate database subscriptions or stale values.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect every `stateIn`, `shareIn`, `combine`, and derived Flow.
2. Trace subscriber start and stop behavior.
3. Check tests with multiple collectors.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Each recomposition creates another upstream subscription.
2. WhileSubscribed timeout causes a required source to stop and miss a state transition.
3. Replay delivers a transient event as persistent state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run coroutine tests with controlled collectors and virtual time if changed.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 82. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 82: Buffered Channel capacity and overflow behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Buffered Channel capacity and overflow behavior

Verify that transient effects use a Channel capacity and overflow policy that matches expected low-volume navigation, message, and haptic events.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Channel declarations and send versus trySend use.
2. Trace what happens with no active collector and with bursts of taps.
3. Check whether failed sends are observed or intentionally ignored.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Unlimited buffering grows from rapid repeated input.
2. Rendezvous send suspends a critical repository result indefinitely.
3. DROP_OLDEST removes a required navigation event.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused effect-channel tests under absent, slow, and active collectors.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 83. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 83: One-shot effect separation from persistent state

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: One-shot effect separation from persistent state

Verify that messages, navigation, haptics, and launch requests are not stored in persistent state in a way that replays them after recomposition or recreation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect state fields and effect types.
2. Trace acknowledgment or consumption patterns if present.
3. Check every operation success and error path.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A success message appears repeatedly after rotation.
2. An import launcher is reopened from restored state.
3. A haptic event is lost because it is modeled as a state value overwritten before collection.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel and recreation tests for any corrected event path.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 84. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 84: `rememberUpdatedState` around long-lived effect collectors

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: `rememberUpdatedState` around long-lived effect collectors

Verify that long-lived `LaunchedEffect` collectors invoke the latest navigation, Snackbar, launcher, and haptic callbacks without restarting unnecessarily or capturing stale objects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect collector keys and captured callback references.
2. Trace callback changes caused by recomposition or Activity recreation.
3. Check all three Routes.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Collector calls a stale NavController callback after host changes.
2. Adding callback to the effect key restarts collection and duplicates or loses buffered events.
3. A remembered state is applied to data that should instead restart the effect.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused recomposition and effect-delivery tests.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 85. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 85: LaunchedEffect, DisposableEffect, and SideEffect key correctness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: LaunchedEffect, DisposableEffect, and SideEffect key correctness

Verify that Compose side effects start, restart, and dispose only for the state identities that truly require it.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect every effect in Routes, Counter image controls, window handling, and dialogs.
2. Trace key stability and cleanup.
3. Check whether changing project IDs cancels old collectors before starting new ones.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A constant key retains work for the previous project.
2. An unstable lambda key restarts an effect every recomposition.
3. Disposable cleanup never runs and a window flag remains set.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use Compose tests that change the relevant key and assert one start and one cleanup.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 86. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 86: ViewModelScope cancellation and operation lifetime

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: ViewModelScope cancellation and operation lifetime

Verify that ViewModel operations cancel with the ViewModel and do not launch independent scopes that outlive the screen or leak work.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect coroutine scopes, supervisors, and context switching.
2. Trace database and file operations launched from ViewModels.
3. Check whether cancellation is incorrectly swallowed and converted to a user error.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. GlobalScope or an unmanaged scope outlives the ViewModel.
2. CancellationException is caught as a failure and emits misleading UI.
3. An import continues mutating after its owning ViewModel is cleared contrary to intended behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use coroutine tests that cancel the ViewModel or test scope.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 87. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 87: Dispatcher use for Room, DataStore, JSON, and file I/O

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Dispatcher use for Room, DataStore, JSON, and file I/O

Verify that potentially blocking stream and JSON work does not run on the main thread while avoiding unnecessary dispatcher hopping around Room and DataStore APIs that are already asynchronous.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace actual blocking calls and contexts.
2. Inspect withContext boundaries and injected dispatchers in tests if present.
3. Measure or reproduce a main-thread stall before changing architecture.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A 5 MiB import is fully read and decoded on the main thread.
2. Nested dispatcher switches complicate cancellation without benefit.
3. A test-only dispatcher leaks into production.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused coroutine tests and strict-mode evidence if available. Do not add dispatcher abstractions speculatively.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 88. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 88: Exception propagation and structured concurrency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Exception propagation and structured concurrency

Verify that child coroutine failures are handled at the correct boundary, required sibling work is cancelled or preserved intentionally, and errors reach structured result or effect paths.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect launch, async, supervisor, runCatching, and catch blocks.
2. Trace CancellationException separately.
3. Check database-then-preference sequences and import/export operations.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A catch-all swallows programming errors and leaves stale loading.
2. One failed child cancels an unrelated collector.
3. An exception after database commit is reported as if the database change failed.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run failure-injection ViewModel or repository tests for changed paths.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 89. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 89: Rapid repeated UI actions and reentrancy control

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Rapid repeated UI actions and reentrancy control

Verify that create, delete, archive, import, export, reset, and set-count actions behave safely when invoked repeatedly before the first operation completes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace button enabled state, in-flight flags, repository serialization, and duplicate effects.
2. Distinguish idempotent reads from destructive writes.
3. Check process and configuration recreation while an operation is active.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two import replacements run concurrently.
2. Double create inserts two projects from one visible submission.
3. A disabled button remains clickable through semantics or stale callback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use controlled delayed repository fakes and rapid-action tests.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 90. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 90: Action bundle identity, freshness, and stale lambda capture

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Action bundle identity, freshness, and stale lambda capture

Verify that action objects passed to Content always target the current project and latest callbacks without causing unnecessary recomposition or stale operations.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/*/*Route.kt`, `*Screen.kt`, `*ViewModel.kt`, state and action models, effect Channels, flow operators, coroutine launches, and repository interfaces used by presentation code.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect remember usage, data-class construction, and lambda captures.
2. Change project ID or selection in tests and invoke actions.
3. Check equality or stability annotations only where actually used.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. An edit action still targets the previous project after navigation.
2. A remembered action captures an obsolete dialog state.
3. Attempting to optimize stability hides state updates.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused Compose tests that recompose with a different state and invoke each affected action.
2. Use focused host-side ViewModel tests and Compose tests. Validate lifecycle behavior with instrumentation only when it cannot be established from code and existing tests. Do not introduce new architecture layers merely for style.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 91. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 7. Domain identity, project validation, numeric invariants, and result semantics

## Prompt 91: Project ID generation and persisted identity stability

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project ID generation and persisted identity stability

Verify that newly created projects receive a unique nonblank identity and that edits, archive, restore, count changes, export, import, and UI mapping preserve that identity exactly.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace ID generation at creation and all copy or mapping operations.
2. Check imported IDs are accepted or rejected according to actual validation rather than replaced silently.
3. Inspect list keys and navigation route use of the ID.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Editing a project generates a new ID and separates it from its history.
2. Two creations can reuse a fixed or blank ID.
3. Import silently rewrites IDs and breaks referential meaning.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run creation, edit, export/import, and history identity tests after any correction.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 92. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 92: Project name trimming at every authoritative write path

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project name trimming at every authoritative write path

Verify that project names are trimmed consistently before persistence through create, edit, and import while UI-only validation does not replace repository validation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace raw editor text into repository methods and entity fields.
2. Check leading and trailing Unicode whitespace behavior used by the actual implementation.
3. Compare import and interactive writes.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Create trims but edit persists spaces.
2. Import bypasses trimming and creates names that UI cannot reproduce.
3. Only the dialog validates, so a direct repository caller persists invalid text.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused `ProjectValidation` and repository tests for whitespace cases.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 93. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 93: Blank project name rejection

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Blank project name rejection

Verify that names empty after the implementation-defined trimming are rejected by every repository and import path with a structured, noncrashing result.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test empty, spaces, and representative Unicode whitespace supported by current logic.
2. Trace error mapping to create and edit UI.
3. Check no partial insert or timestamp update occurs.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A blank project is inserted through import.
2. Edit converts a valid project to a blank name.
3. UI displays an error but repository still writes.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run validation, repository, and editor error tests.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 94. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 94: Maximum 60 Unicode code point name limit

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Maximum 60 Unicode code point name limit

Verify that the maximum project name length is measured as 60 Unicode code points rather than UTF-16 code units, bytes, or grapheme assumptions, consistently across editor, repository, and import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect the exact length function in `ProjectValidation` and UI.
2. Test boundary values with supplementary characters and combining sequences without imposing normalization.
3. Check error messages and persistence.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A 60-code-point emoji name is rejected because it uses 120 UTF-16 units.
2. A 61-code-point name passes one path but fails another.
3. Import measures UTF-8 bytes and disagrees with interactive creation.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary tests with ASCII and supplementary Unicode code points.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 95. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 95: Persisted counter-unit decoding fallback for unknown database values

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Persisted counter-unit decoding fallback for unknown database values

Verify that unknown persisted `counterUnit` values map safely to `ROWS` when reading existing Room data, as documented, without crashing or mutating the row merely by reading it.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect entity-to-domain mapper and enum parsing.
2. Test unknown, case-varied, blank, and valid stored values according to actual database constraints.
3. Trace UI labels and later edits.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. `valueOf` crashes on an unknown stored string.
2. Unknown values map inconsistently across list and counter screens.
3. Reading an unknown value writes it back without an explicit user action.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run mapper and Room fixture tests with an unknown stored value.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 96. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 96: Strict rejection of unknown counter units in imported backups

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Strict rejection of unknown counter units in imported backups

Verify that import rejects unknown counter-unit strings instead of applying the database-read fallback intended only for already persisted data.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace JSON decoding, DTO mapping, validation, and error reporting.
2. Test typo, case difference, blank, and future unknown values.
3. Confirm validation completes before replacement mutation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Import converts an unknown unit to ROWS and loses source meaning silently.
2. One decoder is reused for both persisted fallback and strict import.
3. An invalid unit reaches Room and later appears valid.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run negative backup import tests and verify zero database mutation.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 97. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 97: Counter count lower bound of zero

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter count lower bound of zero

Verify that count can never be persisted below zero through decrement, direct set, reset, edit, import, undo, or concurrent stale actions.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace every write and restore path.
2. Check repository validation after UI validation.
3. Test corrupted or invalid history inputs only through realistic seams.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Decrement at zero writes -1.
2. Direct set parses a negative string and persists it.
3. Undo restores an invalid negative previousCount without validation or trusted-history invariant.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary and transaction tests for all count-setting paths.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 98. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 98: Counter count upper bound of 999,999

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter count upper bound of 999,999

Verify that count never exceeds 999,999 through increment, direct set, reset, import, undo, or integer arithmetic.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect comparison order and overflow behavior.
2. Test exactly 999,998, 999,999, and larger input.
3. Check UI enabled state separately from repository enforcement.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Increment at maximum writes 1,000,000 or overflows.
2. Import accepts a larger value that UI cannot display correctly.
3. A direct set clamps silently when rejection is the intended behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run exact upper-bound tests and UI enabled-state tests.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 99. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 99: Start value restricted to zero or one

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Start value restricted to zero or one

Verify that creation, edit, import, reset, and domain mapping permit only start value 0 or 1 and preserve the selected value.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace editor control values and repository validation.
2. Test reset after edit and after process restart.
3. Check backup round-trip.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Start value 2 enters through import and reset later writes 2.
2. Editing unrelated fields resets start value to a default.
3. UI shows 1 while persisted value is 0.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run validation, edit, reset, and backup tests.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 100. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 100: Optional target count presence and range

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Optional target count presence and range

Verify that target count is either absent or within 1 through 999,999 in every write path, with blank editor input mapping deliberately to null.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace string parsing, nullable mapping, repository validation, and import.
2. Test 0, 1, maximum, above maximum, blank, whitespace, and malformed text.
3. Check existing target preservation during unrelated edits.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Target 0 is accepted and progress divides or displays incorrectly.
2. Blank input becomes zero instead of null.
3. Import and editor enforce different maxima.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary and editor parsing tests.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 101. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 101: Optional repeat length presence and range

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Optional repeat length presence and range

Verify that repeat length is either absent or within 2 through 999, consistently across create, edit, import, derived display, and persistence.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test blank, 1, 2, 999, 1000, malformed, and whitespace input.
2. Trace nullable mapping and validation.
3. Check unrelated edits preserve a valid repeat length.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Repeat length 1 causes meaningless boundary feedback.
2. Blank becomes zero and passes a nullable check.
3. Import accepts 1000 while editor rejects it.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run repeat validation and derived-display tests.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 102. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 102: Cross-field count, start, target, and repeat consistency

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Cross-field count, start, target, and repeat consistency

Verify that valid independent values are not rejected by invented cross-field rules and that actual required relationships are enforced consistently where the repository defines them.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `ProjectValidation` for explicit cross-field checks.
2. Test count above target, start above target, and repeat unrelated to target according to current intended behavior.
3. Do not assume target is a hard maximum if the UI only reports progress.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A speculative rule prevents count from exceeding target despite intended continued counting.
2. A valid project cannot be edited because start value and current count differ.
3. One path applies a hidden cross-field constraint absent elsewhere.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run tests that distinguish documented bounds from merely plausible business rules.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 103. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 103: Archived project immutability invariant

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archived project immutability invariant

Verify that archived projects cannot receive count mutations, reset, direct set, or edits that the current design forbids, even through stale UI or direct repository calls.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace archive state checks inside authoritative repository operations.
2. Check which metadata operations, if any, are deliberately allowed.
3. Test concurrent archive and mutation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. UI disables buttons but repository still mutates archived data.
2. A stale Counter ViewModel updates after archive.
3. Undo changes an archived project.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run repository tests for every mutation result on archived projects.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 104. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 104: Timestamp validity and update semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Timestamp validity and update semantics

Verify that `createdAt` and `updatedAt` remain valid epoch milliseconds, creation time is stable, and successful user-visible changes update ordering time according to actual repository policy.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace clock use, mapper copies, import preservation, and all writes.
2. Check no-op operations do not falsely update unless deliberately designed.
3. Use injected or controlled time in tests if already available.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Editing overwrites `createdAt`.
2. A failed or boundary no-op updates `updatedAt` and reorders lists.
3. Milliseconds are confused with seconds in import or display logic.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run deterministic timestamp tests; do not add a clock abstraction unless needed for a confirmed defect.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 105. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 105: Structured repository result and error semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Structured repository result and error semantics

Verify that repositories distinguish success, missing project, archived project, boundary no-op, validation failure, and persistence failure in ways callers handle correctly.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterProject`, `ProjectValidation`, counter-unit and change-reason models, every repository write path, import validation, editor validation, mappers, and focused tests. Repository-level validation remains authoritative.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect sealed results or return types and every consumer.
2. Trace messages, haptics, navigation, and history behavior for each result.
3. Check unexpected exceptions are not mislabeled as normal no-ops.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A missing project is reported as a boundary no-op and no fallback occurs.
2. A successful no-op emits haptic and history.
3. A validation failure is thrown and crashes a ViewModel that expects a result.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run result-mapping tests from repository through ViewModel effects.
2. Use focused pure unit tests and repository tests. Do not change persisted formats, numeric limits, or enum behavior unless a concrete implementation defect and intended contract are unambiguous.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 106. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 8. Room database schema, entities, mappers, DAO queries, and transaction boundaries

## Prompt 106: Database filename and schema version identity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Database filename and schema version identity

Verify that production consistently opens `rowtool.db` at Room schema version 1 and tests intentionally use either that contract or isolated in-memory databases.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect database annotation, builder name, test builders, and documentation.
2. Check no alternate builder creates a second production database.
3. Trace version values into exported schema path.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A typo in the database name makes existing projects appear lost.
2. Production version differs from the committed schema directory.
3. A test uses a simplified database that omits production entities.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run database open and persistence tests after any correction.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 107. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 107: Projects entity table name, columns, and nullability

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Projects entity table name, columns, and nullability

Verify that the `projects` entity exactly represents the persisted fields and nullability needed by the domain and backup contract.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare annotations, Kotlin types, default values, schema JSON, mappers, and DAO SQL.
2. Check column renames or embedded objects.
3. Verify non-null fields cannot receive null through raw fixtures or migration behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Entity annotation and exported schema disagree.
2. A nullable domain field is stored non-null with an ambiguous sentinel.
3. A mapper omits a persisted column.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run KSP, schema, and mapper round-trip tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 108. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 108: Project primary key and identity conflict behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project primary key and identity conflict behavior

Verify that project `id` is the non-null text primary key and that insert or upsert conflict behavior cannot silently overwrite an unrelated project.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `@PrimaryKey`, DAO insert annotations, update methods, and import replacement insertion.
2. Test duplicate IDs in direct DAO calls and validated repository paths.
3. Distinguish deliberate update from accidental replace.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. `REPLACE` deletes and reinserts a project, cascading its history unexpectedly.
2. Duplicate creation overwrites an existing project.
3. Update succeeds for a nonexistent ID and is reported as real edit.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run DAO conflict and history-preservation tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 109. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 109: Projects active/archive ordering index

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Projects active/archive ordering index

Verify that the composite index on `isArchived, updatedAt` exists with the correct column order for actual active and archived ordered queries.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare entity indices, schema JSON, and DAO WHERE/ORDER BY clauses.
2. Inspect query plans only if performance concern is concrete.
3. Check index uniqueness is not accidentally enabled.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Index column order does not support the query and large lists regress.
2. Schema JSON lacks the declared index.
3. A unique index prevents multiple projects sharing timestamps or archive state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run schema verification and representative query tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 110. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 110: Counter-history entity columns and change-reason storage

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter-history entity columns and change-reason storage

Verify that `counter_history` stores ID, project ID, previous count, new count, change reason, and creation time with types and nullability matching repository writes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare entity, schema, DAO projections, and history creation code.
2. Test each change reason.
3. Check auto-generation of history ID.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Previous and new counts are swapped.
2. A reason is stored as an unstable localized string.
3. History ID is not generated and rows collide.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run history insertion and readback tests for every mutation reason.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 111. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 111: History foreign key and cascade deletion

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: History foreign key and cascade deletion

Verify that `counter_history.projectId` references `projects.id` with `ON DELETE CASCADE` and that Room foreign-key enforcement is active in actual database use.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect entity foreign key annotation, schema JSON, builder behavior, and delete tests.
2. Check indexes required for foreign key performance.
3. Test project deletion with existing history.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Deleting a project leaves orphan history.
2. Import replacement fails because old history references cleared projects in the wrong order.
3. A DAO manually deletes without transaction and exposes transient constraint failure.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run foreign-key and cascade tests on a real Room database.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 112. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 112: History projectId and ID index for newest lookup

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: History projectId and ID index for newest lookup

Verify that the index on `projectId, id` supports newest-history retrieval and per-project retention operations with the intended ordering.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare index annotation, schema JSON, and DAO queries.
2. Check ascending versus descending use and LIMIT behavior.
3. Ensure queries always constrain project ID.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Undo retrieves another project’s history.
2. Newest lookup uses ascending order and undoes the oldest change.
3. Retention query scans or deletes globally.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run multi-project ordered history tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 113. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 113: Committed Room schema JSON consistency and reproducibility

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Committed Room schema JSON consistency and reproducibility

Verify that `app/schemas/com.finnvek.rowtool.data.local.RowToolDatabase/1.json` matches the current Room entities, indices, foreign keys, and identity hash.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Regenerate schema through the configured build only when needed and compare semantically.
2. Check the file is tracked and not hand-edited to mask drift.
3. Ensure schema export path is stable across environments.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Source compiles but committed schema is stale.
2. A hand edit makes schema JSON invalid or inconsistent with generated identity.
3. Schema export writes to a local absolute path.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run KSP or Room schema generation and inspect exact diff. Do not accept unexplained schema changes.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 114. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 114: Counter-unit and change-reason storage conversion

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Counter-unit and change-reason storage conversion

Verify that enum-like values are stored as stable machine strings and decoded with the documented strictness differences between Room and import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect converters or mappers rather than assuming Room type converters exist.
2. Test all current values and unknown stored values.
3. Confirm change reasons remain internal and not localized.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Renaming a Kotlin enum silently changes persisted text.
2. Counter unit and change reason use the wrong converter.
3. Unknown history reason crashes undo even though reason is not needed for restoration.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run mapper and database fixture tests before changing any persisted string.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 115. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 115: Domain-to-entity and entity-to-domain round-trip fidelity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Domain-to-entity and entity-to-domain round-trip fidelity

Verify that every project field survives mapping in both directions and that defaults are applied only where the persisted contract explicitly requires them.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare constructors field by field.
2. Use values that differ from defaults to expose omissions.
3. Test archived, target, repeat, timestamps, count, start, and unit together.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Mapper resets archived to false.
2. Repeat or target is dropped.
3. Created and updated timestamps are swapped.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run exact equality round-trip tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 116. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 116: Active-project DAO query filtering and ordering

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Active-project DAO query filtering and ordering

Verify that the active list query filters `isArchived = false`, orders by most recent `updatedAt`, and emits changes reactively as intended.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect SQL, return Flow, tie ordering, and indexes.
2. Test active plus archived rows.
3. Check update, archive, restore, and delete emissions.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Archived projects appear in the active list.
2. Oldest projects appear first.
3. A one-shot list is used where UI expects live updates.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run DAO Flow tests with deterministic timestamps.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 117. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 117: Archived-project DAO query filtering and ordering

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archived-project DAO query filtering and ordering

Verify that archived projects are isolated from active rows and ordered according to the actual UI contract, with reactive updates on restore or delete.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect SQL and UI assumptions.
2. Test mixed rows and equal timestamps.
3. Check expandable section receives a stable list.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Active rows leak into archive section.
2. Restored project remains in archived Flow.
3. Ordering differs between DAO and UI because both sort inconsistently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run archived-list DAO and screen-state tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 118. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 118: Single-project lookup and active-only lookup semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Single-project lookup and active-only lookup semantics

Verify that DAO lookup methods clearly distinguish any project from active-only project when startup, counter, archive, and restore flows need different semantics.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect every lookup query and caller.
2. Test active, archived, and missing IDs.
3. Check null handling and Flow completion.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Startup uses any-project lookup and reopens archived data.
2. Counter edit needs an archived record but active-only lookup returns null unexpectedly.
3. Missing rows are represented by a synthetic default project.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run lookup contract tests and caller tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 119. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 119: DAO insert, update, and conflict strategy correctness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: DAO insert, update, and conflict strategy correctness

Verify that create, edit, archive, restore, and import use DAO operations whose row-count and conflict semantics match repository result handling.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect annotations, SQL updates, return values, and transaction callers.
2. Test nonexistent IDs and duplicate IDs.
3. Check that updates preserve unrelated columns.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. An update reports success despite affecting zero rows.
2. A partial-column update overwrites count with a stale value.
3. Import conflict strategy silently drops one duplicate after validation failed to catch it.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run DAO row-count and field-preservation tests.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 120. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 120: Database transactions, callbacks, and migration boundary

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Database transactions, callbacks, and migration boundary

Verify that multi-step mutations use Room transactions, callbacks do not mutate user data unexpectedly, and schema version 1 has no destructive or imaginary migration path.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/local/`, `RowToolDatabase`, entities, DAOs, mapper functions, Room annotations, the committed version 1 schema JSON, KSP output only as evidence, and Room-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `withTransaction`, `@Transaction`, builder callbacks, and migration registration.
2. Trace rollback behavior on thrown exceptions.
3. Confirm no destructive fallback is configured.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. History inserts commit while count update rolls back or vice versa.
2. An onOpen callback clears or rewrites data.
3. A version mismatch destroys the database.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run rollback tests and database-open tests. Do not add a migration without an actual schema transition.
2. Use Robolectric or instrumented Room tests, `:app:kspDebugKotlin`, schema comparison, and focused query tests. Do not edit generated Kotlin or introduce a migration unless an actual schema change is technically unavoidable.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 121. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
