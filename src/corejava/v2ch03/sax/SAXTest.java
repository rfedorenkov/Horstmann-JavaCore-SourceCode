package corejava.v2ch03.sax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.URL;

/**
 * В этой программе демонстрируется применение SAX-анализатора.
 * Программа выводит все гиперссылки из веб-страницы формата XHTML
 * Использование: java SAXTest url
 * @version 1.00 2001-09-29
 * @author Cay Horstmann
 */
public class SAXTest {
    public static void main(String[] args) throws Exception {
        String url;
        if (args.length == 0) {
            url = "https://www.w3.org";
            System.out.println("Using " + url);
        } else url = args[0];

        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) {
                if (lName.equals("a") && attrs != null) {
                    for (int i = 0; i < attrs.getLength(); i++) {
                        String aName = attrs.getLocalName(i);
                        if (aName.equals("href"))
                            System.out.println(attrs.getValue(i));
                    }
                }
            }
        };

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        SAXParser saxParser = factory.newSAXParser();
        InputStream in = new URL(url).openStream();
        saxParser.parse(in, handler);
    }
}