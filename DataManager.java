import java.sql.*;
import java.util.Scanner;

public class DataManager {

    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/scada_data", "root", "Rolex@2005");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void addRecord(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.print("Enter substation name: ");
            String substationName = scanner.nextLine();

            System.out.print("Enter feeder ID: ");
            String feederId = scanner.nextLine();

            System.out.print("Enter voltage level (e.g., 11kV): ");
            String voltageLevel = scanner.nextLine();

            System.out.print("Enter current load (Amps): ");
            double currentLoad = scanner.nextDouble();

            System.out.print("Enter power factor: ");
            double powerFactor = scanner.nextDouble();

            System.out.print("Enter frequency (Hz): ");
            double frequency = scanner.nextDouble();

            System.out.print("Enter energy supplied (kWh): ");
            double energySupplied = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            System.out.print("Enter status (Online/Tripped/etc.): ");
            String status = scanner.nextLine();

            System.out.print("Enter remarks: ");
            String remarks = scanner.nextLine();

            String query = "INSERT INTO power_device_log (substation_name, feeder_id, voltage_level, current_load, power_factor, frequency, energy_supplied, status, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, substationName);
            stmt.setString(2, feederId);
            stmt.setString(3, voltageLevel);
            stmt.setDouble(4, currentLoad);
            stmt.setDouble(5, powerFactor);
            stmt.setDouble(6, frequency);
            stmt.setDouble(7, energySupplied);
            stmt.setString(8, status);
            stmt.setString(9, remarks);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " record(s) inserted.");
        } catch (Exception e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    public static void viewAllRecords() {
        try (Connection conn = connect()) {
            String query = "SELECT * FROM power_device_log";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.printf("%-5s %-20s %-10s %-10s %-12s %-10s %-10s %-10s %-15s %-20s %-20s\n",
                    "ID", "Substation", "Feeder", "Voltage", "Current (A)", "P.F", "Freq", "Energy", "Status",
                    "Remarks", "Timestamp");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10s %-10s %-12.2f %-10.2f %-10.2f %-10.2f %-15s %-20s %-20s\n",
                        rs.getInt("id"),
                        rs.getString("substation_name"),
                        rs.getString("feeder_id"),
                        rs.getString("voltage_level"),
                        rs.getDouble("current_load"),
                        rs.getDouble("power_factor"),
                        rs.getDouble("frequency"),
                        rs.getDouble("energy_supplied"),
                        rs.getString("status"),
                        rs.getString("remarks"),
                        rs.getTimestamp("timestamp").toString());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }

    public static void updateRecord(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.print("Enter the ID of the record to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Enter new status: ");
            String status = scanner.nextLine();

            System.out.print("Enter new remarks: ");
            String remarks = scanner.nextLine();

            String query = "UPDATE power_device_log SET status = ?, remarks = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setString(2, remarks);
            stmt.setInt(3, id);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " record(s) updated.");
        } catch (Exception e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    public static void deleteRecord(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.print("Enter the ID of the record to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline

            String query = "DELETE FROM power_device_log WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " record(s) deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("\n--- SBPDCL SCADA Data Logger ---");
        System.out.println("1. Add Record");
        System.out.println("2. View Records");
        System.out.println("3. Update Record");
        System.out.println("4. Delete Record");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
        
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // clear buffer
            continue;
        }

        switch (choice) {
            case 1 -> addRecord(scanner);
            case 2 -> viewAllRecords();
            case 3 -> updateRecord(scanner);
            case 4 -> deleteRecord(scanner);
            case 5 -> {
                System.out.println("Exiting...");
                scanner.close();
                return;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
}

}
