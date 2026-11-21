CREATE TABLE users (
    id bigserial PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    email varchar(100) NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    role varchar(10) DEFAULT 'USER' CHECK (role IN ('USER', 'ADMIN')),
    is_active boolean DEFAULT true,
    avatar_path varchar(255),
    bio text,
    tg_link varchar(100),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE artworks (
    id bigserial PRIMARY KEY,
    title varchar(128) NOT NULL,
    description text,
    image_path varchar(255) NOT NULL,
    author_id bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status varchar(10) DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'PUBLISHED')),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_artworks_author ON artworks (author_id);

CREATE INDEX idx_artworks_status ON artworks (status);

CREATE TABLE galleries (
    id bigserial PRIMARY KEY,
    title varchar(128) NOT NULL,
    description text,
    cover_artwork_id bigint REFERENCES artworks (id) ON DELETE SET NULL,
    author_id bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    available_for_commission boolean NOT NULL DEFAULT false,
    featured boolean NOT NULL DEFAULT false
);

CREATE TABLE gallery_artworks (
    gallery_id bigint NOT NULL REFERENCES galleries (id) ON DELETE CASCADE,
    artwork_id bigint NOT NULL REFERENCES artworks (id) ON DELETE CASCADE,
    sort_order integer DEFAULT 0,
    PRIMARY KEY (gallery_id, artwork_id)
);

CREATE INDEX idx_gallery_artworks_gallery ON gallery_artworks (gallery_id);

CREATE TABLE tags (
    id bigserial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE,
    category varchar(20) NOT NULL CHECK (category IN ('GENERAL', 'META', 'CHARACTER'))
);

CREATE TABLE artwork_tags (
    artwork_id bigint NOT NULL REFERENCES artworks (id) ON DELETE CASCADE,
    tag_id bigint NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    PRIMARY KEY (artwork_id, tag_id)
);

CREATE TABLE likes (
    user_id bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    artwork_id bigint NOT NULL REFERENCES artworks (id) ON DELETE CASCADE,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, artwork_id)
);

CREATE INDEX idx_likes_artwork ON likes (artwork_id);

CREATE TABLE subscriptions (
    subscriber_id bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    subscribed_to_id bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (subscriber_id, subscribed_to_id),
    CHECK (subscriber_id <> subscribed_to_id)
);

CREATE INDEX idx_subscriptions_subscribed ON subscriptions (subscribed_to_id);
