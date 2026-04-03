package com.example.rick_and_morty_vol2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rick_and_morty_vol2.data.local.CharacterEntity
import com.example.rick_and_morty_vol2.data.model.Character
import com.example.rick_and_morty_vol2.data.remote.RickAndMortyApi
import com.example.rick_and_morty_vol2.ui.theme.Rick_And_Morty_Vol2Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder

@Composable
fun CharacterCard(
    character: CharacterEntity,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row (
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = character.shortDescription ?: "${character.species} - ${character.status}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if(character.isFavorite) "Remove favorite" else "Add to favorites",
                    tint = if(character.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }

    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun CharacterCardLightPreview() {
    Rick_And_Morty_Vol2Theme(darkTheme = false) {
        CharacterCard(
            character = CharacterEntity(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                isFavorite = false,
                shortDescription = "Human -Alive"
            ),
            onClick = TODO(),
            onFavoriteClick = TODO(),
            modifier = TODO()
        )
    }
}