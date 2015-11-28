drop database if exists "ohmbaseopenwei";
create database "ohmbaseopenwei";
set datestyle to 'SQL, DMY';

create table amplifiers
(
	name text null constraint valid_name check (length(name) >0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table batteries
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
	);
create table buzzers
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table capacitors
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table diodes
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table fuses
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table inductors
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table isolators
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table leds
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table logic_gates
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table microcontrollers
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table microphones
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table motors
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table oscillators
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table potentiometers
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table power_supplies
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table resistors
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table sensors
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table speakers
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table transformers
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table transistors
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table other
(
	name text null constraint valid_name check (length(name) > 0),
	notes text,
	quantity integer null constraint valid_quantity check (quantity >= 0),
	last_modified date default current_date,
	spec_sheets text,
	location text null constraint valid_location check (length(location) > 0)
);
create table account
(
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