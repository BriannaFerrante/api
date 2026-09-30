package Payroll;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController {

  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/employees")
  List<Employee> all() {
    return repository.findAll();
  }

  @PostMapping("/employees")
  ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
    Employee saved = repository.save(newEmployee);
    return ResponseEntity.created(URI.create("/employees/" + saved.getId())).body(saved);
  }

  @GetMapping("/employees/{id}")
  Employee one(@PathVariable Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @PutMapping("/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
    return repository.findById(id)
        .map(employee -> {
          employee.setNombre(newEmployee.getNombre());
          employee.setCargo(newEmployee.getCargo());
          employee.setDepartamento(newEmployee.getDepartamento());
          employee.setSalario(newEmployee.getSalario());
          employee.setCorreo(newEmployee.getCorreo());
          employee.setFechaIngreso(newEmployee.getFechaIngreso());
          return repository.save(employee);
        })
        .orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @DeleteMapping("/employees/{id}")
  ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    if (!repository.existsById(id)) {
      throw new EmployeeNotFoundException(id);
    }
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
