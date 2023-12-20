package com.arwani.pokedex.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.arwani.pokedex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onChangeValue: (String) -> Unit
) {

    var value by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrow_back",
                        tint = Color.Black
                    )
                }
                OutlinedTextField(
                    value = value,
                    onValueChange = {
                        value = it
                        onChangeValue(value)
                    },
                    modifier = modifier
                        .width(300.dp)
                        .heightIn(min = 25.dp)
                        .focusRequester(focusRequester),
                    singleLine = true,
                    shape = RoundedCornerShape(percent = 30),
                    placeholder = {
                        Text(
                            color = Color.Gray.copy(0.4f),
                            text = "Search ...",
                            fontSize = 16.sp,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Black.copy(0.7f)
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    trailingIcon = {
                        if (value.isNotEmpty()) {
                            IconButton(onClick = { value = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.Black
                                )
                            }
                        }
                    },
                )
            }
        }
        Divider(color = Color.Gray.copy(0.2f))
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigate: Boolean = false,
    navigateUp: () -> Unit = {},
    actionsRow: @Composable RowScope.() -> Unit = {},
    titleColor: Color = Color.Black,
) {
    when {
        canNavigate -> {
            TopAppBar(
                title = { Text(text = title, color = titleColor) },
                modifier = modifier,
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = titleColor
                        )
                    }
                },
            )
        }


        else -> {
            TopAppBar(
                title = { Text(text = title, color = titleColor) },
                modifier = modifier,
                actions = actionsRow
            )
        }
    }

}