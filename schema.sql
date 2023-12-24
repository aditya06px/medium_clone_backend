-- create a database owner is postgres -----------------------------------------
CREATE DATABASE "read_world"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


\c read_world;

-- Tables ----------------------------------------------------------

CREATE TABLE public.user (
    id serial primary key,
    username varchar(100) not null unique,
    email varchar(250) not null unique,
    password text not null,
    image text,
    bio text,
    google_id text
);

create table public.article (
    id serial primary key,
    title varchar(250) not null,
    slug text not null,
    description varchar(500),
    author_id int not null references public.user(id) on delete cascade,
    body text,
	created_at timestamptz default CURRENT_TIMESTAMP,
	updated_at timestamptz default CURRENT_TIMESTAMP,
    fav_count int default 0
);

create table public.user_follows(
    user_id int not null  references public.user(id) on delete cascade,
    follower_id int not null references public.user(id) on delete cascade,
    primary key(user_id, follower_id)
);

create table public.user_fav_articles(
    article_id int not null  references public.article(id) on delete cascade,
    user_id int not null  references public.user(id) on delete cascade,
    primary key(article_id, user_id)
);

create table public.tag(
    id serial primary key,
    name varchar(250) not null unique
);

create table public.article_tags (
    article_id int not null  references public.article(id) on delete cascade,
    tag_id int not null references public.tag(id) on delete cascade,
    primary key(article_id,tag_id)
);

create table public.comment(
    id serial primary key,
    commenter_id int not null references public.user(id) on delete cascade,
    body text,
    article_id int not null references public.article(id) on delete cascade,
    created_at timestamptz default CURRENT_TIMESTAMP,
	updated_at timestamptz default CURRENT_TIMESTAMP
);

-- Triggers --------------------------------------------------------------------------------------------------------
-- this trigger updates fav_count on article when a row is inserted or deleted from user_fav_articles table.
CREATE OR REPLACE FUNCTION update_article_fav_count()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        -- Increment fav_count on article added to favourite
        UPDATE public.article
        SET fav_count = fav_count + 1
        WHERE id = NEW.article_id;

    ELSIF TG_OP = 'DELETE' THEN
        -- Decrement fav_count on article removed from favourite
        UPDATE public.article
        SET fav_count = fav_count - 1
        WHERE id = OLD.article_id;

    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER article_favorited_trigger
AFTER INSERT OR DELETE ON public.user_fav_articles
FOR EACH ROW
EXECUTE FUNCTION update_article_fav_count();

-- ------------------------------------------------------------------------------------------------------------------