package mx.nic.rdap.sql.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.nic.rdap.core.catalog.Role;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.sql.QueryGroup;

/**
 * Model for the {@link Role} objects of nested entities of main objects.
 * 
 */
public class RoleModel {

	private final static Logger logger = Logger.getLogger(RoleModel.class.getName());

	private final static String QUERY_GROUP = "Rol";

	private static final String DOMAIN_STORE_QUERY = "storeDomainsEntityRol";
	private static final String ENTITY_STORE_QUERY = "storeEntitiesEntityRol";
	private static final String NAMESERVER_STORE_QUERY = "storeNSEntityRol";
	private static final String AUTNUM_STORE_QUERY = "storeAutnumEntityRol";
	private static final String IP_NETWORK_STORE_ROLES = "storeIpNetworkEntityRol";
	private static final String MAIN_ENTITY_STORE_QUERY = "storeMainEntityRole";

	private static final String DOMAIN_GET_QUERY = "getDomainRol";
	private static final String ENTITY_GET_QUERY = "getEntityRol";
	private static final String NAMESERVER_GET_QUERY = "getNSRol";
	private static final String AUTNUM_GET_QUERY = "getAutnumRol";
	private static final String IP_NETWORK_GET_QUERY = "getIpNetworkRol";
	private static final String MAIN_ENTITY_GET_QUERY = "getMainEntityRole";

	private static QueryGroup queryGroup = null;

	public static void loadQueryGroup(String schema) {
		try {
			QueryGroup qG = new QueryGroup(QUERY_GROUP, schema);
			setQueryGroup(qG);
		} catch (IOException e) {
			throw new RuntimeException("Error loading query group");
		}
	}

	private static void setQueryGroup(QueryGroup qG) {
		queryGroup = qG;
	}

	private static QueryGroup getQueryGroup() {
		return queryGroup;
	}

	public static List<Role> getDomainEntityRol(Long domainId, Long entityId, Connection connection)
			throws SQLException {
		return getNestedEntityRol(domainId, entityId, connection, DOMAIN_GET_QUERY);
	}

	public static List<Role> getNameserverEntityRol(Long nameserverId, Long entityId, Connection connection)
			throws SQLException {
		return getNestedEntityRol(nameserverId, entityId, connection, NAMESERVER_GET_QUERY);
	}

	public static List<Role> getEntityEntityRol(Long mainEntityId, Long nestedEntityId, Connection connection)
			throws SQLException {
		return getNestedEntityRol(mainEntityId, nestedEntityId, connection, ENTITY_GET_QUERY);
	}

	public static List<Role> getAutnumEntityRol(Long autnumId, Long asnId, Connection connection) throws SQLException {
		return getNestedEntityRol(autnumId, asnId, connection, AUTNUM_GET_QUERY);
	}

	public static List<Role> getIpNetworkEntityRol(Long ipNetworkId, Long entityId, Connection connection)
			throws SQLException {
		return getNestedEntityRol(ipNetworkId, entityId, connection, IP_NETWORK_GET_QUERY);
	}

	private static List<Role> getNestedEntityRol(Long ownerId, Long nestedEntityId, Connection connection,
			String getQuery) throws SQLException {
		String query = getQueryGroup().getQuery(getQuery);
		List<Role> roles = null;
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, ownerId);
			statement.setLong(2, nestedEntityId);
			ResultSet rs = statement.executeQuery();

			if (!rs.next()) {
				return Collections.emptyList();
			}

			roles = new ArrayList<>();
			do {
				int rolId = rs.getInt(1);
				if (rs.wasNull()) {
					throw new NullPointerException("Role id was null");
				}
				roles.add(Role.getById(rolId));
			} while (rs.next());
		}

		return roles;
	}

	public static void storeEntityEntityRoles(List<Entity> entities, Long entityId, Connection connection)
			throws SQLException {
		storeEntitiesRoles(entities, entityId, connection, ENTITY_STORE_QUERY);
	}

	public static void storeNameserverEntityRoles(List<Entity> entities, Long nsId, Connection connection)
			throws SQLException {
		storeEntitiesRoles(entities, nsId, connection, NAMESERVER_STORE_QUERY);
	}

	public static void storeDomainEntityRoles(List<Entity> entities, Long domainId, Connection connection)
			throws SQLException {
		storeEntitiesRoles(entities, domainId, connection, DOMAIN_STORE_QUERY);
	}

	public static void storeAutnumEntityRoles(List<Entity> entities, Long autnumId, Connection connection)
			throws SQLException {
		storeEntitiesRoles(entities, autnumId, connection, AUTNUM_STORE_QUERY);
	}

	public static void storeIpNetworkEntityRoles(List<Entity> entities, Long ipNetworkId, Connection connection)
			throws SQLException {
		storeEntitiesRoles(entities, ipNetworkId, connection, IP_NETWORK_STORE_ROLES);
	}

	private static void storeEntitiesRoles(List<Entity> entities, Long ownerId, Connection connection,
			String storeQuery) throws SQLException {
		if (entities.isEmpty())
			return;

		String query = getQueryGroup().getQuery(storeQuery);

		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, ownerId);
			for (Entity entity : entities) {
				statement.setLong(2, entity.getId());
				for (Role role : entity.getRoles()) {
					statement.setLong(3, role.getId());
					logger.log(Level.INFO, "Executing QUERY" + statement.toString());
					statement.execute();
				}
			}
		}
	}

	public static void storeMainEntityRol(Entity mainEntity, Connection connection) throws SQLException {
		if (mainEntity.getRoles().isEmpty()) {
			return;
		}

		String query = getQueryGroup().getQuery(MAIN_ENTITY_STORE_QUERY);
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, mainEntity.getId());
			for (Role role : mainEntity.getRoles()) {
				statement.setInt(2, role.getId());
				logger.log(Level.INFO, "Executing QUERY" + statement.toString());
				statement.execute();
			}
		}
	}

	public static List<Role> getMainEntityRol(Long entId, Connection connection) throws SQLException {
		List<Role> result = null;
		String query = getQueryGroup().getQuery(MAIN_ENTITY_GET_QUERY);
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, entId);
			logger.log(Level.FINE, "Executing QUERY: " + statement.toString());
			ResultSet rs = statement.executeQuery();
			if (!rs.next()) {
				return Collections.emptyList();
			}

			result = new ArrayList<>();
			do {
				int rolId = rs.getInt(1);
				if (rs.wasNull()) {
					throw new NullPointerException("Role id was null");
				}
				result.add(Role.getById(rolId));
			} while (rs.next());
		}
		return result;
	}

}
