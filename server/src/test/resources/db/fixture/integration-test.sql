/*
  This file gets executed by tests using the ApplicationRule when it is specified with either Reload.PER_TEST or PER_TEST_CLASS
*/

DELETE FROM dummy_tbl;

INSERT INTO dummy_tbl (id, val) VALUES (1, 'ONE'), (2, 'TWO'), (3, 'Three');
