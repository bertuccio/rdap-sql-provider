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

import mx.nic.rdap.core.catalog.Status;
import mx.nic.rdap.sql.QueryGroup;
import mx.nic.rdap.sql.SQLProviderConfiguration;

/**
 * Model for the {@link Status} Object
 * 
 */
public class StatusModel {

	private final static Logger logger = Logger.getLogger(StatusModel.class.getName());

	private final static String QUERY_GROUP = "Status";

	private static QueryGroup queryGroup = null;

	private static final String NAMESERVER_GET_QUERY = "getByNameServerId";
	private static final String DOMAIN_GET_QUERY = "getByDomainId";
	private static final String ENTITY_GET_QUERY = "getByEntityId";
	private static final String AUTNUM_GET_QUERY = "getByAutnumid";
	private static final String IP_NETWORK_GET_QUERY = "getByIpNetworkId";

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

	public static List<Status> getByNameServerId(Long nameserverId, Connection connection) throws SQLException {
		return getByRelationsId(nameserverId, connection, NAMESERVER_GET_QUERY);
	}

	public static List<Status> getByDomainId(Long domainId, Connection connection) throws SQLException {
		return getByRelationsId(domainId, connection, DOMAIN_GET_QUERY);
	}

	public static List<Status> getByEntityId(Long entityId, Connection connection) throws SQLException {
		return getByRelationsId(entityId, connection, ENTITY_GET_QUERY);
	}

	public static List<Status> getByAutnumId(Long autnumId, Connection connection) throws SQLException {
		return getByRelationsId(autnumId, connection, AUTNUM_GET_QUERY);
	}

	public static List<Status> getByIpNetworkId(Long ipNetworkId, Connection connection) throws SQLException {
		return getByRelationsId(ipNetworkId, connection, IP_NETWORK_GET_QUERY);
	}

	private static List<Status> getByRelationsId(Long id, Connection connection, String getQueryId)
			throws SQLException {
		List<Status> result = null;
		String query = getQueryGroup().getQuery(getQueryId);
		if (SQLProviderConfiguration.isUserSQL() && query == null) {
			return Collections.emptyList();
		}

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			logger.log(Level.INFO, "Executing QUERY:" + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					return Collections.emptyList();
				}
				List<Status> status = new ArrayList<Status>();
				do {
					status.add(Status.getById(resultSet.getInt("sta_id")));
				} while (resultSet.next());
				result = status;
			}
		}

		return result;
	}

}
