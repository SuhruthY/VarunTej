package com.example.employeesystem.service;

import com.example.employeesystem.dto.EmployeeDTO;
import com.example.employeesystem.exception.EmployeeNotFoundException;
import com.example.employeesystem.mapper.EmployeeMapper;
import com.example.employeesystem.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<Long, Employee> employeeMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        employee.setId(idCounter.incrementAndGet());
        employeeMap.put(employee.getId(), employee);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeMap.get(id);
        if (employee == null || employee.isDeleted()) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees(String department, Boolean includeDeleted) {
        return employeeMap.values().stream()
                .filter(e -> (includeDeleted || !e.isDeleted()) &&
                             (department == null || department.equalsIgnoreCase(e.getDepartment())))
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeMap.get(id);
        if (existing == null || existing.isDeleted()) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        existing.setName(employeeDTO.getName());
        existing.setDepartment(employeeDTO.getDepartment());
        existing.setEmail(employeeDTO.getEmail());
        return EmployeeMapper.toDTO(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employee.setDeleted(true);
    }
}
