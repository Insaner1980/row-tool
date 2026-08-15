# Google Play Data safety

This document describes the intended Google Play Data safety declarations for RowTool 1.0.0. The declarations must be checked against the final release artifact before submission.

## Declaration position

- **Data collected:** No data is collected.
- **Data shared:** No data is shared with third parties.
- **Accounts:** RowTool does not create or use user accounts.
- **Advertising:** RowTool contains no advertising SDK or advertising identifier access.
- **Analytics:** RowTool contains no analytics or telemetry SDK.
- **Cloud storage:** RowTool does not upload project data or backups.

## Local project data

Project names, counts, targets, repeat settings, archive state, and application preferences are stored only in RowTool's private on-device storage. The application works without an internet connection and does not transmit this data.

Automatic Android backup is disabled. Uninstalling RowTool removes its private local application data through the Android platform.

## User-initiated backup and restore

RowTool creates a JSON backup only after the user selects **Export data** and chooses a destination with Android's system document picker. The file can contain the user's RowTool projects.

RowTool reads a backup only after the user selects **Import data** and chooses a file. Import and export do not upload the file to Finnvek or another service. An exported file is outside RowTool's private storage and remains in its chosen location until the user deletes it.

## Release verification

Before completing the Play Console declaration:

- Inspect the final merged manifest and release artifact and confirm that `INTERNET`, advertising ID, and broad storage permissions are absent.
- Confirm that the final dependency graph contains no analytics, advertising, crash-reporting, cloud, or account SDK.
- Confirm that the published privacy policy matches the shipped behavior.
- Answer all Play Console data-type categories as not collected and not shared only while the verified release continues to match this document.
