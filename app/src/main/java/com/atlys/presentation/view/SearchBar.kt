package com.atlys.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atlys.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClear: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    BackHandler(enabled = isFocused) { focusManager.clearFocus() }

    OutlinedTextField(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        value = searchQuery,
        onValueChange = {
            onValueChange(it.trim())
            onSearch(it.trim())
        },
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.search_movies),
                color = Color.Gray
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        },
        trailingIcon = {
            AnimatedVisibility(searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onClear()
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.Gray
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Gray,
            selectionColors = TextSelectionColors(
                handleColor = Color.Gray,
                backgroundColor = Color.Gray
            )
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onSearch(searchQuery)
            }
        ),
    )
}

@Composable
@Preview
fun SearchPreview() {
    SearchBar(searchQuery = "", onValueChange = {}, onSearch = {}, onClear = {})
}