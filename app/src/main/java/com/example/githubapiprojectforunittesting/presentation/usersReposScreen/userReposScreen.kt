package com.example.githubapiprojectforunittesting.presentation.usersReposScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserRepositoriesScreen(
    viewModel: UserRepositoriesViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    var repoList by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchField(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            onSearchClicked = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Perform search operation here using searchText
                // For demo, just updating repoList with dummy data
                repoList = listOf(
                    "Repo 1" to "Description 1",
                    "Repo 2" to "Description 2",
                    "Repo 3" to "Description 3",
                    "Repo 1" to "Description 1",
                    "Repo 2" to "Description 2",
                    "Repo 3" to "Description 3",
                    "Repo 1" to "Description 1",
                    "Repo 2" to "Description 2",
                    "Repo 3" to "Description 3"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(60.dp)
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            shape = MaterialTheme.shapes.large,
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "SHOW REPOS",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(repoList) { repo ->
                RepositoryItem(name = repo.first, description = repo.second)
            }
        }
    }
}

@Composable
fun SearchField(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        textStyle = MaterialTheme.typography.body1,
        placeholder = { Text("Input full GitHub username") },
        leadingIcon = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun RepositoryItem(name: String, description: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Black)
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Repo Name: $name",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Description: $description",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchRepoScreen() {
    UserRepositoriesScreen()
}
