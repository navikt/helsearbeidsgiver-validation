package no.nav.helsearbeidsgiver.validation

import no.nav.helsearbeidsgiver.validation.FoedselsNrValidator.Companion.tabeller.kontrollsiffer1
import no.nav.helsearbeidsgiver.validation.FoedselsNrValidator.Companion.tabeller.kontrollsiffer2

class FoedselsNrValidator(input: String?) {
    private val asString: String

    init {
        require(input != null)
        asString = input
        require("""\d{11}""".toRegex().matches(asString)) { "Ikke et gyldig fødselsnummer: $asString" }
        require(gyldigeKontrollsiffer) { "Kontrollsiffer må være gyldige" }
    }

    private val gyldigeKontrollsiffer: Boolean
        get() {
            val ks1 = asString[9].toString().toInt()
            val ks2 = asString[10].toString().toInt()

            val c1 = checksum(kontrollsiffer1, asString)
            if (c1 == 10 || c1 != ks1) {
                return false
            }

            val c2 = checksum(kontrollsiffer2, asString)
            if (c2 == 10 || c2 != ks2) {
                return false
            }
            return true
        }

    companion object {
        object tabeller {
            val kontrollsiffer1: List<Int> = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
            val kontrollsiffer2: List<Int> = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)
        }

        fun isValid(asString: String?): Boolean {
            return try {
                FoedselsNrValidator(asString)
                true
            } catch (t: Throwable) {
                false
            }
        }

        fun checksum(liste: List<Int>, str: String): Int {
            var sum = 0
            for ((i, m) in liste.withIndex()) {
                sum += m * str[i].toString().toInt()
            }

            val res = 11 - (sum % 11)
            return if (res == 11) 0 else res
        }
    }
}
