package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Agent;
import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.exception.AgencyRecordNotFoundException;
import com.weekytripstravelcrm.model.AgentModel;
import com.weekytripstravelcrm.repository.AgentRepository;
import com.weekytripstravelcrm.util.ArgumentValidation;
import com.weekytripstravelcrm.util.PasswordGenerator;
import com.weekytripstravelcrm.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author kushmandal
 * The Admin service class provides B2B agency details logics for save, update, fetch and delete records.
 */
@Service
public class AgentService {
    Logger log = LoggerFactory.getLogger(Agent.class);
    @Autowired
    private AgentRepository agentRepository;

    /**
     * The methode provides the details logics for saving agency record into database.
     * @param agentModel the request record to save database.
     * @return it returns the string message about process success or not.
     */
    public String createAgentRecord(AgentModel agentModel) {
        log.info("Create agency records in database.");
        ArgumentValidation.notNull(agentModel, "agentModel should not be null.");
        try {
            validateRequest(agentModel);
            Agent agent = new Agent();
            agent.setAgencyName(agentModel.getAgencyName());
            agent.setAddress1(agentModel.getAddress1());
            agent.setAddress2(agentModel.getAddress2());
            agent.setCity(agentModel.getCity());
            agent.setState(agentModel.getState());
            agent.setCountry(agentModel.getCountry());
            agent.setEmail(agentModel.getEmail());
            agent.setContact(agentModel.getContact());
            agent.setManagerName(agentModel.getManagerName());
            agent.setPassword(PasswordGenerator.generatePassword());
            agent.setRoll("AGENT");
            agent.setDocument(agentModel.getDocument());
            this.agentRepository.save(agent);
            log.info("The agency record save successfully...");
        } catch (Exception e) {
            log.error("The agency record fails to save into database !");
            throw new RuntimeException("The agency record fail to save " + e.getMessage());
        }
        return "success";
    }

    public AgentModel getRecordById(Integer agentId) {
        log.info(" Fetch agency record by agency Id .");
        ArgumentValidation.notNull(agentId, "Id can not be null.");
        AgentModel agentModel = new AgentModel();
        try {
            Optional<Agent> agents = this.agentRepository.findById(agentId);
            if (!agents.isEmpty()) {
                Agent agent = agents.get();
                agentModel.setAgencyName(agent.getAgencyName());
                agentModel.setAddress1(agent.getAddress1());
                agentModel.setAddress2(agent.getAddress2());
                agentModel.setCity(agent.getCity());
                agentModel.setState(agent.getState());
                agentModel.setCountry(agent.getCountry());
                agentModel.setEmail(agent.getEmail());
                agentModel.setContact(agent.getContact());
                agentModel.setManagerName(agent.getManagerName());
                agentModel.setDocument(agent.getDocument());
            }
        } catch (Exception e) {
            throw new AgencyRecordNotFoundException("Record not found, please provide valid Id, ", e.getMessage());
        }
        return agentModel;
    }

    public List<AgentModel> getAllAgencyRecord() {
        log.info("Fetch all agency record list.");
        List<AgentModel> agentModels = new ArrayList<>();
        List<Agent> agents = this.agentRepository.findAll();
        for (Agent agent : agents) {
            AgentModel agentModel = new AgentModel();
            if (agent != null) {
                agentModel.setAgencyName(agent.getAgencyName());
                agentModel.setAddress1(agent.getAddress1());
                agentModel.setAddress2(agent.getAddress2());
                agentModel.setCity(agent.getCity());
                agentModel.setState(agent.getState());
                agentModel.setCountry(agent.getCountry());
                agentModel.setEmail(agent.getEmail());
                agentModel.setContact(agent.getContact());
                agentModel.setManagerName(agent.getManagerName());
                agentModel.setDocument(agent.getDocument());
            }
            agentModels.add(agentModel);
        }
        return agentModels;
    }

    /**
     * The methode to delete  records
     * @param agentId should be agent Id to fetch records
     * @return it returns the string for result.
     */
    public String deleteRecordById(Integer agentId) {
        log.info("Delete record of Agency record by given Id");
        ArgumentValidation.notNull(agentId, "Id can not be null.");
        this.agentRepository.deleteById(agentId);
        return "Successfully delete";
    }

    public String updateRecord(Integer id, AgentModel agentModel) {
        log.info("Update the records of agency");
        ArgumentValidation.notNull(agentModel, "agentModel should not be null.");
        try {
            Agent agent = agentRepository.findById(id).get();

            validateRequest(agentModel);
            agent.setAgencyName(agentModel.getAgencyName());
            agent.setAddress1(agentModel.getAddress1());
            agent.setAddress2(agentModel.getAddress2());
            agent.setCity(agentModel.getCity());
            agent.setState(agentModel.getState());
            agent.setCountry(agentModel.getCountry());
            agent.setEmail(agentModel.getEmail());
            agent.setContact(agentModel.getContact());
            agent.setManagerName(agentModel.getManagerName());
            agent.setDocument(agentModel.getDocument());
            this.agentRepository.save(agent);
            log.info("The agency record Update and save successfully...");

        } catch (Exception e) {
            log.error("The agency record fails to update into database !");
            throw new AgencyRecordNotFoundException("The agency record fail to update ", e.getMessage());
        }
        return "Success";
    }

    private void validateRequest(AgentModel agentModel) throws Exception {
        ValidateUtil.isValidName(agentModel.getAgencyName());
        ValidateUtil.isValidName(agentModel.getManagerName());
        String agentEmails = agentModel.getEmail();
        String[] emails = agentEmails.split(",");
        for (String email : emails) {
            ValidateUtil.isValidEmail(email.trim());
        }
        String agentContacts = agentModel.getContact();
        String[] contacts = agentContacts.split(",");
        for (String contact : contacts) {
            ValidateUtil.isValidMobile(contact.trim());
        }
    }
}
