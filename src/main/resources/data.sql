

--This file is for test purposes only!!

delete from posts;
delete from events;

ALTER TABLE posts ALTER COLUMN post_id RESTART WITH 1;
ALTER TABLE events ALTER COLUMN event_id RESTART WITH 1;












