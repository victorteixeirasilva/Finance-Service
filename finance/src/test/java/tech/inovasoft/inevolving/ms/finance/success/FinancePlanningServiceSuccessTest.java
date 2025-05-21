package tech.inovasoft.inevolving.ms.finance.success;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.service.FinancePlanningService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinancePlanningServiceSuccessTest {

    @Mock
    private FinancePlanningRepository repository;

    @InjectMocks
    private FinancePlanningService service;

    @Test
    public void addPlanningWhenRegistering(){
        //Given
        var idUser = UUID.randomUUID();
        var expectedFinancePlanning = new FinancePlanning();
        expectedFinancePlanning.setIdUser(idUser);
        expectedFinancePlanning.setWage(0.0);

        //When
        when(repository.savePlanningForUser(idUser)).thenReturn(expectedFinancePlanning);
        var result = service.addPlanningWhenRegistering(idUser);

        //Then
        assertNotNull(result);
        assertEquals(result.getIdUser(), idUser);
        assertEquals(0.0, result.getWage());

        verify(repository).savePlanningForUser(idUser);
    }

    @Test
    public void updateWage(){
        //Given
        var idUser = UUID.randomUUID();
        var expectedFinancePlanning = new FinancePlanning();
        expectedFinancePlanning.setIdUser(idUser);
        expectedFinancePlanning.setWage(4300.0);

        var oldFinancePlanning = new FinancePlanning();
        oldFinancePlanning.setIdUser(idUser);
        oldFinancePlanning.setWage(0.0);

        //When
        when(repository.findById(idUser)).thenReturn(oldFinancePlanning);
        when(repository.savePlanning(any())).thenReturn(expectedFinancePlanning);
        var result = service.updateWage(idUser, 4300.0);

        //Then
        assertNotNull(result);
        assertEquals(result.wage(), expectedFinancePlanning.getWage());

        verify(repository, times(1)).findById(idUser);
        verify(repository, times(1)).savePlanning(any());
    }



}
