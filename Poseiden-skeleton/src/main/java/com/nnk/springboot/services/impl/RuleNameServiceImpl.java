package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.exceptions.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service gérant les opérations liées aux noms de règle (RuleName).
 */
@Service
@AllArgsConstructor
@Slf4j
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    /**
     * Récupère la liste de tous les noms de règle.
     *
     * @return La liste de tous les noms de règle.
     */
    @Override
    public List<RuleName> findAllRuleNames() {
        log.info("Find all Rule Name");
        return ruleNameRepository.findAll();
    }

    /**
     * Récupère un nom de règle par son identifiant.
     *
     * @param ruleNameId L'identifiant du nom de règle à récupérer.
     * @return Une option contenant le nom de règle correspondant s'il existe.
     */
    @Override
    public Optional<RuleName> findRuleNameById(final int ruleNameId) {
        log.info("Find rule Name by id : " + ruleNameId);
        return ruleNameRepository.findById(ruleNameId);
    }

    /**
     * Crée un nouveau nom de règle.
     *
     * @param ruleName Le nom de règle à créer.
     * @return Le nom de règle créé.
     */
    @Override
    public RuleName createRuleName(final RuleName ruleName) {
        log.info("Creating rule Name : " + ruleName);
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Met à jour un nom de règle existant.
     *
     * @param id L'identifiant du nom de règle à mettre à jour.
     * @param ruleName Le nom de règle mis à jour.
     * @return Le nom de règle mis à jour.
     * @throws NotFoundException si le nom de règle n'est pas trouvé.
     */
    @Override
    public RuleName updateRuleName(int id, RuleName ruleName) {
        log.info("Updating rule Name : " + ruleName);
        RuleName ruleNameFound = ruleNameRepository.findById(id).orElseThrow(() -> new NotFoundException("Rule name does not exist"));
        ruleNameFound.setName(ruleName.getName());
        ruleNameFound.setDescription(ruleName.getDescription());
        ruleNameFound.setJson(ruleName.getJson());
        ruleNameFound.setTemplate(ruleName.getTemplate());
        ruleNameFound.setSqlStr(ruleName.getSqlStr());
        ruleNameFound.setSqlPart(ruleName.getSqlPart());

        return ruleNameRepository.saveAndFlush(ruleNameFound);
    }

    /**
     * Supprime un nom de règle par son identifiant.
     *
     * @param ruleNameId L'identifiant du nom de règle à supprimer.
     * @throws NotFoundException si le nom de règle n'est pas trouvé.
     */
    @Override
    public void deleteRuleName(final int ruleNameId) {
        log.info("Deleting by rule Name id : " + ruleNameId);
        Assert.isFound(ruleNameRepository.existsById(ruleNameId), "rule Name requested for delete does not exist");
        ruleNameRepository.deleteById(ruleNameId);
    }
}
