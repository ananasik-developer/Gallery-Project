TRUNCATE TABLE artwork_tags, gallery_artworks, likes, subscriptions, artworks, galleries, tags, users RESTART IDENTITY CASCADE;

SET client_encoding = 'UTF8';

INSERT INTO public.users (id, username, email, password_hash, role, is_active, avatar_path, bio, tg_link, created_at) VALUES (2, 'katushka', 'katushka@local.test', '$2a$10$C0fuUesN2WEfRtUEWtVBtubDoQgP9s..DtjXMif6OIf6R7DrObsz2', 'USER', true, '/uploads/avatars/avatar_2_1795795338983.png', 'чиловая герл', '@bytheway31', '2026-11-27 19:00:19.278642');

INSERT INTO public.users (id, username, email, password_hash, role, is_active, avatar_path, bio, tg_link, created_at) VALUES (3, 'adel', 'adel@local.test', '$2a$10$9xQv.Jim6XHF9DrnXvkVxusq.DQtOusSMn2XVJbpieXZMHoCgehDa', 'USER', true, '/uploads/avatars/avatar_3_1795795635183.jpg', 'люблю Солида', '@koshmarikk1', '2026-11-27 19:06:20.120197');

INSERT INTO public.users (id, username, email, password_hash, role, is_active, avatar_path, bio, tg_link, created_at) VALUES (4, 'benne', 'benne@local.test', '$2a$10$ZaVb9PRKaGC2bf8VCWi0vOBndbCqZ.1ar1ShOBMFT.fnSGEiXydue', 'USER', true, '/uploads/avatars/avatar_4_1795796539668.jpg', 'где мой пиздюлятор', '@benneakxsys', '2026-11-27 19:20:27.00449');

INSERT INTO public.users (id, username, email, password_hash, role, is_active, avatar_path, bio, tg_link, created_at) VALUES (1, 'admin', 'admin@gallery.local', '$2a$10$Nvn54JT9ROhagXCbZbxF9OGIx08LVnoGrEcrP5UJd5bavrfjNjcmC', 'ADMIN', true, '/uploads/avatars/avatar_1_1795797601010.png', 'Администратор сайта', '@admin', '2026-11-27 18:33:23.504135');

INSERT INTO public.users (id, username, email, password_hash, role, is_active, avatar_path, bio, tg_link, created_at) VALUES (5, 'werngral', 'werngral@local.test', '$2a$10$9sclzHe49tVgSZOw7LucBusfSc1SYK2YGrUpzZRtDXo.xwNH3tFo.', 'USER', true, '/uploads/avatars/avatar_5_1795797750287.png', NULL, '@nanobanana', '2026-11-27 19:41:00.837307');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (1, 'Xingqiu', 'это мой милый мальчик', '/uploads/artworks/2026-11-27_1795795462645.jpg', 2, 'PUBLISHED', '2026-11-27 19:04:22.79704', '2026-11-27 19:04:39.876214');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (2, 'girl', 'very cute girl', '/uploads/artworks/2026-11-27_1795795534784.png', 2, 'PUBLISHED', '2026-11-27 19:05:35.009152', '2026-11-27 19:05:43.447642');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (3, 'Miller', 'best boy', '/uploads/artworks/2026-11-27_1795795790351.jpg', 3, 'PUBLISHED', '2026-11-27 19:09:50.437251', '2026-11-27 19:10:14.632303');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (4, 'My little Solid Snake', 'О черт, я его обожаю', '/uploads/artworks/2026-11-27_1795796074794.jpg', 3, 'PUBLISHED', '2026-11-27 19:14:34.987416', '2026-11-27 19:14:37.517186');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (5, 'eyeye', 'something beautiful', '/uploads/artworks/2026-11-27_1795796247328.jpg', 3, 'PUBLISHED', '2026-11-27 19:17:27.532988', '2026-11-27 19:17:29.203546');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (6, 'Chiyo', 'каштанка милая', '/uploads/artworks/2026-11-27_1795796388067.png', 2, 'PUBLISHED', '2026-11-27 19:19:48.253967', '2026-11-27 19:19:50.019814');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (7, 'эмелджи чука', 'беееем', '/uploads/artworks/2026-11-27_1795796646643.jpg', 4, 'PUBLISHED', '2026-11-27 19:24:06.733715', '2026-11-27 19:24:07.956545');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (8, 'Мой любимый орис', 'описание', '/uploads/artworks/2026-11-27_1795797150274.jpg', 4, 'PUBLISHED', '2026-11-27 19:32:30.430654', '2026-11-27 19:32:31.979333');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (9, 'пивасик', 'пивасик', '/uploads/artworks/2026-11-27_1795797305249.png', 4, 'PUBLISHED', '2026-11-27 19:35:05.44279', '2026-11-27 19:35:08.254069');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (10, 'Stelle', 'stelle', '/uploads/artworks/2026-11-27_1795797371189.png', 2, 'PUBLISHED', '2026-11-27 19:36:11.374731', '2026-11-27 19:36:14.190243');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (12, 'не ии чесн', 'чесн', '/uploads/artworks/2026-11-27_1795797783528.png', 5, 'PUBLISHED', '2026-11-27 19:43:03.801282', '2026-11-27 19:43:05.250784');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (13, 'ну чесн', 'чесн не аи ну клянись', '/uploads/artworks/2026-11-27_1795797859420.png', 5, 'PUBLISHED', '2026-11-27 19:44:19.664085', '2026-11-27 19:44:24.786396');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (14, 'ai not ai aiaiaiaiaiaiai', 'ai', '/uploads/artworks/2026-11-27_1795797917611.png', 5, 'PUBLISHED', '2026-11-27 19:45:17.968854', '2026-11-27 19:45:19.871571');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (15, 'Никооооо', 'Белик', '/uploads/artworks/2026-11-27_1795798145179.jpg', 3, 'PUBLISHED', '2026-11-27 19:49:05.280049', '2026-11-27 19:49:10.283876');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (16, 'big bear', 'ooooh sh', '/uploads/artworks/2026-11-27_1795798208800.jpg', 3, 'PUBLISHED', '2026-11-27 19:50:08.906011', '2026-11-27 19:50:10.82481');

INSERT INTO public.artworks (id, title, description, image_path, author_id, status, created_at, updated_at) VALUES (18, 'red', 'red', '/uploads/artworks/2026-11-27_1795798750079.png', 1, 'PUBLISHED', '2026-11-27 19:59:10.247108', '2026-11-27 19:59:11.616035');

INSERT INTO public.tags (id, name, category) VALUES (1, 'female', 'GENERAL');

INSERT INTO public.tags (id, name, category) VALUES (2, 'male', 'GENERAL');

INSERT INTO public.tags (id, name, category) VALUES (3, 'furry', 'GENERAL');

INSERT INTO public.tags (id, name, category) VALUES (4, 'nature', 'META');

INSERT INTO public.tags (id, name, category) VALUES (5, 'digital', 'META');

INSERT INTO public.tags (id, name, category) VALUES (6, 'pixel', 'META');

INSERT INTO public.tags (id, name, category) VALUES (7, '3d', 'META');

INSERT INTO public.tags (id, name, category) VALUES (8, 'Subaru', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (9, 'Choya', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (10, 'Syao', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (11, 'Tanako', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (12, 'Tanjiro', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (13, 'sketch', 'GENERAL');

INSERT INTO public.tags (id, name, category) VALUES (14, 'Cats', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (15, '67', 'GENERAL');

INSERT INTO public.tags (id, name, category) VALUES (16, 'xingqiu', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (17, 'Kazuhira Miller', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (18, 'Solid Snake', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (19, 'impressionism', 'META');

INSERT INTO public.tags (id, name, category) VALUES (20, 'Mikoshi Chiyo', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (21, 'oni', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (22, 'Link', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (23, 'star boy', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (24, 'Stelle', 'CHARACTER');

INSERT INTO public.tags (id, name, category) VALUES (25, 'witch', 'META');

INSERT INTO public.tags (id, name, category) VALUES (26, 'Niko Bellic', 'META');

INSERT INTO public.tags (id, name, category) VALUES (27, 'выапвы', 'META');

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (1, 15);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (1, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (1, 16);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (2, 15);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (2, 1);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (2, 13);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (3, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (3, 13);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (3, 17);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (4, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (4, 18);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (5, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (5, 19);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (6, 1);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (6, 20);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (6, 21);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (7, 15);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (8, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (8, 22);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (8, 23);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (9, 15);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (9, 1);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (10, 1);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (10, 24);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (12, 1);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (12, 6);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (13, 1);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (13, 25);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (14, 6);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (15, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (15, 26);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (16, 2);

INSERT INTO public.artwork_tags (artwork_id, tag_id) VALUES (18, 1);

INSERT INTO public.galleries (id, title, description, cover_artwork_id, author_id, created_at, available_for_commission, featured) VALUES (1, 'Мои хорошики', 'Моя первая галерея', NULL, 2, '2026-11-27 19:37:17.814628', false, true);

INSERT INTO public.galleries (id, title, description, cover_artwork_id, author_id, created_at, available_for_commission, featured) VALUES (2, 'моя не ии галерея', 'честно', NULL, 5, '2026-11-27 19:45:50.405345', false, true);

INSERT INTO public.galleries (id, title, description, cover_artwork_id, author_id, created_at, available_for_commission, featured) VALUES (3, 'Комишки от 500р', 'хэд - 500
боди - 1000
фулл боди - 1500
фон - доп. 200', NULL, 3, '2026-11-27 19:52:09.506214', true, false);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (1, 10, 0);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (1, 1, 1);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (1, 6, 2);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (2, 14, 0);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (2, 12, 1);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (3, 16, 0);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (3, 15, 1);

INSERT INTO public.gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (3, 3, 2);

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (2, 1, '2026-11-27 19:36:41.592182');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (2, 6, '2026-11-27 19:38:10.191568');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (2, 2, '2026-11-27 19:38:14.821358');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (5, 14, '2026-11-27 19:46:39.87216');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (3, 16, '2026-11-27 19:50:15.402341');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (3, 6, '2026-11-27 19:52:59.106882');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (3, 10, '2026-11-27 19:53:05.083844');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (3, 9, '2026-11-27 19:53:09.35747');

INSERT INTO public.likes (user_id, artwork_id, created_at) VALUES (3, 8, '2026-11-27 19:53:15.61547');

SELECT pg_catalog.setval('public.artworks_id_seq', 18, true);

SELECT pg_catalog.setval('public.galleries_id_seq', 4, true);

SELECT pg_catalog.setval('public.tags_id_seq', 27, true);

SELECT pg_catalog.setval('public.users_id_seq', 5, true);
