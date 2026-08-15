# RowTool release checklist

## Product and legal review

- [ ] Review availability of the application name **RowTool: Row Counter**.
- [ ] Review trademark availability for the RowTool name and identity.
- [ ] Verify the Google Play developer or publisher identity is Finnvek.

## Release build

- [ ] Create or configure a secure Google Play upload signing key outside the repository.
- [ ] Run the final unit tests and Android lint checks.
- [ ] Build the minified release Android App Bundle (AAB).
- [ ] Inspect the release AAB and its merged manifest.
- [ ] Confirm that the release requests no `INTERNET` or broad storage permission and no unnecessary permission.
- [ ] Confirm that the release dependency graph contains no billing or advertising dependency.
- [ ] Test the release build on at least one real Android device.

## Store content and policy

- [ ] Configure the paid Google Play price around EUR 1.99 and review regional price equivalents.
- [ ] Review all supported translations for correctness and layout fit.
- [ ] Capture current Google Play screenshots from the release build.
- [ ] Host `privacy-policy.html` at a stable public URL and add that URL to the Play listing.
- [ ] Complete the Google Play Data safety form using the verified release behavior.
- [ ] Complete the Google Play content-rating questionnaire accurately.
- [ ] Verify the target-audience declaration.
- [ ] Verify the app category.
- [ ] Review the final store title, short description, full description, icon, screenshots, and privacy-policy link.

## Submission and later releases

- [ ] Upload the inspected AAB and complete the remaining Play Console release fields.
- [ ] Increment `versionCode` for every later release before uploading its AAB.
