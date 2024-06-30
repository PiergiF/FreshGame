INSERT INTO journalist (id, name, surname) VALUES(nextval('journalist_id_seq'), 'Me', 'Medesimo');
INSERT INTO credentials (id, journalist_id, role, username, password) VALUES(nextval('credentials_id_seq'), 1, 'JOURNALIST', 'g', '$2a$10$COCVOMAk7DDFizP5W2FDeuAk5ddfYSrW3QxAxcv5qoNyF2yDR9TIO'); --pw: g

INSERT INTO game (id, name, genres) VALUES(nextval('game_id_seq'), 'Destiny','{FPS, LOOTER_SHOOTER, LIVE_SERVICE}');
INSERT INTO game (id, name, genres) VALUES(nextval('game_id_seq'), 'Destiny 2','{FPS, LOOTER_SHOOTER, LIVE_SERVICE}');