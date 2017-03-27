package mx.nic.rdap.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.db.DatabaseSession;
import mx.nic.rdap.db.exception.RdapDataAccessException;
import mx.nic.rdap.db.model.EntityModel;
import mx.nic.rdap.db.spi.EntityDAO;
import mx.nic.rdap.db.struct.SearchResultStruct;

public class EntityDAOImpl implements EntityDAO {

	public long storeToDatabase(Entity entity) throws RdapDataAccessException {
		try (Connection connection = DatabaseSession.getRdapConnection()) {
			return EntityModel.storeToDatabase(entity, connection);
		} catch (SQLException e) {
			throw new RdapDataAccessException(e);
		}
	}

	@Override
	public Entity getByHandle(String entityHandle) throws RdapDataAccessException {
		try (Connection connection = DatabaseSession.getRdapConnection()) {
			return EntityModel.getByHandle(entityHandle, connection);
		} catch (SQLException e) {
			throw new RdapDataAccessException(e);
		}
	}

	@Override
	public SearchResultStruct<Entity> searchByHandle(String handle, int resultLimit) throws RdapDataAccessException {
		try (Connection connection = DatabaseSession.getRdapConnection()) {
			return EntityModel.searchByHandle(handle, resultLimit, connection);
		} catch (SQLException e) {
			throw new RdapDataAccessException(e);
		}
	}

	@Override
	public SearchResultStruct<Entity> searchByVCardName(String vCardName, int resultLimit)
			throws RdapDataAccessException {
		try (Connection connection = DatabaseSession.getRdapConnection()) {
			return EntityModel.searchByVCardName(vCardName, resultLimit, connection);
		} catch (SQLException e) {
			throw new RdapDataAccessException(e);
		}
	}

	@Override
	public SearchResultStruct<Entity> searchByRegexHandle(String regexHandle, int resultLimit)
			throws RdapDataAccessException {
		try (Connection connection = DatabaseSession.getRdapConnection()) {
			return EntityModel.searchByRegexHandle(regexHandle, resultLimit, connection);
		} catch (SQLException e) {
			throw new RdapDataAccessException(e);
		}
	}

	@Override
	public SearchResultStruct<Entity> searchByRegexVCardName(String regexName, int resultLimit)
			throws RdapDataAccessException {
		try (Connection connection = DatabaseSession.getRdapConnection()) {
			return EntityModel.searchByRegexName(regexName, resultLimit, connection);
		} catch (SQLException e) {
			throw new RdapDataAccessException(e);
		}
	}

}
