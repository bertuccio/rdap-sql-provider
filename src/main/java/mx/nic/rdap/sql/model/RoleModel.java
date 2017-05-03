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
import mx.nic.rdap.sql.QueryGroup;

/**
 * Model for the {@link Role} objects of nested entities of main objects.
 * 
 */
public class RoleModel {

	private final static Logger logger = Logger.getLogger(RoleModel.class.getName());

	private final static String QUERY_GROUP = "Role";

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
