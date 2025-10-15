
public class TestPayroll {
    public static void main(String[] args) {
        Payroll emps1 = new Payroll();
        Payroll emps2 = new Payroll();

        Employee emp1 = new Employee();
        emp1.name = "John Smith";
        emp1.ID = 1001;
        emp1.salary = 55000.0;

        Employee emp2 = new Employee();
        emp2.name = "Sarah Johnson";
        emp2.ID = 1002;
        emp2.salary = 62000.0;

        Employee emp3 = new Employee();
        emp3.name = "Michael Brown";
        emp3.ID = 1003;
        emp3.salary = 48000.0;

        Employee emp4 = new Employee();
        emp4.name = "Emily Davis";
        emp4.ID = 1004;
        emp4.salary = 71000.0;

        Employee emp5 = new Employee();
        emp5.name = "David Wilson";
        emp5.ID = 1005;
        emp5.salary = 59000.0;

        emps1.add_employee(emp1);
        emps1.add_employee(emp2);
        emps1.add_employee(emp3);
        emps1.add_employee(emp4);
        emps1.add_employee(emp5);

        Employee emp6 = new Employee();
        emp6.name = "Lisa Anderson";
        emp6.ID = 1006;
        emp6.salary = 67000.0;

        Employee emp7 = new Employee();
        emp7.name = "Robert Taylor";
        emp7.ID = 1007;
        emp7.salary = 53000.0;

        Employee emp8 = new Employee();
        emp8.name = "Jennifer Martinez";
        emp8.ID = 1008;
        emp8.salary = 64000.0;

        Employee emp9 = new Employee();
        emp9.name = "Christopher Lee";
        emp9.ID = 1009;
        emp9.salary = 56000.0;

        Employee emp10 = new Employee();
        emp10.name = "Amanda White";
        emp10.ID = 1010;
        emp10.salary = 69000.0;

        emps2.add_employee(emp6);
        emps2.add_employee(emp7);
        emps2.add_employee(emp8);
        emps2.add_employee(emp9);
        emps2.add_employee(emp10);
        

        System.out.println(emps1.size());
        System.out.println(emps2.size());

        emps1.print();
        emps2.print();

        try{
            emps1.remove_employee(2);
        }
        catch(EmployeeIndexException e){
            System.out.println("Entry at index 2 not found");
        }

        emps1.print();

        try{
            emps1.find_employee("David Wilson");
        }
        catch(EmployeeNotFoundException e){
            System.out.println("Employee named David Wilson not found");
        }

        emps1.copy_payroll(emps2);

        emps1.print();
    }
}
