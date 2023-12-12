package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.Agent;
import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.exception.AgencyRecordNotFoundException;
import com.weekytripstravelcrm.model.AgentModel;
import com.weekytripstravelcrm.repository.AgentRepository;
import com.weekytripstravelcrm.service.AgentService;
import com.weekytripstravelcrm.util.PasswordGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AgentServiceTest {
    private final static Integer AGENT_ID = 1;
    private final static Integer INVALID_AGENT_ID = 1203;
    private MockMvc mockMvc;
    private AgentModel agentModel;
    private Agent agent;
    @Mock
    private AgentRepository agentRepository;
    @InjectMocks
    private AgentService agentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(agentService).build();
        agentModel = new AgentModel();
        agent = new Agent();

        agentModel.setAgencyName("Zorba BToB");
        agentModel.setAddress1("445 ABCD ");
        agentModel.setAddress2("suite 200");
        agentModel.setCity("xyzasd");
        agentModel.setState("Texas");
        agentModel.setCountry("USA");
        agentModel.setEmail("agency@gmail.com, agency1@gmail.com");
        agentModel.setContact("1234556789, 9842033714");
        agentModel.setManagerName("ABCDESF xyzc");
        agentModel.setDocument("agreement.pdf, ddl.jpg");

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
    }

    @Test
    public void createAgentRecord_success() throws Exception {
        Mockito.when(agentRepository.save(Mockito.any(Agent.class))).thenReturn(agent);
        String msg = this.agentService.createAgentRecord(agentModel);
        Assert.assertEquals(msg, "success");
        verify(agentRepository).save(Mockito.any());
    }

    @Test
    public void createAgentRecord_failure() throws Exception {
        Mockito.when(agentRepository.save(Mockito.any(Agent.class))).thenThrow(RuntimeException.class);
        Assert.assertThrows(RuntimeException.class, () -> agentService.createAgentRecord(agentModel));
    }
    @Test
    public void createAgentRecord_failure_withNull() throws Exception {
        Assert.assertThrows(RuntimeException.class, () -> agentService.createAgentRecord(null));
    }

    @Test
    public void getRecordById_success() {
        Mockito.when(agentRepository.findById(AGENT_ID)).thenReturn(Optional.of(agent));
        AgentModel result = this.agentService.getRecordById(AGENT_ID);
        Assert.assertEquals(result.getAgencyName(), agent.getAgencyName());
        Assert.assertEquals(result.getEmail(), agent.getEmail());
        verify(agentRepository).findById(AGENT_ID);
    }

    @Test
    public void getRecordById_failure() {
        Mockito.when(agentRepository.findById(INVALID_AGENT_ID)).thenThrow(AgencyRecordNotFoundException.class);
        Assert.assertThrows(AgencyRecordNotFoundException.class, () -> agentService.getRecordById(INVALID_AGENT_ID));
    }
    @Test
    public void getRecordById_failure_withNullId() {
        Assert.assertThrows(RuntimeException.class, () -> agentService.getRecordById(null));
    }

    @Test
    public void getAllAgencyRecord_success() {
        List<Agent> agents = new ArrayList<>();
        agents.add(agent);
        Mockito.when(agentRepository.findAll()).thenReturn(agents);
        List<AgentModel> result = this.agentService.getAllAgencyRecord();
        Assert.assertEquals(result.get(0).getAgencyName(), agents.get(0).getAgencyName());
        Assert.assertEquals(result.get(0).getEmail(), agents.get(0).getEmail());
        Assert.assertEquals(result.get(0).getCity(), agents.get(0).getCity());
    }

    @Test
    public void deleteRecordById_success() {
        doNothing().when(agentRepository).deleteById(AGENT_ID);
        String message = this.agentService.deleteRecordById(AGENT_ID);
        Assert.assertEquals(message, "Successfully delete");
    }

    @Test
    public void delete_failure_withNullId() {
        Assert.assertThrows(RuntimeException.class, () -> agentService.deleteRecordById(null));
    }
    @Test
    public void updateRecord_success() throws Exception {
        agentModel.setManagerName("Kush");
        agentModel.setCity("Lexington");
        Mockito.when(agentRepository.findById(AGENT_ID)).thenReturn(Optional.ofNullable(agent));
        Mockito.when(agentRepository.save(Mockito.any(Agent.class))).thenReturn(agent);
        String result = this.agentService.updateRecord(AGENT_ID, agentModel);
        Assert.assertEquals(result, "Success");
    }

    @Test
    public void updateRecord_failure() throws Exception {
        Mockito.when(agentRepository.findById(INVALID_AGENT_ID)).thenThrow(AdminRecordNotFoundException.class);
        Assert.assertThrows(RuntimeException.class, () -> agentService.updateRecord(INVALID_AGENT_ID, agentModel));
    }

    @Test
    public void updateRecord_failure_withNullId() {
        Mockito.when(agentRepository.findById(null)).thenThrow(RuntimeException.class);
        Assert.assertThrows(RuntimeException.class, () -> agentService.updateRecord(null, agentModel));
    }

}
