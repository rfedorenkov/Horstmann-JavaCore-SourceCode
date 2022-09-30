package corejava.v2ch03.stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.net.URL;

/**
 * В этой программе демонстрируется применение StAX-анализатора.
 * Программа выводит все гиперссылки из веб-страницы формата XHTML
 * Использование: java stax.StAXTest url
 * @author Cay Horstmann
 * @version 1.0 2007-06-23
 */
public class StAXTest {
    public static void main(String[] args) throws Exception {
        String urlString;
        if (args.length == 0) {
            urlString = "https://www.w3.org";
            System.out.println("Using " + urlString);
        } else urlString = args[0];
        URL url = new URL(urlString);
        InputStream in = url.openStream();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(in);
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (parser.getLocalName().equals("a")) {
                    String href = parser.getAttributeValue(null, "href");
                    if (href != null)
                        System.out.println(href);
                }
            }
        }
    }
}