package com.voicefun.domain.model

data class VoiceEffect(
    val id: String,
    val name: String,
    val icon: String,
    val pitchShift: Float = 1.0f,
    val category: EffectCategory = EffectCategory.BASIC
)

enum class EffectCategory {
    BASIC, CHARACTER, STYLE, EMOTION
}

object VoiceEffects {
    val all = listOf(
        VoiceEffect("male_to_female", "男变女", "👩", 1.5f, EffectCategory.BASIC),
        VoiceEffect("female_to_male", "女变男", "👨", 0.7f, EffectCategory.BASIC),
        VoiceEffect("child", "童声", "👶", 1.8f, EffectCategory.BASIC),
        VoiceEffect("elderly", "老年", "👴", 0.6f, EffectCategory.BASIC),
        VoiceEffect("robot", "机器人", "🤖", 1.0f, EffectCategory.STYLE),
        VoiceEffect("alien", "外星人", "👽", 0.8f, EffectCategory.STYLE),
        VoiceEffect("monster", "怪物", "👹", 0.5f, EffectCategory.STYLE),
        VoiceEffect("cartoon", "卡通", "🎭", 1.3f, EffectCategory.STYLE),
        VoiceEffect("pirate", "海盗", "🏴‍☠️", 0.8f, EffectCategory.CHARACTER),
        VoiceEffect("ninja", "忍者", "🥷", 0.9f, EffectCategory.CHARACTER),
        VoiceEffect("wizard", "巫师", "🧙", 0.7f, EffectCategory.CHARACTER),
        VoiceEffect("happy", "开心", "😊", 1.2f, EffectCategory.EMOTION),
        VoiceEffect("sad", "悲伤", "😢", 0.8f, EffectCategory.EMOTION),
        VoiceEffect("angry", "愤怒", "😠", 0.7f, EffectCategory.EMOTION)
    )
    
    fun byCategory(category: EffectCategory) = all.filter { it.category == category }
}
