insert into course (course_id, name, semester, year, instructor)
values ('b7af3970fb3511e4b9390800200c9a66', 'GIS 101', 'fall', '2014', 'Zimmerman');

insert into course (course_id, name, semester, year, instructor)
values ('7664e640fb3511e4b9390800200c9a66', 'GIS 101', 'spring', '2015', 'Cote');

insert into project_resource (id, resource_type)
    values ('32f6d340fb3611e4b9390800200c9a66', 'poster_full_pdf');

insert into project (project_id, title, course_id, poster_id, searchable)
    values ('9a116ff0fb3511e4b9390800200c9a66', 'Test Project', '7664e640fb3511e4b9390800200c9a66', '32f6d340fb3611e4b9390800200c9a66', true);


