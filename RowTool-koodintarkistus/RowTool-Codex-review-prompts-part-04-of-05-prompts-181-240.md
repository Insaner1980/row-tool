# 13. JSON backup import parsing, limits, identity checks, and complete pre-mutation validation

## Prompt 181: Five MiB import size limit enforcement strategy

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Five MiB import size limit enforcement strategy

Verify that files over 5 MiB are rejected without first allocating or decoding an unbounded payload and that the limit applies to actual bytes read.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect metadata-size use and streaming byte count.
2. Handle unknown or dishonest provider size.
3. Test just below, exactly at, and just above the limit.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Code trusts reported size and then reads unlimited bytes.
2. Limit is applied to characters after decoding, not bytes.
3. Exactly 5 MiB is rejected because of an off-by-one unless contract says otherwise.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run byte-precise stream tests and assert bounded reads.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 182. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
## Prompt 182: Import byte-count boundary and early termination

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Import byte-count boundary and early termination

Verify that the reader stops as soon as the configured maximum is exceeded and does not continue consuming a malicious or endless provider stream.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect buffer loop, counter type, overflow safety, and close behavior.
2. Use a stream that reports no length and produces more than the limit.
3. Check error classification.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Counter overflows or uses Int unsafely.
2. Entire oversized file is read before rejection.
3. Early rejection leaks the stream.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run custom stream tests that record bytes requested and close calls.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 183. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 183: Malformed JSON rejection and exception containment

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Malformed JSON rejection and exception containment

Verify that syntactically malformed, truncated, or wrong-root JSON is rejected as an import validation error without crash or partial state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test truncated object, invalid escape, trailing garbage, scalar root, and array root.
2. Inspect serialization exception handling.
3. Check user-visible error is concise and non-sensitive.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Decoder exception escapes ViewModel and crashes.
2. Parser accepts trailing malicious content unexpectedly.
3. Malformed input reaches replacement transaction.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run malformed corpus tests with unchanged database assertions.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 184. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 184: Required root fields and type validation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Required root fields and type validation

Verify that every required root field is present with the expected JSON type before a preview can be created.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Remove each field individually and substitute null, string, number, object, or array types.
2. Inspect serializer default values that could mask absence.
3. Trace validation error specificity.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Missing application defaults to RowTool and passes.
2. Projects null becomes an empty list and would wipe data after confirmation.
3. String schema version is coerced silently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run one negative test per required field and wrong type.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 185. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 185: Unknown JSON keys accepted for forward-compatible optional additions

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Unknown JSON keys accepted for forward-compatible optional additions

Verify that unknown root and project keys are ignored as intended without weakening required-field or known-field validation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `ignoreUnknownKeys` or custom parser behavior.
2. Add nested unknown values of different types.
3. Confirm known invalid fields still fail in the same payload.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Unknown optional key causes rejection contrary to contract.
2. Enabling leniency also coerces invalid known types.
3. Unknown key shadows a known field through case-insensitive handling.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run positive unknown-key tests plus combined negative known-field tests.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 186. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 186: Unsupported backup schema version rejection

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Unsupported backup schema version rejection

Verify that any schemaVersion other than supported version 1 is rejected before project mapping or replacement, with no attempt to guess compatibility.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test lower, higher, negative, very large, and wrong-type values.
2. Inspect comparison and error result.
3. Ensure Room schema version does not accidentally govern this check.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Future version 2 is imported with silently dropped fields.
2. Negative version reaches project validation.
3. Unsupported version creates a preview and can wipe current data.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run version rejection tests and assert zero mutation.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 187. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 187: Wrong application identity rejection

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Wrong application identity rejection

Verify that import requires exact machine identity `RowTool` and rejects another application, blank value, translated label, or case variant before mutation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect string comparison and normalization.
2. Test visually similar Unicode and whitespace variants if parser allows them.
3. Compare with export identity.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Case-insensitive match accepts an unintended application.
2. Whitespace trimming changes the machine contract silently.
3. Wrong identity still shows a valid preview.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run identity negative tests and own-export positive round-trip.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 188. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 188: Maximum 1,000 imported projects

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Maximum 1,000 imported projects

Verify that 1,000 projects are accepted when otherwise valid and 1,001 or more are rejected before expensive mapping or database mutation where practical.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect list materialization and count check placement.
2. Test exact boundary and provider-size interaction.
3. Check error report and current database preservation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Off-by-one rejects 1,000.
2. The app validates and inserts the first 1,000 while ignoring the rest.
3. Huge list allocation occurs before both byte and count limits.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run exact project-count boundary tests.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 189. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 189: Blank imported project IDs

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Blank imported project IDs

Verify that empty or blank project IDs are rejected for every item before replacement and are not auto-generated or trimmed into a different identity silently.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test empty, ASCII spaces, and supported Unicode whitespace.
2. Inspect validation order relative to duplicate detection.
3. Check error does not expose whole payload.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Blank ID receives a random replacement and breaks deterministic backup fidelity.
2. Whitespace-only IDs pass and create unusable routes.
3. Only the first project ID is validated.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run per-position blank-ID tests and assert zero mutation.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 190. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 190: Duplicate imported project IDs

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Duplicate imported project IDs

Verify that duplicate IDs anywhere in the payload are detected before replacement, regardless of project order or differing field values.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect set-based detection and exact equality semantics.
2. Test adjacent and distant duplicates, including the last item.
3. Check no DAO conflict strategy substitutes for validation.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Later duplicate silently overwrites earlier data.
2. Detection is case-insensitive despite IDs being exact strings, or vice versa without contract.
3. Only active projects are checked for duplicates.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run duplicate-position tests and unchanged-state assertions.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 191. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 191: Imported project name validation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Imported project name validation

Verify that every imported name passes the same authoritative trimming, blank, and 60-code-point rules as repository writes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test each boundary and Unicode case within a multi-project payload.
2. Inspect whether validated trimmed name or raw name is inserted.
3. Check one invalid project rejects the entire payload.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Import accepts names the app cannot create interactively.
2. Invalid item is dropped while remaining projects replace current data.
3. Validation reports success but insertion later fails.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run shared-validation tests and full-payload rejection tests.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 192. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 192: Imported numeric field validation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Imported numeric field validation

Verify count, startValue, targetCount, and repeatLength bounds and nullability for every imported project before replacement.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test each lower and upper boundary, wrong type, null on required fields, and malformed extreme numbers.
2. Check integer overflow handling in decoder.
3. Use one invalid item late in the list.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Oversized number overflows into an accepted Int.
2. Null required count defaults to zero.
3. Invalid final project is missed after a partial validation loop.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run a matrix of exact numeric negative tests with zero mutation.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 193. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 193: Unknown imported counter-unit rejection

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Unknown imported counter-unit rejection

Verify that only exact supported unit values accepted by the backup contract pass and that the persisted-data fallback to ROWS is not reused.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test valid values and unknown, blank, lower-case, and future values.
2. Inspect DTO-to-domain mapping order.
3. Check entire payload rejection.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Unknown unit maps to ROWS silently.
2. Localized row or round labels are accepted as machine values.
3. Only preview fails later, after partial data mutation.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run strict unit validation tests.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 194. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 194: Imported booleans, timestamps, and nullable field types

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Imported booleans, timestamps, and nullable field types

Verify that `isArchived`, `createdAt`, `updatedAt`, and nullable optional fields have valid types and values required by current repository semantics.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test wrong JSON types, null required fields, negative or implausible timestamps only if existing validation defines them invalid, and optional nulls.
2. Do not invent timestamp range restrictions absent from `ProjectValidation` or repository contract.
3. Trace exact values into preview and replacement.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. String booleans are coerced.
2. Null timestamp defaults to current time and breaks fidelity.
3. Speculative timestamp validation rejects legitimate old backups.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run type-focused tests and preserve source-supported semantics.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 195. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 195: Complete payload validation before any database or preference mutation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Complete payload validation before any database or preference mutation

Verify that parsing, root checks, duplicate detection, and validation of every project finish successfully before Room replacement, history clearing, or preference changes begin.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect content stream reading, `BackupCodec`, DTOs, `ProjectValidation`, import result models, backup tests, and all paths from selected URI to validated preview. No database mutation may occur before the full payload is accepted and confirmed.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace the full call graph and transaction entry point.
2. Place an invalid project at the end of a large valid list.
3. Monitor DAOs and DataStore for zero calls on rejection.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. First valid projects are inserted before a later invalid item fails.
2. History clears during preview generation.
3. Last-active preference changes when validation fails.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run spy or real-database tests asserting no writes for every rejected payload.
2. Use byte-boundary, malformed-input, serializer, validation, and repository tests. Assert database and preferences remain unchanged for every rejected payload. Do not weaken validation to accept ambiguous data.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 196. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 14. Import preview, replacement transaction, last-active resolution, and SAF input handling

## Prompt 196: Preview active and archived project counts

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preview active and archived project counts

Verify that the import preview reports accurate active and archived counts from the fully validated payload and handles zero counts without ambiguity.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect count derivation and preview model.
2. Test all active, all archived, mixed, empty if format permits, and 1,000-project payloads.
3. Check localized display values.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Counts are derived from current database instead of import payload.
2. Archived Boolean is inverted.
3. Large counts are truncated or formatted incorrectly.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run preview model and Compose dialog tests.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 197. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 197: Preview identity bound to the exact validated payload

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preview identity bound to the exact validated payload

Verify that confirmation applies the same validated import snapshot represented by the preview, not a mutable shared object or a newly reread URI that may have changed.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace preview object storage, URI handling, and confirm callback.
2. Check provider content changes between selection and confirmation.
3. Inspect immutability of project lists.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Preview shows file A but confirmation rereads and imports changed file B content.
2. Mutable list changes after preview.
3. A second selection overwrites payload while first dialog remains visible.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run stale-payload and second-selection tests.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 198. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 198: Explicit confirmation before replacement

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Explicit confirmation before replacement

Verify that selecting and validating a file never replaces data until the user confirms the destructive replacement dialog.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace each call to replacement DAO methods.
2. Test preview display, dismissal, Back, outside tap according to dialog policy, and confirm.
3. Check accessibility action labels.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Validation success triggers replacement immediately.
2. Dialog dismissal defaults to confirm.
3. A lifecycle effect repeats confirm without a tap.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run full import UI tests with DAO call assertions.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 199. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 199: Stale preview after configuration change or process recreation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Stale preview after configuration change or process recreation

Verify that a preview survives or is safely dismissed according to actual state ownership without importing stale or unserializable platform objects.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect whether preview lives in ViewModel, saved state, or Route local state.
2. Rotate or recreate before confirm.
3. Check URI and validated model ownership.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Dialog remains but confirm target is null or different.
2. Raw InputStream or Context is retained across recreation.
3. Replacement runs twice after restoration.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run recreation tests around preview and confirm.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 200. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 200: Cancel or dismiss confirmation causes zero mutation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Cancel or dismiss confirmation causes zero mutation

Verify that cancelling an import preview leaves projects, history, settings, and last-active selection unchanged and resets operation state for a later import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Snapshot Room and DataStore before cancellation.
2. Dismiss through every supported route.
3. Retry with a new file.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. History clears on preview cancellation.
2. Last-active preference is updated during preview.
3. Import button remains disabled after dismissal.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run cancellation and retry tests with exact state comparison.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 201. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 201: Replacement transaction clears and inserts projects atomically

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Replacement transaction clears and inserts projects atomically

Verify that confirmed replacement clears existing projects and inserts all validated projects inside one Room transaction visible to observers only as a committed result.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect `withTransaction` or `@Transaction` scope.
2. Observe active and archived Flows around replacement.
3. Test empty valid project list if supported.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Observers see an intermediate empty database and navigate incorrectly.
2. Clear commits before inserts.
3. Only active projects are replaced.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run transaction and Flow-observation tests.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 202. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 202: Rollback on inserted-project failure

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Rollback on inserted-project failure

Verify that any unexpected insertion or constraint failure rolls back the entire replacement, preserving old projects and history.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inject or construct a safe failure after clear but before completion.
2. Inspect exception propagation and result.
3. Check preferences receive no update.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Old data is lost after partial insert failure.
2. Some imported projects remain alongside old data.
3. UI reports success despite rollback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run rollback tests on a real Room database.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 203. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 203: History clearing in the same replacement transaction

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: History clearing in the same replacement transaction

Verify that all old history is removed atomically with project replacement and no history is generated for imported project counts.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect explicit history clear versus cascade from project clear.
2. Test reused project IDs.
3. Check rollback and observers.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Old history attaches to imported project with the same ID.
2. History clearing commits even if insertion fails.
3. Import creates MANUAL_SET history for initial counts.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run reused-ID and rollback history tests.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 204. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 204: Last-active project resolution after committed import

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Last-active project resolution after committed import

Verify that after replacement, last-active selection resolves to a valid imported active project according to the actual policy and never points to an archived or absent row.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect whether an old matching active ID is preserved, newest active is selected, or preference is cleared.
2. Use deterministic imported timestamps.
3. Test no active imported projects.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Preference points to a removed old project.
2. Archived imported project becomes active destination.
3. Resolution runs before transaction commit and reads old data.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run post-import selection tests for every dataset shape.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 205. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 205: Preference update occurs only after database commit

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preference update occurs only after database commit

Verify ordering between replacement transaction completion and last-active DataStore write so preferences never advertise data not yet committed.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace suspend sequence and error boundaries.
2. Delay transaction and preference fake independently.
3. Observe app-level startup or navigation state.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Preference changes before Room commit and UI opens missing project.
2. A transaction rollback leaves new imported ID in DataStore.
3. Concurrent collector sees impossible cross-store state permanently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ordering tests with controlled suspensions.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 206. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 206: Preference failure after successful replacement

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Preference failure after successful replacement

Verify that a failed last-active preference update is reported as a partial post-commit problem while the valid imported database remains committed and startup repair can recover.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inject DataStore failure after confirmed replacement.
2. Check current UI navigation and next cold startup.
3. Ensure no unsafe manual rollback is attempted.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Code restores old database from memory after preference failure.
2. UI says import entirely failed and invites duplicate retry without explaining committed data.
3. Exception crashes after successful destructive change.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run partial-failure tests and verify committed imported state.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 207. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 207: ContentResolver input stream null and open failure

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: ContentResolver input stream null and open failure

Verify that a provider returning null or throwing while opening the selected URI yields a clean import failure with no preview or mutation.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect null handling and exception scope.
2. Use fake resolver/provider behavior.
3. Check operation state and retry.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Null stream is force-unwrapped.
2. Stale preview from a previous import remains confirmable.
3. Failure is treated as malformed JSON after reading nonexistent bytes.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run null and throwing stream tests.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 208. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 208: URI grant lifetime and no broad storage permission

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: URI grant lifetime and no broad storage permission

Verify that import and export rely only on the Storage Access Framework grant needed for the immediate operation and do not request broad storage permission or retain unnecessary long-term access.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect ActivityResult contracts, flags, manifest, and any `takePersistableUriPermission` call.
2. Determine whether persistent access is actually required.
3. Check merged manifest.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Broad READ or MANAGE_EXTERNAL_STORAGE permission is added.
2. Persistable permission is taken without a future-use need and never released.
3. Operation fails because a temporary grant is used after its lifetime through delayed reread.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Inspect merged manifest and run launcher-flow tests. Do not add permissions.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 209. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 209: Repeated import selection, concurrent operations, and reentrancy

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Repeated import selection, concurrent operations, and reentrancy

Verify that a second import selection cannot race with parsing, preview, confirmation, or replacement from the first in a way that mixes state or performs two replacements.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace in-progress flags and launcher availability.
2. Delay parsing and select another URI if UI permits.
3. Tap confirm repeatedly.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Preview counts from one file and projects from another.
2. Two replacement transactions run sequentially from one visible confirmation.
3. Operation state never returns to idle.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run controlled reentrancy tests.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 210. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 210: Import error messages and privacy-preserving logs

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Import error messages and privacy-preserving logs

Verify that malformed, oversized, wrong-app, unsupported-version, provider, and database errors are distinguished enough for the user without logging backup contents, project names, URIs, or sensitive paths unnecessarily.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect Settings Route and ViewModel, import preview models and dialogs, `BackupRepository`, Room replacement transactions, DataStore updates, ContentResolver input streams, and full import tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect result-to-message mapping and Log calls.
2. Check localization placeholders and exception interpolation.
3. Separate cancellation from error.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Whole JSON payload is logged.
2. Content URI or project names appear in production error text without need.
3. Every failure becomes the same misleading malformed-file message.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run message mapping tests and inspect release logging configuration.
2. Use focused UI, repository, transaction, and stream tests. Replacement is destructive only after explicit confirmation and must remain atomic in Room. A failed preference update does not roll back committed replacement.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 211. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 15. Projects screen state, list rendering, editor validation, and project actions

## Prompt 211: Active project list reactive updates and ordering

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Active project list reactive updates and ordering

Verify that the Projects screen renders the authoritative active Flow in DAO order and updates correctly after create, edit, count change, archive, restore, delete, and import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace DAO Flow through ViewModel mapping and lifecycle-aware collection.
2. Check whether UI performs a second inconsistent sort.
3. Use stable timestamps and multiple rows.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Active list remains stale after a repository commit.
2. UI reverses the intended newest-first ordering.
3. A transient initial empty state replaces loaded projects.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel Flow and Compose list-update tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 212. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 212: Archived section expansion state and data changes

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archived section expansion state and data changes

Verify that the archived section appears only according to actual UI design, expands and collapses reliably, and reacts to archive, restore, delete, and import changes without targeting stale items.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect local versus ViewModel ownership of expansion state.
2. Change archived list while expanded and collapsed.
3. Recreate the screen if persistence is intended or verify reset if not.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Expansion tap triggers a project action.
2. Restored rows remain visible in archived section.
3. Empty archived section retains an inaccessible expanded container.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run isolated section interaction tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 213. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 213: Projects empty, active-only, archived-only, and mixed states

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Projects empty, active-only, archived-only, and mixed states

Verify that empty-state copy and available actions are correct for no projects, archived-only data, active-only data, and mixed data.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect state derivation rather than just screenshots.
2. Check create action remains reachable.
3. Verify archived-only state does not claim there are no saved projects if that would contradict design.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Empty state overlays a nonempty archived list.
2. Create button disappears when there are no active projects.
3. Loading state is mislabeled as empty.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose tests for all dataset shapes.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 214. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 214: Lazy list item keys and identity preservation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Lazy list item keys and identity preservation

Verify that active and archived list items use stable unique project IDs as keys so local interaction state does not jump when ordering changes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect LazyColumn or LazyListScope keys.
2. Reorder rows through timestamp updates.
3. Open menus or confirmations before reordering.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Index keys attach an open menu to another project.
2. Active and archived sections reuse colliding key scopes incorrectly.
3. Duplicate IDs from invalid data crash without being prevented earlier.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run reorder interaction tests with distinct IDs.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 215. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 215: Create-dialog initial values and clean reopening

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Create-dialog initial values and clean reopening

Verify that opening create initializes name, unit, start, target, repeat, errors, and submission state to deliberate defaults every time.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Open, type, dismiss, and reopen.
2. Compare initial values with repository defaults.
3. Check state retained during ordinary recomposition but not across a new create request unless intended.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Previous cancelled input leaks into a new project.
2. Edit values become create defaults.
3. Error text remains after a fresh open.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run create-dialog reopen tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 216. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 216: Edit-dialog field population from current project

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Edit-dialog field population from current project

Verify that edit initializes every editable field from the selected current project and does not substitute defaults for nullable or nondefault values.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Use a project with ROUNDS, start 0, target, repeat, large count, and archived state according to allowed edit flow.
2. Trace selected ID to latest state.
3. Open after a concurrent update.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Repeat or target appears blank despite persisted value.
2. Dialog edits a stale copy and overwrites newer metadata.
3. Unit or start defaults silently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run exact field-population tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 217. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 217: Shared ProjectEditorDialog create and edit state isolation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Shared ProjectEditorDialog create and edit state isolation

Verify that the shared dialog handles create and edit modes without cross-mode labels, callbacks, validation, or retained state.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect keys used for remember or rememberSaveable.
2. Switch from edit to create and between two edited project IDs.
3. Check confirm button text and cancel behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Editing project B shows project A values.
2. Create calls the edit callback with a stale ID.
3. Mode change keeps an incompatible validation error.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run mode-switch tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 218. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 218: Project name input and 60-code-point feedback

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project name input and 60-code-point feedback

Verify that editor feedback uses the same blank and 60-code-point rules as `ProjectValidation`, including supplementary characters, without allowing UI validation to be the only guard.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect input transformations, counter display, and error conditions.
2. Test 60 and 61 code points with emoji.
3. Compare repository rejection result mapping.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. UI counts UTF-16 units and rejects valid names.
2. Confirm remains enabled for a repository-invalid name.
3. Text is truncated silently instead of reporting according to design.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run UI boundary tests plus repository tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 219. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 219: Numeric text parsing without crash or locale ambiguity

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Numeric text parsing without crash or locale ambiguity

Verify that start, target, repeat, and direct numeric editor inputs handle blank, digits, pasted whitespace, signs, very large values, and nonnumeric text safely.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect keyboard options and actual parser.
2. Test overflow beyond Int range.
3. Determine whether localized digits are supported from code rather than assuming.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. `toInt()` crashes on pasted large text.
2. Malformed input becomes zero and passes.
3. A locale decimal separator is accepted then truncated unpredictably.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run parser and Compose input tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 220. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 220: Optional target and repeat blank-to-null semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Optional target and repeat blank-to-null semantics

Verify that blank optional fields map to null, valid numbers map exactly, and clearing an existing value truly removes it after successful edit.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect UI model conversion and repository arguments.
2. Test whitespace-only and clear-then-confirm.
3. Reopen persisted project.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Blank becomes zero and fails later.
2. Cleared target retains old persisted value because null is treated as no update.
3. Create and edit interpret blank differently.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run create/edit persistence tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 221. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 221: Rows versus rounds selector state and accessibility

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Rows versus rounds selector state and accessibility

Verify that unit selection exposes exactly ROWS and ROUNDS, displays localized labels, preserves current selection, and has clear selected semantics.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect selector component, state callbacks, and content descriptions or roles.
2. Test keyboard or accessibility action if supported.
3. Reopen edit after save.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Visual selection changes but submitted unit does not.
2. Both options expose selected state.
3. Machine enum names leak untranslated.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose semantics and persistence tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 222. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 222: Successful creation and exactly-once auto-open

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Successful creation and exactly-once auto-open

Verify that one accepted create operation closes the dialog, inserts one project, and navigates exactly once to that exact ID.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Delay repository completion and tap confirm repeatedly.
2. Trace loading and enabled state.
3. Check validation and persistence failure paths.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Double tap creates duplicates.
2. Dialog closes and navigation happens before commit.
3. Failure leaves a phantom project in list state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run ViewModel effect and full create-flow tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 223. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 223: Opening a project from a stale list row

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Opening a project from a stale list row

Verify that tapping a project that was deleted, archived, or replaced between render and action is handled safely by authoritative lookup or destination fallback.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Capture a row action, mutate data, then invoke it.
2. Trace preference write and navigation.
3. Check no wrong row is selected by index.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Stale row opens a missing counter and remains blank.
2. Preference stores an invalid ID.
3. Another project is opened because list position changed.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run stale-action Compose and navigation tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 224. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 224: Archive and restore actions from Projects

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archive and restore actions from Projects

Verify that archive and restore target the correct ID, honor repository results, update lists, and do not emit success for missing or already transitioned rows.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect overflow menu callback identity.
2. Test rapid repeated action and list reorder.
3. Check last-active clearing only for archive.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Menu action applies to a different row.
2. Restore is offered or executed for active project incorrectly.
3. Failure still shows success and moves the row locally.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run action identity, result, and list-update tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 225. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 225: Delete confirmation and stale project handling from Projects

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Delete confirmation and stale project handling from Projects

Verify that deletion requires explicit confirmation, displays the intended project identity safely, and handles a target removed before confirm with no unrelated deletion.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/projects/`, `ProjectEditorDialog`, shared action models and menu items, ProjectsViewModel, repository calls, localized resources, and isolated or full-activity Compose tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect dialog state, dismiss, confirm, and repository result.
2. Reorder or remove the target before confirming.
3. Check history cascade and last-active preference.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Delete runs on menu tap before confirmation.
2. Confirmation stores list index.
3. Missing-target confirmation deletes all or another project.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run full delete-confirmation tests.
2. Use focused ViewModel and Compose tests with controlled project Flows. Validate exact IDs and results, not only visible text. Preserve repository-level validation and do not move business rules solely into UI.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 226. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

# 16. Counter screen state, mutations, dialogs, progress, repeat display, and rapid interaction

## Prompt 226: Authoritative project Flow collection for the open counter

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Authoritative project Flow collection for the open counter

Verify that Counter displays and reacts to the current Room-backed project for its route ID, including edits, count changes, archive, delete, and replacement import.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace ID into repository Flow and ViewModel state.
2. Switch route IDs and ensure old collection cancels.
3. Check loading, missing, and archived representations.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Counter remains subscribed to previous project after navigation.
2. Local optimistic count diverges from Room permanently.
3. Missing row leaves stale previous project visible.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run project-switch and live-update tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 227. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 227: Project name and row or round label correctness

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Project name and row or round label correctness

Verify that the counter header shows the current project name and the correct localized singular or generic row/round label for its unit without stale values.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Edit name and unit while counter is open.
2. Check every locale resource reference and state mapping.
3. Test long names and accessibility reading.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Unit changes but label remains rows.
2. Name edit appears only after reopening.
3. Raw enum text is shown.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run live edit and localization tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 228. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 228: Plus control enabled state at upper bound

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Plus control enabled state at upper bound

Verify that plus is enabled for active counts below 999,999, disabled or semantically unavailable at the maximum, and still guarded by the repository.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect UI predicate, semantics, callback, and repository result.
2. Test 999,998 and 999,999.
3. Race a final increment with stale enabled state.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Plus appears enabled at maximum and emits false success.
2. It disables one count too early.
3. Disabled semantics exist but click callback still mutates.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary Compose and repository tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 229. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 229: Minus control enabled state at zero

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Minus control enabled state at zero

Verify that minus is enabled above zero, disabled at zero, and cannot persist negative values through stale UI or rapid taps.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect predicate and semantics.
2. Test zero, one, and concurrent taps.
3. Check archived state composition with boundary state.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Minus disables at one.
2. Rapid taps from one produce negative count.
3. Disabled button still provides haptic feedback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run boundary and rapid-tap tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 230. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 230: Undo availability and enabled semantics

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Undo availability and enabled semantics

Verify that undo availability reflects persisted history for the current active project and updates after mutations, undo, delete, import, archive, restore, and restart.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Trace history availability Flow or query.
2. Switch projects with different histories.
3. Check repository no-history guard.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Undo remains enabled after history is consumed.
2. History from prior project enables undo.
3. Restart loses enabled state despite persisted history.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run multi-project and restart undo UI tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 231. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 231: Archived counter read-only state

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archived counter read-only state

Verify that an archived project, if displayable, clearly prevents every mutation and destructive edit not intended for archived data while offering only deliberate navigation or restore options.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect all buttons, count click, menus, dialogs, and ViewModel guards.
2. Archive while screen is open.
3. Attempt stale callbacks captured before archive.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Plus and minus disable but set-count remains active.
2. Overflow reset mutates archived project.
3. Read-only state relies only on color.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run archived-state semantics and stale-callback tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 232. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 232: Direct set-count dialog initialization and parsing

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Direct set-count dialog initialization and parsing

Verify that tapping the accessible count opens a set-count dialog initialized from the latest persisted count and safely parses valid range input.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Open after a concurrent count update.
2. Test blank, malformed, negative, maximum, overflow, same value, cancel, and confirm.
3. Check keyboard and focus behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Dialog initializes with stale count.
2. Overflow input crashes.
3. Cancel submits due to shared callback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run dialog parser and persistence tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 233. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 233: Reset confirmation and current start value

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Reset confirmation and current start value

Verify that reset requires explicit confirmation, describes the intended action, and uses the latest persisted startValue 0 or 1 at commit time.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Edit start value while confirmation is open if possible.
2. Test already-at-start and archived transition.
3. Inspect history and effects.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Reset occurs before confirm.
2. Dialog displays one start value while repository uses another without safe revalidation.
3. Already-at-start adds history and success feedback.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run stale-confirmation and reset tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 234. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 234: Editing project metadata from Counter

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Editing project metadata from Counter

Verify that Counter edit opens the shared editor with current metadata, saves through authoritative validation, and updates the open screen without replacing count or history.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Change name, unit, start, target, and repeat separately.
2. Race a count mutation with edit.
3. Check validation errors and archived transition.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Edit saves a stale full entity and loses recent count.
2. Unit update does not refresh labels.
3. Dialog closes and reports success after repository rejection.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run concurrent edit and live-state tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 235. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 235: Archiving from Counter and destination transition

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Archiving from Counter and destination transition

Verify that archive action targets the open ID, honors success or failure, clears matching last-active preference when possible, and leaves the counter destination safely.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect confirmation requirement if any from actual design.
2. Test double tap, missing row, and preference failure.
3. Check back stack.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Navigation occurs before archive commit.
2. Old counter remains under Projects and can be revisited mutable.
3. Wrong project is archived after route change.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run repository, effect, and full navigation tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 236. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 236: Deleting from Counter and destination transition

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Deleting from Counter and destination transition

Verify that delete requires confirmation, removes the exact project and history, and navigates away once without leaving stale dialogs or routes.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Open confirmation, change underlying state, and confirm.
2. Test missing target and preference failure.
3. Check Back behavior.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Delete callback uses current route after dialog target changed.
2. Deletion success leaves set-count dialog open on Projects.
3. Back returns to deleted counter.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run full delete flow tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 237. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 237: Target progress calculation and clamping

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Target progress calculation and clamping

Verify that optional target progress is derived safely from current count and target, handles count beyond target, and feeds a UI component within its accepted numeric range.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect integer-to-float conversion, division, null target, and maximum values.
2. Test count 0, target 1, equal, above, and maximum.
3. Check target is not treated as a hard count cap.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Division by zero from invalid or transient target.
2. Progress above 1 crashes or renders incorrectly because it is not clamped where required.
3. Integer division shows zero until target.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run pure calculation and Compose rendering tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 238. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 238: Repeat position calculation

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Repeat position calculation

Verify that repeat position for repeatLength 2 through 999 follows the intended one-based row or round presentation at count boundaries and start-value variations.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Derive expected examples from current product behavior and tests.
2. Test count 0, 1, repeatLength, multiples, and large count.
3. Check rows versus rounds wording.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Modulo result displays position 0 instead of final position.
2. Start value incorrectly shifts repeat position.
3. Large count overflows intermediate arithmetic.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run table-driven repeat-position tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 239. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 239: Completed-repeat count and combined target or repeat display

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Completed-repeat count and combined target or repeat display

Verify completed repeats and any combined target/repeat information remain internally consistent, localized, and hidden when optional fields are absent.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Test before first completion, exact boundary, after boundary, and high counts.
2. Inspect conditional UI branches and formatting.
3. Check target and repeat together.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Completed count increments one row early.
2. Null repeat displays placeholder data.
3. Target progress replaces repeat info unexpectedly under one state.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run derived-state and Compose visibility tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 240. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```

## Prompt 240: Rapid taps, press animation, and exactly-once mutation dispatch

```text
ROLE AND BOUNDED OBJECTIVE

You are auditing and, only where justified by verified evidence, minimally correcting the current RowTool repository. Execute this prompt as one fully self-contained audit area. Do not answer from generic Android knowledge and do not assess an imagined implementation.

Audit area: Rapid taps, press animation, and exactly-once mutation dispatch

Verify that rapid plus, minus, and undo taps produce one repository request per accepted click, preserve order, and do not multiply calls because of pointer, semantics, or animation handlers.

PROJECT-SPECIFIC SOURCE AND SCOPE GUIDANCE

Inspect `ui/screens/counter/`, `CounterViewModel`, `CounterScreen.kt`, `CounterImageButton.kt`, dialog and menu state, repository results, haptic effects, localized strings, and counter-focused tests.

This prompt is tightly limited to the audit area named above. Inspect adjacent code only when it is necessary to establish the complete real call path, state path, persistence path, lifecycle, platform behavior, configuration, validation, or safeguard relevant to this area. Do not turn this prompt into a broad refactor or a duplicate review of unrelated concerns.

PROJECT-SPECIFIC INSPECTION PROCEDURE

1. Inspect clickable ownership in `CounterImageButton`, press-state effects, and nested modifiers.
2. Test fast sequential and simultaneous-style input.
3. Check disabled transition at boundaries.
4. Inspect the existing tests, configuration, guards, error paths, and all callers or consumers materially relevant to this exact topic. Do not treat a single warning, line, or isolated function as sufficient evidence.
5. Reconcile the current implementation with `PROJECT.md` only after verifying the actual repository. If they disagree and intent remains ambiguous, preserve working behavior and report the discrepancy without changing `PROJECT.md`.
6. Before any correction, state the concrete runtime, data-integrity, accessibility, privacy, security, build, test, or handoff failure mechanism that has been verified. If no mechanism is verified, make no change.

CONCRETE FAILURE MODES TO VERIFY OR RULE OUT

1. Nested click handlers dispatch twice.
2. Press animation cancellation triggers a click.
3. UI drops ordinary taps because an overaggressive debounce was added.
4. A generic best-practice concern is reported as a defect even though the actual RowTool implementation, version range, validation, lifecycle, transaction, or test already prevents it.

EVIDENCE THRESHOLD AND CORRECTION BOUNDARY

A correction is justified only when the current repository proves a real defect or a directly violated RowTool requirement in this bounded area. Verify the complete relevant path and show why existing validation, Room transactions, the repository Mutex, DataStore behavior, lifecycle-aware collection, version constraints, Android platform behavior, tests, or another safeguard does not already prevent the issue. Treat a merely possible concern as unverified unless it can be reproduced, derived conclusively from the code and configuration, or demonstrated by a focused safe test. Preserve persisted data formats, Room schema version 1, backup schema version 1, public interfaces, UI behavior, localization, accessibility, privacy boundaries, and supported Android compatibility unless changing one is strictly necessary to correct a confirmed defect.

TARGETED VALIDATION EXPECTATIONS

1. Run Compose interaction tests with a counting fake and repository concurrency tests.
2. Use focused ViewModel, Compose semantics, and full-activity persistence tests. Repository data remains authoritative. Test boundaries, archived and missing states, process restart, and rapid taps.
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

After this prompt's inspection, any justified minimal correction, targeted validation, file-by-file diff review, and result record are complete, continue automatically to Prompt 241. Do not stop to request approval for an ordinary safe repository-internal correction.

If execution limits prevent continuing through the document, stop only at a clear numbered boundary. State the last prompt completed in full, the prompt that remains incomplete or is next, every file changed so far, and every validation command actually run with its result. Do not skip prompts, compress unprocessed prompts into a claim of completion, or state that the whole audit is complete.
```
