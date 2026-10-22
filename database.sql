CREATE DATABASE IF NOT EXISTS energy_audit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE energy_audit;

CREATE TABLE obiekt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nazva VARCHAR(150) NOT NULL,
    adresa VARCHAR(200) NOT NULL,
    typ VARCHAR(50) NOT NULL,
    ploshcha DOUBLE NOT NULL
);

CREATE TABLE enerhoaudyt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    obiekt_id INT NOT NULL,
    data_provedennia DATE NOT NULL,
    auditor VARCHAR(100) NOT NULL,
    vysnovok TEXT,
    FOREIGN KEY (obiekt_id) REFERENCES obiekt(id) ON DELETE CASCADE
);

CREATE TABLE zakhid (
    id INT AUTO_INCREMENT PRIMARY KEY,
    enerhoaudyt_id INT NOT NULL,
    nazva VARCHAR(150) NOT NULL,
    vartist DOUBLE NOT NULL,
    potentsial_ekonomii DOUBLE NOT NULL,
    termin_okupnosti DOUBLE NOT NULL,
    potentsial_skorochennia_vykydiv DOUBLE NOT NULL,
    stan VARCHAR(30) NOT NULL,
    FOREIGN KEY (enerhoaudyt_id) REFERENCES enerhoaudyt(id) ON DELETE CASCADE
);

CREATE TABLE zapys_spozhyvannia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    obiekt_id INT NOT NULL,
    data_zapysu DATE NOT NULL,
    spozhyvannia_kvt_hod DOUBLE NOT NULL,
    vartist_hryvnia DOUBLE NOT NULL,
    FOREIGN KEY (obiekt_id) REFERENCES obiekt(id) ON DELETE CASCADE
);

INSERT INTO obiekt (nazva, adresa, typ, ploshcha) VALUES 
('Головний корпус', 'вул. Першотравнева 24', 'Офісна будівля', 1200.5),
('Склад №1', 'вул. Промислова 8', 'Складське приміщення', 2500.0);

INSERT INTO enerhoaudyt (obiekt_id, data_provedennia, auditor, vysnovok) VALUES 
(1, '2026-09-10', 'Іваненко І.І.', 'Виявлено високе споживання через стару систему опалення.'),
(2, '2026-09-15', 'Петренко П.П.', 'Задовільний стан, але є проблеми з освітленням.');

INSERT INTO zakhid (enerhoaudyt_id, nazva, vartist, potentsial_ekonomii, termin_okupnosti, potentsial_skorochennia_vykydiv, stan) VALUES 
(1, 'Заміна вікон на енергоефективні', 50000.0, 80.0, 24.0, 70.0, 'ЗАПРОПОНОВАНО'),
(1, 'Утеплення фасаду', 120000.0, 90.0, 36.0, 85.0, 'СХВАЛЕНО');

INSERT INTO zapys_spozhyvannia (obiekt_id, data_zapysu, spozhyvannia_kvt_hod, vartist_hryvnia) VALUES 
(1, '2026-08-01', 15000.5, 45000.0),
(1, '2026-09-01', 14500.0, 43500.0);