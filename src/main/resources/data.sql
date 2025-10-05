-- Sample Patients with diverse blood groups
INSERT INTO patient (name, birth_date, email, gender, created_at, blood_group) VALUES
('John Doe', '1979-05-12', 'john.doe@example.com', 'Male', '2024-06-01 10:00:00', 'O_POSITIVE'),
('Jane Smith', '1992-03-08', 'jane.smith@example.com', 'Female', '2024-06-02 11:00:00', 'A_POSITIVE'),
('Michael Brown', '1964-11-21', 'michael.brown@example.com', 'Male', '2024-06-03 12:00:00', 'B_POSITIVE'),
('Emily Davis', '1997-02-14', 'emily.davis@example.com', 'Female', '2024-06-04 13:00:00', 'AB_POSITIVE'),
('William Johnson', '1974-07-30', 'william.johnson@example.com', 'Male', '2024-06-05 14:00:00', 'O_NEGATIVE'),
('Sarah Williams', '1985-09-15', 'sarah.williams@example.com', 'Female', '2024-06-06 15:00:00', 'A_NEGATIVE'),
('Robert Miller', '1990-12-20', 'robert.miller@example.com', 'Male', '2024-06-07 16:00:00', 'B_NEGATIVE'),
('Lisa Anderson', '1988-03-25', 'lisa.anderson@example.com', 'Female', '2024-06-08 17:00:00', 'AB_NEGATIVE');

-- Sample Doctors with different specializations
INSERT INTO doctor (name, specialization, email) VALUES
('Dr. Rajesh Kumar', 'Cardiology', 'dr.rajesh.kumar@hospital.com'),
('Dr. Priya Sharma', 'Neurology', 'dr.priya.sharma@hospital.com'),
('Dr. Amit Patel', 'Orthopedics', 'dr.amit.patel@hospital.com');