package com.finnvek.rowtool.ui.screens.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.ProjectNote
import com.finnvek.rowtool.ui.theme.RowToolDimens

@Composable
internal fun NotePreview(
    note: ProjectNote,
    onOpen: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = RowToolDimens.Space16)
            .heightIn(min = 48.dp)
            .testTag("note-preview")
            .clickable(role = Role.Button, onClick = onOpen)
            .padding(vertical = RowToolDimens.Space8),
    ) {
        Text(stringResource(R.string.note_title), style = MaterialTheme.typography.titleSmall)
        Text(note.text, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
        NoteSavedDetails(null, note.savedCount)
    }
}
