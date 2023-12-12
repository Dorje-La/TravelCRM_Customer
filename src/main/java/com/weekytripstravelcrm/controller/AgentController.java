package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.exception.AgencyRecordNotFoundException;
import com.weekytripstravelcrm.model.AdminModel;
import com.weekytripstravelcrm.model.AgentModel;
import com.weekytripstravelcrm.service.AgentService;
import com.weekytripstravelcrm.util.ArgumentValidation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @authore Kush
 * The Admin Controller class create api for save, update, fetch and delete admin records.
 */

@RestController
@RequestMapping("/agent")
@SecurityRequirement(name = "test-swagger")
public class AgentController {
    Logger log = LoggerFactory.getLogger(AgentController.class);
    @Autowired
    private AgentService agentService;

    /**
     * The methode to create api to save agency records
     * @param agentModel contains the request data of admin
     * @return it returns the message for result.
     */
    @PostMapping(value = "/save")
    public String saveAdminRecord(@RequestBody AgentModel agentModel) {
        log.info("Create controller for agent record api");
        ArgumentValidation.notNull(agentModel, "agentModel should not be null");
        String message = this.agentService.createAgentRecord(agentModel);
        return message;
    }

    /**
     * The methode to create api to fetch agent records by Id
     * @param agentId should be agent Id to fetch records
     * @return it returns the agency record for result.
     */
    @GetMapping(value = "/get/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgentModel getAgencyInfo(@PathVariable("Id") int agentId) {
        log.info("Fetch agency record by Id api.");
        ArgumentValidation.notNull(agentId, "Id should not be null");
        AgentModel agentModel = null;
        try {
            log.info("Successfully fetch the records of given d of agency.");
           agentModel  = this.agentService.getRecordById(agentId);
        } catch (Exception ex) {
            log.error("Agent record not found, please use valid Id !!");
            throw new AgencyRecordNotFoundException("Record not found :", ex.getMessage());
        }
        return agentModel;
    }

    /**
     * The methode to create api to fetch all agency records
     * @return it returns the list of all record for result.
     */
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AgentModel> getAllRecord() {
        log.info("Get agency all records api");
        try {
            log.info("Successfully fetch all the records of agents.");
            return this.agentService.getAllAgencyRecord();
        } catch (Exception ex) {
            log.error("Agents record not found");
            throw new AgencyRecordNotFoundException("Record not found :", ex.getMessage());
        }
    }

    /**
     * The methode to create api to update agent records
     * @param agentModel should contain the update entities records
     * @return it returns the string for result.
     */
    @PutMapping(value = "/update/{id}")
    public String updateAgentRecord(@PathVariable Integer id, @RequestBody AgentModel agentModel) {
        log.info("Update the agent records");
        ArgumentValidation.notNull(agentModel, "agentModel should not be null");
        ArgumentValidation.notNull(id, "Id should not be null");
        String message = this.agentService.updateRecord(id, agentModel);
        return message;
    }

    /**
     * The methode to create api to delete agent records
     * @param agentId it is Id to fetch records
     * @return it returns the string for result.
     */
    @DeleteMapping(value = "/delete/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAgentInfo(@PathVariable("Id") int agentId) {
        log.info("Api for delete the agent record.");
        ArgumentValidation.notNull(agentId, "Id should not be null");
        String message = null;
        try {
            log.info("successfully deleted the record !");
            message = this.agentService.deleteRecordById(agentId);
        } catch (Exception ex) {
            log.error("Agency record not found, please use valid Id !!");
            throw new AgencyRecordNotFoundException("Record not found :", ex.getMessage());
        }
        return message;
    }
}
