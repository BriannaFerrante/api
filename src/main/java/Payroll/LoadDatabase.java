package Payroll;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new Employee(
          "Ana López", "Analista de nómina", "Finanzas",
          new BigDecimal("52000.00"), "ana.lopez@empresa.com", LocalDate.of(2022, 4, 11))));
      log.info("Preloading " + repository.save(new Employee(
          "Carlos Mendes", "Desarrollador", "Tecnología",
          new BigDecimal("68000.00"), "carlos.mendes@empresa.com", LocalDate.of(2021, 9, 3))));
      log.info("Preloading " + repository.save(new Employee(
          "Lucía Ferreira", "Supervisora", "Operaciones",
          new BigDecimal("61000.00"), "lucia.ferreira@empresa.com", LocalDate.of(2019, 1, 28))));
    };
  }
}
