package com.example.rick_and_morty_vol2.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.rick_and_morty_vol2.data.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBackClick: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val character by viewModel.character.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        character?.let { char ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Large Image
                AsyncImage(
                    model = char.image,
                    contentDescription = char.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = char.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Status Badge
                    val statusColor = when (char.status.lowercase()) {
                        "alive" -> Color.Green
                        "dead" -> Color.Red
                        else -> Color.Gray
                    }

                    Surface(
                        color = statusColor.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = char.status,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = statusColor
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow("Species", char.species)
                    DetailRow("Gender", char.gender)
                    DetailRow("Origin", char.origin.name)
                    DetailRow("Location", char.location.name)
                    DetailRow("Episodes", char.episode.size.toString())
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}