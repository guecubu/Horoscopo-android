package com.gf.horoscopo

import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

// 🔥 Hacemos una función de extensión sobre TextView.
// Esto significa que cualquier TextView de tu app podrá llamarla directamente.
fun TextView.setTranslatedText(textoOriginalDeApi: String, idiomaOrigenApi: String) {

    // 1. Detectar qué idioma se está renderizando activamente
    val locas = AppCompatDelegate.getApplicationLocales()
    val idiomaActivoDeLaApp =
        if (!locas.isEmpty) locas.get(0)?.language else Locale.getDefault().language

    // 2. Si el idioma de la app coincide con el de la API, pintamos y terminamos
    if (idiomaActivoDeLaApp == idiomaOrigenApi) {
        this.text = textoOriginalDeApi
        return
    }

    // 3. Configurar ML Kit dinámicamente
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(
                TranslateLanguage.fromLanguageTag(idiomaOrigenApi) ?: TranslateLanguage.ENGLISH
        )
        .setTargetLanguage(
                TranslateLanguage.fromLanguageTag(idiomaActivoDeLaApp ?: "en")
                    ?: TranslateLanguage.ENGLISH
        )
        .build()

    val translator = Translation.getClient(options)

    translator.downloadModelIfNeeded()
        .addOnSuccessListener {
            translator.translate(textoOriginalDeApi)
                .addOnSuccessListener { textoTraducido ->
                    this.text = textoTraducido // 'this' se refiere al TextView actual
                }
                .addOnFailureListener { this.text = textoOriginalDeApi }
        }
        .addOnFailureListener { this.text = textoOriginalDeApi }
}
