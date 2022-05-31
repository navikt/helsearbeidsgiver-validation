package no.nav.helsearbeidsgiver.validation

import TestData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FoedselsNrValidatorTest {

    @Test
    fun `Skal godta gyldig identitetsnummer`(){
        FoedselsNrValidator(TestData.validIdentitetsnummer)
    }

    @Test
    fun `Skal ikke godta ugyldig identitetsnummer`(){
        assertThrows <IllegalArgumentException>{
            FoedselsNrValidator(TestData.notValidIdentitetsnummer)
        }
    }

    @Test
    fun `Skal ikke godta identitetsnummer med ugyldig checksum`(){
        assertThrows <IllegalArgumentException>{
            FoedselsNrValidator(TestData.notValidIdentitetsnummerInvalidCheckSum2)
        }
    }
}
