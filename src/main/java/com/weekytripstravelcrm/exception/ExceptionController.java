package com.weekytripstravelcrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ContactNumberException.class)
    public ResponseEntity<Object> phoneNumberException(ContactNumberException e) {
        return new ResponseEntity<>("Incorrect Format", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AdminRecordNotFoundException.class)
    public ResponseEntity<Object> adminRecordNotFoundException(AdminRecordNotFoundException ex) {
        return new ResponseEntity<>("Admin Record Not found !!", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = AgencyRecordNotFoundException.class)
    public ResponseEntity<Object> agentRecordNotFoundException(AgencyRecordNotFoundException ex) {
        return new ResponseEntity<>("Agent Record Not found !!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public ResponseEntity<String> invalidEmailException(InvalidEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidMobileException.class)
    public ResponseEntity<Object> invalidMobileException(InvalidMobileException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidNameException.class)
    public ResponseEntity<Object> invalidNameException(InvalidNameException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<Object> invalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = PasswordMismatchException.class)
    public ResponseEntity<Object> passwordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = TripNotFoundException.class)
    public ResponseEntity<Object> tripNotFoundException(TripNotFoundException e){
        return new ResponseEntity<>("Trip not found!!!" , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PackageNotFoundException.class)
    public ResponseEntity<Object> packageNotFoundException(PackageNotFoundException e){
        return new ResponseEntity<>("Package not found!!!" , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HotelNotFoundException.class)
    public ResponseEntity<Object> hotelNotFoundException(HotelNotFoundException e){
        return new ResponseEntity<>("Hotel data not found",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PropertyRuleNotFoundException.class)
    public ResponseEntity<Object> propertyRuleNotFoundException(PropertyRuleNotFoundException e){
        return new ResponseEntity<>("Hotel data not found",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = LocationNotFoundException.class)
    public ResponseEntity<Object> locationNotFoundException(LocationNotFoundException e){
        return new ResponseEntity<>("Location Not Found",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HotelAmenitiesNotFoundException.class)
    public ResponseEntity<Object> hotelAmenitiesNotFoundException(HotelAmenitiesNotFoundException e){
        return new ResponseEntity<>("Hotel Amenities not found exception",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NullException.class)
    public ResponseEntity<Object> nullValueFound(NullException e){
        return new ResponseEntity<>("Null value is found. Please check the value again",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AddressException.class)
    public ResponseEntity<Object> addressNullException(AddressException e){
        return new ResponseEntity<>("Null value is found. Address can not be null",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> customException(CustomException e){
        return new ResponseEntity<>("Record already found into the database",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderNotFound.class)
    public ResponseEntity<Object> orderNotFound(OrderNotFound e){

        return new ResponseEntity<>("Order not found!!!" , HttpStatus.NOT_FOUND);
    }
}