note
	description: "Summary description for {PENSIONIST}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	PENSIONIST

inherit
	PERSON
		rename
			make as make_person
		end

create
	make

feature
	make (pens_name: STRING)
	do
		make_person(pens_name)
	end

end
