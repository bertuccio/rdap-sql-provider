package mx.nic.rdap.db.impl;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.List;

import mx.nic.rdap.core.db.Domain;
import mx.nic.rdap.db.DBConnection;
import mx.nic.rdap.db.exception.InvalidValueException;
import mx.nic.rdap.db.exception.NotImplementedException;
import mx.nic.rdap.db.exception.ObjectNotFoundException;
import mx.nic.rdap.db.exception.RdapDatabaseException;
import mx.nic.rdap.db.model.DomainModel;
import mx.nic.rdap.db.model.ZoneModel;
import mx.nic.rdap.db.spi.DomainSpi;
import mx.nic.rdap.db.struct.SearchResultStruct;

public class DomainDAOImpl implements DomainSpi {

	@Override
	public Long storeToDatabase(Domain domain, Boolean useNsAsAttribute) throws RdapDatabaseException {
		try (Connection connection = DBConnection.getConnection()) {
			return DomainModel.storeToDatabase(domain, useNsAsAttribute, connection);
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
	}

	@Override
	public Domain getByName(String domainName, Boolean useNsAsAttribute) throws RdapDatabaseException {
		if (!domainName.contains("."))
			throw new InvalidValueException("Invalid fqdn");
		String name = domainName.substring(0, domainName.indexOf('.'));
		String zone = domainName.substring(domainName.indexOf('.') + 1);
		if (!ZoneModel.existsZone(zone))
			throw new ObjectNotFoundException("Zone not found.");

		try (Connection connection = DBConnection.getConnection()) {
			Domain domain = DomainModel.findByLdhName(name, ZoneModel.getIdByZoneName(zone), useNsAsAttribute,
					connection);
			return domain;
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
	}

	@Override
	public SearchResultStruct searchByName(String domainName, Integer resultLimit,
			boolean useNameserverAsDomainAttribute) throws RdapDatabaseException {
		if (domainName.contains("*")) {
			List<String> labels = Arrays.asList(domainName.split("\\."));
			for (String label : labels) {
				if (label.contains("*") && !label.endsWith("*"))
					throw new InvalidValueException("Patterns can only have an * at the end.");
			}
		}
		SearchResultStruct domains = null;
		try (Connection connection = DBConnection.getConnection()) {
			if (domainName.contains(".")) {
				String name = domainName.substring(0, domainName.indexOf('.'));
				String zone = domainName.substring(domainName.indexOf('.') + 1);
				domains = DomainModel.searchByName(name, zone, resultLimit, useNameserverAsDomainAttribute, connection);
			} else {
				domains = DomainModel.searchByName(domainName, resultLimit, useNameserverAsDomainAttribute, connection);
			}
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
		return domains;
	}

	@Override
	public SearchResultStruct searchByNsName(String nsName, Integer resultLimit, boolean useNsAsAttribute)
			throws RdapDatabaseException {
		if (nsName.contains("*")) {
			List<String> labels = Arrays.asList(nsName.split("\\."));
			for (String label : labels) {
				if (label.contains("*") && !label.endsWith("*"))
					throw new InvalidValueException("Patterns can only have an * at the end");
			}
		}
		try (Connection connection = DBConnection.getConnection()) {
			return DomainModel.searchByNsLdhName(nsName, resultLimit, useNsAsAttribute, connection);
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
	}

	@Override
	public SearchResultStruct searchByNsIp(String ip, Integer resultLimit, boolean useNsAsAttribute)
			throws RdapDatabaseException {
		try (Connection connection = DBConnection.getConnection()) {
			return DomainModel.searchByNsIp(ip, resultLimit, useNsAsAttribute, connection);
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
	}

	@Override
	public SearchResultStruct searchByRegexName(String regexName, Integer resultLimit, boolean useNsAsDomainAttribute)
			throws RdapDatabaseException {
		SearchResultStruct domains = null;
		String[] regexWZone = null;
		if (regexName.contains("\\.")) {
			regexWZone = regexName.split("\\\\.", 2);
		}
		try (Connection connection = DBConnection.getConnection()) {
			if (regexWZone == null || Array.getLength(regexWZone) <= 1) {
				domains = DomainModel.searchByRegexName(regexName, resultLimit, useNsAsDomainAttribute, connection);
			} else {
				domains = DomainModel.searchByRegexName(regexWZone[0], regexWZone[1], resultLimit,
						useNsAsDomainAttribute, connection);
			}
		} catch (SQLSyntaxErrorException e) {
			throw new InvalidValueException(e.getMessage(), e);
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
		return domains;
	}

	@Override
	public SearchResultStruct searchByRegexNsName(String regexNsName, Integer resultLimit,
			boolean useNameserverAsDomainAttribute) throws RdapDatabaseException {
		try (Connection connection = DBConnection.getConnection()) {
			return DomainModel.searchByRegexNsLdhName(regexNsName, resultLimit, useNameserverAsDomainAttribute,
					connection);
		} catch (SQLSyntaxErrorException e) {
			throw new InvalidValueException(e.getMessage(), e);
		} catch (SQLException e) {
			throw new RdapDatabaseException(e);
		}
	}

	@Override
	public SearchResultStruct searchByRegexNsIp(String ip, Integer resultLimit, boolean useNsAsAttribute)
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public boolean existByName(String domainName) throws RdapDatabaseException {
		if (!domainName.contains("."))
			throw new RdapDatabaseException("Invalid fqdn");
		String name = domainName.substring(0, domainName.indexOf('.'));
		String zone = domainName.substring(domainName.indexOf('.') + 1);
		if (!ZoneModel.existsZone(zone))
			throw new RdapDatabaseException("Zone not found");

		try (Connection connection = DBConnection.getConnection()) {
			DomainModel.existByLdhName(name, ZoneModel.getIdByZoneName(zone), connection);
		} catch (SQLException e) {
			throw new RdapDatabaseException();
		}
		return true;
	}

}
