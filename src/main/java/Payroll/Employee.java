package Payroll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Employee {

  private @Id @GeneratedValue Long id;
  private String nombre;
  private String cargo;
  private String departamento;

  @Column(precision = 12, scale = 2)
  private BigDecimal salario;

  private String correo;
  private LocalDate fechaIngreso;

  Employee() {}

  Employee(String nombre, String cargo, String departamento, BigDecimal salario, String correo, LocalDate fechaIngreso) {
    this.nombre = nombre;
    this.cargo = cargo;
    this.departamento = departamento;
    this.salario = salario;
    this.correo = correo;
    this.fechaIngreso = fechaIngreso;
  }

  public Long getId() {
    return this.id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getCargo() {
    return this.cargo;
  }

  public String getDepartamento() {
    return this.departamento;
  }

  public BigDecimal getSalario() {
    return this.salario;
  }

  public String getCorreo() {
    return this.correo;
  }

  public LocalDate getFechaIngreso() {
    return this.fechaIngreso;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public void setSalario(BigDecimal salario) {
    this.salario = salario;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public void setFechaIngreso(LocalDate fechaIngreso) {
    this.fechaIngreso = fechaIngreso;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Employee))
      return false;
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id)
        && Objects.equals(this.nombre, employee.nombre)
        && Objects.equals(this.cargo, employee.cargo)
        && Objects.equals(this.departamento, employee.departamento)
        && Objects.equals(this.salario, employee.salario)
        && Objects.equals(this.correo, employee.correo)
        && Objects.equals(this.fechaIngreso, employee.fechaIngreso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.nombre, this.cargo, this.departamento, this.salario, this.correo, this.fechaIngreso);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + this.id + ", nombre='" + this.nombre + '\'' + ", cargo='" + this.cargo + '\''
        + ", departamento='" + this.departamento + '\'' + ", salario=" + this.salario + ", correo='" + this.correo
        + '\'' + ", fechaIngreso=" + this.fechaIngreso + '}';
  }
}
