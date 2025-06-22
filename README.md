SCADA Data Logger — Java + MySQL Project
This project is a real-time data logging system for power distribution monitoring, inspired by my summer internship at SCADA Bhawan, Patna under SBPDCL (South Bihar Power Distribution Company Limited). It is developed using Java (JDBC) and MySQL, and simulates logging of substation data like voltage, frequency, current load, power factor, and energy supplied.

Technologies Used
Java (JDK 17+)

MySQL

JDBC (MySQL Connector/J)

VS Code

MySQL Workbench

📂 Project Structure
graphql
Copy
Edit
SCADA-Data-Logger/
├── DataManager.java       # Main Java file with CRUD operations
├── scada_data.sql         # SQL file for table creation and sample data
├── README.md              # Project documentation
└── lib/
    └── mysql-connector-j-xx.jar  # JDBC driver
Features
✅ Add new power device records (substation-wise)
✅ View all logged records in tabular format
✅ Update status and remarks by ID
✅ Delete records by ID
✅ Real-time timestamp logging
✅ Clean and interactive CLI interface

Database Schema
Table: power_device_log

Column Name	Type	Description
id	INT	Auto-increment primary key
substation_name	VARCHAR(100)	Name of the substation
feeder_id	VARCHAR(50)	ID of the feeder line
voltage_level	VARCHAR(20)	e.g., 11kV, 33kV
current_load	DECIMAL(10,2)	Current in Amperes
power_factor	DECIMAL(3,2)	Efficiency indicator (0–1)
frequency	DECIMAL(5,2)	Typically 49–50 Hz
energy_supplied	DECIMAL(10,2)	Energy in kWh
status	VARCHAR(50)	Online / Tripped / Maintenance
remarks	VARCHAR(255)	Additional notes
timestamp	DATETIME	Auto-generated on insert

How to Run
Clone the repo

bash
Copy
Edit
git clone https://github.com/your-username/SCADA-Data-Logger.git
cd SCADA-Data-Logger
Create the database and table

Import scada_data.sql into MySQL

Or manually run SQL from inside DataManager.java description

Add JDBC Driver

Download MySQL Connector/J

Place the .jar in lib/ and link it in your project classpath

Compile and Run

bash
Copy
Edit
javac -cp "lib/mysql-connector-j-*.jar" DataManager.java
java -cp ".;lib/mysql-connector-j-*.jar" DataManager
📌 Learning Outcomes
Practical experience with JDBC and relational databases

Understanding of power distribution system logging

Clean implementation of CRUD operations

Structured project documentation for real-world use

🙋 Author
👨‍💻 Rohan Kumar
🎓 B.Tech in Computer Science
🏫 Government Engineering College, Vaishali
🗂️ Internship at SCADA Bhawan, Patna (SBPDCL)
