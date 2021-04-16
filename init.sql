create table review(id int);
/*DROP TABLE IF EXISTS review;
DROP SEQUENCE IF EXISTS SEQ_REVIEW;
CREATE SEQUENCE "SEQ_REVIEW" INCREMENT 1 MINVALUE 1 START 1;
CREATE TABLE public.review (
    "id" int DEFAULT nextval('"SEQ_REVIEW"') NOT NULL,
    "title" varchar(255) NOT NULL,
    "description" varchar(255) NOT NULL,
    "points" int DEFAULT 0 NOT NULL,
    "created_by" varchar(255) NOT NULL,
    "created_date" timestamp NOT NULL,
    "last_modified_by" varchar(255) NOT NULL,
    "last_modified_date" timestamp NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0;*/