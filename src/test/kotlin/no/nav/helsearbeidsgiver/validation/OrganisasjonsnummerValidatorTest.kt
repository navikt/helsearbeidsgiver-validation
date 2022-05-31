package no.nav.helsearbeidsgiver.validation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class OrganisasjonsnummerValidatorTest {

    @Test
    fun `Skal godta gyldig organisasjonsnummer`(){
        OrganisasjonsnummerValidator(TestData.validOrgNr)
    }

    @Test
    fun `Skal ikke godta ugyldig organisasjonsnummer`(){
        assertThrows <IllegalArgumentException>{
            OrganisasjonsnummerValidator(TestData.notValidOrgNr)
        }
    }
}
