package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.Customer;
import com.weekytripstravelcrm.entity.CustomerOrderEntity;
import com.weekytripstravelcrm.entity.PackageEntity;
import com.weekytripstravelcrm.exception.CustomerRecordNotFoundException;
import com.weekytripstravelcrm.exception.OrderNotFound;
import com.weekytripstravelcrm.exception.PackageNotFoundException;
import com.weekytripstravelcrm.model.CustomerOrderModel;
import com.weekytripstravelcrm.repository.CustomerRepository;
import com.weekytripstravelcrm.repository.OrderRepository;
import com.weekytripstravelcrm.repository.PackageRepository;
import com.weekytripstravelcrm.service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    private MockMvc mockMvc;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private OrderService orderService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(orderService).build();
    }

    @Test
    public void saveOrder_success(){
        CustomerOrderModel customerOrderModel=new CustomerOrderModel();
        customerOrderModel.setCustomerId("CUS1001");
        customerOrderModel.setPackageId("WT120923");

        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();

        PackageEntity packageEntity=new PackageEntity();
        packageEntity.setPackageId(customerOrderModel.getPackageId());
        packageEntity.setTotalPackageCost(20000.0);
        packageEntity.setDiscountPercent(2.0);
        packageEntity.setCommissionPercent(3.0);
        customerOrderEntity.setPackageEntity(packageEntity);


        Customer customer=new Customer();
        customer.setCustomerID(customerOrderModel.getCustomerId());

        Mockito.when(customerRepository.findById(customerOrderModel.getCustomerId()))
                .thenReturn(Optional.of(customer));

        Mockito.when(packageRepository.findById(customerOrderModel.getPackageId()))
                .thenReturn(Optional.of(packageEntity));

        // Mockito.when(orderRepository.save(customerOrderEntity)).thenReturn(customerOrderEntity);
        String msg=this.orderService.addOrder(customerOrderModel);
        Assert.assertEquals(msg,"Order added successfully");


    }

    @Test
    public void addOrder_failure_customerNotFound(){
        CustomerOrderModel customerOrderModel=new CustomerOrderModel();
        customerOrderModel.setCustomerId("CUS1001");
        customerOrderModel.setPackageId("WT120923");

        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();

        PackageEntity packageEntity=new PackageEntity();
        packageEntity.setPackageId(customerOrderModel.getPackageId());
        packageEntity.setTotalPackageCost(20000.0);
        packageEntity.setDiscountPercent(2.0);
        packageEntity.setCommissionPercent(3.0);
        customerOrderEntity.setPackageEntity(packageEntity);


        Customer customer=new Customer();
        customer.setCustomerID(customerOrderModel.getCustomerId());

        Mockito.when(customerRepository.findById(customerOrderModel.getCustomerId()))
                .thenReturn(Optional.empty());

        boolean rightExceptionThrown=false;

        try{
            this.orderService.addOrder(customerOrderModel);
        }catch (CustomerRecordNotFoundException e){
            rightExceptionThrown=true;
        }

        Assert.assertTrue(rightExceptionThrown);
    }

    @Test
    public void addOrder_failure_packageNotFound(){
        CustomerOrderModel customerOrderModel=new CustomerOrderModel();
        customerOrderModel.setCustomerId("CUS1001");
        customerOrderModel.setPackageId("WT120923");

        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();

        PackageEntity packageEntity=new PackageEntity();
        packageEntity.setPackageId(customerOrderModel.getPackageId());
        packageEntity.setTotalPackageCost(20000.0);
        packageEntity.setDiscountPercent(2.0);
        packageEntity.setCommissionPercent(3.0);
        customerOrderEntity.setPackageEntity(packageEntity);


        Customer customer=new Customer();
        customer.setCustomerID(customerOrderModel.getCustomerId());

        Mockito.when(customerRepository.findById(customerOrderModel.getCustomerId()))
                .thenReturn(Optional.of(customer));

        Mockito.when(packageRepository.findById(customerOrderModel.getPackageId()))
                .thenReturn(Optional.empty());

        boolean rightExceptionThrown=false;

        try{
            this.orderService.addOrder(customerOrderModel);
        }catch (PackageNotFoundException e){
            rightExceptionThrown=true;
        }

        Assert.assertTrue(rightExceptionThrown);
    }


    @Test
    public void deleteOrder_success(){
        String customerOrderId="CUO1001";


        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();
        customerOrderEntity.setCustomerOrderId(customerOrderId);
        Mockito.when(orderRepository.findById(customerOrderId))
                .thenReturn(Optional.of(customerOrderEntity));

        String msg=this.orderService.deleteOrder(customerOrderId);
        Assert.assertEquals(msg,"Order Deleted successfully");
    }

    @Test
    public void deleteOrder_failure(){
        String customerOrderId="CUO1234";
        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();
        customerOrderEntity.setCustomerOrderId(customerOrderId);
        Mockito.when(orderRepository.findById(customerOrderId))
                .thenReturn(Optional.empty());
        boolean rightExceptionThrown=false;
        try{
            this.orderService.deleteOrder(customerOrderId);
        }catch(OrderNotFound e){
            rightExceptionThrown=true;
        }

        Assert.assertTrue(rightExceptionThrown);
    }

    @Test
    public void deleteAllOrder_success(){
        String customerId="CUS1001";
        String customerOrderId="CUO1001";
        List<CustomerOrderEntity> customerOrderEntityList=new ArrayList<>();
        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();
        customerOrderEntity.setCustomerOrderId(customerOrderId);
        customerOrderEntityList.add(customerOrderEntity);

        Mockito.when(orderRepository.findByCustomer_CustomerID(customerId))
                .thenReturn(customerOrderEntityList);

        String msg=this.orderService.deleteAllOrder(customerId);
        Assert.assertEquals(msg,"All Orders have been deleted succesfully");
    }

    @Test
    public void deleteAllOrder_failure(){
        String customerId="CUS1001";
        String customerOrderId="";
        List<CustomerOrderEntity> customerOrderEntityList=new ArrayList<>();
        CustomerOrderEntity customerOrderEntity=new CustomerOrderEntity();
        customerOrderEntity.setCustomerOrderId(customerOrderId);

        Mockito.when(orderRepository.findByCustomer_CustomerID(customerId))
                .thenReturn(customerOrderEntityList);
        boolean rightExceptionThrown=false;
        try{
            this.orderService.deleteAllOrder(customerId);
        }catch (OrderNotFound ex){
            rightExceptionThrown=true;
        }
        Assert.assertTrue(rightExceptionThrown);

    }
}

