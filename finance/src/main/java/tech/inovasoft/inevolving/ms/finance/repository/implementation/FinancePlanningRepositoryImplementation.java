package tech.inovasoft.inevolving.ms.finance.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa.FinancePlanningRepositoryJPA;

import java.util.Optional;
import java.util.UUID;

public class FinancePlanningRepositoryImplementation implements FinancePlanningRepository {

    @Autowired
    private FinancePlanningRepositoryJPA repository;

    /**
     * @description - Save a new planning for user | Salva um novo planejamento para o usuário.
     * @param idUser - User id | Id do usuário
     * @return - New planning | Novo planejamento
     */
    @Override
    public FinancePlanning savePlanningForUser(UUID idUser) throws DataBaseException {
        var newPlanning = new FinancePlanning(idUser, 0.0);
        return savePlanning(newPlanning);
    }

    /**
     * @description - Find a planning by user id | Busca um planejamento pelo id do usuário
     * @param idUser - User id | Id do usuário
     * @return - Planning | Planejamento
     * @throws DataBaseException - Database error | Erro no banco de dados
     * @throws NotFoundFinancePlanning - Planning not found | Planejamento não encontrado
     */
    @Override
    public FinancePlanning findById(UUID idUser) throws DataBaseException, NotFoundFinancePlanning {
        Optional<FinancePlanning> planningOptional;
        try {
            planningOptional = repository.findById(idUser);
        } catch (Exception e) {
            throw new DataBaseException("(findById)");
        }
        if (planningOptional.isEmpty()) {
            throw new NotFoundFinancePlanning();
        } else {
            return planningOptional.get();
        }
    }

    /**
     * @description - Save a new planning | Salva um novo planejamento
     * @param planning - Planning | Planejamento
     * @return - Planning | Planejamento
     * @throws DataBaseException - Database error | Erro no banco de dados
     */
    @Override
    public FinancePlanning savePlanning(FinancePlanning planning) throws DataBaseException {
        try {
            return repository.save(planning);
        } catch (Exception e) {
            throw new DataBaseException("(save)");
        }
    }
}
