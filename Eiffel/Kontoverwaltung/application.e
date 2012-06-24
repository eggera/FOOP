note
	description : "Root class of foop_3 Accounting Exercise"
	date        : "$Date$"
	revision    : "$Revision$"

class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature {NONE} -- Initialization

	make
			-- Run application.
		local
			test_class: TEST
		do
			create test_class
			test_class.runTest
		end
end
