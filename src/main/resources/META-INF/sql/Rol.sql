#storeDomainsEntityRol
INSERT INTO {schema}.domain_entity_roles VALUES (?, ?, ?);

#storeEntitiesEntityRol
INSERT INTO {schema}.entity_entity_roles VALUES (?, ?, ?);

#storeNSEntityRol
INSERT INTO {schema}.nameserver_entity_roles VALUES (?, ?, ?);

#storeAutnumEntityRol
INSERT INTO {schema}.asn_entity_roles VALUES (?,?,?);

#storeIpNetworkEntityRol
INSERT INTO {schema}.ip_network_entity_roles VALUES (?, ?, ?);

#storeMainEntityRole
INSERT INTO {schema}.entity_role VALUES (?, ?);

#getMainEntityRole
SELECT rol.rol_id FROM {schema}.entity_role rol WHERE rol.ent_id = ?;

#getDomainRol
SELECT rol.rol_id FROM {schema}.domain_entity_roles rol WHERE rol.dom_id = ? AND rol.ent_id = ?;

#getEntityRol
SELECT rol.rol_id FROM {schema}.entity_entity_roles rol WHERE rol.main_ent_id = ? AND rol.ent_id = ?;

#getNSRol
SELECT rol.rol_id FROM {schema}.nameserver_entity_roles rol WHERE rol.nse_id = ? AND rol.ent_id = ?;

#getAutnumRol
SELECT rol.rol_id FROM {schema}.asn_entity_roles rol WHERE rol.asn_id = ? AND rol.ent_id = ?;

#getIpNetworkRol
SELECT rol.rol_id FROM {schema}.ip_network_entity_roles rol WHERE rol.ine_id = ? AND rol.ent_id = ?;

