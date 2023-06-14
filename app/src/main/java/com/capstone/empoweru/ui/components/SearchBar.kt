package com.capstone.empoweru.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.R
import com.capstone.empoweru.ui.theme.Poppins
import com.capstone.empoweru.ui.theme.PoppinsTypography
import kotlin.text.Typography

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var focused by remember { mutableStateOf(false) }
    var hasInput by remember { mutableStateOf(false) }
    var searchQueryState by remember { mutableStateOf("") }

    TextField(
        value = searchQueryState,
        onValueChange = { query ->
            searchQueryState = query
            onSearch(query)
            hasInput = query.isNotEmpty()
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        textStyle = MaterialTheme.typography.body1.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search_UMKM),
                style = PoppinsTypography.body1,
                fontSize = 14.sp
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = if (focused || hasInput) 1.dp else 0.dp,
                color = if (focused || hasInput) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                focused = focusState.isFocused
                if (focused) {
                    focusRequester.requestFocus()
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    EmpowerUTheme {
        SearchBar(onSearch = { })
    }
}