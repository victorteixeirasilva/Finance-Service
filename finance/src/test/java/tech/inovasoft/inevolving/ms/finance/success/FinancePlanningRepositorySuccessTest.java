package tech.inovasoft.inevolving.ms.finance.success;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.repository.implementation.FinancePlanningRepositoryImplementation;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa.FinancePlanningRepositoryJPA;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinancePlanningRepositorySuccessTest {

    @Mock
    private FinancePlanningRepositoryJPA jpaRepository;

    @InjectMocks
    private FinancePlanningRepositoryImplementation repository;

    @Test
    public void savePlanningForUserSuccessTest() {
        //Given
        var idUser = UUID.randomUUID();
        var planning = new FinancePlanning(
                idUser,
                0.0
        );

        //When
        when(jpaRepository.save(planning)).thenReturn(planning);
        var result = repository.savePlanningForUser(idUser);

        //Then
        assertNotNull(result);
        assertEquals(planning.getIdUser(), result.getIdUser());
        assertEquals(planning.getWage(), result.getWage());

        verify(jpaRepository).save(planning);
    }

    @Test
    public void findByIdSuccessTest() throws NotFoundFinancePlanning, DataBaseException {
        //Given
        var idUser = UUID.randomUUID();
        var planning = new FinancePlanning(
                idUser,
                1000.0
        );

        //When
        when(jpaRepository.findById(idUser)).thenReturn(Optional.of(planning));
        var result = repository.findById(idUser);

        //Then
        assertNotNull(result);
        assertEquals(planning.getIdUser(), result.getIdUser());
        assertEquals(planning.getWage(), result.getWage());

        verify(jpaRepository).findById(idUser);
    }

    @Test
    public void savePlanning(){
        //Given
        var planning = new FinancePlanning(
                UUID.randomUUID(),
                1000.0
        );

        //When
        when(jpaRepository.save(planning)).thenReturn(planning);
        var result = repository.savePlanning(planning);

        //Then
        assertNotNull(result);
        assertEquals(planning.getIdUser(), result.getIdUser());
        assertEquals(planning.getWage(), result.getWage());

        verify(jpaRepository).save(planning);
    }

}
