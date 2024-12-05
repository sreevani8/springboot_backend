package com.mulit.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mulit.springboot.exception.ResourceNotFoundException;
import com.mulit.springboot.model.Employee;
import com.mulit.springboot.repository.EmployeeRepository;



//@CrossOrigin(origins = "http://localhost:4200")

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	//get all employees
	  @GetMapping("/emp") 
	  public List<Employee> getAllEmployees(){
		  return employeeRepository.findAll(); 
		  }
	 
	  //create employee rest api
	  @PostMapping("/create_emp")
	  public Employee createEmployee(@RequestBody Employee employee){
		  return employeeRepository.save(employee);
	  }
	  
	// get employee by id rest api
		@GetMapping("/emp/{id}")
		public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
			return ResponseEntity.ok(employee);
		}
		
		//update employee rest api
		
		@PutMapping("/emp/{id}")
		public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
			
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id:" +id));
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			employee.setEmailId(employeeDetails.getEmailId());
			
			Employee updateEmployee = employeeRepository.save(employee);
			
			return ResponseEntity.ok(updateEmployee);
		}
		
		//delete employee rest api
		@DeleteMapping("/emp/{id}")
		public ResponseEntity<Map<String, Boolean>> deletemployee(@PathVariable Long id){
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id:"+id));
					
					employeeRepository.delete(employee);
					Map<String, Boolean> response = new HashMap<>();
					response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
			
		}
		
		
		
	/*@GetMapping("/emp/{id}")
	public Employee getEmpById(@PathVariable Long id) {
		Optional<Employee> employee=employeeRepository.findById(id);
				//.orElseThrow(()->new ResourceNotFoundException(""+id));
		if(employee.isPresent()) {
			return employee.get();
		}else {
			throw new ResourceNotFoundException("record not found",+id);
		}
		*/
	}	
		


