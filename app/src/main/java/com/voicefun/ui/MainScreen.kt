package com.voicefun.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voicefun.domain.model.EffectCategory
import com.voicefun.domain.model.VoiceEffect
import com.voicefun.domain.model.VoiceEffects
import com.voicefun.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedEffect by remember { mutableStateOf<VoiceEffect?>(null) }
    var selectedCategory by remember { mutableStateOf(EffectCategory.BASIC) }
    var isRecording by remember { mutableStateOf(false) }
    
    val effects = remember(selectedCategory) {
        VoiceEffects.byCategory(selectedCategory)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "VoiceFun",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(Background, SurfaceVariant)))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎤 让声音更有趣！",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "选择一个变声效果开始体验",
                color = OnSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            CategoryTabs(
                selectedCategory = selectedCategory,
                onSelected = { selectedCategory = it }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(effects) { effect ->
                    EffectCard(
                        effect = effect,
                        isSelected = selectedEffect?.id == effect.id,
                        onClick = { selectedEffect = effect }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            RecordButton(
                isRecording = isRecording,
                enabled = selectedEffect != null,
                onClick = { isRecording = !isRecording }
            )
        }
    }
}

@Composable
fun CategoryTabs(
    selectedCategory: EffectCategory,
    onSelected: (EffectCategory) -> Unit
) {
    val categories = listOf(
        EffectCategory.BASIC to "基础",
        EffectCategory.CHARACTER to "角色",
        EffectCategory.STYLE to "风格",
        EffectCategory.EMOTION to "情绪"
    )
    
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { (category, name) ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onSelected(category) },
                label = { Text(name) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}

@Composable
fun EffectCard(
    effect: VoiceEffect,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Primary else Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = effect.icon,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = effect.name,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else OnSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RecordButton(
    isRecording: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val color = if (isRecording) Secondary else Primary
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                if (enabled) Brush.horizontalGradient(listOf(color, PrimaryDark))
                else Brush.horizontalGradient(listOf(OnSurfaceVariant, OnSurfaceVariant))
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isRecording) "停止录音" else "开始录音",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
    
    if (!enabled) {
        Text(
            text = "请先选择一个变声效果",
            color = OnSurfaceVariant,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
