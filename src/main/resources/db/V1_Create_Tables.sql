CREATE TABLE users (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    username varchar(100) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE subscribers (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint NOT NULL,
    target_user_id bigint NOT NULL,
    is_friend boolean default false,
    create_at timestamp default now(),
    update_at timestamp default now(),
    status varchar(25) default 'ACTIVE'
);

CREATE TABLE access_tokens (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint,
    token varchar(255),
    created_at timestamp default now(),
    expires_at timestamp
);

CREATE TABLE refresh_tokens (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint,
    token varchar(255),
    created_at timestamp default now(),
    expires_at timestamp
);

CREATE TABLE posts(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title varchar(255),
    text text,
    user_id bigint references users(id) ON DELETE CASCADE,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE images(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    image_data bytea,
    post_id bigint references posts(id) ON DELETE CASCADE,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE friendship_requests(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    from_user_id bigint NOT NULL,
    to_user_id bigint NOT NULL,
    request_status varchar(25) NOT NULL,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE subscribers(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint NOT NULL,
    target_user_id bigint NOT NULL,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE subscriptions(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint NOT NULL,
    follower_user_id bigint NOT NULL,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE user_friend(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint NOT NULL,
    friend_id bigint NOT NULL,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);

CREATE TABLE messages(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    sender_id bigint NOT NULL,
    receiver_id bigint NOT NULL,
    content text NOT NULL,
    create_at timestamp NOT NULL,
    update_at timestamp NOT NULL,
    status varchar(25) NOT NULL
);