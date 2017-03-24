package mx.nic.rdap.db;

import java.sql.SQLException;

import org.junit.Test;

import mx.nic.rdap.db.exception.ObjectNotFoundException;
import mx.nic.rdap.db.exception.RequiredValueNotFoundException;
import mx.nic.rdap.db.model.RdapUserModel;
import mx.nic.rdap.db.objects.RdapUserDbObj;
import mx.nic.rdap.db.objects.RdapUserRoleDbObj;

/**
 * Test for the rdapuserDAO class
 * 
 */
public class RdapUserTest extends DatabaseTest {

	private String userName = "Test2";
	private String pass = "12345678A";
	private Integer maxSearchResult = 1;
	private String roleName = "AUTHENTICATED";

	@Test
	public void storeToDatabase() {
		try {
			RdapUserDbObj user = new RdapUserDbObj();
			user.setName(userName);
			user.setPass(pass);
			user.setMaxSearchResults(maxSearchResult);
			RdapUserRoleDbObj role = new RdapUserRoleDbObj();
			role.setRoleName(roleName);
			user.setUserRole(role);
			RdapUserModel.storeToDatabase(user, connection);
			assert true;
		} catch (RequiredValueNotFoundException | SQLException e) {
			e.printStackTrace();
			assert false;
		}
	}

	@Test
	public void getByName() {
		try {
			RdapUserDbObj user = new RdapUserDbObj();
			user.setName(userName);
			user.setPass(pass);
			user.setMaxSearchResults(maxSearchResult);
			RdapUserRoleDbObj role = new RdapUserRoleDbObj();
			role.setRoleName(roleName);
			user.setUserRole(role);
			RdapUserModel.storeToDatabase(user, connection);
			RdapUserModel.getByName(userName, connection);
			assert true;
		} catch (SQLException | RequiredValueNotFoundException | ObjectNotFoundException e) {
			e.printStackTrace();
			assert false;
		}
	}

	@Test
	public void cleanUsersTableTest() {
		try {
			RdapUserModel.cleanRdapUserDatabase(connection);
			assert true;
		} catch (SQLException e) {
			e.printStackTrace();
			assert false;
		}
	}

}
