package Payroll;

class EmployeeNotFoundException extends RuntimeException {

  EmployeeNotFoundException(Long id) {
    super("No se encontró el empleado " + id);
  }
}