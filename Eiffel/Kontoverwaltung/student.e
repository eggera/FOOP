note
	description: "Summary description for {STUDENT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	STUDENT

inherit
	PERSON
		rename
			make as make_person
		end

create
	make

feature
	make (stud_name: STRING stud_matrNr: STRING)
--	require else
	do
--		name := stud_name
		make_person(stud_name)
		matrNr := stud_matrNr
	end

--access
feature
	matrNr: STRING


end
