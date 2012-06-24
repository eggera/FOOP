note
	description: "Summary description for {PERSON}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	PERSON

create
	make

feature
	-- constructor
	make(pers_name: STRING)
	do
		setName(pers_name)
	end

-- members
feature
	name: STRING

-- setters
feature
	setName(pers_name: STRING)
	require
		nameNotVoid: pers_name /= Void
	do
		name := pers_name
	end

end
