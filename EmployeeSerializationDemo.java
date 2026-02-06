import java.io.*;
import java.util.*;
/**
 * Demo for reading employees from a text file,
 * serializing them to a binary file, and deserializing back.
 */
public class EmployeeSerializationDemo{
    /**
     * Reads employees from a text file.
     * Format per line: id,name,salary
     */
    public static List<Employee> readEmployeesFromText(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] parts=line.split(",");
                int id=Integer.parseInt(parts[0].trim());
                String name=parts[1].trim();
                double salary=Double.parseDouble(parts[2].trim());
                employees.add(new Employee(id, name, salary));
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return employees;
    }
    /** Serializes a list of employees to a binary file. */
    public static void serializeEmployees(List<Employee> employees, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(employees);
            System.out.println("Employees serialized successfully!");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** Deserializes employees from a binary file. */
    @SuppressWarnings("unchecked")
    public static List<Employee> deserializeEmployees(String fileName){
        List<Employee> employees=null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            employees = (List<Employee>) ois.readObject();
            System.out.println("Employees deserialized successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }
    /** Main method to run the demo. */
    public static void main(String[] args) {
        String textFile = "employees.txt";
        String binaryFile = "employees.dat";

        List<Employee> employees = readEmployeesFromText(textFile);
        serializeEmployees(employees, binaryFile);
        List<Employee> deserializedEmployees = deserializeEmployees(binaryFile);

        System.out.println("Employees from deserialized file:");
        for (Employee emp : deserializedEmployees) {
            System.out.println(emp);
        }
    }
}
