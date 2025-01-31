-- Limpiar tablas si existen
TRUNCATE TABLE flights CASCADE;
TRUNCATE TABLE airports CASCADE;
TRUNCATE TABLE users CASCADE;

-- Reiniciar secuencias
ALTER SEQUENCE IF EXISTS airports_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS flights_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS users_id_seq RESTART WITH 1;

-- Insertar Aeropuertos
INSERT INTO airports (code, name, city, country, active, size) VALUES
('MAD', 'Adolfo Suárez Madrid–Barajas Airport', 'Madrid', 'Spain', true, 'LARGE'),
('BCN', 'Barcelona International Airport', 'Barcelona', 'Spain', true, 'LARGE'),
('VLC', 'Valencia Airport', 'Valencia', 'Spain', true, 'MEDIUM'),
('LHR', 'London Heathrow Airport', 'London', 'UK', true, 'LARGE'),
('CDG', 'Charles de Gaulle Airport', 'Paris', 'France', true, 'LARGE');

-- Insertar Usuarios
INSERT INTO users (username, email, password, first_name, last_name, role, active) VALUES
('admin', 'admin@airport.com', '$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa', 'Admin', 'User', 'ROLE_ADMIN', true),
('user1', 'user1@airport.com', '$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa', 'John', 'Doe', 'ROLE_USER', true),
('user2', 'user2@airport.com', '$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa', 'Jane', 'Smith', 'ROLE_USER', true);

-- Insertar Vuelos
INSERT INTO flights (
    flight_number,
    origin_airport_id,
    destination_airport_id,
    departure_time,
    arrival_time,
    total_seats,
    available_seats,
    price,
    active,
    status
) VALUES
-- Madrid a Barcelona
('FL001', 1, 2, CURRENT_TIMESTAMP + interval '1 day', CURRENT_TIMESTAMP + interval '1 day 2 hours', 150, 150, 199.99, true, 'SCHEDULED'),
-- Barcelona a Madrid
('FL002', 2, 1, CURRENT_TIMESTAMP + interval '1 day', CURRENT_TIMESTAMP + interval '1 day 2 hours', 150, 150, 199.99, true, 'SCHEDULED'),
-- Madrid a Valencia
('FL003', 1, 3, CURRENT_TIMESTAMP + interval '2 days', CURRENT_TIMESTAMP + interval '2 days 1 hour 30 minutes', 120, 120, 149.99, true, 'SCHEDULED'),
-- Valencia a Madrid
('FL004', 3, 1, CURRENT_TIMESTAMP + interval '2 days', CURRENT_TIMESTAMP + interval '2 days 1 hour 30 minutes', 120, 120, 149.99, true, 'SCHEDULED'),
-- Madrid a Londres
('FL005', 1, 4, CURRENT_TIMESTAMP + interval '3 days', CURRENT_TIMESTAMP + interval '3 days 2 hours 30 minutes', 200, 200, 299.99, true, 'SCHEDULED'),
-- Londres a Madrid
('FL006', 4, 1, CURRENT_TIMESTAMP + interval '3 days', CURRENT_TIMESTAMP + interval '3 days 2 hours 30 minutes', 200, 200, 299.99, true, 'SCHEDULED'),
-- Barcelona a París
('FL007', 2, 5, CURRENT_TIMESTAMP + interval '4 days', CURRENT_TIMESTAMP + interval '4 days 2 hours', 180, 180, 249.99, true, 'SCHEDULED'),
-- París a Barcelona
('FL008', 5, 2, CURRENT_TIMESTAMP + interval '4 days', CURRENT_TIMESTAMP + interval '4 days 2 hours', 180, 180, 249.99, true, 'SCHEDULED');

-- Verificar datos insertados
SELECT 'Airports count: ' || COUNT(*) FROM airports;
SELECT 'Users count: ' || COUNT(*) FROM users;
SELECT 'Flights count: ' || COUNT(*) FROM flights;

-- Mostrar algunos datos de ejemplo
SELECT
    f.flight_number,
    a1.code as origin,
    a2.code as destination,
    f.departure_time,
    f.arrival_time,
    f.price
FROM flights f
JOIN airports a1 ON f.origin_airport_id = a1.id
JOIN airports a2 ON f.destination_airport_id = a2.id
WHERE f.active = true
ORDER BY f.departure_time;