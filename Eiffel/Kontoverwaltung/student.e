note
	description: "Summary description for {STUDENT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	STUDENT

inherit
	PERSON

--export {ANY} all end

create
	make

feature
	make (s_name: STRING)
	do
		name := s_name
	end



end
