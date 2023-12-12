package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.model.CustomerOrderModel;
import com.weekytripstravelcrm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-order")
public class CustomerOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add-order")
    public ResponseEntity<String> saveCustomerOrder(@RequestBody CustomerOrderModel customerOrderModel){

        return ResponseEntity.ok(orderService.addOrder(customerOrderModel));
    }

   /* @GetMapping("/get-orders")
    public ResponseEntity<List<CustomerOrderModel>> getAllOrders(@RequestBody CustomerOrderModel customerOrderModel){
        return ResponseEntity.ok(orderService.getAllOrders());
    }*/

    @DeleteMapping(value="/delete-order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable (name="id") String id){
        try{
            String msg=this.orderService.deleteOrder(id);
            return ResponseEntity.ok(msg);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting order");
        }
    }

    @DeleteMapping(value="/delete-all-orders/{id}")
    public ResponseEntity<String> deleteAllOrders(@PathVariable (name="id") String customerId){
        try{
            String msg=this.orderService.deleteAllOrder(customerId);
            return ResponseEntity.ok(msg);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting all orders");
        }
    }
}
