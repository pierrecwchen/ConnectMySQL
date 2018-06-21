#------------------------------------------------------
#	Game Characters Database
#	Auther: Ching Wen Chen (102921)
#	Assignment 5
#	
#	There is NO engine support check constraint
# 	in mySQL, and only innodb support foreign keys.
#------------------------------------------------------


#---------------------------------------------------------
#
#	Create a characters table
#
#---------------------------------------------------------

drop table characters;

create table characters(
characterID  char(5)	PRIMARY KEY,
characterName	      varchar(100),
characterLevel	      int,
gender	char(1),

check(gender = 'F' OR gender = 'M' AND characterLevel >= 1)
);

load data local infile "Characters.dat" into table characters;

#---------------------------------------------

#---------------------------------------------------------
#
#	Create a parties table
#
#---------------------------------------------------------

drop table parties;

create table parties(
partyID	   char(5)	PRIMARY KEY,
partyName  varchar(100),
numberOfMember	int,
rate		int,

check(numberOfMember >= 1 AND rate >= 1)
);

load data local infile "Parties.dat" into table parties;

#--------------------------------------------

#---------------------------------------------------------
#
#	Create a partyMembers table
#
#---------------------------------------------------------

drop table partyMembers;

create table partyMembers(
characterID  char(5),
partyID	     char(20),
positionC	char(50),

foreign key(partyID) references parties(partyID),
primary key(characterID, partyID)
);

load data local infile "PartyMembers.dat" into table partyMembers;

#--------------------------------------------

#---------------------------------------------------------
#
#	Create a weapons table
#
#---------------------------------------------------------

drop table weapons;

create table weapons(
weaponName   char(100)	primary key,
weaponType	char(70),
attackSpeed  float,
attackRange  float,
introductionW	char(255),

check(attackSpeed >= 1.0 AND attackRange >= 1.0)
);

load data local infile "Weapons.dat" into table weapons;

#------------------------------------------

#---------------------------------------------------------
#
#	Create a skills table
#
#---------------------------------------------------------

drop table skills;

create table skills(
skillName    char(100)	primary key,
skillRange   float,
introductionS	char(255),
coolDown	float,
skillLevel int,

check (skillRange >= 1.0 and skillRange <= 20.0 and skillLevel >= 1)
);

load data local infile "Skills.dat" into table skills;

#-------------------------------------------

#---------------------------------------------------------
#
#	Create a learns table
#
#---------------------------------------------------------

drop table learns;

create table learns(
skillName    char(100) references skills(skillName),
characterID  char(5),
price	    float,
primary key(skillName, characterID)
);

load data local infile "Learns.dat" into table learns;

#------------------------------------------

#---------------------------------------------------------
#
#	Create a houses table
#
#---------------------------------------------------------

drop table houses;

create table houses(
houseID	     char(5)	primary key,
houseLocation		char(100),
houseType		char(100),
isLocked		boolean
);

load data local infile "Houses.dat" into table houses;

#------------------------------------------

#---------------------------------------------------------
#
#	Create a races table
#
#---------------------------------------------------------

drop table races;

create table races(
raceName     char(100)	primary key,
trait	     char(150),
traitInfo	char(255),
introductionR	char(255)
);

load data local infile "Race.dat" into table races;

#----------------------------------------------------------------------------

#----------------------------------------------------------------------------
#
#	Create a characterList table
#
#----------------------------------------------------------------------------

drop table characterList;

create table characterList(
raceName   char(100),
characterID	char(5),
weaponName	char(100),
houseID		char(5),
primary key(raceName, characterID, weaponName, houseID)
);

load data local infile "CharacterList.dat" into table characterList;


#---------------------------------------------------------------------------

#---------------------------------------------------------------------------
#
#	Create a view which shows only character name, character level, and race name.
#
#---------------------------------------------------------------------------

drop view otherCharacter;

create view otherCharacter (character_name, character_level, race_name) as 
	select characters.characterName, characters.characterLevel, characterList.raceName
	from characters join characterList on characters.characterID = characterList.characterID;

#---------------------------------------------------------------------------

#---------------------------------------------------------------------------
#
#	Create a view which shows only character name, party name, and party rate.
#
#---------------------------------------------------------------------------

drop view basicCharacterPartyInfo;

create view basicCharacterPartyInfo (character_name, party_name, party_rate) as 
	select characters.characterName, parties.partyName, parties.rate
	from characters join partyMembers on characters.characterID = partyMembers.characterID join
		parties on parties.partyID = partyMembers.partyID;



