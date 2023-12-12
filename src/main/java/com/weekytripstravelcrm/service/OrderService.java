package com.weekytripstravelcrm.service;

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
import com.weekytripstravelcrm.util.ArgumentValidation;
import com.weekytripstravelcrm.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PackageRepository packageRepository;

    public String addOrder(CustomerOrderModel customerOrderModel) {
        Long id = orderRepository.findMaxCustomerOrderId();
        String newId = KeyGenerator.generateId("CUO", id, CustomerOrderEntity.class, 15001L);
        ArgumentValidation.notNull(customerOrderModel, "Pass order parameters.");
        CustomerOrderEntity order = new CustomerOrderEntity();
        order.setCustomerOrderId(newId);
        Optional<Customer> customer = customerRepository.findById(customerOrderModel.getCustomerId());
        if (customer.isEmpty()) {
            throw new CustomerRecordNotFoundException();
        }
        Customer cust = customer.get();
        order.setCustomer(cust);
        Optional<PackageEntity> packageEntity = packageRepository.findById(customerOrderModel.getPackageId());
        if (packageEntity.isEmpty()) {
            throw new PackageNotFoundException();
        }
        PackageEntity newPackage = packageEntity.get();
        order.setPackageEntity(newPackage);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        order.setOrderDateTime(now);
        Double finalPrice = calculatePackagePrice(newPackage);
        order.setFinalPrice(finalPrice);
        orderRepository.save(order);
        return "Order added successfully";
    }

    private double calculatePackagePrice(PackageEntity packageEntity){
        return packageEntity.getTotalPackageCost() - (packageEntity.getDiscountPercent() / 100) * packageEntity.getTotalPackageCost() + (packageEntity.getCommissionPercent() / 100) * packageEntity.getTotalPackageCost();
    }

   /* public List<CustomerOrderModel> getAllOrders(){
        Iterable<CustomerOrderEntity> listOfOrders= orderRepository.findAll();
        ArrayList<CustomerOrderModel> ordersToGet=new ArrayList<>();
        for(CustomerOrderEntity customerOrderEntity: listOfOrders){
            CustomerOrderModel model = new CustomerOrderModel();
            model.setPackageId(customerOrderEntity.getPackageEntity().getPackageId());
            model.setCustomerId(customerOrderEntity.getCustomer().getCustomerId());
            ordersToGet.add(new CustomerOrderModel());
        }
        return ordersToGet;
    }*/

    public String deleteOrder(String id){
        Optional<CustomerOrderEntity> order = this.orderRepository.findById(id);
        if(order.isEmpty()){
            throw new OrderNotFound();
        }
        this.orderRepository.delete(order.get());
        return "Order Deleted successfully";

    }

    public String deleteAllOrder(String customerId){
        List<CustomerOrderEntity> ordersToDelete=this.orderRepository.findByCustomer_CustomerID(customerId);
        if(ordersToDelete.isEmpty()){
            throw new OrderNotFound();
        }
        orderRepository.deleteAll(ordersToDelete);
        return "All Orders have been deleted succesfully";
    }
}