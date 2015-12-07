drop database if exists "ohmbaseopenwei";
create database "ohmbaseopenwei";
set datestyle to 'SQL, DMY';

create table amplifiers
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) >0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (amplifiers);

create table batteries
( 
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (batteries);
	
create table buzzers
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (buzzers);

create table capacitors
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (capacitors);

create table diodes
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (diodes);

create table fuses
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (fuses);

create table inductors
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (inductors);

create table isolators
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (isolators);

create table leds
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (leds);

create table logic_gates
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (logic_gates);

create table microcontrollers
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (microcontrollers);

create table microphones
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (microphones);

create table motors
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (motors);

create table oscillators
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (oscillators);

create table potentiometers
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (potentiometers);

create table power_supplies
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (power_supplies);

create table resistors
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (resistors);

create table sensors
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (sensors);

create table speakers
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (speakers);

create table transformers
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (transformers);

create table transistors
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (transistors);

create table other
(
	ID serial primary key,
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity text null constraint valid_quantity check (length(quantity) > 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
CREATE UNIQUE INDEX name ON table (other);

create schema users;
create table users.account
(
	ID serial primary key,
	username text null constraint valid_username check (length(username) >= 0),
	password text null constraint valid_password check (length(password) >= 0)
);

CREATE OR REPLACE FUNCTION update_date()
RETURNS TRIGGER AS $$
BEGIN
   NEW.last_modified = current_date; 
   RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER lastmod1
    BEFORE UPDATE ON amplifiers
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod2
    BEFORE UPDATE ON batteries
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod3
    BEFORE UPDATE ON buzzers
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod4
    BEFORE UPDATE ON capacitors
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod5
    BEFORE UPDATE ON diodes
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod6
    BEFORE UPDATE ON fuses
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod7
    BEFORE UPDATE ON inductors
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod8
    BEFORE UPDATE ON isolators
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod9
    BEFORE UPDATE ON leds
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod10
    BEFORE UPDATE ON logic_gates
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod11
    BEFORE UPDATE ON microcontrollers
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();

	CREATE TRIGGER lastmod12
    BEFORE UPDATE ON microphones
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod13
    BEFORE UPDATE ON motors
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod14
    BEFORE UPDATE ON oscillators
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();

	CREATE TRIGGER lastmod15
    BEFORE UPDATE ON potentiometers
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();

	CREATE TRIGGER lastmod16
    BEFORE UPDATE ON power_supplies
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod17
    BEFORE UPDATE ON resistors
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod18
    BEFORE UPDATE ON sensors
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod19
    BEFORE UPDATE ON speakers
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod20
    BEFORE UPDATE ON transformers
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod21
    BEFORE UPDATE ON transistors
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();
	
	CREATE TRIGGER lastmod22
    BEFORE UPDATE ON other
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE PROCEDURE update_date();