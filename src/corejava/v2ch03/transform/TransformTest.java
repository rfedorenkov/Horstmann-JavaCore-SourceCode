package corejava.v2ch03.transform;

import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * В этой программе демонстрируются преобразования языковыми
 * средствами XSLT. Преобразования выполняются над записями о
 * сотрудниках из файла employee.dat в формат XML. Для этого
 * нужно указать таблицу стилей в командной строке, например,
 * следующим образом:
 * java transform.TransformTest transform/makeprop.xsl
 * @version 1.03 2016-04-27
 * @author Cay Horstmann
 */
public class TransformTest {
    public static void main(String[] args) throws Exception {
        Path path;
        if (args.length > 0) path = Paths.get(args[0]);
        else path = Paths.get("transform", "makehtml.xsl");
        try (InputStream styleIn = Files.newInputStream(path)) {
            StreamSource styleSource = new StreamSource(styleIn);

            Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            try (InputStream docIn = Files.newInputStream(Paths.get("transform", "employee.dat"))) {
                t.transform(new SAXSource(new EmployeeReader(), new InputSource(docIn)),
                        new StreamResult(System.out));
            }
        }
    }
}

/**
 * Этот класс читает однородный файл employee.dat и уведомляет
 * о событиях в SAX-анализаторе, как будто он сам выполнил
 * синтаксический анализ XML-документа
 */
class EmployeeReader implements XMLReader {
    private ContentHandler handler;

    @Override
    public void parse(InputSource source) throws IOException, SAXException {
        InputStream stream = source.getByteStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String rootElement = "staff";
        AttributesImpl atts = new AttributesImpl();

        if (handler == null)
            throw new SAXException("No content handler");

        handler.startDocument();
        handler.startElement("", rootElement, rootElement, atts);
        String line;
        while ((line = in.readLine()) != null) {
            handler.startElement("", "employee", "employee", atts);
            StringTokenizer t = new StringTokenizer(line, "|");

            handler.startElement("", "name", "name", atts);
            String s = t.nextToken();
            handler.characters(s.toCharArray(), 0, s.length());
            handler.endElement("", "name", "name");

            handler.startElement("", "salary", "salary", atts);
            s = t.nextToken();
            handler.characters(s.toCharArray(), 0, s.length());
            handler.endElement("", "salary", "salary");

            atts.addAttribute("", "year", "year", "CDATA", t.nextToken());
            atts.addAttribute("", "month", "month", "CDATA", t.nextToken());
            atts.addAttribute("", "day", "month", "CDATA", t.nextToken());
            handler.startElement("", "hiredate", "hiredate", atts);
            handler.endElement("", "hiredate", "hiredate");
            atts.clear();

            handler.endElement("", rootElement, rootElement);
        }
        handler.endElement("", "employee", "employee");
        handler.endDocument();
    }

    @Override
    public void setContentHandler(ContentHandler newValue) {
        handler = newValue;
    }


    @Override
    public ContentHandler getContentHandler() {
        return handler;
    }

    // следующие методы являются всего лишь холостыми реализациями
    @Override
    public void parse(String systemId) throws IOException, SAXException { }

    @Override
    public void setErrorHandler(ErrorHandler handler) { }

    @Override
    public ErrorHandler getErrorHandler() { return null; }

    @Override
    public void setDTDHandler(DTDHandler handler) { }

    @Override
    public DTDHandler getDTDHandler() { return null; }

    @Override
    public void setEntityResolver(EntityResolver resolver) { }

    @Override
    public EntityResolver getEntityResolver() { return null; }

    @Override
    public void setProperty(String name, Object value) { }

    @Override
    public Object getProperty(String name) { return null; }

    @Override
    public void setFeature(String name, boolean value) { }

    @Override
    public boolean getFeature(String name) { return false; }
}