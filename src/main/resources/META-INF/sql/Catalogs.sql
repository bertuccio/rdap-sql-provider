	INSERT INTO rdap.status VALUES(1,'validated');
	INSERT INTO rdap.status VALUES(2,'renew prohibited');
	INSERT INTO rdap.status VALUES(3,'update prohibited');
	INSERT INTO rdap.status VALUES(4,'transfer prohibited');
	INSERT INTO rdap.status VALUES(5,'delete prohibited');
	INSERT INTO rdap.status VALUES(6,'proxy');
	INSERT INTO rdap.status VALUES(7,'private');
	INSERT INTO rdap.status VALUES(8,'removed');
	INSERT INTO rdap.status VALUES(9,'obscured');
	INSERT INTO rdap.status VALUES(10,'associated');
	INSERT INTO rdap.status VALUES(11,'active');
	INSERT INTO rdap.status VALUES(12,'inactive');
	INSERT INTO rdap.status VALUES(13,'locked');
	INSERT INTO rdap.status VALUES(14,'pending create');
	INSERT INTO rdap.status VALUES(15,'pending renew');
	INSERT INTO rdap.status VALUES(16,'pending transfer');
	INSERT INTO rdap.status VALUES(17,'pending update');
	INSERT INTO rdap.status VALUES(18,'pending delete');
	
	INSERT INTO rdap.event_action VALUES(1,'registration');
	INSERT INTO rdap.event_action VALUES(2,'reregistration');
	INSERT INTO rdap.event_action VALUES(3,'last changed');
	INSERT INTO rdap.event_action VALUES(4,'expiration');
	INSERT INTO rdap.event_action VALUES(5,'deletion');
	INSERT INTO rdap.event_action VALUES(6,'reinstantiation');
	INSERT INTO rdap.event_action VALUES(7,'transfer');
	INSERT INTO rdap.event_action VALUES(8,'locked');
	INSERT INTO rdap.event_action VALUES(9,'unlocked');
	INSERT INTO rdap.event_action VALUES(10,'last update of RDAP database');
	INSERT INTO rdap.event_action VALUES(11,'registrar expiration');
	INSERT INTO rdap.event_action VALUES(12,'enum validation expiration');

	INSERT INTO rdap.relation VALUES(1, 'registered');
	INSERT INTO rdap.relation VALUES(2, 'unregistered');
	INSERT INTO rdap.relation VALUES(3, 'registration restricted');
	INSERT INTO rdap.relation VALUES(4, 'open registration');
	INSERT INTO rdap.relation VALUES(5, 'conjoined');

	INSERT INTO rdap.roles VALUES (1,'registrant');
	INSERT INTO rdap.roles VALUES (2,'technical');
	INSERT INTO rdap.roles VALUES (3,'administrative');
	INSERT INTO rdap.roles VALUES (4,'abuse');
	INSERT INTO rdap.roles VALUES (5,'billing');
	INSERT INTO rdap.roles VALUES (6,'registrar');
	INSERT INTO rdap.roles VALUES (7,'reseller');
	INSERT INTO rdap.roles VALUES (8,'sponsor');
	INSERT INTO rdap.roles VALUES (9,'proxy');
	INSERT INTO rdap.roles VALUES (10,'notifications');
	INSERT INTO rdap.roles VALUES (11,'noc');

	INSERT INTO rdap.country_code VALUES (4,'AF');
	INSERT INTO rdap.country_code VALUES (248,'AX');
	INSERT INTO rdap.country_code VALUES (8,'AL');
	INSERT INTO rdap.country_code VALUES (12,'DZ');
	INSERT INTO rdap.country_code VALUES (16,'AS');
	INSERT INTO rdap.country_code VALUES (20,'AD');
	INSERT INTO rdap.country_code VALUES (24,'AO');
	INSERT INTO rdap.country_code VALUES (660,'AI');
	INSERT INTO rdap.country_code VALUES (10,'AQ');
	INSERT INTO rdap.country_code VALUES (28,'AG');
	INSERT INTO rdap.country_code VALUES (32,'AR');
	INSERT INTO rdap.country_code VALUES (51,'AM');
	INSERT INTO rdap.country_code VALUES (533,'AW');
	INSERT INTO rdap.country_code VALUES (36,'AU');
	INSERT INTO rdap.country_code VALUES (40,'AT');
	INSERT INTO rdap.country_code VALUES (31,'AZ');
	INSERT INTO rdap.country_code VALUES (44,'BS');
	INSERT INTO rdap.country_code VALUES (48,'BH');
	INSERT INTO rdap.country_code VALUES (50,'BD');
	INSERT INTO rdap.country_code VALUES (52,'BB');
	INSERT INTO rdap.country_code VALUES (112,'BY');
	INSERT INTO rdap.country_code VALUES (56,'BE');
	INSERT INTO rdap.country_code VALUES (84,'BZ');
	INSERT INTO rdap.country_code VALUES (204,'BJ');
	INSERT INTO rdap.country_code VALUES (60,'BM');
	INSERT INTO rdap.country_code VALUES (64,'BT');
	INSERT INTO rdap.country_code VALUES (68,'BO');
	INSERT INTO rdap.country_code VALUES (70,'BA');
	INSERT INTO rdap.country_code VALUES (72,'BW');
	INSERT INTO rdap.country_code VALUES (74,'BV');
	INSERT INTO rdap.country_code VALUES (76,'BR');
	INSERT INTO rdap.country_code VALUES (92,'VG');
	INSERT INTO rdap.country_code VALUES (86,'IO');
	INSERT INTO rdap.country_code VALUES (96,'BN');
	INSERT INTO rdap.country_code VALUES (100,'BG');
	INSERT INTO rdap.country_code VALUES (854,'BF');
	INSERT INTO rdap.country_code VALUES (108,'BI');
	INSERT INTO rdap.country_code VALUES (116,'KH');
	INSERT INTO rdap.country_code VALUES (120,'CM');
	INSERT INTO rdap.country_code VALUES (124,'CA');
	INSERT INTO rdap.country_code VALUES (132,'CV');
	INSERT INTO rdap.country_code VALUES (136,'KY');
	INSERT INTO rdap.country_code VALUES (140,'CF');
	INSERT INTO rdap.country_code VALUES (148,'TD');
	INSERT INTO rdap.country_code VALUES (152,'CL');
	INSERT INTO rdap.country_code VALUES (156,'CN');
	INSERT INTO rdap.country_code VALUES (344,'HK');
	INSERT INTO rdap.country_code VALUES (446,'MO');
	INSERT INTO rdap.country_code VALUES (162,'CX');
	INSERT INTO rdap.country_code VALUES (166,'CC');
	INSERT INTO rdap.country_code VALUES (170,'CO');
	INSERT INTO rdap.country_code VALUES (174,'KM');
	INSERT INTO rdap.country_code VALUES (178,'CG');
	INSERT INTO rdap.country_code VALUES (180,'CD');
	INSERT INTO rdap.country_code VALUES (184,'CK');
	INSERT INTO rdap.country_code VALUES (188,'CR');
	INSERT INTO rdap.country_code VALUES (384,'CI');
	INSERT INTO rdap.country_code VALUES (191,'HR');
	INSERT INTO rdap.country_code VALUES (192,'CU');
	INSERT INTO rdap.country_code VALUES (196,'CY');
	INSERT INTO rdap.country_code VALUES (203,'CZ');
	INSERT INTO rdap.country_code VALUES (208,'DK');
	INSERT INTO rdap.country_code VALUES (262,'DJ');
	INSERT INTO rdap.country_code VALUES (212,'DM');
	INSERT INTO rdap.country_code VALUES (214,'DO');
	INSERT INTO rdap.country_code VALUES (218,'EC');
	INSERT INTO rdap.country_code VALUES (818,'EG');
	INSERT INTO rdap.country_code VALUES (222,'SV');
	INSERT INTO rdap.country_code VALUES (226,'GQ');
	INSERT INTO rdap.country_code VALUES (232,'ER');
	INSERT INTO rdap.country_code VALUES (233,'EE');
	INSERT INTO rdap.country_code VALUES (231,'ET');
	INSERT INTO rdap.country_code VALUES (238,'FK');
	INSERT INTO rdap.country_code VALUES (234,'FO');
	INSERT INTO rdap.country_code VALUES (242,'FJ');
	INSERT INTO rdap.country_code VALUES (246,'FI');
	INSERT INTO rdap.country_code VALUES (250,'FR');
	INSERT INTO rdap.country_code VALUES (254,'GF');
	INSERT INTO rdap.country_code VALUES (258,'PF');
	INSERT INTO rdap.country_code VALUES (260,'TF');
	INSERT INTO rdap.country_code VALUES (266,'GA');
	INSERT INTO rdap.country_code VALUES (270,'GM');
	INSERT INTO rdap.country_code VALUES (268,'GE');
	INSERT INTO rdap.country_code VALUES (276,'DE');
	INSERT INTO rdap.country_code VALUES (288,'GH');
	INSERT INTO rdap.country_code VALUES (292,'GI');
	INSERT INTO rdap.country_code VALUES (300,'GR');
	INSERT INTO rdap.country_code VALUES (304,'GL');
	INSERT INTO rdap.country_code VALUES (308,'GD');
	INSERT INTO rdap.country_code VALUES (312,'GP');
	INSERT INTO rdap.country_code VALUES (316,'GU');
	INSERT INTO rdap.country_code VALUES (320,'GT');
	INSERT INTO rdap.country_code VALUES (831,'GG');
	INSERT INTO rdap.country_code VALUES (324,'GN');
	INSERT INTO rdap.country_code VALUES (624,'GW');
	INSERT INTO rdap.country_code VALUES (328,'GY');
	INSERT INTO rdap.country_code VALUES (332,'HT');
	INSERT INTO rdap.country_code VALUES (334,'HM');
	INSERT INTO rdap.country_code VALUES (336,'VA');
	INSERT INTO rdap.country_code VALUES (340,'HN');
	INSERT INTO rdap.country_code VALUES (348,'HU');
	INSERT INTO rdap.country_code VALUES (352,'IS');
	INSERT INTO rdap.country_code VALUES (356,'IN');
	INSERT INTO rdap.country_code VALUES (360,'ID');
	INSERT INTO rdap.country_code VALUES (364,'IR');
	INSERT INTO rdap.country_code VALUES (368,'IQ');
	INSERT INTO rdap.country_code VALUES (372,'IE');
	INSERT INTO rdap.country_code VALUES (833,'IM');
	INSERT INTO rdap.country_code VALUES (376,'IL');
	INSERT INTO rdap.country_code VALUES (380,'IT');
	INSERT INTO rdap.country_code VALUES (388,'JM');
	INSERT INTO rdap.country_code VALUES (392,'JP');
	INSERT INTO rdap.country_code VALUES (832,'JE');
	INSERT INTO rdap.country_code VALUES (400,'JO');
	INSERT INTO rdap.country_code VALUES (398,'KZ');
	INSERT INTO rdap.country_code VALUES (404,'KE');
	INSERT INTO rdap.country_code VALUES (296,'KI');
	INSERT INTO rdap.country_code VALUES (408,'KP');
	INSERT INTO rdap.country_code VALUES (410,'KR');
	INSERT INTO rdap.country_code VALUES (414,'KW');
	INSERT INTO rdap.country_code VALUES (417,'KG');
	INSERT INTO rdap.country_code VALUES (418,'LA');
	INSERT INTO rdap.country_code VALUES (428,'LV');
	INSERT INTO rdap.country_code VALUES (422,'LB');
	INSERT INTO rdap.country_code VALUES (426,'LS');
	INSERT INTO rdap.country_code VALUES (430,'LR');
	INSERT INTO rdap.country_code VALUES (434,'LY');
	INSERT INTO rdap.country_code VALUES (438,'LI');
	INSERT INTO rdap.country_code VALUES (440,'LT');
	INSERT INTO rdap.country_code VALUES (442,'LU');
	INSERT INTO rdap.country_code VALUES (807,'MK');
	INSERT INTO rdap.country_code VALUES (450,'MG');
	INSERT INTO rdap.country_code VALUES (454,'MW');
	INSERT INTO rdap.country_code VALUES (458,'MY');
	INSERT INTO rdap.country_code VALUES (462,'MV');
	INSERT INTO rdap.country_code VALUES (466,'ML');
	INSERT INTO rdap.country_code VALUES (470,'MT');
	INSERT INTO rdap.country_code VALUES (584,'MH');
	INSERT INTO rdap.country_code VALUES (474,'MQ');
	INSERT INTO rdap.country_code VALUES (478,'MR');
	INSERT INTO rdap.country_code VALUES (480,'MU');
	INSERT INTO rdap.country_code VALUES (175,'YT');
	INSERT INTO rdap.country_code VALUES (484,'MX');
	INSERT INTO rdap.country_code VALUES (583,'FM');
	INSERT INTO rdap.country_code VALUES (498,'MD');
	INSERT INTO rdap.country_code VALUES (492,'MC');
	INSERT INTO rdap.country_code VALUES (496,'MN');
	INSERT INTO rdap.country_code VALUES (499,'ME');
	INSERT INTO rdap.country_code VALUES (500,'MS');
	INSERT INTO rdap.country_code VALUES (504,'MA');
	INSERT INTO rdap.country_code VALUES (508,'MZ');
	INSERT INTO rdap.country_code VALUES (104,'MM');
	INSERT INTO rdap.country_code VALUES (516,'NA');
	INSERT INTO rdap.country_code VALUES (520,'NR');
	INSERT INTO rdap.country_code VALUES (524,'NP');
	INSERT INTO rdap.country_code VALUES (528,'NL');
	INSERT INTO rdap.country_code VALUES (530,'AN');
	INSERT INTO rdap.country_code VALUES (540,'NC');
	INSERT INTO rdap.country_code VALUES (554,'NZ');
	INSERT INTO rdap.country_code VALUES (558,'NI');
	INSERT INTO rdap.country_code VALUES (562,'NE');
	INSERT INTO rdap.country_code VALUES (566,'NG');
	INSERT INTO rdap.country_code VALUES (570,'NU');
	INSERT INTO rdap.country_code VALUES (574,'NF');
	INSERT INTO rdap.country_code VALUES (580,'MP');
	INSERT INTO rdap.country_code VALUES (578,'NO');
	INSERT INTO rdap.country_code VALUES (512,'OM');
	INSERT INTO rdap.country_code VALUES (586,'PK');
	INSERT INTO rdap.country_code VALUES (585,'PW');
	INSERT INTO rdap.country_code VALUES (275,'PS');
	INSERT INTO rdap.country_code VALUES (591,'PA');
	INSERT INTO rdap.country_code VALUES (598,'PG');
	INSERT INTO rdap.country_code VALUES (600,'PY');
	INSERT INTO rdap.country_code VALUES (604,'PE');
	INSERT INTO rdap.country_code VALUES (608,'PH');
	INSERT INTO rdap.country_code VALUES (612,'PN');
	INSERT INTO rdap.country_code VALUES (616,'PL');
	INSERT INTO rdap.country_code VALUES (620,'PT');
	INSERT INTO rdap.country_code VALUES (630,'PR');
	INSERT INTO rdap.country_code VALUES (634,'QA');
	INSERT INTO rdap.country_code VALUES (638,'RE');
	INSERT INTO rdap.country_code VALUES (642,'RO');
	INSERT INTO rdap.country_code VALUES (643,'RU');
	INSERT INTO rdap.country_code VALUES (646,'RW');
	INSERT INTO rdap.country_code VALUES (652,'BL');
	INSERT INTO rdap.country_code VALUES (654,'SH');
	INSERT INTO rdap.country_code VALUES (659,'KN');
	INSERT INTO rdap.country_code VALUES (662,'LC');
	INSERT INTO rdap.country_code VALUES (663,'MF');
	INSERT INTO rdap.country_code VALUES (666,'PM');
	INSERT INTO rdap.country_code VALUES (670,'VC');
	INSERT INTO rdap.country_code VALUES (882,'WS');
	INSERT INTO rdap.country_code VALUES (674,'SM');
	INSERT INTO rdap.country_code VALUES (678,'ST');
	INSERT INTO rdap.country_code VALUES (682,'SA');
	INSERT INTO rdap.country_code VALUES (686,'SN');
	INSERT INTO rdap.country_code VALUES (688,'RS');
	INSERT INTO rdap.country_code VALUES (690,'SC');
	INSERT INTO rdap.country_code VALUES (694,'SL');
	INSERT INTO rdap.country_code VALUES (702,'SG');
	INSERT INTO rdap.country_code VALUES (703,'SK');
	INSERT INTO rdap.country_code VALUES (705,'SI');
	INSERT INTO rdap.country_code VALUES (90,'SB');
	INSERT INTO rdap.country_code VALUES (706,'SO');
	INSERT INTO rdap.country_code VALUES (710,'ZA');
	INSERT INTO rdap.country_code VALUES (239,'GS');
	INSERT INTO rdap.country_code VALUES (728,'SS');
	INSERT INTO rdap.country_code VALUES (724,'ES');
	INSERT INTO rdap.country_code VALUES (144,'LK');
	INSERT INTO rdap.country_code VALUES (736,'SD');
	INSERT INTO rdap.country_code VALUES (740,'SR');
	INSERT INTO rdap.country_code VALUES (744,'SJ');
	INSERT INTO rdap.country_code VALUES (748,'SZ');
	INSERT INTO rdap.country_code VALUES (752,'SE');
	INSERT INTO rdap.country_code VALUES (756,'CH');
	INSERT INTO rdap.country_code VALUES (760,'SY');
	INSERT INTO rdap.country_code VALUES (158,'TW');
	INSERT INTO rdap.country_code VALUES (762,'TJ');
	INSERT INTO rdap.country_code VALUES (834,'TZ');
	INSERT INTO rdap.country_code VALUES (764,'TH');
	INSERT INTO rdap.country_code VALUES (626,'TL');
	INSERT INTO rdap.country_code VALUES (768,'TG');
	INSERT INTO rdap.country_code VALUES (772,'TK');
	INSERT INTO rdap.country_code VALUES (776,'TO');
	INSERT INTO rdap.country_code VALUES (780,'TT');
	INSERT INTO rdap.country_code VALUES (788,'TN');
	INSERT INTO rdap.country_code VALUES (792,'TR');
	INSERT INTO rdap.country_code VALUES (795,'TM');
	INSERT INTO rdap.country_code VALUES (796,'TC');
	INSERT INTO rdap.country_code VALUES (798,'TV');
	INSERT INTO rdap.country_code VALUES (800,'UG');
	INSERT INTO rdap.country_code VALUES (804,'UA');
	INSERT INTO rdap.country_code VALUES (784,'AE');
	INSERT INTO rdap.country_code VALUES (826,'GB');
	INSERT INTO rdap.country_code VALUES (840,'US');
	INSERT INTO rdap.country_code VALUES (581,'UM');
	INSERT INTO rdap.country_code VALUES (858,'UY');
	INSERT INTO rdap.country_code VALUES (860,'UZ');
	INSERT INTO rdap.country_code VALUES (548,'VU');
	INSERT INTO rdap.country_code VALUES (862,'VE');
	INSERT INTO rdap.country_code VALUES (704,'VN');
	INSERT INTO rdap.country_code VALUES (850,'VI');
	INSERT INTO rdap.country_code VALUES (876,'WF');
	INSERT INTO rdap.country_code VALUES (732,'EH');
	INSERT INTO rdap.country_code VALUES (887,'YE');
	INSERT INTO rdap.country_code VALUES (894,'ZM');
	INSERT INTO rdap.country_code VALUES (716,'ZW');


	INSERT INTO rdap.ip_version VALUES (4, 'v4');
	INSERT INTO rdap.ip_version VALUES (6, 'v6');