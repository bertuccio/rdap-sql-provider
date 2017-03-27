package mx.nic.rdap.db.model;

import java.io.IOException;
import java.net.IDN;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.nic.rdap.IpUtils;
import mx.nic.rdap.core.catalog.Role;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.Nameserver;
import mx.nic.rdap.db.QueryGroup;
import mx.nic.rdap.db.exception.RequiredValueNotFoundException;
import mx.nic.rdap.db.exception.http.BadRequestException;
import mx.nic.rdap.db.objects.NameserverDbObj;
import mx.nic.rdap.db.struct.SearchResultStruct;

/**
 * Model for the {@link Nameserver} Object
 * 
 */
public class NameserverModel {

	private final static Logger logger = Logger.getLogger(NameserverModel.class.getName());

	private final static String QUERY_GROUP = "Nameserver";

	private static QueryGroup queryGroup = null;

	private static final String STORE_QUERY = "storeToDatabase";

	private static final String GET_ALL_QUERY = "getAll";
	private static final String GET_BY_HANDLE_QUERY = "getByHandle";

	private static final String FIND_BY_NAME_QUERY = "findByName";

	private static final String SEARCH_BY_PARTIAL_NAME_QUERY = "searchByPartialName";
	private static final String SEARCH_BY_NAME_QUERY = "searchByName";
	private static final String SEARCH_BY_IP6_QUERY = "searchByIp6";
	private static final String SEARCH_BY_IP4_QUERY = "searchByIp4";

	private static final String DOMAIN_GET_QUERY = "getByDomainId";
	private static final String DOMAIN_STORE_QUERY = "storeDomainNameserversToDatabase";

	private static final String SEARCH_BY_NAME_REGEX_QUERY = "searchByRegexName";

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

	private static void isValidForStore(Nameserver nameserver, boolean useNameserverAsAttribute)
			throws RequiredValueNotFoundException {
		if (!useNameserverAsAttribute && (nameserver.getHandle() == null || nameserver.getHandle().isEmpty()))
			throw new RequiredValueNotFoundException("handle", "Nameserver");
		if (nameserver.getPunycodeName() == null || nameserver.getPunycodeName().isEmpty())
			throw new RequiredValueNotFoundException("ldhName", "Nameserver");
	}

	// Store as object
	public static void storeToDatabase(Nameserver nameserver, Connection connection)
			throws RequiredValueNotFoundException, SQLException {
		storeToDatabase(nameserver, false, connection);
	}

	public static void storeToDatabase(Nameserver nameserver, boolean useNameserverAsAttribute, Connection connection)
			throws SQLException, RequiredValueNotFoundException {
		isValidForStore(nameserver, useNameserverAsAttribute);
		String query = getQueryGroup().getQuery(STORE_QUERY);
		Long nameserverId = null;
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			((NameserverDbObj) nameserver).storeToDatabase(statement);
			logger.log(Level.INFO, "Executing QUERY:" + statement.toString());
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			result.next();
			nameserverId = result.getLong(1);// The id of the nameserver
												// inserted
			nameserver.setId(nameserverId);
		}
		storeNestedObjects(nameserver, connection);
	}

	public static void storeNestedObjects(Nameserver nameserver, Connection connection)
			throws SQLException, RequiredValueNotFoundException {
		Long nameserverId = nameserver.getId();
		IpAddressModel.storeToDatabase(nameserver.getIpAddresses(), nameserverId, connection);
		StatusModel.storeNameserverStatusToDatabase(nameserver.getStatus(), nameserverId, connection);
		RemarkModel.storeNameserverRemarksToDatabase(nameserver.getRemarks(), nameserverId, connection);
		LinkModel.storeNameserverLinksToDatabase(nameserver.getLinks(), nameserverId, connection);
		EventModel.storeNameserverEventsToDatabase(nameserver.getEvents(), nameserverId, connection);
		storeNameserverEntities(nameserver, connection);
	}

	public static void storeNameserverEntities(Nameserver nameserver, Connection connection) throws SQLException {
		if (nameserver.getEntities().size() > 0) {
			EntityModel.validateParentEntities(nameserver.getEntities(), connection);
			RoleModel.storeNameserverEntityRoles(nameserver.getEntities(), nameserver.getId(), connection);
		}

	}

	/**
	 * Store a list of nameservers that belong from a domain
	 * 
	 */
	public static void storeDomainNameserversToDatabase(List<Nameserver> nameservers, Long domainId,
			Connection connection) throws SQLException {
		if (nameservers.isEmpty()) {
			return;
		}

		String query = getQueryGroup().getQuery(DOMAIN_STORE_QUERY);
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			Long nameserverId;
			for (Nameserver nameserver : nameservers) {
				statement.setLong(1, domainId);
				nameserverId = nameserver.getId();
				statement.setLong(2, nameserverId);
				logger.log(Level.INFO, "Executing QUERY:" + statement.toString());
				statement.executeUpdate();
			}
		}
	}

	public static NameserverDbObj findByName(String name, Connection connection)
			throws SQLException {
		String query = getQueryGroup().getQuery(FIND_BY_NAME_QUERY);
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, IDN.toASCII(name));
			statement.setString(2, IDN.toUnicode(name));
			logger.log(Level.INFO, "Executing QUERY:" + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					return null;
				}
				NameserverDbObj nameserver = new NameserverDbObj(resultSet);
				NameserverModel.loadNestedObjects(nameserver, connection);
				return nameserver;
			}
		}
	}

	public static SearchResultStruct<Nameserver> searchByName(String namePattern, int resultLimit,
			Connection connection) throws SQLException {
		String query = null;
		if (namePattern.contains("*")) {// check if is a partial search
			query = getQueryGroup().getQuery(SEARCH_BY_PARTIAL_NAME_QUERY);
			namePattern = namePattern.replace('*', '%');
		} else {
			query = getQueryGroup().getQuery(SEARCH_BY_NAME_QUERY);
		}
		return searchByName(namePattern, resultLimit, connection, query);
	}

	public static SearchResultStruct<Nameserver> searchByRegexName(String namePattern, int resultLimit,
			Connection connection) throws SQLException {
		return searchByName(namePattern, resultLimit, connection, getQueryGroup().getQuery(SEARCH_BY_NAME_REGEX_QUERY));
	}

	private static SearchResultStruct<Nameserver> searchByName(String namePattern, int resultLimit,
			Connection connection, String query) throws SQLException {
		SearchResultStruct<Nameserver> result = new SearchResultStruct<Nameserver>();
		// Hack to know is there is more domains that the limit, used for
		// notices
		resultLimit = resultLimit + 1;
		String criteria = namePattern;
		List<NameserverDbObj> nameservers = new ArrayList<NameserverDbObj>();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, criteria);
			statement.setString(2, criteria);
			statement.setInt(3, resultLimit);
			logger.log(Level.INFO, "Executing QUERY:" + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {

				if (!resultSet.next()) {
					return null;
				}
				do {
					NameserverDbObj nameserver = new NameserverDbObj(resultSet);
					nameservers.add(nameserver);
				} while (resultSet.next());

				resultLimit = resultLimit - 1;// Back to the original limit
				if (nameservers.size() > resultLimit) {
					result.setResultSetWasLimitedByUserConfiguration(true);
					nameservers.remove(nameservers.size() - 1);
				}
				for (NameserverDbObj nameserver : nameservers) {
					loadNestedObjects(nameserver, connection);
				}
				result.setSearchResultsLimitForUser(resultLimit);
				result.getResults().addAll(nameservers);
				return result;
			}
		}
	}

	public static SearchResultStruct<Nameserver> searchByIp(String ipaddressPattern, int resultLimit,
			Connection connection) throws SQLException, BadRequestException {
		SearchResultStruct<Nameserver> result = new SearchResultStruct<Nameserver>();
		// Hack to know is there is more domains that the limit, used for
		// notices
		resultLimit = resultLimit + 1;
		String query = "";
		List<NameserverDbObj> nameservers = new ArrayList<NameserverDbObj>();

		InetAddress address = IpUtils.validateIpAddress(ipaddressPattern);
		if (address instanceof Inet6Address) {
			query = getQueryGroup().getQuery(SEARCH_BY_IP6_QUERY);
		} else if (address instanceof Inet4Address) {
			query = getQueryGroup().getQuery(SEARCH_BY_IP4_QUERY);
		}

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, ipaddressPattern);
			statement.setInt(2, resultLimit);
			logger.log(Level.INFO, "Executing QUERY:" + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {

				if (!resultSet.next()) {
					return null;
				}
				do {
					NameserverDbObj nameserver = new NameserverDbObj(resultSet);
					nameservers.add(nameserver);
				} while (resultSet.next());

				resultLimit = resultLimit - 1;// Back to the original limit
				if (nameservers.size() > resultLimit) {
					result.setResultSetWasLimitedByUserConfiguration(true);
					nameservers.remove(nameservers.size() - 1);
				}
				for (NameserverDbObj nameserver : nameservers) {
					loadNestedObjects(nameserver, connection);
				}
				result.setSearchResultsLimitForUser(resultLimit);
				result.getResults().addAll(nameservers);
				return result;
			}
		}
	}

	/**
	 * Find nameservers that belongs from a domain by the domain's id
	 * 
	 * @param useNameserverAsDomainAttribute
	 *            if true, don't have to load nested objects
	 * 
	 */
	public static List<Nameserver> getByDomainId(Long domainId, boolean useNameserverAsDomainAttribute,
			Connection connection) throws SQLException {
		String query = getQueryGroup().getQuery(DOMAIN_GET_QUERY);
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, domainId);
			logger.log(Level.INFO, "Executing QUERY: " + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					return Collections.emptyList();
				}
				List<Nameserver> nameservers = new ArrayList<Nameserver>();
				do {
					Nameserver nameserver = new NameserverDbObj(resultSet);
					if (!useNameserverAsDomainAttribute)
						NameserverModel.loadNestedObjects(nameserver, connection);
					nameservers.add(nameserver);
				} while (resultSet.next());
				return nameservers;
			}
		}
	}

	private static void loadNestedObjects(Nameserver nameserver, Connection connection) throws SQLException {

		// Retrieve the ipAddress
		nameserver.setIpAddresses(IpAddressModel.getIpAddressStructByNameserverId(nameserver.getId(), connection));

		// Retrieve the status
		nameserver.getStatus().addAll(StatusModel.getByNameServerId(nameserver.getId(), connection));

		// Retrieve the remarks
		nameserver.getRemarks().addAll(RemarkModel.getByNameserverId(nameserver.getId(), connection));

		// Retrieve the links
		nameserver.getLinks().addAll(LinkModel.getByNameServerId(nameserver.getId(), connection));

		// Retrieve the events
		nameserver.getEvents().addAll(EventModel.getByNameServerId(nameserver.getId(), connection));
		// Retrieve the entities
		List<Entity> entities = EntityModel.getEntitiesByNameserverId(nameserver.getId(), connection);
		nameserver.getEntities().addAll(entities);
		for (Entity entity : entities) {
			List<Role> roles = RoleModel.getNameserverEntityRol(nameserver.getId(), entity.getId(), connection);
			entity.setRoles(roles);
		}
	}

	public static List<Nameserver> getAll(Connection connection) throws SQLException {
		String query = getQueryGroup().getQuery(GET_ALL_QUERY);
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			logger.log(Level.INFO, "Executing QUERY: " + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					return Collections.emptyList();
				}
				List<Nameserver> nameservers = new ArrayList<Nameserver>();
				do {
					Nameserver nameserver = new NameserverDbObj(resultSet);
					NameserverModel.loadNestedObjects(nameserver, connection);
					nameservers.add(nameserver);
				} while (resultSet.next());
				return nameservers;
			}
		}
	}

	public static NameserverDbObj getByHandle(String handle, Connection rdapConnection)
			throws SQLException, RequiredValueNotFoundException {
		if (handle == null || handle.isEmpty()) {
			throw new RequiredValueNotFoundException("handle", "Nameserver");
		}
		String query = getQueryGroup().getQuery(GET_BY_HANDLE_QUERY);
		try (PreparedStatement statement = rdapConnection.prepareStatement(query)) {
			statement.setString(1, handle);
			logger.log(Level.INFO, "Executing QUERY: " + statement.toString());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					return null;
				}
				NameserverDbObj nameserver = new NameserverDbObj(resultSet);
				loadNestedObjects(nameserver, rdapConnection);
				return nameserver;
			}
		}
	}

	public static void storeDomainNameserversAsAttributesToDatabase(List<Nameserver> nameservers, Long domainId,
			Connection connection) throws RequiredValueNotFoundException, SQLException {
		for (Nameserver ns : nameservers) {
			NameserverModel.storeToDatabase(ns, true, connection);
		}
		storeDomainNameserversToDatabase(nameservers, domainId, connection);

	}
}