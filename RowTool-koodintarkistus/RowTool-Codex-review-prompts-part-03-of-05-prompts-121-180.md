# 9. CounterRepository serialization, mutation atomicity, boundaries, and concurrent callers

## Prompt 121: Single effective Mutex instance for all counter mutations

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Single effective Mutex instance for all counter mutations

Verify that every production counter mutation for a database is serialized through the same effective repository Mutex rather than one Mutex per call or multiple repository instances.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Mutex declaration, repository lifetime, AppContainer wiring, and alternate constructors.
2. Trace all mutation entry points.
3. Test concurrent callers through the production repository instance.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A local Mutex is created inside each function and provides no cross-call protection.
2. Two CounterRepository instances mutate the same database concurrently.
3. One mutation path bypasses the Mutex.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run concurrent mutation tests and construction tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 122. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
## Prompt 122: Increment read-check-write transaction

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Increment read-check-write transaction

Verify that increment reads the current authoritative row, checks active and upper-bound state, writes the new count and timestamp, and adds history atomically.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace the exact transaction and result returned.
2. Test maximum, missing, archived, and ordinary values.
3. Check the history previous and new values.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Count update commits without history.
2. A stale caller-provided count overwrites a newer value.
3. Increment at maximum still updates timestamp or history.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run focused Room transaction tests for all increment outcomes.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 123. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 123: Decrement read-check-write transaction

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Decrement read-check-write transaction

Verify that decrement uses the persisted count, refuses values below zero, and atomically records only accepted changes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test zero, one, missing, archived, and concurrent increment/decrement.
2. Inspect comparison and transaction ordering.
3. Check result-to-haptic mapping.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Decrement at zero writes -1 or history.
2. Two concurrent decrements from one produce -1.
3. A rejected decrement is reported as accepted.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary and concurrent decrement tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 124. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 124: Direct manual-set transaction and same-value handling

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Direct manual-set transaction and same-value handling

Verify that direct count setting validates range, checks current project state, and handles setting the current value according to the actual no-op contract without false history.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace parsed input to repository call.
2. Test same value, bounds, missing, archived, and concurrent mutation.
3. Inspect MANUAL_SET history reason.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Same-value set appends useless history and enables undo to the same value.
2. Out-of-range value reaches SQL.
3. A stale current count is trusted instead of re-read.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run direct-set repository and ViewModel tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 125. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 125: Reset transaction to persisted start value

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Reset transaction to persisted start value

Verify that reset uses the project’s current persisted startValue, is confirmed by UI, and atomically adds RESET history only when it changes count.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test start 0 and 1, already-at-start, archived, missing, and edit-then-reset.
2. Trace confirmation separately from repository enforcement.
3. Inspect timestamps and history.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Reset uses a stale ViewModel start value after edit.
2. Already-reset project adds duplicate history.
3. Reset changes count before confirmation because UI and repository calls are misordered.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run reset transaction and confirmation-flow tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 126. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 126: No-op boundary mutations and history suppression

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: No-op boundary mutations and history suppression

Verify that increment at maximum, decrement at zero, same-value manual set, and same-value reset do not create history or misleading state changes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect result types, timestamp writes, history DAO calls, haptics, and messages.
2. Test each no-op independently.
3. Check concurrent transition to the boundary.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. No-op updates `updatedAt` and reorders projects.
2. No-op emits accepted haptic.
3. No-op history consumes retention capacity and alters undo behavior.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run no-op assertions against count, timestamp, history count, and effects.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 127. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 127: Archived-project guard inside each mutation transaction

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archived-project guard inside each mutation transaction

Verify that archive state is checked from the authoritative row inside the same serialized transaction for increment, decrement, set, reset, and undo.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect every method rather than relying on a shared UI guard.
2. Test archive racing with a queued mutation.
3. Check result specificity.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Mutation reads active state before waiting for Mutex and applies after archive.
2. Undo bypasses the archived check.
3. One operation checks a stale domain object passed by UI.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run archive-versus-mutation concurrency tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 128. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 128: Missing-project guard and structured result

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Missing-project guard and structured result

Verify that deletion or import replacement racing with a queued mutation yields a missing-project result with no history or crash.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace null lookup handling in every operation.
2. Test deletion before and during queued calls.
3. Check ViewModel fallback and navigation effects.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Null is force-unwrapped.
2. History is inserted for a deleted foreign key and fails the transaction unexpectedly.
3. Missing is mislabeled as archived or boundary.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run missing-project repository and presentation tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 129. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 129: Lost-update prevention with stale UI state

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Lost-update prevention with stale UI state

Verify that mutation calculations always use the current Room value rather than a count supplied by potentially stale UI state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect repository signatures and transaction reads.
2. Simulate two ViewModels or delayed UI state.
3. Check direct set remains explicit replacement while increment/decrement are relative operations.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Two rapid increments both read 10 outside the transaction and write 11.
2. A resumed screen decrements an obsolete value and loses newer changes.
3. History previousCount does not match actual database state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run concurrent and stale-caller tests with exact final count and history sequence.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 130. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 130: History row field correctness for every accepted mutation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: History row field correctness for every accepted mutation

Verify that accepted mutations write previousCount, newCount, reason, projectId, and createdAt matching the committed count change.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect construction for INCREMENT, DECREMENT, MANUAL_SET, and RESET.
2. Use nondefault values to expose swapped fields.
3. Check transaction time and project identity.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Manual set records INCREMENT reason.
2. Previous and new counts are reversed.
3. History row belongs to the wrong project after rapid navigation.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run per-reason exact history tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 131. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 131: Project `updatedAt` change within mutation atomicity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project `updatedAt` change within mutation atomicity

Verify that accepted count changes update project ordering time in the same transaction and rejected changes do not.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect update SQL or entity copy.
2. Test rollback after history failure.
3. Check deterministic ordering after concurrent mutations.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Count changes but `updatedAt` does not, so recent ordering is stale.
2. Timestamp commits while count transaction rolls back.
3. Boundary no-op reorders the list.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run transaction rollback and timestamp assertions.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 132. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 132: Mutation result mapping from DAO outcomes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Mutation result mapping from DAO outcomes

Verify that affected-row counts, transaction results, and validation failures map to the correct structured repository result rather than assumed success.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect every return branch and impossible-state handling.
2. Test zero-row update after deletion.
3. Trace result consumers.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. DAO affects zero rows but repository returns success.
2. A constraint exception becomes a boundary result.
3. A successful write is reported missing because a follow-up read races.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run result contract tests with controlled DAO outcomes or real Room races.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 133. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 133: Concurrent increment burst exactness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Concurrent increment burst exactness

Verify that a burst of accepted increments produces exactly one count increase and history row per accepted call up to the upper bound.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Launch many coroutines against one project.
2. Assert final count, ordered history, cap interaction, and result counts.
3. Repeat to detect nondeterminism.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Calls are lost under contention.
2. History order or previousCount chain has gaps.
3. Some calls return success after the maximum is reached.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run deterministic stress-style repository tests within reasonable limits.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 134. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 134: Mutation racing with archive, delete, or import replacement

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Mutation racing with archive, delete, or import replacement

Verify that serialized operations have a coherent commit order when counter changes race with project archive, deletion, or whole-database replacement import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace which operations share the same transaction and Mutex boundaries.
2. Test both possible acquisition orders.
3. Check preference and navigation behavior separately.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A mutation resurrects a deleted or replaced project.
2. History survives replacement unexpectedly.
3. Archive state is overwritten by a stale entity update.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run targeted concurrency tests for each cross-operation pair that can actually overlap.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 135. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 135: Cancellation and exception rollback during mutation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Cancellation and exception rollback during mutation

Verify that cancellation or an exception inside a Room transaction cannot leave count, timestamp, and history partially committed and that Mutex release is guaranteed.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `CounterRepository.kt`, relevant DAOs, transaction bodies, the repository Mutex, mutation result types, history creation, timestamp updates, callers, and concurrency tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `withLock`, `withTransaction`, catch blocks, and finally behavior.
2. Inject failure between logical steps only through safe test seams.
3. Attempt a later mutation to confirm lock release.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. History commits but count does not.
2. Mutex remains locked after exception.
3. Cancellation is swallowed and operation reports success.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run rollback and post-failure liveness tests.
2. Use deterministic repository and Room tests with concurrent coroutines. Validate actual accepted, rejected, missing, archived, and boundary results. Do not replace the Mutex or transaction model without a proven defect.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 136. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 10. Undo history, retention, archive and delete semantics, and project ordering

## Prompt 136: Newest history row selection for undo

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Newest history row selection for undo

Verify that undo selects the newest history row for the current project by the stable history identity and not merely by a potentially equal timestamp.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect DAO ORDER BY and LIMIT.
2. Create interleaved history for multiple projects and equal timestamps.
3. Trace selected row into restoration.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Undo chooses the oldest change.
2. History from another project is selected.
3. Timestamp ties make selection nondeterministic.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ordered multi-project history tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 137. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 137: Undo atomically restores previous count and consumes history

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Undo atomically restores previous count and consumes history

Verify that one undo restores `previousCount`, updates the project as intended, and deletes exactly the consumed newest history row in one transaction.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect update and delete order.
2. Test rollback on either step failure.
3. Check returned state and timestamps.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. History row is deleted but count restore fails.
2. Count restores but row remains, so repeated undo repeats the same change.
3. Undo appends another history row and creates oscillation.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run atomic undo and rollback tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 138. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 138: Persistent multi-step undo order

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Persistent multi-step undo order

Verify that repeated undo traverses accepted changes in strict reverse commit order across increment, decrement, manual set, and reset.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Create a mixed mutation sequence with distinct counts.
2. Restart repository or database connection before undo.
3. Assert each intermediate count and remaining history.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Undo order groups by reason rather than commit.
2. A reset skips earlier changes.
3. History is held only in memory and disappears after restart.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run mixed-sequence persistence tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 139. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 139: Undo with no available history

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Undo with no available history

Verify that undo on an active project with no history returns the intended no-op result and makes no project, timestamp, or haptic change.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect empty-query handling.
2. Test new project, fully consumed history, and history cleared by import.
3. Trace UI enabled state and repository enforcement.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Null history crashes.
2. No-history undo updates timestamp.
3. UI says undo succeeded and haptics fire.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run repository and UI no-history tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 140. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 140: Undo after process restart and database reopen

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Undo after process restart and database reopen

Verify that persisted history remains usable after process reconstruction and that no in-memory stack is required for correctness.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Close and reopen the Room database or recreate the application in tests.
2. Check newest ordering and project lookup.
3. Confirm DataStore is irrelevant to history restoration.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Undo button disables after restart despite stored rows.
2. History mapper loses reason or count values on reopen.
3. A repository cache points to a closed database.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run database reopen and full-activity persistence tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 141. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 141: Newest 100 history rows retention rule

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Newest 100 history rows retention rule

Verify that accepted changes retain exactly the newest 100 history rows per project and discard only older rows after successful mutation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect cleanup SQL, threshold arithmetic, and transaction placement.
2. Test 99, 100, 101, and larger sequences.
3. Assert exact retained IDs and counts.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Only 99 rows are retained due to off-by-one.
2. Newest row is deleted instead of oldest.
3. Cleanup runs before insertion and temporarily retains 101 persistently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary retention tests with deterministic IDs.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 142. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 142: History retention isolation per project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: History retention isolation per project

Verify that exceeding 100 changes for one project never deletes history belonging to another project.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Interleave two project histories with one above the limit.
2. Inspect DELETE subquery constraints.
3. Assert both row counts and undo sequences.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Global LIMIT cleanup removes another project’s oldest rows.
2. A missing projectId predicate affects all history.
3. Shared timestamp ordering causes cross-project deletion.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run multi-project retention tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 143. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 143: Project deletion cascades all and only its history

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project deletion cascades all and only its history

Verify that deleting a project removes every associated history row through the foreign key while preserving every other project and its history.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test projects with zero, one, and many rows.
2. Inspect cascade and explicit deletion code for duplication.
3. Check transaction and returned result.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Orphan rows remain.
2. Explicit broad delete clears all history.
3. Deletion of a missing project clears unrelated history anyway.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run cascade isolation tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 144. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 144: Replacement import clears old history before new project set becomes visible

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Replacement import clears old history before new project set becomes visible

Verify that confirmed replacement removes all pre-import undo history as an intentional part of the same database transaction that replaces projects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect replacement order and transaction.
2. Observe database only before and after commit, not intermediate state.
3. Test rollback on inserted-project failure.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Imported projects inherit history from matching old IDs.
2. History clears although project replacement rolls back.
3. UI briefly observes new projects with old undo availability.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run replacement atomicity and Flow observation tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 145. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 145: Archive preserves history while blocking mutation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archive preserves history while blocking mutation

Verify that archiving does not delete valid undo history unless the actual repository explicitly requires it, while archived projects remain nonmutable.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Check archive SQL and cascade behavior.
2. Archive and restore a project with history.
3. Test undo while archived and after restore.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Archive clears history unexpectedly.
2. Archived project can undo.
3. Restore recreates project with a new ID and loses history association.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run archive/restore history tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 146. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 146: Restore re-enables mutation without altering count or history

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Restore re-enables mutation without altering count or history

Verify that restoring an archived project changes only intended archive and timestamp state, preserves identity, count, settings fields, and history, and allows later mutations.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare every field before and after restore.
2. Check list ordering and last-active behavior.
3. Perform one mutation and undo after restore.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Restore resets count or start value.
2. History is duplicated or missing.
3. Project remains blocked because stale archived state is cached.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run field-preservation and post-restore mutation tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 147. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 147: Rename preserves counter state and history

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Rename preserves counter state and history

Verify that renaming changes the validated name and intended update timestamp without overwriting count, archive state, target, repeat, unit, start, identity, or history.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect partial versus full entity update.
2. Simulate stale UI state while count changes before rename commits.
3. Assert history sequence unchanged.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Full stale entity save loses a recent increment.
2. Rename generates a new project and cascades history loss.
3. Unrelated optional fields reset to defaults.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run concurrent rename/count and field-preservation tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 148. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 148: Editing target, repeat, unit, or start preserves unrelated state

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Editing target, repeat, unit, or start preserves unrelated state

Verify that project editing updates only submitted validated metadata while preserving current count, identity, archive state, timestamps as intended, and existing history.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace editor model to repository update.
2. Use values different from defaults for every unrelated field.
3. Test count mutation racing with edit.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Stale edit overwrites a newer count.
2. Changing target resets repeat or unit.
3. Changing start immediately resets current count without explicit reset.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run metadata edit preservation and concurrency tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 149. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 149: Delete confirmation target identity under list changes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Delete confirmation target identity under list changes

Verify that deletion confirmation retains the exact intended project ID even if lists reorder, the archive section changes, or state emits before confirmation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect dialog state type and LazyColumn keying.
2. Open confirmation, mutate ordering through another operation, then confirm in a test.
3. Handle already-deleted target safely.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Dialog stores list index and deletes a different project.
2. Recomposition replaces target with current selection.
3. Stale confirmation crashes or deletes broadly.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose and repository stale-target tests.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 150. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 150: UpdatedAt ordering after create, edit, count, archive, and restore

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: UpdatedAt ordering after create, edit, count, archive, and restore

Verify that project ordering changes only for operations that deliberately update `updatedAt`, with deterministic active and archived list results.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect history DAOs and queries, undo transaction logic, archive, restore, edit, delete, replacement import, timestamp updates, list ordering, and persistence tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Catalog timestamp behavior for each operation.
2. Test no-op and failed operations separately.
3. Check tie handling and list recomposition.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A no-op jumps a project to the top.
2. Restore remains buried because timestamp is not updated despite intended recency.
3. Create and edit use seconds while count uses milliseconds.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run deterministic ordering tests covering every operation.
2. Use real Room transaction tests with multiple projects and deterministic timestamps. Preserve the newest 100 history rows per project and the rule that undo consumes rather than appends history.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 151. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 11. Preferences DataStore keys, settings propagation, and startup consistency repair

## Prompt 151: Preferences file name and single key namespace

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preferences file name and single key namespace

Verify that production uses the exact DataStore name `rowtool_preferences` and one coherent key namespace without accidental renames that make existing settings appear lost.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect creation sites, key declarations, tests, and any migration code.
2. Search for legacy or duplicate literal names.
3. Confirm test stores are isolated intentionally.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. One code path writes to a differently named file.
2. A key is renamed without migration and silently resets user choice.
3. Two declarations use the same key name for different types.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run DataStore persistence and reopen tests after a correction.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 152. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 152: Theme mode key, default, and safe enum decoding

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Theme mode key, default, and safe enum decoding

Verify that absent or unknown persisted theme values resolve safely to SYSTEM while valid SYSTEM, LIGHT, and DARK values round-trip exactly.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect storage representation, parser, writer, and state mapping.
2. Test absent, valid, blank, unknown, and case-varied values according to actual contract.
3. Check no read-side write occurs unless deliberate migration exists.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Unknown text crashes with `valueOf`.
2. Absent key defaults to LIGHT instead of SYSTEM.
3. Writer stores localized labels that decoder cannot read after locale change.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run preference codec and theme-state tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 153. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 153: Haptic feedback key and default true

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Haptic feedback key and default true

Verify that `haptic_feedback_enabled` is a Boolean preference with default true and no string or inverse-semantic mismatch.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect key type, read default, writer, Settings state, and Counter consumer.
2. Test absence and both explicit values.
3. Check a failed write does not falsely update persistent state.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Default is false on first install despite intended true.
2. UI toggle is inverted relative to stored value.
3. A type mismatch throws when reading an old value.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run DataStore and Counter feedback-state tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 154. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 154: Keep-screen-awake key and default true

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Keep-screen-awake key and default true

Verify that `keep_screen_awake` defaults to true, round-trips as Boolean, and is interpreted consistently by Settings and the Counter window effect.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace key declaration through ViewModel state to window flag.
2. Test absent, true, false, and runtime changes.
3. Check no other screen consumes it accidentally.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. First install does not keep the counter awake.
2. False is read as true due to fallback placement.
3. Settings displays one value while the Activity applies another.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run preference and window-flag propagation tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 155. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 155: Last-active project key absence and blank-value handling

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Last-active project key absence and blank-value handling

Verify that no preference is the canonical default for `last_active_project_id` and that blank or malformed stored text cannot become a valid destination without Room validation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect key type, clear operation, read mapping, and startup selection.
2. Test absent, blank, valid, missing, and archived IDs.
3. Check imported IDs according to actual validation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Blank becomes a `counter/` route.
2. Clear writes an empty string that other code treats as present.
3. A malformed value crashes route encoding.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run startup selection tests across stored-value states.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 156. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 156: DataStore singleton and file corruption behavior

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: DataStore singleton and file corruption behavior

Verify that a single DataStore instance owns the preferences file and that corruption or read exceptions follow the existing deliberate fallback or error policy without wiping unrelated app data.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect corruption handler presence, catch blocks, and Flow error recovery.
2. Distinguish IOException from programming errors.
3. Check UI state and later writes after a read failure.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Duplicate instances throw at runtime.
2. A broad catch replaces preferences for any exception.
3. Corruption leaves settings Flow permanently silent and splash hanging.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use focused corrupted-file or throwing-data-source tests only through existing safe test seams.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 157. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 157: Theme write persistence and reactive propagation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Theme write persistence and reactive propagation

Verify that selecting a theme writes exactly once, emits the persisted value, updates Compose and system bars, and survives recreation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace Settings action to repository or DataStore update.
2. Check in-flight state and repeated same-value selection.
3. Observe app-level theme collector.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. UI optimistically changes then reverts silently after failed write.
2. Same-value selection causes unnecessary repeated writes and effects.
3. System bars retain old appearance.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel, DataStore reopen, and Activity theme tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 158. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 158: Haptic toggle persistence and Counter propagation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Haptic toggle persistence and Counter propagation

Verify that changing haptic feedback updates the active counter behavior without requiring process restart and persists across later sessions.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace Flow collection into Counter state or Route.
2. Test toggle while counter is open.
3. Check accepted and boundary actions after change.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Counter captures the initial value and ignores updates.
2. Toggle state updates UI only, not DataStore.
3. A failed write still disables haptics in memory permanently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Settings-to-Counter propagation tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 159. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 159: Keep-awake toggle persistence and immediate window update

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Keep-awake toggle persistence and immediate window update

Verify that changing keep-screen-awake while Counter is open immediately adds or clears the window flag and persists for later counters.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace Settings write, active collector, and DisposableEffect or Activity API.
2. Navigate between screens after each value.
3. Check activity recreation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Flag changes only after reopening the app.
2. Clearing preference does not clear current flag.
3. Flag is applied while Settings is visible.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run runtime toggle and navigation tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 160. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 160: Writing last-active project on successful open

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Writing last-active project on successful open

Verify that opening a valid active project records its ID only after the operation is accepted and does not write stale or invalid selections.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace list tap, repository validation if any, navigation event, and preference write ordering.
2. Test rapid taps on two projects.
3. Check write failure behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Preference records project A while navigation opens project B.
2. Missing or archived project is stored as last active.
3. Write failure prevents otherwise valid navigation without a deliberate requirement.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run rapid-selection and failure-order tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 161. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 161: Clearing matching last-active ID on archive

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Clearing matching last-active ID on archive

Verify that archiving a project clears the preference only when it equals the archived ID and does not clear a different valid last-active project.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect conditional clear logic and database-before-preference ordering.
2. Test matching, nonmatching, absent, and failed write.
3. Check startup repair afterward.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Archiving any project clears last active globally.
2. Matching ID remains and startup reopens archived data.
3. Preference failure rolls back a committed archive.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run archive plus preference-state tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 162. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 162: Clearing matching last-active ID on delete

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Clearing matching last-active ID on delete

Verify that deletion conditionally clears the matching preference after database commit while preserving a different last-active ID.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace deletion result, preference read or atomic edit, and error handling.
2. Test matching, nonmatching, already-deleted, and failure cases.
3. Check process restart.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Deleting project B clears last-active project A.
2. Deleted ID remains and no startup repair occurs.
3. Database delete is reported failed solely because preference clear failed.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run delete and restart-selection tests.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 163. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 163: Database commit followed by preference-write failure

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Database commit followed by preference-write failure

Verify that archive, delete, import replacement, or valid project opening report database and preference outcomes accurately when the database commit succeeds but DataStore write fails.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect sequencing and exception boundaries.
2. Inject a preference failure after confirmed database commit.
3. Trace user message and startup repair.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Committed database work is falsely retried and duplicates effects.
2. UI claims the database operation failed although it succeeded.
3. Code attempts an unsafe manual database rollback after preference failure.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run failure-injection tests that assert committed Room state and reported partial failure.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 164. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 164: Startup repair of stale last-active preference

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Startup repair of stale last-active preference

Verify that startup handles missing or archived stored IDs by selecting the newest active project or Projects, and updates or clears the preference only according to actual policy.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace validation, fallback selection, and any repair write.
2. Test repair-write failure without blocking safe navigation.
3. Check archived-only and empty databases.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Startup loops because repair write fails.
2. A stale ID remains the start destination despite fallback state.
3. Repair overwrites a newer concurrent user selection.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run startup repair tests with controlled DataStore failures.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 165. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 165: Preferences Flow exceptions, fallback state, and user messaging

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preferences Flow exceptions, fallback state, and user messaging

Verify that read and write failures do not crash collectors, leave permanent loading, or claim a setting was saved when it was not.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `data/preferences/`, preference keys and enum decoding, `RowToolAppViewModel`, Settings and Counter ViewModels, Activity or Route consumers, and DataStore tests. Settings are intentionally excluded from JSON backup.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect Flow `catch`, write result handling, and transient effects.
2. Keep CancellationException distinct.
3. Check sensitive path or data details are not surfaced.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A read IOException cancels app-level theme collection forever.
2. Write failure is swallowed and toggle remains visually committed.
3. Generic catch masks a programmer error as a recoverable preference issue.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run throwing-fake ViewModel tests and use realistic severity.
2. Use focused DataStore and ViewModel tests with temporary stores and controlled failures. Preserve defaults SYSTEM, true, true, and absent last-active ID. Do not add settings to project backup.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 166. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 12. JSON backup export schema, snapshot integrity, encoding, and document creation

## Prompt 166: Backup root field contract

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Backup root field contract

Verify that exported UTF-8 JSON root contains the required `schemaVersion`, `application`, `exportedAt`, and `projects` fields with the intended types and no accidental wrapper mismatch.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect serializable root model, codec configuration, and tests.
2. Parse actual exported bytes rather than trusting object construction.
3. Check required field names are stable machine keys, not localized text.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A field is renamed in code and old import can no longer parse it.
2. Root is emitted as an array rather than object.
3. A nullable required field is omitted.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run exact export-structure and round-trip tests.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 167. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 167: Exported schemaVersion value 1

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Exported schemaVersion value 1

Verify that export always writes backup `schemaVersion: 1` from one deliberate source and does not confuse it with Room schema version through accidental coupling.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect constants, model defaults, and encoder call sites.
2. Check tests parse the emitted number.
3. Trace future-version comments only as nonbinding context.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Export emits 0 or a string value.
2. A Room database version change would silently alter backup format.
3. Multiple constants drift.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run schema-version export and import acceptance tests.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 168. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 168: Exported application identity literal RowTool

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Exported application identity literal RowTool

Verify that export writes the stable machine identity `RowTool`, independent of locale, launcher label, publisher, or Android application ID.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace the value source and serializer.
2. Test under at least one nondefault locale if resources are involved.
3. Compare import requirement.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Export writes a translated app label.
2. Publisher name is written instead of application identity.
3. Case mismatch makes the app reject its own backup.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run identity round-trip tests.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 169. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 169: ExportedAt epoch-millisecond semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: ExportedAt epoch-millisecond semantics

Verify that `exportedAt` is a valid epoch-millisecond timestamp captured for the export event and serialized without overflow or locale formatting.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect clock source and type.
2. Test deterministic clock if an existing seam exists.
3. Confirm decoder handles the emitted numeric range.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Seconds are labeled or interpreted as milliseconds.
2. Timestamp is formatted as localized text and import rejects it.
3. A 32-bit conversion overflows.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run serializer tests with a controlled timestamp where practical.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 170. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 170: Exported project field completeness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Exported project field completeness

Verify that every persisted project field needed for replacement import is exported exactly once: identity, name, unit, count, start, optional target and repeat, archive state, and timestamps.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Compare entity, domain, backup DTO, mapper, and parsed JSON field by field.
2. Use nondefault values.
3. Check null optional fields and archived projects.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Repeat length or archive state is omitted.
2. CreatedAt and updatedAt are swapped.
3. Default values mask missing serialized fields in tests.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run field-complete exact round-trip tests.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 171. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 171: Intentional exclusion of counter undo history

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Intentional exclusion of counter undo history

Verify that no `counter_history` rows, reasons, IDs, or derived undo state enter the backup payload, filename, or logs.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect export queries, models, serializers, and tests with populated history.
2. Search emitted JSON for history-specific fields.
3. Confirm import replacement clears old history separately.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. History is exported accidentally and exposes internal event detail.
2. A project DTO embeds a history list.
3. Tests use projects without history and fail to detect leakage.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Export a database with history and assert its absence from parsed JSON.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 172. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 172: Intentional exclusion of DataStore settings

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Intentional exclusion of DataStore settings

Verify that theme, haptic, keep-awake, and last-active preferences are not read into or serialized by project backup.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect backup dependencies and model fields.
2. Export with nondefault settings.
3. Confirm import does not overwrite current settings except resolving last active after replacement.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Backup includes private preferences contrary to contract.
2. Import resets theme and haptics.
3. Last-active ID is serialized and points to a project absent in another backup version.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run export/import tests with nondefault settings.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 173. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 173: UTF-8 encoding and Unicode project-name fidelity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: UTF-8 encoding and Unicode project-name fidelity

Verify that export writes valid UTF-8 and preserves all valid Unicode project-name code points without platform-default encoding dependence.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect OutputStreamWriter or byte conversion charset.
2. Test supplementary, combining, and non-Latin names.
3. Decode bytes explicitly as UTF-8.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Windows default encoding corrupts names.
2. A surrogate pair is split during truncation or write.
3. A BOM or invalid byte sequence breaks the importer.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run byte-level UTF-8 round-trip tests.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 174. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 174: JSON syntax, escaping, and finite numeric output

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: JSON syntax, escaping, and finite numeric output

Verify that serializer output correctly escapes quotes, backslashes, control characters, and Unicode while all numeric values remain valid finite JSON numbers.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Use names containing special characters and line breaks if validation permits them.
2. Parse emitted output with the production decoder.
3. Inspect custom serializers if present.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. A project name breaks JSON syntax.
2. Manual string concatenation permits injection into the document.
3. A custom numeric conversion emits localized commas.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run production encode-then-decode tests; do not replace a working serializer speculatively.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 175. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 175: Consistent export snapshot under concurrent mutations

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Consistent export snapshot under concurrent mutations

Verify that the exported project set represents one coherent Room snapshot rather than a mixture observed while counts, archive state, or projects change concurrently.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect DAO query and transaction boundaries.
2. Determine whether one list query is already an atomic SQLite snapshot.
3. Test a controlled concurrent mutation only if a mixed snapshot mechanism is plausible.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Count and timestamp come from separate reads and disagree.
2. Project list changes during per-row export and duplicates or omits entries.
3. A broad transaction blocks UI unnecessarily without evidence.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use a concurrency test that proves or rules out an actual mixed-read path before changing it.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 176. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 176: Export ordering determinism and semantic irrelevance

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Export ordering determinism and semantic irrelevance

Verify that project ordering in the backup is either deliberately deterministic or correctly treated as semantically irrelevant by import and tests.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect export query ORDER BY and equality assertions.
2. Check active and archived projects.
3. Do not add sorting solely for cosmetic diff quality unless a real requirement exists.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Nondeterministic order makes tests flaky or backup previews inconsistent.
2. Import assumes active projects precede archived projects without validation.
3. A sorting change alters last-active resolution unexpectedly.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run repeated export tests only if order has a consumer or observable instability.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 177. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 177: Export size and memory behavior with up to 1,000 projects

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Export size and memory behavior with up to 1,000 projects

Verify that exporting the largest valid project set stays within reasonable memory and does not create unbounded copies or quadratic string building.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect serializer and stream path.
2. Estimate bounded maximum from field limits and project count.
3. Measure only if code structure suggests a realistic issue.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Repeated string concatenation becomes quadratic.
2. Entire JSON is copied through several large buffers unnecessarily.
3. A theoretical small allocation is labeled a critical performance bug without evidence.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Use a bounded large-fixture test or allocation evidence when a correction is justified.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 178. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 178: Create-document contract, MIME type, and suggested filename

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Create-document contract, MIME type, and suggested filename

Verify that export uses the system document picker with a suitable JSON MIME type and safe suggested filename without assuming direct filesystem access.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect ActivityResult contract, launcher ownership, filename generation, and localized or stable extension.
2. Check cancellation and returned URI handling.
3. Verify no storage permission request.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Filename lacks `.json` or contains invalid path separators.
2. Wrong contract attempts to open an existing file rather than create one.
3. Broad storage access bypasses SAF.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run isolated Route launcher tests or instrumentation if contract behavior changed.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 179. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 179: Output stream acquisition, write, flush, and close

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Output stream acquisition, write, flush, and close

Verify that a null or failing ContentResolver output stream is handled, bytes are fully written, and all resources close on success, failure, and cancellation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `use` blocks, buffering, flush assumptions, and exception boundaries.
2. Test null stream and mid-write failure through fakes where possible.
3. Check no stream is retained in ViewModel state.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Null stream is force-unwrapped.
2. Exception leaks an open descriptor.
3. UI reports success before write completes.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run fake ContentResolver or stream tests and assert close behavior.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 180. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 180: Export cancellation, failure messaging, and no false success

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Export cancellation, failure messaging, and no false success

Verify that picker cancellation makes no file write and is not shown as an error, while real failures produce accurate non-sensitive feedback exactly once.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt`, project DAOs and mappers, Settings Route launchers, string resources, and backup export tests. Export includes project data but intentionally excludes undo history and DataStore settings.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace ActivityResult null URI, repository exceptions, effects, and operation state reset.
2. Recreate the Activity during selection.
3. Check retry after failure.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Cancellation shows a scary failure Snackbar.
2. Write error still emits success.
3. Loading or disabled state remains stuck after failure.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run cancellation, failure, and retry ViewModel or Route tests.
2. Use focused serializer, repository, byte-stream, and Storage Access Framework tests. Verify exact bytes or parsed structure where useful. Do not add network storage or broad permissions.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 181. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
