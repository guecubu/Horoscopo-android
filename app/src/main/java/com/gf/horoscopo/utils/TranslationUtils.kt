package com.gf.horoscopo.utils

import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.gf.horoscopo.R
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

// Extension function on TextView to translate text dynamically using ML Kit.
fun TextView.setTranslatedText(originalText: String, sourceLanguage: String) {
    val context = this.context

    // 1. Detect the currently active app language
    val appLocales = AppCompatDelegate.getApplicationLocales()
    val activeAppLanguage =
        if (!appLocales.isEmpty) appLocales.get(0)?.language else Locale.getDefault().language

    // 2. If the app language matches the source language, set text directly and return
    if (activeAppLanguage == sourceLanguage) {
        this.gravity = Gravity.START or Gravity.TOP
        this.text = originalText
        return
    }

    // Elegant and centered visual feedback with emoji while translating
    this.gravity = Gravity.CENTER
    this.setText(R.string.translating)

    // 3. Configure ML Kit dynamically
    val options = TranslatorOptions.Builder().setSourceLanguage(
            TranslateLanguage.fromLanguageTag(sourceLanguage) ?: TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.fromLanguageTag(activeAppLanguage ?: "en")
                               ?: TranslateLanguage.ENGLISH).build()

    val translator = Translation.getClient(options)

    translator.downloadModelIfNeeded().addOnSuccessListener {
        translator.translate(originalText).addOnSuccessListener { translatedText ->
            this.gravity = Gravity.START or Gravity.TOP
            this.text = translatedText
        }.addOnFailureListener {
            this.gravity = Gravity.START or Gravity.TOP
            this.text = originalText
        }
    }.addOnFailureListener {
        this.gravity = Gravity.START or Gravity.TOP
        this.text = originalText
    }
}
