package com.capstone.empoweru.ui.addplaces

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.data.request.CreateLocationRequest
import com.capstone.empoweru.ui.components.AddButton
import com.capstone.empoweru.ui.components.CancelButton
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
    var selectedIndex by remember { mutableStateOf(-1) }

    var showToast by remember { mutableStateOf(false) }
    var errorToastMessage by remember { mutableStateOf("") }

    // Showing Toast
    if (showToast) {
        val selectedPrediction = searchResults.getOrNull(selectedIndex)
        selectedPrediction?.let {
            val toastText = if (errorToastMessage.isNotEmpty()) {
                errorToastMessage
            } else {
                "${selectedPrediction.getPrimaryText(null)} berhasil ditambahkan"
            }
            Toast.makeText(LocalContext.current, toastText, Toast.LENGTH_SHORT).show()
        }
        showToast = false
        errorToastMessage = ""
    }

    LaunchedEffect(searchQueryState, searchResults) {
        selectedIndex = -1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(modifier = Modifier) {
                itemsIndexed(searchResults) { index, prediction ->
                    SuggestionCard(
                        prediction = prediction,
                        isSelected = index == selectedIndex,
                        onClick = {
                            if ( selectedIndex == index) {
                                selectedIndex = -1
                            } else {
                                selectedIndex = index
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CancelButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            AddButton(
                onClick = {
                          if (selectedIndex != -1) {
                              val selectedPrediction = searchResults.getOrNull(selectedIndex)
                              selectedPrediction?.let {
                                  val gmapsID = it.placeId
                                  val predictionText = selectedPrediction.getPrimaryText(null)

                                  // Calling API Request
                                  val request = CreateLocationRequest(gmapsID)
                                  viewModel.createLocation(request) { response ->
                                      if (response.isSuccessful) {
                                          Log.d("API", "API request successful: $response")
                                          showToast = true
                                          navController.popBackStack()
                                      } else {
                                          Log.d("API", "API request failed: $response")
                                          errorToastMessage = "Gagal, ${predictionText} sudah pernah ditambahkan"
                                          showToast = true
                                      }
                                  }
                              }
                          }

                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SuggestionCard(
    prediction: AutocompletePrediction,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 4.dp,
        backgroundColor = if (isSelected) Color.Red else MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = prediction.getPrimaryText(null).toString(),
                style = MaterialTheme.typography.body1.copy(
                    color = if (isSelected) Color.White else MaterialTheme.colors.onSurface
                ),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = prediction.getSecondaryText(null).toString(),
                style = MaterialTheme.typography.body2.copy(
                    color = if (isSelected) Color.White else MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun AddPlaceScreenPreview() {
    EmpowerUTheme {
        AddPlaceScreen(navController = rememberNavController())
    }
}