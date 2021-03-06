package mx.nic.rdap.sql.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import mx.nic.rdap.core.catalog.EventAction;
import mx.nic.rdap.core.catalog.Role;
import mx.nic.rdap.core.catalog.Status;
import mx.nic.rdap.core.db.Autnum;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.Event;
import mx.nic.rdap.core.db.Link;
import mx.nic.rdap.core.db.Remark;
import mx.nic.rdap.core.db.RemarkDescription;
import mx.nic.rdap.db.exception.http.NotImplementedException;
import mx.nic.rdap.sql.model.AutnumModel;
import mx.nic.rdap.sql.objects.AutnumDbObj;
import mx.nic.rdap.sql.objects.EntityDbObj;
import mx.nic.rdap.sql.objects.EventDbObj;
import mx.nic.rdap.sql.objects.LinkDbObj;
import mx.nic.rdap.sql.objects.RemarkDbObj;
import mx.nic.rdap.sql.objects.RemarkDescriptionDbObj;
import mx.nic.rdap.store.model.AutnumStoreModel;
import mx.nic.rdap.store.model.EntityStoreModel;

/**
 * Test for {@link Autnum}
 * 
 */
public class AutnumTest extends DatabaseTest {

	@Test
	public void insertAndGetAutnum() throws SQLException, NotImplementedException {
		Entity registrant = new EntityDbObj();
		registrant.setHandle("testHandler");
		registrant.setPort43("testestestest");
		registrant.getRoles().add(Role.REGISTRANT);
		EntityStoreModel.storeToDatabase(registrant, connection);

		Link link = new LinkDbObj();
		link.setHref("dummy.com.mx");
		link.setValue("http://dummy.net/ASN");
		link.setType("application/rdap+json");
		link.setRel("self");

		Event event = new EventDbObj();
		event.setEventAction(EventAction.REGISTRATION);
		event.setEventDate(new Date());
		event.setEventActor("");

		Remark remark = new RemarkDbObj();
		remark.setLanguage("ES");
		remark.setTitle("Prueba");
		remark.setType("PruebaType");

		List<RemarkDescription> descriptions = new ArrayList<RemarkDescription>();
		RemarkDescription description1 = new RemarkDescriptionDbObj();
		description1.setOrder(1);
		description1.setDescription("She sells sea shells down by the sea shore.");

		RemarkDescription description2 = new RemarkDescriptionDbObj();
		description2.setOrder(2);
		description2.setDescription("Originally written by Terry Sullivan.");

		descriptions.add(description1);
		descriptions.add(description2);
		remark.setDescriptions(descriptions);

		Autnum autnum = new AutnumDbObj();
		autnum.setCountry("MX");
		autnum.setStartAutnum(100L);
		autnum.setEndAutnum(101L);
		autnum.setName("testName");
		autnum.setType("testType");
		autnum.getEntities().add(registrant);
		autnum.setPort43("dummy.dummy.mx");
		autnum.getStatus().add(Status.ACTIVE);
		autnum.getLinks().add(link);
		autnum.getEvents().add(event);
		autnum.getRemarks().add(remark);
		autnum.setHandle("dummyASN");
		AutnumStoreModel.storeToDatabase(autnum, connection);

		Autnum getByRange = AutnumModel.getByRange(autnum.getStartAutnum(), connection);
		Assert.assertTrue("AutnumModel#getByRange fails", autnum.equals(getByRange));

	}
}
