package mx.nic.rdap.db;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import mx.nic.rdap.core.catalog.EventAction;
import mx.nic.rdap.core.catalog.Rol;
import mx.nic.rdap.core.catalog.Status;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.Event;
import mx.nic.rdap.core.db.IpAddress;
import mx.nic.rdap.core.db.Link;
import mx.nic.rdap.core.db.Nameserver;
import mx.nic.rdap.core.db.PublicId;
import mx.nic.rdap.core.db.Remark;
import mx.nic.rdap.core.db.RemarkDescription;
import mx.nic.rdap.core.db.VCard;
import mx.nic.rdap.core.db.VCardPostalInfo;
import mx.nic.rdap.core.db.struct.NameserverIpAddressesStruct;
import mx.nic.rdap.db.exception.ObjectNotFoundException;
import mx.nic.rdap.db.exception.RequiredValueNotFoundException;
import mx.nic.rdap.db.model.DomainModel;
import mx.nic.rdap.db.model.EntityModel;
import mx.nic.rdap.db.model.NameserverModel;
import mx.nic.rdap.db.model.ZoneModel;
import mx.nic.rdap.db.objects.DomainDAO;
import mx.nic.rdap.db.objects.EntityDAO;
import mx.nic.rdap.db.objects.EventDAO;
import mx.nic.rdap.db.objects.IpAddressDAO;
import mx.nic.rdap.db.objects.LinkDAO;
import mx.nic.rdap.db.objects.NameserverDAO;
import mx.nic.rdap.db.objects.PublicIdDAO;
import mx.nic.rdap.db.objects.RemarkDAO;
import mx.nic.rdap.db.objects.RemarkDescriptionDAO;

/**
 *
 */
public class DummyDataTest extends DatabaseTest {

	public static char[] idnChars = { 'á', 'á', 'è', 'í', 'ö', 'â', 'ñ', 'ç', '明', '行', '極', '珠', '度', '雄', '熊', '強',
			'ア', 'イ', 'ウ', 'エ', 'オ', 'а', 'њ', 'ш', 'я', 'й', 'ж', 'ग', '-', '_', 'ब', 'ह', 'द', 'श' };
	public static String[] zones = { "mx", "lat", "com", "lat.com", "com.mx", "org" };

	public static void main(String[] args) throws RequiredValueNotFoundException, IOException {
		DummyDataTest dummyDataTest = new DummyDataTest();
		dummyDataTest.createDataDummy();
	}

	public void createDataDummy() throws RequiredValueNotFoundException, IOException {
		int numberOfDomains = 2000;
		try {
			for (int index = 801; index < numberOfDomains; index++) {
				String domainName = "DN" + index;
				if (getRandomBoolean()) {
					domainName = domainName
							.concat("" + idnChars[ThreadLocalRandom.current().nextInt(0, idnChars.length)]);
				}
				createDomain(domainName, zones[ThreadLocalRandom.current().nextInt(0, zones.length)], "domain" + index,
						getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean(),
						getRandomBoolean());
			}
			connection.commit();
		} catch (SQLException | ObjectNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}

	private static void createDomain(String name, String zone, String handle, boolean hasEntities,
			boolean hasNameservers, boolean hasRemarks, boolean hasLinks, boolean hasEvents)
			throws RequiredValueNotFoundException, IOException, SQLException, ObjectNotFoundException {
		DomainDAO domain = new DomainDAO();
		domain.setPunycodeName(name);
		domain.setHandle(handle);
		domain.setPort43("whois.mx");
		try {
			ZoneModel.storeToDatabase(zone, connection);
		} catch (SQLException e1) {
			e1.printStackTrace();
			fail(e1.toString());
		}
		domain.setZone(zone);
		if (hasNameservers) {
			domain.setNameServers(createNameservers(domain.getPunycodeName() + zone, getRandomBoolean(),
					getRandomBoolean(), getRandomBoolean(), getRandomBoolean()));
		}
		if (hasEntities) {
			domain.setEntities(createRandomEntities(name, hasEntities, hasRemarks, hasLinks, hasEvents));
		}
		if (hasRemarks) {
			domain.setRemarks(createRandomRemarks(domain.getLdhName(), getRandomBoolean()));
		}
		if (hasLinks)
			domain.setLinks(createRandomLinks());
		if (hasEvents) {
			domain.setEvents(createRandomEvents());
		}

		DomainModel.storeToDatabase(domain, false, connection);
	}

	private static List<Nameserver> createNameservers(String name, boolean hasEntities, boolean hasRemarks,
			boolean hasLinks, boolean hasEvents) throws RequiredValueNotFoundException, IOException, SQLException {
		List<Nameserver> nameservers = new ArrayList<Nameserver>();

		int numberOfNameservers = ThreadLocalRandom.current().nextInt(1, 3);
		for (int index = 0; index < numberOfNameservers; index++) {
			Nameserver nameserver = new NameserverDAO();
			String nsName = ("NS-" + index + "-").concat(name);
			nameserver.setPunycodeName(nsName + index);
			nameserver.setHandle(nsName + "-handle");
			nameserver.setPort43("whois.mx");

			List<Status> statusList = new ArrayList<Status>();
			statusList.add(Status.ACTIVE);
			if (getRandomBoolean())
				statusList.add(Status.ASSOCIATED);
			nameserver.setStatus(statusList);

			// IpAddressStruct data
			NameserverIpAddressesStruct ipAddresses = new NameserverIpAddressesStruct();
			int numberOfIp4 = ThreadLocalRandom.current().nextInt(0, 2);
			for (int indexIP = 0; indexIP < numberOfIp4; indexIP++) {
				IpAddress ipv41 = new IpAddressDAO();
				try {
					ipv41.setAddress(InetAddress.getByName("192.0.2." + indexIP));
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				ipv41.setType(4);
				ipAddresses.getIpv4Adresses().add(ipv41);
			}

			int numberOfIp6 = ThreadLocalRandom.current().nextInt(0, 2);
			for (int indexIP = 0; indexIP < numberOfIp6; indexIP++) {
				IpAddress ipv6 = new IpAddressDAO();
				try {
					ipv6.setAddress(InetAddress.getByName("2001:db8::12" + indexIP));
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				ipv6.setType(6);
				ipAddresses.getIpv6Adresses().add(ipv6);
				nameserver.setIpAddresses(ipAddresses);
			}

			if (hasEntities) {
				nameserver.setEntities(createRandomEntities(nameserver.getPunycodeName(), getRandomBoolean(),
						getRandomBoolean(), getRandomBoolean(), getRandomBoolean()));
			}
			if (hasRemarks) {
				nameserver.setRemarks(createRandomRemarks(nameserver.getLdhName(), getRandomBoolean()));
			}
			if (hasLinks) {
				nameserver.setLinks(createRandomLinks());
			}
			if (hasEvents) {
				nameserver.setEvents(createRandomEvents());
			}
			NameserverModel.storeToDatabase(nameserver, connection);
			nameservers.add(nameserver);
		}
		return nameservers;
	}

	private static List<Entity> createRandomEntities(String name, boolean hasEntities, boolean hasRemarks,
			boolean hasLinks, boolean hasEvents) throws RequiredValueNotFoundException, SQLException, IOException {
		List<Entity> entities = new ArrayList<Entity>();
		int numberOfEntities = ThreadLocalRandom.current().nextInt(1, 3);
		for (int index = 0; index < numberOfEntities; index++) {
			Entity entity = new EntityDAO();
			String entName = name.concat("-Ent-" + index);
			entity.setHandle(entName + "-Handle");
			entity.setPort43("whois.mx");
			List<Status> statusList = new ArrayList<Status>();
			statusList.add(Status.ACTIVE);
			if (getRandomBoolean())
				statusList.add(Status.ASSOCIATED);
			entity.setStatus(statusList);
			if (getRandomBoolean())
				entity.getRoles().add(Rol.SPONSOR);
			if (getRandomBoolean())
				entity.getRoles().add(Rol.REGISTRANT);
			if (getRandomBoolean())
				entity.getRoles().add(Rol.RESELLER);

			if (getRandomBoolean()) {
				PublicId pid = new PublicIdDAO();
				pid.setPublicId(entName + "-PId");
				pid.setType("Type");
				entity.getPublicIds().add(pid);
			}

			// Vcard data
			if (getRandomBoolean()) {
				VCard vCard = VCardTest.createVCardDao(null, entName, "company", "www." + entName + ".com", null, null,
						null, null, null);
				List<VCardPostalInfo> postalInfoList = new ArrayList<>();
				for (int i = 0; i < 2; i++) {
					postalInfoList.add(VCardTest.createVCardPostalInfo(null, null, null, "MX", "monterrey", null, null,
							null, "NL", null));
				}
				entity.getVCardList().add(vCard);
			}
			if (hasRemarks) {
				entity.setRemarks(createRandomRemarks(entName, getRandomBoolean()));
			}
			if (hasLinks) {
				entity.setLinks(createRandomLinks());
			}
			if (hasEvents) {
				entity.setEvents(createRandomEvents());
			}
			EntityModel.storeToDatabase(entity, connection);
			entities.add(entity);
		}
		return entities;
	}

	private static List<Remark> createRandomRemarks(String title, boolean hasLinks) {
		List<Remark> remarks = new ArrayList<Remark>();
		int numberOfRemarks = ThreadLocalRandom.current().nextInt(1, 3);
		for (int index = 0; index < numberOfRemarks; index++) {
			Remark remark = new RemarkDAO();
			remark.setLanguage("EN");
			String remTitle = title + "-Rem-" + index;
			remark.setTitle(remTitle);
			remark.setType("random type");
			if (hasLinks) {
				remark.setLinks(createRandomLinks());
			}
			int numberOfRemarkDescriptions = ThreadLocalRandom.current().nextInt(1, 3);
			for (int index2 = 0; index2 < numberOfRemarkDescriptions; index2++) {
				RemarkDescription rd = new RemarkDescriptionDAO();
				rd.setOrder(index2 + 1);
				rd.setDescription(remark.getTitle() + "-Description-" + index2);
				remark.getDescriptions().add(rd);
			}
			remarks.add(remark);
		}
		return remarks;
	}

	private static List<Link> createRandomLinks() {
		List<Link> links = new ArrayList<Link>();
		int numberOfLinks = ThreadLocalRandom.current().nextInt(1, 3);
		for (int index = 0; index < numberOfLinks; index++) {
			Link link = new LinkDAO();
			link.setValue("Link-" + index + ".com");
			link.setHref("link" + index);
			link.setRel("self");
			links.add(link);
		}
		return links;
	}

	private static List<Event> createRandomEvents() {
		List<Event> events = new ArrayList<Event>();
		int numberOfEvents = ThreadLocalRandom.current().nextInt(1, 3);
		for (int index = 0; index < numberOfEvents; index++) {
			Event event = new EventDAO();
			if (getRandomBoolean())
				event.setEventAction(EventAction.EXPIRATION);
			else
				event.setEventAction(EventAction.REGISTRATION);
			event.setEventDate(new Date());
			event.setEventActor("-EA-" + index);
			if (getRandomBoolean()) {
				event.setLinks(createRandomLinks());
			}
			events.add(event);
		}
		return events;
	}

	public static boolean getRandomBoolean() {
		return ThreadLocalRandom.current().nextInt(1, 3) == 1;
	}
}
