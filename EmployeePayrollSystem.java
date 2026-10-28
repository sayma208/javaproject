
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class Employee {
    int id;
    String name, dept;
    double salary;

    // Default constructor
    Employee() {
        id = 0;
        name = "";
        dept = "";
        salary = 0;
    }

    // Parameterized constructor
    Employee(int i, String n, double s, String d) {
        id = i;
        name = n;
        salary = s;
        dept = d;
    }

    // Copy constructor
    Employee(Employee e) {
        id = e.id;
        name = e.name;
        salary = e.salary;
        dept = e.dept;
    }

    // Net salary
    double netSalary() {
        return salary + salary * 0.20 - salary * 0.10;
    }

    public String toString() {
        return "ID: " + id + "\nName: " + name
            + "\nDepartment: " + dept
            + "\nBasic Salary: " + salary
            + "\nNet Salary: " + netSalary();
    }
}

public class EmployeePayrollSystem extends JFrame {

    JTextField id, name, salary;
    JComboBox<String> dept;
    ArrayList<Employee> list = new ArrayList<>();

    EmployeePayrollSystem() {
        setTitle("Employee Payroll System");
        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Employee ID"));
        id = new JTextField();
        add(id);

        add(new JLabel("Name"));
        name = new JTextField();
        add(name);

        add(new JLabel("Salary"));
        salary = new JTextField();
        add(salary);

        add(new JLabel("Department"));
        dept = new JComboBox<>(
            new String[]{"IT", "HR", "Sales", "Finance"});
        add(dept);

        JButton addBtn = new JButton("Add Employee");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        JButton viewBtn = new JButton("View All");

        add(addBtn);
        add(saveBtn);
        add(loadBtn);
        add(viewBtn);

        addBtn.addActionListener(e -> addEmployee());
        saveBtn.addActionListener(e -> save());
        loadBtn.addActionListener(e -> load());
        viewBtn.addActionListener(e -> view());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void addEmployee() {
        try {
            Employee e = new Employee(
                Integer.parseInt(id.getText()),
                name.getText(),
                Double.parseDouble(salary.getText()),
                dept.getSelectedItem().toString()
            );

            list.add(new Employee(e));

            JOptionPane.showMessageDialog(this,
                "Employee Added!\nNet Salary: " + e.netSalary());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Enter correct details!");
        }
    }

    void save() {
        try {
            FileWriter f = new FileWriter("employees.txt");

            for (Employee e : list) {
                f.write(e.id + "|" + e.name + "|" + e.salary
                    + "|" + e.dept + "\n");
            }

            f.close();
            JOptionPane.showMessageDialog(this, "Records Saved!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Save Error!");
        }
    }

    void load() {
        try {
            BufferedReader b = new BufferedReader(
                new FileReader("employees.txt"));

            list.clear();
            String line;

            while ((line = b.readLine()) != null) {
                String[] a = line.split("\\|", -1);

                list.add(new Employee(
                    Integer.parseInt(a[0]), a[1],
                    Double.parseDouble(a[2]), a[3]));
            }

            b.close();
            JOptionPane.showMessageDialog(this, "Records Loaded!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "File Not Found!");
        }
    }

    void view() {
        String s = "";

        for (Employee e : list) {
            s += e.toString() + "\n\n";
        }

        if (s.isEmpty()) {
            s = "No Records Found!";
        }

        JOptionPane.showMessageDialog(this, s);
    }

    public static void main(String[] args) {
        new EmployeePayrollSystem();
    }
}
