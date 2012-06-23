note
	description: "Summary description for {PENSIONIST}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	PENSIONIST

inherit
	PERSON

create
	make

feature
	make (p_name: STRING)
	do
		name := p_name
	end

end
