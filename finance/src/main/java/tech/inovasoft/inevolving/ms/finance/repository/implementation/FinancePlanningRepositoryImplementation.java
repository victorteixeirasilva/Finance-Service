package tech.inovasoft.inevolving.ms.finance.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa.FinancePlanningRepositoryJPA;

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
    public FinancePlanning savePlanningForUser(UUID idUser) {
        var newPlanning = new FinancePlanning(idUser, 0.0);
        return repository.save(newPlanning); // TODO: Refatornar linha para usar o metodo FinancePlanningRepositoryImplementation.savePlanning()
    }

    @Override
    public FinancePlanning findById(UUID idUser) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return null;
    }

    @Override
    public FinancePlanning savePlanning(FinancePlanning planning) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return null;
    }
}
