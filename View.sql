#----------------------------------------------
#	View Query Test
#	Auther: Ching Wen Chen (102921)
#	Assignment 5
#----------------------------------------------

#----------------------------------------------
#
#	List the character name, party name and
#	party rate which less than 5.
#
#----------------------------------------------

select *
from basicCharacterPartyInfo
where party_rate < 5;

#----------------------------------------------

#----------------------------------------------
#
#	List the character name and race name
#	for charaters.
#
#----------------------------------------------

select character_name, race_name
from otherCharacter;

#---------------------------------------------
