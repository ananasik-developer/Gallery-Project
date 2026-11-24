TRUNCATE TABLE artwork_tags, gallery_artworks, likes, subscriptions, artworks, galleries, tags, users RESTART IDENTITY CASCADE;

INSERT INTO users (username, email, password_hash, role, is_active, bio, tg_link, avatar_path) VALUES
('admin', 'admin@gallery.local', '$2a$10$TshWNBHvDKLd7F7Gyy5mSOZV03hVNZyOF2mz7flfE/87flfo7JkdW', 'ADMIN', true,
 'Администратор сайта', '@admin', null),
('laboba', 'laboba@gallery.local', '$2a$10$bYOAiEAtdO2k7fdZ/ks0.OaWmAc5HorSyRC5t5DGbLqIajWawmI0a', 'USER', true,
 'Рисую персонажей в цифре, беру заказы на портреты', '@laboba', '/uploads/avatars/avatar_6_1795207971398.jpg'),
('shishka', 'shishka@gallery.local', '$2a$10$rnJ6ZFVIUXwMmLGzIHS6q.TTSgT1MgHydvFm172XdlE0XYF/5wpSK', 'USER', true,
 'Фоны, окружение, немного 3d. Работаю по описанию', '@shishka', '/uploads/avatars/avatar_7_1795218925346.png'),
('alti', 'alti@gallery.local', '$2a$10$mGGgU5QRrSGRYNGCcME/4eJUDT4Z.4m5jabbPSG8eHZRgjnuFTEXe', 'USER', true,
 null, null, null);

INSERT INTO artworks (title, description, image_path, author_id, status, created_at, updated_at) VALUES
('Красный шарф', 'Портрет персонажа для истории. Изначально рисовался в акриле, потом переведён в цифру.',
 '/uploads/artworks/2026-11-18_1795007324370.jpg', 2, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '12 days', CURRENT_TIMESTAMP - INTERVAL '12 days'),
('Девушка и вороны', 'Серия про птиц и тишину. Третий вариант композиции, первый забракован.',
 '/uploads/artworks/2026-11-18_1795008782981.jpg', 2, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '11 days', CURRENT_TIMESTAMP - INTERVAL '11 days'),
('Ночной город', 'Фон для главы. Свет выставлял слоями, чтобы потом было проще менять время суток.',
 '/uploads/artworks/2026-11-18_1795013169605.jpg', 2, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '10 days'),
('Портрет с татуировкой', 'Заказной портрет, делался по фотографии. Татуировку согласовывали три раза.',
 '/uploads/artworks/2026-11-18_1795013235287.jpg', 2, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '9 days', CURRENT_TIMESTAMP - INTERVAL '9 days'),
('Незаконченный скетч', 'Лежит в работе, руки ещё не проработаны.',
 '/uploads/artworks/2026-11-18_1795014463767.jpg', 2, 'DRAFT',
 CURRENT_TIMESTAMP - INTERVAL '8 days', CURRENT_TIMESTAMP - INTERVAL '8 days'),
('Лесная тропа', 'Утренний свет в лесу, проба новой кисти для листвы.',
 '/uploads/artworks/2026-11-18_1795014862312.jpg', 3, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '7 days', CURRENT_TIMESTAMP - INTERVAL '7 days'),
('Горный перевал', 'Фон для уровня. Камни собирал из десятка референсов.',
 '/uploads/artworks/2026-11-18_1795017526503.jpg', 3, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '6 days', CURRENT_TIMESTAMP - INTERVAL '6 days'),
('Старый мост', 'Этюд на один вечер. Нравится, как легла вода.',
 '/uploads/artworks/2026-11-20_1795205604094.jpg', 3, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '5 days'),
('Туман над рекой', 'Черновик, ещё не решил с цветом.',
 '/uploads/artworks/2026-11-20_1795206302473.jpg', 3, 'DRAFT',
 CURRENT_TIMESTAMP - INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '4 days'),
('Кот в коробке', 'Быстрый домашний скетч, рисовался по фотографии.',
 '/uploads/artworks/2026-11-20_1795207540634.jpg', 4, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_TIMESTAMP - INTERVAL '3 days'),
('Цветы на подоконнике', 'Натюрморт с натуры, пока стояли цветы.',
 '/uploads/artworks/2026-11-21_1795218961523.jpg', 4, 'PUBLISHED',
 CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),
('Быстрый набросок', 'Проба кисти, к публикации не готов.',
 '/uploads/artworks/2026-11-21_1795269175764.jpg', 4, 'DRAFT',
 CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '1 day');

INSERT INTO galleries (title, description, cover_artwork_id, author_id, available_for_commission, featured) VALUES
('Персонажи', 'Портреты и персонажи. Беру заказы от 3000 рублей, срок от трёх дней, правки входят в цену.',
 1, 2, true, true),
('Городские зарисовки', 'Фоны и зарисовки города. Работы из личных проектов, часть можно взять в работу.',
 3, 2, false, false),
('Природа', 'Фоны для игр и анимации. Цена от 5000 рублей зависит от детализации и размера.',
 6, 3, true, true),
('Домашнее', 'Небольшие работы для себя, для примера уровня детализации.',
 10, 4, true, false),
('Эксперименты', 'Пробы стиля, к заказам не относятся.',
 null, 4, false, false);

INSERT INTO gallery_artworks (gallery_id, artwork_id, sort_order) VALUES
(1, 1, 0),
(1, 2, 1),
(1, 4, 2),
(2, 3, 0),
(3, 6, 0),
(3, 7, 1),
(3, 8, 2),
(4, 10, 0),
(4, 11, 1);

INSERT INTO tags (name, category) VALUES
('female', 'GENERAL'),
('male', 'GENERAL'),
('furry', 'GENERAL'),
('nature', 'META'),
('digital', 'META'),
('pixel', 'META'),
('3d', 'META'),
('Subaru', 'CHARACTER'),
('Choya', 'CHARACTER'),
('Syao', 'CHARACTER'),
('Tanako', 'CHARACTER'),
('Tanjiro', 'CHARACTER');

INSERT INTO artwork_tags (artwork_id, tag_id) VALUES
(1, 1),
(1, 5),
(2, 1),
(2, 4),
(3, 6),
(4, 2),
(4, 5),
(6, 4),
(7, 4),
(7, 5),
(8, 4),
(10, 3),
(11, 4),
(12, 7);

INSERT INTO likes (user_id, artwork_id) VALUES
(3, 1),
(4, 1),
(3, 2),
(4, 3),
(4, 4),
(2, 6),
(4, 6),
(2, 7),
(4, 8),
(2, 10),
(3, 10),
(3, 11);

INSERT INTO subscriptions (subscriber_id, subscribed_to_id) VALUES
(4, 2),
(2, 3),
(3, 2),
(4, 3);
