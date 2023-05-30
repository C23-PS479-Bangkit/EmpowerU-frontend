package com.capstone.empoweru.ui.addplaces

import android.app.Application
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.R
import com.capstone.empoweru.ui.ViewModelFactory
import com.capstone.empoweru.ui.components.navigation.AddButton
import com.capstone.empoweru.ui.components.navigation.CancelButton
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.ui.theme.PoppinsTypography
import com.google.android.libraries.places.api.model.AutocompletePrediction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddPlaceScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AddPlaceViewModel = viewModel(
        factory = PlacesViewModellFactory(
            LocalContext.current.applicationContext as Application
        )
    )
) {
    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    var searchQueryState by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Tambah Tempat",
                    style = MaterialTheme.typography.h1,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                val keyboardController = LocalSoftwareKeyboardController.current

                var focused by remember { mutableStateOf(false) }
                var hasInput by remember { mutableStateOf(false) }

                TextField(
                    value = searchQueryState,
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
                            text = "Cari lokasi baru",
                            style = PoppinsTypography.body1,
                            fontSize = 14.sp
                        )
                    },
                    onValueChange = { query ->
                        searchQueryState = query
                        viewModel.searchPlaces(query)
                        hasInput = query.isNotEmpty()
                    },
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = if (focused || hasInput) 1.dp else 0.dp,
                            color = if (focused || hasInput) Color.Red else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .onFocusChanged { focusState ->
                            focused = focusState.isFocused
                        }
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(modifier = Modifier) {
                    items(searchResults) { prediction ->
                        SuggestionCard(prediction = prediction)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CancelButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            AddButton(
                onClick = { /* TODO: Handle add button click */ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SuggestionCard(prediction: AutocompletePrediction) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = prediction.getPrimaryText(null).toString(),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = prediction.getSecondaryText(null).toString(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPlaceScreenPreview() {
    EmpowerUTheme {
        AddPlaceScreen(navController = rememberNavController())
    }
}