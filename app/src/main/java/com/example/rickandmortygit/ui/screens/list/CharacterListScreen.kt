package com.example.rick_and_morty_vol2.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.rick_and_morty_vol2.data.local.CharacterEntity
import com.example.rick_and_morty_vol2.ui.components.CharacterCard
import androidx.compose.runtime.collectAsState
import com.example.rick_and_morty_vol2.ui.theme.Rick_And_Morty_Vol2Theme
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit = {}
) {
    val pagingItems: LazyPagingItems<CharacterEntity> =
        viewModel.charactersFlow.collectAsLazyPagingItems()

    val searchQuery by viewModel.searchQuery.collectAsState()

    Rick_And_Morty_Vol2Theme {
        Scaffold(
            topBar =  {
                TopAppBar(
                    title = { Text("Rick and Morty") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {viewModel.onSearchQueryChanged(it)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    label = { Text("Search Characters...") },
                    singleLine = true
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.id },
                        contentType = pagingItems.itemContentType { "character" }
                    ) { index ->
                        val character = pagingItems[index]
                        if (character != null) {
                            CharacterCard(
                                character = character,
                                onClick = { onCharacterClick(character.id) },
                                onFavoriteClick = { viewModel.toggleFavorite(character)},
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }

                    }

                    when (pagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                Text(
                                    text = "Error loading more characters",
                                    modifier = Modifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}