package no.nav.helsearbeidsgiver.validation

import no.nav.helsearbeidsgiver.validation.OrganisasjonsnummerValidator.Companion.tabeller.weights

/**
 * Sjekker at strengen er et gydlig org nummer ifølge:
 * https://www.brreg.no/om-oss/oppgavene-vare/alle-registrene-vare/om-enhetsregisteret/organisasjonsnummeret/
 */
class OrganisasjonsnummerValidator(input: String?) {
    private val asString: String

    init {
        require(input != null)
        asString = input
        require("""\d{9}""".toRegex().matches(asString)) { "Ikke et gyldig organisasjonsnummer: $asString" }
        require(gyldigKontrollsiffer) { "Kontrollsiffer må være gyldige" }
    }

    private val gyldigKontrollsiffer: Boolean
        get() {
            val kontrollsiffer = asString[8].toString().toInt()
            val kalulertKontrollsiffer = checksum(weights, asString)
            if (kalulertKontrollsiffer == 10 || kalulertKontrollsiffer != kontrollsiffer) {
                return false
            }

            return true
        }

    companion object {
        object tabeller {
            val weights: List<Int> = listOf(3, 2, 7, 6, 5, 4, 3, 2)
        }

        fun isValid(asString: String?): Boolean {
            return try {
                OrganisasjonsnummerValidator(asString)
                true
            } catch (t: Throwable) {
                false
            }
        }

        fun checksum(kontrollsifferVekter: List<Int>, orgNr: String): Int {
            var produktsum = 0
            for ((i, m) in kontrollsifferVekter.withIndex()) {
                produktsum += m * orgNr[i].toString().toInt()
            }

            val res = 11 - (produktsum % 11)
            return if (res == 11) 0 else res
        }
    }
}
