#----------------------------------------------
#	Index Query Test
#	Auther: Ching Wen Chen (102921)
#	Assignment 5
#----------------------------------------------

#----------------------------------------------
#
#	Test the execution time between using
#	and not using index.
#
#----------------------------------------------

select now();

select * from weapons where weaponType = 'weaponType1';

select now();

#---------------------------------------------

create index weapon_type on weapons(weaponType);

#---------------------------------------------

select now();

select * from weapons where weaponType = 'weaponType1';

select now();
