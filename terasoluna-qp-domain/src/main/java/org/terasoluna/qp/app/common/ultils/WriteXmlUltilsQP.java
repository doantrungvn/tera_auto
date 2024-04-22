package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.util.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.LibraryManagement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WriteXmlUltilsQP {

	private static final String PROXIES = "proxies";
	private static final String PROXY = "proxy";
	private static final String PROXY_ID = "id";
	private static final String PROXY_ACTIVE = "active";
	private static final String PROXY_PROTOCOL = "protocol";
	private static final String PROXY_USER = "username";
	private static final String PROXY_PASS = "password";
	private static final String PROXY_HOST = "host";
	private static final String PROXY_PORT = "port";

	public static void addBatchModule(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filePath);
				Node modules = doc.getElementsByTagName("modules").item(0);
				Element module = doc.createElement("module");
				module.setTextContent("${terasoluna-qp}-batch-job");
				modules.appendChild(module);

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new FileOutputStream(filePath));
				transformer.transform(source, result);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
	}

	public static void processWriteMavenSetting(String filePath, AccountProfile accountProfile) {

		File file = new File(filePath);
		if (file.exists()) {

			XmlDocumentHelper helper = XmlDocumentHelper.createNew(filePath);

			Document doc = helper.getDocument();
			Node proxies = doc.getElementsByTagName(PROXIES).item(0);
			NodeList proxyList = doc.getElementsByTagName(PROXY);
			Element proxyElement = null;
			Node proxy = null;
			if (proxyList.getLength() == 0) {

				proxyElement = doc.createElement(PROXY);
				proxyElement.appendChild(doc.createElement(PROXY_ID));
				proxyElement.appendChild(doc.createElement(PROXY_ACTIVE));
				proxyElement.appendChild(doc.createElement(PROXY_PROTOCOL));
				proxyElement.appendChild(doc.createElement(PROXY_USER));
				proxyElement.appendChild(doc.createElement(PROXY_PASS));
				proxyElement.appendChild(doc.createElement(PROXY_HOST));
				proxyElement.appendChild(doc.createElement(PROXY_PORT));

				proxies.appendChild(proxyElement);
				proxy = proxyElement;
			} else {
				proxy = proxyList.item(0);
			}

			boolean useProxy = !StringUtils.isEmpty(accountProfile.getProxyHost());

			NodeList nodes = proxy.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (PROXY_ID.equals(node.getNodeName())) {
					node.setTextContent("optional");
				}
				if (PROXY_ACTIVE.equals(node.getNodeName())) {
					if (useProxy) {
						node.setTextContent("true");
					} else {
						node.setTextContent("false");
					}
				}
				if (PROXY_PROTOCOL.equals(node.getNodeName())) {
					node.setTextContent("http");
				}
				if (PROXY_USER.equals(node.getNodeName())) {
					node.setTextContent(accountProfile.getProxyUser());
				}
				if (PROXY_PASS.equals(node.getNodeName())) {
					node.setTextContent(accountProfile.getProxyPass());
				}
				if (PROXY_HOST.equals(node.getNodeName())) {
					node.setTextContent(accountProfile.getProxyHost());
				}
				if (PROXY_PORT.equals(node.getNodeName())) {
					if (StringUtils.isEmpty(accountProfile.getProxyPort())) {
						node.setTextContent("8080");
					} else {
						node.setTextContent(accountProfile.getProxyPort());
					}
				}
			}
			helper.persistDocument();
		}
	}

	public static void processWritePomFile(String filePath, List<LibraryManagement> libraryList) throws Exception {

		File file = new File(filePath);
		if (file.exists()) {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filePath);

				Node dependencyManagement = doc.getElementsByTagName("dependencyManagement").item(0);
				NodeList dependencies = doc.getElementsByTagName("dependencies");
				Element dependenciesElement = null;
				Node dependenciesNode = null;
				if (dependencies.getLength() == 0) {
					dependenciesElement = doc.createElement("dependencies");
					dependencyManagement.appendChild(dependenciesElement);
					dependenciesNode = dependenciesElement;
				} else {
					dependenciesNode = dependencies.item(0);
				}

				for (LibraryManagement libraryManagement : libraryList) {

					// create dependency element
					Element element = doc.createElement("dependency");

					// create groupId element
					Element groupId = doc.createElement("groupId");
					groupId.setTextContent(libraryManagement.getGroupId());
					element.appendChild(groupId);

					// create artifactId element
					Element artifactId = doc.createElement("artifactId");
					artifactId.setTextContent(libraryManagement.getArtifactId());
					element.appendChild(artifactId);

					// create version id
					if (!StringUtils.isEmpty(libraryManagement.getVersion())) {
						Element version = doc.createElement("version");
						version.setTextContent(libraryManagement.getVersion());
						element.appendChild(version);
					} else if (DataTypeUtils.equals("system", libraryManagement.getScope())) {
						Element version = doc.createElement("version");
						version.setTextContent("0.0.0");
						element.appendChild(version);
					}

					// create classifier element
					if (!StringUtils.isEmpty(libraryManagement.getClassifier())) {
						Element classifier = doc.createElement("classifier");
						classifier.setTextContent(libraryManagement.getClassifier());
						element.appendChild(classifier);
					}

					// create type element
					if (!StringUtils.isEmpty(libraryManagement.getType())) {
						Element type = doc.createElement("type");
						type.setTextContent(libraryManagement.getType());
						element.appendChild(type);
					}

					if (DataTypeUtils.notEquals(libraryManagement.getSystemFlag(), "2")) {
						// create scope element
						if (!StringUtils.isEmpty(libraryManagement.getScope())) {
							Element scope = doc.createElement("scope");
							scope.setTextContent(libraryManagement.getScope());
							element.appendChild(scope);
						}
	
						if (DataTypeUtils.equals("system", libraryManagement.getScope())) {
							Element systemPath = doc.createElement("systemPath");
							/*if (DataTypeUtils.equal(libraryManagement.getSystemFlag(), "2")) {*/
								//systemPath.setTextContent("${basedir}\\..\\lib\\" + libraryManagement.getArtifactId() + "_" + libraryManagement.getUploadFileName());
							/*} else {*/
								systemPath.setTextContent(libraryManagement.getSystemPath());
							/*}*/
							element.appendChild(systemPath);
						}
					}

					// create optional element
					if (libraryManagement.getOptionalFlg() == DbDomainConst.YesNoFlg.YES) {
						Element optional = doc.createElement("optional");
						optional.setTextContent("true");
						element.appendChild(optional);
					}

					dependenciesNode.appendChild(element);
				}

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new FileOutputStream(filePath));
				transformer.transform(source, result);
			} catch (ParserConfigurationException e) {
				throw e;
			} catch (SAXException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} catch (TransformerConfigurationException e) {
				throw e;
			} catch (TransformerException e) {
				throw e;
			}
		}
	}
}
