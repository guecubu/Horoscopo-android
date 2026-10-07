package com.gf.horoscopo.data

import com.gf.horoscopo.R

data class Horoscope(val id: String, val name: Int, val dateRange: Int, val icon: Int) {
    companion object {
        private val horoscopeList: List<Horoscope> = listOf(
                Horoscope("aries", R.string.aries, R.string.aries_date_range,
                        R.drawable.aries_icon),
                Horoscope("taurus", R.string.taurus, R.string.taurus_date_range,
                        R.drawable.taurus_icon),
                Horoscope("gemini", R.string.gemini, R.string.gemini_date_range,
                        R.drawable.gemini_icon),
                Horoscope("cancer", R.string.cancer, R.string.cancer_date_range,
                        R.drawable.cancer_icon),
                Horoscope("leo", R.string.leo, R.string.leo_date_range, R.drawable.leo_icon),
                Horoscope("virgo", R.string.virgo, R.string.virgo_date_range,
                        R.drawable.virgo_icon),
                Horoscope("libra", R.string.libra, R.string.libra_date_range,
                        R.drawable.libra_icon),
                Horoscope("scorpio", R.string.scorpio, R.string.scorpio_date_range,
                        R.drawable.scorpio_icon),
                Horoscope("sagittarius", R.string.sagittarius, R.string.sagittarius_date_range,
                        R.drawable.sagittarius_icon),
                Horoscope("capricorn", R.string.capricorn, R.string.capricorn_date_range,
                        R.drawable.capricorn_icon),
                Horoscope("aquarius", R.string.aquarius, R.string.aquarius_date_range,
                        R.drawable.aquarius_icon),
                Horoscope("pisces", R.string.pisces, R.string.pisces_date_range,
                        R.drawable.pisces_icon),

                )

        fun getAll(): List<Horoscope> {
            return horoscopeList
        }

        fun getById(id: String): Horoscope {
            return horoscopeList.find { it.id == id }!!
        }
    }
}
