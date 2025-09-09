
public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    public Payroll() {
        current_size = 0;
        maximum_size = INITIAL_MAXIMUM_SIZE;
        people = new Employee[maximum_size];
        
    }

    public int size() {
        return current_size;
    }

    public void print(){
        for (Employee e: people){
            System.out.println("ID: "+e.ID+", Name: "+e.name+", Salary: "+e.salary);
        }
    }
    
    public void add_employee(Employee newbie) {
        if (current_size < maximum_size){
            people[current_size+1] = newbie;
            current_size+=1;
        }
        else{
            Employee[] p = new Employee[maximum_size*2]
            for (int i = 0; i < current_size; i++){
                p[i] = people[i];
            }
            people = p;
            people[current_size+1] = newbie;
            current_size+=1;
        }
    }

    public void remove_employee(int i) throws EmployeeIndexException {
        if(i >= current_size || i == 0){
            throw new EmployeeIndexException();
        }
        else{
            for(int j = i; j < current_size; j++){
                people[j] = people[j+1];
            }
            current_size-=1;
        }
    }
    
    public int find_employee(String name) throws EmployeeNotFoundException {
        for(int i = 0; i < current_size; i++){
            if(name.equals(people[i].name))
                return i;
        }
        throw new EmployeeNotFoundException();
    }

    public void add_payroll(Payroll source) {
        int size = current_size + source.current_size;

        if(size >= maximum_size){
            Employee[] p = new Employee[maximum_size*2];
            for(int i = 0; i<current_size; i++)
                p[i] = people[i];
            for(int i = 0; i<source.current_size; i++)
                p[i+current_size] = source.people[i];
            people = p;
        }
        else{
            for(int i = current_size; i < source.current_size; i++)
                people[i] = source.people[i];
            
        }
        current_size = size;
    }

    public void copy_payroll(Payroll source) {
        add_payroll(source);
    }

    private Employee people[];
    private int maximum_size;
    private int current_size;
}
