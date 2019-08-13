package spring.mooc.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.mooc.seckill.bean.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);

    public List<Employee> selectByIdAll();
}
